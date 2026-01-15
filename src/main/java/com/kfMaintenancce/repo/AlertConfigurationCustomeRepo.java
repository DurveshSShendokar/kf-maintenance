package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AlertConfiguration;

public interface AlertConfigurationCustomeRepo {
	List<AlertConfiguration> getAlertConfigurationByLimit(int pageNo, int perPage);
	int getCountAlertConfigurationBySearch(String searchText);

	List<AlertConfiguration> getAlertConfigurationCountAndSearch(String searchText, int pageNo, int perPage);

}
