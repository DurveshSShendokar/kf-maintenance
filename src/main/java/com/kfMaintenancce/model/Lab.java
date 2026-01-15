package com.kfMaintenancce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="lab_mst")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lab {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lab_id;
	
	
	@OneToMany(mappedBy = "lab", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserLabMapping> userLabMappings;

	
	

	public List<UserLabMapping> getUserLabMappings() {
		return userLabMappings;
	}

	public void setUserLabMappings(List<UserLabMapping> userLabMappings) {
		this.userLabMappings = userLabMappings;
	}

	@Column(name="lab_name")
	private String labName;
	
	@Column(name="lab_code")
	private String labCode;

	public int getLab_id() {
		return lab_id;
	}

	public void setLab_id(int lab_id) {
		this.lab_id = lab_id;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}

	
	

}
