package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.dto.BreakdownChatbotDTO;

import com.kfMaintenancce.model.Breakdown;


public interface BreakdownRepo  extends JpaRepository<Breakdown, Integer>,BreakdownRepoCustom{
	
	//closed breakdown are visible for other assign machine owner
	@Query("SELECT b FROM Breakdown b WHERE b.status = 3 AND b.machine.machine_id IN " +
		       "(SELECT m.machine.machine_id FROM MachineOwner m WHERE m.user.id = ?1)")
		List<Breakdown> getClosedBreakdownsForAssignedEngineers(int userId);

	 @Query("SELECT b FROM Breakdown b WHERE b.machine.machine_id = :machineId "
	         + "AND (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate) "
	         + "AND (:toDate IS NULL OR b.ticket_closed_time <= :toDate) "
	         + "ORDER BY b.ticket_raised_time DESC")
	    List<Breakdown> findByMachineAndDateRange(
	        @Param("machineId") int machineId,
	        @Param("fromDate") Date fromDate,
	        @Param("toDate") Date toDate
	    );
	
	
	@Query("From Breakdown b where b.machine.machine_id=?1 ")
	List<Breakdown> getBreakDownListByMachine(int machine_id);
	@Query("From Breakdown b where b.status=1 ")
	List<Breakdown> getBreakdownList();
	
	@Query("select count(*) from Breakdown b where  substr(b.bd_slip,1,4)=?1")
	int getBreakdownCountByYearMonth(String mnyr);
	
	@Query("select MAX(b.bd_slip) from  Breakdown b where  substr(b.bd_slip,1,4)=?1")
	String getMaxBreakdownNoNoByYearMonth(String mnyr);
	
	@Query("From Breakdown b where b.breakdown_id=?1 ")
	List<Breakdown> getBreakdoenById(int breakdown_id);
	@Query("From Breakdown b where b.status=?1 ")
	List<Breakdown> getBreakBodownByStatus(int i);
	
	@Query("From Breakdown b where Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	List<Breakdown> getHistory(Date startDate, Date endDate);
	@Query("From Breakdown b where b.machine.machine_name=?1 ")
	List<Breakdown> getBreakDownByMachineName(String machineName);
	@Query("From Breakdown b where b.machine.machine_name=?1 and b.ticket_raised_time>=?2 and b.ticket_raised_time<=?3")
	List<Breakdown> getBreakDownByMachineNameAndDateBeetween(String machine, Date janS, Date janE);
	@Query("From Breakdown b where b.machine.machine_name=?1 and b.tr_month=?2 ")
	List<Breakdown> getBreakDownByMachineNameAndMonth(String machine, String string);
	
	
	@Query("select count(b) From Breakdown b where b.machine.machine_name=?1 and b.tr_month=?2 and YEAR(b.ticket_raised_time)=?3 ")
	int getNoOfBreakDownByMachineNameAndMonth(String machine, String string,int year);
	
	@Query("select COALESCE(SUM(tc_tr_hr), 0)  From Breakdown b where b.machine.machine_name=?1 and b.tr_month=?2 and YEAR(b.ticket_raised_time)=?3 ")
	double getSumOfBreakDownTimeByMachineNameAndMonth(String machine, String string,int year);
	
	@Query("select count(b)From Breakdown b where b.machine.machine_name=?1 and b.tr_month=?2 and YEAR(b.ticket_raised_time)=?3  ")
	int getCountOfBreakDownTimeByMachineNameAndMonth(String machine, String string,int year);
	
	@Query("select count(b)From Breakdown b where b.status=3 and  b.machine.machine_name=?1 and b.tr_month=?2 and YEAR(b.ticket_raised_time)=?3  ")

	int getNoOfCloedBreakDownByMachineNameAndMonth(String machine, String string, int year);
	@Query("select DISTINCT(b.machine.eqid)  From Breakdown b where b.machine.machine_name=?1 and b.tr_month=?2 and YEAR(b.ticket_raised_time)=?3 ")
	List<String> getDistincrMachineInBreakDownByMachineNameAndMonth(String machine, String string, int year);


	@Query("From Breakdown b where b.status=1  and b.machine.machine_id=?1")
	List<Breakdown> getOpenBreakDownByMachine(int machine_id);
	
	@Query("From Breakdown b where b.status=2  and b.machine.machine_id=?1")
	List<Breakdown> getTraikBreakDownByMachine(int machine_id);
	
	
	@Query("From Breakdown b where b.status=3 or b.status=4 ")
	List<Breakdown> geClosedBreakDownByUser(int userId);
	
	@Query("From Breakdown b where b.machine.eqid=?1")
	List<Breakdown> getBreakdownByEquipMenID(String equipmentId);
	
	// @Query("SELECT b FROM Breakdown b LEFT JOIN FETCH b.breakdownSpares bs LEFT JOIN FETCH bs.spare WHERE b.machine.eqid = :eqid")
	  //  List<Breakdown> getBreakdownsWithSpares(@Param("eqid") String eqid);
	 
	 
	
	 @Query("SELECT COUNT(b) FROM Breakdown b where b.status=?2 and b.machine.machine_id in ?1")	 
	 int getBreakDownCountByMachineIDsAndStatus(List<Integer> machieIds,int status);
	 
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = ?2 AND b.machine.machine_name = ?1")
	 int getBreakdownCountByMachineNameAndStatus(String machineName, int status);
	 //////////////////////////////////////////////////////////////////////////
	 
	// ✅ 1️⃣ Pagination only
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 1 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> findOpenBreakdownsByDateRangePaginated(@Param("fromDate") Date fromDate,
	                                                        @Param("toDate") Date toDate,
	                                                        Pageable pageable);


	 // ✅ 2️⃣ Pagination + Searching
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 1 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
	        "AND (LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.addedBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	        "ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> searchOpenBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                                 @Param("toDate") Date toDate,
	                                                 @Param("keyword") String keyword,
	                                                 Pageable pageable);


