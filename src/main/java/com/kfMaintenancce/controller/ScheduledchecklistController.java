package com.kfMaintenancce.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.model.Scheduledchecklist;
import com.kfMaintenancce.service.MachineServices;
import com.kfMaintenancce.service.MaintServices;
import com.kfMaintenancce.service.ScheduledchecklistServices;



@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/scheduledchecklist")

public class ScheduledchecklistController {

	
	@Autowired
	MachineServices machineServices;
	
	

	@Autowired
	MaintServices maintServices;
	
	
	
	@Autowired
	ScheduledchecklistServices scheduledchecklistServices;
	
	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Scheduledchecklist> getScheduledchecklists(@RequestParam(value="maint_id", required=false) int maint_id) {
		List<Scheduledchecklist> scheduledchecklistList = null;
		try {
			
			scheduledchecklistList = scheduledchecklistServices.getChecklistList(maint_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduledchecklistList;
	}
	
	
	
	
	
	
	
}
