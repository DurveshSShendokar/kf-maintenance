
package com.kfMaintenancce.service;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Role;


public interface DemoService {

	

	
	 
	 
	 public Location updateLocation(int loc_id, Location location);
	 public List<Location> saveLocations(List<Location> locations);
	 public Location createLocation(Location location);
	 public List<Location> getAllLocations();
	 public void deleteLocation(int loc_id);
	 
	 
	 public Lab createLab(Lab lab);
	 public List<Lab> saveLabs(List<Lab> labs);
	 public Lab updateLab(int lab_id, Lab lab);
	 public void deleteLab(int lab_id);
	 public List<Lab> getAllLabs();
	 
	 public List<Role> getAllRoles();
	 public void deleteRole(int role_id);
	 public Role updateRole(int role_id, Role role);
	 public List<Role> saveroles(List<Role> role);
	 public Role createRole(Role role);
	 
	
	


	 
	
	 
}