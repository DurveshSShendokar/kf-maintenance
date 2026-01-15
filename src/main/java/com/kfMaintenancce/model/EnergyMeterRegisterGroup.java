package com.kfMaintenancce.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="engergy_meter_register_group")
public class EnergyMeterRegisterGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@ManyToOne
	@JoinColumn(name="module_id")
	private EnergyMeterModule module;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="no_of_register")
	private String noOfRegister;
	
	
	@Transient
	private List<EnergyMeterRegisterGroupItems> items;

	public List<EnergyMeterRegisterGroupItems> getItems() {
		return items;
	}

	public void setItems(List<EnergyMeterRegisterGroupItems> items) {
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNoOfRegister() {
		return noOfRegister;
	}

	public void setNoOfRegister(String noOfRegister) {
		this.noOfRegister = noOfRegister;
	}

	public EnergyMeterModule getModule() {
		return module;
	}

	public void setModule(EnergyMeterModule module) {
		this.module = module;
	}
	
	
	
}
