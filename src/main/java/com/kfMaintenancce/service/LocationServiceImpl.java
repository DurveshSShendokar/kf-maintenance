package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.repo.LocationRepo;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	LocationRepo locationRepo;	

	public void addLocation(Location location) {
		// TODO Auto-generated method stub
		locationRepo.save(location);
	}

	
	public List<Location> getLocationByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return locationRepo.getLocationByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) locationRepo.count();
	}

	
	public List<Location> getLocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return locationRepo.getLocationByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getLocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return locationRepo.getLocationCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	 public void deleteLocation(int location_id) {
		 locationRepo.deleteById(location_id);
	    }
	 
	 
	 public List<Location> getlocations() {
	        return locationRepo.findAll();
	    } 

}