	 // ✅ 3️⃣ Count
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 1 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 long countOpenBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                     @Param("toDate") Date toDate);

	 
	 @Query("From Breakdown b where b.status = 1 ")
	 List<Breakdown> getOpenBreakdowns();
	 
	 @Query("FROM Breakdown b WHERE b.status = 1 AND Date(b.ticket_raised_time) >= :fromDate AND Date(b.ticket_raised_time) <= :toDate")
	 List<Breakdown> getOpenBreakdownsByDateRange(
	     @Param("fromDate") Date fromDate, 
	     @Param("toDate") Date toDate);


//////////////////////////////////////////////////////////////////////////////////////
	 @Query("From Breakdown b where b.status = 2 ")
	 List<Breakdown> getTrialBreakdowns();
	 
	 @Query("FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 List<Breakdown> getTrialBreakdownsByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	// ✅ 1️⃣ Pagination only
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> findTrialBreakdownsByDateRangePaginated(@Param("fromDate") Date fromDate,
	                                                         @Param("toDate") Date toDate,
	                                                         Pageable pageable);


	 // ✅ 2️⃣ Pagination + Searching
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
	        "AND (LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.updateBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	        "ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> searchTrialBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                                  @Param("toDate") Date toDate,
	                                                  @Param("keyword") String keyword,
	                                                  Pageable pageable);


	 // ✅ 3️⃣ Count
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 long countTrialBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                      @Param("toDate") Date toDate);

	 ///////////////////////////////////////////////////////////////////////////////////////////

	 @Query("From Breakdown b where b.status = 3 ")
	 List<Breakdown> getClosedBreakdowns();
	 
	 @Query("From Breakdown b where b.status = 3 and b.breakdown_id = :breakdownId")
	 Optional<Breakdown> getClosedBreakdownById(@Param("breakdownId") int breakdownId);

	
	 @Query("FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 List<Breakdown> getClosedBreakdownsByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	// ✅ 1️⃣ Pagination only
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> findClosedBreakdownsByDateRangePaginated(@Param("fromDate") Date fromDate,
	                                                          @Param("toDate") Date toDate,
	                                                          Pageable pageable);


	 // ✅ 2️⃣ Pagination + Searching
	 @Query("SELECT b FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
	        "AND (LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.trialBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	        "ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> searchClosedBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                                   @Param("toDate") Date toDate,
	                                                   @Param("keyword") String keyword,
	                                                   Pageable pageable);


	 // ✅ 3️⃣ Count
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 long countClosedBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                       @Param("toDate") Date toDate);
//////////////////////////////////////////////////////////////////////

	 @Query("From Breakdown b where (b.status = 1 or b.status = 2 or b.status = 3) ")
	    List<Breakdown> getAllBreakdowns();
	 ///////////////////////////
	 @Query("From Breakdown b where (b.status = 1 or b.status = 2 or b.status = 3) AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	    List<Breakdown> getAllBreakdownsByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	 
	// ✅ 1️⃣ Pagination only
	 @Query("SELECT b FROM Breakdown b WHERE (b.status = 1 OR b.status = 2 OR b.status = 3) " +
	        "AND b.ticket_raised_time BETWEEN :fromDate AND :toDate ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> findAllBreakdownsByDateRangePaginated(@Param("fromDate") Date fromDate,
	                                                       @Param("toDate") Date toDate,
	                                                       Pageable pageable);


	 // ✅ 2️⃣ Pagination + Searching
	 @Query("SELECT b FROM Breakdown b WHERE (b.status = 1 OR b.status = 2 OR b.status = 3) " +
	        "AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
	        "AND (LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.addedBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.updateBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.trialBy.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	        "OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	        "ORDER BY b.ticket_raised_time DESC")
	 Page<Breakdown> searchAllBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                                @Param("toDate") Date toDate,
	                                                @Param("keyword") String keyword,
	                                                Pageable pageable);


	 // ✅ 3️⃣ Count
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE (b.status = 1 OR b.status = 2 OR b.status = 3) " +
	        "AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
	 long countAllBreakdownsByDateRange(@Param("fromDate") Date fromDate,
	                                    @Param("toDate") Date toDate);
