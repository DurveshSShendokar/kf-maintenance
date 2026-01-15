package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;

public interface AssetInventoryCustomeRepo {
	List<AssetInventory> getAssetInventoryByLimit(int pageNo, int perPage);
	List<AssetInventory> getAssetInventoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	

}
