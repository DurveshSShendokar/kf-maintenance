package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AlertConfiguration;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.repo.AlertConfigurationRepo;
@RestController
@CrossOrigin("*")
@RequestMapping("/alert")
public class AlertConfigurationController {

	
	@Autowired
	AlertConfigurationRepo alertConfigurationRepo;
	
	
	
	@RequestMapping(value = "/deleteAlertConfiguration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteAlertConfiguration(@RequestBody AlertConfiguration alertConfiguration ) {
		Status status= new Status();
		try {
			
			alertConfigurationRepo.delete(alertConfiguration);
			status.setCode(200);
			status.setMessage("Alert Configuration is deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@RequestMapping(value = "/addAlertConfiguration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addAlertConfiguration(@RequestBody AlertConfiguration alertConfiguration ) {
		Status status= new Status();
		try {
			Optional<AlertConfiguration> optional=alertConfigurationRepo.getAlertConfigurationByRegisterNo(alertConfiguration.getRegisterNo());
			if(alertConfiguration.getId()!=0) {
				alertConfigurationRepo.save(alertConfiguration);
				status.setCode(200);
				status.setMessage("Alert Configuration is added successfully");
			
				
			}else {
				if(optional.isPresent()) {
					status.setCode(500);
					status.setMessage("Alert Configuration is already Exits	");
				}else {
					alertConfigurationRepo.save(alertConfiguration);
					status.setCode(200);
					status.setMessage("Alert Configuration is added successfully");
				}
			}
			
			
			
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/getAlertConfigurationByLimit", method = RequestMethod.GET)
	public @ResponseBody List<AlertConfiguration> getAlertConfigurationByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<AlertConfiguration> list = new ArrayList<AlertConfiguration>();
		try {
			list = alertConfigurationRepo.getAlertConfigurationByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/geAlertConfigurationCount", method = RequestMethod.GET)
	public @ResponseBody int geAlertConfigurationCount() {
		int count = 0;
		try {
				count = (int) alertConfigurationRepo.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getCountAlertConfigurationBySearch", method = RequestMethod.GET)
	public @ResponseBody int getCountAlertConfigurationBySearch(@RequestParam("searchText") String searchText) {
		int supplierCount = 0;
		try {
			supplierCount = alertConfigurationRepo.getCountAlertConfigurationBySearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}

	/*
	 * @RequestMapping(value = "/getAlertConfigurationCountAndSearch", method =
	 * RequestMethod.GET) public @ResponseBody List<AlertConfiguration>
	 * getAlertConfigurationCountAndSearch(@RequestParam("searchText") String
	 * searchText) { List<AlertConfiguration> list = new
	 * ArrayList<AlertConfiguration>(); try { list =
	 * alertConfigurationRepo.getAlertConfigurationCountAndSearch(searchText); }
	 * catch (Exception e) { e.printStackTrace(); } return list; }
	 */
	@RequestMapping(value = "/getAlertConfigurationCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<AlertConfiguration> getAlertConfigurationCountAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<AlertConfiguration> list = new ArrayList<AlertConfiguration>();
		try {
			list = alertConfigurationRepo.getAlertConfigurationCountAndSearch(searchText, pageNo, perPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody List<AlertConfiguration> getAll() {
		List<AlertConfiguration> list = new ArrayList<AlertConfiguration>();
		try {
			list = alertConfigurationRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	 

	
}
