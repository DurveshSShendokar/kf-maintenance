package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.MaintSpareStock;

public interface MaintSpareStockServices {
	
	public List<MaintSpareStock> getAllSpareStocks();
	 public MaintSpareStock saveSpareStock(MaintSpareStock spareStock);
	// public MaintSpareStock updateSpareStock(int id, MaintSpareStock updatedStock);
	  public void deleteSpareStock(int id);

}
