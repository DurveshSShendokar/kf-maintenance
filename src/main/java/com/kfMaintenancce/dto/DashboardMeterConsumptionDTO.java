package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.ControlPanel;

public class DashboardMeterConsumptionDTO {
 private ControlPanel panel;
 private 	List<MeterConsumptionDTO> consumptions;
public ControlPanel getPanel() {
	return panel;
}
public void setPanel(ControlPanel panel) {
	this.panel = panel;
}
public List<MeterConsumptionDTO> getConsumptions() {
	return consumptions;
}
public void setConsumptions(List<MeterConsumptionDTO> consumptions) {
	this.consumptions = consumptions;
}
 
 
}
