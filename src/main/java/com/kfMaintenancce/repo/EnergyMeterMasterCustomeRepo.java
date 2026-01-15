package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;

public interface EnergyMeterMasterCustomeRepo {
	List<EnergyMeterMaster> getEnergyMeterMasterByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterMasterCountByLimitAndSearch(String searchText);

	List<EnergyMeterMaster> getEnergyMeterMasterByLimit(int pageNo, int perPage);

	int getCountOfEnergyMeterMaster();
}
