package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer>,LocationRepoCustom{
    Optional<Location> findByLocationName(String locationName);
	  
}