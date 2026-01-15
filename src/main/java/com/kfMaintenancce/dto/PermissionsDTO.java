package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Permissions;

import java.util.List;



public class PermissionsDTO {
  private List<Permissions> machinemasterPermission;
  
  private List<Permissions> emsmasterPermission;
  
  private List<Permissions> assetmasterPermission;
  
  private List<Permissions> breakdownPermission;
  private List<Permissions> maintenanceofmachinePermission;

  private List<Permissions> transactionPermission;

  private List<Permissions> itreportPermission;
  
  private List<Permissions> emsreportPermission;
  
  private List<Permissions> reportPermission;
  
 private List<Permissions> dashboardPermission;
  
  private List<Permissions> configurationPermission;

public List<Permissions> getMachinemasterPermission() {
	return machinemasterPermission;
}

public void setMachinemasterPermission(List<Permissions> machinemasterPermission) {
	this.machinemasterPermission = machinemasterPermission;
}

public List<Permissions> getEmsmasterPermission() {
	return emsmasterPermission;
}

public void setEmsmasterPermission(List<Permissions> emsmasterPermission) {
	this.emsmasterPermission = emsmasterPermission;
}

public List<Permissions> getAssetmasterPermission() {
	return assetmasterPermission;
}

public void setAssetmasterPermission(List<Permissions> assetmasterPermission) {
	this.assetmasterPermission = assetmasterPermission;
}

public List<Permissions> getBreakdownPermission() {
	return breakdownPermission;
}

public void setBreakdownPermission(List<Permissions> breakdownPermission) {
	this.breakdownPermission = breakdownPermission;
}

public List<Permissions> getMaintenanceofmachinePermission() {
	return maintenanceofmachinePermission;
}

public void setMaintenanceofmachinePermission(List<Permissions> maintenanceofmachinePermission) {
	this.maintenanceofmachinePermission = maintenanceofmachinePermission;
}

public List<Permissions> getTransactionPermission() {
	return transactionPermission;
}

public void setTransactionPermission(List<Permissions> transactionPermission) {
	this.transactionPermission = transactionPermission;
}

public List<Permissions> getItreportPermission() {
	return itreportPermission;
}

public void setItreportPermission(List<Permissions> itreportPermission) {
	this.itreportPermission = itreportPermission;
}

public List<Permissions> getEmsreportPermission() {
	return emsreportPermission;
}

public void setEmsreportPermission(List<Permissions> emsreportPermission) {
	this.emsreportPermission = emsreportPermission;
}

public List<Permissions> getReportPermission() {
	return reportPermission;
}

public void setReportPermission(List<Permissions> reportPermission) {
	this.reportPermission = reportPermission;
}

public List<Permissions> getDashboardPermission() {
	return dashboardPermission;
}

public void setDashboardPermission(List<Permissions> dashboardPermission) {
	this.dashboardPermission = dashboardPermission;
}

public List<Permissions> getConfigurationPermission() {
	return configurationPermission;
}

public void setConfigurationPermission(List<Permissions> configurationPermission) {
	this.configurationPermission = configurationPermission;
}
 

  
}
