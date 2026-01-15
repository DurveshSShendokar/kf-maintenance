package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.repo.SpareStockRepo;

@Service
public class SpareStockServiceImpl implements SpareStockService {
	
	@Autowired
	SpareStockRepo spareStockRepo;	

	public void addSpareStock(SpareStock spareStock) {
		// TODO Auto-generated method stub
		spareStockRepo.save(spareStock);
	}

	
	public List<SpareStock> getSpareStockByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return spareStockRepo.getSpareStockByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) spareStockRepo.count();
	}

	
	public List<SpareStock> getSpareStockByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return spareStockRepo.getSpareStockByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getSpareStockCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return spareStockRepo.getSpareStockCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	 public void deleteSpareStock(int spare_stock_id) {
		 spareStockRepo.deleteById(spare_stock_id);
	    }
	 

	 public SpareStock updateSpareStock(int spare_stock_id, SpareStock updatedSpareStock) {
	     Optional<SpareStock> optionalSpareStock = spareStockRepo.findById(spare_stock_id);
	     if (optionalSpareStock.isPresent()) {
	         SpareStock existingSpareStock = optionalSpareStock.get();
	         // Update the existing spare stock with the new values
	       //  existingSpareStock.setStock(updatedSpareStock.getStock());
	        //// existingSpareStock.setQuantity(updatedSpareStock.getQuantity());
	       //  existingSpareStock.setUnitPrice(updatedSpareStock.getUnitPrice());
	      //   existingSpareStock.setTotalPrice(updatedSpareStock.getQuantity() * updatedSpareStock.getUnitPrice());

	         return spareStockRepo.save(existingSpareStock);
	     } else {
	         throw new RuntimeException("Spare stock with ID " + spare_stock_id + " not found.");
	     }
	 }
	 
	 public List<SpareStock> getAllspares() {
	        return spareStockRepo.findAll();
	    } 


}
