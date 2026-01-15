package com.kfMaintenancce.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.repo.ComplaintRepo;
import com.kfMaintenancce.repo.NotificationRepo;
@Service
public class ComplaintNotificationScheduler {

    @Autowired
    private ComplaintRepo complaintRepository;

    @Autowired
    private NotificationRepo notificationRepository;

 // Runs every 30 minutes
    @Scheduled(cron = "0 0/30 * * * ?")
    public void checkUnallocatedComplaints() {
        // Complaints older than 48 hours
        Date threshold = Date.from(LocalDateTime.now()
                .minusHours(48)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        List<Complaint> complaints = complaintRepository.findUnallocatedComplaints(threshold);

        System.out.println("Scheduler threshold: " + threshold);
        complaints.forEach(c -> System.out.println("ComplaintTime: " + c.getComplaintTime()));

        for (Complaint complaint : complaints) {
            boolean exists = notificationRepository.existsByComplaintAndType(
                    complaint.getComp_id(), "ADMIN");

            if (!exists) {
                Notification notification = new Notification();
                notification.setTitle("Unallocated Complaint");
                notification.setMessage("Complaint with Ticket No: " 
                        + complaint.getTicketNo() 
                        + " is pending allocation for more than 48 hours.");
                notification.setNotificationFor("ADMIN");
                notification.setNotificationForSpecId(complaint.getComp_id());
                notification.setNotificationDept("IT");
                notification.setRaisedTime(new Date());
                notification.setViewed(0);

                notificationRepository.save(notification);
                System.out.println("🚨 Notification created for complaint: " + complaint.getComp_id());
            }
        }
    }
}

