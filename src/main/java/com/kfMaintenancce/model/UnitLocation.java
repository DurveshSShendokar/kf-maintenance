package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unit_location")
public class UnitLocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int unit_location_id;
	
	
	@Column(name="location_name")
	private String locationName;
	
	@Column(name="plant_name")
	private String plantName;	
	
	@Column(name="address")
	private String address;	
	
	@Column(name="active")
	private int active;	
	
	@Column(name="added_by")
	private String addedBy;
	
	
	@Column(name="added_Date")
	private Date addedDate;


	public int getUnit_location_id() {
		return unit_location_id;
	}


	public void setUnit_location_id(int unit_location_id) {
		this.unit_location_id = unit_location_id;
	}




	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getPlantName() {
		return plantName;
	}


	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public String getAddedBy() {
		return addedBy;
	}


	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}


	public Date getAddedDate() {
		return addedDate;
	}


	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
	
	
	
}
