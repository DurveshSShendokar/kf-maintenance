package com.kfMaintenancce.dto;

public class TotalsResponse2 {
    
    private long totalComplaints;
    private long totalAssets;

    public TotalsResponse2() {
        
    }

	public long getTotalComplaints() {
		return totalComplaints;
	}

	public void setTotalComplaints(long totalComplaints) {
		this.totalComplaints = totalComplaints;
	}

	public long getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(long totalAssets) {
		this.totalAssets = totalAssets;
	}

	public TotalsResponse2(long totalComplaints, long totalAssets) {
		super();
		this.totalComplaints = totalComplaints;
		this.totalAssets = totalAssets;
	}

	
    
}
