package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Location;

public interface LocationService {

	void addLocation(Location location);

	List<Location> getLocationByLimit(int pageNo, int perPage);

	int count();

	List<Location> getLocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getLocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteLocation(int location_id);

	 public List<Location> getlocations();
}
