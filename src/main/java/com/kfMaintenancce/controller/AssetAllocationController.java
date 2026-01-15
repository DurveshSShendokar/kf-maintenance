package com.kfMaintenancce.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetAllocationHistory;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.repo.AssetAllocationHistoryRepo;
import com.kfMaintenancce.repo.AssetAllocationRepo;
import com.kfMaintenancce.service.AssetAllocationService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/assetAllocation")
public class AssetAllocationController {
	@Autowired
	AssetAllocationService assetAllocationService;
	
	@Autowired
	AssetAllocationRepo assetAllocationRepo;	

	@Autowired 
	AssetAllocationHistoryRepo assetAllocationHistoryRepo;
	
	@PostMapping(value = "/addAssetAllocation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addAssetAllocation(@RequestBody AssetAllocation assetAllocation ) {
		Status status= new Status();
		try {
			System.out.println("cHER");
			assetAllocationService.addAssetAllocation(assetAllocation);
			status.setCode(200);
			status.setMessage("AssetAllocation is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@GetMapping("/getAssetAllocationByLimit")
	public @ResponseBody List<AssetAllocation> getAssetAllocationByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<AssetAllocation> list = new ArrayList<AssetAllocation>();
		try {
			list = assetAllocationService.getAssetAllocationByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/geAssetAllocationCount")
	public @ResponseBody int getAssetAllocationCount() {
		int count = 0;
		try {
				count = (int) assetAllocationService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getAssetAllocationByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<AssetAllocation> getAssetAllocationByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<AssetAllocation> list = new ArrayList<AssetAllocation>();
		try {
			
			list = assetAllocationService.getAssetAllocationByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssetAllocationCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getAssetAllocationCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = assetAllocationService.getAssetAllocationCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{asset_allocation_id}")
	    public ResponseEntity<Map<String, String>> deleteAssetAllocation(@PathVariable int asset_allocation_id) {
		 assetAllocationService.deleteAssetAllocation(asset_allocation_id);
	        String message = "AssetAllocation with ID " + asset_allocation_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	 

	 @PutMapping("/update/{asset_allocation_id}")
	    public ResponseEntity<?> updateAssetAllocation(@PathVariable int asset_allocation_id, @RequestBody AssetAllocation updatedAssetAllocation) {
	        try {
	        	assetAllocationService.updateAssetAllocation(asset_allocation_id, updatedAssetAllocation);
	            return ResponseEntity.ok("AssetAllocation  with ID : " + asset_allocation_id + " updated successfully " );
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	
	 
	 
	 @GetMapping("/AllocatedAssets")
	    public ResponseEntity<List<AssetAllocation>> getAllAssetAllocations() {
	        List<AssetAllocation> assets = assetAllocationService.getAllAssetAllocations();
	        return new ResponseEntity<>(assets, HttpStatus.OK);
	    }
	 

	 // De - Allocate Asset
	 
	 @PostMapping(value = "/deallocateAsset/{allocationId}")
	 public @ResponseBody Status deallocateAsset(@PathVariable int allocationId) {
	     Status status = new Status();
	     try {
	         boolean result = assetAllocationService.deallocateAsset(allocationId);

	         if (result) {
	             status.setCode(200);
	             status.setMessage("Asset deallocated successfully");
	         } else {
	             status.setCode(404);
	             status.setMessage("Allocation not found or already deallocated");
	         }
	         return status;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return new Status(e.toString());
	     }
	 }

	 
	 
	 @GetMapping("/getActiveAllocations")
	 public @ResponseBody List<AssetAllocation> getActiveAllocations() {
	     try {
	         return assetAllocationService.getActiveAllocations();
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }

	 
	 @GetMapping("/getAllocationHistoryByAsset/{assetId}")
	 public @ResponseBody List<AssetAllocation> getAllocationHistoryByAsset(@PathVariable int assetId) {
	     try {
	         return assetAllocationService.getAllocationHistoryByAsset(assetId);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }

	 
	 /////////////////////////////////////////////////
	 @GetMapping("/getAssetAllocationHistory")
	 public @ResponseBody List<AssetAllocationHistory> getAllocationHistory() {
	     try {
	         return assetAllocationHistoryRepo.findAllDeallocated();
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }
	 
	   // ✅ 1. Pagination only
	    @GetMapping("/getAssetAllocationHistoryWithPage")
	    public ResponseEntity<Page<AssetAllocationHistory>> getAllocationHistory(
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page-1, size, Sort.by("history_id").descending());
	        Page<AssetAllocationHistory> result = assetAllocationHistoryRepo.findAllDeallocated(pageable);
	        return ResponseEntity.ok(result);
	    }

	    // ✅ 2. Pagination + Search
	    @GetMapping("/getAssetAllocationHistoryWithSearch")
	    public ResponseEntity<Page<AssetAllocationHistory>> searchAllocationHistory(
	            @RequestParam(defaultValue = "") String keyword,
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page-1, size, Sort.by("history_id").descending());
	        Page<AssetAllocationHistory> result;

	        if (keyword.isEmpty()) {
	            result = assetAllocationHistoryRepo.findAllDeallocated(pageable);
	        } else {
	            result = assetAllocationHistoryRepo.searchDeallocated(keyword, pageable);
	        }

	        return ResponseEntity.ok(result);
	    }

	    // ✅ 3. Count API
	    @GetMapping("/getAssetAllocationHistoryWithCount")
	    public ResponseEntity<Long> getDeallocatedCount() {
	        long count = assetAllocationHistoryRepo.countAllDeallocated();
	        return ResponseEntity.ok(count);
	    }
	    
	    
	    ////////////////////////////////////////
	 
	 
	 

	 @GetMapping("/getAssetDeAllocationCount")
	 public @ResponseBody long getAssetDeAllocationCount() {
	     try {
	         return assetAllocationHistoryRepo.count();
	     } catch (Exception e) {
	         e.printStackTrace();
	         return 0;
	     }
	 }

	 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 // Re Allocation API
	 
	 @PostMapping("/reallocateAsset/{historyId}")
	 public @ResponseBody Status reallocateAsset(
	         @PathVariable int historyId,
	         @RequestParam int userId) {

	     Status status = new Status();
	     try {
	         boolean result = assetAllocationService.reallocateAsset(historyId, userId);

	         if (result) {
	             status.setCode(200);
	             status.setMessage("Asset reallocated successfully");
	         } else {
	             status.setCode(404);
	             status.setMessage("History record not found or asset already allocated");
	         }
	         return status;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return new Status(e.toString());
	     }
	 }
	 
	 
	 
	 // Re Allocation Asset History Report
	 
	 @GetMapping("/getAssetHistoryReport")
	 public @ResponseBody Map<String, List<?>> getAssetHistoryReport(
	         @RequestParam(required = false) Integer assetId,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	         @RequestParam(required = false) Integer month,
	         @RequestParam(required = false) Integer year) {

	     Map<String, List<?>> response = new HashMap<>();
	     try {
	         // If month & year are provided, build fromDate and toDate
	         if (month != null && year != null) {
	             LocalDate start = LocalDate.of(year, month, 1);
	             LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
	             startDate = java.sql.Date.valueOf(start);
	             endDate = java.sql.Date.valueOf(end);
	         }

	         // Default: if no range provided, take all records
	         if (startDate == null) {
	             startDate = new Date(0); // epoch
	         }
	         if (endDate == null) {
	             endDate = new Date(); // now
	         }

	         List<AssetAllocation> active;
	         List<AssetAllocationHistory> history;

	         if (assetId != null) {
	             // Case 2: With AssetId
	             active = assetAllocationRepo.findActiveByAssetAndDateRange(assetId, startDate, endDate);
	             history = assetAllocationHistoryRepo.findHistoryByAssetAndDateRange(assetId, startDate, endDate);
	         } else {
	             // Case 1: Without AssetId
	             active = assetAllocationRepo.findActiveByDateRange(startDate, endDate);
	             history = assetAllocationHistoryRepo.findHistoryByDateRange(startDate, endDate);
	         }

	         response.put("activeAllocations", active);
	         response.put("historyAllocations", history);

	     } catch (Exception e) {
	         e.printStackTrace();
	         response.put("error", Arrays.asList(e.getMessage()));
	     }
	     return response;
	 }

	 
	 @GetMapping("/getAssetHistoryReportWithPage")
	 public ResponseEntity<Map<String, Object>> getAssetHistoryReport(
	         @RequestParam(required = false) Integer assetId,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	         @RequestParam(required = false) Integer month,
	         @RequestParam(required = false) Integer year,
	         @RequestParam int page,
	         @RequestParam int size) {

	     Map<String, Object> response = assetAllocationService.getAssetHistoryReport(assetId, startDate, endDate, month, year, page, size, null);
	     return ResponseEntity.ok(response);
	 }

	 @GetMapping("/getAssetHistoryReportWithSearch")
	 public ResponseEntity<Map<String, Object>> searchAssetHistoryReport(
	         @RequestParam(required = false) Integer assetId,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	         @RequestParam(required = false) Integer month,
	         @RequestParam(required = false) Integer year,
	         @RequestParam int page,
	         @RequestParam int size,
	         @RequestParam(defaultValue = "") String keyword) {

	     Map<String, Object> response = assetAllocationService.getAssetHistoryReport(assetId, startDate, endDate, month, year, page, size, keyword);
	     return ResponseEntity.ok(response);
	 }

	 
	 @GetMapping("/getAssetHistoryReportWithCount")
	 public ResponseEntity<Map<String, Long>> getAssetHistoryReportCount(
	         @RequestParam(required = false) Integer assetId,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
	         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	         @RequestParam(required = false) Integer month,
	         @RequestParam(required = false) Integer year) {

	     Map<String, Long> countMap = assetAllocationService.getAssetHistoryReportCount(assetId, startDate, endDate, month, year);
	     return ResponseEntity.ok(countMap);
	 }



	 //////////////////////////////////////////////////////////





@GetMapping("/getAssetAllocationHistoryyByLimit")
public @ResponseBody List<AssetAllocationHistory> getAssetAllocationHistoryByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<AssetAllocationHistory> list = new ArrayList<AssetAllocationHistory>();
	try {
		list = assetAllocationHistoryRepo.getAssetAllocationHistoryByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geAssetAllocationHistoryyCount")
public @ResponseBody int getAssetAllocationHistoryCount() {
	int count = 0;
	try {
			count = (int) assetAllocationHistoryRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getAssetAllocationHistoryyByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<AssetAllocationHistory> getBreakdownByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<AssetAllocationHistory> list = new ArrayList<AssetAllocationHistory>();
	try {
		
		list = assetAllocationHistoryRepo.getAssetAllocationHistoryByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getAssetAllocationHistoryyCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getAssetAllocationHistoryRepoCustomCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = assetAllocationHistoryRepo.getAssetAllocationHistoryCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}




}
