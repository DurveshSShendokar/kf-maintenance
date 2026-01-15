package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;


public interface AssetInventoryService {

	void addAssetInventory(AssetInventory assetInventory);

	List<AssetInventory> getAssetInventoryByLimit(int pageNo, int perPage);

	int count();

	List<AssetInventory> getAssetInventoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	public void deleteassetInventory(int asset_inventory_id);
	 public AssetInventory findById(int asset_inventory_id);
	 public List<AssetInventory> getAllAssets();
	 public boolean processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException;
	 public byte[] generateExcelFile() throws IOException ;
	 public List<AssetInventory> getAssetsByModel(String model);
	 public List<AssetInventory> getNonAllocatedAssets();
	 public byte[] generateExcelTemplate() throws IOException ;
}
