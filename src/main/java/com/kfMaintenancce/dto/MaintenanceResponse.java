package com.kfMaintenancce.dto;

public class MaintenanceResponse {
    private int totalMaintCount;
    private int activeMaintCount;
    private int inactiveMaintCount;
    private int todayCount;

    public MaintenanceResponse(int totalMaintCount, int activeMaintCount, int inactiveMaintCount, int todayCount ) {
        this.totalMaintCount = totalMaintCount;
        this.activeMaintCount = activeMaintCount;
        this.inactiveMaintCount = inactiveMaintCount;
        this.todayCount= todayCount;   
        }

	public int getTotalMaintCount() {
		return totalMaintCount;
	}

	public void setTotalMaintCount(int totalMaintCount) {
		this.totalMaintCount = totalMaintCount;
	}

	public int getActiveMaintCount() {
		return activeMaintCount;
	}

	public void setActiveMaintCount(int activeMaintCount) {
		this.activeMaintCount = activeMaintCount;
	}

	public int getInactiveMaintCount() {
		return inactiveMaintCount;
	}

	public void setInactiveMaintCount(int inactiveMaintCount) {
		this.inactiveMaintCount = inactiveMaintCount;
	}

	public int getTodayCount() {
		return todayCount;
	}

	public void setTodayCount(int todayCount) {
		this.todayCount = todayCount;
	}

    // Getters and setters
}
