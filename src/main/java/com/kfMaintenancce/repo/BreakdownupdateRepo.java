package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Breakdownupdate;

public interface BreakdownupdateRepo  extends JpaRepository<Breakdownupdate, Integer>{
	@Query("From Breakdownupdate b where b.breakdown.breakdown_id=?1")
	List<Breakdownupdate> getBreakDownUpdateByBreakdownId(int breakdown_id);
	
	
	@Query("From Breakdownupdate b where b.breakdown.bd_slip=?1")
	List<Breakdownupdate> getBrekdownUpdatesByBreakdown(String bd_slip);

	

}
