package com.kfMaintenancce.dto;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TicketResolutionReportDTO {

    private String ticketNo;
    private String equipmentId; 
    private String comapnyName; 
    private String allocateTo;
    private int status;
    private Date complaintDate;
   
    private double adminProcessHours;
    private double engineerProcessHours;
    private double engineerSolutionHours;
    private double totalHours;

 

	

	public TicketResolutionReportDTO(String ticketNo, String equipmentId, String comapnyName, String allocateTo,
			int status, Date complaintDate, double adminProcessHours, double engineerProcessHours,
			double engineerSolutionHours, double totalHours) {
		super();
		this.ticketNo = ticketNo;
		this.equipmentId = equipmentId;
		this.comapnyName = comapnyName;
		this.allocateTo = allocateTo;
		this.status = status;
		this.complaintDate = complaintDate;
		this.adminProcessHours = adminProcessHours;
		this.engineerProcessHours = engineerProcessHours;
		this.engineerSolutionHours = engineerSolutionHours;
		this.totalHours = totalHours;
	}

	public String getComapnyName() {
		return comapnyName;
	}

	public void setComapnyName(String comapnyName) {
		this.comapnyName = comapnyName;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAllocateTo() {
		return allocateTo;
	}

	public void setAllocateTo(String allocateTo) {
		this.allocateTo = allocateTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public double getAdminProcessHours() {
		return adminProcessHours;
	}

	public void setAdminProcessHours(double adminProcessHours) {
		this.adminProcessHours = adminProcessHours;
	}

	public double getEngineerProcessHours() {
		return engineerProcessHours;
	}

	public void setEngineerProcessHours(double engineerProcessHours) {
		this.engineerProcessHours = engineerProcessHours;
	}

	public double getEngineerSolutionHours() {
		return engineerSolutionHours;
	}

	public void setEngineerSolutionHours(double engineerSolutionHours) {
		this.engineerSolutionHours = engineerSolutionHours;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(double totalHours) {
		this.totalHours = totalHours;
	}

  
}
