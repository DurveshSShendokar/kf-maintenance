package com.kfMaintenancce.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.dto.MaintStatusDTO;
import com.kfMaintenancce.dto.MaintenanceDetailsDTO;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.UserDetails;


public interface MaintRepo  extends JpaRepository<Maint, Integer>, MaintRepoCustom{
	
	 @Query("SELECT m FROM Maint m WHERE m.schedule_date BETWEEN :startDate AND :endDate")
	    List<Maint> findByScheduleDateBetweens(Date startDate, Date endDate);
	
	 @Query("SELECT m FROM Maint m WHERE m.schedule_date BETWEEN :startDate AND :endDate AND m.lab.lab_id = :labId")
	 List<Maint> findByScheduleDateBetweenAndLab(@Param("startDate") Date startDate,
	                                              @Param("endDate") Date endDate,
	                                              @Param("labId") Integer labId);

	 
	 @Query("SELECT m FROM Maint m WHERE m.closedDate BETWEEN :fromDate AND :toDate")
	 List<Maint> getClosedMaintenancesBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	
	@Query("From Maint m where m.maint_id=?1")
	List<Maint> getByMaintId(int maintId);
	@Query("From Maint m where m.statusCode=1")
	List<Maint> getMaintList();
	
	@Query("from Maint m where m.machine.machine_id=?1 and m.statusCode=0 ORDER BY m.closedDate DESC")
	List<Maint> getDoneMaintenanceByMachine(int machineId);
	
	
	@Query("from Maint m where m.machine.machine_id=?1 and m.statusCode=1 and Date(m.schedule_date)=?2")
	List<Maint> getTodayMaintenanceByMachine(int machine_id,Date today);
	
	
	@Query("from Maint m where m.machine.machine_id=?1 and m.statusCode=1 and Date(m.schedule_date)>=?2 and Date(m.schedule_date)<=?3")
	List<Maint> getThisWeekMaintenanceDownByMachine(int machine_id, Date time, Date time2);
	
	@Query("from Maint m where m.machine.machine_id=?1 and m.done_by.id=?2 and m.statusCode=0 ORDER BY m.closedDate DESC")
	List<Maint> getDoneMaintenanceDownByMachine(int machine_id, int userId);
	
	

	 @Query("SELECT m FROM Maint m WHERE m.lab.lab_id = :labId")
	    List<Maint> findByLabId(@Param("labId") int labId);
    
//	@Query("SELECT m FROM Maint m WHERE m.done_by.id = ?1 AND m.statusCode = 0")
//	List<Maint> getDoneMaintenanceByUser(int userId);
	
	@Query("SELECT m FROM Maint m WHERE m.machine.machine_id IN " +
		       "(SELECT mo.machine.machine_id FROM MachineOwner mo WHERE mo.user.id = :userId) " +
		       "AND m.statusCode = 0 ORDER BY m.closedDate DESC")
		List<Maint> getDoneMaintenanceByUser(@Param("userId") int userId);

	
	@Query("SELECT m FROM Maint m WHERE m.statusCode = :statusCode")
	List<Maint> findByStatusCode(@Param("statusCode") int statusCode);
	
	   @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 ORDER BY m.schedule_date DESC")
	    Page<Maint> findPendingClosedApprovals(Pageable pageable);

	    // 2️⃣ Pagination + Searching
	    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 " +
	           "AND (LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.raisedBy.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.raisedBy.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	           "ORDER BY m.schedule_date DESC")
	    Page<Maint> searchPendingClosedApprovals(@Param("keyword") String keyword, Pageable pageable);

	    // 3️⃣ Count only
	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 0")
	    long countPendingClosedApprovals();
	
	
	
	
	
	
	/////////////////////////////////////////////////
	
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 ORDER BY m.closedDate DESC")
	    List<Maint> findClosedApprovals();
	  
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.closedDate BETWEEN :fromDate AND :toDate ORDER BY m.closedDate DESC")
	  List<Maint> findClosedApprovalsByDateRange(@Param("fromDate") Date fromDate,
	                                             @Param("toDate") Date toDate);
	  
	  
	// ✅ 1. Pagination only
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.closedDate BETWEEN :fromDate AND :toDate ORDER BY m.closedDate DESC")
	  Page<Maint> findClosedApprovalsByDateRange(@Param("fromDate") Date fromDate,
	                                             @Param("toDate") Date toDate,
	                                             Pageable pageable);


	  @Query(value = """
			    SELECT m.* 
			    FROM machine_maint m
			    LEFT JOIN machine_mst mc ON m.machine_id = mc.machine_id
			    LEFT JOIN access_user_details doneBy ON m.done_by = doneBy.id
			    LEFT JOIN access_user_details approvalBy ON m.approval_by = approvalBy.id
			    LEFT JOIN lab_mst l ON m.lab_id = l.lab_id
			    WHERE m.status_code = 2
			      AND m.closed_date BETWEEN :fromDate AND :toDate
			      AND (
			          :keyword IS NULL OR :keyword = '' OR
			          LOWER(COALESCE(mc.machine_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(doneBy.user_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(approvalBy.user_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(m.executive_remark, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(l.lab_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(m.tech_mark, '')) LIKE CONCAT('%', LOWER(:keyword), '%')
			      )
			    ORDER BY m.closed_date DESC
			    """,
			    countQuery = """
			    SELECT COUNT(*) 
			    FROM machine_maint m
			    LEFT JOIN machine_mst mc ON m.machine_id = mc.machine_id
			    LEFT JOIN access_user_details doneBy ON m.done_by = doneBy.id
			    LEFT JOIN access_user_details approvalBy ON m.approval_by = approvalBy.id
			    LEFT JOIN lab_mst l ON m.lab_id = l.lab_id
			    WHERE m.status_code = 2
			      AND m.closed_date BETWEEN :fromDate AND :toDate
			      AND (
			          :keyword IS NULL OR :keyword = '' OR
			          LOWER(COALESCE(mc.machine_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(doneBy.user_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(approvalBy.user_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(m.executive_remark, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(l.lab_name, '')) LIKE CONCAT('%', LOWER(:keyword), '%') OR
			          LOWER(COALESCE(m.tech_mark, '')) LIKE CONCAT('%', LOWER(:keyword), '%')
			      )
			    """,
			    nativeQuery = true)
			Page<Maint> searchClosedApprovalsByDateRangeNative(
			        @Param("fromDate") Date fromDate,
			        @Param("toDate") Date toDate,
			        @Param("keyword") String keyword,
			        Pageable pageable);


