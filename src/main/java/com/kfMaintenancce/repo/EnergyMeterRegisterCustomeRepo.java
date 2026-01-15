package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.EnergyMeterRegister;

public interface EnergyMeterRegisterCustomeRepo {
	List<EnergyMeterRegister> getEnergyMeterRegisterByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterRegisterCountByLimitAndSearch(String searchText);

	List<EnergyMeterRegister> getEnergyMeterRegisterByLimit(int pageNo, int pageNo2);

	int getCountOfEnergyMeterRegister();
}
