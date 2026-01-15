package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kfMaintenancce.dto.MaintenanceDetailsDTO;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;

public interface MaintServices {

	//void addMaint(Maint maint);
	public Maint addMaint(Maint maint);
	List<Maint> getByMaintId(int maintId);
	List<Maint> getMaintList();
	
	
	 public List<Maint> generateMonthlyReport(String month, String year);
	  public List<Maint> getAllReports();
	  public List<Maint> generateYearlyReport(String year);
	   public List<Maint> generateDateRangeReport(String fromDate, String toDate);
	  

	List<Maint> getDoneMaintenanceByMachine(int machineId);
	List<Maint> getReport(Maint maint);
	void deleteMaint(int maint_id);

	void saveMaintenenaceChekPoint(MaintenenaceCheckPoint checkPoint);

	Maint saveMaint(Maint maint);

	Maint getMaintById(int maintId);
	
	public void saveAllMaintenenaceCheckPoints(List<MaintenenaceCheckPoint> checkPoints);

	List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency);

	List<Maint> getTodayMaintenanceByMachine(int machine_id);

	List<Maint> getThisWeekMaintenanceDownByMachine(int machine_id);

	List<Maint> getDoneMaintenanceDownByMachine(int machine_id, int userId);
	
	  List<Maint> getOpenMaintenances();
	

	    List<Maint> getClosedMaintenances();

	    List<Maint> getAllMaintenances();
	    public List<Maint> getTodayMaintenanceRecords() ;

		List<Maint> getCurrentWeekOpenMaintenance(Date startOfWeek, Date endOfWeek);

		List<Maint> getCurrentWeekOverudesMaintenance(Date startOfWeek);

		List<Maint> getCurrentWeekClosedMaintenance(Date startOfWeek, Date endOfWeek);

		List<Maint> getCurrentWeekOpenMaintenanceByMachineName(Date startDate, Date endDate, String machineName);

		List<Maint> getCurrentWeekClosedMaintenanceByMachineName(Date startDate, Date endDate, String machineName);

		List<Maint> getCurrentWeekOverudesMaintenanceByMachineName(Date startOfWeek, String machineName);

}
