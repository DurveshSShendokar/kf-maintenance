package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kfMaintenancce.dto.BreakdownRepoCountDTO;
import com.kfMaintenancce.dto.BreakdownRepoDTO;
import com.kfMaintenancce.dto.BreakdownReportDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTOMob;
import com.kfMaintenancce.dto.BreakdownStatusCounts;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.Spares;

public interface BreakdownServices {

	List<Breakdown> getBreakDownListByMachine(int machine_id);

	void addBreakdown(Breakdown b);

	List<Breakdown> getBreakdownList();

	void deleteBreakdown(int breakdown_id);

	Breakdown getBreakdownById(int breakdown_id);

	Breakdownupdate saveBreakdownUpdate(Breakdownupdate breakdownupdate);

	String getNewBreakDownNo();
	
	 public BreakdownResponseDTOMob getBreakdownDetailsById(int breakdownId);
	 
		public Double calculateTotalConsumedCost(Breakdown breakdown) ;

	List<Breakdown> getBreakdoenById(int breakdown_id);

	List<Spares> getSpareListByBreakdownUpdate(int breakdown_update_id);

	Breakdownupdate getBreakDownUpdateByBreakDown(int breakdown_id);
	List<Breakdown> getOpenBreakDownByMachine(int machine_id);

	List<Breakdown> getTraikBreakDownByMachine(int machine_id);

	List<Breakdown> geClosedBreakDownByUser(int userId);
	
	  List<Breakdown> getOpenBreakdowns();
	    List<Breakdown> getTrialBreakdowns();
	    List<Breakdown> getClosedBreakdowns();
	    List<Breakdown> getAllBreakdowns();

		List<Breakdown> listByUserId(int id);

		List<Breakdown> getClosedBreakDownByMachine(int machine_id);

		List<Breakdown> getBreakdownByDates(Date startDate, Date endDate);

		List<Breakdown> getOpenBreakdownByDates(Date startDate, Date endDate);

		List<Breakdown> getClosedBreakdownByDates(Date startDate, Date endDate);

		List<Breakdown> getTrailBreakdownByDates(Date startDate, Date endDate);

		List<Breakdown> getOpenBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);

		List<Breakdown> getTrailBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);

		List<Breakdown> getClosedBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName);

		List<Breakdown> getBreakdownByDatesAndMachineName(Date startDate, Date endDate, String machineName);

//		void saveSpare(Spares spare);
		public void saveSpare(MaintSpare spare) ;

		List<Breakdown> getTrailBreakdownByUser(int userId);

		   public BreakdownReportDTO generateBreakdownReport(Breakdown breakdown);
		   public List<BreakdownReportDTO> generateBreakdownReportByDateRange(Date startDate, Date endDate);
		   
		   public List<BreakdownReportDTO> generateAllBreakdownReports();
		   
		 //  public List<BreakdownReportDTO> generateBreakdownReportsByUser(int userId) ;
		   
		   public List<BreakdownReportDTO> generateBreakdownReportsByUserAndDate(int userId, Date fromDate, Date toDate);
		   
		   public long countBreakdownsByUserAndDate(int userId, Date fromDate, Date toDate);
		   
		   public Page<BreakdownReportDTO> getPaginatedBreakdownReportsByUser(
		            int userId, Date fromDate, Date toDate, int page, int size);
		   
		   public Page<BreakdownReportDTO> searchPaginatedBreakdownReportsByUser(
		            int userId, Date fromDate, Date toDate, String keyword, int page, int size);
		   
		   public BreakdownRepoCountDTO getBreakdownsAndDaysBetween(int machine_id);
		   public BreakdownRepoCountDTO getBreakdownsAndDaysBetween(int machine_id, Date fromDate, Date toDate);
		   
		   public BreakdownStatusCounts getBreakdownCountsByMachineId(int machineId);
		   
		   public BreakdownStatusCounts getBreakdownCountsByMachineId(int machineId, Date fromDate, Date toDate);
		   
		
		   public List<BreakdownResponseDTO> findAndMarkDuplicates();
		   public BreakdownResponseDTO convertToDTO(Breakdown breakdown, boolean isDuplicate);

		List<Breakdownupdate> getBrekdownUpdatesByBreakdown(String bd_slip);

		Optional<Breakdown> getBreakdownID(int breakdown_id);
		   
		   
}
