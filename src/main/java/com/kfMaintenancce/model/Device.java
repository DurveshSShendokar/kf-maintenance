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
@Table(name="device_mst")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="device_name")
	private String deviceName;
	
	@Column(name="device_id")
	private String deviceId;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	private UnitLocation location;
	
	@ManyToOne
	@JoinColumn(name="device_model_id")
	private DeviceModel model;
	
	

	@Column(name="added_by")
	private String addedBy;
	
	@Column(name="added_date")
	private Date addedDate;
	
	
	@Column(name="active")
	private int active;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDeviceName() {
		return deviceName;
	}


	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public UnitLocation getLocation() {
		return location;
	}


	public void setLocation(UnitLocation location) {
		this.location = location;
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


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public DeviceModel getModel() {
		return model;
	}


	public void setModel(DeviceModel model) {
		this.model = model;
	}


	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceName=" + deviceName + ", deviceId=" + deviceId + ", location=" + location
				+ ", model=" + model + ", addedBy=" + addedBy + ", addedDate=" + addedDate + ", active=" + active + "]";
	}


	
	
}
