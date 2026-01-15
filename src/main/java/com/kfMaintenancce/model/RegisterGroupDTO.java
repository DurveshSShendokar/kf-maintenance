package com.kfMaintenancce.model;

import java.util.List;

import com.kfMaintenancce.dto.RegisterDTO;

public class RegisterGroupDTO {
private String groupName;
private boolean showView;
private List<RegisterDTO>  list;
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public List<RegisterDTO> getList() {
	return list;
}
public void setList(List<RegisterDTO> list) {
	this.list = list;
}
public boolean isShowView() {
	return showView;
}
public void setShowView(boolean showView) {
	this.showView = showView;
}

}
