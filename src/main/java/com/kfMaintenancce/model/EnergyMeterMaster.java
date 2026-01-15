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
import javax.persistence.Transient;

@Entity
@Table(name="energy_meter_master")
public class EnergyMeterMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@ManyToOne
	@JoinColumn(name="panel_id")
	private ControlPanel panel;
	
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private EnergyMeterModule module;
	
	
	@Column(name="energy_meter_no")
	private String energyMeterNo;
	
	
	
	
	
	@Column(name="create_date_time")
	private Date createDateTime;

	
	@Transient
	private double currentConsumptionValue;
	
	
	

	public double getCurrentConsumptionValue() {
		return currentConsumptionValue;
	}


	public void setCurrentConsumptionValue(double currentConsumptionValue) {
		this.currentConsumptionValue = currentConsumptionValue;
	}


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


	public EnergyMeterModule getModule() {
		return module;
	}


	public void setModule(EnergyMeterModule module) {
		this.module = module;
	}




	public String getEnergyMeterNo() {
		return energyMeterNo;
	}


	public void setEnergyMeterNo(String energyMeterNo) {
		this.energyMeterNo = energyMeterNo;
	}


	public Date getCreateDateTime() {
		return createDateTime;
	}


	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}


	
	
}
