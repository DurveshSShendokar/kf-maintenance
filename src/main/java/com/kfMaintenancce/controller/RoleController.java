package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.repo.RoleRepo;
import com.kfMaintenancce.service.DemoService;

@RequestMapping("/Role")
@RestController
@CrossOrigin(origins = "*")
public class RoleController {
	
	
	
	  @Autowired
	    private DemoService service;
	  
      
   @Autowired
   RoleRepo roleRepo;
   
 //for roles
	  
	  
	  
	  


@GetMapping("/getRoleByLimit")
public @ResponseBody List<Role> getRoleByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Role> list = new ArrayList<Role>();
	try {
		list = roleRepo.getRoleByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


@RequestMapping(value = "/getRoleByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Role> getRoleByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Role> list = new ArrayList<Role>();
	try {
		
		list = roleRepo.getRoleByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getRoleCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getRoleCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = roleRepo.getRoleCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@GetMapping("/getRoleCount")
public @ResponseBody int  getRoleCount() {
    int  count= 0;
    try {
        count= (int) roleRepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}

	  
   
   @PostMapping("/role")
   public ResponseEntity<Role> createRole(@RequestBody Role role) {
       Role createRole = service.createRole(role);
       return new ResponseEntity<>(createRole, HttpStatus.CREATED);
   }
   
   @PostMapping("/roles")
   public ResponseEntity<List<Role>> addRoles(@Validated @RequestBody List<Role> role) {
     
       List<Role> savedRole = this.service.saveroles(role);
       return new ResponseEntity<>(savedRole, HttpStatus.CREATED); 
   }
  
   @PutMapping("/roles/{role_id}")
   public ResponseEntity<Role> updateRole(@PathVariable int role_id, @RequestBody Role role) {
       Role updatedRole = service.updateRole(role_id, role);
       return new ResponseEntity<>(updatedRole, HttpStatus.OK);
   }
   
  
   @DeleteMapping("/roles/{role_id}")
   public ResponseEntity<String> deleteRole(@PathVariable int role_id) {
       service.deleteRole(role_id);
       String message = "Role with ID " + role_id + " has been deleted successfully.";
       return new ResponseEntity<>(message, HttpStatus.OK);
   }
   
  
   @GetMapping("/roles")
   public ResponseEntity<List<Role>> getAllRoles() {
       List<Role> roles = service.getAllRoles();
       return new ResponseEntity<>(roles, HttpStatus.OK);
   }
   
    

}
