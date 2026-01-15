package com.kfMaintenancce.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name="complaint_mst")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Complaint {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comp_id;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
		private AssetInventory assetInventory;

	

	@Transient
	private String complaintTimeStr;
	
	@Transient
	private String complaintClosedTimeStr;
	@Transient
	private String complaintInprocessTimeStr;
	



	@Column(name="conclusion")
	private String conclusion;
	
	@Column(name="rootCause")
	private String rootCause;
	
		
		
		@Column(name="priority")
		private String priority;
		
		@Column(name="cause")
		private String cause;
			
		

		@Column(name="ticket_no")
		private String ticketNo;
		
		
		
		@Column(name="status")
		private int status ;
		
		@Column(name="description_data")
		private String description;
		
		
		
		
		
	
	@ManyToOne
	@JoinColumn(name="raised_By")
	private UserDetails raisedBy;
	
	@ManyToOne
	@JoinColumn(name="allocate_to")
	private UserDetails allocateTo;
	
	@Transient 
	private List<Spare> consumedspareList;
	
	@Transient 
	private UserDetails reAllocateTo;
	
	@Transient 
	private List<Spare> spares;
	
	
	@Column(name="Allocate_Time")
	private Date allocateTime;
	
	@Column(name="Allocate_date")
	private Date allocateDate;
	
	
	
	@Column(name="Closed_Time")
	private Date closedTime;
	
	@Column(name="Closed_date")
	private Date closedDate;
	
	
	
	@Column(name="Workstart_Time")
	private Date workstartTime;
	
	@Column(name="Workstart_date")
	private Date workstartDate;
	
	
	@Column(name="InProcess_Time")
	private Date InProcessTime;
	
	@Column(name="InProcess_Date")
	private Date InProcessDate;
	
	
	
	@Column(name="Complaint_Time")
	private Date complaintTime;
	
	@Column(name="Complaint_date")
	private Date complaintDate;

	public int getComp_id() {
		return comp_id;
	}

	public void setComp_id(int comp_id) {
		this.comp_id = comp_id;
	}
	
	
	
	public String getComplaintClosedTimeStr() {
		return complaintClosedTimeStr;
	}

	public void setComplaintClosedTimeStr(String complaintClosedTimeStr) {
		this.complaintClosedTimeStr = complaintClosedTimeStr;
	}

	public String getComplaintInprocessTimeStr() {
		return complaintInprocessTimeStr;
	}

	public void setComplaintInprocessTimeStr(String complaintInprocessTimeStr) {
		this.complaintInprocessTimeStr = complaintInprocessTimeStr;
	}

	 public AssetInventory getAssetInventory() {
	        return assetInventory;
	    }

	    public void setAssetInventory(AssetInventory assetInventory) {
	        this.assetInventory = assetInventory;
	    }
	    
		
		public String getComplaintTimeStr() {
			return complaintTimeStr;
		}

		public void setComplaintTimeStr(String complaintTimeStr) {
			this.complaintTimeStr = complaintTimeStr;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

	public UserDetails getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(UserDetails raisedBy) {
		this.raisedBy = raisedBy;
	}

	

	public UserDetails getAllocateTo() {
		return allocateTo;
	}

	public void setAllocateTo(UserDetails allocateTo) {
		this.allocateTo = allocateTo;
	}

	public Date getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(Date allocateTime) {
		this.allocateTime = allocateTime;
	}

	public Date getAllocateDate() {
		return allocateDate;
	}

	public void setAllocateDate(Date allocateDate) {
		this.allocateDate = allocateDate;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Date getWorkstartTime() {
		return workstartTime;
	}

	public void setWorkstartTime(Date workstartTime) {
		this.workstartTime = workstartTime;
	}

	public Date getWorkstartDate() {
		return workstartDate;
	}

	public void setWorkstartDate(Date workstartDate) {
		this.workstartDate = workstartDate;
	}

	public Date getComplaintTime() {
		return complaintTime;
	}

	public void setComplaintTime(Date date) {
		this.complaintTime = date;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public Date getInProcessTime() {
		return InProcessTime;
	}

	public void setInProcessTime(Date inProcessTime) {
		InProcessTime = inProcessTime;
	}

	public Date getInProcessDate() {
		return InProcessDate;
	}

	public void setInProcessDate(Date inProcessDate) {
		InProcessDate = inProcessDate;
	}

	
	

	public UserDetails getReAllocateTo() {
		return reAllocateTo;
	}

	public void setReAllocateTo(UserDetails reAllocateTo) {
		this.reAllocateTo = reAllocateTo;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

//	 public byte[] getImage() {
//	        return image;
//	    }
//
//	    public void setImage(byte[] image) {
//	        this.image = image;
//	    }

	public List<Spare> getConsumedspareList() {
		return consumedspareList;
	}

	public void setConsumedspareList(List<Spare> consumedspareList) {
		this.consumedspareList = consumedspareList;
	}

	public List<Spare> getSpares() {
		return spares;
	}

	public void setSpares(List<Spare> spares) {
		this.spares = spares;
	}

	@Override
	public String toString() {
		return "Complaint [comp_id=" + comp_id + ", assetInventory=" + assetInventory + ", complaintTimeStr="
				+ complaintTimeStr + ", complaintClosedTimeStr=" + complaintClosedTimeStr
				+ ", complaintInprocessTimeStr=" + complaintInprocessTimeStr + ", conclusion=" + conclusion
				+ ", rootCause=" + rootCause + ", priority=" + priority + ", cause=" + cause + ", ticketNo=" + ticketNo
				+ ", status=" + status + ", description=" + description + ", raisedBy=" + raisedBy + ", allocateTo="
				+ allocateTo + ", consumedspareList=" + consumedspareList + ", reAllocateTo=" + reAllocateTo
				+ ", spares=" + spares + ", allocateTime=" + allocateTime + ", allocateDate=" + allocateDate
				+ ", closedTime=" + closedTime + ", closedDate=" + closedDate + ", workstartTime=" + workstartTime
				+ ", workstartDate=" + workstartDate + ", InProcessTime=" + InProcessTime + ", InProcessDate="
				+ InProcessDate + ", complaintTime=" + complaintTime + ", complaintDate=" + complaintDate + "]";
	}


	
	
	
}
