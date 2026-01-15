package com.kfMaintenancce.dto;

public class MaintenanceCountsByMachineDTO {
    private String machineName;
    private String date;
    private int open;
    private int closed;
    private int overdue;

    // Constructors, getters, and setters

    public MaintenanceCountsByMachineDTO() {
    }

    public MaintenanceCountsByMachineDTO(String machineName, int open, int closed) {
        this.machineName = machineName;
        this.open = open;
        this.closed = closed;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getOverdue() {
		return overdue;
	}

	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}
    
    
}
