package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.UnitLocation;

public interface UnitLocationRepo extends JpaRepository<UnitLocation, Integer>{
	@Query("from UnitLocation u where u.plantName=?1 and u.locationName=?2")
	Optional<UnitLocation> getUnitLocationByPlantNameAndLocation(String plantName, String locationName);
	@Query("from UnitLocation u where u.locationName=?1")
	Optional<UnitLocation> getUnitLocationByLocationName(String locationName);
	
}