	  // ✅ 3. Count API
	  @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 2 AND m.closedDate BETWEEN :fromDate AND :toDate")
	  long countClosedApprovalsByDateRange(@Param("fromDate") Date fromDate,
	                                       @Param("toDate") Date toDate);

	  
	  //////////////

	  
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.machine.machine_id = :machineId ORDER BY m.closedDate DESC")
	  List<Maint> findClosedApprovalsByMachineId(@Param("machineId") int machineId);

	  
	  @Query("SELECT m FROM Maint m WHERE m.lab.labName = :labName AND m.approvalBit = 2 ORDER BY m.closedDate DESC")
	  List<Maint> findUnApprovalsByLab(@Param("labName") String labName);
	  
	  @Query("SELECT m FROM Maint m WHERE m.lab.labName = :labName AND m.approvalBit = 1 ORDER BY m.closedDate DESC")
	  List<Maint> findClosedApprovalsByLab(@Param("labName") String labName);
	  
	  
//	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.closedApprovalDate BETWEEN :fromDate AND :toDate")
//	  List<Maint> findClosedApprovalsByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);



	  @Query("SELECT m FROM Maint m WHERE m.machine.machine_id = :machineId "
		         + "AND (:fromDate IS NULL OR m.schedule_date >= :fromDate) "
		         + "AND (:toDate IS NULL OR m.closedDate <= :toDate) "
		         + "ORDER BY m.schedule_date DESC")
		    List<Maint> findByMachineAndDateRange(
		        @Param("machineId") int machineId,
		        @Param("fromDate") Date fromDate,
		        @Param("toDate") Date toDate
		    );
	

	  
	  
	  
	  
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 and m.approvalBit = 2  ORDER BY m.closedDate DESC" )
	    List<Maint> findUnApprovals();
	  
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.approvalBit = 2 AND m.machine.machine_id = :machineId ORDER BY m.closedDate DESC")
	  List<Maint> findUnApprovalsByMachineId(@Param("machineId") int machineId);

	  /////////////////////////////////////////////////////////////////////////////
	  
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.approvalBit = 2 AND m.unApprovalDate BETWEEN :fromDate AND :toDate")
	  List<Maint> findUnApprovalsByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


	// ✅ 1️⃣ Pagination only
	  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.approvalBit = 2 " +
	         "AND m.unApprovalDate BETWEEN :fromDate AND :toDate ORDER BY m.unApprovalDate DESC")
	  Page<Maint> findUnApprovalsByDatePaginated(@Param("fromDate") Date fromDate,
	                                             @Param("toDate") Date toDate,
	                                             Pageable pageable);


	  // ✅ 2️⃣ Pagination + Searching
	// Repository method
	  @Query(
			    value = "SELECT m.* FROM machine_maint m " +
			            "LEFT JOIN machine_mst mach ON m.machine_id = mach.machine_id " +
			            "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
			            "LEFT JOIN access_user_details u ON m.approval_by = u.id " +
			            "WHERE m.status_code = 1 " +
			            "AND m.approval_bit = 2 " +
			            "AND DATE(m.un_approval_date) BETWEEN :fromDate AND :toDate " +
			            "AND (" +
			            "  :keyword IS NULL OR " +
			            "  LOWER(mach.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(CONCAT(u.first_name, ' ', u.last_name)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(m.executive_remark) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			            ") " +
			            "ORDER BY m.un_approval_date DESC",
			    countQuery = "SELECT COUNT(*) FROM machine_maint m " +
			            "LEFT JOIN machine_mst mach ON m.machine_id = mach.machine_id " +
			            "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
			            "LEFT JOIN access_user_details u ON m.approval_by = u.id " +
			            "WHERE m.status_code = 1 " +
			            "AND m.approval_bit = 2 " +
			            "AND DATE(m.un_approval_date) BETWEEN :fromDate AND :toDate " +
			            "AND (" +
			            "  :keyword IS NULL OR " +
			            "  LOWER(mach.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(CONCAT(u.first_name, ' ', u.last_name)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			            "  LOWER(m.executive_remark) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			            ")",
			    nativeQuery = true
			)
			Page<Maint> searchUnApprovalsByDate(
			    @Param("fromDate") Date fromDate,
			    @Param("toDate") Date toDate,
			    @Param("keyword") String keyword,
			    Pageable pageable
			);



	  // ✅ 3️⃣ Count API
	  @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.approvalBit = 2 " +
	         "AND m.unApprovalDate BETWEEN :fromDate AND :toDate")
	  long countUnApprovalsByDate(@Param("fromDate") Date fromDate,
	                              @Param("toDate") Date toDate);
