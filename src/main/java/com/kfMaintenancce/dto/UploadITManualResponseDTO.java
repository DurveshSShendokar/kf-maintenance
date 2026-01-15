package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Upload_IT_Manual;

import java.util.List;

public class UploadITManualResponseDTO {
    private int assetInventoryId;
    private String equipmentId;
    private List<Upload_IT_Manual> uploadITManuals;

    public UploadITManualResponseDTO(int assetInventoryId, String equipmentId, List<Upload_IT_Manual> uploadITManuals) {
        this.assetInventoryId = assetInventoryId;
        this.equipmentId = equipmentId;
        this.uploadITManuals = uploadITManuals;
    }

    public int getAssetInventoryId() {
        return assetInventoryId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public List<Upload_IT_Manual> getUploadITManuals() {
        return uploadITManuals;
    }

	public void setAssetInventoryId(int assetInventoryId) {
		this.assetInventoryId = assetInventoryId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setUploadITManuals(List<Upload_IT_Manual> uploadITManuals) {
		this.uploadITManuals = uploadITManuals;
	}
}
