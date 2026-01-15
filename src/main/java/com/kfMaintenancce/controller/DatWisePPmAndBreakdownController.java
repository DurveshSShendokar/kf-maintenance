package com.kfMaintenancce.controller;
import java.text.DateFormat;
import java.io.Closeable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kfMaintenancce.dto.BreakdownCountsByMachineDTO;
import com.kfMaintenancce.dto.CardDataDTO;
import com.kfMaintenancce.dto.AssetsCountDTO;
import com.kfMaintenancce.dto.BreakdownCountWithRecordsDTO;
import com.kfMaintenancce.dto.ComplaintResponse;
import com.kfMaintenancce.dto.ConsupmtionDashboardDTO;
import com.kfMaintenancce.dto.DashboardCardCountDto;
import com.kfMaintenancce.dto.EngineerComplaintCountDTO;
import com.kfMaintenancce.dto.EngineerComplaintRecordDTO;
import com.kfMaintenancce.dto.GraphResponceDTO;
import com.kfMaintenancce.dto.GraphResponseDTO2;
import com.kfMaintenancce.dto.MaintDashboardRecordsDTO;
import com.kfMaintenancce.dto.MaintenanceCountsByMachineDTO;

import com.kfMaintenancce.dto.MaintenanceResponse;
import com.kfMaintenancce.dto.MonthlyComplaintCountsDTO;
import com.kfMaintenancce.dto.PPMDashboardCountDTO;
import com.kfMaintenancce.dto.TotalsResponse;
import com.kfMaintenancce.dto.TotalsResponse2;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.DayWiseConsumption;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.MeterWiseDashboardCardData;
import com.kfMaintenancce.model.OverviewDahboardCardCount;
import com.kfMaintenancce.model.OverviewDahboardPieChart;
import com.kfMaintenancce.model.OverviewDahboardUnitConsumption;
import com.kfMaintenancce.model.OverviewDahboardVoltageMinMax;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.DailyConsumptionLogRepo;
import com.kfMaintenancce.repo.DayWiseConsumptionRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;
import com.kfMaintenancce.repo.MeterWiseDashboardCardDataRepo;
import com.kfMaintenancce.repo.OverviewDahboardCardCountrrRepo;
import com.kfMaintenancce.repo.OverviewDahboardPieChartRepo;
import com.kfMaintenancce.repo.OverviewDahboardUnitConsumptionRepo;
import com.kfMaintenancce.repo.OverviewDahboardVoltageMinMaxRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.CommanService;
import com.kfMaintenancce.service.Dashboard1CountService;

@RestController
@CrossOrigin("*")
@RequestMapping("/datewise")
public class DatWisePPmAndBreakdownController 
{

    @Autowired
    private Dashboard1CountService dashboardCountService;
    
    @Autowired
	MaintRepo Maintrepo;
    
    @Autowired
	MachineRepo machineRepo;
    
    @Autowired
    private UserDetailsRepo userDetailsRepo;
    
    @Autowired
    private BreakdownRepo breakdownRepo;
    
    @Autowired
    MachineOwnerRepo machineOwnerRepo;
    
    @Autowired
    private ComplaintRepoOLD complaintRepo;
    
    @Autowired
    private CommanService commanService; 
    
    @Autowired
    private OverviewDahboardCardCountrrRepo  overviewDahboardCardCountrrRepo; 
    @Autowired
    private OverviewDahboardPieChartRepo overviewDahboardPieChartRepo;
    @Autowired
    private OverviewDahboardUnitConsumptionRepo overviewDahboardUnitConsumptionRepo;
    @Autowired
     OverviewDahboardVoltageMinMaxRepo overviewDahboardVoltageMinMaxRepo;
    
	@Autowired
	MaintenenaceCheckPointRepo  maintenenaceCheckPointRepo ; 
    
	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	@Autowired
	EnergyMeterMasterRepo energyMeterMasterRepo;
	
	@Autowired
	DailyConsumptionLogRepo dailyConsumptionLogRepo;
	@Autowired
	DayWiseConsumptionRepo dayWiseConsumptionRepo;
	@Autowired
	MeterWiseDashboardCardDataRepo meterWiseDashboardCardDataRepo;
    //************************************* DASHBOARD FOR OVERVIEW ***********************************//
    
    
	
	@GetMapping(value = "/total_open_maintenance")
	public List<Maint> getOpenMaintenanceRecords(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    return Maintrepo.findOpenMaintenancesByDateRange(fromDate, toDate);
	}
	
