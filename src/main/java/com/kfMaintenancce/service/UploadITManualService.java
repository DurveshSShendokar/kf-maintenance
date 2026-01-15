package com.kfMaintenancce.service;

import java.util.List;


import com.kfMaintenancce.model.Upload_IT_Manual;

public interface UploadITManualService {
	
	 public List<Upload_IT_Manual> getActiveManualsByAssetInventoryId(int assetInventoryId);

}
