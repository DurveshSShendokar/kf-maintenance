package com.kfMaintenancce.dto;
//BreakdownCountsByMachineDTO
public class BreakdownCountsByMachineDTO {
    private String machineName;
    private int open;
    private int closed;
    private int trail;

    // Constructors, getters, and setters

    public BreakdownCountsByMachineDTO() {
    }

    public BreakdownCountsByMachineDTO(String machineName, int open, int closed, int trail) {
        this.machineName = machineName;
        this.open = open;
        this.closed = closed;
        this.trail = trail;
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

    public int getTrail() {
        return trail;
    }

    public void setTrail(int trail) {
        this.trail = trail;
    }
}
