package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Spares;


public interface SparesRepo  extends JpaRepository<Spares, Integer>{
	@Query("From Spares s where s.breakdownupdate.breakdown_update_id=?1")
	List<Spares> getSparesByBreakDownUpdate(int breakdownUpdateId);
	@Query("From Spares s where s.breakdownupdate.breakdown.breakdown_id=?1")
	List<Spares> getSpareByBreakdownId(int breakdown_id);


}
