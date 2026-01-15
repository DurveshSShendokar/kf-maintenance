package com.kfMaintenancce.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kfMaintenancce.dto.BreakdownCounts;
import com.kfMaintenancce.dto.EngineerComplaintCountDTO;
import com.kfMaintenancce.dto.EngineerComplaintRecordDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;

public interface Dashboard1CountService {
    long countMachines();
    long countUsers();
   
    int getBreakdownCount();
//    int getBreakdownCountByStatus(int status);
//	Map<String, Integer> getBreakdownCountsByStatus();
    Map<String, Long> countBreakdownStatuses(List<Breakdown> breakdowns);
	int countMaintenances();
	List<Maint> getDoneMaintenanceByMachine(int machineId);
	Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(int userId, int machine_id);
	List<MachineOwner> getAllMachineOwners();
	
	int getActiveMaintCount();
	int getInactiveMaintCount();
	public Map<String, Long> getEngineerComplaintCount(int user_id);
	public Map<String, Object> getEngineerComplaintRecords(int user_id, int page, int size, String keyword);
	
	List<Breakdown> getBreakdownList();
//	 Map<String, Long> getDoneMaintenanceByMachineOwners(MachineOwner machineOwner);
	 List<Maint> getDoneMaintenanceByMachineOwners(List<MachineOwner> machineOwners);
	 public int getTodayCount(LocalDate date);
	
	 public long countAssets();
	 public long countComplaints();
	 public int getClosedComplaintCount();
	 public int getOpenComplaintCount() ;
	 public int gettodayCounts(Date date) ;
	 public long countAssetsWithAllocate() ;
	  public long countAssetsWithoutAllocate();
	int getOpenMaintenanceByDates(Date startOfWeek, Date endOfWeek);
	int getOverduesMaintenaceByDate(Date startOfWeek);
	int getClosedMaintenanceByDate(Date startOfWeek, Date endOfWeek);
	List<EngineerComplaintCountDTO> getEngineersWithComplaintCounts();
	int getAllocateComplaints();
	int getInprocessComplaints();
	int getNonAllocatedComplaintsCount();
	EngineerComplaintRecordDTO getEngineerComplaintRecords(int user_id);
	
}
