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
import com.kfMaintenancce.model.AssetInventory;
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
import com.kfMaintenancce.repo.AssetInventoryRepo;
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
@RequestMapping("/dashboard")
public class DashBoardController 
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
	    private AssetInventoryRepo assetInventoryRepository;
	
	@Autowired
	MeterWiseDashboardCardDataRepo meterWiseDashboardCardDataRepo;
    //************************************* DASHBOARD FOR OVERVIEW ***********************************//
    
    
  
    
    
    @GetMapping(value = "/getOverviewDahboardPieChart")
    public OverviewDahboardPieChart getOverviewDahboardPieChart() 
    {
    	OverviewDahboardPieChart overviewDahboardCardCount= new OverviewDahboardPieChart();
    	List<OverviewDahboardPieChart> list=overviewDahboardPieChartRepo.findAll();
    	overviewDahboardCardCount=list.get(0);
          return overviewDahboardCardCount;
    }
    
    

    
    //************************************* Gauge Chart FOR OVERVIEW ***********************************//  

    
    //engineer wise complaint status report
    @GetMapping("/engineerComplaintRecords/{user_id}")
    public ResponseEntity<List<Complaint>> getEngineerComplaintRecords(@PathVariable int user_id) {
        EngineerComplaintRecordDTO engineerComplaintRecord = dashboardCountService.getEngineerComplaintRecords(user_id);
        if (engineerComplaintRecord != null) {
            List<Complaint> allComplaints = engineerComplaintRecord.getAllComplaints();
            return ResponseEntity.ok(allComplaints);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    
    @GetMapping("/engineerComplaintCounts")
    public ResponseEntity<List<EngineerComplaintCountDTO>> getEngineersWithComplaintCounts() {
        List<EngineerComplaintCountDTO> engineerComplaintCounts = dashboardCountService.getEngineersWithComplaintCounts();
        return ResponseEntity.ok(engineerComplaintCounts);
    }
     
    @GetMapping("/getMaintenanceCountsByMachine")
    public ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceCountsByMachine(@RequestParam("week") int week) {
        List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
        Set<String> machineNames = new HashSet<>();
        List<Machine> machineList = machineRepo.findAll();

        for (Machine machine : machineList) {
            machineNames.add(machine.getMachine_name());
        }
    	Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
        Date startDate = startEndDates[0];
        Date endDate = startEndDates[1];
        for (String machineName : machineNames) {
            int openCount = maintRepo.getMaintenanceCountByMachineNamesAndStatusAndDate(machineName, 1,startDate,endDate);
            int closedCount = maintRepo.getMaintenanceCountByMachineNamesAndStatusAndDate(machineName, 0,startDate,endDate);

            if (openCount != 0 || closedCount != 0) {
                MaintenanceCountsByMachineDTO byMachineDTO = new MaintenanceCountsByMachineDTO();
                byMachineDTO.setMachineName(machineName);
                byMachineDTO.setOpen(openCount);
                byMachineDTO.setClosed(closedCount);
                list.add(byMachineDTO);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
//    @GetMapping("/getMaintenanceOverallDashaboardGraph")
//    public ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceOverallDashaboardGraph(@RequestParam("week") int week) {
//        List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
//       
//    	Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
//        Date startDate = startEndDates[0];
//        Date endDate = startEndDates[1];
//        List<Date> dates=commanService.getDatesBetween(startDate, endDate);
//        for (Date date : dates) {
//        	System.out.println("Date "+date);
//            int openCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 1);
//            int closedCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 0);
//            int overdeusCount = maintRepo.getOverduesMaintenaceByDate(date);
//            int totalCount = (int) maintRepo.count();
//         //   if (openCount != 0 || closedCount != 0) {
//                MaintenanceCountsByMachineDTO byMachineDTO = new MaintenanceCountsByMachineDTO();
//                Format formatter = new SimpleDateFormat("dd-MM-yyyy");
//                String s = formatter.format(date);
//                byMachineDTO.setDate(s);
//                byMachineDTO.setOpen(openCount);
//                byMachineDTO.setClosed(closedCount);
//                byMachineDTO.setOverdue(overdeusCount);
//                list.add(byMachineDTO);
//            //}
//        }
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
	
	  public static Date[] getStartAndEndDatesOfWeek(int year, int weekNumber) {
	        // Create a Calendar instance
	        Calendar calendar = Calendar.getInstance();

	        // Set the year and week number
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);

	        // Set the day to the first day of the week
	        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
	        Date startDate = calendar.getTime();

	        // Add 6 days to get the end date of the week
	        calendar.add(Calendar.DAY_OF_WEEK, 6);
	        Date endDate = calendar.getTime();

	        // Return the start and end dates
	        return new Date[] { startDate, endDate };
	    }
	  
	  
	  @GetMapping("/getMaintenanceOverallDashaboardGraphByLab")
	  public ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceOverallDashaboardGraphByLab(
	          @RequestParam(value = "week", required = false) Integer week, 
	          @RequestParam(value = "month", required = false) Integer month,
	          @RequestParam(value = "year", required = false) Integer year,
	          @RequestParam(value = "labName", required = false) String labName) {  // ✅ Added labName

	      if (week != null) {
	          return getMaintenanceForWeek(week, year, labName);
	      } else if (month != null) {
	          return getMaintenanceForMonth(month, year, labName);
	      } else if (year != null) {
	          return getMaintenanceForYear(year, labName);
	      } else {
	          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	      }
	  }

	  private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForWeek(int week, int year, String labName) {
		    List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
		    Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
		    Date startDate = startEndDates[0];
		    Date endDate = startEndDates[1];
		    List<Date> dates = commanService.getDatesBetween(startDate, endDate);
		    
		    for (Date date : dates) {
		        int openCount = maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 1, labName);
		        int closedCount = maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 0, labName);
		        int overduesCount = maintRepo.getOverduesMaintenaceByDatesAndLabs(date, labName);
		        List<String> machineNames = maintRepo.getMachineNameByStatusAndDatesAndLab(date, 1, labName);
		        String machineName = machineNames.isEmpty() ? "" : machineNames.get(0); 

		        MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
		        dto.setDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
		        dto.setOpen(openCount);
		        dto.setClosed(closedCount);
		        dto.setOverdue(overduesCount);
		        dto.setMachineName(machineName);
		        list.add(dto);
		    }
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}

	  private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForMonth(int month, int year, String labName) {
		    List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.MONTH, month - 1);
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date startDate = calendar.getTime();
		    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		    Date endDate = calendar.getTime();

		    List<Date> dates = commanService.getDatesBetween(startDate, endDate);
		    for (Date date : dates) {
		        int openCount = maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 1, labName);
		        int closedCount = maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 0, labName);
		        int overduesCount = maintRepo.getOverduesMaintenaceByDatesAndLabs(date, labName);
		        List<String> machineNames = maintRepo.getMachineNameByStatusAndDatesAndLab(date, 1, labName);
		        String machineName = machineNames.isEmpty() ? "" : machineNames.get(0);

		        MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
		        dto.setDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
		        dto.setOpen(openCount);
		        dto.setClosed(closedCount);
		        dto.setOverdue(overduesCount);
		        dto.setMachineName(machineName);
		        list.add(dto);
		    }
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}

	  private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForYear(int year, String labName) {
		    List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
		    
		    for (int month = 1; month <= 12; month++) {
		        List<Date> monthDates = commanService.getDatesBetween(getStartOfMonth(year, month), getEndOfMonth(year, month));
		        int openCount = 0, closedCount = 0, overduesCount = 0;
		        
		        for (Date date : monthDates) {
		            openCount += maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 1, labName);
		            closedCount += maintRepo.getMaintenanceCountByStatusAndDateAndLab(date, 0, labName);
		            overduesCount += maintRepo.getOverduesMaintenaceByDatesAndLabs(date, labName);
		        }
		        
		        MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
		        dto.setDate(String.format("Month %d", month));
		        dto.setOpen(openCount);
		        dto.setClosed(closedCount);
		        dto.setOverdue(overduesCount);
		        list.add(dto);
		    }
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}

	  
	  

    @GetMapping("/getMaintenanceOverallDashaboardGraph")
    public ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceOverallDashaboardGraph(
            @RequestParam(value = "week", required = false) Integer week, 
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year) {

        if (week != null) {
            return getMaintenanceForWeek(week, year);
        } else if (month != null) {
            return getMaintenanceForMonth(month, year);
        } else if (year != null) {
            return getMaintenanceForYear(year);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForWeek(int week, int year) {
        List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
        Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
        Date startDate = startEndDates[0];
        Date endDate = startEndDates[1];
        List<Date> dates = commanService.getDatesBetween(startDate, endDate);
        for (Date date : dates) {
            int openCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 1);
            int closedCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 0);
            int overduesCount = maintRepo.getOverduesMaintenaceByDate(date);
            int totalCount = maintRepo.countTotalMaintenances();
//            List<String> machineNames = maintRepo.getMachineNameByStatusAndDates(date, 1);
//	        String machineName = machineNames.isEmpty() ? "" : machineNames.get(0);
            MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
            dto.setDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
            dto.setOpen(openCount);
            dto.setClosed(closedCount);
            dto.setOverdue(overduesCount);
          //  dto.setMachineName(machineName);
            list.add(dto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForMonth(int month, int year) {
        List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);  // Months are 0-based in Calendar
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<Date> dates = commanService.getDatesBetween(startDate, endDate);
        for (Date date : dates) {
            int openCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 1);
            int closedCount = maintRepo.getMaintenanceCountByStatusAndDate(date, 0);
            int overduesCount = maintRepo.getOverduesMaintenaceByDate(date);
            int totalCount = maintRepo.countTotalMaintenances();
            List<String> machineNames = maintRepo.getMachineNameByStatusAndDates(date, 1);
	        String machineName = machineNames.isEmpty() ? "" : machineNames.get(0);
            MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
            dto.setDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
            dto.setOpen(openCount);
            dto.setClosed(closedCount);
            dto.setOverdue(overduesCount);
            dto.setMachineName(machineName);
            list.add(dto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseEntity<List<MaintenanceCountsByMachineDTO>> getMaintenanceForYear(int year) {
        List<MaintenanceCountsByMachineDTO> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            List<Date> monthDates = commanService.getDatesBetween(getStartOfMonth(year, month), getEndOfMonth(year, month));
            int openCount = 0, closedCount = 0, overduesCount = 0;
            for (Date date : monthDates) {
                openCount += maintRepo.getMaintenanceCountByStatusAndDate(date, 1);
                closedCount += maintRepo.getMaintenanceCountByStatusAndDate(date, 0);
                overduesCount += maintRepo.getOverduesMaintenaceByDate(date);
                
                
            }
            MaintenanceCountsByMachineDTO dto = new MaintenanceCountsByMachineDTO();
            dto.setDate(String.format("Month %d", month));
            dto.setOpen(openCount);
            dto.setClosed(closedCount);
            dto.setOverdue(overduesCount);
            list.add(dto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private Date getStartOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);  // Set to the first day of the month
        return calendar.getTime();
    }

    private Date getEndOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    
    
    
    @GetMapping("/getBreakdownCountsByMachineByLab")
    public ResponseEntity<List<BreakdownCountsByMachineDTO>> getBreakdownCountsByMachineByLab(@RequestParam("labName") String labName) {
        List<BreakdownCountsByMachineDTO> list = new ArrayList<>();
        Set<String> machineNames = new HashSet<>();
        List<Machine> machineList = machineRepo.findAll();

        // Collect all machine names
        for (Machine machine : machineList) {
            machineNames.add(machine.getMachine_name());
        }

        // Iterate over each machine name and get breakdown counts for the given labName
        for (String machineName : machineNames) {
            int openCount = breakdownRepo.getBreakdownCountByMachineNameAndStatusAndLab(machineName, 1, labName);
            int closedCount = breakdownRepo.getBreakdownCountByMachineNameAndStatusAndLab(machineName, 3, labName);
            int trialCount = breakdownRepo.getBreakdownCountByMachineNameAndStatusAndLab(machineName, 2, labName);

            if (openCount != 0 || closedCount != 0 || trialCount != 0) {
                BreakdownCountsByMachineDTO byMachineDTO = new BreakdownCountsByMachineDTO();
                byMachineDTO.setMachineName(machineName);
                byMachineDTO.setOpen(openCount);
                byMachineDTO.setClosed(closedCount);
                byMachineDTO.setTrail(trialCount);
                list.add(byMachineDTO);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    
    @GetMapping("/getBreakdownCountsByMachine")
    public ResponseEntity<List<BreakdownCountsByMachineDTO>> getBreakdownCountsByMachine() {
        List<BreakdownCountsByMachineDTO> list = new ArrayList<>();        
        Set<String> machineNames = new HashSet<>();
        List<Machine> machineList = machineRepo.findAll();  
        
        for (Machine machine : machineList) {
            machineNames.add(machine.getMachine_name());
        }
        
        for (String machineName : machineNames) {
            int OpenCount = breakdownRepo.getBreakdownCountByMachineNameAndStatus(machineName, 1);
            int ClosedCount = breakdownRepo.getBreakdownCountByMachineNameAndStatus(machineName, 3);
            int TrialCount = breakdownRepo.getBreakdownCountByMachineNameAndStatus(machineName, 2);
            
            if (OpenCount != 0 || ClosedCount != 0 || TrialCount !=0) {
            BreakdownCountsByMachineDTO byMachineDTO = new BreakdownCountsByMachineDTO();
            byMachineDTO.setMachineName(machineName);
            byMachineDTO.setOpen(OpenCount);
            byMachineDTO.setClosed(ClosedCount);
            byMachineDTO.setTrail(TrialCount);
            list.add(byMachineDTO);
            }
        }
       
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    
    @GetMapping("/getBreakdownGraphDataByLab")
    public ResponseEntity<List<GraphResponceDTO>> getBreakdownGraphDataByLab(@RequestParam("labName") String labName) {
        List<GraphResponceDTO> list = new ArrayList<>();
        List<MachineOwner> machineOwnes = machineOwnerRepo.findAll();
        Set<UserDetails> users = new HashSet<UserDetails>();
        
        for (MachineOwner machineOwner : machineOwnes) {
            users.add(machineOwner.getUser());
        }

        for (UserDetails userDetail : users) {
            GraphResponceDTO graphResponceDTO = new GraphResponceDTO();
            List<Integer> machineIds = machineOwnerRepo.getMachineIdsByUserOwner(userDetail.getId());

            // Fetch breakdown data based on machine IDs and status, and filter by labName
            int countOpenBreakdown = breakdownRepo.getBreakDownCountByMachineIDsAndStatusAndLab(machineIds, 1, labName);
            int countClosedBreakdown = breakdownRepo.getBreakDownCountByMachineIDsAndStatusAndLab(machineIds, 3, labName);
            int countTrialBreakdown = breakdownRepo.getBreakDownCountByMachineIDsAndStatusAndLab(machineIds, 2, labName);

            if (countOpenBreakdown != 0 || countClosedBreakdown != 0 || countTrialBreakdown != 0) {
                graphResponceDTO.setClosed(countClosedBreakdown);
                graphResponceDTO.setOpen(countOpenBreakdown);
                graphResponceDTO.setTrail(countTrialBreakdown);
                graphResponceDTO.setUser(userDetail);
                list.add(graphResponceDTO);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    
    @GetMapping("/getBreakdownGraphData")
    public ResponseEntity<List<GraphResponceDTO>> getBreakdoenGraphData() 
    {
        List<GraphResponceDTO> list= new ArrayList<GraphResponceDTO>();        
        List<MachineOwner> machineOwnes=machineOwnerRepo.findAll();
        Set<UserDetails> users=new HashSet<UserDetails>();
        
        for(MachineOwner machineOwner:machineOwnes) {
        	users.add(machineOwner.getUser());
        }
        
        for(UserDetails userdetail:users)
        {
        	
        	GraphResponceDTO graphResponceDTO= new GraphResponceDTO();
        	List<Integer> machineIds=machineOwnerRepo.getMachineIdsByUserOwner(userdetail.getId());
        	
        	int countOpenBreakdown=breakdownRepo.getBreakDownCountByMachineIDsAndStatus(machineIds,1);
        	int countClosedBreakdown=breakdownRepo.getBreakDownCountByMachineIDsAndStatus(machineIds,3);
        	int countTarilBreakdown=breakdownRepo.getBreakDownCountByMachineIDsAndStatus(machineIds,2);
        	
        	if (countOpenBreakdown != 0 || countClosedBreakdown != 0 || countTarilBreakdown !=0) {
        	graphResponceDTO.setClosed(countClosedBreakdown);
        	graphResponceDTO.setOpen(countOpenBreakdown);
        	graphResponceDTO.setTrail(countTarilBreakdown);
        	graphResponceDTO.setUser(userdetail);
        	list.add(graphResponceDTO);
          }  
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    
    @GetMapping("/getMaintenanceGraphData")
    public ResponseEntity<List<GraphResponseDTO2>> getMaintenanceGraphData() 
    {

        List<GraphResponseDTO2> list = new ArrayList<>();
        List<MachineOwner> machineOwners = machineOwnerRepo.findAll();
        Set<UserDetails> users = new HashSet<>();
        for (MachineOwner machineOwner : machineOwners)
        {
            users.add(machineOwner.getUser());
        }

        for (UserDetails userDetails : users) {
        	GraphResponseDTO2 graphResponseDTO2 = new GraphResponseDTO2();
            List<Integer> machineIds = machineOwnerRepo.getMachineIdsByUserOwner(userDetails.getId());

            int countOpenMaintenance = maintRepo.getMaintenanceCountByMachineIDsAndStatus(machineIds, 1);
          int countClosedMaintenance = maintRepo.getMaintenanceCountByMachineIDsAndStatus(machineIds, 0);

          if (countOpenMaintenance != 0 || countClosedMaintenance != 0) {
            graphResponseDTO2.setClosed(countClosedMaintenance);
            graphResponseDTO2.setOpen(countOpenMaintenance);
            graphResponseDTO2.setUser(userDetails);
            list.add(graphResponseDTO2);
          }
        }   
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/total_count")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TotalsResponse> getTotalCounts() 
    {
        long machineCount = machineRepo.count();;
        long userCount = userDetailsRepo.count();

        TotalsResponse response = new TotalsResponse(machineCount, userCount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/total_countByLab")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TotalsResponse> getTotalCountsByLab(@RequestParam String labName) {
        long machineCount = machineRepo.countByLabName(labName);
        long userCount = userDetailsRepo.count(); 

        TotalsResponse response = new TotalsResponse(machineCount, userCount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    
    @GetMapping(value = "/breakdown_countByLab")
    public @ResponseBody DashboardCardCountDto breakdown_countByLab(@RequestParam("labName") String labName) {
        Calendar calendar = Calendar.getInstance();
        
        // Set the calendar to the start of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();
        
        // Format the date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start of the week: " + sdf.format(startOfWeek));
        
        // Set the calendar to the end of the week (Saturday)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();
        System.out.println("End of the week: " + sdf.format(endOfWeek));
        
        DashboardCardCountDto dashboardCardCountDto = new DashboardCardCountDto();
        
        int totalBreakDown = breakdownRepo.getTotalCountByDatesAndLab(startOfWeek, endOfWeek, labName);
        int trail = breakdownRepo.getCountOfTrailBrekdownByDatesAndLab(startOfWeek, endOfWeek, labName);
        int closed = breakdownRepo.getCountOfClosedBrekdownByDatesAndLab(startOfWeek, endOfWeek, labName);
        int open = breakdownRepo.getCountOpenBreakdownByDatesAndLab(startOfWeek, endOfWeek, labName);
        
        dashboardCardCountDto.setTotal(totalBreakDown);
        dashboardCardCountDto.setTrail(trail);
        dashboardCardCountDto.setClosed(closed);
        dashboardCardCountDto.setOpen(open);
        
        return dashboardCardCountDto;
    }


    @GetMapping(value = "/breakdown_count")
    public @ResponseBody DashboardCardCountDto
     getTotalBreakdownCounts() 
    {
    	Calendar calendar = Calendar.getInstance();
        
        // Set the calendar to the start of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();
        
        // Format the date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start of the week: " + sdf.format(startOfWeek));
        
        // Set the calendar to the end of the week (Saturday)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();
        System.out.println("End of the week: " + sdf.format(endOfWeek));
        
       
    	DashboardCardCountDto dashboardCardCountDto= new DashboardCardCountDto();
    int totalBreakDown=(int) breakdownRepo.getTotalCountByDates(startOfWeek,endOfWeek);
    int trail= breakdownRepo.getCountOfTrailBrekdownByDates(startOfWeek,endOfWeek);
    int closed= breakdownRepo.getCountOfClosedBrekdownByDates(startOfWeek,endOfWeek);
    int open= breakdownRepo.getCountOpenBreakdownByDates(startOfWeek,endOfWeek);
    dashboardCardCountDto.setTotal(totalBreakDown);
    dashboardCardCountDto.setTrail(trail);
    dashboardCardCountDto.setClosed(closed);
    dashboardCardCountDto.setOpen(open);
	
        return dashboardCardCountDto;
    }

    @GetMapping(value = "/maintaince_count")
    public PPMDashboardCountDTO getMaintCounts() 
    {
    	PPMDashboardCountDTO pPMDashboardCountDTO= new PPMDashboardCountDTO();
    	Calendar calendar = Calendar.getInstance();
        
        // Set the calendar to the start of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();
        
        // Format the date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start of the week: " + sdf.format(startOfWeek));
        
        // Set the calendar to the end of the week (Saturday)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();
        System.out.println("End of the week: " + sdf.format(endOfWeek));
        
        
        
        int openMaintence = dashboardCountService.getOpenMaintenanceByDates(startOfWeek,endOfWeek);
        int closed = dashboardCountService.getClosedMaintenanceByDate(startOfWeek,endOfWeek);

        
        int overduesMaintenace = dashboardCountService.getOverduesMaintenaceByDate(new Date());
         int totalOpenMaintenance = maintRepo.countOpeneMaintenancesByDate(new Date());
        int totalClosedMaintenance = maintRepo.countInactiveMaintenances();
        
        
        List<Maint> closedApprovals = maintRepo.findClosedApprovals();
        int closedApprovalPPMCount = closedApprovals.size();
        
        
        
      int totalMaintence=overduesMaintenace+totalClosedMaintenance+totalOpenMaintenance+closedApprovalPPMCount;
      pPMDashboardCountDTO.setClosed(closed);
      pPMDashboardCountDTO.setOpenMaintence(openMaintence);
    
      
      pPMDashboardCountDTO.setOverduesMaintenace(overduesMaintenace);
      pPMDashboardCountDTO.setTotalMaintence(totalMaintence);
      pPMDashboardCountDTO.setTotalOpenMaintenance(totalOpenMaintenance); 
      pPMDashboardCountDTO.setTotalClosedMaintenance(totalClosedMaintenance); 
        return pPMDashboardCountDTO;
    }
   
    @GetMapping(value = "/maintaince_counts/{machineId}")
    public PPMDashboardCountDTO getMaintCountsByMachine(@PathVariable int machineId) {
        PPMDashboardCountDTO dto = new PPMDashboardCountDTO();
        Calendar calendar = Calendar.getInstance();

        // Start of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        // End of the week (Saturday)
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = calendar.getTime();

        int openMaintence = maintRepo.getOpenMaintenanceByDates(startOfWeek, endOfWeek, machineId);
        int closed = maintRepo.getClosedMaintenanceByDate(startOfWeek, endOfWeek, machineId);
        int overduesMaintenace = maintRepo.getOverduesMaintenaceByDate(machineId);
        int totalOpenMaintenance = maintRepo.countOpeneMaintenancesByDate(machineId);
        int totalClosedMaintenance = maintRepo.countInactiveMaintenances(machineId);

        List<Maint> closedApprovals = maintRepo.findClosedApprovals(machineId);
        int closedApprovalPPMCount = closedApprovals.size();

        int totalMaintence = overduesMaintenace + totalClosedMaintenance + totalOpenMaintenance + closedApprovalPPMCount;

        dto.setClosed(closed);
        dto.setOpenMaintence(openMaintence);
        dto.setOverduesMaintenace(overduesMaintenace);
        dto.setTotalMaintence(totalMaintence);
        dto.setTotalOpenMaintenance(totalOpenMaintenance);
        dto.setTotalClosedMaintenance(totalClosedMaintenance);

        return dto;
    }

    
    @GetMapping("/maintaince_records/{machineId}")
    public ResponseEntity<Map<String, Object>> getMaintRecordsByMachine(
            @PathVariable int machineId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        // -------------------------
        // Open records (special handling with CURRENT_DATE)
        // -------------------------
        List<Maint> openRecords;
        if (fromDate == null && toDate == null) {
            openRecords = maintRepo.findUpcomingOpenMaintenance(machineId);
        } else {
            openRecords = maintRepo.findOpenMaintenanceByDates(fromDate, toDate, machineId);
        }

        // -------------------------
        // Set default values for other queries
        // -------------------------
        if (fromDate == null) {
            fromDate = new Date(0); // epoch
        }
        if (toDate == null) {
            toDate = new Date(); // today
        }

        // Other records
        List<Maint> closedRecords = maintRepo.findClosedMaintenanceByDates(fromDate, toDate, machineId);
        List<Maint> approvedRecords = maintRepo.findApprovedMaintenanceByDates(fromDate, toDate, machineId);
        List<Maint> unApprovedRecords = maintRepo.findUnApprovedMaintenanceByDates(fromDate, toDate, machineId);
        List<Maint> overdueRecords = maintRepo.findOverdueMaintenance(machineId);

        // -------------------------
        // Build totals
        // -------------------------
        int totalOpenCount = openRecords.size();
        int totalClosedCount = closedRecords.size();
        int totalApprovedCount = approvedRecords.size();
        int totalOverdueCount = overdueRecords.size();

        int totalMaintenanceCount = totalOpenCount + totalClosedCount + totalApprovedCount + totalOverdueCount;

        // Merge records into one list
        List<Maint> totalMaintenanceRecords = new ArrayList<>();
        totalMaintenanceRecords.addAll(openRecords);
        totalMaintenanceRecords.addAll(closedRecords);
        totalMaintenanceRecords.addAll(approvedRecords);
        totalMaintenanceRecords.addAll(overdueRecords);

        // -------------------------
        // Prepare response
        // -------------------------
        Map<String, Object> response = new HashMap<>();
        response.put("totalOpenRecords", openRecords);
        response.put("totalOpenCount", totalOpenCount);

        response.put("totalClosedRecords", closedRecords);
        response.put("totalClosedCount", totalClosedCount);

        response.put("totalApprovedRecords", approvedRecords);
        response.put("totalApprovedCount", totalApprovedCount);

        response.put("totalUnApprovedRecords", unApprovedRecords);
        response.put("totalUnApprovedCount", unApprovedRecords.size());

        response.put("totalOverdueRecords", overdueRecords);
        response.put("totalOverdueCount", totalOverdueCount);

        response.put("totalMaintenanceRecords", totalMaintenanceRecords);
        response.put("totalMaintenanceCount", totalMaintenanceCount);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/counts")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TotalsResponse2> getCounts() 
    {
        long complaintCount = dashboardCountService.countComplaints();
        long assetCount = dashboardCountService.countAssets();

        TotalsResponse2 response = new TotalsResponse2(complaintCount, assetCount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping(value = "/complaint_count")
    public Map<String, ComplaintResponse> getComplaintCounts() 
    {
    	  int openCount = dashboardCountService.getOpenComplaintCount();
          int closedCount = dashboardCountService.getClosedComplaintCount();
          int allocateCount = dashboardCountService.getAllocateComplaints();
          int InprocessCount = dashboardCountService.getInprocessComplaints();
          int NonallocateCount = dashboardCountService.getNonAllocatedComplaintsCount();
          long complaintCount = dashboardCountService.countComplaints();
          long assetCount = dashboardCountService.countAssets();
          
          ComplaintResponse response = new ComplaintResponse( openCount, closedCount, allocateCount, InprocessCount, NonallocateCount,complaintCount,assetCount);
          
          Map<String, ComplaintResponse> resultMap = new HashMap<>();
          resultMap.put("Total_Complaint", response);
          
          return resultMap;
    }

    
    
    
    
    @GetMapping(value = "/getComplaintCountsAndRecords")
    public ResponseEntity<Map<String, Object>> getComplaintCountsAndRecords() {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // ✅ Counts
            int openCount = dashboardCountService.getOpenComplaintCount();
            int closedCount = dashboardCountService.getClosedComplaintCount();
            int allocateCount = dashboardCountService.getAllocateComplaints();
            int inprocessCount = dashboardCountService.getInprocessComplaints();
            int nonallocateCount = dashboardCountService.getNonAllocatedComplaintsCount();
            long totalComplaintCount = dashboardCountService.countComplaints();
            long totalAssetCount = dashboardCountService.countAssets();

            // ✅ Complaint counts summary object
            ComplaintResponse counts = new ComplaintResponse(
                    openCount,
                    closedCount,
                    allocateCount,
                    inprocessCount,
                    nonallocateCount,
                    totalComplaintCount,
                    totalAssetCount
            );

            // ✅ Fetch complaint lists
            List<Complaint> openComplaints = complaintRepo.findOpenComplaints();
            List<Complaint> closedComplaints = complaintRepo.findClosedComplaints();
            List<Complaint> allocatedComplaints = complaintRepo.findAllocatedComplaints();
            List<Complaint> inprocessComplaints = complaintRepo.findInprocessComplaints();
            List<Complaint> nonallocatedComplaints = complaintRepo.findNonAllocatedComplaints();
            
            List<Complaint> allComplaints = complaintRepo.findAll();
            List<AssetInventory> allAssets = assetInventoryRepository.findAll();

            // ✅ Build combined response
            responseMap.put("counts", counts);
            responseMap.put("openComplaints", openComplaints);
            responseMap.put("closedComplaints", closedComplaints);
            responseMap.put("allocatedComplaints", allocatedComplaints);
            responseMap.put("inprocessComplaints", inprocessComplaints);
            responseMap.put("nonallocatedComplaints", nonallocatedComplaints);
            
            responseMap.put("allComplaints", allComplaints);
            responseMap.put("allAssets", allAssets);

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", "Failed to fetch complaint counts and records: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }

    
    
    // ✅ 1️⃣ Pagination API (no search)
    @GetMapping("/getComplaintCountsAndRecordsWithpage")
    public ResponseEntity<Map<String, Object>> getComplaintCountsAndRecords(
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Map<String, Object> responseMap = new HashMap<>();

        try {
            Pageable pageable = PageRequest.of(pageNo - 1, perPage);

            // ✅ Counts
            int openCount = dashboardCountService.getOpenComplaintCount();
            int closedCount = dashboardCountService.getClosedComplaintCount();
            int allocateCount = dashboardCountService.getAllocateComplaints();
            int inprocessCount = dashboardCountService.getInprocessComplaints();
            int nonallocateCount = dashboardCountService.getNonAllocatedComplaintsCount();
            long totalComplaintCount = dashboardCountService.countComplaints();
            long totalAssetCount = dashboardCountService.countAssets();

            ComplaintResponse counts = new ComplaintResponse(
                    openCount, closedCount, allocateCount, inprocessCount, nonallocateCount, totalComplaintCount, totalAssetCount
            );

            // ✅ Paginated complaint lists
            Page<Complaint> openComplaints = complaintRepo.findOpenComplaints(pageable);
            Page<Complaint> closedComplaints = complaintRepo.findClosedComplaints(pageable);
            Page<Complaint> allocatedComplaints = complaintRepo.findAllocatedComplaints(pageable);
            Page<Complaint> inprocessComplaints = complaintRepo.findInprocessComplaints(pageable);
            Page<Complaint> nonallocatedComplaints = complaintRepo.findNonAllocatedComplaints(pageable);

            // ✅ All records
            List<Complaint> allComplaints = complaintRepo.findAll();
            List<AssetInventory> allAssets = assetInventoryRepository.findAll();

            // ✅ Build response
            responseMap.put("counts", counts);
            responseMap.put("openComplaints", openComplaints);
            responseMap.put("closedComplaints", closedComplaints);
            responseMap.put("allocatedComplaints", allocatedComplaints);
            responseMap.put("inprocessComplaints", inprocessComplaints);
            responseMap.put("nonallocatedComplaints", nonallocatedComplaints);
            responseMap.put("allComplaints", allComplaints);
            responseMap.put("allAssets", allAssets);

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }
    
    
    @GetMapping("/getComplaintCountsAndRecordsWithDatewise")
    public ResponseEntity<Map<String, Object>> getComplaintCountsAndRecordsWithPage(
            @RequestParam int pageNo,
            @RequestParam int perPage,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        Map<String, Object> responseMap = new HashMap<>();

        try {
        	
        	 if (toDate != null) {
                 Calendar cal = Calendar.getInstance();
                 cal.setTime(toDate);
                 cal.add(Calendar.DAY_OF_MONTH, 1); // include entire 'toDate' day
                 toDate = cal.getTime();
             }
        	
            Pageable pageable = PageRequest.of(pageNo - 1, perPage);

            // ✅ Counts
            int openCount = dashboardCountService.getOpenComplaintCount();
            int closedCount = dashboardCountService.getClosedComplaintCount();
            int allocateCount = dashboardCountService.getAllocateComplaints();
            int inprocessCount = dashboardCountService.getInprocessComplaints();
            int nonallocateCount = dashboardCountService.getNonAllocatedComplaintsCount();
            long totalComplaintCount = dashboardCountService.countComplaints();
            long totalAssetCount = dashboardCountService.countAssets();

            ComplaintResponse counts = new ComplaintResponse(
                    openCount, closedCount, allocateCount, inprocessCount, nonallocateCount,
                    totalComplaintCount, totalAssetCount
            );

            Page<Complaint> openComplaints = complaintRepo.findOpenComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<Complaint> closedComplaints = complaintRepo.findClosedComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<Complaint> allocatedComplaints = complaintRepo.findAllocatedComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<Complaint> inprocessComplaints = complaintRepo.findInprocessComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<Complaint> nonallocatedComplaints = complaintRepo.findNonAllocatedComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<Complaint> allComplaints = complaintRepo.findAllComplaintsWithSearch(
                    fromDate, toDate, search, pageable);

            Page<AssetInventory> allAssets = assetInventoryRepository.findAllAssetsWithSearch(
                    search, pageable);

            // ✅ Build response
            responseMap.put("counts", counts);
            responseMap.put("openComplaints", openComplaints);
            responseMap.put("closedComplaints", closedComplaints);
            responseMap.put("allocatedComplaints", allocatedComplaints);
            responseMap.put("inprocessComplaints", inprocessComplaints);
            responseMap.put("nonallocatedComplaints", nonallocatedComplaints);
            responseMap.put("allComplaints", allComplaints);
            responseMap.put("allAssets", allAssets);

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }


    // ✅ 2️⃣ Pagination + Search API
    @GetMapping("/getComplaintCountsAndRecordsSearch")
    public ResponseEntity<Map<String, Object>> getComplaintCountsAndRecordsSearch(
            @RequestParam int pageNo,
            @RequestParam int perPage,
            @RequestParam(defaultValue = "") String keyword) {

        Map<String, Object> responseMap = new HashMap<>();

        try {
            Pageable pageable = PageRequest.of(pageNo - 1, perPage);
            String searchTerm = (keyword == null || keyword.isEmpty()) ? "%" : "%" + keyword.toLowerCase() + "%";

            // ✅ Counts
            int openCount = dashboardCountService.getOpenComplaintCount();
            int closedCount = dashboardCountService.getClosedComplaintCount();
            int allocateCount = dashboardCountService.getAllocateComplaints();
            int inprocessCount = dashboardCountService.getInprocessComplaints();
            int nonallocateCount = dashboardCountService.getNonAllocatedComplaintsCount();
            long totalComplaintCount = dashboardCountService.countComplaints();
            long totalAssetCount = dashboardCountService.countAssets();

            ComplaintResponse counts = new ComplaintResponse(
                    openCount, closedCount, allocateCount, inprocessCount, nonallocateCount, totalComplaintCount, totalAssetCount
            );

            // ✅ Paginated + Search complaint lists
            Page<Complaint> openComplaints = complaintRepo.searchOpenComplaints(searchTerm, pageable);
            Page<Complaint> closedComplaints = complaintRepo.searchClosedComplaints(searchTerm, pageable);
            Page<Complaint> allocatedComplaints = complaintRepo.searchAllocatedComplaints(searchTerm, pageable);
            Page<Complaint> inprocessComplaints = complaintRepo.searchInprocessComplaints(searchTerm, pageable);
            Page<Complaint> nonallocatedComplaints = complaintRepo.searchNonAllocatedComplaints(searchTerm, pageable);

            // ✅ All records
            List<Complaint> allComplaints = complaintRepo.findAll();
            List<AssetInventory> allAssets = assetInventoryRepository.findAll();

            // ✅ Build response
            responseMap.put("counts", counts);
            responseMap.put("openComplaints", openComplaints);
            responseMap.put("closedComplaints", closedComplaints);
            responseMap.put("allocatedComplaints", allocatedComplaints);
            responseMap.put("inprocessComplaints", inprocessComplaints);
            responseMap.put("nonallocatedComplaints", nonallocatedComplaints);
            responseMap.put("allComplaints", allComplaints);
            responseMap.put("allAssets", allAssets);

            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }
    
    
    
    @GetMapping("/allocate_count")
    public ResponseEntity<AssetsCountDTO> countAssets() {
        long countWithAllocate = dashboardCountService.countAssetsWithAllocate();
        long countWithoutAllocate = dashboardCountService.countAssetsWithoutAllocate();
        
        AssetsCountDTO assetsCountDTO = new AssetsCountDTO(countWithAllocate, countWithoutAllocate);

        return new ResponseEntity<>(assetsCountDTO, HttpStatus.OK);
    }
    
    
    @GetMapping("/monthlyComplaintCounts")
    public ResponseEntity<List<MonthlyComplaintCountsDTO>> getMonthlyComplaintCounts() {
    	int year = LocalDate.now().getYear(); 

        List<MonthlyComplaintCountsDTO> countsList = new ArrayList<>();
        
        for (int month = 1; month <= 12; month++) {
            int openCount = complaintRepo.opencountComplaintsByMonthAndYear(month, year);
            int closedCount = complaintRepo.countComplaintsByStatusAndMonthAndYear(4, month, year);
            int inprocessCount = complaintRepo.countComplaintsByStatusAndMonthAndYear(3, month, year);

            MonthlyComplaintCountsDTO countsDTO = new MonthlyComplaintCountsDTO();
            countsDTO.setMonth(Month.of(month).toString());
            countsDTO.setOpenCount(openCount);
            countsDTO.setInprocessCount(inprocessCount);
            countsDTO.setClosedCount(closedCount);
            countsList.add(countsDTO);
        }

        return new ResponseEntity<>(countsList, HttpStatus.OK);
    }
    
    
    //
    @GetMapping("/monthlyComplaintCount")
    public ResponseEntity<List<MonthlyComplaintCountsDTO>> getMonthlyComplaintCount(@RequestParam(value = "year", defaultValue = "2024") int year) {
        List<MonthlyComplaintCountsDTO> countsList = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            int openCount = complaintRepo.countComplaintByStatusAndMonthAndYear(1,month, year);
            int closedCount = complaintRepo.countComplaintByStatusAndMonthAndYear(4, month, year);
            int inprocessCount = complaintRepo.countComplaintByStatusAndMonthAndYear(3, month, year);

            MonthlyComplaintCountsDTO countsDTO = new MonthlyComplaintCountsDTO();
            countsDTO.setMonth(Month.of(month).toString());
            countsDTO.setOpenCount(openCount);
            countsDTO.setInprocessCount(inprocessCount);
            countsDTO.setClosedCount(closedCount);
            countsList.add(countsDTO);
        }

        return new ResponseEntity<>(countsList, HttpStatus.OK);
    }

    
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
     	
    
}
