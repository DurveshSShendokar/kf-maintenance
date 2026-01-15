package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.EnergyMeterModule;

public interface EnergyMeterModuleCustomeRepo {
	List<EnergyMeterModule> getEnergyMeterModuleByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterModuleCountByLimitAndSearch(String searchText);

	List<EnergyMeterModule> getEnergyMeterModuleByLimit(int pageNo, int perPage);

	int getCountOfEnergyMeterModule();
}
