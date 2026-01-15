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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

@Table(name="maint_utilized_spare")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintUtilizedSpare {

	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private int id; 
	
	@ManyToOne
	@JoinColumn(name="maint_spare_id")
	private MaintSpare maintspare;
	
	
	
	@ManyToOne
	@JoinColumn(name="breakdown_id")
	private Breakdown breakdown;
	
	@Column(name="quanity")
	private double quanity;
	
	
	
	@Column(name="utilized_date")
	private Date utilizedDate;
	
	
	
	@Column(name="utilized_by")
	private String utilizedBy;
	
	
	
	
	

	public Date getUtilizedDate() {
		return utilizedDate;
	}

	public void setUtilizedDate(Date utilizedDate) {
		this.utilizedDate = utilizedDate;
	}

	public String getUtilizedBy() {
		return utilizedBy;
	}

	public void setUtilizedBy(String utilizedBy) {
		this.utilizedBy = utilizedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MaintSpare getMaintspare() {
		return maintspare;
	}

	public void setMaintspare(MaintSpare maintspare) {
		this.maintspare = maintspare;
	}

	public Breakdown getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}

	public double getQuanity() {
		return quanity;
	}

	public void setQuanity(double quanity) {
		this.quanity = quanity;
	}
	
	
	
	
	
	
	
}
