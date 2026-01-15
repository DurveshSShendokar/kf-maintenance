package com.kfMaintenancce.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.AssetSpareAssign;

import com.kfMaintenancce.repo.AssetSpareAssignRepository;


@Service
public class AssetSpareAssignServiceImpl  implements AssetSpareAssignService{

	
	
	 @Autowired
	    private AssetSpareAssignRepository assetSpareAssignRepository;


    public void addSpareAssign(AssetSpareAssign spareAssign) {
    	assetSpareAssignRepository.save(spareAssign);
    }	 
   
    public void deleteAssetSpareAssignById(int id) {
        if (assetSpareAssignRepository.existsById(id)) {
            assetSpareAssignRepository.deleteById(id);
        } else {
            throw new RuntimeException("AssetSpareAssign with ID " + id + " not found");
        }
    }



}
