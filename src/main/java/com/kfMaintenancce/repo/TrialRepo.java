package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Trial;


public interface TrialRepo extends JpaRepository<Trial, Integer>, TrialRepoCustom {
	@Query("from Trial t where t.breakdown.breakdown_id=?1")
	List<Trial> getListByBreakdownUpdate(int breakdown_id);

}
