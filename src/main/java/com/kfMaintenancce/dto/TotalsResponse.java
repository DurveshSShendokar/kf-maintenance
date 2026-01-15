package com.kfMaintenancce.dto;

public class TotalsResponse {
    
    private long totalMachines;
    private long totalUsers;

    public TotalsResponse() {
        
    }

	public long getTotalMachines() {
		return totalMachines;
	}

	public void setTotalMachines(long totalMachines) {
		this.totalMachines = totalMachines;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public TotalsResponse(long totalMachines, long totalUsers) {
		super();
		this.totalMachines = totalMachines;
		this.totalUsers = totalUsers;
	}

    
}
