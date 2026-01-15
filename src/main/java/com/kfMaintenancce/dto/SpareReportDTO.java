package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Spare;

public class SpareReportDTO {

	// private String spareName;
	    private Spare spare;   
	    private double availableQuantity;
	    private double utilizedQuantity;
	    private double totalQuantity;
	  private List<UtilizationDetail> utilizationDetails; 
	    
  
	    
	    
		public List<UtilizationDetail> getUtilizationDetails() {
			return utilizationDetails;
		}
		public void setUtilizationDetails(List<UtilizationDetail> utilizationDetails) {
			this.utilizationDetails = utilizationDetails;
		}
		public double getAvailableQuantity() {
			return availableQuantity;
		}
		public Spare getSpare() {
			return spare;
		}
		public void setSpare(Spare spare) {
			this.spare = spare;
		}
		public void setAvailableQuantity(double availableQuantity) {
			this.availableQuantity = availableQuantity;
		}
		public double getUtilizedQuantity() {
			return utilizedQuantity;
		}
		public void setUtilizedQuantity(double utilizedQuantity) {
			this.utilizedQuantity = utilizedQuantity;
		}
		public double getTotalQuantity() {
			return totalQuantity;
		}
		public void setTotalQuantity(double totalQuantity) {
			this.totalQuantity = totalQuantity;
		}
	    
}
