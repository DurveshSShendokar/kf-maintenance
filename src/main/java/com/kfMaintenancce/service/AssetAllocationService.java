package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;

public interface AssetAllocationService {

	void addAssetAllocation(AssetAllocation assetAllocation);
	public boolean deallocateAsset(int allocationId);
	public List<AssetAllocation> getActiveAllocations();
	
	public boolean reallocateAsset(int historyId, int userId);
	
	public List<AssetAllocation> getAllocationHistoryByAsset(int assetId);
	
	public List<AssetAllocation> getAllocationHistory();

	public long countDeAllocatedAssets();

	List<AssetAllocation> getAssetAllocationByLimit(int pageNo, int perPage);

	int count();
	
	 public Map<String, Object> getAssetHistoryReport(Integer assetId, Date startDate, Date endDate,
             Integer month, Integer year, int page, int size, String keyword);
	 
	 public Map<String, Long> getAssetHistoryReportCount(Integer assetId, Date startDate, Date endDate,
             Integer month, Integer year) ;

	List<AssetAllocation> getAssetAllocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getAssetAllocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteAssetAllocation(int asset_allocation_id);
	 public AssetAllocation updateAssetAllocation(int asset_allocation_id, AssetAllocation updatedAssetAllocation);
	 public List<AssetAllocation> getAllAssetAllocations() ;

}
