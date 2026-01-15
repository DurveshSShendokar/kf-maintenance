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
@Table(name="day_wise_consumption")
public class DayWiseConsumption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="register_id")
	private EnergyMeterRegister energyMeterRegister;
	
	
	
	@ManyToOne
	@JoinColumn(name="energy_meter_id")
	private EnergyMeterMaster energyMeterMaster;
	  
	
	@Column(name="register_value")
	private double registerValue;
	
	
	@Column(name="consumption_date")
	private Date consumptionDate;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public EnergyMeterRegister getEnergyMeterRegister() {
		return energyMeterRegister;
	}


	public void setEnergyMeterRegister(EnergyMeterRegister energyMeterRegister) {
		this.energyMeterRegister = energyMeterRegister;
	}


	public EnergyMeterMaster getEnergyMeterMaster() {
		return energyMeterMaster;
	}


	public void setEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		this.energyMeterMaster = energyMeterMaster;
	}


	public double getRegisterValue() {
		return registerValue;
	}


	public void setRegisterValue(double registerValue) {
		this.registerValue = registerValue;
	}


	public Date getConsumptionDate() {
		return consumptionDate;
	}


	public void setConsumptionDate(Date consumptionDate) {
		this.consumptionDate = consumptionDate;
	}
	
	
	
	
}
