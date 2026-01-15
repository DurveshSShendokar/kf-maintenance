package com.kfMaintenancce.dto;

public class EMSDAshboardCardDTO {
	private double todayConsumption;
	private double monthlyConsumption;
	private double noOfMeter;
	
	private double todayCost;
	private double monthlyCost;
	private double noOfLocation;
	public double getTodayConsumption() {
		return todayConsumption;
	}
	public void setTodayConsumption(double todayConsumption) {
		this.todayConsumption = todayConsumption;
	}
	public double getMonthlyConsumption() {
		return monthlyConsumption;
	}
	public void setMonthlyConsumption(double monthlyConsumption) {
		this.monthlyConsumption = monthlyConsumption;
	}
	public double getNoOfMeter() {
		return noOfMeter;
	}
	public void setNoOfMeter(double noOfMeter) {
		this.noOfMeter = noOfMeter;
	}
	public double getTodayCost() {
		return todayCost;
	}
	public void setTodayCost(double todayCost) {
		this.todayCost = todayCost;
	}
	public double getMonthlyCost() {
		return monthlyCost;
	}
	public void setMonthlyCost(double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}
	public double getNoOfLocation() {
		return noOfLocation;
	}
	public void setNoOfLocation(double noOfLocation) {
		this.noOfLocation = noOfLocation;
	}
	
	

}
