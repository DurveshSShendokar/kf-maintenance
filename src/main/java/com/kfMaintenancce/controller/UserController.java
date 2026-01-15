package com.kfMaintenancce.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.UserDetailsDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.CompanyProfile;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.CompanyProfileRepo;
import com.kfMaintenancce.repo.DepartmentRepo;
import com.kfMaintenancce.repo.RoleRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.UserAndLoginService;




@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserAndLoginService loginService;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	UserDetailsRepo userDetailsRepo;
	
	 @Autowired
      CompanyProfileRepo companyRepo;




@GetMapping("/getUserDetailsByLimit")
public @ResponseBody List<UserDetails> getUserDetailsByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<UserDetails> list = new ArrayList<UserDetails>();
	try {
		list = userDetailsRepo.getUserDetailsByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


@RequestMapping(value = "/getUserDetailsByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<UserDetails> getUserDetailsByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<UserDetails> list = new ArrayList<UserDetails>();
	try {
		
		list = userDetailsRepo.getUserDetailsByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getUserDetailsCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getUserDetailsCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = userDetailsRepo.getUserDetailsCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@GetMapping("/getUserDetailsCount")
public @ResponseBody int  getUserDetailsCount() {
    int  count= 0;
    try {
        count= (int) userDetailsRepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}


	
	@GetMapping("/getAlluserForMachine")
	public ResponseEntity<List<UserDetails>> getAlluserForMachine() {
		
		List<UserDetails> allUsers =	loginService.getAlluserForMachine();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}

	@GetMapping("/getITEnginner")
	public ResponseEntity<List<UserDetails>> getITEnginner() {
		
		List<UserDetails> allUsers =	loginService.getITEnginner();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}
	@GetMapping("/getAllUserForComplaintAllocation")
	public ResponseEntity<List<UserDetails>> getAllUserForComplaintAllocation() {
		
		List<UserDetails> allUsers =	loginService.getAllUserForComplaintAllocation();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}

	
	@GetMapping("/getAlluserPagination")
	public ResponseEntity<Map<String, Object>> getUsers(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Page<UserDetailsDTO> userPage = loginService.getUserss(page - 1, size);

	    Map<String, Object> response = new HashMap<>();
	    response.put("users", userPage.getContent());
	    response.put("currentPage", page); // 1-based page
	    response.put("totalItems", userPage.getTotalElements());
	    response.put("totalPages", userPage.getTotalPages());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	
	@GetMapping("/getAlluser")
	public ResponseEntity<List<UserDetails>> getUsers() {
		
		List<UserDetails> allUsers =	loginService.getUsers();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}


    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable("id") int id) {
        UserDetails user = loginService.getUserByIds(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAllRoles")
	public ResponseEntity<List<Role>> getAllRoles() {
		
		List<Role> allUsers =	roleRepo.findAll();
		return new ResponseEntity<List<Role>>(allUsers, HttpStatus.OK);
	}
	
	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<Department>> getAllDepartments() {
		
		List<Department> allUsers =	departmentRepo.findAll();
		return new ResponseEntity<List<Department>>(allUsers, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/create")
	public @ResponseBody
	Status addBreakdown(@RequestBody UserDetails userDetails) {
		Status status= new Status();
		try {
			
			System.out.println("User "+userDetails.toString());
Optional<UserDetails> optional=userDetailsRepo.getUserByUserName(userDetails.getUserName());
	if(optional.isPresent()) {
		status.setCode(500);
		status.setMessage("User Name already Exist");
	}else {
		status.setCode(200);
		status.setMessage("User Saved Successfully");
					
			String maxId=userDetailsRepo.getMaxId();
			int id=Integer.valueOf(maxId);
			id++;
			userDetails.setId(id);
			

		loginService.addUser(userDetails);
	}
		
		} catch (Exception e) {
		    e.printStackTrace();
		    status.setCode(500);
		    status.setMessage(e.getMessage());
		}

		return status;


	}
	
	
	
	
	
	@PostMapping(value = "/changeStatus")
	public @ResponseBody
	Status changeStatus(@RequestBody UserDetails userDetails) {
		Status status= new Status();
		try {
			if(userDetails.getActive()==1) {
				userDetails.setActive(0);
			}else {
				userDetails.setActive(1);
				}
			userDetailsRepo.save(userDetails);
	status.setCode(200);
	status.setMessage("Stastus Change Successfully");
		
		} catch (Exception e) {
			// e.printStackTrace();
			}
		return status;


	}
	@PostMapping(value = "/edit")
	public @ResponseBody
	Status edit(@RequestBody UserDetails userDetails) {
		Status status= new Status();
		try {
			
	
		status.setCode(200);
		status.setMessage("User Updated Successfully");
		loginService.addUser(userDetails);
	
		
		} catch (Exception e) {
			// e.printStackTrace();
			}
		return status;


	}
	@PostMapping(value = "/deleteUser")
	public @ResponseBody
	Status deleteUser(@RequestBody UserDetails userDetails) {
		Status status= new Status();
		try {
			
			System.out.println("User "+userDetails.toString());

		status.setCode(200);
		status.setMessage("User Deleted Successfully");
		loginService.deleteUser(userDetails);
	
		
		} catch (Exception e) {
			// e.printStackTrace();
			}
		return status;


	}
	}
	
	

