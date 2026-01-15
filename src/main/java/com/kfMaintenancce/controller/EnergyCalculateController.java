package com.kfMaintenancce.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.ConsupmtionDashboardDTO;
import com.kfMaintenancce.dto.DashbordDataDTO;
import com.kfMaintenancce.dto.DropGraphDTO;
import com.kfMaintenancce.dto.StartEndDateResponce;
import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.CurrentDropGraphData;
import com.kfMaintenancce.model.DailyConsumptionLog;
import com.kfMaintenancce.model.DashboardCardData;
import com.kfMaintenancce.model.DayWiseConsumption;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.MonthlyConsumptionGraph;
import com.kfMaintenancce.model.OverviewDahboardCardCount;
import com.kfMaintenancce.model.PowerFactorGraphData;
import com.kfMaintenancce.model.VoltageDropGraphData;
import com.kfMaintenancce.model.WeeklyConsumptionGraph;
import com.kfMaintenancce.repo.ConsumptionDetailsRepo;
import com.kfMaintenancce.repo.CurrentDropGraphDataRepo;
import com.kfMaintenancce.repo.DailyConsumptionLogRepo;
import com.kfMaintenancce.repo.DashboardCardDataRepo;
import com.kfMaintenancce.repo.DayWiseConsumptionRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.MonthlyConsumptionGraphRepo;
import com.kfMaintenancce.repo.PowerFactorGraphDataRepo;
import com.kfMaintenancce.repo.RegisterCoversionUnitRepo;
import com.kfMaintenancce.repo.VoltageDropGraphDataRepo;
import com.kfMaintenancce.repo.WeeklyConsumptionGraphRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/engergy_calcualte")
public class EnergyCalculateController {

	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	@Autowired
	DailyConsumptionLogRepo dailyConsumptionLogRepo;
	@Autowired
	DayWiseConsumptionRepo dayWiseConsumptionRepo;
	@Autowired
	EnergyMeterMasterRepo energyMeterMasterRepo;
	@Autowired
	RegisterCoversionUnitRepo registerCoversionUnitRepo;
	@Autowired
	ConsumptionDetailsRepo consumptionDetailsRepo;

	@Autowired
	DashboardCardDataRepo dashboardCardDataRepo;

	@Autowired
	MonthlyConsumptionGraphRepo monthlyConsumptionGraphRepo;
	
	@Autowired
	WeeklyConsumptionGraphRepo weeklyConsumptionGraphRepo;
	
