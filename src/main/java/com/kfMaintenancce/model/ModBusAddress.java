package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mod_bus_address")
public class ModBusAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mod_bus_address_id;
	
	
	@Column(name="name")
	private String name;
	
	@Column(name="mod_address")
	private String modAddress;	
	
	
	@Column(name="active")
	private int active;	
	
	
	
	@Column(name="added_Date")
	private Date addedDate;






	public int getMod_bus_address_id() {
		return mod_bus_address_id;
	}



	public void setMod_bus_address_id(int mod_bus_address_id) {
		this.mod_bus_address_id = mod_bus_address_id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getModAddress() {
		return modAddress;
	}



	public void setModAddress(String modAddress) {
		this.modAddress = modAddress;
	}



	public int getActive() {
		return active;
	}



	public void setActive(int active) {
		this.active = active;
	}



	public Date getAddedDate() {
		return addedDate;
	}



	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
	
	



}
