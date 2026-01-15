package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.MaintSpareStock;

public class BreakdownSpareReport {
	
	   private Breakdown breakdown; 
	    private int spareId;
	    private String spareName;
	    private MaintSpareStock maintSpareStock; 
	    
		public Breakdown getBreakdown() {
			return breakdown;
		}
		public void setBreakdown(Breakdown breakdown) {
			this.breakdown = breakdown;
		}
		public int getSpareId() {
			return spareId;
		}
		public void setSpareId(int spareId) {
			this.spareId = spareId;
		}
		public String getSpareName() {
			return spareName;
		}
		public void setSpareName(String spareName) {
			this.spareName = spareName;
		}
		
		public MaintSpareStock getMaintSpareStock() {
			return maintSpareStock;
		}
		public void setMaintSpareStock(MaintSpareStock maintSpareStock) {
			this.maintSpareStock = maintSpareStock;
		}
		public BreakdownSpareReport() {
			
		}
		public BreakdownSpareReport(Breakdown breakdown, int spareId, String spareName,
				MaintSpareStock maintSpareStock) {
			super();
			this.breakdown = breakdown;
			this.spareId = spareId;
			this.spareName = spareName;
			this.maintSpareStock = maintSpareStock;
		}
	    
	    
	    
	    

}