//////////////////////////////////////////////////////////////////////////////
	  
	  
	    @Query("SELECT c FROM MaintenenaceCheckPoint c WHERE c.maint.maint_id = :maintId")
	    List<MaintenenaceCheckPoint> findCheckpointsByMaintId(@Param("maintId") int maintId);


	
	@Query("from Maint m where m.machine.machine_id=?1 and m.statusCode=1 and m.schedule_date < CURRENT_DATE")
	List<Maint> getOverdueMaintenanceByMachine(int machine_id);

	
	
	@Query("from Maint m where m.machine.eqid=?1")
	List<Maint> getByMaintEqipId(String equipmentId);
	
	@Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?2 AND m.machine.machine_id IN ?1")
	 int getMaintenanceCountByMachineIDsAndStatus(List<Integer> machineIds, int statusCode);
	 @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?2 AND m.machine.machine_name=?1")
	int getMaintenanceCountByMachineNamesAndStatus(String machieName, int i);
	 
	 @Query(value = "SELECT COUNT(*) FROM machine_maint WHERE DATE(schedule_date) = :date", nativeQuery = true)
	    int getTodayCount(@Param("date") LocalDate date);
	 
	 @Query("SELECT COUNT(m) FROM Maint m")
	    int countTotalMaintenances();
	 
	 @Query("SELECT m.machine.machine_name FROM Maint m WHERE m.schedule_date = :date OR m.statusCode = :status")
	 String getMachineNameByStatusAndDate(@Param("date") Date date, @Param("status") int status);

	 @Query("SELECT m.machine.machine_name  FROM Maint m  WHERE m.schedule_date = :date OR m.statusCode = :status")
	 List<String> getMachineNameByStatusAndDates(Date date, int status);

	 
	 @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?1 AND MONTH(m.schedule_date) = ?2 AND YEAR(m.schedule_date) = ?3")
	 int getMaintenanceCountByMonthAndYear(int statusCode, int month, int year);

	 @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?1 AND YEAR(m.schedule_date) = ?2")
	 int getMaintenanceCountByYear(int statusCode, int year);

	 
	 @Query("from Maint m where Date(m.schedule_date) = ?1")
	    List<Maint> getTodayMaintenanceRecords(Date today);
	    
	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1")
	    int countActiveMaintenances();
	    
	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 0")
	    int countInactiveMaintenances();
	    
	    @Query("SELECT u FROM UserDetails u WHERE u.id = :userId")
	    UserDetails findUserById(@Param("userId") int userId);
	    ////////////////////////////££££££££££££££££££££££££
	  
	    @Query("SELECT m FROM Maint m WHERE m.done_by.id = :userId  and m.statusCode IN (0,2)  AND m.closedDate BETWEEN :fromDate AND :toDate")
	    List<Maint> findByDoneByIdAndDateBetween(@Param("userId") int userId, 
	                                             @Param("fromDate") Date fromDate, 
	                                             @Param("toDate") Date toDate);

	    // ✅ 1. Date range + userId + pagination
	    @Query("SELECT m FROM Maint m WHERE m.done_by.id = :userId AND m.statusCode IN (0,2) " +
	           "AND m.closedDate BETWEEN :fromDate AND :toDate")
	    Page<Maint> findByDoneByIdAndDateBetween(
	            @Param("userId") int userId,
	            @Param("fromDate") Date fromDate,
	            @Param("toDate") Date toDate,
	            Pageable pageable);

	    // ✅ 2. Date range + userId + search + pagination
	    @Query("SELECT m FROM Maint m " +
	           "WHERE m.done_by.id = :userId AND m.statusCode IN (0,2) " +
	           "AND m.closedDate BETWEEN :fromDate AND :toDate " +
	           "AND (" +
	           "LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	           ")")
	    Page<Maint> searchByUserIdDateAndKeyword(
	            @Param("userId") int userId,
	            @Param("fromDate") Date fromDate,
	            @Param("toDate") Date toDate,
	            @Param("keyword") String keyword,
	            Pageable pageable);

	    // ✅ 3. Count only (for summary purposes)
	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.done_by.id = :userId AND m.statusCode IN (0,2) " +
	           "AND m.closedDate BETWEEN :fromDate AND :toDate")
	    long countByUserIdAndDateBetween(
	            @Param("userId") int userId,
	            @Param("fromDate") Date fromDate,
	            @Param("toDate") Date toDate);

	    //////////////////////////////////££££££££££££££££££££££££££££££££££££££££££££
	    @Query("SELECT m FROM MaintenenaceCheckPoint m WHERE m.maint.maint_id = :maintId")
	    List<MaintenenaceCheckPoint> findCheckPointsByMaintId(@Param("maintId") int maintId);
	   
//	    @Query("SELECT m FROM Maint m WHERE m.done_by.id = :doneById")
//	    List<Maint> findByDoneById(@Param("doneById") int doneById);
	    
	    @Query("SELECT m FROM Maint m WHERE m.done_by.id = :doneById OR m.statusCode = 1 ")
	    List<Maint> findByDoneById(@Param("doneById") int doneById);

	    
	    @Query("SELECT m.done_by FROM Maint m WHERE m.statusCode = 0")
	    List<UserDetails> findUsersWithClosedMaintenance();
	    
	    
	    @Query("From Maint m where m.statusCode = 1")
	    List<Maint> getOpenMaintenances();

