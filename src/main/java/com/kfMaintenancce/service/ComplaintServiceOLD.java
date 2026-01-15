package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Image;

public interface ComplaintServiceOLD {
    Complaint createComplaint(Complaint complaint);
    public List<Complaint> getAllComplaints() ;
    public Complaint updateComplaint(int comp_id, Complaint updatedComplaint);
    public List<Complaint> getComplaintByLimit(int pageNo, int perPage);
    public int count();
    public List<Complaint> getComplaintByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
    public int getComplaintCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	String getNewTicketNo();
	List<Complaint> getunallocatedComplaints();
	List<Complaint> getAllocatedComplaintByEnginner(int empId);
	List<Complaint> getComplaintByEquipmentId(String equipmentId);
	
	public List<Complaint> getOpenComplaints();
	 public List<Complaint> getClosedComplaints();
	List<Complaint> getComplaintByRaisedBy(int userId);
	List<Complaint> getInprocessComplaints();
	List<Complaint> filterComplaints(int status, String filterType, Date date, Date fromDate, Date toDate);
	List<TicketResolutionReportDTO> getTicketResolutionReport(Date fromDate, Date toDate, Date selectedDate);
	void exportFilteredComplaints(int status, String filterType, Date date, Date fromDate, Date toDate,
			HttpServletResponse response);
	ByteArrayInputStream generateExcelReport(List<TicketResolutionReportDTO> reports);
	List<Complaint> getAllocateComplaints();
	List<Complaint> getNonAllocatedComplaints();
	Complaint saveComplaint(Complaint complaint);
	Complaint updateComplaint1(Complaint complaint);
	List<Complaint> getComplaintReport(Date fromDate, Date toDate, Date selectedDate);
	 public void uploadComplaintImages(int comp_id, MultipartFile[] files, int userId) throws IOException  ;
	 public List<Image> findComplaintsImagesById(int comp_id);
	 public Optional<Image> findComplaintImageById(int comp_id);
   
}
