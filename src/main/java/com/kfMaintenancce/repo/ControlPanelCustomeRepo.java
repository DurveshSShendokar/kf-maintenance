package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.ControlPanel;

public interface ControlPanelCustomeRepo {


	List<ControlPanel> getControlPanelByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getControlPanelCountByLimitAndSearch(String searchText);

	List<ControlPanel> getControlPanelByLimit(int pageNo, int perPage);

	int getCountOfControlPanel();
}
