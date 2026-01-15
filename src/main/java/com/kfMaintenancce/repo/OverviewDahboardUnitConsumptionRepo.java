package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.OverviewDahboardUnitConsumption;

public interface OverviewDahboardUnitConsumptionRepo  extends JpaRepository<OverviewDahboardUnitConsumption, Integer>,OverviewDahboardUnitConsumptionCustomeRepo{
//	@Query(value = "SELECT * FROM overview_dahboard_unit_consumption ORDER BY date ASC LIMIT ?1", nativeQuery = true)
//	List<OverviewDahboardUnitConsumption> getData(int noOfDays);
	@Query("from OverviewDahboardUnitConsumption where day=?1")
	Optional<OverviewDahboardUnitConsumption> getByDay(int day);
	@Query("select consumption from OverviewDahboardUnitConsumption where Date(date)=?1")

	String getByDate(Date monthdDayDate);


	

}
