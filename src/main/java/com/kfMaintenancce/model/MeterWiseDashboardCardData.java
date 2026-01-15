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
@Table(name="Meter_wise_dashboard_card_data")
public class MeterWiseDashboardCardData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name="meter_id")
	private EnergyMeterMaster energyMeterMaster;
	
	
	
	
	@Column(name="voltage")
	private String voltage;
	
	
	@Column(name="current")
	private String current;
	
	
	@Column(name="power_factor")
	private String powerFactor;
	
	
	
	@Column(name="total_engery_consumption")
	private String totalengeryConsumption;
	
	
	
	
	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}

	@Column(name="engery_consumption_value")
	private double engeryConsumptionValue;
	
	@Column(name="engery_consumption_time")
	private String engeryConsumptionTime;
	
	@Column(name="power_value")
	private String powerValue;
	
	@Column(name="power_time")
	private String powerTime;
	
	
	@Column(name="apparant_value")
	private String apparantValue;
	
	@Column(name="apparant_time")
	private String apparantTime;
	
	
	@Column(name="reactive_power_value")
	private String reactivePowerValue;
	
	@Column(name="reactive_power_time")
	private String reactivePowerTime;
	
	
	@Column(name="reactive_energy")
	private String reactiveEnergy;
	
	
	
	
	@Column(name="real_energy")
	private String realEnergy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EnergyMeterMaster getEnergyMeterMaster() {
		return energyMeterMaster;
	}

	public void setEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		this.energyMeterMaster = energyMeterMaster;
	}

	public double getEngeryConsumptionValue() {
		return engeryConsumptionValue;
	}

	public void setEngeryConsumptionValue(double engeryConsumptionValue) {
		this.engeryConsumptionValue = engeryConsumptionValue;
	}

	public String getEngeryConsumptionTime() {
		return engeryConsumptionTime;
	}

	public void setEngeryConsumptionTime(String engeryConsumptionTime) {
		this.engeryConsumptionTime = engeryConsumptionTime;
	}

	public String getPowerValue() {
		return powerValue;
	}

	public void setPowerValue(String powerValue) {
		this.powerValue = powerValue;
	}

	public String getPowerTime() {
		return powerTime;
	}

	public void setPowerTime(String powerTime) {
		this.powerTime = powerTime;
	}

	public String getApparantValue() {
		return apparantValue;
	}

	public void setApparantValue(String apparantValue) {
		this.apparantValue = apparantValue;
	}

	public String getApparantTime() {
		return apparantTime;
	}

	public void setApparantTime(String apparantTime) {
		this.apparantTime = apparantTime;
	}

	public String getReactivePowerValue() {
		return reactivePowerValue;
	}

	public void setReactivePowerValue(String reactivePowerValue) {
		this.reactivePowerValue = reactivePowerValue;
	}

	public String getReactivePowerTime() {
		return reactivePowerTime;
	}

	public void setReactivePowerTime(String reactivePowerTime) {
		this.reactivePowerTime = reactivePowerTime;
	}

	public String getTotalengeryConsumption() {
		return totalengeryConsumption;
	}

	public void setTotalengeryConsumption(String totalengeryConsumption) {
		this.totalengeryConsumption = totalengeryConsumption;
	}

	public String getReactiveEnergy() {
		return reactiveEnergy;
	}

	public void setReactiveEnergy(String reactiveEnergy) {
		this.reactiveEnergy = reactiveEnergy;
	}

	public String getRealEnergy() {
		return realEnergy;
	}

	public void setRealEnergy(String realEnergy) {
		this.realEnergy = realEnergy;
	}


	
}
