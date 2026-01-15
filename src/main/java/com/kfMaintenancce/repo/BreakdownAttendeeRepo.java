package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.BreakdownAttendee;


@Repository
public interface BreakdownAttendeeRepo extends JpaRepository<BreakdownAttendee, Integer>{
	  
	
	
}