	 // ✅ 1. Pagination
    @GetMapping("/total_open_maintenanceWithPaginated")
    public Page<Maint> getOpenMaintenancePaginated(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        return Maintrepo.findOpenMaintenancesByDateRange(fromDate, toDate, pageable);
    }

    // ✅ 2. Pagination + Search
    @GetMapping("/total_open_maintenanceWithSearch")
    public Page<Maint> searchOpenMaintenancePaginated(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        return Maintrepo.searchOpenMaintenances(fromDate, toDate, keyword, pageable);
    }

    // ✅ 3. Count API
    @GetMapping("/total_open_maintenanceWithCount")
    public long countOpenMaintenanceRecords(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return Maintrepo.countOpenMaintenances(fromDate, toDate);
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping(value = "/total_closed_maintenance")
	public List<Maint> getClosedMaintenanceRecords(
			  @RequestParam(value = "fromDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

		        @RequestParam(value = "toDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate)

		    {
	    return Maintrepo.findClosedMaintenancesByDateRange(fromDate, toDate);
	}
	
    
    // ✅ 1️⃣ Pagination API
    @GetMapping("/total_closedmaintenanceWithPaginated")
    public Page<Maint> getClosedMaintenancePaginated(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Maint> result = Maintrepo.findClosedMaintenancesByDateRange(fromDate, toDate, pageable);

        result.forEach(maint -> {
            List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
            StringBuilder checkListStr = new StringBuilder();
            int i = 1;
            for (MaintenenaceCheckPoint cp : checkPoints) {
                checkListStr.append(i++).append(". ")
                        .append(cp.getCheckpoint().getTask())
                        .append(" - ").append(cp.getStatus())
                        .append("/");
            }
            maint.setCheckListStr(checkListStr.toString());
        });

        return result;
    }


    @GetMapping("/total_closedmaintenanceWithSearch")
    public Page<Maint> searchClosedMaintenancePaginated(
            @RequestParam (defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Maint> result = Maintrepo.searchClosedMaintenancesByDateRange(keyword, fromDate, toDate, pageable);

        result.forEach(maint -> {
            List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
            StringBuilder checkListStr = new StringBuilder();
            int i = 1;
            for (MaintenenaceCheckPoint cp : checkPoints) {
                checkListStr.append(i++).append(". ")
                        .append(cp.getCheckpoint().getTask())
                        .append(" - ").append(cp.getStatus())
                        .append("/");
            }
            maint.setCheckListStr(checkListStr.toString());
        });

        return result;
    }

    // ✅ 3️⃣ Count API
    @GetMapping("/total_closedmaintenanceWithCount")
    public long countClosedMaintenances(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return Maintrepo.countClosedMaintenancesByDateRange(fromDate, toDate);
    }

    
    ////////////////////////////////////////////////////////////////
    
    @GetMapping(value = "/getOverduesMaintenanceByDateRange", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getOverduesMaintenanceByDateRange(
			  @RequestParam(value = "fromDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

		        @RequestParam(value = "toDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate)

		         {
	    List<Maint> overduesMaintList = new ArrayList<>();
	    try {
	        overduesMaintList = Maintrepo.getOverduesMaintenanceByDateRange(fromDate, toDate);
	        int i = 1;
	        for (Maint maint : overduesMaintList) {
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);
	            if (maint.getRaisedBy() != null) {
	                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            }

	            maint.setWeek(weekOfYear);
	            Date endDate = new Date(); 
	            long diffInMillis = endDate.getTime() - maint.getSchedule_date().getTime();
	            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

	            maint.setOverdues(diffInDays + " Day");
	            System.out.println("diffInDays: " + diffInDays);
	            i++;

	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String strDate = dateFormat.format(maint.getSchedule_date());
	            maint.setScheduleDateStr(strDate);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return overduesMaintList;
	}


    // ✅ 1️⃣ Pagination API
    @GetMapping(value = "/getOverduesMaintenanceByDateRangeWithPaginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Maint> getOverduesPaginated(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	      
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> result = Maintrepo.findOverduesMaintenanceByDateRange(fromDate, toDate, pageable);

        enrichMaintenanceData(result.getContent());
        return result;
    }

    // ✅ 2️⃣ Pagination + Search API
    @GetMapping(value = "/getOverduesMaintenanceByDateRangeWithSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Maint> searchOverduesPaginated(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> result = Maintrepo.searchOverduesMaintenance(fromDate, toDate, keyword, pageable);

        enrichMaintenanceData(result.getContent());
        return result;
    }

    // ✅ 3️⃣ Count API
    @GetMapping("/getOverduesMaintenanceByDateRangeWithCount")
    public long countOverduesMaintenance(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return Maintrepo.countOverduesMaintenance(fromDate, toDate);
    }

    // ✅ Helper method for your additional fields
    private void enrichMaintenanceData(List<Maint> maintList) {
        int i = 1;
        for (Maint maint : maintList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(maint.getSchedule_date());
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            maint.setSrNo(i);
            i++;

            if (maint.getRaisedBy() != null) {
                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
            }

            maint.setWeek(weekOfYear);

            // Calculate overdue days
            Date now = new Date();
            long diffInMillis = now.getTime() - maint.getSchedule_date().getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            maint.setOverdues(diffInDays + " Day");

            // Format date string
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            maint.setScheduleDateStr(dateFormat.format(maint.getSchedule_date()));
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    
    
    
    @GetMapping("/getClosedApprovalsByDateRange")
    public ResponseEntity<Map<String, Object>> getClosedApprovalsByDateRange(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        List<Maint> closedApprovals = Maintrepo.findClosedApprovalsByDateRange(fromDate, toDate);

        if (closedApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : closedApprovals) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", closedApprovals.size());
        response.put("datas", closedApprovals);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getClosedApprovalsByDateRangeWithPagination")
    public ResponseEntity<Map<String, Object>> getClosedApprovalsByDateRangePaginated(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	       
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> pageResult = Maintrepo.findClosedApprovalsByDateRange(fromDate, toDate, pageable);

        if (pageResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : pageResult.getContent()) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("datas", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getClosedApprovalsByDateRangeWithSearch")
    public ResponseEntity<Map<String, Object>> searchClosedApprovalsByDateRange(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        // ✅ Trim spaces
        keyword = keyword.trim().toLowerCase();

        // ✅ Expand end date to include full day (important!)
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        toDate = cal.getTime();

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Maint> pageResult = Maintrepo.searchClosedApprovalsByDateRangeNative(fromDate, toDate, keyword, pageable);
        System.out.println("##########################################fromDate=" + fromDate + ", toDate=" + toDate + ", keyword='" + keyword + "'");
        if (pageResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : pageResult.getContent()) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }
      

        Map<String, Object> response = new HashMap<>();
        response.put("datas", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }
 
    @GetMapping("/getClosedApprovalsByDateRangeWithCount")
    public ResponseEntity<Map<String, Object>> countClosedApprovalsByDateRange(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        long count = Maintrepo.countClosedApprovalsByDateRange(fromDate, toDate);

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
    
    //////////////////////////////////////////////////////////
    @GetMapping("/getUnApprovalsByDate")
    public ResponseEntity<Map<String, Object>> getUnApprovals(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate

    	       ) {
        
        List<Maint> unApprovals = Maintrepo.findUnApprovalsByDate(fromDate, toDate);

        if (unApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : unApprovals) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", unApprovals.size());
        response.put("datas", unApprovals);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getUnApprovalsByDateWithpPagination")
    public ResponseEntity<Map<String, Object>> getUnApprovalsByDatePaginated(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	       
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> pageResult = Maintrepo.findUnApprovalsByDatePaginated(fromDate, toDate, pageable);

        if (pageResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : pageResult.getContent()) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("datas", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getUnApprovalsByDateWithSearch")
    public ResponseEntity<Map<String, Object>> searchUnApprovalsByDate(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> pageResult = Maintrepo.searchUnApprovalsByDate(fromDate, toDate, keyword, pageable);

        if (pageResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : pageResult.getContent()) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("datas", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/getUnApprovalsByDate/count")
    public ResponseEntity<Map<String, Object>> countUnApprovalsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {

        long count = Maintrepo.countUnApprovalsByDate(fromDate, toDate);

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
//////////////////////////////////////////////////////////
    


private Date adjustEndDate(Date toDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(toDate);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTime();
}
    
    @GetMapping("/datewiseBreakdownTrial")
    public @ResponseBody List<Breakdown> getTrialBreakdowns(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        try {
            toDate = adjustEndDate(toDate);
            return breakdownRepo.getTrialBreakdownsByDateRange(fromDate, toDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/datewiseBreakdownTrialWithPagination")
    public ResponseEntity<Map<String, Object>> getTrialBreakdownsPaginated(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        try {
            toDate = adjustEndDate(toDate);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Breakdown> pageResult = breakdownRepo.findTrialBreakdownsByDateRangePaginated(fromDate, toDate, pageable);

            if (pageResult.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("datas", pageResult.getContent());
            response.put("currentPage", pageResult.getNumber());
            response.put("totalItems", pageResult.getTotalElements());
            response.put("totalPages", pageResult.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/datewiseBreakdownTrialWithSearch")
    public ResponseEntity<Map<String, Object>> searchTrialBreakdowns(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        try {
            toDate = adjustEndDate(toDate);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Breakdown> pageResult = breakdownRepo.searchTrialBreakdownsByDateRange(fromDate, toDate, keyword, pageable);

            if (pageResult.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("datas", pageResult.getContent());
            response.put("currentPage", pageResult.getNumber());
            response.put("totalItems", pageResult.getTotalElements());
            response.put("totalPages", pageResult.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/datewiseBreakdownTrialWithCount")
    public ResponseEntity<Map<String, Object>> countTrialBreakdowns(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        try {
            toDate = adjustEndDate(toDate);
            long count = breakdownRepo.countTrialBreakdownsByDateRange(fromDate, toDate);

            Map<String, Object> response = new HashMap<>();
            response.put("count", count);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
    
    /////////////////////////////////////////////////////////
    
    @GetMapping("/datewiseBreakdownClosed")
    public @ResponseBody List<Breakdown> getClosedBreakdowns(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        try {
            toDate = adjustEndDate(toDate);
            return breakdownRepo.getClosedBreakdownsByDateRange(fromDate, toDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/datewiseBreakdownClosedWithPagination")
    public ResponseEntity<Map<String, Object>> getClosedBreakdownsPaginated(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        try {
            toDate = adjustEndDate(toDate);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Breakdown> pageResult = breakdownRepo.findClosedBreakdownsByDateRangePaginated(fromDate, toDate, pageable);

            if (pageResult.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("datas", pageResult.getContent());
            response.put("currentPage", pageResult.getNumber());
            response.put("totalItems", pageResult.getTotalElements());
            response.put("totalPages", pageResult.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/datewiseBreakdownClosedWithSearch")
    public ResponseEntity<Map<String, Object>> searchClosedBreakdowns(
    		  @RequestParam(value = "fromDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

    	        @RequestParam(value = "toDate", required = false)
    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

    	        @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        try {
            toDate = adjustEndDate(toDate);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Breakdown> pageResult = breakdownRepo.searchClosedBreakdownsByDateRange(fromDate, toDate, keyword, pageable);

            if (pageResult.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("datas", pageResult.getContent());
            response.put("currentPage", pageResult.getNumber());
            response.put("totalItems", pageResult.getTotalElements());
            response.put("totalPages", pageResult.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/datewiseBreakdownClosedWithCount")
    public ResponseEntity<Map<String, Object>> countClosedBreakdowns(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        try {
            toDate = adjustEndDate(toDate);
            long count = breakdownRepo.countClosedBreakdownsByDateRange(fromDate, toDate);

            Map<String, Object> response = new HashMap<>();
            response.put("count", count);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /////////////////////////////////////////////////////////////////////////
    
    
    
	  @GetMapping("/datewiseBreakdownOpen")
	  public @ResponseBody List<Breakdown> getOpenBreakdowns(
			  @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getOpenBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  @GetMapping("/datewiseBreakdownOpenWithPagination")
	  public ResponseEntity<Map<String, Object>> getOpenBreakdownsPaginated(
	          @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	          @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	          @RequestParam int page,
	          @RequestParam int size) {

	      try {
	          toDate = adjustEndDate(toDate);
	          Pageable pageable = PageRequest.of(page-1, size);
	          Page<Breakdown> pageResult = breakdownRepo.findOpenBreakdownsByDateRangePaginated(fromDate, toDate, pageable);

	          if (pageResult.isEmpty()) {
	              return ResponseEntity.noContent().build();
	          }

	          Map<String, Object> response = new HashMap<>();
	          response.put("datas", pageResult.getContent());
	          response.put("currentPage", pageResult.getNumber());
	          response.put("totalItems", pageResult.getTotalElements());
	          response.put("totalPages", pageResult.getTotalPages());

	          return ResponseEntity.ok(response);

	      } catch (Exception e) {
	          e.printStackTrace();
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                  .body(Map.of("error", e.getMessage()));
	      }
	  }
	  @GetMapping("/datewiseBreakdownOpenWithSearch")
	  public ResponseEntity<Map<String, Object>> searchOpenBreakdowns(
			  @RequestParam(value = "fromDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

		        @RequestParam(value = "toDate", required = false)
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

		        @RequestParam(defaultValue = "") String keyword,
	          @RequestParam int page,
	          @RequestParam int size) {

	      try {
	          toDate = adjustEndDate(toDate);
	          Pageable pageable = PageRequest.of(page-1, size);
	          Page<Breakdown> pageResult = breakdownRepo.searchOpenBreakdownsByDateRange(fromDate, toDate, keyword, pageable);

	          if (pageResult.isEmpty()) {
	              return ResponseEntity.noContent().build();
	          }

	          Map<String, Object> response = new HashMap<>();
	          response.put("datas", pageResult.getContent());
	          response.put("currentPage", pageResult.getNumber());
	          response.put("totalItems", pageResult.getTotalElements());
	          response.put("totalPages", pageResult.getTotalPages());

	          return ResponseEntity.ok(response);

	      } catch (Exception e) {
	          e.printStackTrace();
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                  .body(Map.of("error", e.getMessage()));
	      }
	  }
	  @GetMapping("/datewiseBreakdownOpenWithCount")
	  public ResponseEntity<Map<String, Object>> countOpenBreakdowns(
	          @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	          @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

	      try {
	          toDate = adjustEndDate(toDate);
	          long count = breakdownRepo.countOpenBreakdownsByDateRange(fromDate, toDate);

	          Map<String, Object> response = new HashMap<>();
	          response.put("count", count);

	          return ResponseEntity.ok(response);

	      } catch (Exception e) {
	          e.printStackTrace();
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                  .body(Map.of("error", e.getMessage()));
	      }
	  }
////////////////////////////////////////////////////////////////////////
	    @GetMapping("/datewiseBreakdownAll")
	    public @ResponseBody List<Breakdown> getAllBreakdownsByDateRange(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getAllBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    @GetMapping("/datewiseBreakdownAllWithPagination")
	    public ResponseEntity<Map<String, Object>> getAllBreakdownsPaginated(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	            @RequestParam int page,
	            @RequestParam int size) {

	        try {
	            toDate = adjustEndDate(toDate);
	            Pageable pageable = PageRequest.of(page-1, size);
	            Page<Breakdown> pageResult = breakdownRepo.findAllBreakdownsByDateRangePaginated(fromDate, toDate, pageable);

	            if (pageResult.isEmpty()) {
	                return ResponseEntity.noContent().build();
	            }

	            Map<String, Object> response = new HashMap<>();
	            response.put("datas", pageResult.getContent());
	            response.put("currentPage", pageResult.getNumber());
	            response.put("totalItems", pageResult.getTotalElements());
	            response.put("totalPages", pageResult.getTotalPages());

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", e.getMessage()));
	        }
	    }

	    
	    @GetMapping("/datewiseBreakdownAllWithSearch")
	    public ResponseEntity<Map<String, Object>> searchAllBreakdowns(
	    		  @RequestParam(value = "fromDate", required = false)
	    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

	    	        @RequestParam(value = "toDate", required = false)
	    	        @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

	    	        @RequestParam(defaultValue = "") String keyword,
	            @RequestParam int page,
	            @RequestParam int size) {

	        try {
	            toDate = adjustEndDate(toDate);
	            Pageable pageable = PageRequest.of(page-1, size);
	            Page<Breakdown> pageResult = breakdownRepo.searchAllBreakdownsByDateRange(fromDate, toDate, keyword, pageable);

	            if (pageResult.isEmpty()) {
	                return ResponseEntity.noContent().build();
	            }

	            Map<String, Object> response = new HashMap<>();
	            response.put("datas", pageResult.getContent());
	            response.put("currentPage", pageResult.getNumber());
	            response.put("totalItems", pageResult.getTotalElements());
	            response.put("totalPages", pageResult.getTotalPages());

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", e.getMessage()));
	        }
	    }

	    @GetMapping("/datewiseBreakdownAllWithCount")
	    public ResponseEntity<Map<String, Object>> countAllBreakdowns(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

	        try {
	            toDate = adjustEndDate(toDate);
	            long count = breakdownRepo.countAllBreakdownsByDateRange(fromDate, toDate);

	            Map<String, Object> response = new HashMap<>();
	            response.put("count", count);

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", e.getMessage()));
	        }
	    }

	    
	    
}
