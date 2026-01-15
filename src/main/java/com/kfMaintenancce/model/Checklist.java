package com.kfMaintenancce.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="checklist_mst")
public class Checklist implements Serializable {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int checklist_id;
	
	@Column(name="task")
	private String task;

	@Enumerated(EnumType.STRING)
	@Column(name = "check_type")
	private CheckType checkType;

	@Column(name = "LowerRange")
	private Double lower_range;

	@Column(name = "UpperRange")
	private Double upper_range;

	@Column(name = "active_bit")
	private int activeBit = 1;

	
	@Column(name="CheckUnit",  columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String check_unit;
	

	@Column(name="operation")
	private String operation;
	
	@Column(name = "acceptable_range", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String acceptableRange;

	
	@Transient
	String machineName;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(nullable = true)
	private Integer serialNumber = 0;




	@Transient
	private List<Checklist> checklist;

	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	
	@Column(name="type")
	private String type;
	@Transient
	private int checkpointId;
	
	
	
	// ******************************************//
	

	

	

	public String getAcceptableRange() {
		return acceptableRange;
	}

	

	


	public Integer getSerialNumber() {
		return serialNumber;
	}






	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}






	public int getActiveBit() {
		return activeBit;
	}



	public void setActiveBit(int activeBit) {
		this.activeBit = activeBit;
	}



	public void setAcceptableRange(String acceptableRange) {
		this.acceptableRange = acceptableRange;
	}
	
	
	public int getCheckpointId() {
		return checkpointId;
	}

	public void setCheckpointId(int checkpointId) {
		this.checkpointId = checkpointId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	


	
	

	public CheckType getCheckType() {
		return checkType;
	}

	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}

	

	public Double getLower_range() {
		return lower_range;
	}

	public void setLower_range(Double lower_range) {
		this.lower_range = lower_range;
	}

	public Double getUpper_range() {
		return upper_range;
	}

	public void setUpper_range(Double upper_range) {
		this.upper_range = upper_range;
	}

	public String getCheck_unit() {
		return check_unit;
	}

	public void setCheck_unit(String check_unit) {
		this.check_unit = check_unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public int getChecklist_id() {
		return checklist_id;
	}

	public void setChecklist_id(int checklist_id) {
		this.checklist_id = checklist_id;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}






public List<Checklist> getChecklist() {
	return checklist;
}

public void setChecklist(List<Checklist> checklist) {
	this.checklist = checklist;
}

public String getMachineName() {
	return machineName;
}

public void setMachineName(String machineName) {
	this.machineName = machineName;
}






}
