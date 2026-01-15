package com.kfMaintenancce.dto;

public class CardDataDTO {
	public String voltageAvg;
	public String currentAvg;
	public String apparant;
	public double energyConsumption;
	public String getVoltageAvg() {
		return voltageAvg;
	}
	public void setVoltageAvg(String voltageAvg) {
		this.voltageAvg = voltageAvg;
	}
	public String getCurrentAvg() {
		return currentAvg;
	}
	public void setCurrentAvg(String currentAvg) {
		this.currentAvg = currentAvg;
	}
	public String getApparant() {
		return apparant;
	}
	public void setApparant(String apparant) {
		this.apparant = apparant;
	}
	public double getEnergyConsumption() {
		return energyConsumption;
	}
	public void setEnergyConsumption(double energyConsumption) {
		this.energyConsumption = energyConsumption;
	}
	
	
	
}
