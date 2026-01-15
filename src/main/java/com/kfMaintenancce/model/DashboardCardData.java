package com.kfMaintenancce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dashboard_cardData")
public class DashboardCardData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	double dailyEnergyConsumption;
	String dailyEnergyApparent;
	String avarageVoltage;
	String avarageCurrent;
	String monthlyEnergyConsumption;
	String monthlyEnergyApparent;
	private int meterId;
	public int getMeterId() {
		return meterId;
	}
	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getDailyEnergyConsumption() {
		return dailyEnergyConsumption;
	}
	public void setDailyEnergyConsumption(double dailyEnergyConsumption) {
		this.dailyEnergyConsumption = dailyEnergyConsumption;
	}
	public String getDailyEnergyApparent() {
		return dailyEnergyApparent;
	}
	public void setDailyEnergyApparent(String dailyEnergyApparent) {
		this.dailyEnergyApparent = dailyEnergyApparent;
	}
	public String getAvarageVoltage() {
		return avarageVoltage;
	}
	public void setAvarageVoltage(String avarageVoltage) {
		this.avarageVoltage = avarageVoltage;
	}
	public String getAvarageCurrent() {
		return avarageCurrent;
	}
	public void setAvarageCurrent(String avarageCurrent) {
		this.avarageCurrent = avarageCurrent;
	}
	public String getMonthlyEnergyConsumption() {
		return monthlyEnergyConsumption;
	}
	public void setMonthlyEnergyConsumption(String monthlyEnergyConsumption) {
		this.monthlyEnergyConsumption = monthlyEnergyConsumption;
	}
	public String getMonthlyEnergyApparent() {
		return monthlyEnergyApparent;
	}
	public void setMonthlyEnergyApparent(String monthlyEnergyApparent) {
		this.monthlyEnergyApparent = monthlyEnergyApparent;
	}
	
	
	
	
}
