package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.UserDetails;

public class MaintStatusDTO {

	 private int maintId;
	    private String scheduleDate;
	    private String closedDate;
	    private String frequency;
	    private String weekNo;
	    private UserDetails raisedBy;
	    private UserDetails doneBy;
	    private String overallStatus;

	    // Machine details
	    private String machineName;
	    private String machineEqId;
	    private String machineLocation;

	    // Constructor
	    public MaintStatusDTO(Maint maint) {
	        this.maintId = maint.getMaint_id();
	        this.scheduleDate = maint.getSchedule_date() != null ? maint.getSchedule_date().toString() : null;
	        this.closedDate = maint.getClosedDate() != null ? maint.getClosedDate().toString() : null;
	        this.frequency = maint.getFrequency();
	        this.weekNo = maint.getWeek() != 0 ? String.valueOf(maint.getWeek()) : null;
	        this.raisedBy = maint.getRaisedBy();
	        this.doneBy = maint.getDone_by();
	        this.overallStatus = maint.getOverall_status();

	        // Machine details
	        if (maint.getMachine() != null) {
	            Machine machine = maint.getMachine();
	            this.machineName = machine.getMachine_name();
	            this.machineEqId = machine.getEqid();
	            this.machineLocation = machine.getLocation();
	        }
	    }

		public int getMaintId() {
			return maintId;
		}

		public void setMaintId(int maintId) {
			this.maintId = maintId;
		}

		public String getScheduleDate() {
			return scheduleDate;
		}

		public void setScheduleDate(String scheduleDate) {
			this.scheduleDate = scheduleDate;
		}

		public String getClosedDate() {
			return closedDate;
		}

		public void setClosedDate(String closedDate) {
			this.closedDate = closedDate;
		}

		public String getFrequency() {
			return frequency;
		}

		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}

		public String getWeekNo() {
			return weekNo;
		}

		public void setWeekNo(String weekNo) {
			this.weekNo = weekNo;
		}

		public UserDetails getRaisedBy() {
			return raisedBy;
		}

		public void setRaisedBy(UserDetails raisedBy) {
			this.raisedBy = raisedBy;
		}

		public UserDetails getDoneBy() {
			return doneBy;
		}

		public void setDoneBy(UserDetails doneBy) {
			this.doneBy = doneBy;
		}

		public String getOverallStatus() {
			return overallStatus;
		}

		public void setOverallStatus(String overallStatus) {
			this.overallStatus = overallStatus;
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

		@Override
		public String toString() {
			return "MaintStatusDTO [maintId=" + maintId + ", scheduleDate=" + scheduleDate + ", closedDate="
					+ closedDate + ", frequency=" + frequency + ", weekNo=" + weekNo + ", raisedBy=" + raisedBy
					+ ", doneBy=" + doneBy + ", overallStatus=" + overallStatus + ", machineName=" + machineName
					+ ", machineEqId=" + machineEqId + ", machineLocation=" + machineLocation + "]";
		}

	    
	    
	    
	
	    

	   
	
}