///////////////////////////////////////////////////////////////
	 
	 
	 @Query("From Breakdown b where b.addedBy.id =?1")
	List<Breakdown> listByUserId(int id);
	 
	 @Query("SELECT COUNT(b) From Breakdown b where b.status = 2 ")
	int getCountOfTrailBrekdown();
	 @Query("SELECT COUNT(b) From Breakdown b where b.status = 3")
	int getCountOfClosedBrekdown();
	 
	 @Query("SELECT b.status, COUNT(b) " +
		       "FROM Breakdown b " +
		       "GROUP BY b.status")
		List<Object[]> getStatusBreakdownCounts();

		@Query("SELECT b.status, COUNT(b) " +
			       "FROM Breakdown b " +
			       "WHERE b.machine.machine_id = :machineId " +
			       "GROUP BY b.status")
			List<Object[]> getStatusCountsByMachineId(@Param("machineId") int machineId);

		
		
	 
	 @Query("From Breakdown b where b.status=3  and b.machine.machine_id=?1")
	 List<Breakdown> getClosedBreakDownByMachine(int machine_id);
	 
	 @Query("SELECT COUNT(b) From Breakdown b where  Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	int getTotalCountByDates(Date startOfWeek, Date endOfWeek);

	 @Query("SELECT COUNT(b) From Breakdown b where b.status=2  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	int getCountOfTrailBrekdownByDates(Date startOfWeek, Date endOfWeek);

	 
	 @Query("SELECT COUNT(b) From Breakdown b where b.status=3  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	 int getCountOfClosedBrekdownByDates(Date startOfWeek, Date endOfWeek);

	 @Query("SELECT COUNT(b) From Breakdown b where b.status=1  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	 int getCountOpenBreakdownByDates(Date startOfWeek, Date endOfWeek);
	 
	 @Query("From Breakdown b where  Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	List<Breakdown> getBreakdownByDates(Date startDate, Date endDate);

	@Query("From Breakdown b where b.status=1  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	List<Breakdown> getOpenBreakdownByDates(Date startDate, Date endDate);
	
	
	 @Query("From Breakdown b where b.status=3  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	List<Breakdown> getClosedBreakdownByDates(Date startDate, Date endDate);
	 
	 @Query("From Breakdown b where b.status=2  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2")
	 List<Breakdown> getTrailBreakdownByDates(Date startDate, Date endDate);
	 
	 
	 
		@Query("From Breakdown b where b.status=1  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3")
	List<Breakdown> getOpenBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);
	
	
	
	 @Query("From Breakdown b where b.status=2  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3")
	List<Breakdown> getTrailBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);
	 
	 
	 @Query("From Breakdown b where b.status=3  and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3")
	List<Breakdown> getClosedBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);
	 
	 
	 @Query("FROM Breakdown b WHERE b.updateBy.id = :userId")
	 List<Breakdown> findBreakdownsByUserId(@Param("userId") int userId);

	 
	 
	 @Query("From Breakdown b where  Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3")
	List<Breakdown> getBreakdownByDatesAndMachineName(Date startDate, Date endDate, String machineName);
	 @Query("From Breakdown b where b.status=2  and b.addedBy.id=?1")
	List<Breakdown> getTrailBreakdownByUser(int userId);
	 
	 //////////////////////////////////////////////////////////////////////
	 @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time BETWEEN :startDate AND :endDate")
	 List<Breakdown> findByTicketRaisedTimeBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	// Date range filter
	

	 // For end date only
	 @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time <= :endDate")
	 List<Breakdown> findByTicketRaisedTimeBefore(@Param("endDate") Date endDate);

//	 @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time BETWEEN :startDate AND :endDate")
//	 Page<Breakdown> findByTicketRaisedTimeBetween(@Param("startDate") Date startDate,
//	                                               @Param("endDate") Date endDate,
//	                                               Pageable pageable);
//
//	 @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time <= :endDate")
//	 Page<Breakdown> findByTicketRaisedTimeBefore(@Param("endDate") Date endDate, Pageable pageable);

	 @Query("""
			    SELECT b FROM Breakdown b
			    WHERE (:startDate IS NULL OR b.ticket_raised_time >= :startDate)
			      AND (:endDate IS NULL OR b.ticket_raised_time <= :endDate)
			      AND (
			            :keyword = '' 
			            OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			            OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			          )
			    ORDER BY b.ticket_raised_time DESC
			""")
			Page<Breakdown> searchBreakdowns(
			        @Param("keyword") String keyword,
			        @Param("startDate") Date startDate,
			        @Param("endDate") Date endDate,
			        Pageable pageable);


	 // ✅ Between dates (sorted by ticket_raised_time DESC)
	    @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time BETWEEN :startDate AND :endDate ORDER BY b.ticket_raised_time DESC")
	    Page<Breakdown> findByTicketRaisedTimeBetween(
	            @Param("startDate") Date startDate,
	            @Param("endDate") Date endDate,
	            Pageable pageable);

	    // ✅ Before end date (sorted DESC)
	    @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time <= :endDate ORDER BY b.ticket_raised_time DESC")
	    Page<Breakdown> findByTicketRaisedTimeBefore(
	            @Param("endDate") Date endDate,
	            Pageable pageable);

	    // ✅ For start date only (open-ended range, sorted DESC)
	    @Query("SELECT b FROM Breakdown b WHERE b.ticket_raised_time >= :startDate ORDER BY b.ticket_raised_time DESC")
	    Page<Breakdown> findByTicketRaisedTimeAfter(
	            @Param("startDate") Date startDate,
	            Pageable pageable);
	 
	 
	 
	 @Query("""
			    SELECT COUNT(b) FROM Breakdown b
			    WHERE (:startDate IS NULL OR b.ticket_raised_time >= :startDate)
			      AND (:endDate IS NULL OR b.ticket_raised_time <= :endDate)
			      AND (:keyword = '' OR LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			           OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')))
			""")
			Long countFilteredBreakdowns(@Param("keyword") String keyword,
			                             @Param("startDate") Date startDate,
			                             @Param("endDate") Date endDate);

	 
	 ///////////////////////////////////////////////////////////////////////////////////////
	 
	 
	 @Query("FROM Breakdown b WHERE b.machine.machine_id = ?1 ORDER BY b.ticket_raised_time ASC")
	    List<Breakdown> getBreakdownsByMachineOrdered(int machine_id);
	 
	 @Query("FROM Breakdown b " +
		       "WHERE b.machine.machine_id = :machineId " +
		       "AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
		       "ORDER BY b.ticket_raised_time ASC")
		List<Breakdown> getBreakdownsByMachineOrdered(
		        @Param("machineId") int machineId,
		        @Param("fromDate") Date fromDate,
		        @Param("toDate") Date toDate);

	 
	 @Query("FROM Breakdown b WHERE b.machine.machine_id = ?1")
	 List<Breakdown> findByMachineId(int machine_id);
	 
	 
	 @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.machine.machine_id = :machineId AND b.status = :status")
	    long countByMachineIdAndStatus(@Param("machineId") int machineId, @Param("status") int status);
	 
	  @Query("SELECT b FROM Breakdown b WHERE b.machine.machine_id = :machineId AND b.status = :status")
	    List<Breakdown> findByMachineIdAndStatus(@Param("machineId") int machineId, @Param("status") int status);
	  
	  //machine
	  
	  
	  @Query("SELECT COUNT(b) FROM Breakdown b " +
		       "WHERE b.machine.machine_id = :machineId " +
		       "AND b.status = :status " +
		       "AND (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate) " +
		       "AND (:toDate IS NULL OR b.ticket_raised_time <= :toDate)")
		long countByMachineIdAndStatusWithDate(@Param("machineId") int machineId,
		                                       @Param("status") int status,
		                                       @Param("fromDate") Date fromDate,
		                                       @Param("toDate") Date toDate);


		@Query("SELECT b FROM Breakdown b " +
		       "WHERE b.machine.machine_id = :machineId " +
		       "AND b.status = :status " +
		       "AND (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate) " +
		       "AND (:toDate IS NULL OR b.ticket_raised_time <= :toDate)")
		List<Breakdown> findByMachineIdAndStatusWithDate(@Param("machineId") int machineId,
		                                                 @Param("status") int status,
		                                                 @Param("fromDate") Date fromDate,
		                                                 @Param("toDate") Date toDate);

	  
	  
	  @Query("SELECT b FROM Breakdown b WHERE b.machine.machine_id = :machineId " +
		       "AND b.root_cause = :rootCause " +
		       "AND FUNCTION('DATE', b.ticket_raised_time) = FUNCTION('DATE', :ticketRaisedTime)")
		List<Breakdown> findExactDuplicates(
		        @Param("machineId") int machineId,
		        @Param("rootCause") String rootCause,
		        @Param("ticketRaisedTime") Date ticketRaisedTime
		);

	
	  @Query("SELECT b.status, COUNT(b) " +
		       "FROM Breakdown b " +
		       "WHERE b.machine.eqid = :eqid " +
		       "GROUP BY b.status")
		List<Object[]> getStatusCountsByEqId(@Param("eqid") String eqid);

		@Query("FROM Breakdown b WHERE b.status = 1 AND b.machine.eqid = :eqid")
		List<Breakdown> getOpenBreakDownByEqId(@Param("eqid") String eqid);

		@Query("FROM Breakdown b WHERE b.status = 2 AND b.machine.eqid = :eqid")
		List<Breakdown> getTrialBreakDownByEqId(@Param("eqid") String eqid);

		@Query("FROM Breakdown b WHERE (b.status = 3 OR b.status = 4) AND b.machine.eqid = :eqid")
		List<Breakdown> getClosedBreakDownByEqId(@Param("eqid") String eqid);

/////labname

		
		@Query("SELECT b.status, COUNT(b) " +
			       "FROM Breakdown b " +
			       "WHERE b.lab.labName = :labName " +
			       "GROUP BY b.status")
			List<Object[]> getStatusBreakdownCountsByLabName(@Param("labName") String labName);

			@Query("From Breakdown b where b.status=1 and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3 and b.lab.labName=?4")
			List<Breakdown> getOpenBreakdownByDatesAndMachineAndLab(Date startDate, Date endDate, String machineName, String labName);

			@Query("From Breakdown b where b.status=2 and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3 and b.lab.labName=?4")
			List<Breakdown> getTrailBreakdownByDatesAndMachineAndLab(Date startDate, Date endDate, String machineName, String labName);

			@Query("From Breakdown b where b.status=3 and Date(b.ticket_raised_time)>=?1 and Date(b.ticket_raised_time)<=?2 and b.machine.machine_name=?3 and b.lab.labName=?4")
			List<Breakdown> getClosedBreakdownByDatesAndMachineAndLab(Date startDate, Date endDate, String machineName, String labName);

		
			@Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = ?2 AND b.machine.machine_id IN ?1 AND b.lab.labName = ?3")
			int getBreakDownCountByMachineIDsAndStatusAndLab(List<Integer> machineIds, int status, String labName);
			
			@Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = ?2 AND b.machine.machine_name = ?1 AND b.lab.labName = ?3")
			int getBreakdownCountByMachineNameAndStatusAndLab(String machineName, int status, String labName);

			@Query("SELECT COUNT(b) FROM Breakdown b WHERE Date(b.ticket_raised_time) >= ?1 AND Date(b.ticket_raised_time) <= ?2 AND b.lab.labName = ?3")
			int getTotalCountByDatesAndLab(Date startOfWeek, Date endOfWeek, String labName);

			@Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 2 AND Date(b.ticket_raised_time) >= ?1 AND Date(b.ticket_raised_time) <= ?2 AND b.lab.labName = ?3")
			int getCountOfTrailBrekdownByDatesAndLab(Date startOfWeek, Date endOfWeek, String labName);

			@Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 3 AND Date(b.ticket_raised_time) >= ?1 AND Date(b.ticket_raised_time) <= ?2 AND b.lab.labName = ?3")
			int getCountOfClosedBrekdownByDatesAndLab(Date startOfWeek, Date endOfWeek, String labName);

			@Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 1 AND Date(b.ticket_raised_time) >= ?1 AND Date(b.ticket_raised_time) <= ?2 AND b.lab.labName = ?3")
			int getCountOpenBreakdownByDatesAndLab(Date startOfWeek, Date endOfWeek, String labName);
			
			// Open breakdowns filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 1 AND b.lab.labName = :labName")
			List<Breakdown> getOpenBreakdownsByLabName(@Param("labName") String labName);

			// Open breakdowns within a date range and filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 1 AND Date(b.ticket_raised_time) >= :fromDate AND Date(b.ticket_raised_time) <= :toDate AND b.lab.labName = :labName")
			List<Breakdown> getOpenBreakdownsByDateRangeAndLab(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("labName") String labName);

			// Trial breakdowns filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 2 AND b.lab.labName = :labName")
			List<Breakdown> getTrialBreakdownsByLabName(@Param("labName") String labName);

			// Trial breakdowns within a date range and filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate AND b.lab.labName = :labName")
			List<Breakdown> getTrialBreakdownsByDateRangeAndLab(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("labName") String labName);

			// Closed breakdowns filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 3 AND b.lab.labName = :labName")
			List<Breakdown> getClosedBreakdownsByLabName(@Param("labName") String labName);

			// Closed breakdowns within a date range and filtered by labName
			@Query("FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :fromDate AND :toDate AND b.lab.labName = :labName")
			List<Breakdown> getClosedBreakdownsByDateRangeAndLab(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("labName") String labName);

			// Get all breakdowns filtered by labName
			@Query("FROM Breakdown b WHERE (b.status = 1 OR b.status = 2 OR b.status = 3) AND b.lab.labName = :labName")
			List<Breakdown> getAllBreakdownsByLabName(@Param("labName") String labName);

			// Get all breakdowns within a date range and filtered by labName
			@Query("FROM Breakdown b WHERE (b.status = 1 OR b.status = 2 OR b.status = 3) AND b.ticket_raised_time BETWEEN :fromDate AND :toDate AND b.lab.labName = :labName")
			List<Breakdown> getAllBreakdownsByDateRangeAndLab(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("labName") String labName);

			@Query("From Breakdown b WHERE b.status = 1 AND Date(b.ticket_raised_time) >= ?1 AND Date(b.ticket_raised_time) <= ?2 AND b.lab.labName = ?3")
			List<Breakdown> getOpenBreakdownByDatesAndLab(Date startDate, Date endDate, String labName);

			@Query("FROM Breakdown b WHERE b.status = 2 AND Date(b.ticket_raised_time) >= :startDate AND Date(b.ticket_raised_time) <= :endDate AND b.lab.labName = :labName")
			List<Breakdown> getTrailBreakdownByDatesAndLab(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("labName") String labName);

			@Query("FROM Breakdown b WHERE b.status = 3 AND Date(b.ticket_raised_time) >= :startDate AND Date(b.ticket_raised_time) <= :endDate AND b.lab.labName = :labName")
			List<Breakdown> getClosedBreakdownByDatesAndLab(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("labName") String labName);

			
			
			
			////////////////////////////////////////////////////////////////////////
			@Query("SELECT b FROM Breakdown b WHERE b.updateBy.id = :userId AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
			List<Breakdown> findBreakdownsByUserIdAndDateRange(@Param("userId") int userId, 
			                                                   @Param("fromDate") Date fromDate, 
			                                                   @Param("toDate") Date toDate);

			  // ✅ 1️⃣ Pagination + Date range + User
		    @Query("SELECT b FROM Breakdown b WHERE b.updateBy.id = :userId AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
		    Page<Breakdown> findBreakdownsByUserIdAndDateRange(
		            @Param("userId") int userId,
		            @Param("fromDate") Date fromDate,
		            @Param("toDate") Date toDate,
		            Pageable pageable);

		    // ✅ 2️⃣ Search + Pagination
		    @Query("SELECT b FROM Breakdown b " +
		           "WHERE b.updateBy.id = :userId " +
		           "AND b.ticket_raised_time BETWEEN :fromDate AND :toDate " +
		           "AND (" +
		           "LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
		           ")")
		    Page<Breakdown> searchBreakdownsByUserIdDateAndKeyword(
		            @Param("userId") int userId,
		            @Param("fromDate") Date fromDate,
		            @Param("toDate") Date toDate,
		            @Param("keyword") String keyword,
		            Pageable pageable);

		    // ✅ 3️⃣ Count API
		    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.updateBy.id = :userId AND b.ticket_raised_time BETWEEN :fromDate AND :toDate")
		    long countBreakdownsByUserIdAndDateRange(
		            @Param("userId") int userId,
		            @Param("fromDate") Date fromDate,
		            @Param("toDate") Date toDate);
			
			
			
			////////////////////////////////

			// ==========================
			// ✅ All Breakdowns
			// ==========================
			@Query(
			    value = "SELECT new com.kfMaintenancce.dto.BreakdownChatbotDTO(" +
			            "b.breakdown_id, " +
			            "CONCAT(COALESCE(ab.firstName,''), ' ', COALESCE(ab.lastName,'')), " +
			            "CONCAT(COALESCE(ub.firstName,''), ' ', COALESCE(ub.lastName,'')), " +
			            "CONCAT(COALESCE(tb.firstName,''), ' ', COALESCE(tb.lastName,'')), " +
			            "COALESCE(d.departmentName,''), " +
			            "COALESCE(m.machine_name,''), " +
			            "COALESCE(m.eqid,''), " +
			            "b.spares_used, " +
			            "b.status, " +
			            "b.bd_slip, " +
			            "b.observation, " +
			            "b.action_taken, " +
			            "b.root_cause, " +
			            "b.ticket_raised_time, " +
			            "b.ticket_closed_time, " +
			            "b.ticket_trial_time, " +
			            "b.actualWorkingStartTime, " +
			            "COALESCE(l.labName,''), " +
			            "COALESCE(m.location,'')) " +
			            "FROM Breakdown b " +
			            "LEFT JOIN b.addedBy ab " +
			            "LEFT JOIN b.updateBy ub " +
			            "LEFT JOIN b.trialBy tb " +
			            "LEFT JOIN b.department d " +
			            "LEFT JOIN b.machine m " +
			            "LEFT JOIN b.lab l " +
			            "ORDER BY b.breakdown_id DESC",
			    countQuery = "SELECT COUNT(b) FROM Breakdown b"
			)
			Page<BreakdownChatbotDTO> findAllBreakdowns(Pageable pageable);


			// ==========================
			// ✅ Open Breakdowns (status = 1)
			// ==========================
			@Query(
			    value = "SELECT new com.kfMaintenancce.dto.BreakdownChatbotDTO(" +
			            "b.breakdown_id, " +
			            "CONCAT(COALESCE(ab.firstName,''), ' ', COALESCE(ab.lastName,'')), " +
			            "CONCAT(COALESCE(ub.firstName,''), ' ', COALESCE(ub.lastName,'')), " +
			            "CONCAT(COALESCE(tb.firstName,''), ' ', COALESCE(tb.lastName,'')), " +
			            "COALESCE(d.departmentName,''), " +
			            "COALESCE(m.machine_name,''), " +
			            "COALESCE(m.eqid,''), " +
			            "b.spares_used, " +
			            "b.status, " +
			            "b.bd_slip, " +
			            "b.observation, " +
			            "b.action_taken, " +
			            "b.root_cause, " +
			            "b.ticket_raised_time, " +
			            "b.ticket_closed_time, " +
			            "b.ticket_trial_time, " +
			            "b.actualWorkingStartTime, " +
			            "COALESCE(l.labName,''), " +
			            "COALESCE(m.location,'')) " +
			            "FROM Breakdown b " +
			            "LEFT JOIN b.addedBy ab " +
			            "LEFT JOIN b.updateBy ub " +
			            "LEFT JOIN b.trialBy tb " +
			            "LEFT JOIN b.department d " +
			            "LEFT JOIN b.machine m " +
			            "LEFT JOIN b.lab l " +
			            "WHERE b.status = 1 " +
			            "ORDER BY b.breakdown_id DESC",
			    countQuery = "SELECT COUNT(b) FROM Breakdown b WHERE b.status = 1"
			)
			Page<BreakdownChatbotDTO> findOpenBreakdowns(Pageable pageable);


			// ==========================
			// ✅ Trial Breakdowns (status = 2)
			// ==========================
			@Query(
			    value = "SELECT new com.kfMaintenancce.dto.BreakdownChatbotDTO(" +
			            "b.breakdown_id, " +
			            "CONCAT(COALESCE(ab.firstName,''), ' ', COALESCE(ab.lastName,'')), " +
			            "CONCAT(COALESCE(ub.firstName,''), ' ', COALESCE(ub.lastName,'')), " +
			            "CONCAT(COALESCE(tb.firstName,''), ' ', COALESCE(tb.lastName,'')), " +
			            "COALESCE(d.departmentName,''), " +
			            "COALESCE(m.machine_name,''), " +
			            "COALESCE(m.eqid,''), " +
			            "b.spares_used, " +
			            "b.status, " +
			            "b.bd_slip, " +
			            "b.observation, " +
			            "b.action_taken, " +
			            "b.root_cause, " +
			            "b.ticket_raised_time, " +
			            "b.ticket_closed_time, " +
			            "b.ticket_trial_time, " +
			            "b.actualWorkingStartTime, " +
			            "COALESCE(l.labName,''), " +
			            "COALESCE(m.location,'')) " +
			            "FROM Breakdown b " +
			            "LEFT JOIN b.addedBy ab " +
			            "LEFT JOIN b.updateBy ub " +
			            "LEFT JOIN b.trialBy tb " +
			            "LEFT JOIN b.department d " +
			            "LEFT JOIN b.machine m " +
			            "LEFT JOIN b.lab l " +
			            "WHERE b.status = 2 " +
			            "ORDER BY b.breakdown_id DESC",
			    countQuery = "SELECT COUNT(b) FROM Breakdown b WHERE b.status = 2"
			)
			Page<BreakdownChatbotDTO> findTrialBreakdowns(Pageable pageable);


			// ==========================
			// ✅ Closed Breakdowns (status = 3)
			// ==========================
			@Query(
			    value = "SELECT new com.kfMaintenancce.dto.BreakdownChatbotDTO(" +
			            "b.breakdown_id, " +
			            "CONCAT(COALESCE(ab.firstName,''), ' ', COALESCE(ab.lastName,'')), " +
			            "CONCAT(COALESCE(ub.firstName,''), ' ', COALESCE(ub.lastName,'')), " +
			            "CONCAT(COALESCE(tb.firstName,''), ' ', COALESCE(tb.lastName,'')), " +
			            "COALESCE(d.departmentName,''), " +
			            "COALESCE(m.machine_name,''), " +
			            "COALESCE(m.eqid,''), " +
			            "b.spares_used, " +
			            "b.status, " +
			            "b.bd_slip, " +
			            "b.observation, " +
			            "b.action_taken, " +
			            "b.root_cause, " +
			            "b.ticket_raised_time, " +
			            "b.ticket_closed_time, " +
			            "b.ticket_trial_time, " +
			            "b.actualWorkingStartTime, " +
			            "COALESCE(l.labName,''), " +
			            "COALESCE(m.location,'')) " +
			            "FROM Breakdown b " +
			            "LEFT JOIN b.addedBy ab " +
			            "LEFT JOIN b.updateBy ub " +
			            "LEFT JOIN b.trialBy tb " +
			            "LEFT JOIN b.department d " +
			            "LEFT JOIN b.machine m " +
			            "LEFT JOIN b.lab l " +
			            "WHERE b.status = 3 " +
			            "ORDER BY b.breakdown_id DESC",
			    countQuery = "SELECT COUNT(b) FROM Breakdown b WHERE b.status = 3"
			)
			Page<BreakdownChatbotDTO> findClosedBreakdowns(Pageable pageable);




			    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status IN (1, 2, 3)")
			    long getAllBreakdownCount();

			    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 1")
			    long getOpenBreakdownCount();

			    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 2")
			    long getTrialBreakdownCount();

			    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = 3")
			    long getClosedBreakdownCount();
			    
			    
			    
			    
			    
			    
			    
			 // 🗓 Weekly records (within date range)
			    @Query("SELECT b FROM Breakdown b WHERE b.status = 1 AND DATE(b.ticket_raised_time) >= ?1 AND DATE(b.ticket_raised_time) <= ?2")
			    List<Breakdown> getWeeklyOpenBreakdowns(Date startOfWeek, Date endOfWeek);

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 2 AND DATE(b.ticket_raised_time) >= ?1 AND DATE(b.ticket_raised_time) <= ?2")
			    List<Breakdown> getWeeklyTrialBreakdowns(Date startOfWeek, Date endOfWeek);

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 3 AND DATE(b.ticket_raised_time) >= ?1 AND DATE(b.ticket_raised_time) <= ?2")
			    List<Breakdown> getWeeklyClosedBreakdowns(Date startOfWeek, Date endOfWeek);


			    // 📊 Overall records (no date filter)
			    @Query("SELECT b FROM Breakdown b WHERE b.status = 1")
			    List<Breakdown> getTotalOpenBreakdowns();

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 2")
			    List<Breakdown> getTotalTrialBreakdowns();

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 3")
			    List<Breakdown> getTotalClosedBreakdowns();

			    @Query("SELECT COUNT(b) FROM Breakdown b")
			    int getTotalBreakdownCount();

			    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.status = ?1")
			    int countByStatus(int status);
 
			    ///////
			
			    
			    
			    
			    @Query("SELECT b FROM Breakdown b WHERE b.status = 1 AND b.ticket_raised_time BETWEEN :start AND :end")
			    Page<Breakdown> findWeeklyOpenBreakdowns(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 2 AND b.ticket_raised_time BETWEEN :start AND :end")
			    Page<Breakdown> findWeeklyTrialBreakdowns(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

			    @Query("SELECT b FROM Breakdown b WHERE b.status = 3 AND b.ticket_raised_time BETWEEN :start AND :end")
			    Page<Breakdown> findWeeklyClosedBreakdowns(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

			    Page<Breakdown> findByStatus(int status, Pageable pageable);
			    
			    
			    
			    
			    @Query("SELECT b FROM Breakdown b WHERE b.status = :status " +
			    	       "AND b.ticket_raised_time BETWEEN :start AND :end " +
			    	       "AND (LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
			    	       "ORDER BY b.ticket_raised_time DESC")
			    	Page<Breakdown> searchWeeklyBreakdowns(@Param("status") int status,
			    	                                       @Param("keyword") String keyword,
			    	                                       @Param("start") Date start,
			    	                                       @Param("end") Date end,
			    	                                       Pageable pageable);


			  

			    @Query("SELECT b FROM Breakdown b WHERE b.status = :status AND " +
			    	       "(LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
			    	       "ORDER BY b.ticket_raised_time DESC")
			    	Page<Breakdown> searchTotalBreakdowns(@Param("status") int status,
			    	                                      @Param("keyword") String keyword,
			    	                                      Pageable pageable);

			    @Query("SELECT b FROM Breakdown b WHERE " +
			    	       "LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			    	       "LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			    	       "ORDER BY b.ticket_raised_time DESC")
			    	Page<Breakdown> searchAllBreakdowns(@Param("keyword") String keyword, Pageable pageable);

			    
			    
			    
			    @Query("""
			    	    SELECT b FROM Breakdown b
			    	    WHERE b.status = :status
			    	      AND (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate)
			    	      AND (:toDate IS NULL OR b.ticket_raised_time <= :toDate)
			    	      AND (
			    	          LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	          OR LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	          OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	      )
			    	    ORDER BY b.ticket_raised_time DESC
			    	""")
			    	Page<Breakdown> searchTotalBreakdownses(
			    	        @Param("status") int status,
			    	        @Param("fromDate") Date fromDate,
			    	        @Param("toDate") Date toDate,
			    	        @Param("keyword") String keyword,
			    	        Pageable pageable);

			    
			    
			    @Query("""
			    	    SELECT b FROM Breakdown b
			    	    WHERE (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate)
			    	      AND (:toDate IS NULL OR b.ticket_raised_time <= :toDate)
			    	      AND (
			    	          LOWER(b.machine.machine_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	          OR LOWER(b.bd_slip) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	          OR LOWER(b.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    	      )
			    	    ORDER BY b.ticket_raised_time DESC
			    	""")
			    	Page<Breakdown> searchAllBreakdownses(
			    	        @Param("fromDate") Date fromDate,
			    	        @Param("toDate") Date toDate,
			    	        @Param("keyword") String keyword,
			    	        Pageable pageable);

//mttr new report
			    @Query("""
			    	    SELECT b FROM Breakdown b
			    	    WHERE (:fromDate IS NULL OR b.ticket_raised_time >= :fromDate)
			    	      AND (:toDate IS NULL OR b.ticket_raised_time <= :toDate)
			    	      AND (:machineId IS NULL OR b.machine.machine_id = :machineId)
			    	    ORDER BY b.ticket_raised_time ASC
			    	""")
			    	List<Breakdown> findBreakdownsByFilters(
			    	        @Param("fromDate") Date fromDate,
			    	        @Param("toDate") Date toDate,
			    	        @Param("machineId") Integer machineId
			    	);


}
