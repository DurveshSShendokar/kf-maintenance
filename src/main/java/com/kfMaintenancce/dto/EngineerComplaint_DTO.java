package com.kfMaintenancce.dto;
import java.util.Date;
public class EngineerComplaint_DTO {
	
	
	 private String ticketNo;
	    private String equipmentId; 
	    private String priority; 
	    private String cause; 
	    private String description; 
	    private Date complaintDate;
	    private Date allocateDate; 
	    private Date inProcessDate; 
	    private Date closedDate; 
	    private String allocateTo;
	    private int status;

	    public EngineerComplaint_DTO(String ticketNo, String equipmentId, String priority, String cause,
	                                     String description, Date complaintDate, Date allocateDate,
	                                     Date inProcessDate, Date closedDate, String allocateTo, int status) {
	        this.ticketNo = ticketNo;
	        this.equipmentId = equipmentId;
	        this.priority = priority;
	        this.cause = cause;
	        this.description = description;
	        this.complaintDate = complaintDate;
	        this.allocateDate = allocateDate;
	        this.inProcessDate = inProcessDate;
	        this.closedDate = closedDate;
	        this.allocateTo = allocateTo;
	        this.status = status;
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

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getComplaintDate() {
			return complaintDate;
		}

		public void setComplaintDate(Date complaintDate) {
			this.complaintDate = complaintDate;
		}

		public Date getAllocateDate() {
			return allocateDate;
		}

		public void setAllocateDate(Date allocateDate) {
			this.allocateDate = allocateDate;
		}

		public Date getInProcessDate() {
			return inProcessDate;
		}

		public void setInProcessDate(Date inProcessDate) {
			this.inProcessDate = inProcessDate;
		}

		public Date getClosedDate() {
			return closedDate;
		}

		public void setClosedDate(Date closedDate) {
			this.closedDate = closedDate;
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

	    // Getters and setters
	  
}
