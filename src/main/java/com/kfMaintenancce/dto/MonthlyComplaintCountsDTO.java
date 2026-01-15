package com.kfMaintenancce.dto;

public class MonthlyComplaintCountsDTO {
    private String month;
    private int openCount;
    private int closedCount;
    private int inprocessCount;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
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
	public MonthlyComplaintCountsDTO(String month, int openCount, int closedCount) {
		super();
		this.month = month;
		this.openCount = openCount;
		this.closedCount = closedCount;
	}
	public MonthlyComplaintCountsDTO() {
		
		// TODO Auto-generated constructor stub
	}
	public int getInprocessCount() {
		return inprocessCount;
	}
	public void setInprocessCount(int inprocessCount) {
		this.inprocessCount = inprocessCount;
	}

    // Constructors, getters, and setters
    
    
}

