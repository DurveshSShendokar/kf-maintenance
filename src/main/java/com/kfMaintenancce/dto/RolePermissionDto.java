package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Role;


import java.util.List;

public class RolePermissionDto {
  private Role role;
  
  List<PermissionDTO> permissions;
  
  List<PermissionDTO> permissionsMasters;
  
  List<PermissionDTO> permissionsTransaction;
  
  List<PermissionDTO> permissionsReport;
  
  public Role getRole() {
    return this.role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
  
  public List<PermissionDTO> getPermissions() {
    return this.permissions;
  }
  
  public void setPermissions(List<PermissionDTO> permissions) {
    this.permissions = permissions;
  }
  
  public List<PermissionDTO> getPermissionsMasters() {
    return this.permissionsMasters;
  }
  
  public void setPermissionsMasters(List<PermissionDTO> permissionsMasters) {
    this.permissionsMasters = permissionsMasters;
  }
  
  public List<PermissionDTO> getPermissionsTransaction() {
    return this.permissionsTransaction;
  }
  
  public void setPermissionsTransaction(List<PermissionDTO> permissionsTransaction) {
    this.permissionsTransaction = permissionsTransaction;
  }
  
  public List<PermissionDTO> getPermissionsReport() {
    return this.permissionsReport;
  }
  
  public void setPermissionsReport(List<PermissionDTO> permissionsReport) {
    this.permissionsReport = permissionsReport;
  }
}
