package com.kfMaintenancce.dto;

import java.util.Date;

import com.kfMaintenancce.model.EnergyMeterMaster;

public class ReportRequestDTO {

	private Date startDate;
	private Date endDate;
	private String type;
	private EnergyMeterMaster module;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public EnergyMeterMaster getModule() {
		return module;
	}
	public void setModule(EnergyMeterMaster module) {
		this.module = module;
	}
	
	
	
	
}
