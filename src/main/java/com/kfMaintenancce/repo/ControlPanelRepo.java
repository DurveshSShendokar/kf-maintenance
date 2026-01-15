package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.ControlPanel;

public interface ControlPanelRepo extends JpaRepository<ControlPanel, Integer>,ControlPanelCustomeRepo{

	  @Query("SELECT c FROM ControlPanel c WHERE c.locationName = :locationName AND c.plantName = :plantName")
	    Optional<ControlPanel> findByLocationNameAndPlantName(@Param("locationName") String locationName,
	                                                          @Param("plantName") String plantName);

}
