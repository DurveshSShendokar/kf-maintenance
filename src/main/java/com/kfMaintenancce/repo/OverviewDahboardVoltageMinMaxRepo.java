package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.OverviewDahboardUnitConsumption;
import com.kfMaintenancce.model.OverviewDahboardVoltageMinMax;

public interface OverviewDahboardVoltageMinMaxRepo extends JpaRepository<OverviewDahboardVoltageMinMax, Integer>{

//	@Query(value = "SELECT * FROM overview_dahboard_voltage_min_max ORDER BY date DESC LIMIT ?1", nativeQuery = true)
//	List<OverviewDahboardVoltageMinMax> getData(int noOfDays);
	@Query("From OverviewDahboardVoltageMinMax where dayName=?1")
	Optional<OverviewDahboardVoltageMinMax> getByDay(int day);
	@Query("From OverviewDahboardVoltageMinMax where Date(date)=?1")
	Optional<OverviewDahboardVoltageMinMax> getByDate(Date monthdDayDate);





}
