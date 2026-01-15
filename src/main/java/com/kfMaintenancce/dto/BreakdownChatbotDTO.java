package com.kfMaintenancce.dto;


import java.util.Date;

public class BreakdownChatbotDTO {

	 private int breakdown_id;
	    private String addedByName;
	    private String updateByName;
	    private String trialByName;
	    private String departmentName;
	    private String machineName;
	    private String machineEqId;       // ✅ New field
	    private String sparesUsed;
	    private int status;
	    private String bdSlip;
	    private String observation;
	    private String actionTaken;
	    private String rootCause;
	    private Date ticketRaisedTime;
	    private Date ticketClosedTime;
	    private Date ticketTrialTime;
	    private Date actualWorkingStartTime;
	    private String labName;          
	    private String machineLocation;

	    // ✅ Update constructor
	    public BreakdownChatbotDTO(int breakdown_id, String addedByName, String updateByName, String trialByName,
	                               String departmentName, String machineName, String machineEqId, String sparesUsed, int status,
	                               String bdSlip, String observation, String actionTaken, String rootCause,
	                               Date ticketRaisedTime, Date ticketClosedTime, Date ticketTrialTime, Date actualWorkingStartTime,
	                               String labName, String machineLocation) {
	        this.breakdown_id = breakdown_id;
	        this.addedByName = addedByName;
	        this.updateByName = updateByName;
	        this.trialByName = trialByName;
	        this.departmentName = departmentName;
	        this.machineName = machineName;
	        this.machineEqId = machineEqId;
	        this.sparesUsed = sparesUsed;
	        this.status = status;
	        this.bdSlip = bdSlip;
	        this.observation = observation;
	        this.actionTaken = actionTaken;
	        this.rootCause = rootCause;
	        this.ticketRaisedTime = ticketRaisedTime;
	        this.ticketClosedTime = ticketClosedTime;
	        this.ticketTrialTime = ticketTrialTime;
	        this.actualWorkingStartTime = actualWorkingStartTime;
	        this.labName = labName;
	        this.machineLocation = machineLocation;
	    }
	
	

	public int getBreakdown_id() {
			return breakdown_id;
		}



		public void setBreakdown_id(int breakdown_id) {
			this.breakdown_id = breakdown_id;
		}



		public String getMachineEqId() {
			return machineEqId;
		}



		public void setMachineEqId(String machineEqId) {
			this.machineEqId = machineEqId;
		}



	public int getBreakdownId() {
		return breakdown_id;
	}

	public void setBreakdownId(int breakdown_id) {
		this.breakdown_id = breakdown_id;
	}

	public String getAddedByName() {
		return addedByName;
	}

	public void setAddedByName(String addedByName) {
		this.addedByName = addedByName;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public String getTrialByName() {
		return trialByName;
	}

	public void setTrialByName(String trialByName) {
		this.trialByName = trialByName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getSparesUsed() {
		return sparesUsed;
	}

	public void setSparesUsed(String sparesUsed) {
		this.sparesUsed = sparesUsed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBdSlip() {
		return bdSlip;
	}

	public void setBdSlip(String bdSlip) {
		this.bdSlip = bdSlip;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public Date getTicketRaisedTime() {
		return ticketRaisedTime;
	}

	public void setTicketRaisedTime(Date ticketRaisedTime) {
		this.ticketRaisedTime = ticketRaisedTime;
	}

	public Date getTicketClosedTime() {
		return ticketClosedTime;
	}

	public void setTicketClosedTime(Date ticketClosedTime) {
		this.ticketClosedTime = ticketClosedTime;
	}

	public Date getTicketTrialTime() {
		return ticketTrialTime;
	}

	public void setTicketTrialTime(Date ticketTrialTime) {
		this.ticketTrialTime = ticketTrialTime;
	}

	public Date getActualWorkingStartTime() {
		return actualWorkingStartTime;
	}

	public void setActualWorkingStartTime(Date actualWorkingStartTime) {
		this.actualWorkingStartTime = actualWorkingStartTime;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getMachineLocation() {
		return machineLocation;
	}

	public void setMachineLocation(String machineLocation) {
		this.machineLocation = machineLocation;
	}

   
}