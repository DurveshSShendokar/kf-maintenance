package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.WeeklyConsumptionGraph;

public interface WeeklyConsumptionGraphRepo extends  JpaRepository<WeeklyConsumptionGraph, Integer> {
	@Query("from WeeklyConsumptionGraph w where w.meterId=?1")
	Optional<WeeklyConsumptionGraph> getByMeterId(int meterId);

}
