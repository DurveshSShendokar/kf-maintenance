package com.kfMaintenancce.controller;

import com.kfMaintenancce.dto.BreakdownSpareReport;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.BreakdownSpare;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStocking;
import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.model.SpareStocking;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.BreakdownSpareRepo;
import com.kfMaintenancce.repo.MaintSpareRepo;
import com.kfMaintenancce.repo.MaintSpareStockRepo;
import com.kfMaintenancce.repo.MaintsparestockingRepo;
import com.kfMaintenancce.service.MaintSpareServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/maintspares")
public class MaintSpareController {

    @Autowired
    private MaintSpareServices maintSpareService;
    
    @Autowired
    MaintSpareRepo maintSpareRepo;
    
    @Autowired
    BreakdownRepo breakdownRepo; 
    @Autowired
    BreakdownSpareRepo breakdownSpareRepo;
    
    @Autowired
    MaintSpareStockRepo maintSpareStockRepo;
    
    @Autowired
    MaintsparestockingRepo maintsparestockingRepo;

    @GetMapping("/list")
    public List<MaintSpare> getAllSpares() {
        return maintSpareService.getAllSpares();
    }


    @PostMapping("/create")
    public MaintSpare createSpare(@RequestBody MaintSpare maintSpare) {
        return maintSpareService.saveSpare(maintSpare);
    }
    
    


@GetMapping("/getMaintSpareByLimit")
public @ResponseBody List<MaintSpare> getMaintSpareByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<MaintSpare> list = new ArrayList<MaintSpare>();
	try {
		list = maintSpareRepo.getMaintSpareByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geMaintSpareCount")
public @ResponseBody int getMaintSpareCount() {
	int count = 0;
	try {
			count = (int) maintSpareRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getMaintSpareByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<MaintSpare> getMaintSpareByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<MaintSpare> list = new ArrayList<MaintSpare>();
	try {
		
		list = maintSpareRepo.getMaintSpareByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getMaintSpareCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getMaintSpareCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = maintSpareRepo.getMaintSpareCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}
	
    
    

    @PutMapping("/update/{id}")
    public ResponseEntity<MaintSpare> updateSpare(@PathVariable int id, @RequestBody MaintSpare updatedSpare) {
        try {
            MaintSpare spare = maintSpareService.updateSpare(id, updatedSpare);
            return ResponseEntity.ok(spare);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteSpare(@PathVariable int id) {
    	maintSpareService.deleteSpare(id);
        String message = "Maint Spares with ID " + id + " has been deleted successfully.";
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/breakdown_spare_export")
    public void exportSpareUtilization(HttpServletResponse response) throws IOException {
        List<BreakdownSpare> reports = breakdownSpareRepo.findAll();
        if (!reports.isEmpty()) {
           
        	//maintSpareService.exportBreakdownSpareReport(response, reports);
        } else {
           
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "No utilization reports found.");
        }
    }
    
}
