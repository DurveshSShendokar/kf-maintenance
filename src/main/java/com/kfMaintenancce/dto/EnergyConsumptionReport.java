package com.kfMaintenancce.dto;

import java.util.List;

public class EnergyConsumptionReport {
	  double totalEnergy;
	   double totalApparent;
	   double totalReactive;
	   double totalDemand;
	   double totalPoweFactor;
	   List<DateWiseEnergyConsumptionReportDto> dtos;
	public double getTotalEnergy() {
		return totalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		this.totalEnergy = totalEnergy;
	}
	public double getTotalApparent() {
		return totalApparent;
	}
	public void setTotalApparent(double totalApparent) {
		this.totalApparent = totalApparent;
	}
	public double getTotalReactive() {
		return totalReactive;
	}
	public void setTotalReactive(double totalReactive) {
		this.totalReactive = totalReactive;
	}
	public double getTotalDemand() {
		return totalDemand;
	}
	public void setTotalDemand(double totalDemand) {
		this.totalDemand = totalDemand;
	}
	public double getTotalPoweFactor() {
		return totalPoweFactor;
	}
	public void setTotalPoweFactor(double totalPoweFactor) {
		this.totalPoweFactor = totalPoweFactor;
	}
	public List<DateWiseEnergyConsumptionReportDto> getDtos() {
		return dtos;
	}
	public void setDtos(List<DateWiseEnergyConsumptionReportDto> dtos) {
		this.dtos = dtos;
	}
	   
	   
}
