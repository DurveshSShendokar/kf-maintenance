package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.model.UserLabMapping;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.LabService;
import com.kfMaintenancce.service.UserLabMappingServiceImpl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/lab")
public class LabController {
	@Autowired
	LabService labService;
	
	 @Autowired
	    private UserLabMappingServiceImpl service;
	    @Autowired
	    private UserDetailsRepo userRepo; 

	@PostMapping(value = "/addLab", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addLab(@RequestBody Lab lab ) {
		Status status= new Status();
		try {
			labService.addLab(lab);
			status.setCode(200);
			status.setMessage("Lab is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	  @GetMapping("/user_labs/{userId}")
	    public ResponseEntity<?> getLabsByUserId(@PathVariable int userId) {
	        UserDetails user = userRepo.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	        List<UserLabMapping> mappings = service.getLabsByUser(user);

	        List<Lab> labs = mappings.stream()
	            .map(UserLabMapping::getLab)
	            .collect(Collectors.toList());

	        return ResponseEntity.ok(labs);
	    }
	
	
	@GetMapping("/getLabByLimit")
	public @ResponseBody List<Lab> getLabByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Lab> list = new ArrayList<Lab>();
		try {
			list = labService.getLabByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/getLabCount")
	public @ResponseBody int getLabCount() {
		int count = 0;
		try {
				count = (int) labService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@PostMapping("/getLabByLimitAndGroupSearch")
	public @ResponseBody List<Lab> getLabByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Lab> list = new ArrayList<Lab>();
		try {
			
			list = labService.getLabByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@PostMapping("/getLabCountByLimitAndGroupSearch")
	public @ResponseBody int getLabCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = labService.getLabCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{lab_id}")
	    public ResponseEntity<Map<String, String>> deleteLab(@PathVariable int lab_id) {
	        labService.deleteLab(lab_id);
	        String message = "Lab with ID " + lab_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	 
	 
	 @GetMapping("/all")
	    public ResponseEntity<List<Lab>> getLabs() {
	        List<Lab> labs = labService.getLabs();
	        return new ResponseEntity<>(labs, HttpStatus.OK);
	    }
	

}
