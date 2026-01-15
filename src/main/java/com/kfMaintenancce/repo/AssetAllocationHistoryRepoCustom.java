package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetAllocationHistory;

public interface AssetAllocationHistoryRepoCustom {
	
	List<AssetAllocationHistory> getAssetAllocationHistoryByLimit(int pageNo, int perPage);
	List<AssetAllocationHistory> getAssetAllocationHistoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getAssetAllocationHistoryCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
