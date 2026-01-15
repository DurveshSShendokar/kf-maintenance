package com.kfMaintenancce.dto;
public class MaintDashboardDTO {
    private String scheduleDate;
    private String closedDate;
    private String machineEqid;
    private String labName;

    public MaintDashboardDTO(String scheduleDate, String closedDate, String machineEqid, String labName) {
        this.scheduleDate = scheduleDate;
        this.closedDate = closedDate;
        this.machineEqid = machineEqid;
        this.labName = labName;
    }

    // Getters and Setters
    public String getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(String scheduleDate) { this.scheduleDate = scheduleDate; }
    public String getClosedDate() { return closedDate; }
    public void setClosedDate(String closedDate) { this.closedDate = closedDate; }
    public String getMachineEqid() { return machineEqid; }
    public void setMachineEqid(String machineEqid) { this.machineEqid = machineEqid; }
    public String getLabName() { return labName; }
    public void setLabName(String labName) { this.labName = labName; }
}
