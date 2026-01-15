package com.kfMaintenancce.dto;

import java.util.Date;

import com.kfMaintenancce.model.Breakdown;

public class BreakdownReportDTO {
	
	 	private Breakdown breakdown;
	    private Date ticketRaisedTime;
	    private Date ticketTrialTime;
	    private Date ticketClosedTime;
	    
	    private double raiseToTrial;  
	    private double trialToClosed; 
	    private double totalTime;
	    private double TBF;
	    
	    
	    
		public double getTBF() {
			return TBF;
		}
		public void setTBF(double tBF) {
			TBF = tBF;
		}
		public Breakdown getBreakdown() {
			return breakdown;
		}
		public void setBreakdown(Breakdown breakdown) {
			this.breakdown = breakdown;
		}
		
		public Date getTicketRaisedTime() {
			return ticketRaisedTime;
		}
		public void setTicketRaisedTime(Date ticketRaisedTime) {
			this.ticketRaisedTime = ticketRaisedTime;
		}
		public Date getTicketTrialTime() {
			return ticketTrialTime;
		}
		public void setTicketTrialTime(Date ticketTrialTime) {
			this.ticketTrialTime = ticketTrialTime;
		}
		public Date getTicketClosedTime() {
			return ticketClosedTime;
		}
		public void setTicketClosedTime(Date ticketClosedTime) {
			this.ticketClosedTime = ticketClosedTime;
		}
		public double getRaiseToTrial() {
			return raiseToTrial;
		}
		public void setRaiseToTrial(double raiseToTrial) {
			this.raiseToTrial = raiseToTrial;
		}
		public double getTrialToClosed() {
			return trialToClosed;
		}
		public void setTrialToClosed(double trialToClosed) {
			this.trialToClosed = trialToClosed;
		}
		public double getTotalTime() {
			return totalTime;
		}
		public void setTotalTime(double totalTime) {
			this.totalTime = totalTime;
		}
		
		public BreakdownReportDTO(Breakdown breakdown, Date ticketRaisedTime, Date ticketTrialTime,
				Date ticketClosedTime, double raiseToTrial, double trialToClosed, double totalTime, double tBF) {
			super();
			this.breakdown = breakdown;
			this.ticketRaisedTime = ticketRaisedTime;
			this.ticketTrialTime = ticketTrialTime;
			this.ticketClosedTime = ticketClosedTime;
			this.raiseToTrial = raiseToTrial;
			this.trialToClosed = trialToClosed;
			this.totalTime = totalTime;
			TBF = tBF;
		}
		public BreakdownReportDTO() {
			super();
			// TODO Auto-generated constructor stub
		}    
	    
	    
	    
		
	
	    
}
