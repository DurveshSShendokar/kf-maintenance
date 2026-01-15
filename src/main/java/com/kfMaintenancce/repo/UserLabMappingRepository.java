package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.model.UserLabMapping;

public interface UserLabMappingRepository extends JpaRepository<UserLabMapping, Integer> {
	
	@Query("SELECT ulm FROM UserLabMapping ulm WHERE ulm.user = :user")
	List<UserLabMapping> findByUser(@Param("user") UserDetails user);

	@Query("SELECT ulm FROM UserLabMapping ulm WHERE ulm.lab = :lab")
	List<UserLabMapping> findByLab(@Param("lab") Lab lab);
	
	@Query("SELECT CASE WHEN COUNT(ulm) > 0 THEN true ELSE false END FROM UserLabMapping ulm WHERE ulm.user = :user AND ulm.lab = :lab")
	boolean existsByUserAndLab(@Param("user") UserDetails user, @Param("lab") Lab lab);

	

}
