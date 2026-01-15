package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.AssetAllocation;

import com.kfMaintenancce.model.SpareConsumption;

public class SpareUtilizationReportDTO {
	
	 private SpareConsumption spareConsumption;
	    private double utilizedQuantity;
	 
	    private List<AssetAllocation> assetAllocation;
	    
	    
	    public SpareUtilizationReportDTO(SpareConsumption spareConsumption, double utilizedQuantity,
				
				List<AssetAllocation> assetAllocation) {
			
			this.spareConsumption = spareConsumption;
			this.utilizedQuantity = utilizedQuantity;
	
			this.assetAllocation = assetAllocation;
		}
		public SpareUtilizationReportDTO() {
			
			
		}
	    
	    
	    
		public SpareConsumption getSpareConsumption() {
			return spareConsumption;
		}
		public void setSpareConsumption(SpareConsumption spareConsumption) {
			this.spareConsumption = spareConsumption;
		}
		public double getUtilizedQuantity() {
			return utilizedQuantity;
		}
		public void setUtilizedQuantity(double utilizedQuantity) {
			this.utilizedQuantity = utilizedQuantity;
		}
//		public Complaint getComplaint() {
//			return complaint;
//		}
//		public void setComplaint(Complaint complaint) {
//			this.complaint = complaint;
//		}
		public List<AssetAllocation> getAssetAllocation() {
			return assetAllocation;
		}
		public void setAssetAllocation(List<AssetAllocation> assetAllocation2) {
			this.assetAllocation = assetAllocation2;
		}
		
	    
	    
	    

}
