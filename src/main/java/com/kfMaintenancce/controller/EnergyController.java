package com.kfMaintenancce.controller;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.ConsumptionReportRequest;
import com.kfMaintenancce.dto.ConsupmtionDashboardDTO;
import com.kfMaintenancce.dto.ConsupmtionReportDTO;
import com.kfMaintenancce.dto.DashboardMeterConsumptionDTO;
import com.kfMaintenancce.dto.DashbordDataDTO;
import com.kfMaintenancce.dto.DateWiseEnergyConsumptionReportDto;
import com.kfMaintenancce.dto.EMSDAshboardCardDTO;
import com.kfMaintenancce.dto.EnergyConsumptionReport;
import com.kfMaintenancce.dto.MeterConsumptionDTO;
import com.kfMaintenancce.dto.MeterStatus;
import com.kfMaintenancce.dto.RegisterDTO;
import com.kfMaintenancce.dto.ReportRequestDTO;
import com.kfMaintenancce.dto.ResponceObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.ConsumptionDetails;
import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.ControlPanel;
import com.kfMaintenancce.model.DailyConsumptionLog;
import com.kfMaintenancce.model.DashboardCardData;
import com.kfMaintenancce.model.DayWiseConsumption;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterModule;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.EnergyMeterRegisterGroup;
import com.kfMaintenancce.model.EnergyMeterRegisterGroupItems;
import com.kfMaintenancce.model.OverviewDahboardCardCount;
import com.kfMaintenancce.model.PriceSlabRangeDetails;
import com.kfMaintenancce.model.RegisterCoversionUnit;
import com.kfMaintenancce.model.RegisterGroupDTO;
import com.kfMaintenancce.model.RegisterType;
import com.kfMaintenancce.repo.ConsumptionDetailsRepo;
import com.kfMaintenancce.repo.ConsumptionPriceSlabRepo;
import com.kfMaintenancce.repo.ControlPanelRepo;
import com.kfMaintenancce.repo.DailyConsumptionLogRepo;
import com.kfMaintenancce.repo.DashboardCardDataRepo;
import com.kfMaintenancce.repo.DayWiseConsumptionRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterModuleRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterGroupItemsRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterGroupRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.PriceSlabRangeDetailsRepo;
import com.kfMaintenancce.repo.RegisterCoversionUnitRepo;
import com.kfMaintenancce.repo.RegisterTypeRepo;
import com.kfMaintenancce.service.EnergyServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/energy")
public class EnergyController {

	
	@Autowired
	EnergyServices energyServices;
	@Autowired
	DailyConsumptionLogRepo dailyConsumptionLogRepo;
	
	@Autowired
	EnergyMeterModuleRepo energyMeterModuleRepo;
	
	@Autowired
	ConsumptionDetailsRepo consumptionDetailsRepo;
	
	
	@Autowired
	ConsumptionPriceSlabRepo consumptionPriceSlabRepo;
	@Autowired
	PriceSlabRangeDetailsRepo priceSlabRangeDetailsRepo;
	@Autowired
	EnergyMeterRegisterGroupRepo energyMeterRegisterGroupRepo;
	@Autowired
	EnergyMeterRegisterGroupItemsRepo energyMeterRegisterGroupItemsRepo;
	
	@Autowired
	EnergyMeterMasterRepo energyMeterMasterRepo;
	
	@Autowired
	DayWiseConsumptionRepo dayWiseConsumptionRepo;
	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	
	@Autowired
	ControlPanelRepo controlPanelRepo;
	
	@Autowired
	RegisterTypeRepo registerTypeRepo;
	@Autowired
	RegisterCoversionUnitRepo registerCoversionUnitRepo; 
	
