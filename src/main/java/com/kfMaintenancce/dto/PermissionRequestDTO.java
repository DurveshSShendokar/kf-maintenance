package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.PermissionAction;

import java.util.List;

public class PermissionRequestDTO {
	private String category;
	private int permissionsId;
	private List<PermissionAction> actions;
	private String remark;
	private int userId;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPermissionsId() {
		return permissionsId;
	}
	public void setPermissionsId(int permissionsId) {
		this.permissionsId = permissionsId;
	}
	public List<PermissionAction> getActions() {
		return actions;
	}
	public void setActions(List<PermissionAction> actions) {
		this.actions = actions;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "PermissionRequestDTO [category=" + category + ", permissionsId=" + permissionsId + ", actions="
				+ actions + ", remark=" + remark + ", userId=" + userId + "]";
	}
	
	

}
