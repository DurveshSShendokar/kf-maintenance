package com.kfMaintenancce.service;

import com.kfMaintenancce.dto.UploadITManualResponseDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Upload_IT_Manual;
import com.kfMaintenancce.repo.AssetInventoryRepo; // Ensure you have this repository
import com.kfMaintenancce.repo.uploadITManualRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadITManualServiceImpl implements UploadITManualService {
	
	
	 @Autowired
	    private AssetInventoryRepo assetInventoryRepository; 
	 

	 @Autowired
	    private uploadITManualRepo uploadITManualRepository;

	 public List<Upload_IT_Manual> getActiveManualsByAssetInventoryId(int assetInventoryId) {
	        return uploadITManualRepository.findActiveManualsByAssetInventoryId(assetInventoryId);
	    }

}
