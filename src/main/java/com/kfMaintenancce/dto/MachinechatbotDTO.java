package com.kfMaintenancce.dto;

public class MachinechatbotDTO {

    private String machineName;
    private String labName;
    private String categoryName;
    private String eqid;
    private String location;

    // Constructor matching the query
    public MachinechatbotDTO(String machineName, String labName, String categoryName, String eqid, String location) {
        this.machineName = machineName;
        this.labName = labName;
        this.categoryName = categoryName;
        this.eqid = eqid;
        this.location = location;
    }

    // Getters & Setters
    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }

    public String getLabName() { return labName; }
    public void setLabName(String labName) { this.labName = labName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getEqid() { return eqid; }
    public void setEqid(String eqid) { this.eqid = eqid; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}