package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

@Table(name="scheduled_checklist")

public class Scheduledchecklist implements Serializable {
	@Id
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="s_id")
	private int s_id;

	@ManyToOne
	@JoinColumn(name="checklist_id")
	private Checklist checklist;

	@ManyToOne(fetch= FetchType.EAGER, optional=false)
	@JoinColumn(name="maint_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Maint maint;
	
	@Column(name="status")
	private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public Maint getMaint() {
		return maint;
	}

	public void setMaint(Maint maint) {
		this.maint = maint;
	}

	
	
}
