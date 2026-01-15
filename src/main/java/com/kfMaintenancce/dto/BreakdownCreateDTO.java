package com.kfMaintenancce.dto;
public class BreakdownCreateDTO {

    private String machineEqid;
    private String userEmailId;
    private String observation;

    // Getters and Setters
    public String getMachineEqid() {
        return machineEqid;
    }

    public void setMachineEqid(String machineEqid) {
        this.machineEqid = machineEqid;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
