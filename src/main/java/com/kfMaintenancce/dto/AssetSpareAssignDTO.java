package com.kfMaintenancce.dto;

public class AssetSpareAssignDTO {
	 private int id;
     private int assetInventoryId;
     private int spareId;
     private String equipmentId;
     private String spareName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAssetInventoryId() {
		return assetInventoryId;
	}
	public void setAssetInventoryId(int assetInventoryId) {
		this.assetInventoryId = assetInventoryId;
	}
	public int getSpareId() {
		return spareId;
	}
	public void setSpareId(int spareId) {
		this.spareId = spareId;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}

     

}
