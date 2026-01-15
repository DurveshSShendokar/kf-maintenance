package com.kfMaintenancce.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.kfMaintenancce.dto.DropGraphDTO;
import com.kfMaintenancce.dto.StartEndDateResponce;
import com.kfMaintenancce.model.CurrentDropGraphData;
import com.kfMaintenancce.model.CurrentVoltageCard;
import com.kfMaintenancce.model.DailyConsumptionLog;
import com.kfMaintenancce.model.DayWiseConsumption;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.MeterWiseDashboardCardData;
import com.kfMaintenancce.model.OverviewDahboardCardCount;
import com.kfMaintenancce.model.OverviewDahboardPieChart;
import com.kfMaintenancce.model.OverviewDahboardUnitConsumption;
import com.kfMaintenancce.model.OverviewDahboardVoltageMinMax;
import com.kfMaintenancce.model.VoltageDropGraphData;
import com.kfMaintenancce.repo.ConsumptionDetailsRepo;
import com.kfMaintenancce.repo.CurrentDropGraphDataRepo;
import com.kfMaintenancce.repo.CurrentVoltageCardRepo;
import com.kfMaintenancce.repo.DailyConsumptionLogRepo;
import com.kfMaintenancce.repo.DashboardCardDataRepo;
import com.kfMaintenancce.repo.DayWiseConsumptionRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.MeterWiseDashboardCardDataRepo;
import com.kfMaintenancce.repo.MonthlyConsumptionGraphRepo;
import com.kfMaintenancce.repo.OverviewDahboardCardCountrrRepo;
import com.kfMaintenancce.repo.OverviewDahboardPieChartRepo;
import com.kfMaintenancce.repo.OverviewDahboardUnitConsumptionRepo;
import com.kfMaintenancce.repo.OverviewDahboardVoltageMinMaxRepo;
import com.kfMaintenancce.repo.PowerFactorGraphDataRepo;
import com.kfMaintenancce.repo.RegisterCoversionUnitRepo;
import com.kfMaintenancce.repo.VoltageDropGraphDataRepo;
import com.kfMaintenancce.repo.WeeklyConsumptionGraphRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/emsUtility")
public class EMSUtilityController {

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
	@Autowired
	OverviewDahboardCardCountrrRepo overviewDahboardCardCountrrRepo;
	@Autowired
	OverviewDahboardUnitConsumptionRepo overviewDahboardUnitConsumptionRepo;
	@Autowired
	OverviewDahboardPieChartRepo overviewDahboardPieChartRepo;

	@Autowired
	OverviewDahboardVoltageMinMaxRepo overviewDahboardVoltageMinMaxRepo;
	@Autowired
	CurrentVoltageCardRepo currentVoltageCardRepo;

	@Autowired
	MeterWiseDashboardCardDataRepo meterWiseDashboardCardDataRepo;
	
	
	
