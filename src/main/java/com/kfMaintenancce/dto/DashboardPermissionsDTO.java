package com.kfMaintenancce.dto;

public class DashboardPermissionsDTO {
  private boolean overview;
  
  private boolean install;
  
  private boolean system;
  
  public boolean isOverview() {
    return this.overview;
  }
  
  public void setOverview(boolean overview) {
    this.overview = overview;
  }
  
  public boolean isInstall() {
    return this.install;
  }
  
  public void setInstall(boolean install) {
    this.install = install;
  }
  
  public boolean isSystem() {
    return this.system;
  }
  
  public void setSystem(boolean system) {
    this.system = system;
  }
}
