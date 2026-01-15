package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.EnergyMeterMaster;

public class ConsupmtionReportDTO {
	private String Date;
	private double consupmtionUnit;
	private double consupmtionCost;
	private EnergyMeterMaster energyMeterMaster;
	private String month;
	private String year;
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public double getConsupmtionUnit() {
		return consupmtionUnit;
	}
	public void setConsupmtionUnit(double consupmtionUnit) {
		this.consupmtionUnit = consupmtionUnit;
	}
	public double getConsupmtionCost() {
		return consupmtionCost;
	}
	public void setConsupmtionCost(double consupmtionCost) {
		this.consupmtionCost = consupmtionCost;
	}
	public EnergyMeterMaster getEnergyMeterMaster() {
		return energyMeterMaster;
	}
	public void setEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		this.energyMeterMaster = energyMeterMaster;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
