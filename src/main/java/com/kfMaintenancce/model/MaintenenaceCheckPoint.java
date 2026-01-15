package com.kfMaintenancce.model;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="maint_check_point")
public class MaintenenaceCheckPoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="maint_check_point_id")
	private int maintCheckPointId;
	
	@ManyToOne
	@JoinColumn(name="checklist_id")
	private Checklist checkpoint;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maint_id")
	  @JsonBackReference 
	private Maint maint;

	
	@Column(name="done_by")
	private String done_by;
	
	@Column(name="done_date")
	private Date done_date;
	
	
	
	@Column(name="status")
	private String status;
	
	@Column(name="previous_bit", columnDefinition = "int default 0")
	private int previousBit;

	
	
	
	
	public int getPreviousBit() {
		return previousBit;
	}

	public void setPreviousBit(int previousBit) {
		this.previousBit = previousBit;
	}

	public String getTask() {
        return checkpoint != null ? checkpoint.getTask() : null;
    }
	
	 // Method to get the frequency from the Checklist entity
    public String getFrequency() {
        return checkpoint != null ? checkpoint.getFrequency() : null;
    }

    // Method to get the acceptableRange from the Checklist entity
    public String getAcceptableRange() {
        return checkpoint != null ? checkpoint.getAcceptableRange() : null;
    }
	
	public int getMaintCheckPointId() {
		return maintCheckPointId;
	}

	public void setMaintCheckPointId(int maintCheckPointId) {
		this.maintCheckPointId = maintCheckPointId;
	}

	public Checklist getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(Checklist checkpoint) {
		this.checkpoint = checkpoint;
	}

	public Maint getMaint() {
		return maint;
	}

	public void setMaint(Maint maint) {
		this.maint = maint;
	}

	public String getDone_by() {
		return done_by;
	}

	public void setDone_by(String done_by) {
		this.done_by = done_by;
	}

	public Date getDone_date() {
		return done_date;
	}

	public void setDone_date(Date done_date) {
		this.done_date = done_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MaintenenaceCheckPoint [maintCheckPointId=" + maintCheckPointId + ", checkpoint=" + checkpoint
				+ ", maint=" + maint + ", done_by=" + done_by + ", done_date=" + done_date + ", status=" + status + "]";
	}
	
	
	
	
	
}
