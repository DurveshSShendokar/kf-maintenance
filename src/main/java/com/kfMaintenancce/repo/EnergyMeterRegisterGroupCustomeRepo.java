package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.EnergyMeterRegisterGroup;

public interface EnergyMeterRegisterGroupCustomeRepo {
	List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimitAndSearch(String searchText, int pageNo,
			int perPage);

	int getEnergyMeterRegisterGroupCountByLimitAndSearch(String searchText);

	List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimit(int pageNo, int perPage);

}
