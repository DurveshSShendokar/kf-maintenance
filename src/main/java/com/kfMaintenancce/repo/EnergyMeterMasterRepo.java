package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;

public interface EnergyMeterMasterRepo extends JpaRepository<EnergyMeterMaster, Integer>,EnergyMeterMasterCustomeRepo {
	@Query("From EnergyMeterMaster e where e.panel.id=?1")
	List<EnergyMeterMaster> getAllEnergyMeterMastersByPanels(int id);
	@Query("From EnergyMeterMaster e")

	List<EnergyMeterMaster> getAllMeters();



}
