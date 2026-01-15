package com.kfMaintenancce.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.QRScanDTO;
import com.kfMaintenancce.dto.ReportReqObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.NotificationRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    MachineOwnerRepo machineOwnerRepo;


    @GetMapping("/getNotificationByLimit")
    public @ResponseBody List<Notification> getNotificationByLimit(@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getNotificationByLimit(pageNo, perPage);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @RequestMapping(value = "/getNotificationByLimitAndGroupSearch", method = RequestMethod.POST)
    public @ResponseBody List<Notification> getNotificationByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
        List<Notification> list = new ArrayList<Notification>();
        try {

            list = notificationRepo.getNotificationByLimitAndGroupSearch(groupSearchDTO);


            int srNo = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value = "/getNotificationCountByLimitAndGroupSearch", method = RequestMethod.POST)
    public @ResponseBody int getNotificationCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
        int count = 0;
        try {

            count = notificationRepo.getNotificationCountByLimitAndGroupSearch(groupSearchDTO);


            int srNo = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    @GetMapping("/getNotificationCount")
    public @ResponseBody int getNotificationCount() {
        int count = 0;
        try {
            count = (int) notificationRepo.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    @DeleteMapping("/deleteNotification/{id}")
    public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();

        Optional<Notification> optionalRole = notificationRepo.findById(id);

        if (!optionalRole.isPresent()) {
            response.put("status", 404);
            response.put("message", "Notification not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            notificationRepo.deleteById(id);
            response.put("status", 200);
            response.put("message", "Notification deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            response.put("status", 409);
            response.put("message", "Cannot delete Notification because it is assigned to one or more users.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.put("status", 500);
            response.put("message", "An unexpected error occurred while deleting the Notification.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/getNotificationCountByDept")
    public @ResponseBody int getNotificationCountByDept(@RequestParam("deptName") String deptName) {
        int count = 0;
        try {
            count = notificationRepo.getNotificationCountByDept(deptName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @GetMapping("/getNotificationListByDept")
    public @ResponseBody List<Notification> getNotificationListByDept(@RequestParam("deptName") String deptName) {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getNotificationListByDept(deptName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/getNotificationListForAdmin")
    public @ResponseBody List<Notification> getNotificationListForAdmin() {
        List<Notification> list = new ArrayList<>();
        try {
            list = notificationRepo.getNotificationListByFor("ADMIN");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @GetMapping("/getAllNotificationByOwner")
    public @ResponseBody List<Notification> getAllNotificationByOwner(@RequestParam("userId") int userId) {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getNotificationsForUser(userId);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/getUserNotificationById")
    public @ResponseBody List<Notification> getUserNotificationById(@RequestParam("userId") int userId) {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getUserNotificationById(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @PutMapping("/markAsViewed/{id}")
    public ResponseEntity<Map<String, Object>> markAsViewed(@PathVariable int id) {

        Map<String, Object> response = new HashMap<>();

        try {
            notificationRepo.markAsViewed(id);

            response.put("code", 200);
            response.put("message", "Notification marked as viewed.");
            response.put("data", null);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("code", 500);
            response.put("message", "Error marking notification as viewed.");
            response.put("data", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/getAllNotification")
    public @ResponseBody List<Notification> getAllNotification() {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/getAllNotificationUnView")
    public @ResponseBody List<Notification> getAllNotificationUnView() {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getAllNotificationUnView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/getAllNotificationView")
    public @ResponseBody List<Notification> getAllNotificationView() {
        List<Notification> list = new ArrayList<Notification>();
        try {
            list = notificationRepo.getAllNotificationUnView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    @GetMapping("/getAllNotificationByDept")
    public ResponseEntity<List<Notification>> getAllNotificationByDept(@RequestParam String deptName) {
        try {
            return ResponseEntity.ok(notificationRepo.getAllByDept(deptName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllNotificationUnViewByDept")
    public ResponseEntity<List<Notification>> getAllNotificationUnViewByDept(@RequestParam String deptName) {
        try {
            return ResponseEntity.ok(notificationRepo.getAllUnViewedByDept(deptName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllNotificationViewByDept")
    public ResponseEntity<List<Notification>> getAllNotificationViewByDept(@RequestParam String deptName) {
        try {
            return ResponseEntity.ok(notificationRepo.getAllViewedByDept(deptName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


}
