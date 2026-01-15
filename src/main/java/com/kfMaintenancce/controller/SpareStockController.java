package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.SpareStockingResponseDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.model.SpareStocking;
import com.kfMaintenancce.repo.AssetSpareAssignRepository;
import com.kfMaintenancce.repo.SpareStockRepo;
import com.kfMaintenancce.repo.SpareStockingRepo;
import com.kfMaintenancce.service.SpareStockService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/spareStocking")
public class SpareStockController {
	@Autowired
	SpareStockService spareStockService;
	
	@Autowired
	SpareStockingRepo spareStockingRepo;
	
	@Autowired
	SpareStockRepo spareStockRepo;
	
	@Autowired
	AssetSpareAssignRepository assetSpareAssignRepo;
	
	
	@GetMapping("/getAllocatedSpareByMachineId")
	public @ResponseBody List<Spare> getSpareStockByLimitAndGroupSearch(@RequestParam("assetInventoryId") int assetInventoryId) {
		List<Spare> list = new ArrayList<Spare>();
		try {
			List<AssetSpareAssign> list2=assetSpareAssignRepo.findByassetInventoryID(assetInventoryId);
			System.out.println("Asset Spare Size  "+list2.size());
			for(AssetSpareAssign assetSpareAssign:list2) {
				Spare spare=assetSpareAssign.getSpare();
				Optional<SpareStock> spareStockOP=spareStockRepo.getSpareStockBySpare(assetSpareAssign.getSpare().getSpare_id());
				if(spareStockOP.isPresent()) {
					
					spare.setAvialableQuantity(spareStockOP.get().getAvailableQuantity());
					
					
				}
				list.add(spare);
				
				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	

	@PostMapping(value = "/addSpareStocking", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addSpareStock(@RequestBody SpareStocking spareStocking) {
		Status status = new Status();
	    try {
	        // Set stocking date to current date if it's not already set
	        if (spareStocking.getStockingDate() == null) {
	            spareStocking.setStockingDate(new Date());
	        }
	        
	    	 
	       
	    	
	        spareStockingRepo.save(spareStocking);

	       
	        Optional<SpareStock> optional = spareStockRepo.getSpareStockBySpare(spareStocking.getSpare().getSpare_id());
	        if (optional.isPresent()) {
	         
	            SpareStock spareStock = optional.get();
	            spareStock.setAvailableQuantity(spareStock.getAvailableQuantity() + spareStocking.getStockingQuantity());
	            spareStock.setStockUpdatedDate(new Date()); 
	            spareStockRepo.save(spareStock);
	        } else {
	        
	            SpareStock spareStock = new SpareStock();
	            spareStock.setSpare(spareStocking.getSpare());
	            spareStock.setAvailableQuantity(spareStocking.getStockingQuantity());
	            spareStock.setStockUpdatedDate(new Date()); 
	            spareStockRepo.save(spareStock); 
	           
	            
	        }

	       
	        status.setCode(200);
	        status.setMessage("SpareStock is added successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	     
	        return new Status(e.toString());
	    }
	    
	    return status;
	}

	
	@GetMapping("/getSpareStockByLimit")
	public @ResponseBody List<SpareStockingResponseDTO> getSpareStockByLimit(
	        @RequestParam("pageNo") int pageNo,
	        @RequestParam("perPage") int perPage) {

	    List<SpareStockingResponseDTO> responseDTOs = new ArrayList<>();

	    try {
	        // ✅ Step 1: Apply pagination correctly
	        int firstResult = (pageNo - 1) * perPage;

	        // If you’re using a custom method, ensure it applies setFirstResult/setMaxResults internally
	        List<SpareStocking> spareStockings = spareStockingRepo
	                .findSpareStockingByLimit(firstResult, perPage); // custom query method

	        // ✅ Step 2: Build DTO response
	        for (SpareStocking stocking : spareStockings) {
	            SpareStockingResponseDTO dto = new SpareStockingResponseDTO();
	            dto.setSpareStockingId(stocking.getSpare_stocking_id());
	            dto.setSpare(stocking.getSpare());
	            dto.setStockingQuantity(stocking.getStockingQuantity());
	            dto.setStockingDate(stocking.getStockingDate());
	            dto.setStockingBy(stocking.getStockingBy());
	            dto.setUnitPrice(stocking.getUnitPrice());

	            // Fetch available quantity
	            Optional<SpareStock> optionalSpareStock =
	                    spareStockRepo.getSpareStockBySpare(stocking.getSpare().getSpare_id());

	            dto.setAvailableQuantity(optionalSpareStock.map(SpareStock::getAvailableQuantity).orElse(0.0));

	            responseDTOs.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return responseDTOs;
	}

	
	@GetMapping("/getSpareStockCount")
	public @ResponseBody int getSpareStockCount() {
		int count = 0;
		try {
				count = (int) spareStockService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@PostMapping("/getSpareStockByLimitAndGroupSearch")
	public @ResponseBody List<SpareStockingResponseDTO> getSpareStockByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	    List<SpareStockingResponseDTO> responseDTOs = new ArrayList<>();
	    try {
	        // ✅ Get filtered & paginated SpareStock data
	        List<SpareStock> spareStocks = spareStockService.getSpareStockByLimitAndGroupSearch(groupSearchDTO);

	        // ✅ Convert each record to DTO
	        for (SpareStock spareStock : spareStocks) {
	            SpareStockingResponseDTO dto = new SpareStockingResponseDTO();

	            // Fill SpareStock details
	            dto.setAvailableQuantity(spareStock.getAvailableQuantity());
	            dto.setSpare(spareStock.getSpare());

	            // If you want to include stocking details, fetch them
	            List<SpareStocking> stockList = spareStockingRepo.findBySpare(spareStock.getSpare());
	            if (!stockList.isEmpty()) {
	                SpareStocking stocking = stockList.get(0); // latest or first
	                dto.setSpareStockingId(stocking.getSpare_stocking_id());
	                dto.setStockingQuantity(stocking.getStockingQuantity());
	                dto.setStockingDate(stocking.getStockingDate());
	                dto.setStockingBy(stocking.getStockingBy());
	                dto.setUnitPrice(stocking.getUnitPrice());
	            } else {
	                // If no stocking info found
	                dto.setStockingQuantity(0.0);
	                dto.setUnitPrice(0.0);
	            }

	            responseDTOs.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return responseDTOs;
	}

	@PostMapping("/getSpareStockCountByLimitAndGroupSearch")
	public @ResponseBody int getSpareStockCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = spareStockService.getSpareStockCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	@DeleteMapping("/delete/{spare_stocking_id}")
	public ResponseEntity<Map<String, String>> deleteSpareStock(@PathVariable int spare_stocking_id) {
	    // Retrieve the SpareStocking record
	    Optional<SpareStocking> optionalStocking = spareStockingRepo.findById(spare_stocking_id);

	    if (optionalStocking.isPresent()) {
	        SpareStocking stocking = optionalStocking.get();

	        // Retrieve the associated SpareStock
	        Optional<SpareStock> optionalSpareStock = spareStockRepo.findBySpare(stocking.getSpare());
	        if (optionalSpareStock.isPresent()) {
	            SpareStock spareStock = optionalSpareStock.get();

	            // Subtract the stocking quantity from the available quantity
	            double newAvailableQuantity = spareStock.getAvailableQuantity() - stocking.getStockingQuantity();
	            spareStock.setAvailableQuantity(newAvailableQuantity);

	            // Save the updated SpareStock
	            spareStockRepo.save(spareStock);
	        }

	        // Delete the SpareStocking record
	        spareStockingRepo.deleteById(spare_stocking_id);

	        // Prepare the response
	        String message = "SpareStocking with ID " + spare_stocking_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);

	        return ResponseEntity.ok(response);
	    } else {
	        // Handle the case where the record does not exist
	        String message = "SpareStocking with ID " + spare_stocking_id + " not found.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


	 
	 @GetMapping("/allspareStocking")
	 public ResponseEntity<List<SpareStockingResponseDTO>> getAllspares() {
	     List<SpareStocking> spareStockings = spareStockingRepo.findAll();
	     List<SpareStockingResponseDTO> responseDTOs = new ArrayList<>();
	     for (SpareStocking stocking : spareStockings) {
	         SpareStockingResponseDTO dto = new SpareStockingResponseDTO();
	         dto.setSpareStockingId(stocking.getSpare_stocking_id());
	         dto.setSpare(stocking.getSpare());
	         dto.setStockingQuantity(stocking.getStockingQuantity());
	         dto.setStockingDate(stocking.getStockingDate());
	         dto.setStockingBy(stocking.getStockingBy());
	         dto.setUnitPrice(stocking.getUnitPrice());
	         Optional<SpareStock> optionalSpareStock = spareStockRepo.getSpareStockBySpare(stocking.getSpare().getSpare_id());
	         if (optionalSpareStock.isPresent()) {
	             dto.setAvailableQuantity(optionalSpareStock.get().getAvailableQuantity());
	         } else {
	             dto.setAvailableQuantity(0.0); 
	         }
	         responseDTOs.add(dto);
	     }
	     return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
	 }

	 @GetMapping("/allspareStock")
	    public ResponseEntity<List<SpareStock>> getAllsparestock() {
	        List<SpareStock> spares = spareStockRepo.findAll();
	        return new ResponseEntity<>(spares, HttpStatus.OK);
	    }
	    

}
