package com.kfMaintenancce.dto;

public class CategoryPermissionCountDto {
  private int mastercount;
  
  private int transactioncount;
  
  private int reportcount;
  
  private int dashbaoedcount;
  
  public int getMastercount() {
    return this.mastercount;
  }
  
  public void setMastercount(int mastercount) {
    this.mastercount = mastercount;
  }
  
  public int getTransactioncount() {
    return this.transactioncount;
  }
  
  public void setTransactioncount(int transactioncount) {
    this.transactioncount = transactioncount;
  }
  
  public int getReportcount() {
    return this.reportcount;
  }
  
  public void setReportcount(int reportcount) {
    this.reportcount = reportcount;
  }
  
  public int getDashbaoedcount() {
    return this.dashbaoedcount;
  }
  
  public void setDashbaoedcount(int dashbaoedcount) {
    this.dashbaoedcount = dashbaoedcount;
  }
}
