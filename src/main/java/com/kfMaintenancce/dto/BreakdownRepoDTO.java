package com.kfMaintenancce.dto;

import java.time.LocalDateTime;

import com.kfMaintenancce.model.Breakdown;

public class BreakdownRepoDTO {

	
	private Breakdown breakdown;
   // private int breakdownId;
    private LocalDateTime ticketRaisedTime;
    private String status;  
    private Long NoOfDays;
    private String machineName;
    
    

    public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
//
//	public int getBreakdownId() {
//        return breakdownId;
//    }
//
//    public void setBreakdownId(int breakdownId) {
//        this.breakdownId = breakdownId;
//    }

    public LocalDateTime getTicketRaisedTime() {
        return ticketRaisedTime;
    }

    public void setTicketRaisedTime(LocalDateTime ticketRaisedTime) {
        this.ticketRaisedTime = ticketRaisedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
	
	  public Long getNoOfDays() {
		return NoOfDays;
	}

	public void setNoOfDays(Long noOfDays) {
		NoOfDays = noOfDays;
	}

	
	
	

	public Breakdown getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}

	public BreakdownRepoDTO() {}

	public BreakdownRepoDTO(Breakdown breakdown, LocalDateTime ticketRaisedTime, String status, Long noOfDays,
			String machineName) {
		super();
		this.breakdown = breakdown;
		this.ticketRaisedTime = ticketRaisedTime;
		this.status = status;
		NoOfDays = noOfDays;
		this.machineName = machineName;
	}
	
	
	
}
