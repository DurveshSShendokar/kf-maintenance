package com.kfMaintenancce.dto;

public class BreakdownCounts {
    private int openCount;
    private int closedCount;
    private int trialCount;
	public int getOpenCount() {
		return openCount;
	}
	public void setOpenCount(int openCount) {
		this.openCount = openCount;
	}
	public int getClosedCount() {
		return closedCount;
	}
	public void setClosedCount(int closedCount) {
		this.closedCount = closedCount;
	}
	public int getTrialCount() {
		return trialCount;
	}
	public void setTrialCount(int trialCount) {
		this.trialCount = trialCount;
	}
	public BreakdownCounts(int openCount, int closedCount, int trialCount) {
		
		this.openCount = openCount;
		this.closedCount = closedCount;
		this.trialCount = trialCount;
	}
	public BreakdownCounts() {
		
		// TODO Auto-generated constructor stub
	}

    // Constructors, getters, and setters
    
}
