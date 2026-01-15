package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

		@Entity
		@Table(name="generalbreakdown_mst")
		public class General_breakdown 
		
		{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int genbreak_id;
		
		@Column(name="conclusion")
		private String conclusion;
		
		@Column(name="rootCause")
		private String rootCause;

		@Column(name="Title")
		private String title;
		
		@Column(name="cause")
		private String cause;

//		@Column(name="Type")
//		private String type;
		
		
		@Column(name="TrialSheet")
		private String trialsheet;
		
	
		@Column(name="description_data")
		private String description;
		
		
		
		
		
		@Column(name="Open_Time")
		private Date openTime;
		
		@Column(name="Open_Date")
		private Date openDate;
		
		@Column(name="Closed_Time")
		private Date closedTime;
		
		@Column(name="Closed_date")
		private Date closedDate;
		

		

		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="lab_id")
		private Lab lab;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="loc_id")
		private Location location;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="addedBy")
		private UserDetails addedBy;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="solveBy")
		private UserDetails solveBy;
		
	
		
		@Column(name="status")
		private int status ;
		
		@JoinColumn(name="Breakdown_Number")
		private String breakdownNo; 
		
	


	public String getBreakdownNo() {
			return breakdownNo;
		}

		public void setBreakdownNo(String breakdownNo) {
			this.breakdownNo = breakdownNo;
		}

	public int getGenbreak_id() {
		return genbreak_id;
	}

	public void setGenbreak_id(int genbreak_id) {
		this.genbreak_id = genbreak_id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
	
	
	

	public String getTrialsheet() {
		return trialsheet;
	}

	public UserDetails getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(UserDetails addedBy) {
		this.addedBy = addedBy;
	}

	public UserDetails getSolveBy() {
		return solveBy;
	}

	public void setSolveBy(UserDetails solveBy) {
		this.solveBy = solveBy;
	}

	public void setTrialsheet(String trialsheet) {
		this.trialsheet = trialsheet;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
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

//	public UserDetails getRaisedBy() {
//		return raisedBy;
//	}
//
//	public void setRaisedBy(UserDetails raisedBy) {
//		this.raisedBy = raisedBy;
//	}
//
//	public UserDetails getClosedby() {
//		return closedby;
//	}
//
//	public void setClosedby(UserDetails closedby) {
//		this.closedby = closedby;
//	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "General_breakdown [genbreak_id=" + genbreak_id + ", conclusion=" + conclusion + ", rootCause="
				+ rootCause + ", title=" + title + ", cause=" + cause + ", trialsheet=" + trialsheet + ", description="
				+ description + ", openTime=" + openTime + ", openDate=" + openDate + ", closedTime=" + closedTime
				+ ", closedDate=" + closedDate + ", lab=" + lab + ", location=" + location + ", status=" + status
				+ ", breakdownNo=" + breakdownNo + "]";
	}


	
	
}
