package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.UserDetails;

public class BreakdownResponseDTO {
    private int breakdown_id;
    private MachineDTO machine;
    private String rootCause;
    private String ticketRaisedTime;
    private int duplicateFlag;
    private UserDetails addedBy;

    // Getters and Setters
  

    public MachineDTO getMachine() {
        return machine;
    }

    public UserDetails getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(UserDetails addedBy) {
		this.addedBy = addedBy;
	}

	public int getBreakdown_id() {
		return breakdown_id;
	}

	public void setBreakdown_id(int breakdown_id) {
		this.breakdown_id = breakdown_id;
	}

	public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getTicketRaisedTime() {
        return ticketRaisedTime;
    }

    public void setTicketRaisedTime(String ticketRaisedTime) {
        this.ticketRaisedTime = ticketRaisedTime;
    }

    public int getDuplicateFlag() {
        return duplicateFlag;
    }

    public void setDuplicateFlag(int duplicateFlag) {
        this.duplicateFlag = duplicateFlag;
    }
}
