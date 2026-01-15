package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.MonthlyConsumptionGraph;

public interface MonthlyConsumptionGraphRepo extends JpaRepository<MonthlyConsumptionGraph, Integer>{
	@Query("from MonthlyConsumptionGraph m where m.meterId=?1")
	List<MonthlyConsumptionGraph> findByMeterId(int id);
	@Query("from MonthlyConsumptionGraph m where m.meterId=?1 and m.month=?2 and m.year=?3")
	Optional<MonthlyConsumptionGraph> findByMeterIdMonthAndYear(int id, int month, int currentYear);
	@Query("from MonthlyConsumptionGraph m where m.meterId=?1 and m.year=?2")

	List<MonthlyConsumptionGraph> findByMeterIdAndYear(int id, int currentYear);

}
