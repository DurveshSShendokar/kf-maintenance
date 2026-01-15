package com.kfMaintenancce.dto;

import java.util.List;

public class MachineWeekDTO {
private String machineName;
private List<WeekDataDTO> weekDatas;
public String getMachineName() {
	return machineName;
}
public void setMachineName(String machineName) {
	this.machineName = machineName;
}
public List<WeekDataDTO> getWeekDatas() {
	return weekDatas;
}
public void setWeekDatas(List<WeekDataDTO> weekDatas) {
	this.weekDatas = weekDatas;
}


}
