package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.SpareStock;

public interface SpareStockRepoCustom {
	
	List<SpareStock> getSpareStockByLimit(int pageNo, int perPage);
	List<SpareStock> getSpareStockByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getSpareStockCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