//	    @Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN :startDate AND :endDate")
//	    List<Maint> findOpenMaintenancesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	    //////////////////////////////////////////////////////////////
	    @Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN ?1 AND ?2")
	    List<Maint> findOpenMaintenancesByDateRange(Date startDate, Date endDate);


	    @Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN :startDate AND :endDate")
	    Page<Maint> findOpenMaintenancesByDateRange(@Param("startDate") Date startDate,
	                                                @Param("endDate") Date endDate,
	                                                Pageable pageable);


	    @Query(
	    		  value = "SELECT * FROM machine_maint m " +
	    		          "LEFT JOIN machine_mst ma ON m.machine_id = ma.machine_id " +
	    		          "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
	    		          "LEFT JOIN access_user_details u ON m.raised_by = u.id " +
	    		          "WHERE m.status_code = 1 " +
	    		          "AND DATE(m.schedule_date) BETWEEN :startDate AND :endDate " +
	    		          "AND (" +
	    		          ":keyword = '' OR " +
	    		          "LOWER(ma.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		          "OR LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		          "OR LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		          "OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	    		          ") " +
	    		          "ORDER BY m.schedule_date DESC",
	    		  
	    		  countQuery = "SELECT COUNT(*) FROM machine_maint m " +
	    		               "LEFT JOIN machine_mst ma ON m.machine_id = ma.machine_id " +
	    		               "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
	    		               "LEFT JOIN access_user_details u ON m.raised_by = u.id " +
	    		               "WHERE m.status_code = 1 " +
	    		               "AND DATE(m.schedule_date) BETWEEN :startDate AND :endDate " +
	    		               "AND (" +
	    		               ":keyword = '' OR " +
	    		               "LOWER(ma.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		               "OR LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		               "OR LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	    		               "OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	    		               ")",
	    		  nativeQuery = true
	    		)
	    		Page<Maint> searchOpenMaintenances(
	    		        @Param("startDate") Date startDate,
	    		        @Param("endDate") Date endDate,
	    		        @Param("keyword") String keyword,
	    		        Pageable pageable
	    		);



	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN :startDate AND :endDate")
	    long countOpenMaintenances(@Param("startDate") Date startDate,
	                               @Param("endDate") Date endDate);
///////////////////////////////////////////////////////////////////
	 
	    
	    @Query("FROM Maint m WHERE m.statusCode = 0 AND m.closedDate BETWEEN :startDate AND :endDate")
	    List<Maint> findClosedMaintenancesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	    
	    
	    
	    @Query("SELECT new com.kfMaintenancce.dto.MaintStatusDTO(m) FROM Maint m WHERE m.statusCode = 0 ORDER BY m.closedDate ASC")
	    List<MaintStatusDTO> getClosedMaintenanceStatuses();

	    
	  


	    @Query("SELECT m FROM Maint m WHERE m.maint_id = :maintId AND m.statusCode = 1 AND  m.schedule_date < CURRENT_DATE")
	    List<Maint> getOverdueMaintenancesById(@Param("maintId") int maintId);

	    @Query("From Maint m")
	    List<Maint> getAllMaintenances();
		@Query("SELECT COUNT(m)  from Maint m where  m.statusCode=1 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2")
		int getOpenMaintenanceByDates(Date startOfWeek, Date endOfWeek);
		
		
		
		
		@Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE")
		List<Maint> getOverdueMaintenances();

		
		@Query("SELECT COUNT(m) from Maint m where  m.statusCode=0 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2")
		int getClosedMaintenanceByDate(Date startOfWeek, Date endOfWeek);
		
		//////////////////////////////////////////////////////////////////////////

		@Query("FROM Maint m WHERE m.statusCode = 1 AND Date(m.schedule_date) BETWEEN :fromDate AND :toDate")
		List<Maint> getOverduesMaintenanceByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
		
		 // ✅ 1️⃣ Pagination
	    @Query("FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) BETWEEN :fromDate AND :toDate ORDER BY m.schedule_date DESC")
	    Page<Maint> findOverduesMaintenanceByDateRange(@Param("fromDate") Date fromDate,
	                                                   @Param("toDate") Date toDate,
	                                                   Pageable pageable);

	    // ✅ 2️⃣ Pagination + Search
	    @Query(value =
	            "SELECT m.* FROM machine_maint m " +
	            "LEFT JOIN machine_mst mach ON m.machine_id = mach.machine_id " +
	            "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
	            "WHERE m.status_code = 1 " +
	            "AND (:fromDate IS NULL OR DATE(m.schedule_date) >= :fromDate) " +
	            "AND (:toDate IS NULL OR DATE(m.schedule_date) <= :toDate) " +
	            "AND (" +
	            "  :keyword IS NULL OR " +
	            "  LOWER(mach.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	            "  LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	            "  LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	            ") " +
	            "ORDER BY m.schedule_date DESC",
	            countQuery =
	            "SELECT COUNT(*) FROM machine_maint m " +
	            "LEFT JOIN machine_mst mach ON m.machine_id = mach.machine_id " +
	            "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
	            "WHERE m.status_code = 1 " +
	            "AND (:fromDate IS NULL OR DATE(m.schedule_date) >= :fromDate) " +
	            "AND (:toDate IS NULL OR DATE(m.schedule_date) <= :toDate) " +
	            "AND (" +
	            "  :keyword IS NULL OR " +
	            "  LOWER(mach.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	            "  LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	            "  LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	            ")",
	            nativeQuery = true)
	    Page<Maint> searchOverduesMaintenance(
	            @Param("fromDate") Date fromDate,
	            @Param("toDate") Date toDate,
	            @Param("keyword") String keyword,
	            Pageable pageable);



	    // ✅ 3️⃣ Count
	    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) BETWEEN :fromDate AND :toDate")
	    long countOverduesMaintenance(@Param("fromDate") Date fromDate,
	                                  @Param("toDate") Date toDate);
		////////////////////////////////////////////////////////////////////////////////////////////////////////

		////
		@Query("from Maint m where m.statusCode=1 and  m.schedule_date < CURRENT_DATE  ORDER BY m.schedule_date DESC")
		List<Maint> getCurrentWeekOverudesMaintenance(Date startOfWeek);
		
		
		  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE ORDER BY m.schedule_date DESC")
		    Page<Maint> findCurrentWeekOverdues(Pageable pageable);

		    // 2️⃣ Pagination + Search
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE " +
		           "AND (LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(m.raisedBy.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(m.raisedBy.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
		           "ORDER BY m.schedule_date DESC")
		    Page<Maint> searchCurrentWeekOverdues(@Param("keyword") String keyword, Pageable pageable);

		    // 3️⃣ Count only
		    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE")
		    long countCurrentWeekOverdues();
		
		
		/////
		@Query("from Maint m where  m.statusCode=0 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2")
		List<Maint> getCurrentWeekClosedMaintenance(Date startOfWeek, Date endOfWeek);
		
		
		
		 @Query("FROM Maint m WHERE m.schedule_date <= :currentDate AND m.statusCode = 1")
		    List<Maint> getCurrentWeekOverdueMaintenance(@Param("currentDate") Date currentDate);

		@Query("from Maint m where m.statusCode=0")
		List<Maint> getCurrentWeekClosedMaintenance();
		
		
		@Query("FROM Maint m WHERE m.machine.eqid = ?1 AND m.statusCode = ?2")
		List<Maint> getMaintenancesByEqidAndStatus(String eqid, int statusCode);
		
		  @Query("FROM Maint m WHERE m.machine.eqid = :eqid AND m.statusCode = :statusCode AND m.schedule_date < CURRENT_DATE")
		    List<Maint> getOverdueMaintenances(@Param("eqid") String eqid,@Param("statusCode") int statusCode);
		  
		  @Query("FROM Maint m WHERE m.machine.eqid = :eqid AND m.statusCode = :statusCode AND m.schedule_date >= CURRENT_DATE")
		    List<Maint> getOpenMaintenances(@Param("eqid") String eqid, @Param("statusCode") int statusCode);
		
		
		
		
		
		
		
		

		@Query("FROM Maint m WHERE m.machine.eqid = ?1")
		List<Maint> getAllMaintenancesByEqid(String eqid);


		
		@Query("from Maint m where m.schedule_date between ?1 and ?2")
		List<Maint> findByScheduleDateBetween(Date fromDate, Date toDate);
		
		
		@Query("SELECT m FROM Maint m WHERE m.closedApprovalDate BETWEEN :fromDate AND :toDate")
		List<Maint> findClosedApprovalsByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

		
		
		@Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?2 AND m.machine.machine_id = ?1")
		int getMaintenanceCountByMachineIdAndStatus(int machineId, int statusCode);

		@Query("FROM Maint m WHERE m.statusCode = ?2 AND m.machine.machine_id = ?1")
		List<Maint> getMaintenancesByMachineIdAndStatus(int machineId, int statusCode);

		@Query("FROM Maint m WHERE m.machine.machine_id = ?1")
		List<Maint> getAllMaintenancesByMachineId(int machineId);


		
