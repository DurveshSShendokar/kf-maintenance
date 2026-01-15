package com.kfMaintenancce.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.CardDataDTO;
import com.kfMaintenancce.dto.ConsupmtionDashboardDTO;
import com.kfMaintenancce.dto.DropGraphDTO;
import com.kfMaintenancce.dto.MeterStatus;
import com.kfMaintenancce.dto.RegisterDTO;
import com.kfMaintenancce.dto.StartEndDateResponce;
import com.kfMaintenancce.model.ConsumptionDetails;
import com.kfMaintenancce.model.CurrentDropGraphData;
import com.kfMaintenancce.model.CurrentVoltageCard;
import com.kfMaintenancce.model.DailyConsumptionLog;
import com.kfMaintenancce.model.DayWiseConsumption;
import com.kfMaintenancce.model.DeviceModel;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterModule;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.EnergyMeterRegisterGroup;
import com.kfMaintenancce.model.EnergyMeterRegisterGroupItems;
import com.kfMaintenancce.model.MeterWiseDashboardCardData;
import com.kfMaintenancce.model.MonthlyConsumptionGraph;
import com.kfMaintenancce.model.OverviewDahboardCardCount;
import com.kfMaintenancce.model.OverviewDahboardPieChart;
import com.kfMaintenancce.model.OverviewDahboardUnitConsumption;
import com.kfMaintenancce.model.OverviewDahboardVoltageMinMax;
import com.kfMaintenancce.model.PowerFactorGraphData;
import com.kfMaintenancce.model.RegisterGroupDTO;
import com.kfMaintenancce.model.VoltageDropGraphData;
import com.kfMaintenancce.model.WeeklyConsumptionGraph;
import com.kfMaintenancce.repo.ConsumptionDetailsRepo;
import com.kfMaintenancce.repo.CurrentDropGraphDataRepo;
import com.kfMaintenancce.repo.CurrentVoltageCardRepo;
import com.kfMaintenancce.repo.DailyConsumptionLogRepo;
import com.kfMaintenancce.repo.DayWiseConsumptionRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterGroupItemsRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterGroupRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.MeterWiseDashboardCardDataRepo;
import com.kfMaintenancce.repo.MonthlyConsumptionGraphRepo;
import com.kfMaintenancce.repo.OverviewDahboardCardCountrrRepo;
import com.kfMaintenancce.repo.OverviewDahboardPieChartRepo;
import com.kfMaintenancce.repo.OverviewDahboardUnitConsumptionRepo;
import com.kfMaintenancce.repo.OverviewDahboardVoltageMinMaxRepo;
import com.kfMaintenancce.repo.PowerFactorGraphDataRepo;
import com.kfMaintenancce.repo.VoltageDropGraphDataRepo;
import com.kfMaintenancce.repo.WeeklyConsumptionGraphRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/emsDashboard")
public class EMSDashboard {

	@Autowired
	DailyConsumptionLogRepo dailyConsumptionLogRepo;

	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	@Autowired
	EnergyMeterMasterRepo energyMeterMasterRepo;

	@Autowired
	DayWiseConsumptionRepo dayWiseConsumptionRepo;
	@Autowired
	ConsumptionDetailsRepo consumptionDetailsRepo;
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
	private OverviewDahboardUnitConsumptionRepo overviewDahboardUnitConsumptionRepo;
	@Autowired
	OverviewDahboardPieChartRepo overviewDahboardPieChartRepo;
	@Autowired
	CurrentVoltageCardRepo currentVoltageCardRepo;
	@Autowired
	MeterWiseDashboardCardDataRepo meterWiseDashboardCardDataRepo;
	@Autowired
	private OverviewDahboardCardCountrrRepo overviewDahboardCardCountrrRepo;
	@Autowired
	OverviewDahboardVoltageMinMaxRepo overviewDahboardVoltageMinMaxRepo;
	@Autowired
	EnergyMeterRegisterGroupItemsRepo energyMeterRegisterGroupItemsRepo;
	@Autowired
	EnergyMeterRegisterGroupRepo energyMeterRegisterGroupRepo;
	// *********************************************************************************************
	// OVERVIEW DASHBOARD
	// ************************************************************************************//

