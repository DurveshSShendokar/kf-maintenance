package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.EnergyMeterRegisterGroup;

public interface EnergyMeterRegisterGroupRepo extends JpaRepository<EnergyMeterRegisterGroup, Integer>,EnergyMeterRegisterGroupCustomeRepo {

	
}
