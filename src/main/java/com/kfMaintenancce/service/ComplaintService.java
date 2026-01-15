package com.kfMaintenancce.service;

import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import com.kfMaintenancce.dto.ComplaintStatusDTO;
import com.kfMaintenancce.dto.EngineerComplaint_DTO;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.UserDetails;

public interface ComplaintService {
    Complaint createComplaint(Complaint complaint);
    public List<Complaint> getAllComplaints() ;
    public Complaint updateComplaint(int comp_id, Complaint updatedComplaint);
    public List<Complaint> getComplaintByLimit(int pageNo, int perPage);
    public int count();
    public List<Complaint> getComplaintByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
    public int getComplaintCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	String getNewTicketNo();
	List<Complaint> getunallocatedComplaints();
	public List<Complaint> getUnallocatedComplaintsSearch(int pageNo, int perPage, String keyword) ;
	public long getUnallocatedComplaintsCount(String keyword) ;
	public Page<Complaint> getUnallocatedComplaints(int pageNo, int perPage);
	List<Complaint> getAllocatedComplaintByEnginner(int empId);
	List<Complaint> getComplaintByEquipmentId(String equipmentId);
	  public Map<String, Object> filterComplaintsed(int status, String filterType, Date date, Date fromDate, Date toDate,
              int page, int size, String keyword);
	   public long countComplaints(int status, String filterType, Date date, Date fromDate, Date toDate);
	List<Complaint> getComplaintByRaisedBy(int userId);
	
	List<Complaint> filterComplaints(int status, String filterType, Date date, Date fromDate, Date toDate);
	List<TicketResolutionReportDTO> getTicketResolutionReport(Date fromDate, Date toDate, Date selectedDate);
	void exportFilteredComplaints(int status, String filterType, Date date, Date fromDate, Date toDate,
			HttpServletResponse response);
	ByteArrayInputStream generateExcelReport(List<TicketResolutionReportDTO> reports);
	
	  public long getTicketResolutionCount(Date fromDate, Date toDate, Date selectedDate);
	  public Map<String, Object> getTicketResolutionReportPaged(Date fromDate, Date toDate, Date selectedDate, int page, int size, String keyword);
	public List<Complaint> getNonAllocatedComplaintsByDate(Date startDate, Date endDate);
	public List<Complaint> getInprocessComplaintsByDate(Date startDate, Date endDate);
	 public List<Complaint> getAllocateComplaintsByDate(Date startDate, Date endDate);
	 public List<Complaint> getClosedComplaintsByDate(Date startDate, Date endDate) ;
	 public List<Complaint> getOpenComplaintsByDate(Date startDate, Date endDate);
	
	
	 public ByteArrayInputStream generateExcelRepo(List<EngineerComplaint_DTO> repo);
	  public EngineerComplaint_DTO mapToEngineerComplaint_DTO(Complaint complaint);
	  public List<EngineerComplaint_DTO> getEngineerComplaintRecords(UserDetails allocateTo) ;
	  public ByteArrayInputStream exportComplaintsToExcel(List<Complaint> complaints) throws IOException;
	  public Map<String, ComplaintStatusDTO> getFilteredComplaints(Date startDate, Date endDate) ;
}