	@GetMapping(value = "/getOverviewDahboardCardCounts")
	public OverviewDahboardCardCount getOverviewDahboardCardCounts() {
		OverviewDahboardCardCount overviewDahboardCardCount = new OverviewDahboardCardCount();
		List<OverviewDahboardCardCount> list = overviewDahboardCardCountrrRepo.findAll();
		if (list.size() != 0) {
			overviewDahboardCardCount = list.get(0);
		}

		return overviewDahboardCardCount;
	}

	@GetMapping(value = "/getOverviewDahboardPieChart")
	public OverviewDahboardPieChart getOverviewDahboardPieChart() {
		OverviewDahboardPieChart overviewDahboardCardCount = new OverviewDahboardPieChart();

		try {
			List<OverviewDahboardPieChart> list = overviewDahboardPieChartRepo.findAll();
			overviewDahboardCardCount = list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return overviewDahboardCardCount;
	}

	@GetMapping(value = "/getOverviewDahboardUnitConsumption")
	public ConsupmtionDashboardDTO getOverviewDahboardUnitConsumption(@RequestParam("noOfDays") int noOfDays) {
		ConsupmtionDashboardDTO consupmtionDashboardDTO = new ConsupmtionDashboardDTO();

		try {
			List<String> dates = new ArrayList<String>();
			List<String> data = new ArrayList<String>();
			List<OverviewDahboardUnitConsumption> list = new ArrayList<OverviewDahboardUnitConsumption>();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

			for (int i = 0; i < noOfDays; i++) {
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(new Date());

				// Subtract 2 days
				calendar1.add(Calendar.DATE, -i);

				// Get the updated date
				Date monthdDayDate = calendar1.getTime();
				String consumption = overviewDahboardUnitConsumptionRepo.getByDate(monthdDayDate);
				String dateString = formatter.format(monthdDayDate);
				dates.add(dateString);
				data.add(consumption);

			}

			consupmtionDashboardDTO.setStrings(dates);
			consupmtionDashboardDTO.setStringList(data);
			System.out.println("LIST " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return consupmtionDashboardDTO;
	}

	@GetMapping(value = "/getOverviewDahboardVoltageMinMax")
	public ConsupmtionDashboardDTO getOverviewDahboardVoltageMinMax(@RequestParam("noOfDays") int noOfDays) {
		ConsupmtionDashboardDTO consupmtionDashboardDTO = new ConsupmtionDashboardDTO();
		List<String> dates = new ArrayList<String>();
		List<String> min = new ArrayList<String>();
		List<String> max = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		
		
		for (int i = 0; i < noOfDays; i++) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(new Date());

			// Subtract 2 days
			calendar1.add(Calendar.DATE, -i);

			// Get the updated date
			Date monthdDayDate = calendar1.getTime();
			Optional<OverviewDahboardVoltageMinMax> optional = overviewDahboardVoltageMinMaxRepo.getByDate(monthdDayDate);
			if(optional.isPresent()) {
				System.out.println(i+".    "+monthdDayDate+" "+optional.isPresent());
				String dateString = formatter.format(monthdDayDate);
				dates.add(dateString);
				min.add(optional.get().getMinValue());
				max.add(optional.get().getMaxValue());
			}
			

		}
		
		
		
		
		
		consupmtionDashboardDTO.setStringList(dates);
		consupmtionDashboardDTO.setMin(min);
		consupmtionDashboardDTO.setMax(max);

		return consupmtionDashboardDTO;
	}

	@GetMapping(value = "/getDayWiseConsumptionGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getDayWiseConsumptionGraphData(@RequestParam("meterId") int meterId) {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {
			Date currentDate = new Date();
			List<String> labels = new ArrayList<String>();
			List<String> series = new ArrayList<String>();
			List<String> data = new ArrayList<String>();
			Optional<WeeklyConsumptionGraph> weeklyConsumptionGraphs = weeklyConsumptionGraphRepo.getByMeterId(meterId);
			if (weeklyConsumptionGraphs.isPresent()) {
				data.add(weeklyConsumptionGraphs.get().getTodayConsumption());
				data.add(weeklyConsumptionGraphs.get().getDay1Consumption());
				data.add(weeklyConsumptionGraphs.get().getDay2Consumption());
				data.add(weeklyConsumptionGraphs.get().getDay3Consumption());
				data.add(weeklyConsumptionGraphs.get().getDay4Consumption());
				data.add(weeklyConsumptionGraphs.get().getDay5Consumption());
				data.add(weeklyConsumptionGraphs.get().getDay6Consumption());

				labels.add(weeklyConsumptionGraphs.get().getTodayDate());
				labels.add(weeklyConsumptionGraphs.get().getDay1Date());
				labels.add(weeklyConsumptionGraphs.get().getDay2Date());
				labels.add(weeklyConsumptionGraphs.get().getDay3Date());
				labels.add(weeklyConsumptionGraphs.get().getDay4Date());
				labels.add(weeklyConsumptionGraphs.get().getDay5Date());
				labels.add(weeklyConsumptionGraphs.get().getDay6Date());

			}

			dropGraphDTO.setData1(data);
			dropGraphDTO.setLabel(labels);
			dropGraphDTO.setSeries(series);
			System.out.println(" LABEL " + labels);
			System.out.println(" Serial " + series);
			System.out.println(" Data " + data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

//********************************************************************************************* OVERVIEW DASHBOARD  END  ************************************************************************************//

	// *********************************************************************************************
	// METER WISE DASHBOARD
	// ************************************************************************************//

	@GetMapping(value = "/getMeterActiveStatus")
	public MeterStatus getMeterActiveStatus(@RequestParam("materId") int materId) {
		MeterStatus meterStatus = new MeterStatus();
		try {

			Pageable pageable = PageRequest.of(0, 1); // First page, with one result
			List<DayWiseConsumption> list = dayWiseConsumptionRepo.getLastConsumptionByMeterID(materId, pageable);
			
			String status = "";
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

			for (DayWiseConsumption consumptionLog : list) {

				Date date1 = new Date();
				long diffInMillis = date1.getTime() - consumptionLog.getConsumptionDate().getTime();
				long diffInMinutes = diffInMillis / (60 * 1000);
				if (diffInMinutes <= 5) {
					status = "ON";
				} else {
					status = "OFF";
				}
				String dateString = formatter.format(consumptionLog.getConsumptionDate());
				meterStatus.setLastActiveDate(dateString);
				meterStatus.setStatus(status);
				System.out.println("diffInMinutes   "+diffInMinutes);
				
				System.out.println("CONSUMTIOPN Date "+consumptionLog.getConsumptionDate()+"    "+consumptionLog.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return meterStatus;
	}

	@GetMapping(value = "/getMeterWiseCardData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MeterWiseDashboardCardData getMeterWiseCardData(@RequestParam("meterId") int meterId) {
		MeterWiseDashboardCardData meterWiseDashboardCardData = new MeterWiseDashboardCardData();
		try {
			Optional<MeterWiseDashboardCardData> optional = meterWiseDashboardCardDataRepo.findByMeterId(meterId);
			if (optional.isPresent()) {
				meterWiseDashboardCardData = optional.get();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return meterWiseDashboardCardData;
	}

	@GetMapping(value = "/getVoltageDropGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getAllDeviceModels(@RequestParam("meterId") int meterId) {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {

			List<String> volatagellAVG = new ArrayList<String>();
			List<String> volatagelnAVG = new ArrayList<String>();
			List<String> label = new ArrayList<String>();
			List<String> series = new ArrayList<String>();

			List<VoltageDropGraphData> datas = voltageDropGraphDataRepo.findbyMeterId(meterId);

			for (VoltageDropGraphData currentDropGraphData : datas) {
				label.add(currentDropGraphData.getTime_hrmin());
				volatagellAVG.add(currentDropGraphData.getValue1());
				volatagelnAVG.add(currentDropGraphData.getValue2());
			}

			series.add("Voltage L-L Avg");
			series.add("Voltage L-N Avg");

			dropGraphDTO.setData1(volatagellAVG);
			dropGraphDTO.setData2(volatagelnAVG);

			dropGraphDTO.setLabel(label);
			dropGraphDTO.setSeries(series);

			dropGraphDTO.setData1(volatagellAVG);
			dropGraphDTO.setData2(volatagelnAVG);
			dropGraphDTO.setLabel(label);
			dropGraphDTO.setSeries(series);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

	@GetMapping(value = "/getCurrentDropGraphData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DropGraphDTO getCurrentDropGraphData(@RequestParam("meterId") int meterId) {
		DropGraphDTO dropGraphDTO = new DropGraphDTO();
		try {

			List<String> currentData = new ArrayList<String>();
			List<String> volatagelnAVG = new ArrayList<String>();
			List<String> label = new ArrayList<String>();
			List<String> series = new ArrayList<String>();
			List<CurrentDropGraphData> datas = currentDropGraphDataRepo.findbyMeterId(meterId);

			for (CurrentDropGraphData currentDropGraphData : datas) {
				label.add(currentDropGraphData.getTime_hrmin());
				currentData.add(currentDropGraphData.getValue());
			}

			series.add("Current Trand");

			dropGraphDTO.setData1(currentData);

			dropGraphDTO.setLabel(label);
			dropGraphDTO.setSeries(series);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropGraphDTO;
	}

	@RequestMapping(value = "/getAllRegisterDetialsByMeterId", method = RequestMethod.GET)
	public @ResponseBody List<RegisterGroupDTO> getAllRegisterDetials(@RequestParam("meterId") int meterId) {
		List<RegisterGroupDTO> registerGroupDTOs = new ArrayList<RegisterGroupDTO>();
		try {
			List<EnergyMeterRegisterGroup> groups = energyMeterRegisterGroupRepo.findAll();
			System.out.println("GROUPS " + groups.size());

			Optional<EnergyMeterMaster> optional = energyMeterMasterRepo.findById(meterId);
			for (EnergyMeterRegisterGroup energyMeterRegisterGroup : groups) {
				RegisterGroupDTO groupdto = new RegisterGroupDTO();
				groupdto.setGroupName(energyMeterRegisterGroup.getGroupName());
				List<RegisterDTO> list1 = new ArrayList<RegisterDTO>();
				List<EnergyMeterRegisterGroupItems> list = energyMeterRegisterGroupItemsRepo
						.getEnergyMeterRegisterGroupItemsByGroup(energyMeterRegisterGroup.getId());

				for (EnergyMeterRegisterGroupItems energyMeterRegisterGroupItems : list) {

					// Optional<EnergyMeterRegister>
					// optionalRes=energyServices.getEnergyMeterRegisterByRegisterNoandModuleId(energyMeterRegisterGroupItems.getRegisterNo(),optional.get().getModule().getId());
					System.out.println("Register " + energyMeterRegisterGroupItems.getRegisterNo() + " Module "
							+ optional.get().getModule().getId());
					Optional<EnergyMeterRegister> optionalRes = energyMeterRegisterRepo
							.getEnergyMeterRegisterByRegisterNoandModuleId(
									energyMeterRegisterGroupItems.getRegisterNo(), optional.get().getModule().getId());
					System.out.println("Register " + optionalRes.isPresent());
					if (optionalRes.isPresent()) {

						String currentA = consumptionDetailsRepo.getMaxValueByDateMeterAndModule(meterId,
								optionalRes.get().getId());
						// String
						// currentA=dailyConsumptionLogRepo.getMaxValueByDateMeterAndModule(meterId,
						// optionalRes.get().getId(), new Date());

						RegisterDTO dto = new RegisterDTO();
						dto.setRegisterName(optionalRes.get().getRegisterName());
						System.out.println("currentA ----------------   " + currentA);
						dto.setUnit(optionalRes.get().getUom());
						if (currentA.equalsIgnoreCase("-") || currentA.equalsIgnoreCase("NAN Error")
								|| currentA.equalsIgnoreCase("null")) {
							dto.setRegisterValue(Double.valueOf(0));
						} else {

							dto.setRegisterValue(Double.valueOf(currentA));
						}
						list1.add(dto);

					}

				}
				groupdto.setShowView(false);
				groupdto.setList(list1);
				registerGroupDTOs.add(groupdto);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerGroupDTOs;
	}

	@GetMapping(value = "/getCurrentVoltageCardData", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CurrentVoltageCard getCurrentVoltageCardData(@RequestParam("meterId") int meterId) {
		CurrentVoltageCard card = new CurrentVoltageCard();
		try {

			Optional<CurrentVoltageCard> optional = currentVoltageCardRepo.findByMeterId(meterId);
			if (optional.isPresent()) {
				card = optional.get();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return card;
	}

//********************************************************************************************* METER WISE DASHBOARD END ************************************************************************************//

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public @ResponseBody StartEndDateResponce getStartEndTimeByMinuteDiff(int
	 * minute) { StartEndDateResponce responce = new StartEndDateResponce(); Date
	 * currentDate = new Date();
	 * 
	 * Calendar calendar = Calendar.getInstance(); calendar.setTime(currentDate);
	 * calendar.add(Calendar.MINUTE, -minute); Date timeMinus = calendar.getTime();
	 * 
	 * Calendar calendarE = Calendar.getInstance(); calendarE.setTime(timeMinus);
	 * calendarE.add(Calendar.MINUTE, -10); Date startTime = calendarE.getTime();
	 * 
	 * Calendar calendarS = Calendar.getInstance(); calendarS.setTime(timeMinus);
	 * calendarS.add(Calendar.MINUTE, 10); Date endTime = calendarS.getTime();
	 * 
	 * DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); String strDateStr =
	 * dateFormat.format(startTime); String endStr = dateFormat.format(endTime);
	 * DateFormat dateFormat2 = new SimpleDateFormat("HH:mm"); String currentTime =
	 * dateFormat2.format(timeMinus);
	 * 
	 * responce.setStartDate(strDateStr); responce.setEndDate(endStr);
	 * responce.setCurrentTime(currentTime); return responce; }
	 * 
	 * 
	 * 
	 * @GetMapping(value = "/getCardData", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody CardDataDTO
	 * getCardData(@RequestParam("meterId") int meterId) { CardDataDTO cardDataDTO =
	 * new CardDataDTO(); try {
	 * 
	 * Optional<EnergyMeterMaster> optionalMeter =
	 * energyMeterMasterRepo.findById(meterId);
	 * 
	 * Optional<EnergyMeterRegister> optionalVoltageAvg = energyMeterRegisterRepo
	 * .getEnergyMeterRegisterByRegisterNoandModuleId("403036",
	 * optionalMeter.get().getModule().getId()); Optional<EnergyMeterRegister>
	 * optionalCurrentAvg = energyMeterRegisterRepo
	 * .getEnergyMeterRegisterByRegisterNoandModuleId("403010",
	 * optionalMeter.get().getModule().getId()); Optional<EnergyMeterRegister>
	 * optionalAapparant = energyMeterRegisterRepo
	 * .getEnergyMeterRegisterByRegisterNoandModuleId("403796",
	 * optionalMeter.get().getModule().getId()); Optional<EnergyMeterRegister>
	 * optionalenergy = energyMeterRegisterRepo
	 * .getEnergyMeterRegisterByRegisterNoandModuleId("403204",
	 * optionalMeter.get().getModule().getId());
	 * 
	 * String voltageAvg =
	 * dailyConsumptionLogRepo.getMaxValueByDateMeterAndModule(meterId,
	 * optionalVoltageAvg.get().getId(), new Date()); String currentAvg =
	 * dailyConsumptionLogRepo.getMaxValueByDateMeterAndModule(meterId,
	 * optionalCurrentAvg.get().getId(), new Date()); String apparant =
	 * dailyConsumptionLogRepo.getMaxValueByDateMeterAndModule(meterId,
	 * optionalAapparant.get().getId(), new Date()); Optional<DayWiseConsumption>
	 * dayWiseConsumption = dayWiseConsumptionRepo
	 * .getConsumptionByMeterIDAndDateAndModule(meterId,
	 * optionalenergy.get().getId(), new Date());
	 * 
	 * if (voltageAvg != null) { System.out.println("VOLZTGE ");
	 * cardDataDTO.setVoltageAvg(voltageAvg); } else {
	 * System.out.println("VOLZTGE 3424234	"); cardDataDTO.setVoltageAvg("0"); }
	 * System.out.println("voltageAvg " + voltageAvg); if (currentAvg != null) {
	 * cardDataDTO.setCurrentAvg(currentAvg); } else {
	 * cardDataDTO.setCurrentAvg("0"); }
	 * 
	 * if (apparant != null) { cardDataDTO.setApparant(apparant); } else {
	 * cardDataDTO.setApparant("0"); }
	 * 
	 * if (dayWiseConsumption.isPresent()) { double energyConsumption =
	 * dayWiseConsumption.get().getRegisterValue();
	 * cardDataDTO.setEnergyConsumption(energyConsumption); } else {
	 * cardDataDTO.setEnergyConsumption(0.0); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return cardDataDTO; }
	 * 
	 * 
	 * 
	 * @GetMapping(value = "/getPowerFactorWeeklyTrendsGraphData", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody DropGraphDTO
	 * getPowerFactorWeeklyTrendsGraphData(@RequestParam("meterId") int meterId) {
	 * DropGraphDTO dropGraphDTO = new DropGraphDTO(); try {
	 * 
	 * List<String> currentData = new ArrayList<String>(); List<String>
	 * volatagelnAVG = new ArrayList<String>(); List<String> label = new
	 * ArrayList<String>(); List<String> series = new ArrayList<String>();
	 * List<PowerFactorGraphData> datas =
	 * powerFactorGraphDataRepo.findbyMeterId(meterId);
	 * 
	 * for (PowerFactorGraphData currentDropGraphData : datas) {
	 * label.add(currentDropGraphData.getTime_hrmin());
	 * currentData.add(currentDropGraphData.getValue()); }
	 * 
	 * series.add("Current Trand");
	 * 
	 * dropGraphDTO.setData1(currentData);
	 * 
	 * dropGraphDTO.setLabel(label); dropGraphDTO.setSeries(series);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return dropGraphDTO; }
	 * 
	 * 
	 * 
	 * 
	 * @GetMapping(value = "/getMonthlyConsumptionGraphData", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody DropGraphDTO
	 * getMonthlyConsumptionGraphData(@RequestParam("meterId") int meterId) {
	 * DropGraphDTO dropGraphDTO = new DropGraphDTO(); try { List<String> labels =
	 * new ArrayList<String>(); List<String> data = new ArrayList<String>();
	 * List<String> series = new ArrayList<String>(); int currentYear =
	 * Calendar.getInstance().get(Calendar.YEAR); series.add("Current Consumption");
	 * List<MonthlyConsumptionGraph> list =
	 * monthlyConsumptionGraphRepo.findByMeterIdAndYear(meterId, currentYear);
	 * 
	 * for (MonthlyConsumptionGraph consumptionGraph : list) {
	 * labels.add(consumptionGraph.getMonthName());
	 * data.add(consumptionGraph.getConsumptionValue()); }
	 * 
	 * dropGraphDTO.setData1(data); dropGraphDTO.setLabel(labels);
	 * dropGraphDTO.setSeries(series);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return dropGraphDTO; }
	 * 
	 */

}
