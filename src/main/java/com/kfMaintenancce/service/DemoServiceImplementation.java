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
public class DemoServiceImplementation implements DemoService {

	
	
	 
	 @Autowired
	    private LocationRepo locationrepository;
	 
	 @Autowired
	    private LabRepo labrepository;
	 
	 @Autowired
	    private RoleRepo reloRepo;
	 
	 
	  

	    
	
 
	 
	    
	    
	    
	   //for location
	    
	    public Location createLocation(Location location) {
	        return locationrepository.save(location);
	    }
	    
	    public List<Location> saveLocations(List<Location> locations) {
	        return locationrepository.saveAll(locations);
	    }
	    
	    public Location updateLocation(int loc_id, Location location) {
	        Optional<Location> locationOptional = locationrepository.findById(loc_id);
	        if (locationOptional.isPresent()) {
	            Location existingLocation = locationOptional.get();
	            existingLocation.setLocationName(location.getLocationName());
	            existingLocation.setLocationCode(location.getLocationCode());
	            
	            return locationrepository.save(existingLocation);
	        } else {
	            throw new RuntimeException("Location not found with id: " + loc_id);
	        }
	    }
	    
	   
	    public List<Location> getAllLocations() {
	        return locationrepository.findAll();
	    }
	    
	    public void deleteLocation(int loc_id) {
	        Optional<Location> LocationOptional = locationrepository.findById(loc_id);
	        if (LocationOptional.isPresent()) {
	        	locationrepository.deleteById(loc_id);
	        } else {
	            throw new RuntimeException("Location not found with id: " + loc_id);
	        }
	    }
	    
	    
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
	    
	    
	    
 //for labs
	    
	    public Lab createLab(Lab labs) {
	        return labrepository.save(labs);
	    }
	    
	    public List<Lab> saveLabs(List<Lab> labs) {
	        return labrepository.saveAll(labs);
	    }
	    
	    public Lab updateLab(int lab_id, Lab lab) {
	        Optional<Lab> labOptional = labrepository.findById(lab_id);
	        if (labOptional.isPresent()) {
	            Lab existingLab = labOptional.get();
	            existingLab.setLabName(lab.getLabName());
	            existingLab.setLabCode(lab.getLabCode());
	            return labrepository.save(existingLab);
	        } else {
	            throw new RuntimeException("Lab not found with id: " + lab_id);
	        }
	    }
	    
	    public void deleteLab(int lab_id) {
	        Optional<Lab> LabOptional = labrepository.findById(lab_id);
	        if (LabOptional.isPresent()) {
	        	labrepository.deleteById(lab_id);
	        } else {
	            throw new RuntimeException("Lab not found with id: " + lab_id);
	        }
	    }
	    
	    public List<Lab> getAllLabs() {
	        return labrepository.findAll();
	    }
	    
	    
	    
	    	  	
	    
	    
	
	    
}