	@Autowired
	CurrentDropGraphDataRepo currentDropGraphDataRepo;
	@Autowired
	VoltageDropGraphDataRepo voltageDropGraphDataRepo;
	@Autowired
	PowerFactorGraphDataRepo powerFactorGraphDataRepo;
	

	
	
	
	
	
	
	
	
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/updateOverviewDahboardCardCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateOverviewDahboardCardCount() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			Date currentDate = new Date();
			 // Create a Calendar instance and set it to the current date
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentDate);

	        // Subtract 2 days
	        calendar.add(Calendar.DATE, -1);

	        // Get the updated date
	        Date daysBefore = calendar.getTime();
	        
	        LocalDate currentDate1 = LocalDate.now();
	        
	        // Extract the month and year
	        int year = currentDate1.getYear();
	       int month=currentDate1.getMonthValue()-1;
	       if(month==1) {
	    	   year=year-1;
	       }
	       
	       System.out.println("daysBefore  "+daysBefore);
	       System.out.println("year  "+year);
	       System.out.println("month  "+month);
			OverviewDahboardCardCount overviewDahboardCardCount= new OverviewDahboardCardCount();
			int count=(int) energyMeterMasterRepo.count();
			double lastDayConsumption=dayWiseConsumptionRepo.getTotalConsumptionByDate("403204",daysBefore);
			String lastMonthConsumption=dayWiseConsumptionRepo.getSumConsumptionByRegisterYearAndMonth("403204",month,year);
			String totoalConsumption=dayWiseConsumptionRepo.getSumConsumption("403204");
			
			 System.out.println("NO Of Device   "+count);
		       System.out.println("lastDayConsumption  "+lastDayConsumption);
		         System.out.println("lastMonthConsumption  "+lastMonthConsumption);
		         System.out.println("totoalConsumption  "+totoalConsumption);
	      
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	
	
	
	
	
	
	
	
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/updateDailyLogTable", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateDailyLogTable() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			Date currentDate = new Date();

	        // Create a Calendar instance and set it to the current date
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentDate);

	        // Subtract 2 days
	        calendar.add(Calendar.DATE, -2);

	        // Get the updated date
	        Date twoDaysBefore = calendar.getTime();
	        Pageable top1000 = PageRequest.of(0, 10000); // Page 0, size 1000

			List<DailyConsumptionLog> list=dailyConsumptionLogRepo.getConsumptionByDate(twoDaysBefore,top1000);
			System.out.println("LIST SIZE "+list.size());
			dailyConsumptionLogRepo.deleteAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	
	
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/getPowerFactorWeeklyTrendsGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getPowerFactorWeeklyTrendsGraphData() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
		
			List<String> volatagellAVG = new ArrayList<String>();
			List<String> volatagelnAVG = new ArrayList<String>();
			List<String> label = new ArrayList<String>();
			List<String> series = new ArrayList<String>();

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();
			
			for(EnergyMeterMaster energyMeterMaster:list) {  
				Optional<EnergyMeterRegister> optional = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("442340", energyMeterMaster.getModule().getId());
				
				List<PowerFactorGraphData> datas= powerFactorGraphDataRepo.findbyMeterId(energyMeterMaster.getId());
				
				System.out.println(" getPowerFactorWeeklyTrendsGraphData   -------------------------------------------------."+datas.size());

				
				if(datas.size()==0) {
					
					
					for(int i=1;i<=6;i++) 
					{
						PowerFactorGraphData currentDropGraphData= new PowerFactorGraphData();
						if(i==1) {
							currentDropGraphData.setDataNo(0);
						}
						if(i==2) {
							currentDropGraphData.setDataNo(30);
						}
						if(i==3) {
							currentDropGraphData.setDataNo(60);
						}
						if(i==4) {
							currentDropGraphData.setDataNo(90);
						}
						if(i==5) {
							currentDropGraphData.setDataNo(120);
						}
						if(i==6) {
							currentDropGraphData.setDataNo(150);
						}
						currentDropGraphData.setValue("0");
						currentDropGraphData.setMeterId(energyMeterMaster.getId());
						System.out.println("DDD "+i);
						powerFactorGraphDataRepo.save(currentDropGraphData);
					}
				}else {
					for(PowerFactorGraphData  currentDropGraphData:datas ) {
						
						StartEndDateResponce currentTime = getStartEndTimeByMinuteDiff(currentDropGraphData.getDataNo());
						List<DailyConsumptionLog> currentList = dailyConsumptionLogRepo.getValueByDateAndTime(optional.get().getId(), currentTime.getStartDate(), currentTime.getEndDate());
						System.out.println("currentList     "+currentList.size());
						if (currentList.size() == 0) {
							currentDropGraphData.setValue("");
						}else {
							String maxValue = dailyConsumptionLogRepo.getMaxValueByDateAndTime(optional.get().getId(),
									currentTime.getStartDate(), currentTime.getEndDate());
							if (maxValue.equalsIgnoreCase("nan") || maxValue.equalsIgnoreCase("-")) {
								currentDropGraphData.setValue("");
							} else {
								currentDropGraphData.setValue(maxValue);
							}
						}
						currentDropGraphData.setTime_hrmin(currentTime.getCurrentTime());
						powerFactorGraphDataRepo.save(currentDropGraphData);
					}
				}
				
				
			}
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@Scheduled(fixedRate = 3600000)
	 @Scheduled(fixedRate = 3600000)
	@RequestMapping(value = "/getEnergyCalculate", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getEnergyCalculate() {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			System.out.println("DDDDDD...............  .---------------------------------------------------------------------.");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date yesterday = calendar.getTime();

			List<EnergyMeterMaster> masters = energyMeterMasterRepo.findAll();

			for (EnergyMeterMaster energyMeterMaster : masters) {
				String registerModuleNo = "403204";

				Optional<EnergyMeterRegister> optional = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId(registerModuleNo,
								energyMeterMaster.getModule().getId());
				List<DailyConsumptionLog> dailyLogYes = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(
						energyMeterMaster.getId(), registerModuleNo, yesterday);
				List<DailyConsumptionLog> dailyLogTod = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndModule(
						energyMeterMaster.getId(), registerModuleNo, new Date());
				double consume = 0;
				if (dailyLogYes.size() != 0 && dailyLogTod.size() != 0) {
					double yesterdayValue = Double.valueOf(dailyLogYes.get(0).getRegisterValue());
					double todayValue = 0;
					;
					if (!dailyLogTod.get(0).getRegisterValue().equalsIgnoreCase("-")) {
						todayValue = Double.valueOf(dailyLogTod.get(0).getRegisterValue());
					}
					consume = todayValue - yesterdayValue;
				} else {
					if (dailyLogTod.size() != 0) {
						double todayValue = Double.valueOf(dailyLogTod.get(0).getRegisterValue());
						consume = todayValue;
					}

				}

				System.out.println("METER ID" + energyMeterMaster.getId());
				System.out.println("METER ID 1 " + optional.isPresent());
				System.out.println("Date S " + new Date());
				int count = dayWiseConsumptionRepo.getCountConsumptionByMeterIDAndDateAndModule(
						energyMeterMaster.getId(), optional.get().getId(), new Date());

				if (count == 0) {
					DayWiseConsumption consumption = new DayWiseConsumption();
					consumption.setConsumptionDate(new Date());
					consumption.setEnergyMeterMaster(energyMeterMaster);
					consumption.setEnergyMeterRegister(optional.get());
					consumption.setRegisterValue(consume);
					dayWiseConsumptionRepo.save(consumption);
				} else {
					Optional<DayWiseConsumption> optional2 = dayWiseConsumptionRepo
							.getConsumptionByMeterIDAndDateAndModule(energyMeterMaster.getId(), optional.get().getId(),
									new Date());

					DayWiseConsumption consumption = optional2.get();
					consumption.setRegisterValue(consume);
					dayWiseConsumptionRepo.save(consumption);
				}

				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumptionDTOs;
	}
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/getVoltageDropGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void getVoltageDropGraphData() {
	
		try {

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();
			System.out.println("getCurrentDropGraphData-------------------------------------------------."+list.size());

			for(EnergyMeterMaster energyMeterMaster:list) {  
				Optional<EnergyMeterRegister> optional = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403010", energyMeterMaster.getModule().getId());
				
				List<VoltageDropGraphData> datas= voltageDropGraphDataRepo.findbyMeterId(energyMeterMaster.getId());
				

				
				if(datas.size()==0) {
					for(int i=1;i<=6;i++) 
					{
						VoltageDropGraphData currentDropGraphData= new VoltageDropGraphData();
						if(i==1) {
							currentDropGraphData.setDataNo(0);
						}
						if(i==2) {
							currentDropGraphData.setDataNo(30);
						}
						if(i==3) {
							currentDropGraphData.setDataNo(60);
						}
						if(i==4) {
							currentDropGraphData.setDataNo(90);
						}
						if(i==5) {
							currentDropGraphData.setDataNo(120);
						}
						if(i==6) {
							currentDropGraphData.setDataNo(150);
						}
						currentDropGraphData.setMeterId(energyMeterMaster.getId());
						voltageDropGraphDataRepo.save(currentDropGraphData);
					}
				}else {
					for(VoltageDropGraphData  voltageDropGraphData:datas ) {
					
						Optional<EnergyMeterRegister> optionalRe1 = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403026", energyMeterMaster.getModule().getId());
						Optional<EnergyMeterRegister> optionalRe2 = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403036", energyMeterMaster.getModule().getId());
						StartEndDateResponce currentTime = getStartEndTimeByMinuteDiff(voltageDropGraphData.getDataNo());
						
						
						String maxValue1 = dailyConsumptionLogRepo.getMaxValueByMeeterRegDateAndTime(optionalRe1.get().getId(),energyMeterMaster.getId(),currentTime.getStartDate(), currentTime.getEndDate());
						String maxValue2 = dailyConsumptionLogRepo.getMaxValueByMeeterRegDateAndTime(optionalRe2.get().getId(),energyMeterMaster.getId(),currentTime.getStartDate(), currentTime.getEndDate());
						if (maxValue1.equalsIgnoreCase("nan") || maxValue1.equalsIgnoreCase("-")||maxValue1.equalsIgnoreCase("null")||maxValue1.equalsIgnoreCase(null)) {

							voltageDropGraphData.setValue1("");
						} else {
							voltageDropGraphData.setValue1(maxValue1);
						}
						
						if (maxValue2.equalsIgnoreCase("nan") || maxValue2.equalsIgnoreCase("-")) {

							voltageDropGraphData.setValue2("");
						} else {
							voltageDropGraphData.setValue2(maxValue2);
						}
						voltageDropGraphData.setTime_hrmin(currentTime.getCurrentTime());
						voltageDropGraphDataRepo.save(voltageDropGraphData);
					}
				}
				
				
			}
			
			
			
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Scheduled(fixedRate =1800000)
	@RequestMapping(value = "/getDashbordData", method = RequestMethod.GET)
	public @ResponseBody DashbordDataDTO getDashbordData() {
		DashbordDataDTO dashbordDataDTO = new DashbordDataDTO();
		try {
	

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();

			for (EnergyMeterMaster energyMeterMaster : list) {
				int meterId = energyMeterMaster.getId();
				double dailyEnergyConsumption = 0;
				String dailyEnergyApparent = "0";
				String avarageVoltage;
				String avarageCurrent;
				String monthlyEnergyConsumption = "0";
				String monthlyEnergyApparent = "0";
				Date date = new Date();
				String registerModuleNo = "403204";
				String apparentRegisterNo = "403076";
				Optional<DayWiseConsumption> energyConsumption = dayWiseConsumptionRepo
						.getConssumptionByModuleNoAndDate(registerModuleNo, date);
				String apparent = consumptionDetailsRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId,
						apparentRegisterNo);
				// String
				// apparent=dailyConsumptionLogRepo.getConssumptionByModuleNoAndDate(apparentRegisterNo,date);

				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
System.out.println("Month "+month);
System.out.println("year "+year);
System.out.println("registerModuleNo "+registerModuleNo);
				// String
				// energyConsumptionM=dayWiseConsumptionRepo.getConssumptionByModuleNoAndMonth(registerModuleNo,meterId,month,year);
				/*
				 * String energyConsumptionM = consumptionDetailsRepo
				 * .getSumConsumptionByMeterYearAndMonth(registerModuleNo, meterId, month,
				 * year);
				 */		
				String energyConsumptionM=dayWiseConsumptionRepo.getSumConsumptionByRegisterYearAndMonth(registerModuleNo,month,year);

			System.out.println("MONTHLY ENGER COMSUMPTION ................................................................................................................."+energyConsumptionM);
				// String
				// apparentM=consumptionDetailsRepo.getConsumptionByMeterYearAndMonth(apparentRegisterNo,meterId,month,year);

				monthlyEnergyConsumption = energyConsumptionM;
				// monthlyEnergyApparent=apparentM;
				if (energyConsumption.isPresent()) {
					dailyEnergyConsumption = energyConsumption.get().getRegisterValue();
				}


				dailyEnergyApparent = apparent;

				Optional<EnergyMeterMaster> optionalMeter = energyMeterMasterRepo.findById(meterId);

				avarageCurrent = consumptionDetailsRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId, "403010");

				avarageVoltage = dailyConsumptionLogRepo.getMaxValueByDateMeterAndModuleRegisterNo(meterId, "403036",
						new Date());

				if (avarageCurrent == null) {
					dashbordDataDTO.setAvarageCurrent("0");
				} else {
					dashbordDataDTO.setAvarageCurrent(avarageCurrent);
				}
				if (avarageVoltage == null) {
					dashbordDataDTO.setAvarageVoltage("0");
				} else {
					dashbordDataDTO.setAvarageVoltage(avarageVoltage);
				}
				int count = (int) dashboardCardDataRepo.count();
				Optional<DashboardCardData> optional = dashboardCardDataRepo.getByMeterId(meterId);
				if (optional.isPresent()) {

					DashboardCardData cardData = optional.get();
					cardData.setAvarageCurrent(avarageCurrent);
					cardData.setAvarageVoltage(avarageVoltage);
					cardData.setDailyEnergyApparent(dailyEnergyApparent);
					cardData.setDailyEnergyConsumption(dailyEnergyConsumption);
					cardData.setMonthlyEnergyApparent(monthlyEnergyApparent);
					cardData.setMonthlyEnergyConsumption(monthlyEnergyConsumption);
					cardData.setMeterId(meterId);
					dashboardCardDataRepo.save(cardData);
				} else {

					DashboardCardData cardData = new DashboardCardData();
					cardData.setAvarageCurrent(avarageCurrent);
					cardData.setAvarageVoltage(avarageVoltage);
					cardData.setDailyEnergyApparent(dailyEnergyApparent);
					cardData.setDailyEnergyConsumption(dailyEnergyConsumption);
					cardData.setMonthlyEnergyApparent(monthlyEnergyApparent);
					cardData.setMonthlyEnergyConsumption(monthlyEnergyConsumption);
					cardData.setMeterId(meterId);
					dashboardCardDataRepo.save(cardData);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashbordDataDTO;
	}
	@Scheduled(fixedRate = 3600000)
	@GetMapping(value = "/getMonthlyConsumptionGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getMonthlyConsumptionGraphData() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

			System.out.println("Current Month " + month);
			List<String> labels = new ArrayList<String>();
			List<String> series = new ArrayList<String>();
			List<String> data = new ArrayList<String>();

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();

			for (EnergyMeterMaster energyMeterMaster : list) {

				List<MonthlyConsumptionGraph> graphs = monthlyConsumptionGraphRepo
						.findByMeterIdAndYear(energyMeterMaster.getId(), currentYear);
				if (graphs.size() == 0) {
					for (int i = 1; i <= 12; i++) {
						MonthlyConsumptionGraph monthlyConsumptionGraph = new MonthlyConsumptionGraph();
						monthlyConsumptionGraph.setMeterId(energyMeterMaster.getId());
						monthlyConsumptionGraph.setMonth(i);
						monthlyConsumptionGraph.setYear(currentYear);

						String monthName = "";
						if (i == 1) {
							monthName = "JAN-" + currentYear;
						}
						if (i == 2) {
							monthName = "FEB-" + currentYear;
						}

						if (i == 3) {
							monthName = "MAR-" + currentYear;
						}
						if (i == 4) {
							monthName = "APR-" + currentYear;
						}

						if (i == 5) {
							monthName = "MAY-" + currentYear;
						}
						if (i == 6) {
							monthName = "JUN-" + currentYear;
						}
						if (i == 7) {
							monthName = "JUL-" + currentYear;
						}
						if (i == 8) {
							monthName = "AUG-" + currentYear;
						}
						if (i == 9) {
							monthName = "SEP-" + currentYear;
						}
						if (i == 10) {
							monthName = "OCT-" + currentYear;
						}
						if (i == 11) {
							monthName = "NOV-" + currentYear;
						}
						if (i == 12) {
							monthName = "DEC-" + currentYear;
						}

						monthlyConsumptionGraph.setConsumptionValue("0");

						monthlyConsumptionGraph.setMonthName(monthName);
						monthlyConsumptionGraphRepo.save(monthlyConsumptionGraph);
					}
				}

				MonthlyConsumptionGraph monthlyConsumptionGraph;

				Optional<MonthlyConsumptionGraph> optional = monthlyConsumptionGraphRepo
						.findByMeterIdMonthAndYear(energyMeterMaster.getId(), month, currentYear);

				if (optional.isPresent()) {
					monthlyConsumptionGraph = optional.get();
				} else {
					monthlyConsumptionGraph = new MonthlyConsumptionGraph();

				}
				String consumption = dayWiseConsumptionRepo
						.getSumConsumptionByMeterYearAndMonth(energyMeterMaster.getId(), month, currentYear);

				monthlyConsumptionGraph.setConsumptionValue(consumption);
				monthlyConsumptionGraphRepo.save(monthlyConsumptionGraph);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	@Scheduled(fixedRate = 3600000)
	@GetMapping(value = "/getDayWiseConsumptionGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getDayWiseConsumptionGraphData() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			
			Date currentDate = new Date();
			List<String> labels= new ArrayList<String>();
			List<String> series= new ArrayList<String>();
			List<String> data= new ArrayList<String>();
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(currentDate);
			calendar1.add(Calendar.DAY_OF_MONTH, -1);
			Date day1 = calendar1.getTime();

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(currentDate);
			calendar2.add(Calendar.DAY_OF_MONTH, -2);
			Date day2 = calendar2.getTime();

			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(currentDate);
			calendar3.add(Calendar.DAY_OF_MONTH, -3);
			Date day3 = calendar3.getTime();

			Calendar calendar4 = Calendar.getInstance();
			calendar4.setTime(currentDate);
			calendar4.add(Calendar.DAY_OF_MONTH, -4);
			Date day4 = calendar4.getTime();

			Calendar calendar5 = Calendar.getInstance();
			calendar5.setTime(currentDate);
			calendar5.add(Calendar.DAY_OF_MONTH, -5);
			Date day5 = calendar5.getTime();

			Calendar calendar6 = Calendar.getInstance();
			calendar6.setTime(currentDate);
			calendar6.add(Calendar.DAY_OF_MONTH, -6);
			Date day6 = calendar6.getTime();

			Calendar calendar7 = Calendar.getInstance();
			calendar7.setTime(currentDate);
			calendar7.add(Calendar.DAY_OF_MONTH, -7);
			Date day7 = calendar7.getTime();
			
			
			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();
			
			
			for(EnergyMeterMaster energyMeterMaster:list) {
				int meterId=energyMeterMaster.getId();
				Optional<EnergyMeterRegister> optionalReg = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403204", energyMeterMaster.getModule().getId());
				
				Optional<DayWiseConsumption> consumption0=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), currentDate);
				Optional<DayWiseConsumption> consumption1=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day1);
				Optional<DayWiseConsumption> consumption2=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day2);
				Optional<DayWiseConsumption> consumption3=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day3);
				Optional<DayWiseConsumption> consumption4=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day4);
				Optional<DayWiseConsumption> consumption5=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day5);
				Optional<DayWiseConsumption> consumption6=dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndModule(meterId, optionalReg.get().getId(), day6);
				
				Optional<WeeklyConsumptionGraph> weeklyConsumptionGraphs=weeklyConsumptionGraphRepo.getByMeterId(meterId);
				
				System.out.println("WEEKLY "+weeklyConsumptionGraphs.isPresent() );
				if (weeklyConsumptionGraphs.isPresent()) {
					WeeklyConsumptionGraph consumptionGraph=weeklyConsumptionGraphs.get();
					if(consumption0.isPresent()) {
						consumptionGraph.setTodayConsumption(String.valueOf(consumption0.get().getRegisterValue()));
						consumptionGraph.setTodayDate(dateFormat.format(currentDate));
					}else {
						consumptionGraph.setTodayConsumption("0");
						consumptionGraph.setTodayDate(dateFormat.format(currentDate));
					}
					
					if(consumption1.isPresent()) {
						consumptionGraph.setDay1Consumption(String.valueOf(consumption1.get().getRegisterValue()));
						consumptionGraph.setDay1Date(dateFormat.format(day1));
					}
					else {
						consumptionGraph.setDay1Consumption("0");
						consumptionGraph.setDay1Date(dateFormat.format(day1));
					}
					if(consumption2.isPresent()) {
						consumptionGraph.setDay2Consumption(String.valueOf(consumption2.get().getRegisterValue()));
						consumptionGraph.setDay2Date(dateFormat.format(day2));
					}else {
						consumptionGraph.setDay2Consumption("0");
						consumptionGraph.setDay2Date(dateFormat.format(day2));
					}
					
					if(consumption3.isPresent()) {
						consumptionGraph.setDay3Consumption(String.valueOf(consumption3.get().getRegisterValue()));
						consumptionGraph.setDay3Date(dateFormat.format(day3));
					}else {
						consumptionGraph.setDay3Consumption("0");
						consumptionGraph.setDay3Date(dateFormat.format(day3));
					}
					
					if(consumption4.isPresent()) {
						consumptionGraph.setDay4Consumption(String.valueOf(consumption4.get().getRegisterValue()));
						consumptionGraph.setDay4Date(dateFormat.format(day4));
					}else {
						consumptionGraph.setDay4Consumption("0");
						consumptionGraph.setDay4Date(dateFormat.format(day4));
					}
					
					
					if(consumption5.isPresent()) {
						consumptionGraph.setDay5Consumption(String.valueOf(consumption5.get().getRegisterValue()));
						consumptionGraph.setDay5Date(dateFormat.format(day5));
					}else {
						consumptionGraph.setDay5Consumption("0");
						consumptionGraph.setDay5Date(dateFormat.format(day5));
					}
					
					if(consumption6.isPresent()) {
						consumptionGraph.setDay6Consumption(String.valueOf(consumption6.get().getRegisterValue()));
						consumptionGraph.setDay6Date(dateFormat.format(day6));
					}else {
						consumptionGraph.setDay6Consumption("0");
						consumptionGraph.setDay6Date(dateFormat.format(day6));
					}
					System.out.println("Connsumption "+consumptionGraph.toString());
					weeklyConsumptionGraphRepo.save(consumptionGraph);

				}else {
					WeeklyConsumptionGraph consumptionGraph=new WeeklyConsumptionGraph();
					if(consumption0.isPresent()) {
						consumptionGraph.setTodayConsumption(String.valueOf(consumption0.get().getRegisterValue()));
						consumptionGraph.setTodayDate(dateFormat.format(currentDate));
					}else {
						consumptionGraph.setTodayConsumption("0");
						consumptionGraph.setTodayDate(dateFormat.format(currentDate));
					}
					
					if(consumption1.isPresent()) {
						consumptionGraph.setDay1Consumption(String.valueOf(consumption1.get().getRegisterValue()));
						consumptionGraph.setDay1Date(dateFormat.format(day1));
					}else {
						consumptionGraph.setDay1Consumption("0");
						consumptionGraph.setDay1Date(dateFormat.format(day1));
					}
					
					if(consumption2.isPresent()) {
						consumptionGraph.setDay2Consumption(String.valueOf(consumption2.get().getRegisterValue()));
						consumptionGraph.setDay2Date(dateFormat.format(day2));
					}else {
						consumptionGraph.setDay2Consumption("0");
						consumptionGraph.setDay2Date(dateFormat.format(day2));
					}
					
					if(consumption3.isPresent()) {
						consumptionGraph.setDay3Consumption(String.valueOf(consumption3.get().getRegisterValue()));
						consumptionGraph.setDay3Date(dateFormat.format(day3));
					}else {
						consumptionGraph.setDay3Consumption("0");
						consumptionGraph.setDay3Date(dateFormat.format(day3));
					}
					
					if(consumption4.isPresent()) {
						consumptionGraph.setDay4Consumption(String.valueOf(consumption4.get().getRegisterValue()));
						consumptionGraph.setDay4Date(dateFormat.format(day4));
					}
					else {
						consumptionGraph.setDay4Consumption("0");
						consumptionGraph.setDay4Date(dateFormat.format(day4));
					}
					
					if(consumption5.isPresent()) {
						consumptionGraph.setDay5Consumption(String.valueOf(consumption5.get().getRegisterValue()));
						consumptionGraph.setDay5Date(dateFormat.format(day5));
					}else {
						consumptionGraph.setDay5Consumption("0");
						consumptionGraph.setDay5Date(dateFormat.format(day5));
					}
					
					if(consumption6.isPresent()) {
						consumptionGraph.setDay6Consumption(String.valueOf(consumption6.get().getRegisterValue()));
						consumptionGraph.setDay6Date(dateFormat.format(day6));
					}else {
						consumptionGraph.setDay6Consumption("0");
						consumptionGraph.setDay6Date(dateFormat.format(day6));
					}
					System.out.println("Connsumption "+consumptionGraph.toString());
					consumptionGraph.setMeterId(meterId);
					weeklyConsumptionGraphRepo.save(consumptionGraph);
				}
				
			}
		
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	public @ResponseBody StartEndDateResponce getStartEndTimeByMinuteDiff(int minute) {
		StartEndDateResponce responce = new StartEndDateResponce();
		Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MINUTE, -minute);
		Date timeMinus = calendar.getTime();

		Calendar calendarE = Calendar.getInstance();
		calendarE.setTime(timeMinus);
		calendarE.add(Calendar.MINUTE, -10);
		Date startTime = calendarE.getTime();

		Calendar calendarS = Calendar.getInstance();
		calendarS.setTime(timeMinus);
		calendarS.add(Calendar.MINUTE, 10);
		Date endTime = calendarS.getTime();

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String strDateStr = dateFormat.format(startTime);
		String endStr = dateFormat.format(endTime);
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
		String currentTime = dateFormat2.format(timeMinus);

		responce.setStartDate(strDateStr);
		responce.setEndDate(endStr);
		responce.setCurrentTime(currentTime);
		return responce;
	}


}
