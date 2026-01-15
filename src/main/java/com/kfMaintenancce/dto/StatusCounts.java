package com.kfMaintenancce.dto;

public class StatusCounts {
    private int openCount;
    private int trialCount;
    private int closedCount;
    private int totalBreakdowns; // Add this field

    // Getter and Setter for openCount
    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    // Getter and Setter for trialCount
    public int getTrialCount() {
        return trialCount;
    }

    public void setTrialCount(int trialCount) {
        this.trialCount = trialCount;
    }

    // Getter and Setter for closedCount
    public int getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(int closedCount) {
        this.closedCount = closedCount;
    }

    // Getter and Setter for totalBreakdowns
    public int getTotalBreakdowns() {
        return totalBreakdowns;
    }

    public void setTotalBreakdowns(int totalBreakdowns) {
        this.totalBreakdowns = totalBreakdowns;
    }
}