	@Autowired
	DashboardCardDataRepo dashboardCardDataRepo;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getDashbordData", method = RequestMethod.GET)
	public @ResponseBody DashboardCardData getDashbordData(@RequestParam("meterId") int meterId) { 
		DashboardCardData dashbordDataDTO= new DashboardCardData ();
		try {
			Optional<DashboardCardData>  optional=dashboardCardDataRepo.getByMeterId(meterId);
			if(optional.isPresent()) {
				dashbordDataDTO=optional.get();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
	/*	try {
		double dailyEnergyConsumption = 0;
		String dailyEnergyApparent = "0";
		String avarageVoltage;
		String avarageCurrent;
		String monthlyEnergyConsumption="0";
		String monthlyEnergyApparent = "0";
		Date date = new Date();
		   String registerModuleNo="403204";
		   String apparentRegisterNo="403076";
		   Optional<DayWiseConsumption>  energyConsumption=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(registerModuleNo,date);
		   String apparent=consumptionDetailsRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId,apparentRegisterNo);
		// String apparent=dailyConsumptionLogRepo.getConssumptionByModuleNoAndDate(apparentRegisterNo,date);
		
		   Calendar c = Calendar.getInstance();
		   int year = c.get(Calendar.YEAR);
		   int month = c.get(Calendar.MONTH)+1;
		
		//  String energyConsumptionM=dayWiseConsumptionRepo.getConssumptionByModuleNoAndMonth(registerModuleNo,meterId,month,year);
			String energyConsumptionM=consumptionDetailsRepo.getSumConsumptionByMeterYearAndMonth(registerModuleNo,meterId, month, year);
			  System.out.println("month "+month);
			  System.out.println("ENGER "+energyConsumptionM);
			
		 // String  apparentM=consumptionDetailsRepo.getConsumptionByMeterYearAndMonth(apparentRegisterNo,meterId,month,year);
		
		  monthlyEnergyConsumption=energyConsumptionM;
		//  monthlyEnergyApparent=apparentM;
		  if(energyConsumption.isPresent()) {
			  dailyEnergyConsumption=energyConsumption.get().getRegisterValue();
		  }
		  
		  System.out.println("apparent ::  "+apparent);
		  
	  dailyEnergyApparent=apparent;
		
		
		
			Optional<EnergyMeterMaster> optionalMeter = energyMeterMasterRepo.findById(meterId);


		  avarageCurrent=consumptionDetailsRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId, "403010");
		  
			avarageVoltage=dailyConsumptionLogRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId, "403036", new Date());
			
			if(avarageCurrent==null) {
				dashbordDataDTO.setAvarageCurrent("0");
			}else {
				dashbordDataDTO.setAvarageCurrent(avarageCurrent);
			}
			if(avarageVoltage==null) {
				dashbordDataDTO.setAvarageVoltage("0");
			}else {
				dashbordDataDTO.setAvarageVoltage(avarageVoltage);
			}
		
			dashbordDataDTO.setDailyEnergyApparent(dailyEnergyApparent);
			dashbordDataDTO.setDailyEnergyConsumption(dailyEnergyConsumption);
			dashbordDataDTO.setMonthlyEnergyApparent(monthlyEnergyApparent);
			dashbordDataDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);
		} catch (Exception e) {
			e.printStackTrace();
		} */
		return dashbordDataDTO;
	}
	
	
	

	@RequestMapping(value = "/getReportEngergyConsumptionReport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	EnergyConsumptionReport getReportEngergyConsumptionReport(@RequestBody ReportRequestDTO reportRequestDTO) {
		EnergyConsumptionReport status= new EnergyConsumptionReport();
		try {
			   List<Date> dates = getDatesBetween(reportRequestDTO.getStartDate(),reportRequestDTO.getEndDate());
			   double totalEnergy=0;
			   double totalApparent=0;
			   double totalReactive=0;
			   double totalDemand=0;
			   double totalPoweFactor=0;
			   List<DateWiseEnergyConsumptionReportDto> list= new ArrayList<DateWiseEnergyConsumptionReportDto>();
			if(reportRequestDTO.getType().equalsIgnoreCase("All")) {
				   String registerModuleNo="403204";
				   String apparentRegisterNo="403236";
				   String reactiveRegisterNo="403220";
				   String demandRegisterNo="403876";
				   String powerFactorRegisterNo="442340";
				   for(Date date:dates) {
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
					   String strDate = dateFormat.format(date);
					   DateWiseEnergyConsumptionReportDto dateWiseEnergyConsumptionReportDto= new DateWiseEnergyConsumptionReportDto();
					   Optional<DayWiseConsumption>  energyConsumption=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(registerModuleNo,date);
					   Optional<DayWiseConsumption>  apparent=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(apparentRegisterNo,date);
					   Optional<DayWiseConsumption>  reactive=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(reactiveRegisterNo,date);
					   Optional<DayWiseConsumption>  demand  =dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(demandRegisterNo,date);
					   Optional<DayWiseConsumption>  poerFactor=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDate(powerFactorRegisterNo,date);
					   
					   if(energyConsumption.isPresent()) {
						  double energy=energyConsumption.get().getRegisterValue();
						  totalEnergy+=energy;
						  dateWiseEnergyConsumptionReportDto.setEnergy(energy);
					   }
					   
					   if(apparent.isPresent()) {
							  double apparentV=apparent.get().getRegisterValue();
							  totalApparent+=apparentV;
							  dateWiseEnergyConsumptionReportDto.setApparent(apparentV);
						  }
					   if(reactive.isPresent()) {
							  double reactiveV=apparent.get().getRegisterValue();
							  totalReactive+=reactiveV;
							  dateWiseEnergyConsumptionReportDto.setReactive(reactiveV);
						  }
					   if(demand.isPresent()) {
							  double demandV=apparent.get().getRegisterValue();
							  totalDemand+=demandV;
							  dateWiseEnergyConsumptionReportDto.setDemand(demandV);
						  }
					   if(poerFactor.isPresent()) {
							  double powerFactorV=poerFactor.get().getRegisterValue();
							  totalPoweFactor+=powerFactorV;
							  dateWiseEnergyConsumptionReportDto.setPoerFactor(powerFactorV);
						  }
					   dateWiseEnergyConsumptionReportDto.setDate(strDate);
					   list.add(dateWiseEnergyConsumptionReportDto);
					   
				   }
				   status.setTotalApparent(totalApparent);
				   status.setTotalDemand(totalDemand);
				   status.setTotalEnergy(totalEnergy);
				   status.setTotalPoweFactor(totalPoweFactor);
				   status.setTotalReactive(totalReactive);
				   status.setDtos(list);
			}else {
				 String registerModuleNo="403204";
				   String apparentRegisterNo="403076";
				   String reactiveRegisterNo="403220";
				   String demandRegisterNo="403876";
				   String powerFactorRegisterNo="442340";
				   for(Date date:dates) {
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
					   String strDate = dateFormat.format(date);
					   DateWiseEnergyConsumptionReportDto dateWiseEnergyConsumptionReportDto= new DateWiseEnergyConsumptionReportDto();
					   Optional<DayWiseConsumption>  energyConsumption=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(registerModuleNo,reportRequestDTO.getModule().getId(),date);
					   Optional<DayWiseConsumption>  apparent=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(apparentRegisterNo,reportRequestDTO.getModule().getId(),date);
					   Optional<DayWiseConsumption>  reactive=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(reactiveRegisterNo,reportRequestDTO.getModule().getId(),date);
					   Optional<DayWiseConsumption>  demand  =dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(demandRegisterNo,reportRequestDTO.getModule().getId(),date);
					   Optional<DayWiseConsumption>  poerFactor=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(powerFactorRegisterNo,reportRequestDTO.getModule().getId(),date);
					   
					   if(energyConsumption.isPresent()) {
						  double energy=energyConsumption.get().getRegisterValue();
						  totalEnergy+=energy;
						  dateWiseEnergyConsumptionReportDto.setEnergy(energy);
					   }
					   
					   if(apparent.isPresent()) {
							  double apparentV=apparent.get().getRegisterValue();
							  totalApparent+=apparentV;
							  dateWiseEnergyConsumptionReportDto.setApparent(apparentV);
						  }
					   if(reactive.isPresent()) {
							  double reactiveV=apparent.get().getRegisterValue();
							  totalReactive+=reactiveV;
							  dateWiseEnergyConsumptionReportDto.setReactive(reactiveV);
						  }
					   if(demand.isPresent()) {
							  double demandV=apparent.get().getRegisterValue();
							  totalDemand+=demandV;
							  dateWiseEnergyConsumptionReportDto.setDemand(demandV);
						  }
					   if(poerFactor.isPresent()) {
							  double powerFactorV=poerFactor.get().getRegisterValue();
							  totalPoweFactor+=powerFactorV;
							  dateWiseEnergyConsumptionReportDto.setPoerFactor(powerFactorV);
						  }
					   dateWiseEnergyConsumptionReportDto.setDate(strDate);
					   list.add(dateWiseEnergyConsumptionReportDto);
					   
				   }
				   status.setTotalApparent(totalApparent);
				   status.setTotalDemand(totalDemand);
				   status.setTotalEnergy(totalEnergy);
				   status.setTotalPoweFactor(totalPoweFactor);
				   status.setTotalReactive(totalReactive);
				   status.setDtos(list);
			}
			
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return status;
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getRegisterByName", method = RequestMethod.GET)
	public @ResponseBody ResponceObj getRegisterByName(@RequestParam("registerNo") String registerNo) {
		ResponceObj responceObj = new ResponceObj();
		try {
			List<EnergyMeterRegister> optional= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNo(registerNo);
			if(optional.size()!=0) {
				responceObj.setCode(200);
				responceObj.setData("Valid Register No");
				responceObj.setData(optional.get(0));
			}else {
				responceObj.setCode(500);
				responceObj.setData("InValid Register No");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObj;
	}
	
	@RequestMapping(value = "/getEMSDAshboardCard", method = RequestMethod.GET)
	public @ResponseBody EMSDAshboardCardDTO getEMSDAshboardCard() {
		EMSDAshboardCardDTO consumptionDTOs = new EMSDAshboardCardDTO();
		try {
			double todayConsumption = 0;
			double monthlyConsumption = 0;
			double noOfMeter = 0;
			
			double todayCost = 0;
			double monthlyCost = 0;
			double noOfLocation = 0;
			List<EnergyMeterMaster> meters=energyMeterMasterRepo.findAll();
			noOfMeter=meters.size();
			List<ControlPanel> list=controlPanelRepo.findAll();
			noOfLocation=list.size();
			for(EnergyMeterMaster energyMeterMaster:meters) {
				   String registerModuleNo="403204";
				   Optional<DayWiseConsumption> optionalDayWise=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId( registerModuleNo,energyMeterMaster.getId(),new Date());
					LocalDate currentDate = LocalDate.now();
					 int currentYear = currentDate.getYear();
					  int currentMonth = currentDate.getMonthValue();
					String monthlyConsumption1=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(energyMeterMaster.getId(),currentMonth,currentYear);
					if (monthlyConsumption1.equalsIgnoreCase(null)||monthlyConsumption1.equalsIgnoreCase("null")) {
						monthlyConsumption1="0";
					}
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(new Date());
					
			
				todayConsumption=optionalDayWise.get().getRegisterValue();
				monthlyConsumption=Double.valueOf(monthlyConsumption1);
			
				
			}
			consumptionDTOs.setMonthlyConsumption(monthlyConsumption);
			consumptionDTOs.setNoOfLocation(noOfLocation);
			consumptionDTOs.setNoOfMeter(noOfMeter);
			consumptionDTOs.setTodayConsumption(todayConsumption);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	  public static List<Date> getDatesBetween(Date startDate, Date endDate) {
	        List<Date> dates = new ArrayList<>();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(startDate);

	        while (!calendar.getTime().after(endDate)) {
	            dates.add(calendar.getTime());
	            calendar.add(Calendar.DATE, 1);
	        }

	        return dates;
	    }	
	  public static List<Date> getDatesInMonth(int year, int month) {
	        List<Date> dates = new ArrayList<>();
	        Calendar calendar = Calendar.getInstance();

	        // Set the calendar to the first day of the given month and year
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.MONTH, month - 1); // Calendar months are 0-based
	        calendar.set(Calendar.DAY_OF_MONTH, 1);

	        // Get the number of days in the month
	        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	        // Iterate over each day of the month
	        for (int day = 1; day <= daysInMonth; day++) {
	            calendar.set(Calendar.DAY_OF_MONTH, day);
	            dates.add(calendar.getTime());
	        }

	        return dates;
	    }
	  
	  
	  
	  @RequestMapping(value = "/getReportCurrentCunsumptionCostCount", method = RequestMethod.POST)
	  public @ResponseBody ResponseEntity<Map<String, Object>> getReportCount(
	          @RequestBody ConsumptionReportRequest consumptionReportRequest,
	          @RequestParam(required = false) String keyword) {

	      Map<String, Object> response = new HashMap<>();
	      try {
	          List<ConsupmtionReportDTO> list = new ArrayList<>();
			
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Date")) {
				   List<Date> dates = getDatesBetween(consumptionReportRequest.getFromDate(), consumptionReportRequest.getToDate());

				if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

						
						for(Date date:dates) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
				}else {
					List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
					for(Date date:dates) {
						for(EnergyMeterMaster energyMeterMaster:meterMasters) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
					
					}
				}
				
				
			}
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Month")) {
				String monthName = "";
				if(consumptionReportRequest.getMonth()==1) {
					monthName="JAN";
				}
				if(consumptionReportRequest.getMonth()==2) {
					monthName="FEB";
				}
				if(consumptionReportRequest.getMonth()==3) {
					monthName="MAR";
				}
				if(consumptionReportRequest.getMonth()==4) {
					monthName="APR";
				}
				
				if(consumptionReportRequest.getMonth()==5) {
					monthName="MAY";
				}
				
				if(consumptionReportRequest.getMonth()==6) {
					monthName="JUN";
				}
				if(consumptionReportRequest.getMonth()==7) {
					monthName="JUL";
				}
				
				
				if(consumptionReportRequest.getMonth()==8) {
					monthName="AUG";
				}
				if(consumptionReportRequest.getMonth()==9) {
					monthName="SEP";
				}
				if(consumptionReportRequest.getMonth()==10) {
					monthName="OCT";
				}
				if(consumptionReportRequest.getMonth()==11) {
					monthName="NOV";
				}
				
				if(consumptionReportRequest.getMonth()==12) {
					monthName="DEV";
				}
				
				
				
				
				   List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),consumptionReportRequest.getMonth());

				if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

						
						for(Date date:dates) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
				}else {
					List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
					ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
					double totlCostAll = 0;
					double toalCons = 0;
						for(EnergyMeterMaster energyMeterMaster:meterMasters) {
							for(Date date:dates) {
							
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								totlCostAll+=totlCost;
								toalCons+=optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
							
							}else {
								totlCostAll+=0;
								toalCons+=0;
							}
							
						}
							consumptionDTOs.setConsupmtionCost(totlCostAll);
							consumptionDTOs.setConsupmtionUnit(toalCons);
							consumptionDTOs.setDate(monthName+" "+consumptionReportRequest.getYear());
							System.out.println("datw "+consumptionDTOs.getDate());
							consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
							list.add(consumptionDTOs);
					}
				}
				
				
			}
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Year")) {
				
			//	for(int i=1;i<=12;i++) {
				

					if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {
						for(int i=1;i<=12;i++) {
							List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
							for(Date date:dates) {
								ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
									consumptionDTOs.setConsupmtionCost(totlCost);
									consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
								}else {
									consumptionDTOs.setConsupmtionCost(0);
									consumptionDTOs.setConsupmtionUnit(0);
								}
								
								consumptionDTOs.setDate(formattedDate);
								list.add(consumptionDTOs);
							}
						}
							
					}else {
						List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
						ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
					
							for(EnergyMeterMaster energyMeterMaster:meterMasters) {
								double totlCostAll = 0;
								double toalCons = 0;
								for(int i=1;i<=12;i++) {
								List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
								
								for(Date date:dates) {
								
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									totlCostAll+=totlCost;
									toalCons+=optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
								
								}else {
									totlCostAll+=0;
									toalCons+=0;
								}
								
							}
							}
								consumptionDTOs.setConsupmtionCost(totlCostAll);
								consumptionDTOs.setConsupmtionUnit(toalCons);
								consumptionDTOs.setDate(consumptionReportRequest.getYear()+"");
								consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
								list.add(consumptionDTOs);
						}
					}
				//}
				   
				
				
			}
			  // 🔍 Optional filter
	        if (keyword != null && !keyword.trim().isEmpty()) {
	            String lowerKeyword = keyword.toLowerCase();
	            list = list.stream()
	                    .filter(item -> item.getEnergyMeterMaster() != null &&
	                            item.getEnergyMeterMaster().getEnergyMeterNo().toLowerCase().contains(lowerKeyword))
	                    .collect(Collectors.toList());
	        }

	        response.put("count", list.size());
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("message", "Error fetching count: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	  
	  
	  
	  
	  @RequestMapping(value = "/getReportCurrentCunsumptionCostByIdAndDatesPageSearch", method = RequestMethod.POST)
	  public @ResponseBody ResponseEntity<Map<String, Object>> getReportCurrentCunsumptionCostByIdAndDates(
	          @RequestBody ConsumptionReportRequest consumptionReportRequest,
	          @RequestParam int page,
	          @RequestParam int size,
	          @RequestParam(defaultValue = "") String keyword)
	          {

	      Map<String, Object> response = new HashMap<>();
	      try {
	          List<ConsupmtionReportDTO> list = new ArrayList<>();
			
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Date")) {
				   List<Date> dates = getDatesBetween(consumptionReportRequest.getFromDate(), consumptionReportRequest.getToDate());

				if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

						
						for(Date date:dates) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
				}else {
					List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
					for(Date date:dates) {
						for(EnergyMeterMaster energyMeterMaster:meterMasters) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
					
					}
				}
				
				
			}
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Month")) {
				String monthName = "";
				if(consumptionReportRequest.getMonth()==1) {
					monthName="JAN";
				}
				if(consumptionReportRequest.getMonth()==2) {
					monthName="FEB";
				}
				if(consumptionReportRequest.getMonth()==3) {
					monthName="MAR";
				}
				if(consumptionReportRequest.getMonth()==4) {
					monthName="APR";
				}
				
				if(consumptionReportRequest.getMonth()==5) {
					monthName="MAY";
				}
				
				if(consumptionReportRequest.getMonth()==6) {
					monthName="JUN";
				}
				if(consumptionReportRequest.getMonth()==7) {
					monthName="JUL";
				}
				
				
				if(consumptionReportRequest.getMonth()==8) {
					monthName="AUG";
				}
				if(consumptionReportRequest.getMonth()==9) {
					monthName="SEP";
				}
				if(consumptionReportRequest.getMonth()==10) {
					monthName="OCT";
				}
				if(consumptionReportRequest.getMonth()==11) {
					monthName="NOV";
				}
				
				if(consumptionReportRequest.getMonth()==12) {
					monthName="DEV";
				}
				
				
				
				
				   List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),consumptionReportRequest.getMonth());

				if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

						
						for(Date date:dates) {
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
								consumptionDTOs.setConsupmtionCost(totlCost);
								consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
							}else {
								consumptionDTOs.setConsupmtionCost(0);
								consumptionDTOs.setConsupmtionUnit(0);
							}
							
							consumptionDTOs.setDate(formattedDate);
							list.add(consumptionDTOs);
						}
				}else {
					List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
					ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
					double totlCostAll = 0;
					double toalCons = 0;
						for(EnergyMeterMaster energyMeterMaster:meterMasters) {
							for(Date date:dates) {
							
							  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						     String formattedDate = formatter.format(date);
						        String registerModuleNo="403204";
						      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
							Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
						
							if(optional.isPresent()) {
								ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
								double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
								double totlCost=slabRangeDetails*optional.get().getRegisterValue();
								totlCostAll+=totlCost;
								toalCons+=optional.get().getRegisterValue();
								System.out.println("TOTOAL COST "+totlCost);
							
							}else {
								totlCostAll+=0;
								toalCons+=0;
							}
							
						}
							consumptionDTOs.setConsupmtionCost(totlCostAll);
							consumptionDTOs.setConsupmtionUnit(toalCons);
							consumptionDTOs.setDate(monthName+" "+consumptionReportRequest.getYear());
							System.out.println("datw "+consumptionDTOs.getDate());
							consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
							list.add(consumptionDTOs);
					}
				}
				
				
			}
			if(consumptionReportRequest.getReportType().equalsIgnoreCase("Year")) {
				
			//	for(int i=1;i<=12;i++) {
				

					if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {
						for(int i=1;i<=12;i++) {
							List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
							for(Date date:dates) {
								ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
									consumptionDTOs.setConsupmtionCost(totlCost);
									consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
								}else {
									consumptionDTOs.setConsupmtionCost(0);
									consumptionDTOs.setConsupmtionUnit(0);
								}
								
								consumptionDTOs.setDate(formattedDate);
								list.add(consumptionDTOs);
							}
						}
							
					}else {
						List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
						ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
					
							for(EnergyMeterMaster energyMeterMaster:meterMasters) {
								double totlCostAll = 0;
								double toalCons = 0;
								for(int i=1;i<=12;i++) {
								List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
								
								for(Date date:dates) {
								
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									totlCostAll+=totlCost;
									toalCons+=optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
								
								}else {
									totlCostAll+=0;
									toalCons+=0;
								}
								
							}
							}
								consumptionDTOs.setConsupmtionCost(totlCostAll);
								consumptionDTOs.setConsupmtionUnit(toalCons);
								consumptionDTOs.setDate(consumptionReportRequest.getYear()+"");
								consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
								list.add(consumptionDTOs);
						}
					}
				//}
				   
				
				
			}
			   // 🔍 FILTER RESULTS BASED ON SEARCH KEYWORD (if provided)
	        if (keyword != null && !keyword.trim().isEmpty()) {
	            String lowerKeyword = keyword.toLowerCase();
	            list = list.stream()
	                    .filter(item -> item.getEnergyMeterMaster() != null &&
	                            item.getEnergyMeterMaster().getEnergyMeterNo().toLowerCase().contains(lowerKeyword))
	                    .collect(Collectors.toList());
	        }

	        // 🔢 PAGINATION LOGIC
	        int start = page * size;
	        int end = Math.min((start + size), list.size());
	        List<ConsupmtionReportDTO> pagedList = list.subList(start, end);

	        // 🧾 Prepare response map
	        response.put("content", pagedList);
	        response.put("currentPage", page);
	        response.put("pageSize", size);
	        response.put("totalElements", list.size());
	        response.put("totalPages", (int) Math.ceil((double) list.size() / size));

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("message", "Error generating report: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	  
	  
		@RequestMapping(value = "/getReportCurrentCunsumptionCostByIdAndDates", method = RequestMethod.POST)
		public @ResponseBody List<ConsupmtionReportDTO> getReportCurrentCunsumptionCostByIdAndDates(@RequestBody ConsumptionReportRequest consumptionReportRequest) {
			List<ConsupmtionReportDTO> list = new ArrayList<ConsupmtionReportDTO>();
			try {
				
				
				if(consumptionReportRequest.getReportType().equalsIgnoreCase("Date")) {
					   List<Date> dates = getDatesBetween(consumptionReportRequest.getFromDate(), consumptionReportRequest.getToDate());

					if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

							
							for(Date date:dates) {
								ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
									consumptionDTOs.setConsupmtionCost(totlCost);
									consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
								}else {
									consumptionDTOs.setConsupmtionCost(0);
									consumptionDTOs.setConsupmtionUnit(0);
								}
								
								consumptionDTOs.setDate(formattedDate);
								list.add(consumptionDTOs);
							}
					}else {
						List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
						for(Date date:dates) {
							for(EnergyMeterMaster energyMeterMaster:meterMasters) {
								ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
									consumptionDTOs.setConsupmtionCost(totlCost);
									consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
								}else {
									consumptionDTOs.setConsupmtionCost(0);
									consumptionDTOs.setConsupmtionUnit(0);
								}
								consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
								consumptionDTOs.setDate(formattedDate);
								list.add(consumptionDTOs);
							}
						
						}
					}
					
					
				}
				if(consumptionReportRequest.getReportType().equalsIgnoreCase("Month")) {
					String monthName = "";
					if(consumptionReportRequest.getMonth()==1) {
						monthName="JAN";
					}
					if(consumptionReportRequest.getMonth()==2) {
						monthName="FEB";
					}
					if(consumptionReportRequest.getMonth()==3) {
						monthName="MAR";
					}
					if(consumptionReportRequest.getMonth()==4) {
						monthName="APR";
					}
					
					if(consumptionReportRequest.getMonth()==5) {
						monthName="MAY";
					}
					
					if(consumptionReportRequest.getMonth()==6) {
						monthName="JUN";
					}
					if(consumptionReportRequest.getMonth()==7) {
						monthName="JUL";
					}
					
					
					if(consumptionReportRequest.getMonth()==8) {
						monthName="AUG";
					}
					if(consumptionReportRequest.getMonth()==9) {
						monthName="SEP";
					}
					if(consumptionReportRequest.getMonth()==10) {
						monthName="OCT";
					}
					if(consumptionReportRequest.getMonth()==11) {
						monthName="NOV";
					}
					
					if(consumptionReportRequest.getMonth()==12) {
						monthName="DEV";
					}
					
					
					
					
					   List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),consumptionReportRequest.getMonth());

					if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {

							
							for(Date date:dates) {
								ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
									consumptionDTOs.setConsupmtionCost(totlCost);
									consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
								}else {
									consumptionDTOs.setConsupmtionCost(0);
									consumptionDTOs.setConsupmtionUnit(0);
								}
								
								consumptionDTOs.setDate(formattedDate);
								list.add(consumptionDTOs);
							}
					}else {
						List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
						ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
						double totlCostAll = 0;
						double toalCons = 0;
							for(EnergyMeterMaster energyMeterMaster:meterMasters) {
								for(Date date:dates) {
								
								  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							     String formattedDate = formatter.format(date);
							        String registerModuleNo="403204";
							      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
								Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
							
								if(optional.isPresent()) {
									ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
									double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
									double totlCost=slabRangeDetails*optional.get().getRegisterValue();
									totlCostAll+=totlCost;
									toalCons+=optional.get().getRegisterValue();
									System.out.println("TOTOAL COST "+totlCost);
								
								}else {
									totlCostAll+=0;
									toalCons+=0;
								}
								
							}
								consumptionDTOs.setConsupmtionCost(totlCostAll);
								consumptionDTOs.setConsupmtionUnit(toalCons);
								consumptionDTOs.setDate(monthName+" "+consumptionReportRequest.getYear());
								System.out.println("datw "+consumptionDTOs.getDate());
								consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
								list.add(consumptionDTOs);
						}
					}
					
					
				}
				if(consumptionReportRequest.getReportType().equalsIgnoreCase("Year")) {
					
				//	for(int i=1;i<=12;i++) {
					

						if(consumptionReportRequest.getReportFor().equalsIgnoreCase("Meter")) {
							for(int i=1;i<=12;i++) {
								List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
								for(Date date:dates) {
									ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
									  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								     String formattedDate = formatter.format(date);
								        String registerModuleNo="403204";
								      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, consumptionReportRequest.getMeeterId());
									Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(consumptionReportRequest.getMeeterId(), optionalReg.get().getId(), date);
								
									if(optional.isPresent()) {
										ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
										double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
										double totlCost=slabRangeDetails*optional.get().getRegisterValue();
										System.out.println("TOTOAL COST "+totlCost);
										consumptionDTOs.setConsupmtionCost(totlCost);
										consumptionDTOs.setConsupmtionUnit(optional.get().getRegisterValue());
									}else {
										consumptionDTOs.setConsupmtionCost(0);
										consumptionDTOs.setConsupmtionUnit(0);
									}
									
									consumptionDTOs.setDate(formattedDate);
									list.add(consumptionDTOs);
								}
							}
								
						}else {
							List<EnergyMeterMaster> meterMasters=energyMeterMasterRepo.findAll();
							ConsupmtionReportDTO consumptionDTOs = new ConsupmtionReportDTO();
						
								for(EnergyMeterMaster energyMeterMaster:meterMasters) {
									double totlCostAll = 0;
									double toalCons = 0;
									for(int i=1;i<=12;i++) {
									List<Date> dates = getDatesInMonth(consumptionReportRequest.getYear(),i);
									
									for(Date date:dates) {
									
									  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								     String formattedDate = formatter.format(date);
								        String registerModuleNo="403204";
								      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getId());
									Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optionalReg.get().getId(), date);
								
									if(optional.isPresent()) {
										ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
										double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
										double totlCost=slabRangeDetails*optional.get().getRegisterValue();
										totlCostAll+=totlCost;
										toalCons+=optional.get().getRegisterValue();
										System.out.println("TOTOAL COST "+totlCost);
									
									}else {
										totlCostAll+=0;
										toalCons+=0;
									}
									
								}
								}
									consumptionDTOs.setConsupmtionCost(totlCostAll);
									consumptionDTOs.setConsupmtionUnit(toalCons);
									consumptionDTOs.setDate(consumptionReportRequest.getYear()+"");
									consumptionDTOs.setEnergyMeterMaster(energyMeterMaster);
									list.add(consumptionDTOs);
							}
						}
					//}
					   
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}





	  
	  /////#######################################################################################################################//////////////
	
	@RequestMapping(value = "/getLastDaysCurrentCostById", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getLastDaysCurrentCostById(@RequestParam("meterId") int meterId,@RequestParam("days") int days) {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data	=new ArrayList<Double>();
			 LocalDate currentDate = LocalDate.now();
			 List<Date> dates= new ArrayList<Date>();
			 dates.add(new Date());
			for(int i=1;i<days;i++) {
			LocalDate newDate = currentDate.minusDays(i);
	        Date utilDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        dates.add(utilDate);
			}
			
			int j=2;
			for(Date date:dates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
			      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());
				Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				dateStr.add(formattedDate);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					System.out.println("TOTOAL COST "+totlCost);
					data.add(totlCost);
				}else {
					data.add(0.0);
				}
				
				
				j++;
			}
			
			consumptionDTOs.setDoubleList(data);
			consumptionDTOs.setStrings(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	@RequestMapping(value = "/getLastDaysCostTableById", method = RequestMethod.GET)
	public @ResponseBody List<ConsupmtionDashboardDTO> getLastDaysCostTableById(@RequestParam("meterId") int meterId,@RequestParam("days") int days) {
		List<ConsupmtionDashboardDTO> consumptionDTOs = new ArrayList<ConsupmtionDashboardDTO>();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<String> data=new ArrayList<String>();
			 LocalDate currentDate = LocalDate.now();
			 List<Date> dates= new ArrayList<Date>();
			 dates.add(new Date());
			for(int i=1;i<days;i++) {
			LocalDate newDate = currentDate.minusDays(i);
	        Date utilDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        dates.add(utilDate);
			}
			
			int j=2;
			for(Date date:dates) {
				ConsupmtionDashboardDTO consupmtionDashboardDTO= new ConsupmtionDashboardDTO();
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        
			     String registerModuleNo="403204";
			     Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
			     Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());
					Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);

					//				Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, meterId, date);
					dateStr.add(formattedDate);
					if(optional.isPresent()) {
						
							consupmtionDashboardDTO.setValue(optional.get().getRegisterValue());
							ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
							double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
							double totlCost=slabRangeDetails*optional.get().getRegisterValue();
							System.out.println("TOTOAL COST "+totlCost);
						//	data.add(totlCost);
							 consupmtionDashboardDTO.setValueStr(totlCost+" Rs.");
					}else {
					
						 consupmtionDashboardDTO.setValueStr("0");
					}
					 System.out.println("Date :: "+date);
					
				consupmtionDashboardDTO.setDate(formattedDate);
				consumptionDTOs.add(consupmtionDashboardDTO);
				j++;
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	
	
	
	@RequestMapping(value = "/getMonthlyCurrentCostById", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getMonthlyCurrentCostById(@RequestParam("meterId") int meterId,@RequestParam("year") int year) {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data	=new ArrayList<Double>();
		
			double janCost = 0;
			double febCost = 0;
			double marCost = 0;
			double aprCost = 0;
			double mayCost = 0;
			double junCost = 0;
			double julCost = 0;
			double augCost = 0;
			double sepCost = 0;
			double octCost = 0;
			double novCost = 0;
			double decCost = 0;
			
			 String jan="JAN-"+year;
			 String feb="FEB-"+year;
			 String mar="MAR-"+year;
			 String apr="APR-"+year;
			 String may="MAY-"+year;
			 String jun="JUN-"+year;
			 String jul="JUL-"+year;
			 String aug="AUG-"+year;
			 String sep="SEP-"+year;
			 String oct="OCT-"+year;
			 String nov="NOV-"+year;
			 String dec="DEC-"+year;

			 dateStr.add(jan);
			 dateStr.add(feb);
			 dateStr.add(mar);
			 dateStr.add(apr);
			 dateStr.add(may);
			 dateStr.add(jun);
			 dateStr.add(jul);
			 dateStr.add(aug);
			 dateStr.add(sep);
			 dateStr.add(oct);
			 dateStr.add(nov);
			 dateStr.add(dec);
			 
		
			 // JAN 
			 List<Date> janDates = getAllDates(year, 1);
		
			 for(Date date:janDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
				     Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());

			//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
				Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);

				//dateStr.add(formattedDate);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					janCost+=totlCost;
				}
				
			}
			
			 
			 // FEB
			 List<Date> febDates = getAllDates(year, 2);
			 for(Date date:febDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			      
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				//dateStr.add(formattedDate);
				if(optional.isPresent() ) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					febCost+=totlCost;
				}
				
			}
			 
			 
			 // Mar
			 List<Date> marDates = getAllDates(year, 3);
			 for(Date date:marDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			      
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				//dateStr.add(formattedDate);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					marCost+=totlCost;
				}
				
			}
			 
			// APR
			 List<Date> aprDates = getAllDates(year, 4);
			 for(Date date:aprDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			      
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				//dateStr.add(formattedDate);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					aprCost+=totlCost;
				}
				
			}
			 
			 
			// May
			 List<Date> mayDates = getAllDates(year, 5);
			 for(Date date:mayDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					mayCost+=totlCost;
				}
				
			}
			 
			 
			 
			// Jun
			 List<Date> junDates = getAllDates(year, 6);
			 for(Date date:junDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					junCost+=totlCost;
				}
				
			}
			 
			 
			// Jul
			 List<Date> julDates = getAllDates(year, 7);
			 for(Date date:julDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					System.out.println("consumptionPriceSlab "+consumptionPriceSlab.toString());
					if(consumptionPriceSlab.getId()!=0) {
						double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
						double totlCost=slabRangeDetails*optional.get().getRegisterValue();
						julCost+=totlCost;
						   System.out.println("formattedDate  "+totlCost);
					}
					
				}
				
			}
			 
			 
			// Aug
			 List<Date> augDates = getAllDates(year, 8);
			 for(Date date:augDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			     
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					if(consumptionPriceSlab.getId()!=0) {
						double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
						double totlCost=slabRangeDetails*optional.get().getRegisterValue();
						System.out.println("Value "+totlCost);
						augCost+=totlCost;
					}
					
				}
				
			}
			 
			// Sep
			 List<Date> sepDates = getAllDates(year, 9);
			 for(Date date:sepDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					sepCost+=totlCost;
				}
				
			}
			 
			 
			// Oct
			 List<Date> octDates = getAllDates(year, 10);
			 for(Date date:octDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			      
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					octCost+=totlCost;
				}
				
			}
			 
			 
			// NOV
			 List<Date> novDates = getAllDates(year, 11);
			 for(Date date:novDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					novCost+=totlCost;
				}
				
			}
			 
			 
			// DEC
			 List<Date> decDates = getAllDates(year, 12);
			 for(Date date:decDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, meterId);

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					decCost+=totlCost;
				}
				
			}
			 
			 data.add(janCost);
			 data.add(febCost);
			 data.add(marCost);
			 data.add(aprCost);
			 data.add(mayCost);
			 data.add(junCost);
			 data.add(julCost);
			 data.add(augCost);
			 data.add(sepCost);
			 data.add(octCost);
			 data.add(novCost);
			 data.add(decCost);
			
			 
			 
			consumptionDTOs.setDoubleList(data);
			consumptionDTOs.setStrings(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}

	@RequestMapping(value = "/getMonthlyCurrentCostTableById", method = RequestMethod.GET)
	public @ResponseBody List<ConsupmtionDashboardDTO> getMonthlyCurrentCostTableById(@RequestParam("meterId") int meterId,@RequestParam("year") int year) {
		List<ConsupmtionDashboardDTO> consumptionDTOs = new ArrayList<ConsupmtionDashboardDTO>();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data	=new ArrayList<Double>();
		
			double janCost = 0;
			double febCost = 0;
			double marCost = 0;
			double aprCost = 0;
			double mayCost = 0;
			double junCost = 0;
			double julCost = 0;
			double augCost = 0;
			double sepCost = 0;
			double octCost = 0;
			double novCost = 0;
			double decCost = 0;
			
			 String jan="JAN-"+year;
			 String feb="FEB-"+year;
			 String mar="MAR-"+year;
			 String apr="APR-"+year;
			 String may="MAY-"+year;
			 String jun="JUN-"+year;
			 String jul="JUL-"+year;
			 String aug="AUG-"+year;
			 String sep="SEP-"+year;
			 String oct="OCT-"+year;
			 String nov="NOV-"+year;
			 String dec="DEC-"+year;

		
			 // JAN 
			 List<Date> janDates = getAllDates(year, 1);
		
			 for(Date date:janDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					janCost+=totlCost;
				}
				
			}
			
			 
			 // FEB
			 List<Date> febDates = getAllDates(year, 2);
			 for(Date date:febDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					febCost+=totlCost;
				}
				
			}
			 
			 
			 // Mar
			 List<Date> marDates = getAllDates(year, 3);
			 for(Date date:marDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					marCost+=totlCost;
				}
				
			}
			 
			// APR
			 List<Date> aprDates = getAllDates(year, 4);
			 for(Date date:aprDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					aprCost+=totlCost;
				}
				
			}
			 
			 
			// May
			 List<Date> mayDates = getAllDates(year, 5);
			 for(Date date:mayDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					mayCost+=totlCost;
				}
				
			}
			 
			 
			 
			// Jun
			 List<Date> junDates = getAllDates(year, 6);
			 for(Date date:junDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					junCost+=totlCost;
				}
				
			}
			 
			 
			// Jul
			 List<Date> julDates = getAllDates(year, 7);
			 for(Date date:julDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					System.out.println("consumptionPriceSlab "+consumptionPriceSlab.toString());
					if(consumptionPriceSlab.getId()!=0) {
						double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
						double totlCost=slabRangeDetails*optional.get().getRegisterValue();
						julCost+=totlCost;
						   System.out.println("formattedDate  "+totlCost);
					}
					
				}
				
			}
			 
			 
			// Aug
			 List<Date> augDates = getAllDates(year, 8);
			 for(Date date:augDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			     
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					if(consumptionPriceSlab.getId()!=0) {
						double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
						double totlCost=slabRangeDetails*optional.get().getRegisterValue();
						System.out.println("Value "+totlCost);
						augCost+=totlCost;
					}
					
				}
				
			}
			 
			// Sep
			 List<Date> sepDates = getAllDates(year, 9);
			 for(Date date:sepDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					sepCost+=totlCost;
				}
				
			}
			 
			 
			// Oct
			 List<Date> octDates = getAllDates(year, 10);
			 for(Date date:octDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					octCost+=totlCost;
				}
				
			}
			 
			 
			// NOV
			 List<Date> novDates = getAllDates(year, 11);
			 for(Date date:novDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					novCost+=totlCost;
				}
				
			}
			 
			 
			// DEC
			 List<Date> decDates = getAllDates(year, 12);
			 for(Date date:decDates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMet=energyMeterMasterRepo.findById(meterId);
			        Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMet.get().getModule().getId());

					//	List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, date);
						Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				if(optional.isPresent()) {
					ConsumptionPriceSlab  consumptionPriceSlab=consumptionPriceSlabRepo.getEffectivePriceSlab(date);
					double slabRangeDetails=consumptionPriceSlabRepo.getByConsumptionPriceRateBySlabIdAndUnitValue(consumptionPriceSlab.getId(),optional.get().getRegisterValue());
					double totlCost=slabRangeDetails*optional.get().getRegisterValue();
					decCost+=totlCost;
				}
				
			}
			 
			
			 ConsupmtionDashboardDTO janDTO= new ConsupmtionDashboardDTO();
			 janDTO.setMonth(jan);
			 janDTO.setValue(janCost);
			 consumptionDTOs.add(janDTO);
			 
			 
			 ConsupmtionDashboardDTO febDTO= new ConsupmtionDashboardDTO();
			 febDTO.setMonth(feb);
			 febDTO.setValue(febCost);
			 consumptionDTOs.add(febDTO);
			 
			 
			 ConsupmtionDashboardDTO marDTO= new ConsupmtionDashboardDTO();
			 marDTO.setMonth(mar);
			 marDTO.setValue(marCost);
			 consumptionDTOs.add(marDTO);
			 
			 
			 ConsupmtionDashboardDTO aprDTO= new ConsupmtionDashboardDTO();
			 aprDTO.setMonth(apr);
			 aprDTO.setValue(aprCost);
			 consumptionDTOs.add(aprDTO);
			 
			 
			 ConsupmtionDashboardDTO mayDTO= new ConsupmtionDashboardDTO();
			 mayDTO.setMonth(may);
			 mayDTO.setValue(mayCost);
			 consumptionDTOs.add(mayDTO);
			 
			 
			 ConsupmtionDashboardDTO junDTO= new ConsupmtionDashboardDTO();
			 junDTO.setMonth(jun);
			 junDTO.setValue(junCost);
			 consumptionDTOs.add(junDTO);
			 
			 
			 ConsupmtionDashboardDTO julDTO= new ConsupmtionDashboardDTO();
			 julDTO.setMonth(jul);
			 julDTO.setValue(julCost);
			 consumptionDTOs.add(julDTO);
			 
			 
			 ConsupmtionDashboardDTO augDTO= new ConsupmtionDashboardDTO();
			 augDTO.setMonth(aug);
			 augDTO.setValue(augCost);
			 consumptionDTOs.add(augDTO);
			 
			 
			 ConsupmtionDashboardDTO sepDTO= new ConsupmtionDashboardDTO();
			 sepDTO.setMonth(sep);
			 sepDTO.setValue(sepCost);
			 consumptionDTOs.add(sepDTO);
			 
			 
			 ConsupmtionDashboardDTO octDTO= new ConsupmtionDashboardDTO();
			 octDTO.setMonth(oct);
			 octDTO.setValue(octCost);
			 consumptionDTOs.add(octDTO);
			 
			 
			 ConsupmtionDashboardDTO novDTO= new ConsupmtionDashboardDTO();
			 novDTO.setMonth(nov);
			 novDTO.setValue(novCost);
			 consumptionDTOs.add(novDTO);
			 
			 ConsupmtionDashboardDTO decDTO= new ConsupmtionDashboardDTO();
			 decDTO.setMonth(dec);
			 decDTO.setValue(decCost);
			 consumptionDTOs.add(decDTO);
			 
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	
	 public static List<Date> getAllDates(int year, int month) {
	        List<Date> dates = new ArrayList<>();
	        Calendar calendar = Calendar.getInstance();
	        
	        // Set the year, month, and the first day of the month
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.MONTH, month - 1); // Calendar.MONTH is zero-based
	        calendar.set(Calendar.DAY_OF_MONTH, 1);

	        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	        for (int day = 1; day <= daysInMonth; day++) {
	            calendar.set(Calendar.DAY_OF_MONTH, day);
	            dates.add(calendar.getTime());
	        }

	        return dates;
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@RequestMapping(value = "/getAllRegisterDetialsByMeterId1", method = RequestMethod.GET)
	public @ResponseBody List<RegisterDTO> getAllRegisterDetials1(@RequestParam("meterId") int meterId) {
		List<RegisterDTO> list1 = new ArrayList<RegisterDTO>();
		try {
			List<Integer>	list = consumptionDetailsRepo.getDistinceRegisterIDByMeter(meterId);
			System.out.println("Register List "+list.size());
			
			for(Integer registerId:list){
				
				
				
				System.out.println("Register  "+registerId);
				Optional<EnergyMeterRegister> optionalRes=energyServices.getEnergyMeterRegisterById(registerId);
				Optional<ConsumptionDetails> optional=consumptionDetailsRepo.getConsumptionByMeterIDAndRegisterId(meterId,registerId);
				if(optional.get().getRegisterValue().equalsIgnoreCase("-")) {
					System.out.println("Containt - ");
				}else {
					System.out.println("NOT Containt - ");
					RegisterDTO dto= new RegisterDTO();
					dto.setRegisterName(optionalRes.get().getRegisterName());
					dto.setRegisterValue(Double.valueOf(optional.get().getRegisterValue()));
					list1.add(dto);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/getCurrentConsupmtionByMeterId", method = RequestMethod.GET)
	public @ResponseBody String getCurrentConsupmtionByMeterId(@RequestParam("meterId") int meterId) {
		String consumption="";
		try {
			  String registerModuleNo="403204";
			List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(meterId,registerModuleNo, new Date());
			if(list.size()!=0) {
				
				consumption=list.get(0).getRegisterValue();
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumption;
	}





	@RequestMapping(value = "/getLastDaysCurrentConsupmtionById", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getLastDaysCurrentConsupmtionById(@RequestParam("meterId") int meterId,@RequestParam("days") int days) {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data	=new ArrayList<Double>();
			 LocalDate currentDate = LocalDate.now();
			 List<Date> dates= new ArrayList<Date>();
			 dates.add(new Date());
			for(int i=1;i<days;i++) {
			LocalDate newDate = currentDate.minusDays(i);
	        Date utilDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        dates.add(utilDate);
			}
			
			int j=2;
			for(Date date:dates) {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        String registerModuleNo="403204";
			        Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
			      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());
				Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
				System.out.println("D "+optional.isPresent());
				dateStr.add(formattedDate);
				if(optional.isPresent()) {
					data.add(optional.get().getRegisterValue());
				
					
				}else {
					data.add(0.0);
				}
				j++;
			}
			
			consumptionDTOs.setDoubleList(data);
			consumptionDTOs.setStrings(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	@RequestMapping(value = "/getLastDaysConsupmtionTableById", method = RequestMethod.GET)
	public @ResponseBody List<ConsupmtionDashboardDTO> getLastDaysConsupmtionTableById(@RequestParam("meterId") int meterId,@RequestParam("days") int days) {
		List<ConsupmtionDashboardDTO> consumptionDTOs = new ArrayList<ConsupmtionDashboardDTO>();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<String> data=new ArrayList<String>();
			 LocalDate currentDate = LocalDate.now();
			 List<Date> dates= new ArrayList<Date>();
			 dates.add(new Date());
			for(int i=1;i<days;i++) {
			LocalDate newDate = currentDate.minusDays(i);
	        Date utilDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        dates.add(utilDate);
			}
			
			int j=2;
			for(Date date:dates) {
				ConsupmtionDashboardDTO consupmtionDashboardDTO= new ConsupmtionDashboardDTO();
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			     String formattedDate = formatter.format(date);
			        
			     String registerModuleNo="403204";
			     Optional<EnergyMeterMaster> optionalMeter=energyMeterMasterRepo.findById(meterId);
			      Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, optionalMeter.get().getModule().getId());

									Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), date);
					dateStr.add(formattedDate);
					if(optional.isPresent()) {
						
						 consupmtionDashboardDTO.setValue(optional.get().getRegisterValue());
						
					}else {
					
						 consupmtionDashboardDTO.setValue(0.0);
					}
					 System.out.println("Date :: "+date);
					
				consupmtionDashboardDTO.setDate(formattedDate);
				consumptionDTOs.add(consupmtionDashboardDTO);
				j++;
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	@RequestMapping(value = "/getYearWiseConsupmtionByIdAndYear", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getYearWiseConsupmtionByIdAndYear(@RequestParam("id") int id,@RequestParam("year") int year) {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data=new ArrayList<Double>();
			
			
		
				 
			        
				String sumOfJan=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,1,year);
				if(sumOfJan!=null) {
					data.add(Double.valueOf(sumOfJan));
				}else {
					data.add(Double.valueOf(0));
				}
				String monthJAN="JAN-"+year;
				dateStr.add(monthJAN);
				
				
				
				String sumOfFeb=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,2,year);
				if(sumOfFeb!=null) {
					data.add(Double.valueOf(sumOfFeb));
				}else {
					data.add(Double.valueOf(0));
				}
				String monthFeb="Feb-"+year;
				dateStr.add(monthFeb);
				
				
				
				String sumOfMar=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,3,year);
				if(sumOfMar!=null) {
					data.add(Double.valueOf(sumOfMar));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthMar="Mar-"+year;
				dateStr.add(monthMar);
				
				
				
				String sumOfApr=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,4,year);
				if(sumOfApr!=null) {
					data.add(Double.valueOf(sumOfApr));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthApr="Apr-"+year;
				dateStr.add(monthApr);
				
				String sumOfMay=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,5,year);
				if(sumOfMay!=null) {
					data.add(Double.valueOf(sumOfMay));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthMay="May-"+year;
				dateStr.add(monthMay);
				
				String sumOfJun=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,6,year);
				if(sumOfJun!=null) {
					data.add(Double.valueOf(sumOfJun));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthJun="Jun-"+year;
				dateStr.add(monthJun);
				
				String sumOfJuly=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,7,year);
				if(sumOfJuly!=null) {
					data.add(Double.valueOf(sumOfJuly));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthJuly="July-"+year;
				dateStr.add(monthJuly);
				
				String sumOfAug=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,8,year);
				if(sumOfAug!=null) {
					data.add(Double.valueOf(sumOfAug));
				}else {
					data.add(Double.valueOf(0));
				}
				
				
				String monthAug="Aug-"+year;
				dateStr.add(monthAug);
				
				String sumOfSep=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,9,year);
				if(sumOfSep!=null) {
					data.add(Double.valueOf(sumOfSep));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthSep="Sep-"+year;
				dateStr.add(monthJAN);
				
				String sumOfOct=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,10,year);
				if(sumOfOct!=null) {
					data.add(Double.valueOf(sumOfOct));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthOct="Oct-"+year;
				dateStr.add(monthOct);
				
				String sumOfNov=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,11,year);
				if(sumOfNov!=null) {
					data.add(Double.valueOf(sumOfNov));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthNov="Nov-"+year;
				dateStr.add(monthNov);
				
				String sumOfDec=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,12,year);
				if(sumOfDec!=null) {
					data.add(Double.valueOf(sumOfDec));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthDec="Dec-"+year;
				dateStr.add(monthDec);
				
			
			consumptionDTOs.setDoubleList(data);
			consumptionDTOs.setStrings(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	
	
	
	@RequestMapping(value = "/getYearWiseConsupmtionTableByIdAndYear", method = RequestMethod.GET)
	public @ResponseBody List<ConsupmtionDashboardDTO>  getYearWiseConsupmtionTableByIdAndYear(@RequestParam("id") int id,@RequestParam("year") int year) {
		List<ConsupmtionDashboardDTO> consumptionDTOs = new ArrayList<ConsupmtionDashboardDTO>();
		try {
			List<String> dateStr=new ArrayList<String>();
			List<Double> data=new ArrayList<Double>();
			
			
		
			        
				String sumOfJan=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,1,year);
				if(sumOfJan!=null) {
					data.add(Double.valueOf(sumOfJan));
				}else {
					data.add(Double.valueOf(0));
				}
				String monthJAN="JAN-"+year;
				dateStr.add(monthJAN);
				
				
				
				String sumOfFeb=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,2,year);
				if(sumOfFeb!=null) {
					data.add(Double.valueOf(sumOfFeb));
				}else {
					data.add(Double.valueOf(0));
				}
				String monthFeb="Feb-"+year;
				dateStr.add(monthFeb);
				
				
				
				String sumOfMar=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,3,year);
				if(sumOfMar!=null) {
					data.add(Double.valueOf(sumOfMar));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthMar="Mar-"+year;
				dateStr.add(monthMar);
				
				
				
				String sumOfApr=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,4,year);
				if(sumOfApr!=null) {
					data.add(Double.valueOf(sumOfApr));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthApr="Apr-"+year;
				dateStr.add(monthApr);
				
				String sumOfMay=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,5,year);
				if(sumOfMay!=null) {
					data.add(Double.valueOf(sumOfMay));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthMay="May-"+year;
				dateStr.add(monthMay);
				
				String sumOfJun=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,6,year);
				if(sumOfJun!=null) {
					data.add(Double.valueOf(sumOfJun));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthJun="Jun-"+year;
				dateStr.add(monthJun);
				
				String sumOfJuly=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,7,year);
				if(sumOfJuly!=null) {
					data.add(Double.valueOf(sumOfJuly));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthJuly="July-"+year;
				dateStr.add(monthJuly);
				
				String sumOfAug=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,8,year);
				if(sumOfAug!=null) {
					data.add(Double.valueOf(sumOfAug));
				}else {
					data.add(Double.valueOf(0));
				}
				
				
				String monthAug="Aug-"+year;
				dateStr.add(monthAug);
				
				String sumOfSep=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,9,year);
				if(sumOfSep!=null) {
					data.add(Double.valueOf(sumOfSep));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthSep="Sep-"+year;
				dateStr.add(monthJAN);
				
				String sumOfOct=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,10,year);
				if(sumOfOct!=null) {
					data.add(Double.valueOf(sumOfOct));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthOct="Oct-"+year;
				dateStr.add(monthOct);
				
				String sumOfNov=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,11,year);
				if(sumOfNov!=null) {
					data.add(Double.valueOf(sumOfNov));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthNov="Nov-"+year;
				dateStr.add(monthNov);
				
				String sumOfDec=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(id,12,year);
				if(sumOfDec!=null) {
					data.add(Double.valueOf(sumOfDec));
				}else {
					data.add(Double.valueOf(0));
				}
				
				String monthDec="Dec-"+year;
				dateStr.add(monthDec);
				
				
				int i=0;
				for(String  string: dateStr) {
					ConsupmtionDashboardDTO consupmtionDashboardDTO= new ConsupmtionDashboardDTO();
					System.out.println("Date "+string);
					System.out.println("Data "+data.get(i));
					consupmtionDashboardDTO.setDate(string);
					consupmtionDashboardDTO.setValue(data.get(i));
					consumptionDTOs.add(consupmtionDashboardDTO);
				i++;
				}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getAllEnergyMeterMasters", method = RequestMethod.GET)
	public @ResponseBody List<DashboardMeterConsumptionDTO> getAllEnergyMeterMasters() {
		List<DashboardMeterConsumptionDTO> list1 = new ArrayList<DashboardMeterConsumptionDTO>();
		try {
			List<EnergyMeterMaster>	list = energyServices.getAllEnergyMeterMasters();
			Set<ControlPanel> panels=new HashSet<ControlPanel>();
			for(EnergyMeterMaster energyMeterMaster:list) {
				panels.add(energyMeterMaster.getPanel());
			}
			
			for(ControlPanel controlPanel:panels) {
				DashboardMeterConsumptionDTO dashboardMeterConsumptionDTO= new DashboardMeterConsumptionDTO();
				List<MeterConsumptionDTO> meterConsumptions= new ArrayList<MeterConsumptionDTO>();
				List<EnergyMeterMaster>	meters = energyServices.getAllEnergyMeterMastersByPanels(controlPanel.getId());
				for(EnergyMeterMaster energyMeterMaster:meters) {
					   String registerModuleNo="403204";
					//List<DailyConsumptionLog> optional=dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(),registerModuleNo, new Date());
					Optional<DayWiseConsumption> optional=dayWiseConsumptionRepo.getConssumptionByModuleNoAndDateAndMeterId(registerModuleNo, energyMeterMaster.getId(), new Date());
					
					     Optional<EnergyMeterRegister> optionalReg=energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo, energyMeterMaster.getModule().getId());
					     
							 LocalDate currentDate = LocalDate.now();
						        int currentYear = currentDate.getYear();
						        int currentMonth = currentDate.getMonthValue();
							String monthlyConsumption=dayWiseConsumptionRepo.getSumConsumptionByMeterYearAndMonth(energyMeterMaster.getId(),currentMonth,currentYear);
						if (monthlyConsumption.equalsIgnoreCase(null)||monthlyConsumption.equalsIgnoreCase("null")) {
							monthlyConsumption="0";
						}
					MeterConsumptionDTO consumptionDTO= new MeterConsumptionDTO();
					//consumptionDTO.setTodayconsumption(optionalDayWise.get().getRegisterValue());
					
					/*
					 * System.out.println("Monthly ::"+monthlyConsumption);
					 * System.out.println("Daily ::"+optionalDayWise.get().getRegisterValue());
					 */
					consumptionDTO.setMonthlyconsumption(Double.valueOf(monthlyConsumption));
					
					consumptionDTO.setTodayconsumption(optional.get().getRegisterValue());
					consumptionDTO.setMeterId(energyMeterMaster.getId());
					consumptionDTO.setMeterNo(energyMeterMaster.getEnergyMeterNo());
					meterConsumptions.add(consumptionDTO);
				}
				
				dashboardMeterConsumptionDTO.setConsumptions(meterConsumptions);
				dashboardMeterConsumptionDTO.setPanel(controlPanel);
				
				list1.add(dashboardMeterConsumptionDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//********************************************************************* Engery Meter Master *********************************************************//

	
		@RequestMapping(value = "/addEnergyMeterMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		Status addEnergyMeterMaster(@RequestBody EnergyMeterMaster energyMeterMaster) {
			Status status= new Status();
			try {
				energyMeterMaster.setCreateDateTime(new Date());
				energyServices.addEnergyMeterMaster(energyMeterMaster);
				status.setCode(200);
				status.setMessage("Energy Meter  is added successfully");
				return status;
			} catch (Exception e) {
				// e.printStackTrace();
				e.printStackTrace();
				return new Status(e.toString());
			}

		}
		
		@PutMapping(value = "/updateEnergyMeterMaster/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Status updateEnergyMeterMaster(
		        @PathVariable("id") int id,
		        @RequestBody EnergyMeterMaster updatedMaster) {

		    Status status = new Status();
		    try {
		        Optional<EnergyMeterMaster> existingOpt = energyMeterMasterRepo.findById(id);
		        if (existingOpt.isPresent()) {
		            EnergyMeterMaster existingMaster = existingOpt.get();

		            // Update fields
		            existingMaster.setEnergyMeterNo(updatedMaster.getEnergyMeterNo());
		           // existingMaster.setCreateDateTime(new Date()); // optional, to track update time

		            // Update panel if provided
		            if (updatedMaster.getPanel() != null && updatedMaster.getPanel().getId() != 0) {
		                existingMaster.setPanel(updatedMaster.getPanel());
		            }

		            // Update module if provided
		            if (updatedMaster.getModule() != null && updatedMaster.getModule().getId() != 0) {
		                existingMaster.setModule(updatedMaster.getModule());
		            }

		            energyMeterMasterRepo.save(existingMaster);

		            status.setCode(200);
		            status.setMessage("Energy Meter Master updated successfully");
		            return status;
		        } else {
		            status.setCode(404);
		            status.setMessage("Energy Meter Master not found");
		            return status;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return new Status(e.toString());
		    }
		}

		@DeleteMapping("/deleteEnergyMeterMaster/{id}")
		public @ResponseBody Status deleteEnergyMeterMaster(@PathVariable("id") int id) {
		    Status status = new Status();
		    try {
		        Optional<EnergyMeterMaster> masterOpt = energyMeterMasterRepo.findById(id);
		        if (masterOpt.isPresent()) {
		            energyMeterMasterRepo.deleteById(id);
		            status.setCode(200);
		            status.setMessage("Energy Meter Master deleted successfully");
		        } else {
		            status.setCode(404);
		            status.setMessage("Energy Meter Master not found");
		        }
		        return status;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new Status(e.toString());
		    }
		}
		
	
		// old api
		@RequestMapping(value = "/deleteEnergyMeterMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		Status deleteEnergyMeterMaster(@RequestBody EnergyMeterMaster energyMeterMaster) {
			Status status= new Status();
			try {
				energyServices.deleteEnergyMeterMaster(energyMeterMaster);
				status.setCode(200);
				status.setMessage("Energy Meter  is Deleted successfully");
				return status;
			} catch (Exception e) {
				// e.printStackTrace();
				e.printStackTrace();
				return new Status(e.toString());
			}

		}
		
		@RequestMapping(value = "/getEnergyMeterMasterById", method = RequestMethod.GET)
		public @ResponseBody EnergyMeterMaster getEnergyMeterMasterById(@RequestParam("id") int id) {
			EnergyMeterMaster  energyMeterMaster = new EnergyMeterMaster ();
			try {
				Optional<EnergyMeterMaster> optional = energyServices.getEnergyMeterMasterById(id);
			if(optional.isPresent()) {
				energyMeterMaster=optional.get();
			}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return energyMeterMaster;
		}
		@RequestMapping(value = "/getAllMeters", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterMaster> getAllMeters() {
			List<EnergyMeterMaster> list = new ArrayList<EnergyMeterMaster>();
			try {
				list = energyServices.getAllMeters();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		@RequestMapping(value = "/getEnergyMeterMasterByLimitAndSearch", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterMaster> getEnergyMeterMasterByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
			List<EnergyMeterMaster> list = new ArrayList<EnergyMeterMaster>();
			try {
				list = energyServices.getEnergyMeterMasterByLimitAndSearch(searchText, pageNo, perPage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		@RequestMapping(value = "/getEnergyMeterMasterCountByLimitAndSearch", method = RequestMethod.GET)
		public @ResponseBody int getEnergyMeterMasterCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
			int count = 0 ;
			try {
				count = energyServices.getEnergyMeterMasterCountByLimitAndSearch(searchText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		@RequestMapping(value = "/getEnergyMeterMasterByLimit", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterMaster> getEnergyMeterMasterByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
			List<EnergyMeterMaster> list = new ArrayList<EnergyMeterMaster>();
			try {
				list = energyServices.getEnergyMeterMasterByLimit(pageNo,perPage);
			

			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		@RequestMapping(value = "/getCountOfEnergyMeterMaster", method = RequestMethod.GET)
		public @ResponseBody int getCountOfEnergyMeterMaster() {
			int count = 0;
			try {
					count =energyServices.getCountOfEnergyMeterMaster();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		@GetMapping("/exportEnergyMeterMasters")
		public ResponseEntity<Resource> exportEnergyMeterMasters() {
		    try (Workbook workbook = new XSSFWorkbook()) {
		        Sheet sheet = workbook.createSheet("EnergyMeterMasters");

		        Row header = sheet.createRow(0);
		        String[] columns = {
		            "ID", "Panel ID", "Panel Name", "Module ID", "Module No", "Energy Meter No", "Create Date"
		        };

		        for (int i = 0; i < columns.length; i++) {
		            header.createCell(i).setCellValue(columns[i]);
		        }

		        List<EnergyMeterMaster> masters = energyMeterMasterRepo.findAll();
		        int rowNum = 1;

		        for (EnergyMeterMaster master : masters) {
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(master.getId());
		            row.createCell(1).setCellValue(master.getPanel() != null ? master.getPanel().getId() : 0);
		            row.createCell(2).setCellValue(master.getPanel() != null ? master.getPanel().getPanelName() : "");
		            row.createCell(3).setCellValue(master.getModule() != null ? master.getModule().getId() : 0);
		            row.createCell(4).setCellValue(master.getModule() != null ? master.getModule().getModuleNo() : "");
		            row.createCell(5).setCellValue(master.getEnergyMeterNo());
		            row.createCell(6).setCellValue(master.getCreateDateTime() != null ? master.getCreateDateTime().toString() : "");
		        }

		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        workbook.write(out);
		        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

		        return ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=EnergyMeterMasters.xlsx")
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(resource);
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}

		@PostMapping("/importEnergyMeterMasters")
		public ResponseEntity<Map<String, Object>> importEnergyMeterMasters(@RequestParam("file") MultipartFile file) {
		    Map<String, Object> response = new HashMap<>();
		    List<String> errors = new ArrayList<>();

		    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
		        Sheet sheet = workbook.getSheetAt(0);
		        int rowCount = 0;

		        for (Row row : sheet) {
		            if (rowCount == 0) { // Skip header
		                rowCount++;
		                continue;
		            }

		            try {
		                EnergyMeterMaster master = new EnergyMeterMaster();

		                // 🔹 Read ControlPanel identifiers
		                String panelLocation = row.getCell(0).getStringCellValue();
		                String panelPlant = row.getCell(1).getStringCellValue();

		                // 🔹 Find ControlPanel by locationName & plantName
		                ControlPanel panel = controlPanelRepo.findByLocationNameAndPlantName(panelLocation, panelPlant)
		                        .orElse(null);
		                master.setPanel(panel);

		                // 🔹 Read Module identifiers
		                String companyName = row.getCell(2).getStringCellValue();
		                String moduleNo = row.getCell(3).getStringCellValue();

		                // 🔹 Find Module by companyName & moduleNo
		                EnergyMeterModule module = energyMeterModuleRepo.findByCompanyNameAndModuleNo(companyName, moduleNo)
		                        .orElse(null);
		                master.setModule(module);

		                // 🔹 Other fields
		                master.setEnergyMeterNo(row.getCell(4).getStringCellValue());
		                master.setCreateDateTime(new Date());

		                energyMeterMasterRepo.save(master);
		            } catch (Exception e) {
		                errors.add("Row " + rowCount + ": " + e.getMessage());
		            }

		            rowCount++;
		        }

		        response.put("message", "Excel imported successfully!");
		        if (!errors.isEmpty()) response.put("errors", errors);
		        return ResponseEntity.ok(response);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.put("error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		}

		
		@GetMapping("/downloadEnergyMeterMasterTemplate")
		public ResponseEntity<Resource> downloadEnergyMeterMasterTemplate() {
		    try (Workbook workbook = new XSSFWorkbook()) {
		        Sheet sheet = workbook.createSheet("Template");

		        Row header = sheet.createRow(0);
		        String[] columns = {
		            "panel_location_name", "panel_plant_name", 
		            "module_company_name", "module_no", 
		            "energy_meter_no"
		        };

		        for (int i = 0; i < columns.length; i++) {
		            header.createCell(i).setCellValue(columns[i]);
		        }

		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        workbook.write(out);
		        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

		        return ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=EnergyMeterMaster_Template.xlsx")
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(resource);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}

		
	
	
	
	//********************************************************************* Engery Meter Register*********************************************************//

	
		@RequestMapping(value = "/addEnergyMeterRegister", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		Status addEnergyMeterRegister(@RequestBody EnergyMeterRegister energyMeterRegister) {
			Status status= new Status();
			try {
				energyMeterRegister.setCreateDateTime(new Date());
				energyServices.addEnergyMeterRegister(energyMeterRegister);
				status.setCode(200);
				status.setMessage("Energy Meter Register is added successfully");
				return status;
			} catch (Exception e) {
				// e.printStackTrace();
				e.printStackTrace();
				return new Status(e.toString());
			}

		}
		
		@PutMapping(value = "/updateEnergyMeterRegister/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Status updateEnergyMeterRegister(
		        @PathVariable("id") int id,
		        @RequestBody EnergyMeterRegister updatedRegister) {

		    Status status = new Status();
		    try {
		        Optional<EnergyMeterRegister> existingOpt = energyMeterRegisterRepo.findById(id);
		        if (existingOpt.isPresent()) {
		            EnergyMeterRegister existingRegister = existingOpt.get();

		            // Update fields
		            existingRegister.setRegisterName(updatedRegister.getRegisterName());
		            existingRegister.setRegisterNo(updatedRegister.getRegisterNo());
		            existingRegister.setRegisterFormat(updatedRegister.getRegisterFormat());
		            existingRegister.setUom(updatedRegister.getUom());
		            existingRegister.setScale(updatedRegister.getScale());
		            existingRegister.setMultiplier(updatedRegister.getMultiplier());
		            existingRegister.setUnit(updatedRegister.getUnit());
		            existingRegister.setRegisterActive(updatedRegister.getRegisterActive());
		            existingRegister.setRegisterByteType(updatedRegister.getRegisterByteType());
		            existingRegister.setRegisterCoversionUnit(updatedRegister.getRegisterCoversionUnit());
		            existingRegister.setRegisterMaskChar(updatedRegister.getRegisterMaskChar());
		           // existingRegister.setCreateDateTime(new Date()); // optional, for last updated

		            // Update module (if provided)
		            if (updatedRegister.getModule() != null && updatedRegister.getModule().getId() != 0) {
		                existingRegister.setModule(updatedRegister.getModule());
		            }

		            energyMeterRegisterRepo.save(existingRegister);

		            status.setCode(200);
		            status.setMessage("Energy Meter Register updated successfully");
		            return status;
		        } else {
		            status.setCode(404);
		            status.setMessage("Energy Meter Register not found");
		            return status;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return new Status(e.toString());
		    }
		}
		
		@DeleteMapping("/deleteEnergyMeterRegister/{id}")
		public @ResponseBody Status deleteEnergyMeterRegister(@PathVariable("id") int id) {
		    Status status = new Status();
		    try {
		        Optional<EnergyMeterRegister> registerOpt = energyMeterRegisterRepo.findById(id);
		        if (registerOpt.isPresent()) {
		            energyMeterRegisterRepo.deleteById(id);
		            status.setCode(200);
		            status.setMessage("Energy Meter Register deleted successfully");
		        } else {
		            status.setCode(404);
		            status.setMessage("Energy Meter Register not found");
		        }
		        return status;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new Status(e.toString());
		    }
		}


		//old api
		@RequestMapping(value = "/deleteEnergyMeterRegister", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		Status deleteEnergyMeterRegister(@RequestBody EnergyMeterRegister energyMeterRegister) {
			Status status= new Status();
			try {
				
				energyServices.deleteEnergyMeterRegister(energyMeterRegister);
				status.setCode(200);
				status.setMessage("Energy Meter Register is Deleted successfully");
				return status;
			} catch (Exception e) {
				// e.printStackTrace();
				e.printStackTrace();
				return new Status(e.toString());
			}

		}
		@RequestMapping(value = "/getAllEnergyMeterRegisters", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterRegister> getAllEnergyMeterRegisters() {
			List<EnergyMeterRegister> list = new ArrayList<EnergyMeterRegister>();
			try {
				list = energyServices.getAllEnergyMeterRegisters();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		
		
		@RequestMapping(value = "/getAllRegisters", method = RequestMethod.GET)
		public @ResponseBody List<RegisterDTO> getAllRegisters() {
			List<RegisterDTO> list = new ArrayList<RegisterDTO>();
			try {
				List<String> strings = energyServices.getAllRegisters();
				for(String str:strings) {
					System.out.println(" String    "+str);
					List<String> names=energyServices.getRegisterNameByRegisterNo(str);
					
					RegisterDTO dto= new RegisterDTO();
					dto.setRegisterName(names.get(0));
					dto.setRegisterNo(str);
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		@RequestMapping(value = "/getAllRegistersByModuleId", method = RequestMethod.GET)
		public @ResponseBody List<RegisterDTO> getAllRegistersByModuleId(@RequestParam("moduleId") int moduleId) {
			List<RegisterDTO> list = new ArrayList<RegisterDTO>();
			try {
				List<String> strings = energyServices.getAllRegistersByModule(moduleId);
				for(String str:strings) {
					System.out.println(" String    "+str);
					List<String> names=energyServices.getRegisterNameByRegisterNo(str);
					
					RegisterDTO dto= new RegisterDTO();
					dto.setRegisterName(names.get(0));
					dto.setRegisterNo(str);
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		
		@RequestMapping(value = "/getEnergyMeterRegisterByLimitAndSearch", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterRegister> getEnergyMeterRegisterByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
			List<EnergyMeterRegister> list = new ArrayList<EnergyMeterRegister>();
			try {
				list = energyServices.getEnergyMeterRegisterByLimitAndSearch(searchText, pageNo, perPage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		@RequestMapping(value = "/getEnergyMeterRegisterCountByLimitAndSearch", method = RequestMethod.GET)
		public @ResponseBody int getEnergyMeterRegisterCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
			int count = 0 ;
			try {
				count = energyServices.getEnergyMeterRegisterCountByLimitAndSearch(searchText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		@RequestMapping(value = "/getEnergyMeterRegisterByLimit", method = RequestMethod.GET)
		public @ResponseBody List<EnergyMeterRegister> getEngergyMeterModuleByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
			List<EnergyMeterRegister> list = new ArrayList<EnergyMeterRegister>();
			try {
				list = energyServices.getEnergyMeterRegisterByLimit(pageNo,perPage);
			

			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		@RequestMapping(value = "/getCountOfEnergyMeterRegister", method = RequestMethod.GET)
		public @ResponseBody int getCountOfEngergyMeterModule() {
			int count = 0;
			try {
					count =energyServices.getCountOfEnergyMeterRegister();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		
	
		@PostMapping("/importEnergyMeterRegisters")
		public ResponseEntity<Map<String, Object>> importEnergyMeterRegisters(@RequestParam("file") MultipartFile file) {
		    Map<String, Object> response = new HashMap<>();

		    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
		        Sheet sheet = workbook.getSheetAt(0);
		        int rowCount = 0;

		        for (Row row : sheet) {
		            if (rowCount == 0) { // Skip header row
		                rowCount++;
		                continue;
		            }

		            EnergyMeterRegister register = new EnergyMeterRegister();
		            
		            // Assuming module_id is in column 0
		            int moduleId = (int) row.getCell(0).getNumericCellValue();
		            EnergyMeterModule module = energyMeterModuleRepo.findById(moduleId).orElse(null);
		            register.setModule(module);

		            register.setRegisterName(row.getCell(1).getStringCellValue());
		            register.setRegisterNo(row.getCell(2).getStringCellValue());
		            register.setRegisterFormat(row.getCell(3).getStringCellValue());
		            register.setUom(row.getCell(4).getStringCellValue());
		            register.setScale(row.getCell(5).getStringCellValue());
		            register.setMultiplier(row.getCell(6).getStringCellValue());
		            register.setUnit(row.getCell(7).getStringCellValue());
		            register.setCreateDateTime(new Date());
		            register.setRegisterActive((int) row.getCell(8).getNumericCellValue());
		            register.setRegisterByteType((int) row.getCell(9).getNumericCellValue());
		            register.setRegisterCoversionUnit(row.getCell(10).getStringCellValue());
		            register.setRegisterMaskChar(row.getCell(11).getStringCellValue());

		            energyMeterRegisterRepo.save(register);
		        }

		        response.put("message", "Excel imported successfully!");
		        return ResponseEntity.ok(response);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.put("error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		}

	
		@GetMapping("/downloadEnergyMeterRegisterTemplate")
		public ResponseEntity<Resource> downloadEnergyMeterRegisterTemplate() {
		    try (Workbook workbook = new XSSFWorkbook()) {
		        Sheet sheet = workbook.createSheet("Template");

		        Row header = sheet.createRow(0);
		        String[] columns = {
		            "module_id", "register_name", "register_no", "register_format",
		            "uom", "scale", "multiplier", "unit",
		            "register_active", "register_byte_type", "register_coversion_unit", "register_mask_char"
		        };

		        for (int i = 0; i < columns.length; i++) {
		            header.createCell(i).setCellValue(columns[i]);
		        }

		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        workbook.write(out);
		        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

		        return ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=EnergyMeterRegister_Template.xlsx")
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(resource);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}

		@GetMapping("/exportEnergyMeterRegisters")
		public ResponseEntity<Resource> exportEnergyMeterRegisters() {
		    try (Workbook workbook = new XSSFWorkbook()) {
		        Sheet sheet = workbook.createSheet("EnergyMeterRegisters");

		        Row header = sheet.createRow(0);
		        String[] columns = {
		            "ID", "Module ID", "Register Name", "Register No", "Register Format",
		            "UOM", "Scale", "Multiplier", "Unit",
		            "Register Active", "Register Byte Type", "Register Conversion Unit", "Register Mask Char", "Create Date"
		        };

		        for (int i = 0; i < columns.length; i++) {
		            header.createCell(i).setCellValue(columns[i]);
		        }

		        List<EnergyMeterRegister> registers = energyMeterRegisterRepo.findAll();
		        int rowNum = 1;

		        for (EnergyMeterRegister reg : registers) {
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(reg.getId());
		            row.createCell(1).setCellValue(reg.getModule() != null ? reg.getModule().getId() : 0);
		            row.createCell(2).setCellValue(reg.getRegisterName());
		            row.createCell(3).setCellValue(reg.getRegisterNo());
		            row.createCell(4).setCellValue(reg.getRegisterFormat());
		            row.createCell(5).setCellValue(reg.getUom());
		            row.createCell(6).setCellValue(reg.getScale());
		            row.createCell(7).setCellValue(reg.getMultiplier());
		            row.createCell(8).setCellValue(reg.getUnit());
		            row.createCell(9).setCellValue(reg.getRegisterActive());
		            row.createCell(10).setCellValue(reg.getRegisterByteType());
		            row.createCell(11).setCellValue(reg.getRegisterCoversionUnit());
		            row.createCell(12).setCellValue(reg.getRegisterMaskChar());
		            row.createCell(13).setCellValue(reg.getCreateDateTime() != null ? reg.getCreateDateTime().toString() : "");
		        }

		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        workbook.write(out);
		        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

		        return ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=EnergyMeterRegisters.xlsx")
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(resource);
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		}

	
	
	//********************************************************************* Engery Meter Module*********************************************************//

	
	@RequestMapping(value = "/addEnergyMeterModule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addEnergyMeterModule(@RequestBody EnergyMeterModule engergyMeterModule) {
		Status status= new Status();
		try {
			
			engergyMeterModule.setCreateDateTime(new Date());
			energyServices.addEnergyMeterModule(engergyMeterModule);
			status.setCode(200);
			status.setMessage("Engergy Meter Module is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@PutMapping(value = "/updateEnergyMeterModule/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateEnergyMeterModule(
	        @PathVariable("id") int id,
	        @RequestBody EnergyMeterModule updatedModule) {

	    Status status = new Status();
	    try {
	        Optional<EnergyMeterModule> existingOpt = energyMeterModuleRepo.findById(id);
	        if (existingOpt.isPresent()) {
	            EnergyMeterModule existingModule = existingOpt.get();

	            // Update fields
	            existingModule.setCompanyName(updatedModule.getCompanyName());
	            existingModule.setModuleNo(updatedModule.getModuleNo());
	           // existingModule.setCreateDateTime(new Date()); // Optional — to record update time

	            energyMeterModuleRepo.save(existingModule);

	            status.setCode(200);
	            status.setMessage("Energy Meter Module updated successfully");
	            return status;
	        } else {
	            status.setCode(404);
	            status.setMessage("Energy Meter Module not found");
	            return status;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	@DeleteMapping("/deleteEnergyMeterModule/{id}")
	public @ResponseBody Status deleteEnergyMeterModule(@PathVariable("id") int id) {
	    Status status = new Status();
	    try {
	        Optional<EnergyMeterModule> moduleOpt = energyMeterModuleRepo.findById(id);
	        if (moduleOpt.isPresent()) {
	            energyMeterModuleRepo.deleteById(id);
	            status.setCode(200);
	            status.setMessage("Energy Meter Module deleted successfully");
	        } else {
	            status.setCode(404);
	            status.setMessage("Energy Meter Module not found");
	        }
	        return status;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	
	//od api
	@RequestMapping(value = "/deleteEnergyMeterModule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteEnergyMeterModule(@RequestBody EnergyMeterModule engergyMeterModule) {
		Status status= new Status();
		try {
			energyServices.deleteEnergyMeterModule(engergyMeterModule);
			status.setCode(200);
			status.setMessage("Engergy Meter Module is Deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@RequestMapping(value = "/getAllEnergyMeterModule", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterModule> getAllEnergyMeterModule() {
		List<EnergyMeterModule> list = new ArrayList<EnergyMeterModule>();
		try {
			list = energyServices.getAllEnergyMeterModule();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getEnergyMeterModuleByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterModule> getEnergyMeterModuleByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<EnergyMeterModule> list = new ArrayList<EnergyMeterModule>();
		try {
			list = energyServices.getEnergyMeterModuleByLimitAndSearch(searchText, pageNo, perPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getEnergyMeterModuleCountByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getEnergyMeterModuleCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
		int count = 0 ;
		try {
			count = energyServices.getEnergyMeterModuleCountByLimitAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getEnergyMeterModuleByLimit", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterModule> getEnergyMeterModuleByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<EnergyMeterModule> list = new ArrayList<EnergyMeterModule>();
		try {
			list = energyServices.getEnergyMeterModuleByLimit(pageNo,perPage);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCountOfEnergyMeterModule", method = RequestMethod.GET)
	public @ResponseBody int getCountOfEnergyMeterModule() {
		int count = 0;
		try {
				count =energyServices.getCountOfEnergyMeterModule();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@PostMapping("/importEnergyMeterModules")
	public @ResponseBody Status importEnergyMeterModules(@RequestParam("file") MultipartFile file) {
	    Status status = new Status();
	    try {
	        if (file.isEmpty()) {
	            status.setCode(400);
	            status.setMessage("Please upload a valid Excel file");
	            return status;
	        }

	        List<EnergyMeterModule> modules = new ArrayList<>();

	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            int rowNum = 0;
	            for (Row row : sheet) {
	                if (rowNum++ == 0) continue; // Skip header

	                EnergyMeterModule module = new EnergyMeterModule();
	                module.setCompanyName(getCellValue(row.getCell(0)));
	                module.setModuleNo(getCellValue(row.getCell(1)));
	                module.setCreateDateTime(new Date());

	                modules.add(module);
	            }
	        }

	        // Save all modules
	        energyMeterModuleRepo.saveAll(modules);

	        status.setCode(200);
	        status.setMessage(modules.size() + " Energy Meter Modules imported successfully");
	        return status;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	@GetMapping("/downloadEnergyMeterModuleTemplate")
	public void downloadEnergyMeterModuleTemplate(HttpServletResponse response) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("EnergyMeterModule Template");

	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("companyName");
	        header.createCell(1).setCellValue("moduleNo");

	        for (int i = 0; i < 2; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=EnergyMeterModule_Template.xlsx");

	        workbook.write(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@GetMapping("/exportEnergyMeterModules")
	public void exportEnergyMeterModules(HttpServletResponse response) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("EnergyMeterModules");

	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("ID");
	        header.createCell(1).setCellValue("Company Name");
	        header.createCell(2).setCellValue("Module No");
	        header.createCell(3).setCellValue("Created Date");

	        List<EnergyMeterModule> modules = energyMeterModuleRepo.findAll();

	        int rowNum = 1;
	        for (EnergyMeterModule module : modules) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(module.getId());
	            row.createCell(1).setCellValue(module.getCompanyName());
	            row.createCell(2).setCellValue(module.getModuleNo());
	            row.createCell(3).setCellValue(
	                module.getCreateDateTime() != null ? module.getCreateDateTime().toString() : ""
	            );
	        }

	        for (int i = 0; i < 4; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=EnergyMeterModules.xlsx");

	        workbook.write(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	//********************************************************************* Control Panel *********************************************************//

	
	@RequestMapping(value = "/addControlPanel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addControlPanel(@RequestBody ControlPanel controlPanel) {
		Status status= new Status();
		try {
			controlPanel.setCreateDateTime(new Date());
			energyServices.addControlPanel(controlPanel);
			status.setCode(200);
			status.setMessage("Control Panel is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@PutMapping(value = "/updateControlPanel/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateControlPanel(
	        @PathVariable("id") int id,
	        @RequestBody ControlPanel updatedPanel) {

	    Status status = new Status();
	    try {
	        Optional<ControlPanel> existingOpt = controlPanelRepo.findById(id);
	        if (existingOpt.isPresent()) {
	            ControlPanel existingPanel = existingOpt.get();

	            // Update fields
	            existingPanel.setPanelName(updatedPanel.getPanelName());
	            existingPanel.setPanelIpAddress(updatedPanel.getPanelIpAddress());
	            existingPanel.setPanelPort(updatedPanel.getPanelPort());
	            existingPanel.setLocationName(updatedPanel.getLocationName());
	            existingPanel.setPlantName(updatedPanel.getPlantName());
	            //existingPanel.setCreateDateTime(new Date()); // optional, to track last update

	            controlPanelRepo.save(existingPanel);

	            status.setCode(200);
	            status.setMessage("Control Panel updated successfully");
	            return status;
	        } else {
	            status.setCode(404);
	            status.setMessage("Control Panel not found");
	            return status;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	@DeleteMapping("/deleteControlPanel/{id}")
	public @ResponseBody Status deleteControlPanel(@PathVariable("id") int id) {
	    Status status = new Status();
	    try {
	        Optional<ControlPanel> panelOpt = controlPanelRepo.findById(id);
	        if (panelOpt.isPresent()) {
	            controlPanelRepo.deleteById(id);
	            status.setCode(200);
	            status.setMessage("Control Panel deleted successfully");
	        } else {
	            status.setCode(404);
	            status.setMessage("Control Panel not found");
	        }
	        return status;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	
	//old api
	@RequestMapping(value = "/deleteControlPanels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteControlPanel(@RequestBody ControlPanel controlPanel) {
		Status status= new Status();
		try {
			energyServices.deleteControlPanel(controlPanel);
			status.setCode(200);
			status.setMessage("Control Panel is Deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/getControlPanelByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<ControlPanel> getControlPanelByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<ControlPanel> list = new ArrayList<ControlPanel>();
		try {
			list = energyServices.getControlPanelByLimitAndSearch(searchText, pageNo, perPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllControlPanels", method = RequestMethod.GET)
	public @ResponseBody List<ControlPanel> getAllControlPanels() {
		List<ControlPanel> list = new ArrayList<ControlPanel>();
		try {
			list = energyServices.getAllControlPanels();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@RequestMapping(value = "/getControlPanelCountByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getControlPanelCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
		int count = 0 ;
		try {
			count = energyServices.getControlPanelCountByLimitAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getControlPanelByLimit", method = RequestMethod.GET)
	public @ResponseBody List<ControlPanel> getControlPanelByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<ControlPanel> list = new ArrayList<ControlPanel>();
		try {
			list = energyServices.getControlPanelByLimit(pageNo,perPage);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCountOfControlPanel", method = RequestMethod.GET)
	public @ResponseBody int getCountOfControlPanel() {
		int count = 0;
		try {
				count =energyServices.getCountOfControlPanel();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@PostMapping("/importControlPanels")
	public @ResponseBody Status importControlPanels(@RequestParam("file") MultipartFile file) {
	    Status status = new Status();
	    try {
	        if (file.isEmpty()) {
	            status.setCode(400);
	            status.setMessage("Please upload a valid Excel file");
	            return status;
	        }

	        List<ControlPanel> panels = new ArrayList<>();

	        // ✅ Read Excel file using Apache POI
	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            int rowNum = 0;
	            for (Row row : sheet) {
	                // Skip header
	                if (rowNum++ == 0) continue;

	                ControlPanel panel = new ControlPanel();

	                // Read Excel columns (adjust indexes as per your Excel)
	                panel.setPanelName(getCellValue(row.getCell(0)));
	                panel.setPanelIpAddress(getCellValue(row.getCell(1)));
	                panel.setPanelPort(getCellValue(row.getCell(2)));
	                panel.setLocationName(getCellValue(row.getCell(3)));
	                panel.setPlantName(getCellValue(row.getCell(4)));
	                panel.setCreateDateTime(new Date());

	                panels.add(panel);
	            }
	        }

	        // ✅ Save all panels
	        controlPanelRepo.saveAll(panels);
	        status.setCode(200);
	        status.setMessage(panels.size() + " Control Panels imported successfully");
	        return status;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	@GetMapping("/downloadControlPanelTemplate")
	public void downloadControlPanelTemplate(HttpServletResponse response) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("ControlPanel Template");

	        // Create header row
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("panelName");
	        header.createCell(1).setCellValue("panelIpAddress");
	        header.createCell(2).setCellValue("panelPort");
	        header.createCell(3).setCellValue("locationName");
	        header.createCell(4).setCellValue("plantName");

	        // Adjust column width
	        for (int i = 0; i < 5; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Set response headers
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=ControlPanel_Template.xlsx");

	        // Write workbook to response output stream
	        workbook.write(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	@GetMapping("/exportControlPanels")
	public void exportControlPanels(HttpServletResponse response) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("ControlPanels");

	        // Create header
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("ID");
	        header.createCell(1).setCellValue("Panel Name");
	        header.createCell(2).setCellValue("Panel IP Address");
	        header.createCell(3).setCellValue("Panel Port");
	        header.createCell(4).setCellValue("Location Name");
	        header.createCell(5).setCellValue("Plant Name");
	        header.createCell(6).setCellValue("Created Date");

	        List<ControlPanel> panels = controlPanelRepo.findAll();

	        int rowNum = 1;
	        for (ControlPanel panel : panels) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(panel.getId());
	            row.createCell(1).setCellValue(panel.getPanelName());
	            row.createCell(2).setCellValue(panel.getPanelIpAddress());
	            row.createCell(3).setCellValue(panel.getPanelPort());
	            row.createCell(4).setCellValue(panel.getLocationName());
	            row.createCell(5).setCellValue(panel.getPlantName());
	            row.createCell(6).setCellValue(panel.getCreateDateTime() != null ? panel.getCreateDateTime().toString() : "");
	        }

	        for (int i = 0; i < 7; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=ControlPanels.xlsx");

	        workbook.write(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	
	// ✅ Helper method to safely get cell value as string
	private String getCellValue(Cell cell) {
	    if (cell == null) return null;
	    cell.setCellType(CellType.STRING);
	    return cell.getStringCellValue().trim();
	}

	
	//********************************************************************* Price Slab  *********************************************************//

	
	@RequestMapping(value = "/addPriceSlab", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addPriceSlab(@RequestBody ConsumptionPriceSlab consumptionPriceSlab) {
		Status status= new Status();
		try {
			consumptionPriceSlab.setAddedDate(new Date());
			consumptionPriceSlab.setActive(1);
			Optional<ConsumptionPriceSlab> activePriceSlab=consumptionPriceSlabRepo.getActivePriceSlab();
			if(activePriceSlab.isPresent()) {
				ConsumptionPriceSlab ConsumptionPriceSlab=activePriceSlab.get();
				ConsumptionPriceSlab.setActive(0);
			    Calendar calendar = Calendar.getInstance();
		        calendar.setTime(consumptionPriceSlab.getEffectiveFrom());

		        // Subtract one day from the date
		        calendar.add(Calendar.DAY_OF_MONTH, -1);

		        // Get the new date
		        Date previousDate = calendar.getTime();
				ConsumptionPriceSlab.setEffectiveUptoDate(previousDate);
				consumptionPriceSlabRepo.save(ConsumptionPriceSlab);
			}
		
			ConsumptionPriceSlab consumptionPriceSlab2=consumptionPriceSlabRepo.save(consumptionPriceSlab);
			for(PriceSlabRangeDetails priceSlabRangeDetails:consumptionPriceSlab.getList()) {
				priceSlabRangeDetails.setEffectiveFrom(consumptionPriceSlab.getEffectiveFrom());
				priceSlabRangeDetails.setConsumptionPriceSlab(consumptionPriceSlab2);
				priceSlabRangeDetailsRepo.save(priceSlabRangeDetails);
			}
			status.setCode(200);
			status.setMessage("Price Slab is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@RequestMapping(value = "/deleteConsumptionPriceSlab", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteControlPanel(@RequestBody ConsumptionPriceSlab consumptionPriceSlab) {
		Status status= new Status();
		try {
			List<PriceSlabRangeDetails> priceSlabRangeDetails=priceSlabRangeDetailsRepo.getByConsumptionPriceSlab(consumptionPriceSlab.getId());
			
			//for(PriceSlabRangeDetails details:priceSlabRangeDetails) {
			consumptionPriceSlabRepo.delete(consumptionPriceSlab);
			priceSlabRangeDetailsRepo.deleteAll(priceSlabRangeDetails);
			status.setCode(200);
			status.setMessage("Price Slab is Deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/getConsumptionPriceSlabByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<ConsumptionPriceSlab> list = new ArrayList<ConsumptionPriceSlab>();
		try {
			list = consumptionPriceSlabRepo.getConsumptionPriceSlabByLimitAndSearch(searchText, pageNo, perPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllConsumptionPriceSlab", method = RequestMethod.GET)
	public @ResponseBody List<ConsumptionPriceSlab> getAllConsumptionPriceSlab() {
		List<ConsumptionPriceSlab> list = new ArrayList<ConsumptionPriceSlab>();
		try {
			list = consumptionPriceSlabRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllConsumptionPriceSlabDetailsById", method = RequestMethod.GET)
	public @ResponseBody List<PriceSlabRangeDetails> getAllConsumptionPriceSlabDetailsById(@RequestParam("priceSlabId") int priceSlabId) {
		List<PriceSlabRangeDetails> list = new ArrayList<PriceSlabRangeDetails>();
		try {
			list = priceSlabRangeDetailsRepo.getByConsumptionPriceSlab(priceSlabId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getConsumptionPriceSlabCountByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getConsumptionPriceSlabCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
		int count = 0 ;
		try {
			count = consumptionPriceSlabRepo.getConsumptionPriceSlabCountByLimitAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getConsumptionPriceSlabByLimit", method = RequestMethod.GET)
	public @ResponseBody List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<ConsumptionPriceSlab> list = new ArrayList<ConsumptionPriceSlab>();
		try {
			list = consumptionPriceSlabRepo.getConsumptionPriceSlabByLimit(pageNo,perPage);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCountOfConsumptionPriceSlab", method = RequestMethod.GET)
	public @ResponseBody int getCountOfConsumptionPriceSlab() {
		int count = 0;
		try {
				count =(int) consumptionPriceSlabRepo.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	//********************************************************************* Grop*********************************************************//

	@GetMapping("/exportEnergyMeterRegisterGroupItems")
	public ResponseEntity<byte[]> exportEnergyMeterRegisterGroupItems() {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("Register Group Items");

	        // 🔹 Header row
	        Row headerRow = sheet.createRow(0);
	        String[] headers = {
	            "Group ID",
	            "Group Name",
	            "No of Register",
//	            "Module ID",
	            "Module Company Name",
	            "Module No",
	            "Register Name",
	            "Register No"
	        };
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	        }

	        // 🔹 Fetch all group items
	        List<EnergyMeterRegisterGroupItems> allItems = energyMeterRegisterGroupItemsRepo.findAll();

	        int rowIdx = 1;
	        for (EnergyMeterRegisterGroupItems item : allItems) {
	            Row row = sheet.createRow(rowIdx++);

	            EnergyMeterRegisterGroup group = item.getGroup();
	            EnergyMeterModule module = (group != null) ? group.getModule() : null;

	            row.createCell(0).setCellValue(group != null ? group.getId() : 0);
	            row.createCell(1).setCellValue(group != null ? group.getGroupName() : "");
	            row.createCell(2).setCellValue(group != null ? group.getNoOfRegister() : "");
	         //   row.createCell(3).setCellValue(module != null ? module.getId() : 0);
	            row.createCell(3).setCellValue(module != null ? module.getCompanyName() : "");
	            row.createCell(4).setCellValue(module != null ? module.getModuleNo() : "");
	            row.createCell(5).setCellValue(item.getRegisterName());
	            row.createCell(6).setCellValue(item.getRegisterNo());
	        }

	        // 🔹 Auto-size columns
	        for (int i = 0; i < headers.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // 🔹 Write workbook to byte array
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        workbook.write(out);
	        byte[] bytes = out.toByteArray();

	        HttpHeaders headersExcel = new HttpHeaders();
	        headersExcel.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headersExcel.setContentDispositionFormData("attachment", "EnergyMeterRegisterGroupItems.xlsx");

	        return new ResponseEntity<>(bytes, headersExcel, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
//	
//	@PostMapping("/importEnergyMeterRegisterGroupItems")
//	public ResponseEntity<Status> importEnergyMeterRegisterGroupItems(@RequestParam("file") MultipartFile file) {
//	    Status status = new Status();
//	    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
//	        Sheet sheet = workbook.getSheetAt(0);
//	        int rowCount = 0;
//	        List<EnergyMeterRegisterGroupItems> importedItems = new ArrayList<>();
//
//	        for (Row row : sheet) {
//	            if (rowCount == 0) { // Skip header row
//	                rowCount++;
//	                continue;
//	            }
//
//	            // ✅ Read safely (handle any data type)
//	            int groupId = (int) getNumericValue(row.getCell(0));
//	            String groupName = getStringValue(row.getCell(1));
//	            String noOfRegister = getStringValue(row.getCell(2));
//	            String moduleCompanyName = getStringValue(row.getCell(3));
//	            String moduleNo = getStringValue(row.getCell(4));
//	            String registerName = getStringValue(row.getCell(5));
//	            String registerNo = getStringValue(row.getCell(6));
//
//	            // ✅ Fetch related entities
//	            EnergyMeterRegisterGroup group = energyMeterRegisterGroupRepo.findById(groupId).orElse(null);
//	            if (group == null) continue;
//
//	            EnergyMeterModule module = energyMeterModuleRepo.findByCompanyNameAndModuleNo(moduleCompanyName, moduleNo).orElse(null);
//	            if (module == null) continue;
//
//	            // ✅ Ensure the group is linked to this module
//	            group.setModule(module);
//
//	            // ✅ Create new group item
//	            EnergyMeterRegisterGroupItems item = new EnergyMeterRegisterGroupItems();
//	            item.setGroup(group);
//	            item.setRegisterName(registerName);
//	            item.setRegisterNo(registerNo);
//
//	            importedItems.add(item);
//	        }
//
//	        if (!importedItems.isEmpty()) {
//	            energyMeterRegisterGroupItemsRepo.saveAll(importedItems);
//	        }
//
//	        status.setCode(200);
//	        status.setMessage("Successfully imported " + importedItems.size() + " group items.");
//	        return ResponseEntity.ok(status);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        status.setCode(500);
//	        status.setMessage("Import failed: " + e.getMessage());
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
//	    }
//	}

	/** ✅ Utility: Safely read a string cell */
	private String getStringValue(Cell cell) {
	    if (cell == null) return "";
	    cell.setCellType(CellType.STRING);
	    return cell.getStringCellValue().trim();
	}

	/** ✅ Utility: Safely read a numeric cell */
	private double getNumericValue(Cell cell) {
	    if (cell == null) return 0;
	    if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
	    if (cell.getCellType() == CellType.STRING) {
	        try {
	            return Double.parseDouble(cell.getStringCellValue());
	        } catch (NumberFormatException e) {
	            return 0;
	        }
	    }
	    return 0;
	}


	
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addGroup(@RequestBody EnergyMeterRegisterGroup meterRegisterGroup) {
		Status status= new Status();
		try {
		
			EnergyMeterRegisterGroup consumptionPriceSlab2=energyMeterRegisterGroupRepo.save(meterRegisterGroup);
			for(EnergyMeterRegisterGroupItems meterRegisterGroupItems:meterRegisterGroup.getItems()) {
				meterRegisterGroupItems.setGroup(consumptionPriceSlab2);
				energyMeterRegisterGroupItemsRepo.save(meterRegisterGroupItems);
			}
			status.setCode(200);
			status.setMessage("Group is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	

	@PutMapping("/updateGroup/{id}")
	public @ResponseBody Status updateGroup(
	        @PathVariable int id,
	        @RequestBody EnergyMeterRegisterGroup updatedGroup) {
	    
	    Status status = new Status();
	    try {
	        Optional<EnergyMeterRegisterGroup> existingOpt = energyMeterRegisterGroupRepo.findById(id);

	        if (existingOpt.isPresent()) {
	            EnergyMeterRegisterGroup existing = existingOpt.get();

	            // ✅ Update main fields
	            existing.setGroupName(updatedGroup.getGroupName());
	            existing.setNoOfRegister(updatedGroup.getNoOfRegister());
	            existing.setModule(updatedGroup.getModule());

	            // ✅ Save main group
	            EnergyMeterRegisterGroup savedGroup = energyMeterRegisterGroupRepo.save(existing);

	            // ✅ If items exist, save/update them (without deleting existing)
	            if (updatedGroup.getItems() != null && !updatedGroup.getItems().isEmpty()) {
	                for (EnergyMeterRegisterGroupItems item : updatedGroup.getItems()) {
	                    item.setGroup(savedGroup);
	                    energyMeterRegisterGroupItemsRepo.save(item);
	                }
	            }

	            status.setCode(200);
	            status.setMessage("Group updated successfully (without deleting old items)");
	            return status;
	        } else {
	            status.setCode(404);
	            status.setMessage("Group not found");
	            return status;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}
	
	@DeleteMapping("/deleteGroupItem/{itemId}")
	public @ResponseBody Status deleteGroupItem(@PathVariable int itemId) {
	    Status status = new Status();
	    try {
	        Optional<EnergyMeterRegisterGroupItems> itemOpt = energyMeterRegisterGroupItemsRepo.findById(itemId);

	        if (itemOpt.isPresent()) {
	            energyMeterRegisterGroupItemsRepo.delete(itemOpt.get());
	            status.setCode(200);
	            status.setMessage("Group item deleted successfully");
	        } else {
	            status.setCode(404);
	            status.setMessage("Group item not found");
	        }

	        return status;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	

	@DeleteMapping("/deleteGroup/{id}")
	public @ResponseBody Status deleteGroup(@PathVariable int id) {
	    Status status = new Status();
	    try {
	        Optional<EnergyMeterRegisterGroup> groupOpt = energyMeterRegisterGroupRepo.findById(id);

	        if (groupOpt.isPresent()) {
	            EnergyMeterRegisterGroup group = groupOpt.get();
	            // Delete child items first
	            energyMeterRegisterGroupItemsRepo.deleteAllByGroup(group);
	            // Delete group itself
	            energyMeterRegisterGroupRepo.delete(group);

	            status.setCode(200);
	            status.setMessage("Group deleted successfully");
	        } else {
	            status.setCode(404);
	            status.setMessage("Group not found");
	        }

	        return status;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	
	
//old api	
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteGroup(@RequestBody EnergyMeterRegisterGroup meterRegisterGroup) {
		Status status= new Status();
		try {
			List<EnergyMeterRegisterGroupItems> groupItems=energyMeterRegisterGroupItemsRepo.getEnergyMeterRegisterGroupItemsByGroup(meterRegisterGroup.getId());
			
			//for(PriceSlabRangeDetails details:priceSlabRangeDetails) {
			energyMeterRegisterGroupItemsRepo.deleteAll(groupItems);
			energyMeterRegisterGroupRepo.delete(meterRegisterGroup);
			status.setCode(200);
			status.setMessage("Group is Deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/getDeleteGroupItem", method = RequestMethod.GET)
	public @ResponseBody
	Status getDeleteGroupItem(@RequestParam("id") int id) {
		Status status= new Status();
		try {
			System.out.println("ID "+id);
			Optional<EnergyMeterRegisterGroupItems> optional=energyMeterRegisterGroupItemsRepo.findById(id);
			if(optional.isPresent()) {	
				energyMeterRegisterGroupItemsRepo.delete(optional.get());
			}
	
			
			//for(PriceSlabRangeDetails details:priceSlabRangeDetails) {
		
			status.setCode(200);
			status.setMessage("Group Item is Deleted successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@RequestMapping(value = "/getEnergyMeterRegisterGroupByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<EnergyMeterRegisterGroup> list = new ArrayList<EnergyMeterRegisterGroup>();
		try {
			list = energyMeterRegisterGroupRepo.getEnergyMeterRegisterGroupByLimitAndSearch(searchText, pageNo, perPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllEnergyMeterRegisterGroup", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterRegisterGroup> getAllEnergyMeterRegisterGroup() {
		List<EnergyMeterRegisterGroup> list = new ArrayList<EnergyMeterRegisterGroup>();
		try {
			list = energyMeterRegisterGroupRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getAllEnergyMeterRegisterGroupItemsByGroup", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterRegisterGroupItems> getAllEnergyMeterRegisterGroupItemsByGroup(@RequestParam("groupId") int groupId) {
		List<EnergyMeterRegisterGroupItems> list = new ArrayList<EnergyMeterRegisterGroupItems>();
		try {
			list = energyMeterRegisterGroupItemsRepo.getEnergyMeterRegisterGroupItemsByGroup(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@RequestMapping(value = "/getEnergyMeterRegisterGroupCountByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getEnergyMeterRegisterGroupCountByLimitAndSearch(@RequestParam("searchText") String searchText) {
		int count = 0 ;
		try {
			count = energyMeterRegisterGroupRepo.getEnergyMeterRegisterGroupCountByLimitAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getEnergyMeterRegisterGroupByLimit", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<EnergyMeterRegisterGroup> list = new ArrayList<EnergyMeterRegisterGroup>();
		try {
			list = energyMeterRegisterGroupRepo.getEnergyMeterRegisterGroupByLimit(pageNo,perPage);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCountOfEnergyMeterRegisterGroup", method = RequestMethod.GET)
	public @ResponseBody int getCountOfEnergyMeterRegisterGroup() {
		int count = 0;
		try {
				count =(int) energyMeterRegisterGroupRepo.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getEnergyMeterRegisterGroupItemByGroup", method = RequestMethod.GET)
	public @ResponseBody List<EnergyMeterRegisterGroupItems> getEnergyMeterRegisterGroupItemByGroup(@RequestParam("id") int id) {
		List<EnergyMeterRegisterGroupItems> list = new ArrayList<EnergyMeterRegisterGroupItems>();
		try {
			list = energyMeterRegisterGroupItemsRepo.getEnergyMeterRegisterGroupItemsByGroup(id);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllRegisterCoversionUnit", method = RequestMethod.GET)
	public @ResponseBody List<RegisterCoversionUnit> getAllRegisterCoversionUnit() {
		List<RegisterCoversionUnit> list = new ArrayList<RegisterCoversionUnit>();
		try {
			list = registerCoversionUnitRepo.findAll();
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllRegisterType", method = RequestMethod.GET)
	public @ResponseBody List<RegisterType> getAllRegisterType() {
		List<RegisterType> list = new ArrayList<RegisterType>();
		try {
			list = registerTypeRepo.findAll();
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getEnergyMeterById", method = RequestMethod.GET)
	public @ResponseBody EnergyMeterMaster getEnergyMeterById(@RequestParam("engergyMeterId") int engergyMeterId) {
		EnergyMeterMaster energyMeterMaster = new EnergyMeterMaster();
		try {
			Optional<EnergyMeterMaster>	 optional=energyMeterMasterRepo.findById(engergyMeterId);
			if(optional.isPresent()) {
				energyMeterMaster=optional.get();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return energyMeterMaster;
	}
}
