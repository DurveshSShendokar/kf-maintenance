package com.kfMaintenancce.dto;

public class DateWiseEnergyConsumptionReportDto {
	private String date;
	private double energy;
	private double apparent;
	private double reactive;
	private double demand;
	private double poerFactor;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getApparent() {
		return apparent;
	}
	public void setApparent(double apparent) {
		this.apparent = apparent;
	}
	public double getReactive() {
		return reactive;
	}
	public void setReactive(double reactive) {
		this.reactive = reactive;
	}
	public double getDemand() {
		return demand;
	}
	public void setDemand(double demand) {
		this.demand = demand;
	}
	public double getPoerFactor() {
		return poerFactor;
	}
	public void setPoerFactor(double poerFactor) {
		this.poerFactor = poerFactor;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	

}
