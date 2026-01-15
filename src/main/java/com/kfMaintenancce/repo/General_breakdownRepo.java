package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.General_breakdown;


import java.sql.Timestamp;
@Repository
public interface General_breakdownRepo extends JpaRepository<General_breakdown, Integer>, General_breakdownRepoCustom  {
	

	    
	@Query("select count(*) from General_breakdown b where  substr(b.breakdownNo,1,4)=?1")
	int getBreakdownCountByYearMonth(String mnyr);
	
	@Query("select MAX(b.breakdownNo) from  General_breakdown b where  substr(b.breakdownNo,1,4)=?1")
	String getMaxBreakdownNoNoByYearMonth(String mnyr);
	
	List<General_breakdown> findByStatus(int status);
	
	@Query("FROM General_breakdown b WHERE b.status = 0 AND b.genbreak_id = :id")
	Optional<General_breakdown> getClosedGeneralBreakdownById(@Param("id") int id);

	    
	 List<General_breakdown> findAllByOpenDateBetween(Timestamp startDate, Timestamp endDate);
	 
	 
	 
	 
	  // ✅ 1. Date range with pagination
	    Page<General_breakdown> findAllByOpenDateBetween(Timestamp startDate, Timestamp endDate, Pageable pageable);

	    // ✅ 2. Date range + search (search in machine name, description, or breakdown reason, etc.)
	    @Query("SELECT g FROM General_breakdown g " +
	           "WHERE g.openDate BETWEEN :startDate AND :endDate " +
	           "AND (" +
	           "LOWER(g.breakdownNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(g.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(g.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	           ")")
	    Page<General_breakdown> searchByDateRangeAndKeyword(
	            @Param("startDate") Timestamp startDate,
	            @Param("endDate") Timestamp endDate,
	            @Param("keyword") String keyword,
	            Pageable pageable);

	    // ✅ 3. Count only (date range)
	    long countByOpenDateBetween(Timestamp startDate, Timestamp endDate);

}
