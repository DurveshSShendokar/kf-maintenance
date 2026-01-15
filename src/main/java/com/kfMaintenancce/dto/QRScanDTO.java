package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;

public class QRScanDTO {
	private AssetInventory assetInventory;
	
	private List<Complaint> openComplaints;
	private List<Complaint> allocatedComplaints;
	private List<Complaint> inProcessComplaints;
	private List<Complaint> CloedComplaints;
	public AssetInventory getAssetInventory() {
		return assetInventory;
	}
	public void setAssetInventory(AssetInventory assetInventory) {
		this.assetInventory = assetInventory;
	}
	public List<Complaint> getOpenComplaints() {
		return openComplaints;
	}
	public void setOpenComplaints(List<Complaint> openComplaints) {
		this.openComplaints = openComplaints;
	}
	public List<Complaint> getAllocatedComplaints() {
		return allocatedComplaints;
	}
	public void setAllocatedComplaints(List<Complaint> allocatedComplaints) {
		this.allocatedComplaints = allocatedComplaints;
	}
	public List<Complaint> getInProcessComplaints() {
		return inProcessComplaints;
	}
	public void setInProcessComplaints(List<Complaint> inProcessComplaints) {
		this.inProcessComplaints = inProcessComplaints;
	}
	public List<Complaint> getCloedComplaints() {
		return CloedComplaints;
	}
	public void setCloedComplaints(List<Complaint> cloedComplaints) {
		CloedComplaints = cloedComplaints;
	}
	
	
	

}
