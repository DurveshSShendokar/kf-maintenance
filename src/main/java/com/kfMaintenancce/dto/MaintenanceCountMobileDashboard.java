package com.kfMaintenancce.dto;

public class MaintenanceCountMobileDashboard {
	private int thisWeekcount;
	private int todayCount;
	private int doneCount;
	   private int overdueCount; 
	
	
	public int getThisWeekcount() {
		return thisWeekcount;
	}
	public void setThisWeekcount(int thisWeekcount) {
		this.thisWeekcount = thisWeekcount;
	}
	public int getTodayCount() {
		return todayCount;
	}
	public void setTodayCount(int todayCount) {
		this.todayCount = todayCount;
	}
	public int getDoneCount() {
		return doneCount;
	}
	public void setDoneCount(int doneCount) {
		this.doneCount = doneCount;
	}
	public int getOverdueCount() {
		return overdueCount;
	}
	public void setOverdueCount(int overdueCount) {
		this.overdueCount = overdueCount;
	}
	
	
	

}
