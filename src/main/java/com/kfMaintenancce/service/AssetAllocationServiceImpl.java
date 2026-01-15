package com.kfMaintenancce.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetAllocationHistory;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.AssetAllocationHistoryRepo;
import com.kfMaintenancce.repo.AssetAllocationRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;

@Service
public class AssetAllocationServiceImpl implements AssetAllocationService {
	
	@Autowired
	AssetAllocationRepo assetAllocationRepo;	
	
	@Autowired 
	AssetAllocationHistoryRepo assetAllocationHistoryRepo;
	
	@Autowired 
	UserDetailsRepo userDetailsRepo;
	
	 @Autowired
	    private AssetInventoryService assetInventoryService;

	public void addAssetAllocation(AssetAllocation assetAllocation) {
		
		assetAllocation.setAllocationDate(new Date());
		assetAllocation.setStatus("ALLOCATED");		
		assetAllocationRepo.save(assetAllocation);
	}
	
	@Transactional
	public boolean reallocateAsset(int historyId, int userId) {
	    Optional<AssetAllocationHistory> historyOpt = assetAllocationHistoryRepo.findById(historyId);
	    Optional<UserDetails> userOpt = userDetailsRepo.findById(userId);

	    if (historyOpt.isPresent() && userOpt.isPresent()) {
	        AssetAllocationHistory history = historyOpt.get();
	        UserDetails newUser = userOpt.get();

	        // Create new allocation
	        AssetAllocation newAllocation = new AssetAllocation();
	    //    newAllocation.setAllocationBy("SYSTEM/ADMIN"); // or logged-in user
	        newAllocation.setAllocationDate(new Date());
	        newAllocation.setAllocateTo(newUser);
	        newAllocation.setAssetInventory(history.getAssetInventory());
	        newAllocation.setStatus("ALLOCATED");

	        assetAllocationRepo.save(newAllocation);

	        // Optional: update history to note it was reallocated
	        history.setStatus("REALLOCATED");
	        assetAllocationHistoryRepo.save(history);

	        return true;
	    }
	    return false;
	}

	
	
	@Transactional
	public boolean deallocateAsset(int allocationId) {
	    Optional<AssetAllocation> allocationOpt = assetAllocationRepo.findById(allocationId);
	    if (allocationOpt.isPresent()) {
	        AssetAllocation allocation = allocationOpt.get();

	        // Create history record
	        AssetAllocationHistory history = new AssetAllocationHistory();
	        history.setAllocationBy(allocation.getAllocationBy());
	        history.setAllocationDate(allocation.getAllocationDate());
	        history.setAllocateTo(allocation.getAllocateTo());
	        history.setAssetInventory(allocation.getAssetInventory());
	        history.setDeallocationDate(new Date());
	     //   history.setDeallocationBy("SYSTEM/ADMIN"); // set logged in user here
	        history.setStatus("DEALLOCATED");

	        assetAllocationHistoryRepo.save(history);

	        // Remove from active allocations
	        assetAllocationRepo.delete(allocation);

	        return true;
	    }
	    return false;
	}


public List<AssetAllocation> getActiveAllocations() {
    return assetAllocationRepo.findByStatus("ALLOCATED");
}

public long countDeAllocatedAssets() {
    return assetAllocationRepo.countDeAllocatedAssets();
}



public List<AssetAllocation> getAllocationHistory() {
    return assetAllocationRepo.findAll();
}

public List<AssetAllocation> getAllocationHistoryByAsset(int assetId) {
    return assetAllocationRepo.findByAssetInventoryId(assetId);}
	
