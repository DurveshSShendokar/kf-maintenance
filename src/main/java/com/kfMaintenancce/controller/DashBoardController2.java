package com.kfMaintenancce.controller;
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
@RequestMapping("/dashboard2")
public class DashBoardController2 
{

    @Autowired
    private Dashboard1CountService dashboardCountService;
    
    @Autowired
	MaintRepo maintRepo;
    
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
    
    
  

    
    @GetMapping("/maintenanceDashboard")
    public PPMDashboardCountDTO getMaintenanceDashboard() {
        PPMDashboardCountDTO dto = new PPMDashboardCountDTO();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        // ✅ Counts
        int openMaintence = dashboardCountService.getOpenMaintenanceByDates(startOfWeek, endOfWeek);
        int closed = dashboardCountService.getClosedMaintenanceByDate(startOfWeek, endOfWeek);
        int overduesMaintenace = dashboardCountService.getOverduesMaintenaceByDate(new Date());
        int totalOpenMaintenance = maintRepo.countOpeneMaintenancesByDate(new Date());
        int totalClosedMaintenance = maintRepo.countInactiveMaintenances();

        int totalApprovedClosedPPM = maintRepo.findClosedApprovals().size();
        int totalUnApprovedClosedPPM = maintRepo.findUnApprovals().size();

        int totalMaintence = maintRepo.countTotalMaintenances();

        // ✅ Records
        List<Maint> approvedClosedPPMRecords = maintRepo.findClosedApprovals();
        List<Maint> unApprovedClosedPPMRecords = maintRepo.findUnApprovals();
        List<Maint> openMaintenanceRecords = maintRepo.findOpenMaintenanceRecords();
        List<Maint> overdueMaintenanceRecords = maintRepo.findOverdueMaintenanceRecords();
        List<Maint> closedMaintenanceRecords = maintRepo.findClosedMaintenanceRecords();
        List<Maint> totalMaintenanceRecords = maintRepo.findAllMaintenanceRecords();
        
        

        // ✅ Set values to DTO
        dto.setOpenMaintence(openMaintence);
        dto.setClosed(closed);
        dto.setOverduesMaintenace(overduesMaintenace);
        dto.setTotalMaintence(totalMaintence);
        dto.setTotalOpenMaintenance(totalOpenMaintenance);
        dto.setTotalClosedMaintenance(totalClosedMaintenance);
        dto.setTotalApprovedClosedPPM(totalApprovedClosedPPM);
        dto.setTotalUnApprovedClosedPPM(totalUnApprovedClosedPPM);

        dto.setApprovedClosedPPMRecords(approvedClosedPPMRecords);
        dto.setUnApprovedClosedPPMRecords(unApprovedClosedPPMRecords);
        dto.setOpenMaintenanceRecords(openMaintenanceRecords);
        dto.setOverduesMaintenanceRecords(overdueMaintenanceRecords);
        dto.setClosedRecords(closedMaintenanceRecords);
        dto.setTotalMaintenanceRecords(totalMaintenanceRecords);

        return dto;
    }

    
 // ✅ Approved Closed PPM
    @GetMapping("/approvedclosedppm_Pagination")
    public List<Maint> getApprovedClosedPPM(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("closedDate").descending());
        return maintRepo.findApprovedClosedPPM(pageable).getContent();
    }

    @GetMapping("/approvedclosedppm_PaginationSearch")
    public List<Maint> searchApprovedClosedPPM(@RequestParam int page,
                                               @RequestParam int size,
                                               @RequestParam(defaultValue = "") String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("closedDate").descending());
        return maintRepo.searchApprovedClosedPPM(keyword, pageable).getContent();
    }
    
    @GetMapping("/approvedclosedppm_PaginationSearchDatewise")
    public List<Maint> searchApprovedClosedPPMs(
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,

            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("closedDate").descending());
        Page<Maint> result = maintRepo.searchApprovedClosedPPMs(fromDate, toDate, keyword, pageable);
        return result.getContent();
    }


    // ✅ UnApproved Closed PPM
    @GetMapping("/unapprovedclosedppm_Pagination")
    public List<Maint> getUnApprovedClosedPPM(@RequestParam int page, @RequestParam int size) {
        return maintRepo.findUnApprovedClosedPPM(PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/unapproved_closed_ppm_PaginationSearch")
    public List<Maint> searchUnApprovedClosedPPM(@RequestParam int page,
                                                 @RequestParam int size,
                                                 @RequestParam(defaultValue = "") String keyword) {
        return maintRepo.searchUnApprovedClosedPPM(keyword, PageRequest.of(page - 1, size)).getContent();
    }
    
    @GetMapping("/unapproved_closed_ppm_PaginationSearchDatewise")
    public List<Maint> searchUnApprovedClosedPPM(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(defaultValue = "") String keyword) {

        return maintRepo
                .searchUnApprovedClosedPPMs(fromDate, toDate, keyword, PageRequest.of(page - 1, size))
                .getContent();
    }


    // ✅ Open Maintenance
    @GetMapping("/openPPM_Pagination")
    public List<Maint> getOpenMaintenance(@RequestParam int page, @RequestParam int size) {
        return maintRepo.findOpenMaintenance(PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/openPPM_PaginationSearch")
    public List<Maint> searchOpenMaintenance(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam(defaultValue = "") String keyword) {
        return maintRepo.searchOpenMaintenance(keyword, PageRequest.of(page - 1, size)).getContent();
    }
    
    @GetMapping("/openPPM_PaginationSearchDatewise")
    public List<Maint> searchOpenMaintenance(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(defaultValue = "") String keyword) {

        return maintRepo
                .searchOpenMaintenancess(fromDate, toDate, keyword, PageRequest.of(page - 1, size))
                .getContent();
    }


    // ✅ Overdue Maintenance
    @GetMapping("/overduePPM_Pagination")
    public List<Maint> getOverdueMaintenance(@RequestParam int page, @RequestParam int size) {
        return maintRepo.findOverdueMaintenance(PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/overduePPM_PaginationSearch")
    public List<Maint> searchOverdueMaintenance(@RequestParam int page,
                                                @RequestParam int size,
                                                @RequestParam(defaultValue = "") String keyword) {
        return maintRepo.searchOverdueMaintenance(keyword, PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/overduePPM_PaginationSearchDatewise")
    public List<Maint> searchOverdueMaintenance(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(defaultValue = "") String keyword) {

        return maintRepo
                .searchOverdueMaintenancess(fromDate, toDate, keyword, PageRequest.of(page - 1, size))
                .getContent();
    }

    
    // ✅ Closed Maintenance
    @GetMapping("/closedPaginationPPM")
    public List<Maint> getClosedMaintenance(@RequestParam int page, @RequestParam int size) {
        return maintRepo.findClosedMaintenance(PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/closedPPM_PaginationSearch")
    public List<Maint> searchClosedMaintenance(@RequestParam int page,
                                               @RequestParam int size,
                                               @RequestParam(defaultValue = "") String keyword) {
        return maintRepo.searchClosedMaintenance(keyword, PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/closedPPM_PaginationSearchDatewise")
    public List<Maint> searchClosedMaintenance(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("closedDate").descending());
        return maintRepo.searchClosedMaintenancess(fromDate, toDate, keyword, pageable).getContent();
    }

    
    // ✅ Total Maintenance
    @GetMapping("/pagination_allPPM")
    public List<Maint> getAllMaintenance(@RequestParam int page, @RequestParam int size) {
        return maintRepo.findAllMaintenance(PageRequest.of(page - 1, size)).getContent();
    }

    @GetMapping("/allPPM_PaginationSearch")
    public List<Maint> searchAllMaintenance(@RequestParam int page,
                                            @RequestParam int size,
                                            @RequestParam(defaultValue = "") String keyword) {
        return maintRepo.searchAllMaintenance(keyword, PageRequest.of(page - 1, size)).getContent();
    }
    
    @GetMapping("/allPPM_PaginationSearchDatewise")
    public List<Maint> searchAllMaintenance(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("schedule_date").descending());
        return maintRepo.searchAllMaintenancess(fromDate, toDate, keyword, pageable).getContent();
    }


    
    @GetMapping("/openByWeekPagination")
    public List<Maint> getOpenMaintenanceByWeek(@RequestParam int page,
                                                @RequestParam int size) {
        // Calculate start and end of the week
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("schedule_date").descending());
        return maintRepo.findOpenMaintenanceByDates(startOfWeek, endOfWeek, pageable).getContent();
    }

    
    @GetMapping("/openByWeekPaginationSearch")
    public List<Maint> searchOpenMaintenanceByWeek(@RequestParam int page,
                                                   @RequestParam int size,
                                                   @RequestParam(defaultValue = "") String keyword) {
        // Calculate start and end of the week
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("schedule_date").descending());
        return maintRepo.searchOpenMaintenanceByDates(startOfWeek, endOfWeek, keyword, pageable).getContent();
    }

    
    @GetMapping("/closedByWeekPagination")
    public List<Maint> getClosedMaintenanceByWeek(@RequestParam int page,
                                                  @RequestParam int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("schedule_date").descending());
        return maintRepo.findClosedMaintenanceByDates(startOfWeek, endOfWeek, pageable).getContent();
    }

    @GetMapping("/closedByWeekPaginationSearch")
    public List<Maint> searchClosedMaintenanceByWeek(@RequestParam int page,
                                                     @RequestParam int size,
                                                     @RequestParam(defaultValue = "") String keyword) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("schedule_date").descending());
        return maintRepo.searchClosedMaintenanceByDates(startOfWeek, endOfWeek, keyword, pageable).getContent();
    }


    
    
    //////////////////////////////////////////////////////////////
    
    
    @GetMapping("/breakdown_dashboard")
    @ResponseBody
    public BreakdownCountWithRecordsDTO getBreakdownDashboard() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        BreakdownCountWithRecordsDTO dto = new BreakdownCountWithRecordsDTO();

        // ✅ Weekly counts
        dto.setWeeklyOpen(breakdownRepo.getCountOpenBreakdownByDates(startOfWeek, endOfWeek));
        dto.setWeeklyTrial(breakdownRepo.getCountOfTrailBrekdownByDates(startOfWeek, endOfWeek));
        dto.setWeeklyClosed(breakdownRepo.getCountOfClosedBrekdownByDates(startOfWeek, endOfWeek));
        dto.setWeeklyTotal(breakdownRepo.getTotalCountByDates(startOfWeek, endOfWeek));

        // ✅ Weekly records
        dto.setWeeklyOpenRecords(breakdownRepo.getWeeklyOpenBreakdowns(startOfWeek, endOfWeek));
        dto.setWeeklyTrialRecords(breakdownRepo.getWeeklyTrialBreakdowns(startOfWeek, endOfWeek));
        dto.setWeeklyClosedRecords(breakdownRepo.getWeeklyClosedBreakdowns(startOfWeek, endOfWeek));

        // ✅ Overall counts
        dto.setTotalOpen((int) breakdownRepo.countByStatus(1));
        dto.setTotalTrial((int) breakdownRepo.countByStatus(2));
        dto.setTotalClosed((int) breakdownRepo.countByStatus(3));
        dto.setTotalBreakdowns(breakdownRepo.getTotalBreakdownCount());

        // ✅ Overall records
        dto.setTotalOpenRecords(breakdownRepo.getTotalOpenBreakdowns());
        dto.setTotalTrialRecords(breakdownRepo.getTotalTrialBreakdowns());
        dto.setTotalClosedRecords(breakdownRepo.getTotalClosedBreakdowns());
        dto.setTotalBreakdownRecords(breakdownRepo.getAllBreakdowns());

        return dto;
    }

    
    // weekly breakdown pagination
    
    @GetMapping("/breakdownsWithWeeklyOpenPagination")
    public Page<Breakdown> getWeeklyOpenBreakdowns(
            @RequestParam int page,
            @RequestParam int size) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findWeeklyOpenBreakdowns(startOfWeek, endOfWeek, pageable);
    }

    
    
    @GetMapping("/breakdownsWithWeeklyTrialPagination")
    public Page<Breakdown> getWeeklyTrialBreakdowns(
            @RequestParam int page,
            @RequestParam int size) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findWeeklyTrialBreakdowns(startOfWeek, endOfWeek, pageable);
    }

    
    
    
    @GetMapping("/breakdownsWithWeeklyClosedPagination")
    public Page<Breakdown> getWeeklyClosedBreakdowns(
            @RequestParam int page,
            @RequestParam int size) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findWeeklyClosedBreakdowns(startOfWeek, endOfWeek, pageable);
    }

    
    // total breakdown pagination
    
    
    @GetMapping("/breakdownsWithTotalOpenPagination")
    public Page<Breakdown> getTotalOpenBreakdowns(@RequestParam int page,
                                                  @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findByStatus(1, pageable);
    }

    @GetMapping("/breakdownsWithTotalTrialPagination")
    public Page<Breakdown> getTotalTrialBreakdowns(@RequestParam int page,
                                                   @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findByStatus(2, pageable);
    }

    @GetMapping("/breakdownsWithTotalClosedPagination")
    public Page<Breakdown> getTotalClosedBreakdowns(@RequestParam int page,
                                                    @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findByStatus(3, pageable);
    }

    @GetMapping("/totalBreakdownsWithPagination")
    public Page<Breakdown> getTotalBreakdowns(@RequestParam int page,
                                              @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return breakdownRepo.findAll(pageable);
    }

    
    
    ///pagination + searching breakdown cards
    
    // 🗓 Utility to get current week's start & end date
    private Date[] getWeekRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();
        return new Date[]{startOfWeek, endOfWeek};
    }
    
    // 🔹 WEEKLY OPEN breakdowns
    @GetMapping("/weeklyOpenBreakdownsWithPageSearch")
    public Page<Breakdown> getWeeklyOpenBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Date[] range = getWeekRange();
        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findWeeklyOpenBreakdowns(range[0], range[1], pageable);
        }
        return breakdownRepo.searchWeeklyBreakdowns(1, keyword, range[0], range[1], pageable);
    }

    // 🔹 WEEKLY TRIAL breakdowns
    @GetMapping("/weeklyTrialBreakdownsWithPageSearch")
    public Page<Breakdown> getWeeklyTrialBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Date[] range = getWeekRange();
        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findWeeklyTrialBreakdowns(range[0], range[1], pageable);
        }
        return breakdownRepo.searchWeeklyBreakdowns(2, keyword, range[0], range[1], pageable);
    }

    // 🔹 WEEKLY CLOSED breakdowns
    @GetMapping("/weeklyClosedBreakdownsWithPageSearch")
    public Page<Breakdown> getWeeklyClosedBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Date[] range = getWeekRange();
        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findWeeklyClosedBreakdowns(range[0], range[1], pageable);
        }
        return breakdownRepo.searchWeeklyBreakdowns(3, keyword, range[0], range[1], pageable);
    }

    // 🔹 TOTAL OPEN breakdowns
    @GetMapping("/totalOpenBreakdownsWithPageSearch")
    public Page<Breakdown> getTotalOpenBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findByStatus(1, pageable);
        }
        return breakdownRepo.searchTotalBreakdowns(1, keyword, pageable);
    }

    // 🔹 TOTAL TRIAL breakdowns
    @GetMapping("/totalTrialBreakdownsWithPageSearch")
    public Page<Breakdown> getTotalTrialBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findByStatus(2, pageable);
        }
        return breakdownRepo.searchTotalBreakdowns(2, keyword, pageable);
    }

    // 🔹 TOTAL CLOSED breakdowns
    @GetMapping("/totalClosedBreakdownsWithPageSearch")
    public Page<Breakdown> getTotalClosedBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findByStatus(3, pageable);
        }
        return breakdownRepo.searchTotalBreakdowns(3, keyword, pageable);
    }

    // 🔹 TOTAL ALL breakdowns
    @GetMapping("/totalBreakdownsWithPageSearch")
    public Page<Breakdown> getAllBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        if (keyword.isEmpty()) {
            return breakdownRepo.findAll(pageable);
        }
        return breakdownRepo.searchAllBreakdowns(keyword, pageable);
    }
    
    //###############################################//
    
 // 🔹 TOTAL OPEN breakdowns
    @GetMapping("/totalOpenBreakdownsWithPageSearchDatewise")
    public Page<Breakdown> getTotalOpenBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return breakdownRepo.searchTotalBreakdownses(1, fromDate, toDate, keyword, pageable);
    }
    

    // 🔹 TOTAL TRIAL breakdowns
    @GetMapping("/totalTrialBreakdownsWithPageSearchDatewise")
    public Page<Breakdown> getTotalTrialBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return breakdownRepo.searchTotalBreakdownses(2, fromDate, toDate, keyword, pageable);
    }

    // 🔹 TOTAL CLOSED breakdowns
    @GetMapping("/totalClosedBreakdownsWithPageSearchDatewise")
    public Page<Breakdown> getTotalClosedBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return breakdownRepo.searchTotalBreakdownses(3, fromDate, toDate, keyword, pageable);
    }

    // 🔹 TOTAL ALL breakdowns
    @GetMapping("/totalBreakdownsWithPageSearchDatewise")
    public Page<Breakdown> getAllBreakdowns(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,

            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return breakdownRepo.searchAllBreakdownses(fromDate, toDate, keyword, pageable);
    }

     	
    
}
