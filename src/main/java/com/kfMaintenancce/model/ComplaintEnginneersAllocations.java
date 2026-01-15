package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="complaint_engineer_allocation")
public class ComplaintEnginneersAllocations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="complaint_id")
	private Complaint complaint;
	
	
	@ManyToOne
	@JoinColumn(name="enginner_id")
	private UserDetails  engineer;
	
	@Column(name="allocation_date")
	private Date allocationDate;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	
	@Column(name="status")
	private String status;

	@Column(name="allocated_by")
	private String allocatedBy;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Complaint getComplaint() {
		return complaint;
	}


	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}


	public UserDetails getEngineer() {
		return engineer;
	}


	public void setEngineer(UserDetails engineer) {
		this.engineer = engineer;
	}


	public Date getAllocationDate() {
		return allocationDate;
	}


	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getAllocatedBy() {
		return allocatedBy;
	}


	public void setAllocatedBy(String allocatedBy) {
		this.allocatedBy = allocatedBy;
	}
	
	
	
}
