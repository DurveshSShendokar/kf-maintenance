package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;

public interface AssetAllocationRepoCustom {
	
	List<AssetAllocation> getAssetAllocationByLimit(int pageNo, int perPage);
	List<AssetAllocation> getAssetAllocationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getAssetAllocationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