	@Scheduled(fixedRate = 18000000)
	@GetMapping(value = "/updateDailyLogTable", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateDailyLogTable() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			System.out.println("******************************************* Delting Daily Logs **************************************************************");
			Date currentDate = new Date();

			// Create a Calendar instance and set it to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			// Subtract 2 days
			calendar.add(Calendar.DATE, -1);

			// Get the updated date
			Date twoDaysBefore = calendar.getTime();
			Pageable top1000 = PageRequest.of(0, 10000); // Page 0, size 1000

			List<DailyConsumptionLog> list = dailyConsumptionLogRepo.getConsumptionByDate(twoDaysBefore, top1000);
			System.out.println("LIST SIZE " + list.size());
			dailyConsumptionLogRepo.deleteAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	@Scheduled(fixedRate = 300000)
	@RequestMapping(value = "/getEnergyCalculate", method = RequestMethod.GET)
	public @ResponseBody ConsupmtionDashboardDTO getEnergyCalculate() {
		ConsupmtionDashboardDTO consumptionDTOs = new ConsupmtionDashboardDTO();
		try {
			System.out.println("------------------------------Update DayWiseConsumption ---------------------------------------.");
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
					consumption.setConsumptionDate(new Date());
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
			int month = currentDate1.getMonthValue() - 1;
			if (month == 1) {
				year = year - 1;
			}

			int count = (int) energyMeterMasterRepo.count();

			BigDecimal bd = new BigDecimal(dayWiseConsumptionRepo.getTotalConsumptionByDate("403204", daysBefore))
					.setScale(2, RoundingMode.HALF_UP);
			String lastDayConsumption = bd.toString();

			BigDecimal bd1 = new BigDecimal(
					dayWiseConsumptionRepo.getSumConsumptionByRegisterYearAndMonth("403204", month, year))
					.setScale(2, RoundingMode.HALF_UP);
			String lastMonthConsumption = bd1.toString();

			//System.out.println("");
			
			
			BigDecimal bd2 = new BigDecimal(dayWiseConsumptionRepo.getSumConsumption("403204")).setScale(2,
					RoundingMode.HALF_UP);

			String totoalConsumption = bd2.toString();
			OverviewDahboardCardCount overviewDahboardCardCount = new OverviewDahboardCardCount();

			List<OverviewDahboardCardCount> list = overviewDahboardCardCountrrRepo.findAll();
			if (list.size() == 0) {
				overviewDahboardCardCount = new OverviewDahboardCardCount();
			} else {
				overviewDahboardCardCount = list.get(0);
			}
			overviewDahboardCardCount.setLastDayConsumption(lastDayConsumption);
			overviewDahboardCardCount.setLastMonthConsumption(lastMonthConsumption);
			overviewDahboardCardCount.setNoOfDevice(count);
			overviewDahboardCardCount.setTotalConsumption(totoalConsumption);
			overviewDahboardCardCountrrRepo.save(overviewDahboardCardCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/updateOverviewDahboardUnitConsumption", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateOverviewDahboardUnitConsumption() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			LocalDate currentDate = LocalDate.now();
			int year = currentDate.getYear();
			int month = currentDate.getMonthValue();
			YearMonth yearMonth = YearMonth.of(year, month);
			int daysInMonth = yearMonth.lengthOfMonth();

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year); // Specify the year
			calendar.set(Calendar.MONTH, month-1); // Specify the month

			// Set the day of the month to 1 (the first date of the month)
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			// Get the Date object representing the first date of the month
			Date firstDateOfMonth = calendar.getTime();

			for (int i = 0; i < daysInMonth; i++) {
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(firstDateOfMonth);

				// Subtract 2 days
				calendar1.add(Calendar.DATE, i);

				// Get the updated date
				Date monthdDayDate = calendar1.getTime();

				BigDecimal bd = new BigDecimal(
						dayWiseConsumptionRepo.getTotalConsumptionByDate("403204", monthdDayDate))
						.setScale(2, RoundingMode.HALF_UP);
				String consumption = bd.toString();
				int day = i + 1;

				System.out.println(day + ". " + monthdDayDate + "         " + consumption);

				Optional<OverviewDahboardUnitConsumption> optional = overviewDahboardUnitConsumptionRepo.getByDay(day);
				if (optional.isPresent()) {
					OverviewDahboardUnitConsumption unitConsumption = optional.get();
					unitConsumption.setConsumption(consumption);
					unitConsumption.setDate(monthdDayDate);
					unitConsumption.setDay(day);
					overviewDahboardUnitConsumptionRepo.save(unitConsumption);
				} else {
					OverviewDahboardUnitConsumption unitConsumption = new OverviewDahboardUnitConsumption();
					unitConsumption.setConsumption(consumption);
					unitConsumption.setDate(monthdDayDate);
					unitConsumption.setDay(day);
					overviewDahboardUnitConsumptionRepo.save(unitConsumption);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/updateOverviewDahboardPieChart", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateOverviewDahboardPieChart() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {

			int count = (int) energyMeterMasterRepo.count();
			List<Integer> activeMeters = dayWiseConsumptionRepo.getActiveMeterByDate(new Date());
			int active = activeMeters.size();
			int inactive=count-active;
			List<OverviewDahboardPieChart> charts = overviewDahboardPieChartRepo.findAll();
			if (charts.size() == 0) {
				OverviewDahboardPieChart chart = new OverviewDahboardPieChart();
				chart.setTotal(inactive);
				chart.setActive(active);
				overviewDahboardPieChartRepo.save(chart);
			} else {
				OverviewDahboardPieChart chart = charts.get(0);
				chart.setTotal(inactive);
				chart.setActive(active);
				overviewDahboardPieChartRepo.save(chart);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/updateOverviewDahboardVoltageMinMax", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO updateOverviewDahboardVoltageMinMax() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {

			LocalDate currentDate = LocalDate.now();
			int year = currentDate.getYear();
			int month = currentDate.getMonthValue();
			YearMonth yearMonth = YearMonth.of(year, month);
			int daysInMonth = yearMonth.lengthOfMonth();

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year); // Specify the year
			calendar.set(Calendar.MONTH, month-1); // Specify the month

			// Set the day of the month to 1 (the first date of the month)
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			// Get the Date object representing the first date of the month
			Date firstDateOfMonth = calendar.getTime();

			for (int i = 0; i < daysInMonth; i++) {
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(firstDateOfMonth);

				// Subtract 2 days
				calendar1.add(Calendar.DATE, i);

				// Get the updated date
				Date monthdDayDate = calendar1.getTime();

				BigDecimal bd = new BigDecimal(
						dayWiseConsumptionRepo.getTotalConsumptionByDate("403026", monthdDayDate))
						.setScale(2, RoundingMode.HALF_UP);
				String consumption = bd.toString();
				int day = i + 1;

				String registerNo = "403026";
				Optional<OverviewDahboardVoltageMinMax> optional = overviewDahboardVoltageMinMaxRepo.getByDay(day);

				List<DailyConsumptionLog> logs = dailyConsumptionLogRepo.getByDate(registerNo, monthdDayDate);

				System.out.println("Logs " + logs.size());
				if (optional.isPresent()) {
					OverviewDahboardVoltageMinMax unitConsumption = optional.get();
					String maxvalue = dailyConsumptionLogRepo.getMaxValueByDate(registerNo, monthdDayDate);
					String minvalue = dailyConsumptionLogRepo.getMinValueByDate(registerNo, monthdDayDate);
					if(maxvalue==null) {
						maxvalue="0";
					}
					if(minvalue==null) {
						minvalue="0";	
					}
					unitConsumption.setMaxValue(maxvalue);
					unitConsumption.setMinValue(minvalue);
					unitConsumption.setDate(monthdDayDate);
					unitConsumption.setDayName(day);
					System.out.println(day + ". " + monthdDayDate + "         " + maxvalue + "   " + minvalue);
					overviewDahboardVoltageMinMaxRepo.save(unitConsumption);
				} else {
					OverviewDahboardVoltageMinMax unitConsumption = new OverviewDahboardVoltageMinMax();
					String maxvalue = dailyConsumptionLogRepo.getMaxValueByDate(registerNo, monthdDayDate);
					String minvalue = dailyConsumptionLogRepo.getMinValueByDate(registerNo, monthdDayDate);
					if(maxvalue==null) {
						maxvalue="0";
					}
					if(minvalue==null) {
						minvalue="0";	
					}
					
					
					unitConsumption.setMaxValue(maxvalue);
					unitConsumption.setMinValue(minvalue);
					unitConsumption.setDate(monthdDayDate);
					unitConsumption.setDayName(day);
					System.out.println(day + ". " + monthdDayDate + "       Max  " + maxvalue + "   Min " + minvalue);
					overviewDahboardVoltageMinMaxRepo.save(unitConsumption);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	@Scheduled(fixedRate = 300000)
	@GetMapping(value = "/getCurrentVoltageCardData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CurrentVoltageCard getCurrentVoltageCardData() {
		try {

			List<EnergyMeterMaster> meterMasters = energyMeterMasterRepo.findAll();

			for (EnergyMeterMaster energyMeterMaster : meterMasters) {
				int meterId = energyMeterMaster.getId();

				Optional<EnergyMeterMaster> optionalMeter = energyMeterMasterRepo.findById(meterId);

				String currentR;
				String currentY;
				String currentB;
				String currentN;

				String voltageRY;
				String voltageYB;
				String voltageBR;
				String voltageRB;
				String voltageLN;
				String voltageLL;
				String voltageRN;
				String voltageYN = "";
				String voltageBN;
				

				Optional<EnergyMeterRegister> optionalCurrentR = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403000",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalCurrentY = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403002",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalCurrentB = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403004",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalCurrentN = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403006",
								optionalMeter.get().getModule().getId());

				Optional<EnergyMeterRegister> optionalVoltageRY = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403020",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageYB = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403022",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageBR = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403024",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageRB = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403026	",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageLN = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403036",
								optionalMeter.get().getModule().getId());
				
				
				
				
				
				Optional<EnergyMeterRegister> optionalVoltageLL = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403026",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageRN = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403028",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageYN = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403030",
								optionalMeter.get().getModule().getId());
				Optional<EnergyMeterRegister> optionalVoltageBN = energyMeterRegisterRepo
						.getEnergyMeterRegisterByRegisterNoandModuleId("403032",
								optionalMeter.get().getModule().getId());
				
				
				
				
				
				
				
				
				
				
				

				if (optionalCurrentR.isPresent()) {
					// currentA=dailyConsumptionLogRepo.getMaxValueByDateMeterAndModule(meterId,
					// optionalCurrentA.get().getId(), new Date());
					currentR = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalCurrentR.get().getId());

					if (currentR == null || currentR.equalsIgnoreCase("NAN Error")) {
						currentR = "12";
					}

				} else {
					currentR = "0";
				}
				if (optionalCurrentY.isPresent()) {
					currentY = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalCurrentY.get().getId());
					if (currentY == null || currentY.equalsIgnoreCase("NAN Error")) {
						currentY = "13";
					}
				} else {
					currentY = "0";
				}

				if (optionalCurrentB.isPresent()) {
					currentB = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalCurrentB.get().getId());
					if (currentB == null || currentB.equalsIgnoreCase("NAN Error")) {
						currentB = "0";
					}
				} else {
					currentB = "0";
				}

				if (optionalCurrentN.isPresent()) {
					currentN = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalCurrentN.get().getId());
					if (currentN == null || currentN.equalsIgnoreCase("NAN Error")) {
						currentN = "0";
					}
				} else {
					currentN = "0";
				}

				if (optionalVoltageRY.isPresent()) {
					voltageRY = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageRY.get().getId());
					if (voltageRY == null || voltageRY.equalsIgnoreCase("NAN Error")) {
						voltageRY = "0";
					}
				} else {
					voltageRY = "0";
				}

				if (optionalVoltageYB.isPresent()) {
					voltageYB = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageYB.get().getId());
					if (voltageYB == null || voltageYB.equalsIgnoreCase("NAN Error")) {
						voltageYB = "0";
					}
				} else {
					voltageYB = "0";
				}

				if (optionalVoltageBR.isPresent()) {
					voltageBR = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageBR.get().getId());
					if (voltageBR == null || voltageBR.equalsIgnoreCase("NAN Error")) {
						voltageBR = "0";
					}
				} else {
					voltageBR = "0";
				}

