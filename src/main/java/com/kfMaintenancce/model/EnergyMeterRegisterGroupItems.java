package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="engergy_meter_register_group_items")
public class EnergyMeterRegisterGroupItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private EnergyMeterRegisterGroup group;
	
	@Column(name="register_name")
	private String registerName;
	
	
	@Column(name="register_no")
	private String registerNo;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public EnergyMeterRegisterGroup getGroup() {
		return group;
	}


	public void setGroup(EnergyMeterRegisterGroup group) {
		this.group = group;
	}


	public String getRegisterName() {
		return registerName;
	}


	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}


	public String getRegisterNo() {
		return registerNo;
	}


	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}


	@Override
	public String toString() {
		return "EnergyMeterRegisterGroupItems [id=" + id + ", group=" + group + ", registerName=" + registerName
				+ ", registerNo=" + registerNo + "]";
	}
	
	
	
}
