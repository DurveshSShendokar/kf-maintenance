package com.kfMaintenancce.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Shift;
import com.kfMaintenancce.repo.ShiftRepo;
import com.kfMaintenancce.service.ShiftServices;




@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/shift")

public class ShiftController {

	@Autowired
	ShiftServices shiftServices;
	
	@Autowired
	ShiftRepo shiftRepo;
	

	
	@GetMapping(value = "/{list}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Shift> getShifts() {
		List<Shift> shiftList = null;
		try {
			
			shiftList = shiftServices.getShiftList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shiftList;
	}
	
	
	


@GetMapping("/getShiftCount")
public @ResponseBody int  getShiftCount() {
    int  count= 0;
    try {
        count= (int) shiftRepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}

	
	
	
	
}
