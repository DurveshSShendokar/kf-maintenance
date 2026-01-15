package com.kfMaintenancce.repo;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.UserDetails;

@Repository
public interface ComplaintRepoOLD extends JpaRepository<Complaint, Integer>, ComplaintRepoCustom {
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
   
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1 AND MONTH(c.complaintDate) = ?2")
    int countComplaintsByStatusAndMonth(int status, int month);
    
    //
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1 AND MONTH(c.complaintDate) = ?2 AND YEAR(c.complaintDate) = ?3")
    int countComplaintsByStatusAndMonthAndYear(int status, int month, int year);

    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE MONTH(c.complaintDate) = ?1 AND YEAR(c.complaintDate) = ?2")
    int opencountComplaintsByMonthAndYear(int month, int year);
    
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = ?1 AND MONTH(c.complaintDate) = ?2 AND YEAR(c.complaintDate) = ?3")
    int countComplaintByStatusAndMonthAndYear(int status, int month, int year);

    //
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 2")
    int countAllocateComplaints();
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 3")
    int countInprocessComplaints();
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateDate IS NULL OR c.allocateTime IS NULL")
    int countNonAllocatedComplaints();

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateTo = ?1 and  c.status =?2 ")

    int countByAllocateToAndStatus(UserDetails allocateTo, int status);
    
    	 
    
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
    
    
    

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateTo = ?1")
    int countTotalComplaintsByEngineer(UserDetails allocateTo);
    
    @Query("SELECT c FROM Complaint c WHERE c.allocateTo = ?1 AND c.status = ?2")
    List<Complaint> findByAllocateToAndStatus(UserDetails allocateTo, int status);
    
    @Query("SELECT c FROM Complaint c WHERE c.allocateTo = :allocateTo")
    Page<Complaint> findByEngineerPaginated(@Param("allocateTo") UserDetails allocateTo, Pageable pageable);
    
    @Query("SELECT c FROM Complaint c WHERE c.allocateTo = :allocateTo AND " +
    	       "(LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    	Page<Complaint> searchByEngineerAndKeyword(@Param("allocateTo") UserDetails allocateTo,
    	                                           @Param("keyword") String keyword,
    	                                           Pageable pageable);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateTo = :allocateTo AND c.status = :status")
    long countByAllocateToAndStatused(@Param("allocateTo") UserDetails allocateTo, @Param("status") int status);

    
    
    
    @Query("SELECT c FROM Complaint c WHERE c.status = ?1 AND DATE(c.complaintDate) = ?2")
    List<Complaint> findByStatusAndDate(int status, Date date);

    @Query("SELECT c FROM Complaint c WHERE c.status = ?1 AND Date(c.complaintDate) BETWEEN ?2 AND ?3")
    List<Complaint> findByStatusAndDateRange(int status, Date fromDate, Date toDate);
    @Query("SELECT c FROM Complaint c WHERE  Date(c.complaintDate) BETWEEN ?1 AND ?2")
    List<Complaint> findByDateRange( Date fromDate, Date toDate);
    @Query("SELECT c FROM Complaint c WHERE  Date(c.complaintDate)=?1")
    List<Complaint> findByDate( Date fromDate);
    
    
    
    
 // 🔹 Filter by exact date (pagination)
    @Query("SELECT c FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) = :date")
    Page<Complaint> findByStatusAndDate(@Param("status") int status, @Param("date") Date date, Pageable pageable);

    // 🔹 Filter by date range (pagination)
    @Query("SELECT c FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) BETWEEN :fromDate AND :toDate")
    Page<Complaint> findByStatusAndDateRange(@Param("status") int status,
                                             @Param("fromDate") Date fromDate,
                                             @Param("toDate") Date toDate,
                                             Pageable pageable);

    // 🔹 Search + exact date (pagination)
    @Query("SELECT c FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) = :date "
         + "AND (LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
         + "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')) "
         + "OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Complaint> searchByStatusAndDate(@Param("status") int status,
                                          @Param("date") Date date,
                                          @Param("keyword") String keyword,
                                          Pageable pageable);

