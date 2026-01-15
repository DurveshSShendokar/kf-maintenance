package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.PermissionAction;
import com.kfMaintenancce.model.Permissions;
import com.kfMaintenancce.model.Role;

import java.util.List;


public class PermissionActionDTO {
  private Role role;
  
  private Permissions permissions;
  
  List<PermissionAction> actions;
  
  public Role getRole() {
    return this.role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
  
  public Permissions getPermissions() {
    return this.permissions;
  }
  
  public void setPermissions(Permissions permissions) {
    this.permissions = permissions;
  }
  
  public List<PermissionAction> getActions() {
    return this.actions;
  }
  
  public void setActions(List<PermissionAction> actions) {
    this.actions = actions;
  }
}
