package com.kfMaintenancce.dto;

import java.util.Date;

public class SpareConsumptionReportDTO {

	
	  private String spareName;
	    private String breakdownSlipNo;
	    private String machineName;
	    private String machineEquipmentId;
	    private Date breakdownRaisedDate;
	    private Date spareConsumptionDate;
	    private String shiftName;
	    private String rootCause;
	    private String actionPlan;
	    private Double consumedQuantity; 
	    private Double availableQuantity; 
	    
	    
	    
	  
		public Date getSpareConsumptionDate() {
			return spareConsumptionDate;
		}
		public void setSpareConsumptionDate(Date spareConsumptionDate) {
			this.spareConsumptionDate = spareConsumptionDate;
		}
		public Double getAvailableQuantity() {
			return availableQuantity;
		}
		public void setAvailableQuantity(Double availableQuantity) {
			this.availableQuantity = availableQuantity;
		}
		public Double getConsumedQuantity() {
			return consumedQuantity;
		}
		public void setConsumedQuantity(Double consumedQuantity) {
			this.consumedQuantity = consumedQuantity;
		}
		public String getSpareName() {
			return spareName;
		}
		public void setSpareName(String spareName) {
			this.spareName = spareName;
		}
		public String getBreakdownSlipNo() {
			return breakdownSlipNo;
		}
		public void setBreakdownSlipNo(String breakdownSlipNo) {
			this.breakdownSlipNo = breakdownSlipNo;
		}
		public String getMachineName() {
			return machineName;
		}
		public void setMachineName(String machineName) {
			this.machineName = machineName;
		}
		public String getMachineEquipmentId() {
			return machineEquipmentId;
		}
		public void setMachineEquipmentId(String machineEquipmentId) {
			this.machineEquipmentId = machineEquipmentId;
		}
		public Date getBreakdownRaisedDate() {
			return breakdownRaisedDate;
		}
		public void setBreakdownRaisedDate(Date breakdownRaisedDate) {
			this.breakdownRaisedDate = breakdownRaisedDate;
		}
		public String getShiftName() {
			return shiftName;
		}
		public void setShiftName(String shiftName) {
			this.shiftName = shiftName;
		}
		public String getRootCause() {
			return rootCause;
		}
		public void setRootCause(String rootCause) {
			this.rootCause = rootCause;
		}
		public String getActionPlan() {
			return actionPlan;
		}
		public void setActionPlan(String actionPlan) {
			this.actionPlan = actionPlan;
		}
		
		
		
		public SpareConsumptionReportDTO() {
					}
		public SpareConsumptionReportDTO(String spareName, String breakdownSlipNo, String machineName,
                String machineEquipmentId, Date breakdownRaisedDate, Date spareConsumptionDate, 
                String shiftName, String rootCause, String actionPlan, 
				                Double consumedQuantity, Double availableQuantity) {
				this.spareName = spareName;
				this.breakdownSlipNo = breakdownSlipNo;
				this.machineName = machineName;
				this.machineEquipmentId = machineEquipmentId;
				this.breakdownRaisedDate = breakdownRaisedDate;
				this.spareConsumptionDate = spareConsumptionDate;
				this.shiftName = shiftName;
				this.rootCause = rootCause;
				this.actionPlan = actionPlan;
				this.consumedQuantity = consumedQuantity;
				this.availableQuantity = availableQuantity;
				}

		
		
	    
	    
	    
	    
	    
	    
	    
	    
	    
	
}
