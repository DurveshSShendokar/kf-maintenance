package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.BreakdownImage;

@Repository
public interface BreakdownImageRepo extends JpaRepository<BreakdownImage, Integer> {

	
	@Query("SELECT mi FROM BreakdownImage mi WHERE mi.breakdown.breakdown_id = :breakdownId")
	List<BreakdownImage> findBybreakdownId(@Param("breakdownId") int breakdownId);
	
	@Query("SELECT bi FROM BreakdownImage bi WHERE bi.breakdown.breakdown_id = :breakdownId")
	List<BreakdownImage> getImagesByBreakdownId(@Param("breakdownId") int breakdownId);


}
