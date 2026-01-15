package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Location;

public interface LocationRepoCustom {
	
	List<Location> getLocationByLimit(int pageNo, int perPage);
	List<Location> getLocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getLocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
