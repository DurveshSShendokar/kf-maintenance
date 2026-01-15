package com.kfMaintenancce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="control_panel")
public class ControlPanel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="panel_name")
	private String panelName;
	
	@Column(name="panel_ip_address")
	private String panelIpAddress;
	
	
	@Column(name="panel_port")
	private String panelPort;
	
	@Column(name="location_name")
	private String locationName;
	
	@Column(name="plant_name")
	private String plantName;

	@Column(name="create_date_time")
	private Date createDateTime;
	
	@Transient
	List<EnergyMeterMaster> energyMeters;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPanelName() {
		return panelName;
	}

	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}

	public String getPanelIpAddress() {
		return panelIpAddress;
	}

	public void setPanelIpAddress(String panelIpAddress) {
		this.panelIpAddress = panelIpAddress;
	}

	public String getPanelPort() {
		return panelPort;
	}

	public void setPanelPort(String panelPort) {
		this.panelPort = panelPort;
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

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public List<EnergyMeterMaster> getEnergyMeters() {
		return energyMeters;
	}

	public void setEnergyMeters(List<EnergyMeterMaster> energyMeters) {
		this.energyMeters = energyMeters;
	}

	
	
	
	
	
	
	
}
