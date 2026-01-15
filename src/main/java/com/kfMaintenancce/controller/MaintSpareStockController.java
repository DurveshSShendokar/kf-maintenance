package com.kfMaintenancce.controller;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.MaintSpareStockingResponseDTO2;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.MachineMaintSpare;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.model.MaintSpareStocking;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.model.SpareStocking;
import com.kfMaintenancce.repo.MachineSpareAssignRepository;
import com.kfMaintenancce.repo.MaintSpareStockRepo;
import com.kfMaintenancce.repo.MaintsparestockingRepo;
import com.kfMaintenancce.service.MaintSpareStockServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/MaintSparestock")
public class MaintSpareStockController {
	
	
	  @Autowired
	    MaintSpareStockRepo maintSpareStockRepo;
	  
	  @Autowired
	  MachineSpareAssignRepository machineSpareAssignRepository;
	    
	    @Autowired
	    MaintsparestockingRepo maintsparestockingRepo;

    @Autowired
    private MaintSpareStockServices maintSpareStockService;
    
    


   
    @GetMapping("/list")
    public List<MaintSpareStock> getAllSpareStocks() {
        return maintSpareStockService.getAllSpareStocks();
    }
    
    @GetMapping("/List_MaintSpareStocking")
    public List<MaintSpareStocking> getAllSpareStocking() {
        return maintsparestockingRepo.findAll();
    }

    
 	@RequestMapping(value = "/addSpareStocking", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
 	public @ResponseBody Status addSpareStock(@RequestBody MaintSpareStocking maintSpareStocking) {
 		Status status = new Status();
 	    try {
 	        // Set stocking date to current date if it's not already set
 	        if (maintSpareStocking.getStockingDate() == null) {
 	        	maintSpareStocking.setStockingDate(new Date());
 	        }
 	        
 	    	 
 	       
 	    	
 	        maintsparestockingRepo.save(maintSpareStocking);

 	       
 	        Optional<MaintSpareStock> optional = maintSpareStockRepo.getMaintSpareStockByMaintSpare(maintSpareStocking.getMaintspare().getMaintspare_id());
 	        if (optional.isPresent()) {
 	         
 	            MaintSpareStock spareStock = optional.get();
 	            spareStock.setAvailableQuantity(spareStock.getAvailableQuantity() + maintSpareStocking.getStockingQuantity());
 	            spareStock.setStockUpdatedDate(new Date()); 
 	            maintSpareStockRepo.save(spareStock);
 	        } else {
 	        
 	        	MaintSpareStock maintSpareStock = new MaintSpareStock();
 	        	maintSpareStock.setMaintSpare(maintSpareStocking.getMaintspare());
 	        	maintSpareStock.setAvailableQuantity(maintSpareStocking.getStockingQuantity());
 	        	maintSpareStock.setStockUpdatedDate(new Date()); 
 	            maintSpareStockRepo.save(maintSpareStock); 
 	           
 	            
 	        }

 	       
 	        status.setCode(200);
 	        status.setMessage("SpareStock is added successfully");

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	     
 	        return new Status(e.toString());
 	    }
 	    
 	    return status;
 	}

    
    
//    @DeleteMapping("/delete/{maintsparestocking_id}")
//    public ResponseEntity<Map<String, String>> deleteSpareStock(@PathVariable int maintsparestocking_id)
//    {
//    	maintsparestockingRepo.deleteById(maintsparestocking_id);
//        String message = "Maint Spare Stock with ID " + maintsparestocking_id + " has been deleted successfully.";
//        Map<String, String> response = new HashMap<>();
//        response.put("message", message);
//        return ResponseEntity.ok(response);
//    }
//   
 	
 	@DeleteMapping("/delete/{maintsparestocking_id}")
 	public ResponseEntity<Map<String, String>> deleteSpareStock(@PathVariable int maintsparestocking_id) {
 	   
 	    Optional<MaintSpareStocking> optionalStocking = maintsparestockingRepo.findById(maintsparestocking_id);

 	    if (optionalStocking.isPresent()) {
 	        MaintSpareStocking stocking = optionalStocking.get();

 	       
 	        Optional<MaintSpareStock> optionalSpareStock = maintSpareStockRepo.getMaintSpareStockByMaintSpare(stocking.getMaintspare().getMaintspare_id());
 	        if (optionalSpareStock.isPresent()) {
 	            MaintSpareStock spareStock = optionalSpareStock.get();

 	          
 	            double newAvailableQuantity = spareStock.getAvailableQuantity() - stocking.getStockingQuantity();
 	            spareStock.setAvailableQuantity(newAvailableQuantity);

 	           
 	            maintSpareStockRepo.save(spareStock);
 	        }

 	    
 	        maintsparestockingRepo.deleteById(maintsparestocking_id);

 	    
 	        String message = "Maint Spare Stocking with ID " + maintsparestocking_id + " has been deleted successfully.";
 	        Map<String, String> response = new HashMap<>();
 	        response.put("message", message);

 	        return ResponseEntity.ok(response);
 	    } else {
 	      
 	        String message = "Maint Spare Stocking with ID " + maintsparestocking_id + " not found.";
 	        Map<String, String> response = new HashMap<>();
 	        response.put("message", message);

 	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
 	    }
 	}

    
    @GetMapping("/allMaintspareStocking")
	 public ResponseEntity<List<MaintSpareStockingResponseDTO2>> getAllspares() {
	     List<MaintSpareStocking> spareStockings = maintsparestockingRepo.findAll();
	     List<MaintSpareStockingResponseDTO2> responseDTOs = new ArrayList<>();
	     for (MaintSpareStocking stocking : spareStockings) {
	    	 MaintSpareStockingResponseDTO2 dto = new MaintSpareStockingResponseDTO2();       
	         dto.setSpareStockingId(stocking.getMaintsparestocking_id());
	         dto.setMaintspare(stocking.getMaintspare());
	         dto.setStockingQuantity(stocking.getStockingQuantity());
	         dto.setStockingDate(stocking.getStockingDate());
	         dto.setStockingBy(stocking.getStockingBy());
	         dto.setUnitPrice(stocking.getUnitPrice());       
	         Optional<MaintSpareStock> optionalSpareStock = maintSpareStockRepo.getMaintSpareStockByMaintSpare(stocking.getMaintspare().getMaintspare_id());
	         if (optionalSpareStock.isPresent()) {
	             dto.setAvailableQuantity(optionalSpareStock.get().getAvailableQuantity());
	         } else {
	             dto.setAvailableQuantity(0.0); 
	         }

	         responseDTOs.add(dto);
	     }

	     return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
	 }

    
    @RequestMapping(value = "/getMachineSpareByMachineId", method = RequestMethod.GET)
    public @ResponseBody List<MaintSpare> getMachineSpareByMachineId(@RequestParam("machineId") int machineId) {
        List<MaintSpare> list = new ArrayList<>();
        try {
           
            List<MachineMaintSpare> list2 = machineSpareAssignRepository.findBymachineyID(machineId);
            System.out.println("Machine Spare Assign Size: " + list2.size());

            for (MachineMaintSpare machineSpareAssign : list2) {
                MaintSpare maintSpare = machineSpareAssign.getMaintSpare();
                
              
                Optional<MaintSpareStock> spareStockOP = maintSpareStockRepo.getMaintSpareStockByMaintSpare(maintSpare.getMaintspare_id());
                
                if (spareStockOP.isPresent()) {
                    
                    maintSpare.setAvialableQuantity(spareStockOP.get().getAvailableQuantity());
                }
                list.add(maintSpare);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    
  
}
