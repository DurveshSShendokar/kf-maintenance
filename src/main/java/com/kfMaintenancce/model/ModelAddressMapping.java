package com.kfMaintenancce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="model_address")
public class ModelAddressMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int model_address_id;
	
	
	@ManyToOne
	@JoinColumn(name="device_model_id")
	private DeviceModel deviceModel;
	
	@ManyToOne
	@JoinColumn(name="mod_address_id")
	private ModBusAddress address;

	public int getModel_address_id() {
		return model_address_id;
	}

	public void setModel_address_id(int model_address_id) {
		this.model_address_id = model_address_id;
	}

	public DeviceModel getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public ModBusAddress getAddress() {
		return address;
	}

	public void setAddress(ModBusAddress address) {
		this.address = address;
	}
	
	

}
