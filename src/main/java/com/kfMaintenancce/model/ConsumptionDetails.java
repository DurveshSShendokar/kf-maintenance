package com.kfMaintenancce.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="consumption_details")
public class ConsumptionDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="panel_id")
	private ControlPanel panel;
	
	@ManyToOne
	@JoinColumn(name="register_id")
	private EnergyMeterRegister energyMeterRegister;
	
	
	
	@ManyToOne
	@JoinColumn(name="energy_meter_id")
	private EnergyMeterMaster energyMeterMaster;
	  
	@Column(name="ip_address")
	private String ipAddress;
	
	
	@Column(name="port")
	private String port;
	
	@Column(name="register_value")
	private String registerValue;
	
	@Column(name="create_date_time")
	private Date createDateTime;
	
	
	@Column(name="other_details")
	private String otherDetails;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ControlPanel getPanel() {
		return panel;
	}


	public void setPanel(ControlPanel panel) {
		this.panel = panel;
	}


	public EnergyMeterRegister getEnergyMeterRegister() {
		return energyMeterRegister;
	}


	public void setEnergyMeterRegister(EnergyMeterRegister energyMeterRegister) {
		this.energyMeterRegister = energyMeterRegister;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getRegisterValue() {
		return registerValue;
	}


	public void setRegisterValue(String registerValue) {
		this.registerValue = registerValue;
	}


	public Date getCreateDateTime() {
		return createDateTime;
	}


	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}


	public String getOtherDetails() {
		return otherDetails;
	}


	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}


	public EnergyMeterMaster getEnergyMeterMaster() {
		return energyMeterMaster;
	}


	public void setEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		this.energyMeterMaster = energyMeterMaster;
	}
	
	
	
	
	
}
