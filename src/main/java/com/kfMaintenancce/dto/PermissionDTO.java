package com.kfMaintenancce.dto;

public class PermissionDTO {
  private String permissionName;
  
  private String category;
  
  private boolean permissionAvailable;
  
  private boolean editTab;
  
  public String getPermissionName() {
    return this.permissionName;
  }
  
  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }
  
  public boolean isPermissionAvailable() {
    return this.permissionAvailable;
  }
  
  public void setPermissionAvailable(boolean permissionAvailable) {
    this.permissionAvailable = permissionAvailable;
  }
  
  public boolean isEditTab() {
    return this.editTab;
  }
  
  public void setEditTab(boolean editTab) {
    this.editTab = editTab;
  }
  
  public String getCategory() {
    return this.category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
}
