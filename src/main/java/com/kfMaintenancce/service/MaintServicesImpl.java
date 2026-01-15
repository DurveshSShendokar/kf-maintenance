package com.kfMaintenancce.service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.MaintenanceDetailsDTO;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.repo.ChecklistRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;

@Service
public class MaintServicesImpl implements MaintServices{

	@Autowired
	MaintRepo maintRepo;
	@Autowired
	MaintenenaceCheckPointRepo maintenenaceCheckPointRepo;
	@Autowired
	ChecklistRepo checklistRepo;
	
	
	 public List<Maint> getAllReports() {
	        return maintRepo.findAll();
	    }

	// REMOVE labId from all 3 methods

	 public List<Maint> generateMonthlyReport(String month, String year) {
	     int m = Integer.parseInt(month);
	     int y = Integer.parseInt(year);
	     YearMonth yearMonth = YearMonth.of(y, m);

	     LocalDate startLocalDate = yearMonth.atDay(1);
	     LocalDate endLocalDate = yearMonth.atEndOfMonth();

	     Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	     Date endDate = Date.from(endLocalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

	     return maintRepo.findByScheduleDateBetweens(startDate, endDate);
	 }

	 public List<Maint> generateYearlyReport(String year) {
	     int y = Integer.parseInt(year);
	     LocalDate startLocalDate = LocalDate.of(y, 1, 1);
	     LocalDate endLocalDate = LocalDate.of(y, 12, 31);

	     Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	     Date endDate = Date.from(endLocalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

	     return maintRepo.findByScheduleDateBetweens(startDate, endDate);
	 }

	 public List<Maint> generateDateRangeReport(String fromDate, String toDate) {
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	     LocalDate startLocalDate = LocalDate.parse(fromDate, formatter);
	     LocalDate endLocalDate = LocalDate.parse(toDate, formatter);

	     Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	     Date endDate = Date.from(endLocalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

	     return maintRepo.findByScheduleDateBetweens(startDate, endDate);
	 }

	
	
	
	@Transactional
	public Maint addMaint(Maint maint) {
	    return maintRepo.save(maint);  // Returns the saved entity
	}


	@Override
	public List<Maint> getByMaintId(int maintId) {
		// TODO Auto-generated method stub
		return maintRepo.getByMaintId(maintId);
	}

	@Override
	public List<Maint> getMaintList() {
		// TODO Auto-generated method stub
		return maintRepo.getMaintList();
	}

	@Override
	public List<Maint> getDoneMaintenanceByMachine(int machineId) {
		// TODO Auto-generated method stub
		return maintRepo.getDoneMaintenanceByMachine(machineId);
	}

	@Override
	public List<Maint> getReport(Maint maint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMaint(int maint_id) {
		// TODO Auto-generated method stub
		Optional<Maint> optional = maintRepo.findById(maint_id);
		
		maintRepo.delete(optional.get());
	}

	@Override
	public void saveMaintenenaceChekPoint(MaintenenaceCheckPoint checkPoint) {
		// TODO Auto-generated method stub
		maintenenaceCheckPointRepo.save(checkPoint);
	}

	@Override
	public Maint saveMaint(Maint maint) {
		// TODO Auto-generated method stub
		return maintRepo.save(maint);
	}
	
	@Transactional
	public void saveAllMaintenenaceCheckPoints(List<MaintenenaceCheckPoint> checkPoints) {
		maintenenaceCheckPointRepo.saveAll(checkPoints);
	}


	@Override
	public Maint getMaintById(int maintId) {
		// TODO Auto-generated method stub
		Optional<Maint>  optional=maintRepo.findById(maintId);
		if(optional.isPresent()){
			return optional.get();
		}else{
			return null;
		}
		
	}

	@Override
	public List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency) {
		// TODO Auto-generated method stub
		System.out.println("MACHINE     "+machine_id+"     frq "+frequency);
		return checklistRepo.arrangechecklist(machine_id, frequency);
	}

	@Override
	public List<Maint> getTodayMaintenanceByMachine(int machine_id) {
		// TODO Auto-generated method stub
		Date today=new Date();
		return maintRepo.getTodayMaintenanceByMachine(machine_id,today);
	}

	@Override
	public List<Maint> getThisWeekMaintenanceDownByMachine(int machine_id) {
		// TODO Auto-generated method stub
		
		 Calendar c = Calendar.getInstance();
		    c.setTime(new Date());
		    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		  //  System.out.println("day :" + dayOfWeek);
		    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		 //   System.out.println("start of week day :" + c.getTime());
		    
		    Calendar c1 = Calendar.getInstance();
		    c1.setTime(c.getTime()); // Using today's date
		    c1.add(Calendar.DATE, 6);
		return maintRepo.getThisWeekMaintenanceDownByMachine(machine_id,c.getTime(),c1.getTime());
	}

	@Override
	public List<Maint> getDoneMaintenanceDownByMachine(int machine_id, int userId) {
		// TODO Auto-generated method stub
		return maintRepo.getDoneMaintenanceDownByMachine(machine_id,userId);
	}

	 public List<Maint> getOpenMaintenances() {
	        return maintRepo.getOpenMaintenances();
	    }

	    
	    public List<Maint> getClosedMaintenances() {
	        return maintRepo.getClosedMaintenances();
	    }

	    
	    public List<Maint> getAllMaintenances() {
	        return maintRepo.getAllMaintenances();
	    }

	    public List<Maint> getTodayMaintenanceRecords() {
	        Date today = new Date(); // or use a date utility to get the start of today
	        return maintRepo.getTodayMaintenanceRecords(today);
	    }

		@Override
		public List<Maint> getCurrentWeekOpenMaintenance(Date startOfWeek, Date endOfWeek) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekOpenMaintenance(startOfWeek,endOfWeek);
		}

		@Override
		public List<Maint> getCurrentWeekOverudesMaintenance(Date startOfWeek) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekOverudesMaintenance(startOfWeek);
		}

		@Override
		public List<Maint> getCurrentWeekClosedMaintenance(Date startOfWeek, Date endOfWeek) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekClosedMaintenance(startOfWeek,endOfWeek);
		}

		@Override
		public List<Maint> getCurrentWeekOpenMaintenanceByMachineName(Date startDate, Date endDate,
				String machineName) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekOpenMaintenanceByMachineName(startDate,endDate,machineName);
		}

		@Override
		public List<Maint> getCurrentWeekClosedMaintenanceByMachineName(Date startDate, Date endDate,
				String machineName) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekClosedMaintenanceByMachineName(startDate,endDate,machineName);
		}

		@Override
		public List<Maint> getCurrentWeekOverudesMaintenanceByMachineName(Date startOfWeek, String machineName) {
			// TODO Auto-generated method stub
			return maintRepo.getCurrentWeekOverudesMaintenanceByMachineName(startOfWeek,machineName);
		}
		
		


}
