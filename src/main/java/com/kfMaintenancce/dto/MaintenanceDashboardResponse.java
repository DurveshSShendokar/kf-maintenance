package com.kfMaintenancce.dto;

import java.util.List;

public class MaintenanceDashboardResponse {
    private int todayCount;
    private int thisWeekCount;
    private int doneCount;
    private int overdueCount;

    private List<MaintDashboardDTO> todayRecords;
    private List<MaintDashboardDTO> thisWeekRecords;
    private List<MaintDashboardDTO> doneRecords;
    private List<MaintDashboardDTO> overdueRecords;
	public int getTodayCount() {
		return todayCount;
	}
	public void setTodayCount(int todayCount) {
		this.todayCount = todayCount;
	}
	public int getThisWeekCount() {
		return thisWeekCount;
	}
	public void setThisWeekCount(int thisWeekCount) {
		this.thisWeekCount = thisWeekCount;
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
	public List<MaintDashboardDTO> getTodayRecords() {
		return todayRecords;
	}
	public void setTodayRecords(List<MaintDashboardDTO> todayRecords) {
		this.todayRecords = todayRecords;
	}
	public List<MaintDashboardDTO> getThisWeekRecords() {
		return thisWeekRecords;
	}
	public void setThisWeekRecords(List<MaintDashboardDTO> thisWeekRecords) {
		this.thisWeekRecords = thisWeekRecords;
	}
	public List<MaintDashboardDTO> getDoneRecords() {
		return doneRecords;
	}
	public void setDoneRecords(List<MaintDashboardDTO> doneRecords) {
		this.doneRecords = doneRecords;
	}
	public List<MaintDashboardDTO> getOverdueRecords() {
		return overdueRecords;
	}
	public void setOverdueRecords(List<MaintDashboardDTO> overdueRecords) {
		this.overdueRecords = overdueRecords;
	}
	public MaintenanceDashboardResponse(int todayCount, int thisWeekCount, int doneCount, int overdueCount,
			List<MaintDashboardDTO> todayRecords, List<MaintDashboardDTO> thisWeekRecords,
			List<MaintDashboardDTO> doneRecords, List<MaintDashboardDTO> overdueRecords) {
		super();
		this.todayCount = todayCount;
		this.thisWeekCount = thisWeekCount;
		this.doneCount = doneCount;
		this.overdueCount = overdueCount;
		this.todayRecords = todayRecords;
		this.thisWeekRecords = thisWeekRecords;
		this.doneRecords = doneRecords;
		this.overdueRecords = overdueRecords;
	}
	public MaintenanceDashboardResponse() {
		
	}

    // Getters and Setters
    // ...
    
    
}
