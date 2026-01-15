package com.kfMaintenancce.dto;

import java.util.Date;
import java.util.List;

import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Shift;

public class BreakdownResponseDTOMob {

    private int breakdownId;
    private String bdSlip;
    private int status;
    private int ticketClosedFlag;
    private String detectedBy;
    private String doneBy;
    private String RootCause;
    private String Observation;
    private String ActionTaken;
    private Date RaisedDate;
    private Date ClosedDate;
    private Date TrialDate;
    private Shift shift;
    private Machine machine;
    
    private List<BreakdownSpareDTO> breakdownSpares;

    // Nested DTO for BreakdownSpare ticket_raised_time
    public static class BreakdownSpareDTO {
        private int id;
        private SpareDTO spare;
       
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public SpareDTO getSpare() {
			return spare;
		}
		public void setSpare(SpareDTO spare) {
			this.spare = spare;
		}
		

        
    }

    // Nested DTO for MaintSpare
    public static class SpareDTO {
        private int maintspareId;
        private String spareName;
        private String supplierName;
        private String code;
		public int getMaintspareId() {
			return maintspareId;
		}
		public void setMaintspareId(int maintspareId) {
			this.maintspareId = maintspareId;
		}
		public String getSpareName() {
			return spareName;
		}
		public void setSpareName(String spareName) {
			this.spareName = spareName;
		}
		public String getSupplierName() {
			return supplierName;
		}
		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}

        
    }

    

	public int getBreakdownId() {
		return breakdownId;
	}

	public void setBreakdownId(int breakdownId) {
		this.breakdownId = breakdownId;
	}

	public String getBdSlip() {
		return bdSlip;
	}

	public void setBdSlip(String bdSlip) {
		this.bdSlip = bdSlip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTicketClosedFlag() {
		return ticketClosedFlag;
	}

	public void setTicketClosedFlag(int ticketClosedFlag) {
		this.ticketClosedFlag = ticketClosedFlag;
	}

	public String getDetectedBy() {
		return detectedBy;
	}

	public void setDetectedBy(String detectedBy) {
		this.detectedBy = detectedBy;
	}

	public String getDoneBy() {
		return doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public List<BreakdownSpareDTO> getBreakdownSpares() {
		return breakdownSpares;
	}

	public void setBreakdownSpares(List<BreakdownSpareDTO> breakdownSpares) {
		this.breakdownSpares = breakdownSpares;
	}

	public Date getRaisedDate() {
		return RaisedDate;
	}

	public void setRaisedDate(Date raisedDate) {
		RaisedDate = raisedDate;
	}

	public Date getClosedDate() {
		return ClosedDate;
	}

	public void setClosedDate(Date closedDate) {
		ClosedDate = closedDate;
	}

	public Date getTrialDate() {
		return TrialDate;
	}

	public void setTrialDate(Date trialDate) {
		TrialDate = trialDate;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public String getRootCause() {
		return RootCause;
	}

	public void setRootCause(String rootCause) {
		RootCause = rootCause;
	}

	public String getObservation() {
		return Observation;
	}

	public void setObservation(String observation) {
		Observation = observation;
	}

	public String getActionTaken() {
		return ActionTaken;
	}

	public void setActionTaken(String actionTaken) {
		ActionTaken = actionTaken;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	

    
}
