package com.kfMaintenancce.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;


public interface MaintenenaceCheckPointRepo  extends JpaRepository<MaintenenaceCheckPoint, Integer>, MaintenanceCheckPointRepoCustom{
//	@Query("from MaintenenaceCheckPoint m where m.maint.maint_id=?1")
//	List<MaintenenaceCheckPoint> findByMaintId(int maint_id);
	
	@Query("SELECT c FROM MaintenenaceCheckPoint c WHERE c.previousBit = 0 AND c.maint.maint_id = :maintId")
	List<MaintenenaceCheckPoint> findByMaintId(@Param("maintId") int maintId);

	@Query("SELECT c FROM MaintenenaceCheckPoint c WHERE c.previousBit = 0 AND c.maint.maint_id IN :maintIds")
	List<MaintenenaceCheckPoint> findByMaintIds(@Param("maintIds") List<Integer> maintIds);

	
	 @Query("SELECT cp FROM MaintenenaceCheckPoint cp " +
	           "JOIN cp.maint m " +
	           "JOIN m.machine machine " +
	           "WHERE machine.eqid = :eqid")
	    List<MaintenenaceCheckPoint> findCheckpointsByMachineEqid(String eqid);
	
	 @Query("SELECT mc FROM MaintenenaceCheckPoint mc WHERE mc.maint = :maint")
	 List<MaintenenaceCheckPoint> findByMaint(@Param("maint") Maint maint);
	 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	 @Query("SELECT c FROM MaintenenaceCheckPoint c " +
		       "WHERE c.previousBit = 0 AND c.maint.maint_id = :maintId " +
		       "ORDER BY " +
		       "CASE WHEN c.checkpoint.serialNumber = 0 THEN 1 ELSE 0 END, " +  // put serialNumber = 0 last
		       "c.checkpoint.serialNumber ASC, " +                              // order by serialNumber for non-zero
		       "c.checkpoint.checklist_id ASC")                                 // then by checklist_id
		List<MaintenenaceCheckPoint> findCheckpointsByMaintId(@Param("maintId") int maintId);
	 
	 
	 
	  @Query("SELECT c FROM MaintenenaceCheckPoint c " +
	           "WHERE c.maint.maint_id = :maintId " +
	           "ORDER BY c.id DESC")
	    Page<MaintenenaceCheckPoint> findByMaintIdWithPagination(@Param("maintId") int maintId, Pageable pageable);

	    @Query("SELECT c FROM MaintenenaceCheckPoint c " +
	           "WHERE c.maint.maint_id = :maintId AND " +
	           "(LOWER(c.checkpoint.checkType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(c.status) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	           "ORDER BY c.id DESC")
	    Page<MaintenenaceCheckPoint> searchCheckpointsByMaintId(
	            @Param("maintId") int maintId,
	            @Param("keyword") String keyword,
	            Pageable pageable);

	    @Query("SELECT COUNT(c) FROM MaintenenaceCheckPoint c WHERE c.maint.maint_id = :maintId")
	    long countByMaintId(@Param("maintId") int maintId);
	 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	 @Query("SELECT c FROM MaintenenaceCheckPoint c WHERE c.previousBit = 1 AND c.maint.maint_id = :maintId ")
	    List<MaintenenaceCheckPoint> findCheckpointsByMaintIds(@Param("maintId") int maintId);
	 
	 
	 
	 @Modifying
	    @Transactional
	    @Query("UPDATE MaintenenaceCheckPoint mcp SET mcp.previousBit = 1 WHERE mcp.maint.maint_id = :maintId")
	    void markPreviousCheckPoints(@Param("maintId") int maintId);

}
