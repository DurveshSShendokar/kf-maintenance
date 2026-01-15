package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Maint;

public class PPMDashboardCountDTO {
	  private int openMaintence;
	    private int overduesMaintenace;
	    private int closed;
	    private int totalMaintence;
	    private int totalOpenMaintenance;
	    private int totalClosedMaintenance;

	    private int totalApprovedClosedPPM;
	    private int totalUnApprovedClosedPPM;

	    private List<Maint> approvedClosedPPMRecords;
	    private List<Maint> unApprovedClosedPPMRecords;
	    private List<Maint> totalClosedMaintenanceRecords;
	    private List<Maint> totalOpenMaintenanceRecords;
	    private List<Maint> totalMaintenanceRecords;
	    private List<Maint> closedRecords;
	    private List<Maint> overduesMaintenanceRecords;
	    private List<Maint> openMaintenanceRecords;
		public int getOpenMaintence() {
			return openMaintence;
		}
		public void setOpenMaintence(int openMaintence) {
			this.openMaintence = openMaintence;
		}
		public int getOverduesMaintenace() {
			return overduesMaintenace;
		}
		public void setOverduesMaintenace(int overduesMaintenace) {
			this.overduesMaintenace = overduesMaintenace;
		}
		public int getClosed() {
			return closed;
		}
		public void setClosed(int closed) {
			this.closed = closed;
		}
		public int getTotalMaintence() {
			return totalMaintence;
		}
		public void setTotalMaintence(int totalMaintence) {
			this.totalMaintence = totalMaintence;
		}
		public int getTotalOpenMaintenance() {
			return totalOpenMaintenance;
		}
		public void setTotalOpenMaintenance(int totalOpenMaintenance) {
			this.totalOpenMaintenance = totalOpenMaintenance;
		}
		public int getTotalClosedMaintenance() {
			return totalClosedMaintenance;
		}
		public void setTotalClosedMaintenance(int totalClosedMaintenance) {
			this.totalClosedMaintenance = totalClosedMaintenance;
		}
		public int getTotalApprovedClosedPPM() {
			return totalApprovedClosedPPM;
		}
		public void setTotalApprovedClosedPPM(int totalApprovedClosedPPM) {
			this.totalApprovedClosedPPM = totalApprovedClosedPPM;
		}
		public int getTotalUnApprovedClosedPPM() {
			return totalUnApprovedClosedPPM;
		}
		public void setTotalUnApprovedClosedPPM(int totalUnApprovedClosedPPM) {
			this.totalUnApprovedClosedPPM = totalUnApprovedClosedPPM;
		}
		public List<Maint> getApprovedClosedPPMRecords() {
			return approvedClosedPPMRecords;
		}
		public void setApprovedClosedPPMRecords(List<Maint> approvedClosedPPMRecords) {
			this.approvedClosedPPMRecords = approvedClosedPPMRecords;
		}
		public List<Maint> getUnApprovedClosedPPMRecords() {
			return unApprovedClosedPPMRecords;
		}
		public void setUnApprovedClosedPPMRecords(List<Maint> unApprovedClosedPPMRecords) {
			this.unApprovedClosedPPMRecords = unApprovedClosedPPMRecords;
		}
		public List<Maint> getTotalClosedMaintenanceRecords() {
			return totalClosedMaintenanceRecords;
		}
		public void setTotalClosedMaintenanceRecords(List<Maint> totalClosedMaintenanceRecords) {
			this.totalClosedMaintenanceRecords = totalClosedMaintenanceRecords;
		}
		public List<Maint> getTotalOpenMaintenanceRecords() {
			return totalOpenMaintenanceRecords;
		}
		public void setTotalOpenMaintenanceRecords(List<Maint> totalOpenMaintenanceRecords) {
			this.totalOpenMaintenanceRecords = totalOpenMaintenanceRecords;
		}
		public List<Maint> getTotalMaintenanceRecords() {
			return totalMaintenanceRecords;
		}
		public void setTotalMaintenanceRecords(List<Maint> totalMaintenanceRecords) {
			this.totalMaintenanceRecords = totalMaintenanceRecords;
		}
		public List<Maint> getClosedRecords() {
			return closedRecords;
		}
		public void setClosedRecords(List<Maint> closedRecords) {
			this.closedRecords = closedRecords;
		}
		public List<Maint> getOverduesMaintenanceRecords() {
			return overduesMaintenanceRecords;
		}
		public void setOverduesMaintenanceRecords(List<Maint> overduesMaintenanceRecords) {
			this.overduesMaintenanceRecords = overduesMaintenanceRecords;
		}
		public List<Maint> getOpenMaintenanceRecords() {
			return openMaintenanceRecords;
		}
		public void setOpenMaintenanceRecords(List<Maint> openMaintenanceRecords) {
			this.openMaintenanceRecords = openMaintenanceRecords;
		}



}
