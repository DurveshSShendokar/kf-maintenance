package com.kfMaintenancce.repo;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.MeterWiseDashboardCardData;

public interface MeterWiseDashboardCardDataRepo extends JpaRepository<MeterWiseDashboardCardData, Integer>{
	@Query("from MeterWiseDashboardCardData where energyMeterMaster.id=?1")
	Optional<MeterWiseDashboardCardData> findByMeterId(int meterId);

}
