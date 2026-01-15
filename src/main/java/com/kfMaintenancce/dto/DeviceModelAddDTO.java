package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.DeviceModel;
import com.kfMaintenancce.model.ModBusAddress;

public class DeviceModelAddDTO {
private String deviceModelName;
private DeviceModel deviceModel;
private boolean newDeviceMode;

private List<ModBusAddress> address;

public String getDeviceModelName() {
	return deviceModelName;
}

public void setDeviceModelName(String deviceModelName) {
	this.deviceModelName = deviceModelName;
}

public DeviceModel getDeviceModel() {
	return deviceModel;
}

public void setDeviceModel(DeviceModel deviceModel) {
	this.deviceModel = deviceModel;
}

public boolean isNewDeviceMode() {
	return newDeviceMode;
}

public void setNewDeviceMode(boolean newDeviceMode) {
	this.newDeviceMode = newDeviceMode;
}

public List<ModBusAddress> getAddress() {
	return address;
}

public void setAddress(List<ModBusAddress> address) {
	this.address = address;
}

@Override
public String toString() {
	return "DeviceModelAddDTO [deviceModelName=" + deviceModelName + ", deviceModel=" + deviceModel + ", newDeviceMode="
			+ newDeviceMode + ", address=" + address + "]";
}




}
