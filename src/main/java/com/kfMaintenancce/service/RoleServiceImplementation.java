package com.kfMaintenancce.service;




import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;


import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.repo.CategoryRepo;

import com.kfMaintenancce.repo.DepartmentRepo;

import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.LocationRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.RoleRepo;

import java.io.Reader;
import java.io.BufferedReader;


@Service
public class RoleServiceImplementation implements RoleService {

	
	
	 
	
	 
	 @Autowired
	    private RoleRepo reloRepo;
	 
	 
	  

	    
	
 
	 
	    
	    
	 
	    
	    //for roles
	    
	    public Role createRole(Role role) {
	        return reloRepo.save(role);
	    }
	    
	    public List<Role> saveroles(List<Role> role) {
	        return reloRepo.saveAll(role);
	    }
	    
	    public Role updateRole(int role_id, Role role) {
	        Optional<Role> RoleOptional = reloRepo.findById(role_id);
	        if (RoleOptional.isPresent()) {
	            Role existingrole = RoleOptional.get();
	            existingrole.setRoleName(role.getRoleName());
	            existingrole.setRole_id(role.getRole_id());
	            return reloRepo.save(existingrole);
	        } else {
	            throw new RuntimeException("Role not found with id: " + role_id);
	        }
	    }
	    
	    public void deleteRole(int role_id) {
	        Optional<Role> RoleOptional = reloRepo.findById(role_id);
	        if (RoleOptional.isPresent()) {
	        	reloRepo.deleteById(role_id);
	        } else {
	            throw new RuntimeException("Role not found with id: " + role_id);
	        }
	    }
	    
	    public List<Role> getAllRoles() {
	        return reloRepo.findAll();
	    }
	    
	    
	    	  	
	    
	    
	
	    
}
