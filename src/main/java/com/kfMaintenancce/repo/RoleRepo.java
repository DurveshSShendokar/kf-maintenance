package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Role;



@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>, RoleRepoCustom{
	
	Optional<Role> findByRoleName(String roleName);
	
	@Query("from Role where roleName=?1")
    Optional<Role> getRoleByName(String roleName);
	
	  
}