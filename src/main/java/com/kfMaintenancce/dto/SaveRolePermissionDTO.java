package com.kfMaintenancce.dto;

public class SaveRolePermissionDTO {
  private int roleId;
  
  private int permissionId;
  
  private boolean selected;
  
  private String actionName;
  
  public int getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }
  
  public int getPermissionId() {
    return this.permissionId;
  }
  
  public void setPermissionId(int permissionId) {
    this.permissionId = permissionId;
  }
  
  public boolean isSelected() {
    return this.selected;
  }
  
  public void setSelected(boolean selected) {
    this.selected = selected;
  }
  
  public String getActionName() {
    return this.actionName;
  }
  
  public void setActionName(String actionName) {
    this.actionName = actionName;
  }
}
