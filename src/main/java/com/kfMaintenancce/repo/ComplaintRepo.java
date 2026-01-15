package com.kfMaintenancce.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.UserDetails;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint, Integer>, ComplaintRepoCustom {
	@Query("select count(*) from Complaint c where  substr(c .ticketNo,1,4)=?1")

	int getComplaintCountByYearMonth(String mnyr);
	@Query("select MAX(c.ticketNo) from  Complaint c where  substr(c.ticketNo,1,4)=?1")

	String getMaxComplaintNoByYearMonth(String mnyr);
	
	  @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 1 OR c.status = 2")
	    int countOpenComplaints();
	    
	    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 4")
	    int countClosedComplaints();

    @Query("SELECT COUNT(c) FROM Complaint c WHERE  DATE(c.complaintDate)=?1")
	int gettodayCounts(java.util.Date date);
   
    @Query("SELECT c FROM Complaint c WHERE c.ticketNo = :ticketNo")
    Complaint findByTicketNo(@Param("ticketNo") String ticketNo);
    
    
    
    @Query("SELECT c FROM Complaint c " +
    	       "WHERE c.allocateTo IS NULL " +
    	       "AND c.complaintTime <= :threshold")
    	List<Complaint> findUnallocatedComplaints(@Param("threshold") Date threshold);

    
  


	
	  @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1 AND MONTH(c.complaintDate) = ?2"
	  ) int countComplaintsByStatusAndMonth(int status, int month);
	 
    //
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1 AND MONTH(c.complaintDate) = ?2 AND YEAR(c.complaintDate) = ?3")
    int countComplaintsByStatusAndMonthAndYear(int status, int month, int year);

    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE MONTH(c.complaintDate) = ?1 AND YEAR(c.complaintDate) = ?2")
    int opencountComplaintsByMonthAndYear(int month, int year);
//
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 2")
    int countAllocateComplaints();
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 3")
    int countInprocessComplaints();
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateDate IS NULL OR c.allocateTime IS NULL")
    int countNonAllocatedComplaints();

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateTo = ?1 and  c.status =?2 ")

    int countByAllocateToAndStatus(UserDetails allocateTo, int status);
    
    	
    
    
    

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateTo = ?1")
    int countTotalComplaintsByEngineer(UserDetails allocateTo);
    
    @Query("SELECT c FROM Complaint c WHERE c.allocateTo = ?1 AND c.status = ?2")
    List<Complaint> findByAllocateToAndStatus(UserDetails allocateTo, int status);
    
    
   
    
    
    @Query("SELECT c FROM Complaint c WHERE c.status = ?1 AND DATE(c.complaintDate) = ?2")
    List<Complaint> findByStatusAndDate(int status, Date date);

    @Query("SELECT c FROM Complaint c WHERE c.status = ?1 AND c.complaintDate BETWEEN ?2 AND ?3")
    List<Complaint> findByStatusAndDateRange(int status, Date fromDate, Date toDate);

   
    List<Complaint> findAll();
	    
	 
		  @Query("FROM Complaint c WHERE c.status = 1")
		List<Complaint> getunallocatedComplaints();
		  @Query(" FROM Complaint c WHERE c.allocateTo.id = ?1")
		List<Complaint> getAllocatedComplaintByEnginner(int empId);
		  
		  @Query(" FROM Complaint c WHERE c.assetInventory.equipmentId = ?1")
		List<Complaint> getComplaintByEquipmentId(String qrcode);
		  
		   @Query("SELECT c FROM Complaint c WHERE c.status = 1")
		    List<Complaint> findOpenComplaints1Complaints();

		    @Query("SELECT c FROM Complaint c WHERE c.status = 4")
		    List<Complaint> findClosedComplaints1Complaints();
		    @Query(" FROM Complaint c WHERE c.raisedBy.id = ?1")
			List<Complaint> getComplaintByRaisedBy(int userId);
		    
		    List<Complaint> findByAllocateTo(UserDetails allocateTo);
		    
		    
		    // Fetch complaints by a date range
		    @Query("SELECT c FROM Complaint c WHERE c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findComplaintsByDateRange(
		            @Param("startDate") Date startDate,
		            @Param("endDate") Date endDate
		    );

		    @Query("SELECT c FROM Complaint c WHERE c.complaintDate >= :startDate AND c.complaintDate < :nextDay AND c.status = :status")
		    List<Complaint> findComplaintsByStatusAndDateRange(
		            @Param("status") Integer status,
		            @Param("startDate") Date startDate,
		            @Param("nextDay") Date nextDay
		    );

		    
		    @Query("SELECT c FROM Complaint c WHERE c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findByComplaintDateBetween(@Param("startDate") Date startDate, 
		                                               @Param("endDate") Date endDate);
		    
		    @Query("SELECT c FROM Complaint c WHERE c.status = :status")
		    List<Complaint> findComplaintsByStatus(@Param("status") int status);
		    
		    
		    
		    
		    
		    
		    // without date filter
		    
		    @Query("SELECT c FROM Complaint c WHERE c.status = 1 OR c.status = 2")
		    List<Complaint> findOpenComplaints();

		    @Query("SELECT c FROM Complaint c WHERE c.status = 4")
		    List<Complaint> findClosedComplaints();
		    
		    @Query("SELECT c FROM Complaint c WHERE c.status = 2")
		    List<Complaint> findAllocateComplaints();
		    
		    @Query("SELECT c FROM Complaint c WHERE c.status = 3")
		    List<Complaint> findInprocessComplaints();
		    
		    @Query("SELECT c FROM Complaint c WHERE c.allocateDate IS NULL OR c.allocateTime IS NULL")
		    List<Complaint> findNonAllocatedComplaints();
		    
		    
		    
		    	// with date filter
		    @Query("SELECT c FROM Complaint c WHERE (c.status = 1 OR c.status = 2) AND c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findOpenComplaintsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

		    @Query("SELECT c FROM Complaint c WHERE c.status = 4 AND c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findClosedComplaintsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

		    @Query("SELECT c FROM Complaint c WHERE c.status = 2 AND c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findAllocateComplaintsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

		    @Query("SELECT c FROM Complaint c WHERE c.status = 3 AND c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findInprocessComplaintsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

		    @Query("SELECT c FROM Complaint c WHERE c.status = 1 AND c.complaintDate BETWEEN :startDate AND :endDate")
		    List<Complaint> findNonAllocatedComplaintsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

		    
		    
}