//		
		@Query("from Maint m where  m.statusCode=1 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2 and m.machine.machine_name=?3")
		List<Maint> getCurrentWeekOpenMaintenanceByMachineName(Date startDate, Date endDate, String machineName);
		
		@Query("from Maint m where  m.statusCode=0 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2 and m.machine.machine_name=?3")
		List<Maint> getCurrentWeekClosedMaintenanceByMachineName(Date startDate, Date endDate, String machineName);
		
		
		@Query("from Maint m where m.statusCode=1 and  Date(m.schedule_date)<=?1 and m.machine.machine_name=?2")
		List<Maint> getCurrentWeekOverudesMaintenanceByMachineName(Date startOfWeek, String machineName);
		
		 @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?2 AND m.machine.machine_name=?1 and Date(m.schedule_date)>=?3 and Date(m.schedule_date)<=?4")
		int getMaintenanceCountByMachineNamesAndStatusAndDate(String machineName, int i, Date startDate, Date endDate);
		 
		
			
			@Query(" SELECT COUNT(m) from Maint m where m.statusCode=1 and  m.schedule_date >= CURRENT_DATE")
			int countOpeneMaintenancesByDate(Date date);
			
//////////			///////////////////		
			
			  @Query("SELECT m FROM Maint m WHERE m.schedule_date <= :currentDate AND m.statusCode = 1 AND m.lab.labName = :labName")
			    List<Maint> getOverduesMaintenaceByDatesAndLab(@Param("currentDate") Date currentDate, @Param("labName") String labName);

			    @Query("SELECT m FROM Maint m WHERE  m.statusCode = 1 AND m.lab.labName = :labName")
			    List<Maint> getOpeneMaintenancesByLab( @Param("labName") String labName);

			    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 AND m.lab.labName = :labName ORDER BY m.closedDate DESC")
			    List<Maint> getClosedMaintenancesByLab(@Param("labName") String labName);
			    
			    ///////////////////////////////////////////////////////////////////////////
			    //for closed card
			    @Query("FROM Maint m WHERE m.statusCode = 0 ORDER BY m.closedDate DESC")
			    List<Maint> getClosedMaintenances();

			    @Query("FROM Maint m WHERE m.statusCode = 0 "
			    	     + "AND (:fromDate IS NULL OR m.closedDate >= :fromDate) "
			    	     + "AND (:toDate IS NULL OR m.closedDate <= :toDate) "
			    	     + "ORDER BY m.closedDate DESC")
			    	Page<Maint> findClosedMaintenancesByDateRange(
			    	        @Param("fromDate") Date fromDate,
			    	        @Param("toDate") Date toDate,
			    	        Pageable pageable);


			    @Query(
			    		  value = "SELECT * FROM machine_maint m " +
			    		          "LEFT JOIN machine_mst ma ON m.machine_id = ma.machine_id " +
			    		          "LEFT JOIN access_user_details u ON m.done_by = u.id " +
			    		          "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
			    		          "WHERE m.status_code = 0 " +
			    		          "AND (:fromDate IS NULL OR m.closed_date >= :fromDate) " +
			    		          "AND (:toDate IS NULL OR m.closed_date <= :toDate) " +
			    		          "AND (" +
			    		          "LOWER(ma.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			    		          "OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			    		          "OR LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			    		          ") " +
			    		          "ORDER BY m.closed_date DESC",

			    		  countQuery = "SELECT COUNT(*) FROM machine_maint m " +
			    		          "LEFT JOIN machine_mst ma ON m.machine_id = ma.machine_id " +
			    		          "LEFT JOIN access_user_details u ON m.done_by = u.id " +
			    		          "LEFT JOIN lab_mst l ON m.lab_id = l.lab_id " +
			    		          "WHERE m.status_code = 0 " +
			    		          "AND (:fromDate IS NULL OR m.closed_date >= :fromDate) " +
			    		          "AND (:toDate IS NULL OR m.closed_date <= :toDate) " +
			    		          "AND (" +
			    		          "LOWER(ma.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			    		          "OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			    		          "OR LOWER(l.lab_name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			    		          ")",
			    		  nativeQuery = true
			    		)
			    		Page<Maint> searchClosedMaintenancesByDateRange(
			    		        @Param("keyword") String keyword,
			    		        @Param("fromDate") Date fromDate,
			    		        @Param("toDate") Date toDate,
			    		        Pageable pageable);



			    @Query("SELECT COUNT(m) FROM Maint m " +
			    	       "WHERE m.statusCode = 0 " +
			    	       "AND (:fromDate IS NULL OR m.closedDate >= :fromDate) " +
			    	       "AND (:toDate IS NULL OR m.closedDate <= :toDate)")
			    	long countClosedMaintenancesByDateRange(
			    	        @Param("fromDate") Date fromDate,
			    	        @Param("toDate") Date toDate);

			    
			    /////////////////////////////////////////////////////////////////////////////
			    
			    
			    
			  //total maintenance counts and records
				@Query(" from Maint m where m.statusCode=1 and  m.schedule_date >= CURRENT_DATE ORDER BY m.schedule_date ASC")
				List<Maint>  getOpeneMaintenancesByDate(Date date);
				
				  @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date >= CURRENT_DATE ORDER BY m.schedule_date ASC")
				    Page<Maint> findOpenMaintenances(Pageable pageable);

				    // 2️⃣ Search open maintenance (pagination + search)
				    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date >= CURRENT_DATE " +
				           "AND (LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				           "LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				           "LOWER(m.frequency) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
				           "LOWER(m.raisedBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
				           "ORDER BY m.schedule_date ASC")
				    Page<Maint> searchOpenMaintenances(@Param("keyword") String keyword, Pageable pageable);

				    // 3️⃣ Count of open maintenance
				    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date >= CURRENT_DATE")
				    long countOpenMaintenances();
				    
				    
				    ////
				
				
				
			
				@Query("  from Maint m where m.statusCode=1 and m.schedule_date < CURRENT_DATE ORDER BY m.schedule_date DESC")
				List<Maint> getOverduesMaintenaceByDates(Date startOfWeek);
			
			@Query("from Maint m where  m.statusCode=1 and Date(m.schedule_date)>=?1 and Date(m.schedule_date)<=?2")
			List<Maint> getCurrentWeekOpenMaintenance(Date startOfWeek, Date endOfWeek);
			
			@Query("from Maint m where m.statusCode = 1 and Date(m.schedule_date) >= ?1 and Date(m.schedule_date) <= ?2 and m.lab.labName = ?3")
			List<Maint> getCurrentWeekOpenMaintenanceByLab(Date startOfWeek, Date endOfWeek, String labName);
			
			@Query("from Maint m where m.statusCode = 0 and Date(m.schedule_date) >= ?1 and Date(m.schedule_date) <= ?2 and m.lab.labName = ?3")
			List<Maint> getCurrentWeekClosedMaintenanceByLab(Date startOfWeek, Date endOfWeek, String labName);
			
			@Query("from Maint m where m.statusCode = 1 and Date(m.schedule_date) >= ?1 and Date(m.schedule_date) <= ?2 and m.machine.machine_name = ?3 and m.lab.labName = ?4")
			List<Maint> getCurrentWeekOpenMaintenanceByMachineAndLab(Date startDate, Date endDate, String machineName, String labName);

			@Query("from Maint m where m.statusCode = 0 and Date(m.schedule_date) >= ?1 and Date(m.schedule_date) <= ?2 and m.machine.machine_name = ?3 and m.lab.labName = ?4")
			List<Maint> getCurrentWeekClosedMaintenanceByMachineAndLab(Date startDate, Date endDate, String machineName, String labName);



			
			
			@Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = ?2 AND Date(m.schedule_date) = ?1 AND m.lab.labName = ?3")
			int getMaintenanceCountByStatusAndDateAndLab(Date date, int status, String labName);

			@Query("SELECT COUNT(m) FROM Maint m WHERE m.schedule_date <= :currentDate AND m.statusCode = 1 AND m.lab.labName = :labName")
			int getOverduesMaintenaceByDatesAndLabs(@Param("currentDate") Date currentDate, @Param("labName") String labName);

			@Query("SELECT m.machine.machine_name FROM Maint m WHERE m.statusCode = ?2 OR m.schedule_date = ?1 AND m.lab.labName = ?3")
			List<String> getMachineNameByStatusAndDatesAndLab(Date date, int status, String labName);

			

			@Query("SELECT COUNT(m)  from Maint m where m.statusCode=?2 and Date(m.schedule_date)=?1")
			int getMaintenanceCountByStatusAndDate(Date date, int i);
			
			@Query(" SELECT COUNT(m) from Maint m where m.statusCode=1 and  m.schedule_date < CURRENT_DATE")
			int getOverduesMaintenaceByDate(Date startOfWeek);
			
