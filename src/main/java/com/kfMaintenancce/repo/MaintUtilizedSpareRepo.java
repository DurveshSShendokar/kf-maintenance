package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.kfMaintenancce.model.MaintUtilizedSpare;

public interface MaintUtilizedSpareRepo extends JpaRepository<MaintUtilizedSpare, Integer> {
	@Query("from MaintUtilizedSpare where  breakdown.breakdown_id=?1")
	List<MaintUtilizedSpare> getUtilizedSpareByBreakdownid(int breakdownId);

}
