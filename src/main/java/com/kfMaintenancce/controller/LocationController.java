package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.service.LocationService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/location")
public class LocationController {
	@Autowired
	LocationService locationService;

	@RequestMapping(value = "/addLocation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addLocation(@RequestBody Location location ) {
		Status status= new Status();
		try {
			locationService.addLocation(location);
			status.setCode(200);
			status.setMessage("Location is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@RequestMapping(value = "/getLocationByLimit", method = RequestMethod.GET)
	public @ResponseBody List<Location> getLocationByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Location> list = new ArrayList<Location>();
		try {
			list = locationService.getLocationByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getLocationCount", method = RequestMethod.GET)
	public @ResponseBody int getLocationCount() {
		int count = 0;
		try {
				count = (int) locationService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getLocationByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Location> getLocationByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Location> list = new ArrayList<Location>();
		try {
			
			list = locationService.getLocationByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getLocationCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getLocationCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = locationService.getLocationCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{location_id}")
	    public ResponseEntity<Map<String, String>> deleteLocation(@PathVariable int location_id) {
	        locationService.deleteLocation(location_id);
	        String message = "Location with ID " + location_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	 
	 @GetMapping("/all")
	    public ResponseEntity<List<Location>> getlocations() {
	        List<Location> locs = locationService.getlocations();
	        return new ResponseEntity<>(locs, HttpStatus.OK);
	    }
	

}