				if (optionalVoltageRB.isPresent()) {
					voltageRB = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageRB.get().getId());
					if (voltageRB == null || voltageRB.equalsIgnoreCase("NAN Error")) {
						voltageRB = "0";
					}
				} else {
					voltageRB = "0";
				}

				if (optionalVoltageLN.isPresent()) {
					voltageLN = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageLN.get().getId());
					if (voltageLN == null || voltageLN.equalsIgnoreCase("NAN Error")) {
						voltageLN = "0";
					}
				} else {
					voltageLN = "0";
				}

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				if (optionalVoltageLL.isPresent()) {
					voltageLL = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageLL.get().getId());
					if (voltageLL == null || voltageLL.equalsIgnoreCase("NAN Error")) {
						voltageLL = "0";
					}
				} else {
					voltageLL = "0";
				}

				
				
				
				if (optionalVoltageRN.isPresent()) {
					voltageRN = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageRN.get().getId());
					if (voltageRN == null || voltageRN.equalsIgnoreCase("NAN Error")) {
						voltageRN = "0";
					}
				} else {
					voltageRN = "0";
				}

				
				
				if (optionalVoltageYN.isPresent()) {
					voltageYN = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageYN.get().getId());
					if (voltageYN == null || voltageLN.equalsIgnoreCase("NAN Error")) {
						voltageYN = "0";
					}
				} else {
					voltageLN = "0";
				}

				
				if (optionalVoltageBN.isPresent()) {
					voltageBN = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
							optionalVoltageBN.get().getId());
					if (voltageBN == null || voltageBN.equalsIgnoreCase("NAN Error")) {
						voltageBN = "0";
					}
				} else {
					voltageBN = "0";
				}

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				BigDecimal roundedValue = new BigDecimal(currentB).setScale(2, RoundingMode.HALF_UP);
				currentB = roundedValue.toString();

				BigDecimal roundedValue1 = new BigDecimal(currentN).setScale(2, RoundingMode.HALF_UP);
				currentN = roundedValue1.toString();

				BigDecimal roundedValue2 = new BigDecimal(currentR).setScale(2, RoundingMode.HALF_UP);
				currentR = roundedValue2.toString();

				BigDecimal roundedValue3 = new BigDecimal(currentY).setScale(2, RoundingMode.HALF_UP);
				currentY = roundedValue3.toString();

				BigDecimal roundedValue4 = new BigDecimal(voltageBR).setScale(2, RoundingMode.HALF_UP);
				voltageBR = roundedValue4.toString();

				BigDecimal roundedValue5 = new BigDecimal(voltageLN).setScale(2, RoundingMode.HALF_UP);
				voltageLN = roundedValue5.toString();

				BigDecimal roundedValue6 = new BigDecimal(voltageRB).setScale(2, RoundingMode.HALF_UP);
				voltageRB = roundedValue6.toString();

				BigDecimal roundedValue7 = new BigDecimal(voltageRY).setScale(2, RoundingMode.HALF_UP);
				voltageRY = roundedValue7.toString();

				BigDecimal roundedValue8 = new BigDecimal(voltageYB).setScale(2, RoundingMode.HALF_UP);
				voltageYB = roundedValue8.toString();
				
				
				BigDecimal roundedValue9 = new BigDecimal(voltageLL).setScale(2, RoundingMode.HALF_UP);
				voltageLL = roundedValue9.toString();

				BigDecimal roundedValue10 = new BigDecimal(voltageRN).setScale(2, RoundingMode.HALF_UP);
				voltageRN = roundedValue10.toString();
				
				BigDecimal roundedValue11= new BigDecimal(voltageYN).setScale(2, RoundingMode.HALF_UP);
				voltageYN = roundedValue11.toString();
				
				BigDecimal roundedValue12 = new BigDecimal(voltageBN).setScale(2, RoundingMode.HALF_UP);
				voltageBN = roundedValue12.toString();
				
				
				
				
				

			

				Optional<CurrentVoltageCard> optional = currentVoltageCardRepo.findByMeterId(meterId);
				CurrentVoltageCard currentVoltageCard;
				if (optional.isPresent()) {
					currentVoltageCard = optional.get();
				} else {
					currentVoltageCard = new CurrentVoltageCard();
				}
				
				
				currentVoltageCard.setMeterId(meterId);
				currentVoltageCard.setCurrentB(currentB);
				currentVoltageCard.setCurrentN(currentN);
				currentVoltageCard.setCurrentR(currentR);
				currentVoltageCard.setCurrentY(currentY);

				currentVoltageCard.setVoltageBR(voltageBR);
				currentVoltageCard.setVoltageLN(voltageLN);
				currentVoltageCard.setVoltageRB(voltageRB);
				currentVoltageCard.setVoltageRY(voltageRY);
				currentVoltageCard.setVoltageYB(voltageYB);
				currentVoltageCard.setVoltageLL(voltageLL);
				currentVoltageCard.setVoltageRN(voltageRN);
				currentVoltageCard.setVoltageBN(voltageBN);
				currentVoltageCard.setVoltageYN(voltageYN);
				
				
				
				currentVoltageCardRepo.save(currentVoltageCard);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/getCurrentDropGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getCurrentDropGraphData() {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
		
			List<String> volatagellAVG = new ArrayList<String>();
			List<String> volatagelnAVG = new ArrayList<String>();
			List<String> label = new ArrayList<String>();
			List<String> series = new ArrayList<String>();

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();
			
			for(EnergyMeterMaster energyMeterMaster:list) {  
				Optional<EnergyMeterRegister> optional = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403010", energyMeterMaster.getModule().getId());
				
				List<CurrentDropGraphData> datas= currentDropGraphDataRepo.findbyMeterId(energyMeterMaster.getId());
				

				
				if(datas.size()==0) {
					for(int i=1;i<=6;i++) 
					{
						CurrentDropGraphData currentDropGraphData= new CurrentDropGraphData();
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
						currentDropGraphDataRepo.save(currentDropGraphData);
					}
				}else {
					for(CurrentDropGraphData  currentDropGraphData:datas ) {
						System.out.println("C     "+currentDropGraphData.getId());
						StartEndDateResponce currentTime = getStartEndTimeByMinuteDiff(currentDropGraphData.getDataNo());
						List<DailyConsumptionLog> currentList = dailyConsumptionLogRepo.getValueByDateAndTime(optional.get().getId(), currentTime.getStartDate(), currentTime.getEndDate());
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
						currentDropGraphDataRepo.save(currentDropGraphData);
					}
				}
				
				
			}
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}
	
	
	@Scheduled(fixedRate = 1800000)
	@GetMapping(value = "/getVoltageDropGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void getVoltageDropGraphData() {
	
		try {

			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();

			for(EnergyMeterMaster energyMeterMaster:list) {  
				Optional<EnergyMeterRegister> optional = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403026", energyMeterMaster.getModule().getId());
				
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
					//	Optional<EnergyMeterRegister> optionalRe2 = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403036", energyMeterMaster.getModule().getId());
						StartEndDateResponce currentTime = getStartEndTimeByMinuteDiff(voltageDropGraphData.getDataNo());
						
						
						String maxValue1 = dailyConsumptionLogRepo.getMaxValueByMeeterRegDateAndTime(optionalRe1.get().getId(),energyMeterMaster.getId(),currentTime.getStartDate(), currentTime.getEndDate());
						//String maxValue2 = dailyConsumptionLogRepo.getMaxValueByMeeterRegDateAndTime(optionalRe2.get().getId(),energyMeterMaster.getId(),currentTime.getStartDate(), currentTime.getEndDate());
						if (maxValue1.equalsIgnoreCase("nan") || maxValue1.equalsIgnoreCase("-")||maxValue1.equalsIgnoreCase("null")||maxValue1.equalsIgnoreCase(null)) {

							voltageDropGraphData.setValue1("");
						} else {
							voltageDropGraphData.setValue1(maxValue1);
						}
						
						/*
						 * if (maxValue2.equalsIgnoreCase("nan") || maxValue2.equalsIgnoreCase("-")) {
						 * 
						 * voltageDropGraphData.setValue2(""); } else {
						 * voltageDropGraphData.setValue2(maxValue2); }
						 */
						voltageDropGraphData.setTime_hrmin(currentTime.getCurrentTime());
						voltageDropGraphDataRepo.save(voltageDropGraphData);
					}
				}
				
				
			}
			
			
			
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Scheduled(fixedRate = 300000)
	@GetMapping(value = "/getMeterWiseCardData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MeterWiseDashboardCardData getMeterWiseCardData() {
		try {
			List<EnergyMeterMaster> list = energyMeterMasterRepo.findAll();
			
			for(EnergyMeterMaster energyMeterMaster:list) {
				System.out.println("METER ID "+energyMeterMaster.getId());
				Optional<EnergyMeterRegister> registerEnergy= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403204", energyMeterMaster.getModule().getId());
				Optional<EnergyMeterRegister> registerPower= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403060", energyMeterMaster.getModule().getId());
				Optional<EnergyMeterRegister> registerApparant= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403076", energyMeterMaster.getModule().getId());
				Optional<EnergyMeterRegister> registerReactPower= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403068", energyMeterMaster.getModule().getId());


				Optional<EnergyMeterRegister> registervoltage= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403026", energyMeterMaster.getModule().getId());
				Optional<EnergyMeterRegister> registervoltage1= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403036", energyMeterMaster.getModule().getId());

				Optional<EnergyMeterRegister> registercurrent= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403010", energyMeterMaster.getModule().getId());
				Optional<EnergyMeterRegister> registerPowerFactor= energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403084", energyMeterMaster.getModule().getId());
				
				


				Optional<EnergyMeterRegister> reactiveEnergy = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403220",energyMeterMaster.getModule().getId());
				
				
				Optional<EnergyMeterRegister> realEnergy = energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId("403204",energyMeterMaster.getModule().getId());
				
				
				LocalDate currentDate1 = LocalDate.now();

				// Extract the month and year
				int year = currentDate1.getYear();
				int month = currentDate1.getMonthValue();
				
				
				
				String toalConsumption =  dayWiseConsumptionRepo.getConssumptionByModuleNoAndMonth("403204", energyMeterMaster.getId(), month,year);

				
				List<DailyConsumptionLog> optionalApparant  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registerApparant.get().getId(), new Date());
				Optional<DayWiseConsumption> optionalEnergy  =  dayWiseConsumptionRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registerEnergy.get().getId(), new Date());
				List<DailyConsumptionLog> optionalPower  =  dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registerPower.get().getId(), new Date());
				List<DailyConsumptionLog> optionalReactPower  =  dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registerReactPower.get().getId(), new Date());

				
				List<DailyConsumptionLog> optionalReactVoltage  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registervoltage.get().getId(), new Date());
				List<DailyConsumptionLog> optionalReactVoltage2  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registervoltage1.get().getId(), new Date());

				List<DailyConsumptionLog> optionalReactCurrent  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registercurrent.get().getId(), new Date());
				List<DailyConsumptionLog> optionalReactPowerFactor  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), registerPowerFactor.get().getId(), new Date());

				
				
				List<DailyConsumptionLog> optionalReactEnergy  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), reactiveEnergy.get().getId(), new Date());
				List<DailyConsumptionLog> optionalRealEnergy  = dailyConsumptionLogRepo.getConsumptionByMeterIDAndDateAndRegisterId(energyMeterMaster.getId(), realEnergy.get().getId(), new Date());

			
				
				
				MeterWiseDashboardCardData meterWiseDashboardCardData;
				Optional<MeterWiseDashboardCardData> optional2=meterWiseDashboardCardDataRepo.findByMeterId(energyMeterMaster.getId());
				if(optional2.isPresent()) {
					meterWiseDashboardCardData=optional2.get();
				}else {
					meterWiseDashboardCardData= new MeterWiseDashboardCardData();
				}
		        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
		        
		        
		        
		        
		        
		        

		        String energyTime="";
		        double energyValue;
		        if(optionalEnergy.isPresent()) {
		        	energyTime=formatter.format(optionalEnergy.get().getConsumptionDate());
		        	energyValue=optionalEnergy.get().getRegisterValue();
		        }else{
		        	energyValue=0.0;
		        	energyTime=formatter.format(new Date());
		        }
		        
		        
		        
		        
		        
		        
		        
		        String apparantTime="";
		        String apparantValue;
		        if(optionalApparant.size()!=0) {
		        	 apparantTime=formatter.format(optionalApparant.get(0).getCreateDateTime());
		        	 apparantValue=optionalApparant.get(0).getRegisterValue();
		        }else{
		        	apparantValue="0.0";
		        	 apparantTime=formatter.format(new Date());
		        }
		        
		        
		        
		        
		        
		        String powerTime="";
		        String powerValue;
		        if(optionalPower.size()!=0) {
		        	powerTime=formatter.format(optionalPower.get(0).getCreateDateTime());
		        	powerValue=optionalPower.get(0).getRegisterValue();
		        }else{
		        	powerValue="0.0";
		        	powerTime=formatter.format(new Date());
		        }
		        
		        String reactivePowerTime="";
		        String reactivePowerValue;
		        if(optionalReactPower.size()!=0) {
		        	reactivePowerTime=formatter.format(optionalReactPower.get(0).getCreateDateTime());
		        	reactivePowerValue=optionalReactPower.get(0).getRegisterValue();
		        }else{
		        	reactivePowerValue="0.0";
		        	reactivePowerTime=formatter.format(new Date());
		        }
				
				
		        
		        
		        
		        String voltageValue1;
		        if(optionalReactVoltage.size()!=0) {
		        	voltageValue1=optionalReactVoltage.get(0).getRegisterValue();
		        }else{
		        	voltageValue1="0.0";
		        }
		        
		        String voltageValue2;
		        if(optionalReactVoltage2.size()!=0) {
		        	voltageValue2=optionalReactVoltage2.get(0).getRegisterValue();
		        }else{
		        	voltageValue2="0.0";
		        }
				Double voltageValueD=Double.valueOf(voltageValue1)+Double.valueOf(voltageValue2);
				String voltageValue=String.valueOf(voltageValueD/2);
				
				
				
		        String currentValue;
		        if(optionalReactCurrent.size()!=0) {
		        	currentValue=optionalReactCurrent.get(0).getRegisterValue();
		        }else{
		        	currentValue="0.0";
		        }
				
		        String powerFactorValue;
		        if(optionalReactPowerFactor.size()!=0) {
		        	powerFactorValue=optionalReactPowerFactor.get(0).getRegisterValue();
		        }else{
		        	powerFactorValue="0.0";
		        }
				
		        
		        
		        
		        
		        
		        
		        
		        String reactiveEnergyValue;
		        if(optionalReactEnergy.size()!=0) {
		        	reactiveEnergyValue=optionalReactEnergy.get(0).getRegisterValue();
		        }else{
		        	reactiveEnergyValue="0.0";
		        }
				
		        
		        
		        String realEnergyValue;
		        if(optionalRealEnergy.size()!=0) {
		        	realEnergyValue=optionalRealEnergy.get(0).getRegisterValue();
		        }else{
		        	realEnergyValue="0.0";
		        }
				
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        BigDecimal roundedValue = new BigDecimal(apparantValue).setScale(2, RoundingMode.HALF_UP);
		        apparantValue =(String) roundedValue.toString();
		        
		        BigDecimal roundedValue1 = new BigDecimal(energyValue).setScale(2, RoundingMode.HALF_UP);
		        energyValue = Double.valueOf(roundedValue1.toString());
		        
		        
		        BigDecimal roundedValue2 = new BigDecimal(powerValue).setScale(2, RoundingMode.HALF_UP);
		        powerValue = String.valueOf(roundedValue2.toString());
					
		        
		        BigDecimal roundedValue3 = new BigDecimal(reactivePowerValue).setScale(2, RoundingMode.HALF_UP);
		        reactivePowerValue = String.valueOf(roundedValue3.toString());
		        
		        
		        
		        BigDecimal roundedValue4 = new BigDecimal(voltageValue).setScale(2, RoundingMode.HALF_UP);
		        voltageValue = (String) roundedValue4.toString();
		        
		        
		        BigDecimal roundedValue5 = new BigDecimal(currentValue).setScale(2, RoundingMode.HALF_UP);
		        currentValue = (String) roundedValue5.toString();
		        
		        BigDecimal roundedValue6 = new BigDecimal(powerFactorValue).setScale(2, RoundingMode.HALF_UP);
		        powerFactorValue = (String) roundedValue6.toString();
				
		        BigDecimal roundedValue7 = new BigDecimal(toalConsumption).setScale(2, RoundingMode.HALF_UP);
		        toalConsumption= (String) roundedValue7.toString();
		        
		        
		        BigDecimal roundedValue8 = new BigDecimal(reactiveEnergyValue).setScale(2, RoundingMode.HALF_UP);
		        reactiveEnergyValue= (String) roundedValue8.toString();
		        
		        BigDecimal roundedValue9 = new BigDecimal(realEnergyValue).setScale(2, RoundingMode.HALF_UP);
		        realEnergyValue= (String) roundedValue9.toString();
		              
		        
		        
		        
		        
		        
		        
		        
		        
				meterWiseDashboardCardData.setEnergyMeterMaster(energyMeterMaster);
				meterWiseDashboardCardData.setTotalengeryConsumption(toalConsumption);
				
				meterWiseDashboardCardData.setApparantTime(apparantTime);
				meterWiseDashboardCardData.setApparantValue(apparantValue);
				
				meterWiseDashboardCardData.setEngeryConsumptionTime(energyTime);
				meterWiseDashboardCardData.setEngeryConsumptionValue(energyValue);
				
				
				
				meterWiseDashboardCardData.setPowerTime(powerTime);
				meterWiseDashboardCardData.setPowerValue(powerValue);
				
				
				
				
				meterWiseDashboardCardData.setReactivePowerTime(reactivePowerTime);
				meterWiseDashboardCardData.setReactivePowerValue(reactivePowerValue);
				
				
				meterWiseDashboardCardData.setCurrent(String.valueOf(currentValue));
				meterWiseDashboardCardData.setVoltage(String.valueOf(voltageValue));
				meterWiseDashboardCardData.setPowerFactor(String.valueOf(powerFactorValue));
				
				
				meterWiseDashboardCardData.setRealEnergy(realEnergyValue);
				meterWiseDashboardCardData.setReactiveEnergy(reactiveEnergyValue);
				
				
				meterWiseDashboardCardDataRepo.save(meterWiseDashboardCardData);
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
