package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.SpareStock;

public interface SpareStockService {

	void addSpareStock(SpareStock spareStock);

	List<SpareStock> getSpareStockByLimit(int pageNo, int perPage);

	int count();

	List<SpareStock> getSpareStockByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getSpareStockCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteSpareStock(int spare_stock_id);
	 public SpareStock updateSpareStock(int spare_stock_id, SpareStock updatedSpareStock);
	 public List<SpareStock> getAllspares();

}
