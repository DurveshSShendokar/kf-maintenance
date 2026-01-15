package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Maint;

public class MaintenanceStatusSummaryDTO {
    private long overdueCount;
    private long openCount;
    private long closedCount;private long approvedCount;
  

    private long totalCount; // Total count of all records (overdue + open + closed)
    
    private List<Maint> overdueRecords;
    private List<Maint> openRecords;
    private List<Maint> closedRecords;
    private List<Maint> approvedRecords;
    private List<Maint> totalRecords; // Combined list of all records (overdue + open + closed)

    public long getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(long approvedCount) {
		this.approvedCount = approvedCount;
	}

	public List<Maint> getApprovedRecords() {
		return approvedRecords;
	}

	public void setApprovedRecords(List<Maint> approvedRecords) {
		this.approvedRecords = approvedRecords;
	}

	// Getters and Setters
    public long getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(long overdueCount) {
        this.overdueCount = overdueCount;
    }

    public long getOpenCount() {
        return openCount;
    }

    public void setOpenCount(long openCount) {
        this.openCount = openCount;
    }

    public long getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(long closedCount) {
        this.closedCount = closedCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Maint> getOverdueRecords() {
        return overdueRecords;
    }

    public void setOverdueRecords(List<Maint> overdueRecords) {
        this.overdueRecords = overdueRecords;
    }

    public List<Maint> getOpenRecords() {
        return openRecords;
    }

    public void setOpenRecords(List<Maint> openRecords) {
        this.openRecords = openRecords;
    }

    public List<Maint> getClosedRecords() {
        return closedRecords;
    }

    public void setClosedRecords(List<Maint> closedRecords) {
        this.closedRecords = closedRecords;
    }

    public List<Maint> getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(List<Maint> totalRecords) {
        this.totalRecords = totalRecords;
    }
}
