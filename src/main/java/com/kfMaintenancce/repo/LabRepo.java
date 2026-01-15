package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Lab;


@Repository
public interface LabRepo extends JpaRepository<Lab, Integer>, LabRepoCustom{
	// Optional<Lab> findByLabName(String labName);
	 
	@Query("SELECT l FROM Lab l WHERE TRIM(LOWER(l.labName)) = TRIM(LOWER(:labName))")
	Optional<Lab> findByLabName(@Param("labName") String labName);
	 
	 @Query("SELECT l FROM Lab l WHERE l.labName = :labName")
	 Lab findByLabNames(@Param("labName") String labName);
	 
	  @Query("SELECT l FROM Lab l WHERE l.lab_id = :id")
	    Optional<Lab> findById(@Param("id") int id);

	  
}