	public List<AssetAllocation> getAssetAllocationByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return assetAllocationRepo.getAssetAllocationByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) assetAllocationRepo.count();
	}

	
	public List<AssetAllocation> getAssetAllocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return assetAllocationRepo.getAssetAllocationByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getAssetAllocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return assetAllocationRepo.getAssetAllocationCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	 public void deleteAssetAllocation(int asset_allocation_id) {
		 assetAllocationRepo.deleteById(asset_allocation_id);
	    }

	 
	 
	 public AssetAllocation updateAssetAllocation(int asset_allocation_id, AssetAllocation updatedAssetAllocation) {
	     Optional<AssetAllocation> optionalAssetAllocation = assetAllocationRepo.findById(asset_allocation_id);
	     if (optionalAssetAllocation.isPresent()) {
	    	 AssetAllocation existingAssetAllocation = optionalAssetAllocation.get();
	         // Update the existing AssetAllocation with the new values
	    	 existingAssetAllocation.setAllocationBy(updatedAssetAllocation.getAllocationBy());
	    	 existingAssetAllocation.setAllocationDate(updatedAssetAllocation.getAllocationDate());
	    	 existingAssetAllocation.setAllocateTo(updatedAssetAllocation.getAllocateTo());
	    	 existingAssetAllocation.setAssetInventory(updatedAssetAllocation.getAssetInventory());

	         return assetAllocationRepo.save(existingAssetAllocation);
	     } else {
	         throw new RuntimeException("AssetAllocation with ID " + asset_allocation_id + " not found.");
	     }
	 }
	 
	 
	 public List<AssetAllocation> getAllAssetAllocations() {
	        return assetAllocationRepo.findAll();
	    } 
	 
	 
	 
	 public Map<String, Object> getAssetHistoryReport(Integer assetId, Date startDate, Date endDate,
             Integer month, Integer year, int page, int size, String keyword) {
				
				Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "allocationDate"));
				Page<AssetAllocation> activePage;
				Page<AssetAllocationHistory> historyPage;
				
				// 🔹 Build Date Range from month/year if provided
				if (month != null && year != null) {
				LocalDate start = LocalDate.of(year, month, 1);
				LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
				startDate = java.sql.Date.valueOf(start);
				endDate = java.sql.Date.valueOf(end);
				}
				
				if (startDate == null) startDate = new Date(0);
				if (endDate == null) endDate = new Date();
				
				if (keyword != null && !keyword.isEmpty()) {
				// 🔍 Search + Pagination
				if (assetId != null) {
				activePage = assetAllocationHistoryRepo.searchActiveByAssetAndDateRange(assetId, startDate, endDate, keyword, pageable);
				historyPage = assetAllocationHistoryRepo.searchHistoryByAssetAndDateRange(assetId, startDate, endDate, keyword, pageable);
				} else {
				activePage = assetAllocationHistoryRepo.searchActiveByDateRange(startDate, endDate, keyword, pageable);
				historyPage = assetAllocationHistoryRepo.searchHistoryByDateRange(startDate, endDate, keyword, pageable);
				}
				} else {
				// 🔹 Simple Pagination
				if (assetId != null) {
				activePage = assetAllocationHistoryRepo.findActiveByAssetAndDateRange(assetId, startDate, endDate, pageable);
				historyPage = assetAllocationHistoryRepo.findHistoryByAssetAndDateRange(assetId, startDate, endDate, pageable);
				} else {
				activePage = assetAllocationHistoryRepo.findActiveByDateRange(startDate, endDate, pageable);
				historyPage = assetAllocationHistoryRepo.findHistoryByDateRange(startDate, endDate, pageable);
				}
				}
				
				Map<String, Object> response = new HashMap<>();
				response.put("activeAllocations", activePage.getContent());
				response.put("historyAllocations", historyPage.getContent());
				response.put("currentPage", activePage.getNumber());
				response.put("totalItems", activePage.getTotalElements() + historyPage.getTotalElements());
				response.put("totalPages", Math.max(activePage.getTotalPages(), historyPage.getTotalPages()));
				
				return response;
				}
	 
	 
	 
	 public Map<String, Long> getAssetHistoryReportCount(Integer assetId, Date startDate, Date endDate,
				             Integer month, Integer year) {
				if (month != null && year != null) {
				LocalDate start = LocalDate.of(year, month, 1);
				LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
				startDate = java.sql.Date.valueOf(start);
				endDate = java.sql.Date.valueOf(end);
				}
				
				if (startDate == null) startDate = new Date(0);
				if (endDate == null) endDate = new Date();
				
				long activeCount;
				long historyCount;
				
				if (assetId != null) {
				activeCount = assetAllocationHistoryRepo.countActiveByAssetAndDateRange(assetId, startDate, endDate);
				historyCount = assetAllocationHistoryRepo.countHistoryByAssetAndDateRange(assetId, startDate, endDate);
				} else {
				activeCount = assetAllocationHistoryRepo.countActiveByDateRange(startDate, endDate);
				historyCount = assetAllocationHistoryRepo.countHistoryByDateRange(startDate, endDate);
				}
				
				Map<String, Long> map = new HashMap<>();
				map.put("activeCount", activeCount);
				map.put("historyCount", historyCount);
				map.put("totalCount", activeCount + historyCount);
				
				return map;
				}
				
					 
	 
	 

}
