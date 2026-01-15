package com.kfMaintenancce.dto;

import java.util.Date;

public class MaintenanceDetailsDTO {

    private int maintId;
    private Date scheduleDate;
    private Date closedDate;
    private String frequency;
    private int week;
    private String raisedBy;
    private String doneBy;
    private String statusCode;
    private String machineName;
    private String machineEqId;
    private String machineLocation;

    // Constructor should match the fields returned by the query
    public MaintenanceDetailsDTO(int maintId, Date scheduleDate, Date closedDate, String frequency,
                                 int week, String raisedBy, String doneBy, String statusCode,
                                 String machineName, String machineEqId, String machineLocation) {
        this.maintId = maintId;
        this.scheduleDate = scheduleDate;
        this.closedDate = closedDate;
        this.frequency = frequency;
        this.week = week;
        this.raisedBy = raisedBy;
        this.doneBy = doneBy;
        this.statusCode = statusCode;
        this.machineName = machineName;
        this.machineEqId = machineEqId;
        this.machineLocation = machineLocation;
    }

    // Getters and Setters for each field
    public int getMaintId() {
        return maintId;
    }

    public void setMaintId(int maintId) {
        this.maintId = maintId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineEqId() {
        return machineEqId;
    }

    public void setMachineEqId(String machineEqId) {
        this.machineEqId = machineEqId;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

	public MaintenanceDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