//			@Query("SELECT COUNT(m) FROM Maint m WHERE m.schedule_date < :currentDate AND m.statusCode = 1")
//			int getOverduesMaintenaceByDate(@Param("currentDate") Date currentDate);

			 @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date >= CURRENT_DATE AND m.machine.machine_id = :machineId")
			    int countOpeneMaintenancesByDate(@Param("machineId") int machineId);

			    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 0 AND m.machine.machine_id = :machineId")
			    int countInactiveMaintenances(@Param("machineId") int machineId);

			    @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.machine.machine_id = :machineId ORDER BY m.closedDate DESC")
			    List<Maint> findClosedApprovals(@Param("machineId") int machineId);

			    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) >= :startOfWeek AND DATE(m.schedule_date) <= :endOfWeek AND m.machine.machine_id = :machineId")
			    int getOpenMaintenanceByDates(@Param("startOfWeek") Date startOfWeek,
			                                  @Param("endOfWeek") Date endOfWeek,
			                                  @Param("machineId") int machineId);

			    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 0 AND DATE(m.schedule_date) >= :startOfWeek AND DATE(m.schedule_date) <= :endOfWeek AND m.machine.machine_id = :machineId")
			    int getClosedMaintenanceByDate(@Param("startOfWeek") Date startOfWeek,
			                                   @Param("endOfWeek") Date endOfWeek,
			                                   @Param("machineId") int machineId);

			    @Query("SELECT COUNT(m) FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE AND m.machine.machine_id = :machineId")
			    int getOverduesMaintenaceByDate(@Param("machineId") int machineId);
			
			@Query("FROM Maint m WHERE m.statusCode = 0 AND m.schedule_date BETWEEN :startDate AND :endDate AND m.lab.labName = :labName")
			List<Maint> findClosedMaintenancesByDateRangeAndLab(@Param("startDate") Date startDate, 
			                                                    @Param("endDate") Date endDate, 
			                                                    @Param("labName") String labName);
			
			@Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN :startDate AND :endDate AND m.lab.labName = :labName")
			List<Maint> findOpenMaintenancesByDateRangeAndLabName(
			        @Param("startDate") Date startDate,
			        @Param("endDate") Date endDate,
			        @Param("labName") String labName);

			
			
			
			@Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date >= CURRENT_DATE AND m.machine.machine_id = :machineId ORDER BY m.schedule_date ASC")
			List<Maint> findUpcomingOpenMaintenance(@Param("machineId") int machineId);

			@Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date BETWEEN :fromDate AND :toDate AND m.machine.machine_id = :machineId ORDER BY m.schedule_date ASC")
			List<Maint> findOpenMaintenanceByDates(@Param("fromDate") Date fromDate,
			                                       @Param("toDate") Date toDate,
			                                       @Param("machineId") int machineId);

			

			@Query("FROM Maint m WHERE m.statusCode = 0 AND m.schedule_date BETWEEN :fromDate AND :toDate AND m.machine.machine_id = :machineId")
			List<Maint> findClosedMaintenanceByDates(@Param("fromDate") Date fromDate,
			                                         @Param("toDate") Date toDate,
			                                         @Param("machineId") int machineId);

			@Query("FROM Maint m WHERE m.statusCode = 2 AND m.schedule_date BETWEEN :fromDate AND :toDate AND m.machine.machine_id = :machineId")
			List<Maint> findApprovedMaintenanceByDates(@Param("fromDate") Date fromDate,
			                                           @Param("toDate") Date toDate,
			                                           @Param("machineId") int machineId);

			@Query("FROM Maint m WHERE m.statusCode = 3 AND m.schedule_date BETWEEN :fromDate AND :toDate AND m.machine.machine_id = :machineId")
			List<Maint> findUnApprovedMaintenanceByDates(@Param("fromDate") Date fromDate,
			                                             @Param("toDate") Date toDate,
			                                             @Param("machineId") int machineId);

			@Query("FROM Maint m WHERE m.schedule_date BETWEEN :fromDate AND :toDate AND m.machine.machine_id = :machineId")
			List<Maint> findAllMaintenanceByDates(@Param("fromDate") Date fromDate,
			                                      @Param("toDate") Date toDate,
			                                      @Param("machineId") int machineId);

			@Query("FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE AND m.machine.machine_id = :machineId")
			List<Maint> findOverdueMaintenance(@Param("machineId") int machineId);

			
			
			
			
			
			/////////
			
			// Approved Closed
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 2 AND m.approvalBit = 1")
			List<Maint> findApprovedClosedPPM();

			// UnApproved Closed
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 3 AND m.approvalBit = 2")
			List<Maint> findUnApprovedClosedPPM();

			// Open Maintenance Records
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 1")
			List<Maint> findOpenMaintenanceRecords();

			// Overdue Maintenance Records
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE")
			List<Maint> findOverdueMaintenanceRecords();

			// Closed Maintenance Records
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 0")
			List<Maint> findClosedMaintenanceRecords();

			// Total Maintenance Records
			@Query("SELECT m FROM Maint m")
			List<Maint> findAllMaintenanceRecords();
			
			// Open Maintenance Records by Date Range
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 ORDER BY m.schedule_date DESC")
			List<Maint> findOpenMaintenanceRecordsByDates(Date startOfWeek, Date endOfWeek);

			// Closed Maintenance Records by Date Range
			@Query("SELECT m FROM Maint m WHERE m.statusCode = 0 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 ORDER BY m.schedule_date DESC")
			List<Maint> findClosedMaintenanceRecordsByDates(Date startOfWeek, Date endOfWeek);


			   // ✅ Approved Closed PPM - Pagination only
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 2 ORDER BY m.closedDate DESC")
		    Page<Maint> findApprovedClosedPPM(Pageable pageable);

		    // ✅ Approved Closed PPM - Pagination + Search (by machine name or eqid for example)
		    @Query("SELECT m FROM Maint m " +
		           "WHERE m.statusCode = 2 AND " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
		           "ORDER BY m.closedDate DESC")
		    Page<Maint> searchApprovedClosedPPM(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE m.statusCode = 2
		    	      AND (:fromDate IS NULL OR m.closedDate >= :fromDate)
		    	      AND (:toDate IS NULL OR m.closedDate <= :toDate)
		    	      AND (
		    	          :keyword IS NULL 
		    	          OR LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.closedDate DESC
		    	""")
		    	Page<Maint> searchApprovedClosedPPMs(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);



		    // 🔹 UnApproved Closed PPM
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 and m.approvalBit = 2 ORDER BY m.closedDate DESC")
		    Page<Maint> findUnApprovedClosedPPM(Pageable pageable);

		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 and m.approvalBit = 2 AND " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
		           "ORDER BY m.closedDate DESC")
		    Page<Maint> searchUnApprovedClosedPPM(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE m.statusCode = 1
		    	      AND m.approvalBit = 2
		    	      AND (:fromDate IS NULL OR m.closedDate >= :fromDate)
		    	      AND (:toDate IS NULL OR m.closedDate <= :toDate)
		    	      AND (
		    	          LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.closedDate DESC
		    	""")
		    	Page<Maint> searchUnApprovedClosedPPMs(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);


		    // 🔹 Open Maintenance
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1")
		    Page<Maint> findOpenMaintenance(Pageable pageable);

		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
		    Page<Maint> searchOpenMaintenance(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE m.statusCode = 1
		    	      AND (:fromDate IS NULL OR m.schedule_date >= :fromDate)
		    	      AND (:toDate IS NULL OR m.schedule_date <= :toDate)
		    	      AND (
		    	          LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.schedule_date DESC
		    	""")
		    	Page<Maint> searchOpenMaintenancess(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);



		    // 🔹 Overdue Maintenance
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE")
		    Page<Maint> findOverdueMaintenance(Pageable pageable);

		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND m.schedule_date < CURRENT_DATE AND " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
		    Page<Maint> searchOverdueMaintenance(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE m.statusCode = 1
		    	      AND m.schedule_date < CURRENT_DATE
		    	      AND (:fromDate IS NULL OR m.schedule_date >= :fromDate)
		    	      AND (:toDate IS NULL OR m.schedule_date <= :toDate)
		    	      AND (
		    	          LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.schedule_date DESC
		    	""")
		    	Page<Maint> searchOverdueMaintenancess(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);



		    // 🔹 Closed Maintenance
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0")
		    Page<Maint> findClosedMaintenance(Pageable pageable);

		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 AND " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
		    Page<Maint> searchClosedMaintenance(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE m.statusCode = 0
		    	      AND (:fromDate IS NULL OR m.closedDate >= :fromDate)
		    	      AND (:toDate IS NULL OR m.closedDate <= :toDate)
		    	      AND (
		    	          LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.closedDate DESC
		    	""")
		    	Page<Maint> searchClosedMaintenancess(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);



		    // 🔹 Total Maintenance
		    @Query("SELECT m FROM Maint m")
		    Page<Maint> findAllMaintenance(Pageable pageable);

		    @Query("SELECT m FROM Maint m WHERE " +
		           "(LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		           "OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
		    Page<Maint> searchAllMaintenance(@Param("keyword") String keyword, Pageable pageable);
		    
		    @Query("""
		    	    SELECT m FROM Maint m
		    	    WHERE (:fromDate IS NULL OR m.schedule_date >= :fromDate)
		    	      AND (:toDate IS NULL OR m.schedule_date <= :toDate)
		    	      AND (
		    	          LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	          OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		    	      )
		    	    ORDER BY m.schedule_date DESC
		    	""")
		    	Page<Maint> searchAllMaintenancess(
		    	        @Param("fromDate") Date fromDate,
		    	        @Param("toDate") Date toDate,
		    	        @Param("keyword") String keyword,
		    	        Pageable pageable);

		    
		    
		 // Open Maintenance Records by Date Range - Pagination
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 ORDER BY m.schedule_date DESC")
		    Page<Maint> findOpenMaintenanceByDates(Date startOfWeek, Date endOfWeek, Pageable pageable);

		    // Open Maintenance Records by Date Range - Pagination + Search (machine_name or eqid)
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 1 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 " +
		           "AND (LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', ?3, '%')) OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', ?3, '%'))) " +
		           "ORDER BY m.schedule_date DESC")
		    Page<Maint> searchOpenMaintenanceByDates(Date startOfWeek, Date endOfWeek, String keyword, Pageable pageable);

		    // Closed Maintenance Records by Date Range - Pagination
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 ORDER BY m.schedule_date DESC")
		    Page<Maint> findClosedMaintenanceByDates(Date startOfWeek, Date endOfWeek, Pageable pageable);

		    // Closed Maintenance Records by Date Range - Pagination + Search
		    @Query("SELECT m FROM Maint m WHERE m.statusCode = 0 AND DATE(m.schedule_date) >= ?1 AND DATE(m.schedule_date) <= ?2 " +
		           "AND (LOWER(m.machine.machine_name) LIKE LOWER(CONCAT('%', ?3, '%')) OR LOWER(m.lab.labName) LIKE LOWER(CONCAT('%', ?3, '%'))) " +
		           "ORDER BY m.schedule_date DESC")
		    Page<Maint> searchClosedMaintenanceByDates(Date startOfWeek, Date endOfWeek, String keyword, Pageable pageable);

		    
		    
		}