    // 🔹 Search + date range (pagination)
    @Query("SELECT c FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) BETWEEN :fromDate AND :toDate "
         + "AND (LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
         + "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')) "
         + "OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Complaint> searchByStatusAndDateRange(@Param("status") int status,
                                               @Param("fromDate") Date fromDate,
                                               @Param("toDate") Date toDate,
                                               @Param("keyword") String keyword,
                                               Pageable pageable);

    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) = :date")
    long countByStatusAndDate(@Param("status") int status, @Param("date") Date date);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = :status AND DATE(c.complaintDate) BETWEEN :fromDate AND :toDate")
    long countByStatusAndDateRange(@Param("status") int status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	    
//	    // Count complaints with allocate date
//	    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateDate IS NOT NULL")
//	    long countComplaintsWithAllocateDate();
//
//	    // Count complaints without allocate date
//	    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.allocateDate IS NULL")
//	    long countComplaintsWithoutAllocateDate();

	    ///////////////////////////////////////////////////////////
	 
		  @Query("FROM Complaint c WHERE c.status = 1")
		List<Complaint> getunallocatedComplaints();
		  
		  @Query("FROM Complaint c WHERE c.status = 1")
		  Page<Complaint> findUnallocatedComplaints(Pageable pageable);
		  
		  

		  @Query("FROM Complaint c WHERE c.status = 1 AND " +
			       "(LOWER(c.description) LIKE %:keyword% OR LOWER(c.ticketNo) LIKE %:keyword% OR LOWER(c.assetInventory.lab.labName) LIKE %:keyword%)")
			Page<Complaint> findUnallocatedComplaintsBySearch(@Param("keyword") String keyword, Pageable pageable);

		  @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 1")
		  long countUnallocatedComplaints();

		  @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 1 AND " +
		         "(LOWER(c.description) LIKE %:keyword% OR LOWER(c.ticketNo) LIKE %:keyword% OR LOWER(c.assetInventory.lab.labName) LIKE %:keyword%)")
		  long countUnallocatedComplaintsBySearch(@Param("keyword") String keyword);

		  //////////////////////////////////////////////////////////
		  
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
		    @Query("SELECT COALESCE(COUNT(c), 0) FROM Complaint c WHERE (c.status = 1 OR c.status = 2) AND Month(c.complaintDate) = ?1")
			int opencountComplaintsByMonth( int month);
		    @Query("SELECT c FROM Complaint c WHERE c.allocateTo.id = ?1")

			List<Complaint> findByAllocateTo(int id);
		    
			 @Query("From Complaint c where  Date(c.complaintDate)=?1")
			List<Complaint> findeByDate(Date selectedDate);
			 
			 
			 @Query("SELECT c FROM Complaint c WHERE c.status = 2")
			 List<Complaint> findAllocatedComplaints();

			 
			 @Query("FROM Complaint e WHERE Date(e.complaintDate) BETWEEN :startDate AND :endDate")
			List<Complaint> findeByBeeterrnDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
			 
			 
			 
			 
			 
			 
			 
			// ✅ Pagination only
			 @Query("SELECT c FROM Complaint c WHERE c.status IN (1, 2)")
			 Page<Complaint> findOpenComplaints(Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE c.status = 4")
			 Page<Complaint> findClosedComplaints(Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE c.status = 2")
			 Page<Complaint> findAllocatedComplaints(Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE c.status = 3")
			 Page<Complaint> findInprocessComplaints(Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE c.allocateDate IS NULL OR c.allocateTime IS NULL")
			 Page<Complaint> findNonAllocatedComplaints(Pageable pageable);


			 // ✅ Pagination + Searching
			 @Query("SELECT c FROM Complaint c WHERE (LOWER(c.ticketNo) LIKE :keyword OR LOWER(c.assetInventory.lab.labName) LIKE :keyword) AND c.status IN (1, 2)")
			 Page<Complaint> searchOpenComplaints(@Param("keyword") String keyword, Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE (LOWER(c.ticketNo) LIKE :keyword OR LOWER(c.assetInventory.lab.labName) LIKE :keyword) AND c.status = 4")
			 Page<Complaint> searchClosedComplaints(@Param("keyword") String keyword, Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE (LOWER(c.ticketNo) LIKE :keyword OR LOWER(c.assetInventory.lab.labName) LIKE :keyword) AND c.status = 2")
			 Page<Complaint> searchAllocatedComplaints(@Param("keyword") String keyword, Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE (LOWER(c.ticketNo) LIKE :keyword OR LOWER(c.assetInventory.lab.labName) LIKE :keyword) AND c.status = 3")
			 Page<Complaint> searchInprocessComplaints(@Param("keyword") String keyword, Pageable pageable);

			 @Query("SELECT c FROM Complaint c WHERE (LOWER(c.ticketNo) LIKE :keyword OR LOWER(c.assetInventory.lab.labName) LIKE :keyword) AND (c.allocateDate IS NULL OR c.allocateTime IS NULL)")
			 Page<Complaint> searchNonAllocatedComplaints(@Param("keyword") String keyword, Pageable pageable);

			 
			 
			 
			 
			// ✅ Open Complaints with Date Filter
            @Query("SELECT c FROM Complaint c WHERE (c.status = 1 OR c.status = 2) " +
                    "AND (:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
                    "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
                    "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%'))) ")
            Page<Complaint> findOpenComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);


    @Query("SELECT c FROM Complaint c WHERE c.status = 4 " +
            "AND (:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
            "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
            "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<Complaint> findClosedComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);


    // ✅ Allocated Complaints with Date Filter
    @Query("SELECT c FROM Complaint c WHERE c.status = 2 " +
            "AND (:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
            "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
            "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<Complaint> findAllocatedComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);


    // ✅ In-Process Complaints with Date Filter
    @Query("SELECT c FROM Complaint c WHERE c.status = 3 " +
            "AND (:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
            "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
            "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<Complaint> findInprocessComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);


    // ✅ Non-Allocated Complaints with Date Filter
    @Query("SELECT c FROM Complaint c WHERE (c.allocateDate IS NULL OR c.allocateTime IS NULL) " +
            "AND (:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
            "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
            "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<Complaint> findNonAllocatedComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);


    // ✅ All Complaints (filtered by date range)
    @Query("SELECT c FROM Complaint c WHERE " +
            "(:fromDate IS NULL OR c.complaintDate >= :fromDate) " +
            "AND (:toDate IS NULL OR c.complaintDate <= :toDate) " +
            "AND (:search IS NULL OR LOWER(c.priority) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<Complaint> findAllComplaintsWithSearch(Date fromDate, Date toDate, String search, Pageable pageable);




    // 🔹 Pagination only - Date Range
			 @Query("SELECT c FROM Complaint c WHERE DATE(c.complaintDate) BETWEEN :startDate AND :endDate")
			 Page<Complaint> findByDateRangePaged(@Param("startDate") Date startDate,
			                                      @Param("endDate") Date endDate,
			                                      Pageable pageable);

			 // 🔹 Pagination only - Single Date
			 @Query("SELECT c FROM Complaint c WHERE DATE(c.complaintDate) = :selectedDate")
			 Page<Complaint> findByDatePaged(@Param("selectedDate") Date selectedDate, Pageable pageable);

			 // 🔍 Search + Pagination - Date Range
			 @Query("SELECT c FROM Complaint c WHERE DATE(c.complaintDate) BETWEEN :startDate AND :endDate " +
			        "AND (LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			        "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			 Page<Complaint> searchByDateRangeAndKeyword(@Param("startDate") Date startDate,
			                                             @Param("endDate") Date endDate,
			                                             @Param("keyword") String keyword,
			                                             Pageable pageable);

			 // 🔍 Search + Pagination - Single Date
			 @Query("SELECT c FROM Complaint c WHERE DATE(c.complaintDate) = :selectedDate " +
			        "AND (LOWER(c.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			        "OR LOWER(c.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			 Page<Complaint> searchByDateAndKeyword(@Param("selectedDate") Date selectedDate,
			                                        @Param("keyword") String keyword,
			                                        Pageable pageable);

			 
			 
			 @Query("SELECT COUNT(c) FROM Complaint c WHERE DATE(c.complaintDate) = :selectedDate")
			 long countByDate(@Param("selectedDate") Date selectedDate);

			 @Query("SELECT COUNT(c) FROM Complaint c WHERE DATE(c.complaintDate) BETWEEN :startDate AND :endDate")
			 long countByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

			 
			 
			 
			 
			 
}
