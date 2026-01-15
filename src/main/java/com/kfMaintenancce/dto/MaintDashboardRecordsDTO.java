package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Maint;

public class MaintDashboardRecordsDTO {
    private List<Maint> totalOpenRecords;
    private List<Maint> totalClosedRecords;
    private List<Maint> totalApprovedRecords;
    private List<Maint> totalUnApprovedRecords;
    private List<Maint> totalMaintenanceRecords;
    private List<Maint> totalOverdueRecords;
	public List<Maint> getTotalOpenRecords() {
		return totalOpenRecords;
	}
	public void setTotalOpenRecords(List<Maint> totalOpenRecords) {
		this.totalOpenRecords = totalOpenRecords;
	}
	public List<Maint> getTotalClosedRecords() {
		return totalClosedRecords;
	}
	public void setTotalClosedRecords(List<Maint> totalClosedRecords) {
		this.totalClosedRecords = totalClosedRecords;
	}
	public List<Maint> getTotalApprovedRecords() {
		return totalApprovedRecords;
	}
	public void setTotalApprovedRecords(List<Maint> totalApprovedRecords) {
		this.totalApprovedRecords = totalApprovedRecords;
	}
	public List<Maint> getTotalUnApprovedRecords() {
		return totalUnApprovedRecords;
	}
	public void setTotalUnApprovedRecords(List<Maint> totalUnApprovedRecords) {
		this.totalUnApprovedRecords = totalUnApprovedRecords;
	}
	public List<Maint> getTotalMaintenanceRecords() {
		return totalMaintenanceRecords;
	}
	public void setTotalMaintenanceRecords(List<Maint> totalMaintenanceRecords) {
		this.totalMaintenanceRecords = totalMaintenanceRecords;
	}
	public List<Maint> getTotalOverdueRecords() {
		return totalOverdueRecords;
	}
	public void setTotalOverdueRecords(List<Maint> totalOverdueRecords) {
		this.totalOverdueRecords = totalOverdueRecords;
	}

    // getters and setters
    
    
    
    
}

