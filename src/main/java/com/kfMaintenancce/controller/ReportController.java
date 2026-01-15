package com.kfMaintenancce.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.hibernate.jpa.internal.ManagedFlushCheckerLegacyJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.FTRReport;
import com.kfMaintenancce.dto.MTBFReport;
import com.kfMaintenancce.dto.MTTRReport;
import com.kfMaintenancce.dto.ReportReqObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.MachienOperation;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.Target;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.MachienOperationRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.TargetRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/report")
public class ReportController {
	@Autowired
	MachineRepo machineRepo;
	
	@Autowired
	BreakdownRepo breakdownRepo;
	@Autowired
	MachienOperationRepo machienOperationRepo;
	
	
	@Autowired
	TargetRepo targetRepo;
	
	

	@PostMapping(value = "/getFTPFReport", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody FTRReport getFTPFReport(@RequestBody ReportReqObj reqObj ) {
		FTRReport fTRReport= new FTRReport();
		try {
			
			
			System.out.println("Report  For "+reqObj.getMachine());
			System.out.println("Report  By "+reqObj.getReportDuration());

			
			Optional<MachienOperation>  optional=machienOperationRepo.getByMachine(reqObj.getMachine());
	        WeekFields weekFields = WeekFields.of(Locale.getDefault());

			double dayHour=optional.get().getHourDay();
			double daysWeek=optional.get().getDaysOfWeek();
			double hourPerWeek=dayHour*daysWeek; 
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String yearStr = "" + year;

			
			DecimalFormat df = new DecimalFormat("#.##");
			
			
			
			List<Machine> machines=machineRepo.getGetMachinesByName(reqObj.getMachine());
			double totolMachine=machines.size();
			
			
			double distinceMachineJan=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jan",year).size(); 
			double goodMachineJan=totolMachine-distinceMachineJan; 
			double totalMachingJan=goodMachineJan/totolMachine;
			double cloaedBreakDOenJan=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Jan",year);
			
			
			double distinceMachineFeb=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Feb",year).size(); 
			double goodMachineFeb=totolMachine-distinceMachineFeb; 
			double totalMachingFeb=goodMachineFeb/totolMachine;
			double cloaedBreakDOenFeb=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Feb",year);
			
			System.out.println("distinceMachineFeb  "+distinceMachineFeb);
			System.out.println("totolMachine  "+totolMachine);
			System.out.println("goodMachineFeb  "+goodMachineFeb);
			System.out.println("totalMachingFeb  "+totalMachingFeb);
			System.out.println("cloaedBreakDOenFeb  "+cloaedBreakDOenFeb);
			double distinceMachineMar=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Mar",year).size(); 
			double goodMachineMar=totolMachine-distinceMachineMar; 
			double totalMachingMar=goodMachineMar/totolMachine;
			double cloaedBreakDOenMar=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Mar",year);
			
			
			
			double distinceMachineApr=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Apr",year).size(); 
			double goodMachineApr=totolMachine-distinceMachineApr; 
			double totalMachingApr=goodMachineApr/totolMachine;
			double cloaedBreakDOenApr=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Apr",year);
			
			
			double distinceMachineMay=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"May",year).size(); 
			double goodMachineMay=totolMachine-distinceMachineMay; 
			double totalMachingMay=goodMachineMay/totolMachine;
			double cloaedBreakDOenMay=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "May",year);
			
			
			double distinceMachineJun=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jun",year).size(); 
			double goodMachineJun=totolMachine-distinceMachineJun; 
			double totalMachingJun=goodMachineJun/totolMachine;
			double cloaedBreakDOenJun=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Jun",year);
			
			
			double distinceMachineJul=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jul",year).size(); 
			double goodMachineJul=totolMachine-distinceMachineJul; 
			double totalMachingJul=goodMachineJul/totolMachine;
			double cloaedBreakDOenJul=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Jul",year);
			
			
			double distinceMachineAug=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Aug",year).size(); 
			double goodMachineAug=totolMachine-distinceMachineAug; 
			double totalMachingAug=goodMachineAug/totolMachine;
			double cloaedBreakDOenAug=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Aug",year);
			
			
			double distinceMachineSep=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Sep",year).size(); 
			double goodMachineSep=totolMachine-distinceMachineSep; 
			double totalMachingSep=goodMachineSep/totolMachine;
			double cloaedBreakDOenSep=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Sep",year);
			
			
			double distinceMachineOct=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Oct",year).size(); 
			double goodMachineOct=totolMachine-distinceMachineOct; 
			double totalMachingOct=goodMachineOct/totolMachine;
			double cloaedBreakDOenOct=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Oct",year);
			
			
			double distinceMachineNov=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Nov",year).size(); 
			double goodMachineNov=totolMachine-distinceMachineNov; 
			double totalMachingNov=goodMachineNov/totolMachine;
			double cloaedBreakDOenNov=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Nov",year);
			
			
			double distinceMachineDec=breakdownRepo.getDistincrMachineInBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Dec",year).size(); 
			double goodMachineDec=totolMachine-distinceMachineDec; 
			double totalMachingDec=goodMachineDec/totolMachine;
			double cloaedBreakDOenDec=breakdownRepo.getNoOfCloedBreakDownByMachineNameAndMonth(reqObj.getMachine(), "Dec",year);
			
			
			
			
			fTRReport.setClosedJan(cloaedBreakDOenJan);
			fTRReport.setFtrJan(totalMachingJan);
			

			fTRReport.setClosedFeb(cloaedBreakDOenFeb);
			fTRReport.setFtrFeb(totalMachingFeb);
			
			

			fTRReport.setClosedMar(cloaedBreakDOenMar);
			fTRReport.setFtrMar(totalMachingMar);
			
			

			fTRReport.setClosedApr(cloaedBreakDOenApr);
			fTRReport.setFtrApr(totalMachingApr);
			
			

			fTRReport.setClosedMay(cloaedBreakDOenMay);
			fTRReport.setFtrMay(totalMachingMay);
			
			

			fTRReport.setClosedJun(cloaedBreakDOenJun);
			fTRReport.setFtrJun(totalMachingJun);
			

			fTRReport.setClosedJul(cloaedBreakDOenJul);
			fTRReport.setFtrJul(totalMachingJul);
			

			fTRReport.setClosedAug(cloaedBreakDOenAug);
			fTRReport.setFtrAug(totalMachingAug);
			

			fTRReport.setClosedSep(cloaedBreakDOenSep);
			fTRReport.setFtrSep(totalMachingSep);
			

			fTRReport.setClosedOct(cloaedBreakDOenOct);
			fTRReport.setFtrOct(totalMachingOct);
			

			fTRReport.setClosedNov(cloaedBreakDOenNov);
			fTRReport.setFtrNov(totalMachingNov);
			

			fTRReport.setClosedDec(cloaedBreakDOenDec);
			fTRReport.setFtrDec(totalMachingDec);
			
			double totalMaching1st=totalMachingJan+totalMachingFeb+totalMachingMar;
			double totalMaching2nd=totalMachingApr+totalMachingMay+totalMachingJun;
			double totalMaching3rd=totalMachingJul+totalMachingAug+totalMachingSep;
			double totalMaching4th=totalMachingOct+totalMachingNov+totalMachingDec;

			
			
			double cloaedBreakDOen1st=cloaedBreakDOenJan+cloaedBreakDOenFeb+cloaedBreakDOenMar;
			double cloaedBreakDOen2nd=cloaedBreakDOenApr+cloaedBreakDOenMay+cloaedBreakDOenJun;
			double cloaedBreakDOen3rd = cloaedBreakDOenJul + cloaedBreakDOenAug + cloaedBreakDOenSep;
			double cloaedBreakDOen4Th = cloaedBreakDOenOct + cloaedBreakDOenNov + cloaedBreakDOenDec;

			
			
			fTRReport.setCloaedBreakDOen1st(cloaedBreakDOen1st);
			fTRReport.setCloaedBreakDOen2nd(cloaedBreakDOen2nd);
			fTRReport.setCloaedBreakDOen3rd(cloaedBreakDOen3rd);
			fTRReport.setCloaedBreakDOen4Th(cloaedBreakDOen4Th);			
			
			
			fTRReport.setFtr1st(totalMaching1st);
			fTRReport.setFtr2nd(totalMaching2nd);
			fTRReport.setFtr3rd(totalMaching3rd);
			fTRReport.setFtr4th(totalMaching4th);
			
	
			
			return  fTRReport;
		} catch (Exception e) {
			 e.printStackTrace();
			return fTRReport;
		}

	}
	
	
	
	@PostMapping(value = "/getMTBFReports", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MTBFReport getMTBFReports(@RequestBody ReportReqObj reqObj) {
	    MTBFReport mTBFReport = new MTBFReport();
	    try {
	        System.out.println("Report  For " + reqObj.getMachine());
	        System.out.println("Report  By " + reqObj.getReportDuration());
	        
	        

	        Optional<MachienOperation> optional = machienOperationRepo.getByMachine(reqObj.getMachine());
	        if (!optional.isPresent()) {
	            return mTBFReport; // no data
	        }

	        double dayHour = optional.get().getHourDay();
	        double daysWeek = optional.get().getDaysOfWeek();
	        double hourPerWeek = dayHour * daysWeek;

	        int year = Calendar.getInstance().get(Calendar.YEAR);
	        String yearStr = String.valueOf(year);

	        
	        System.out.println("Fetching breakdowns for: " + reqObj.getMachine() + " " + "Jan" + " " + year);

	        // Store monthly MTBF and targets
	        Map<String, Double> mtbfMap = new HashMap<>();
	        Map<String, Double> targetMap = new HashMap<>();

	        // Month names in order
	        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
	                           "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	        for (int i = 1; i <= 12; i++) {
	            String monthName = months[i - 1];

	            // Weeks in the month
	            YearMonth ym = YearMonth.of(year, i);
	            int weeksInMonth = ym.lengthOfMonth() / 7;

	            double operationHours = hourPerWeek * weeksInMonth;

	            // Breakdown count from DB
	            int breakdowns = breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(), monthName, year);

	            double mtbf = (breakdowns == 0) ? 0 : operationHours / breakdowns;

	            // Target from DB
	            Optional<Target> targetOp = targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(), yearStr, monthName, "MTBF");
	            double target = targetOp.map(t -> Double.parseDouble(t.getHour())).orElse(0.0);

	            mtbfMap.put(monthName, mtbf);
	            targetMap.put(monthName, target);
	        }

	        // ---- Save into MTBFReport ----
	        // Example: dynamic setter mapping (if MTBFReport has setters for each month)
	        mTBFReport.setmtbfJan(mtbfMap.get("Jan"));
	        mTBFReport.setTargetJan(targetMap.get("Jan"));
	        mTBFReport.setmtbfFeb(mtbfMap.get("Feb"));
	        mTBFReport.setTargetFeb(targetMap.get("Feb"));
	        mTBFReport.setmtbfMar(mtbfMap.get("Mar"));
	        mTBFReport.setTargetMar(targetMap.get("Mar"));
	        mTBFReport.setmtbfApr(mtbfMap.get("Apr"));
	        mTBFReport.setTargetApr(targetMap.get("Apr"));
	        mTBFReport.setmtbfMay(mtbfMap.get("May"));
	        mTBFReport.setTargetMay(targetMap.get("May"));
	        mTBFReport.setmtbfJun(mtbfMap.get("Jun"));
	        mTBFReport.setTargetJun(targetMap.get("Jun"));
	        mTBFReport.setmtbfJul(mtbfMap.get("Jul"));
	        mTBFReport.setTargetJul(targetMap.get("Jul"));
	        mTBFReport.setmtbfAug(mtbfMap.get("Aug"));
	        mTBFReport.setTargetAug(targetMap.get("Aug"));
	        mTBFReport.setmtbfSep(mtbfMap.get("Sep"));
	        mTBFReport.setTargetSep(targetMap.get("Sep"));
	        mTBFReport.setmtbfOct(mtbfMap.get("Oct"));
	        mTBFReport.setTargetOct(targetMap.get("Oct"));
	        mTBFReport.setmtbfNov(mtbfMap.get("Nov"));
	        mTBFReport.setTargetNov(targetMap.get("Nov"));
	        mTBFReport.setmtbfDec(mtbfMap.get("Dec"));
	        mTBFReport.setTargetDec(targetMap.get("Dec"));

	        // ---- Quarterly sums ----
	        double mtbf1 = mtbfMap.get("Jan") + mtbfMap.get("Feb") + mtbfMap.get("Mar");
	        double mtbf2 = mtbfMap.get("Apr") + mtbfMap.get("May") + mtbfMap.get("Jun");
	        double mtbf3 = mtbfMap.get("Jul") + mtbfMap.get("Aug") + mtbfMap.get("Sep");
	        double mtbf4 = mtbfMap.get("Oct") + mtbfMap.get("Nov") + mtbfMap.get("Dec");

	        double target1 = targetMap.get("Jan") + targetMap.get("Feb") + targetMap.get("Mar");
	        double target2 = targetMap.get("Apr") + targetMap.get("May") + targetMap.get("Jun");
	        double target3 = targetMap.get("Jul") + targetMap.get("Aug") + targetMap.get("Sep");
	        double target4 = targetMap.get("Oct") + targetMap.get("Nov") + targetMap.get("Dec");

	        mTBFReport.setMtbf1(mtbf1);
	        mTBFReport.setTarget1(target1);
	        mTBFReport.setMtbf2(mtbf2);
	        mTBFReport.setTarget2(target2);
	        mTBFReport.setMtbf3(mtbf3);
	        mTBFReport.setTarget3(target3);
	        mTBFReport.setMtbf4(mtbf4);
	        mTBFReport.setTarget4(target4);
	        
	        

	        return mTBFReport;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return mTBFReport;
	    }
	}


	
	
	
	@PostMapping(value = "/getMTBFReport", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MTBFReport getMTBFReport(@RequestBody ReportReqObj reqObj ) {
		MTBFReport mTBFReport= new MTBFReport();
		try {
			
			
			System.out.println("Report  For "+reqObj.getMachine());
			System.out.println("Report  By "+reqObj.getReportDuration());

			
			Optional<MachienOperation>  optional=machienOperationRepo.getByMachine(reqObj.getMachine());
	        WeekFields weekFields = WeekFields.of(Locale.getDefault());

			double dayHour=optional.get().getHourDay();
			double daysWeek=optional.get().getDaysOfWeek();
			double hourPerWeek=dayHour*daysWeek; 
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String yearStr = "" + year;

			
			DecimalFormat df = new DecimalFormat("#.##");
			
			
			
				
				//Jan 
				 YearMonth yearJan= YearMonth.of(year, 1);
				 int weeksInJan = yearJan.lengthOfMonth() / 7;
				double operatioanHourJan=hourPerWeek*weeksInJan; 
				int noOfBreakDownJan =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jan",year); 
				
				double mtbfJan; 
				if(noOfBreakDownJan==0){
					 mtbfJan=0; 
				}else{
					 mtbfJan=operatioanHourJan/noOfBreakDownJan; 
				}
					
				
				Optional<Target> targetJanOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Jan","MTBF");
				double targetJan=0;
				if(targetJanOp.isPresent()) {
					targetJan=Double.parseDouble(targetJanOp.get().getHour());
				}
				mTBFReport.setmtbfJan(mtbfJan);
				mTBFReport.setTargetJan(targetJan);
				
				
				
				// Feb
				
				 YearMonth yearFeb= YearMonth.of(year, 2);
				 int weeksInFeb = yearFeb.lengthOfMonth() / 7;
				double operatioanHourFeb=hourPerWeek*weeksInFeb; 
				int noOfBreakDownFeb=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Feb",year); 
				double mtbfFeb;
				if(noOfBreakDownFeb==0){
					mtbfFeb=0;
				}else{
					 mtbfFeb=operatioanHourFeb/noOfBreakDownFeb; 
				}
				
				Optional<Target> targetFebOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Feb","MTBF");

				double targetFeb=0;
				if(targetFebOp.isPresent()) {
					targetFeb=Double.parseDouble(targetFebOp.get().getHour());
				}
				
				
				mTBFReport.setmtbfFeb(mtbfFeb);
				mTBFReport.setTargetFeb(targetFeb);
				//Mar
				
				 YearMonth yearMar= YearMonth.of(year, 3);
				 int weeksInMar= yearMar.lengthOfMonth() / 7;
				double operatioanHourMar=hourPerWeek*weeksInMar; 
				int noOfBreakDownMar =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Mar",year); 
				double mtbfMar;
				if(noOfBreakDownMar==0){
					mtbfMar=0;
				}else{
					 mtbfMar=operatioanHourMar/noOfBreakDownMar;
				}
				
				 

				Optional<Target> targetMarOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Mar","MTBF");

				
				double targetMar=0;
				if(targetMarOp.isPresent()) {
					targetMar=Double.parseDouble(targetMarOp.get().getHour());
				}
				
				
				mTBFReport.setmtbfMar(mtbfMar);
				mTBFReport.setTargetMar(targetMar);
				
				
				//Apr
				
				
				 YearMonth yearApr= YearMonth.of(year, 4);
				 int weeksInApr = yearApr.lengthOfMonth() / 7;
				double operatioanHourApr=hourPerWeek*weeksInApr; 
				int noOfBreakDownApr=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Apr",year); 
				double mtbfApr;
				if(noOfBreakDownApr==0){
					mtbfApr=0;
				}else{
					 mtbfApr=operatioanHourApr/noOfBreakDownApr; 

				}
				
				Optional<Target> targetAprOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Apr","MTBF");
				double targetApr=0;
				if(targetAprOp.isPresent()) {
					targetApr=Double.parseDouble(targetAprOp.get().getHour());
				}
				
				mTBFReport.setmtbfApr(mtbfApr);
				mTBFReport.setTargetApr(targetApr);
				
				//May
				
				
				 YearMonth yearMay= YearMonth.of(year, 5);
				 int weeksInMay= yearMay.lengthOfMonth() / 7;
				double operatioanHourMay=hourPerWeek*weeksInMay; 
				int noOfBreakDownMay=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"May",year); 
				double mtbfMay;
				if(noOfBreakDownMay==0){
					mtbfMay=0;
				}else{
					 mtbfMay=operatioanHourMay/noOfBreakDownMay;
				}
				
				
				 
				Optional<Target> targetMayOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"May","MTBF");
				double targetMay=0;
			
				if(targetMayOp.isPresent()) {
					targetMay=Double.parseDouble(targetMayOp.get().getHour());
				}
				
				mTBFReport.setmtbfMay(mtbfMay);
				mTBFReport.setTargetMay(targetMay);
				
				//Jun
				
				
				 YearMonth yearJun= YearMonth.of(year, 6);
				 int weeksInJun= yearJun.lengthOfMonth() / 7;
				double operatioanHourJun=hourPerWeek*weeksInJun; 
				int noOfBreakDownJun =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jun",year); 
				double mtbfJun;
				if(noOfBreakDownJun==0){
					mtbfJun=0;
				}else{
					 mtbfJun=operatioanHourJun/noOfBreakDownJun; 
				}
				
				
				

				Optional<Target> targetJunOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Jun","MTBF");
				
				double targetJun=0;
				if(targetJunOp.isPresent()) {
					targetJun=Double.parseDouble(targetJunOp.get().getHour());
				}
				
				mTBFReport.setmtbfJun(mtbfJun);
				mTBFReport.setTargetJun(targetJun);
				
				//Jul
				
				 YearMonth yearJul= YearMonth.of(year, 7);
				 int weeksInJul = yearJul.lengthOfMonth() / 7;
				double operatioanHourJul=hourPerWeek*weeksInJul; 
				int noOfBreakDownJul=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jul",year); 
				double mtbfJul;
				if(noOfBreakDownJul==0){
					mtbfJul=0;
				}else{
					 mtbfJul=operatioanHourJul/noOfBreakDownJul; 

				}
				
				Optional<Target> targetJulOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Jul","MTBF");
				
				double targetJul=0;
				if(targetJulOp.isPresent()) {
					targetJul=Double.parseDouble(targetJulOp.get().getHour());
				}
				
				mTBFReport.setmtbfJul(mtbfJul);
				mTBFReport.setTargetJul(targetJul);
				
				
				//Aug
				
				
				 YearMonth yearAug= YearMonth.of(year, 8);
				 int weeksInAug = yearAug.lengthOfMonth() / 7;
				double operatioanHourAug=hourPerWeek*weeksInAug; 
				int noOfBreakDownAug=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Aug",year); 
				double mtbfAug;
				if(noOfBreakDownAug==0){
					mtbfAug=0;
				}else{
					 mtbfAug=operatioanHourAug/noOfBreakDownAug; 
				}
				
				
				
				Optional<Target> targetAugOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Aug","MTBF");
				
				double targetAug=0;
				if(targetAugOp.isPresent()) {
					targetAug=Double.parseDouble(targetAugOp.get().getHour());
				}
				mTBFReport.setmtbfAug(mtbfAug);
				mTBFReport.setTargetAug(targetAug);
				
				//Sep
				
				
				
				 YearMonth yearSep= YearMonth.of(year, 9);
				 int weeksInSep = yearJan.lengthOfMonth() / 7;
				double operatioanHourSep=hourPerWeek*weeksInSep; 
				int noOfBreakDownSep=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Sep",year); 
				double mtbfSep;
				if(noOfBreakDownSep==0){
					mtbfSep=0;
				}else{
					 mtbfSep=operatioanHourJan/noOfBreakDownSep; 
				}
				
				
				
				
				Optional<Target> targetSepOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Sep","MTBF");
				
				double targetSep=0;
				if(targetSepOp.isPresent()) {
					targetSep=Double.parseDouble(targetSepOp.get().getHour());
				}
				mTBFReport.setmtbfSep(mtbfSep);
				mTBFReport.setTargetSep(targetSep);
				
				
				//Oct
				
				
				
				 YearMonth yearOct= YearMonth.of(year, 10);
				 int weeksInOct = yearOct.lengthOfMonth() / 7;
				double operatioanHourOct=hourPerWeek*weeksInOct; 
				int noOfBreakDownOct =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Oct",year); 
				double mtbfOct; 
				if(noOfBreakDownOct==0){
					mtbfOct=0;
				 }else{
						 mtbfOct=operatioanHourOct/noOfBreakDownOct; 

				 }
				
				Optional<Target> targetOctOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Oct","MTBF");
				
				double targetOct=0;
				if(targetOctOp.isPresent()) {
					targetOct=Double.parseDouble(targetOctOp.get().getHour());
				}
				
				mTBFReport.setmtbfOct(mtbfOct);
				mTBFReport.setTargetOct(targetOct);
				
				
				//NOv
				
				
				 YearMonth yearNov= YearMonth.of(year, 11);
				 int weeksInNov = yearNov.lengthOfMonth() / 7;
				double operatioanHourNov=hourPerWeek*weeksInNov; 
				int noOfBreakDownNov=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Nov",year); 
				double mtbfNov;	
				if(noOfBreakDownNov==0){
						mtbfNov=0;
					}else{
						 mtbfNov=operatioanHourNov/noOfBreakDownNov; 

					}
				
				Optional<Target> targetNovOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Nov","MTBF");
				
				double targetNov=0;
				if(targetNovOp.isPresent()) {
					targetNov=Double.parseDouble(targetNovOp.get().getHour());
				}
				mTBFReport.setmtbfNov(mtbfNov);
				mTBFReport.setTargetNov(targetNov);
				
				//Dec
				
				 YearMonth yearDec= YearMonth.of(year, 12);
				 int weeksInDec = yearDec.lengthOfMonth() / 7;
				double operatioanHourDec=hourPerWeek*weeksInDec; 
				int noOfBreakDownDec=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Dec",year); 
				double mtbfDec;
				if(noOfBreakDownDec==0){
					mtbfDec=0;
				}else{
					 mtbfDec=operatioanHourDec/noOfBreakDownDec; 

				}
				
				Optional<Target> targetDecOp=targetRepo.getTargetByMachineYearnamdMonth(reqObj.getMachine(),yearStr,"Dec","MTBF");
				
				double targetDec=0;
				if(targetDecOp.isPresent()) {
					targetDec=Double.parseDouble(targetDecOp.get().getHour());
				}
				
				
				mTBFReport.setmtbfDec(mtbfDec);
				mTBFReport.setTargetDec(targetDec);
				 
				System.out.println("mtbfJan  "+mtbfJan);

				
				double mtbf1=mtbfJan+mtbfFeb+mtbfMar;
				double mtbf2=mtbfApr+mtbfMay+mtbfJun;
				double mtbf3=mtbfJul+mtbfAug+mtbfSep;
				double mtbf4=mtbfOct+mtbfNov+mtbfDec;
				
				
				double target1=targetJan+targetFeb+targetMar;
				double target2=targetApr+targetMay+targetJun;
				double target3=targetJul+targetAug+targetSep;
				double target4=targetOct+targetNov+targetDec;
			
				
				
			
				mTBFReport.setMtbf1(mtbf1);
				mTBFReport.setTarget1(target1);
				
				mTBFReport.setMtbf2(mtbf2);
				mTBFReport.setTarget2(target2);
				
				mTBFReport.setMtbf3(mtbf3);
				mTBFReport.setTarget3(target3);
				
				mTBFReport.setMtbf4(mtbf4);
				mTBFReport.setTarget4(target4);
			
			
			
			

			
			return  mTBFReport;
		} catch (Exception e) {
			 e.printStackTrace();
			return mTBFReport;
		}

	}

	
	
	public String getData(String machine, String yearStr,String month,String Type) {
		String responce;
		Optional<Target> str=targetRepo.getTargetByMachineYearnamdMonth(machine,yearStr,month,Type);
		if(!str.isPresent()) {
			responce="0";
		}else {
			responce=str.get().getHour();
		}
		System.out.println("STR :: "+str);
		
		return responce;
	}
	
	
	
	
	
	
	
	@PostMapping(value = "/getMTTRReport", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MTTRReport getMTTRReport(@RequestBody ReportReqObj reqObj ) {
		MTTRReport mTTRReport= new MTTRReport();
		try {
			
			
			System.out.println("Report  For "+reqObj.getMachine());
			System.out.println("Report  By "+reqObj.getReportDuration());
			List<Machine> machines= machineRepo.getGetMachinesByName(reqObj.getMachine());
			
			System.out.println("Report  By "+reqObj.getReportDuration());
			DecimalFormat df = new DecimalFormat("#.##");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String yearStr = "" + year;

			
				
				
				// *****************************  JAN *****************************//
				int noOfBreakDownJan =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jan",year); 
				double sumOftimeJan =breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Jan",year); 
				System.out.println("noOfBreakDownJan "+noOfBreakDownJan);
				System.out.println("sumOftimeJan "+sumOftimeJan);
				String meantimeStrJan ;
				
				if(noOfBreakDownJan==0 && sumOftimeJan==0) {
					meantimeStrJan = "0";
					
				}else {
					meantimeStrJan = df.format(sumOftimeJan/noOfBreakDownJan);
				}
				
				double meantimeJan=Double.parseDouble(meantimeStrJan);
				String responce=getData(reqObj.getMachine(),yearStr,"Jan","MTTR");
			
				double targetJan=Double.parseDouble(responce);
				mTTRReport.setMttrJan(meantimeJan);
				mTTRReport.setTargetJan(targetJan);
				
				// *****************************  FEB *****************************//		
				int noOfBreakDownFeb =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Feb",year); 
				int breakDowncountFeb=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Feb",year);
				double meantimeFeb;
				if(breakDowncountFeb==0){
					meantimeFeb=0;
							
				}else{
					double sumOftimeFeb =breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Feb",year);
						String meantimeStrFeb = df.format(sumOftimeFeb/noOfBreakDownFeb);
						 meantimeFeb=Double.parseDouble(meantimeStrFeb);
				}		
				double targetFeb=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Feb","MTTR"));

				mTTRReport.setMttrFeb(meantimeFeb);
				mTTRReport.setTargetFeb(targetFeb);
					
				
				
				// *****************************  MAR *****************************//
				int noOfBreakDownMar =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Mar",year); 
				int breakDowncountMar=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Mar",year);
				double meantimeMar;
				if(breakDowncountMar==0){
					meantimeMar=0;
				}else{
					double sumOftimeMar =breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Mar",year); 
					String meantimeStrMar = df.format(sumOftimeMar/noOfBreakDownMar);
					 meantimeMar=Double.parseDouble(meantimeStrMar);
				}
				
				double targetMar=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Mar","MTTR"));

				mTTRReport.setMttrMar(meantimeMar);
				mTTRReport.setTargetMar(targetMar);
				
				// *****************************  APR *****************************//
				int noOfBreakDownApr =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Apr",year); 
				int breakDowncountApr=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Apr",year);
				double meantimeApr;
				if(breakDowncountApr==0){
					meantimeApr=0;
				}else{
					double sumOftimeApr=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Apr",year); 
					String meantimeStrApr= df.format(sumOftimeApr/noOfBreakDownApr);
					 meantimeApr=Double.parseDouble(meantimeStrApr);
					
				}
				
				double targetApr=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Apr","MTTR"));

				mTTRReport.setMttrApr(meantimeApr);
				mTTRReport.setTargetApr(targetApr);
				
				// *****************************  MAY *****************************//
				int noOfBreakDownMay =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"May",year); 
				int breakDowncountMay=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"May",year);
				double meantimeMay;
				if(breakDowncountMay==0){
					meantimeMay=0;
				}else{
					double sumOftimeMay=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"May",year); 
					String meantimeStrMay= df.format(sumOftimeMay/noOfBreakDownMay);
					meantimeMay=Double.parseDouble(meantimeStrMay);
				}
							
				
				
				double targetMay=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"May","MTTR"));

				mTTRReport.setMttrMay(meantimeMay);
				mTTRReport.setTargetMay(targetMay);
				
				// *****************************  JUN *****************************//
				int noOfBreakDownJun=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jun",year); 
				int breakDowncountJun=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Jun",year);
				double meantimeJun;
				if(breakDowncountJun==0){
					meantimeJun=0;
				}else{
					double sumOftimeJun=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Jun",year); 
					String meantimeStrJun= df.format(sumOftimeJun/noOfBreakDownJun);
					 meantimeJun=Double.parseDouble(meantimeStrJun);
					
				}
				
					double targetJun=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Jun","MTTR"));

				mTTRReport.setMttrJun(meantimeJun);
				mTTRReport.setTargetJun(targetJun);
				
				// *****************************  JUL *****************************//
				int noOfBreakDownJul=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Jul",year); 
				int breakDowncountJul=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Jul",year);
				double meantimeJul;
				if(breakDowncountJul==0){
					meantimeJul=0;
				}else{
					double sumOftimeJul=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Jul",year); 
					String meantimeStrJul= df.format(sumOftimeJul/noOfBreakDownJul);
					 meantimeJul=Double.parseDouble(meantimeStrJul);
					
				}
				
					double targetJul=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Jul","MTTR"));

				
				mTTRReport.setMttrJul(meantimeJul);
				mTTRReport.setTargetJul(targetJul);
				
				// *****************************  AUG *****************************//
				int noOfBreakDownAug=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Aug",year); 
				int breakDowncountAug=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Aug",year);
				double meantimeAug;
				if(breakDowncountAug==0){
					meantimeAug=0;
				}else{
					double sumOftimeAug=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Aug",year); 
					String meantimeStrAug= df.format(sumOftimeAug/noOfBreakDownAug);
					 meantimeAug=Double.parseDouble(meantimeStrAug);
				
				}
				
					double targetAug=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Aug","MTTR"));

				
				mTTRReport.setMttrAug(meantimeAug);
				mTTRReport.setTargetAug(targetAug);
				
				// *****************************  SEP *****************************//	
				int noOfBreakDownSep=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Sep",year); 
				int breakDowncountSep=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Sep",year);
				double meantimeSep;
				if(breakDowncountSep==0){
					meantimeSep=0;
				}else{
					double sumOftimeSep=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Sep",year); 
					String meantimeStrSep= df.format(sumOftimeSep/noOfBreakDownSep);
					 meantimeSep=Double.parseDouble(meantimeStrSep);
				
				}
				
				
				
					double targetSep=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Sep","MTTR"));

				mTTRReport.setMttrSep(meantimeSep);
				mTTRReport.setTargetSep(targetSep);
				
				// *****************************  OCT *****************************//
				int noOfBreakDownOct =breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Oct",year); 
				int breakDowncountOct=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Oct",year);
				double meantimeOct;
				if(breakDowncountOct==0){
					meantimeOct=0;
				}else{
					double sumOftimeOct=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Oct",year); 
					String meantimeStrOct= df.format(sumOftimeOct/noOfBreakDownOct);
					 meantimeOct=Double.parseDouble(meantimeStrOct);
				
				}
				
					double targetOct=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Oct","MTTR"));

				mTTRReport.setMttrOct(meantimeOct);
				mTTRReport.setTargetOct(targetOct);
				
				// *****************************  NOV *****************************//
				int noOfBreakDownNov=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Nov",year); 
				int breakDowncountNov=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Nov",year);
				double meantimeNov;
				if(breakDowncountNov==0){
					meantimeNov=0;
				}else{
					double sumOftimeNov=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Nov",year); 
					String meantimeStrNov= df.format(sumOftimeNov/noOfBreakDownNov);
					 meantimeNov=Double.parseDouble(meantimeStrNov);
				
				}
				
					double targetNov=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Nov","MTTR"));

				mTTRReport.setMttrNov(meantimeNov);
				mTTRReport.setTargetNov(targetNov);

				// *****************************  DEC *****************************//
				int noOfBreakDownDec=breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(),"Dec",year); 
				int breakDowncountDec=breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Dec",year);
				
				System.out.println("noOfBreakDownDec :: "+noOfBreakDownDec);
				System.out.println("breakDowncountDec :: "+breakDowncountDec);
				
				
				double meantimeDec;
				if(breakDowncountDec==0){
					meantimeDec=0;
				}else{
					double sumOftimeDec=breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(),"Dec",year); 
					
					System.out.println("sumOftimeDec :: "+sumOftimeDec);
					String meantimeStrDec= df.format(sumOftimeDec/noOfBreakDownDec);
					
					System.out.println("sumOftimeDec "+sumOftimeDec);
					
					 meantimeDec=Double.parseDouble(meantimeStrDec);
					
				}
				
				
				double targetDec=Double.parseDouble(getData(reqObj.getMachine(),yearStr,"Dec","MTTR"));
				
				
				
				System.out.println("Target :: "+targetDec+"  FOR "+yearStr);
				
				System.out.println("Target :: ");
				
				

				mTTRReport.setMttrDec(meantimeDec);
				mTTRReport.setTargetDec(targetDec);
			   	
				
				
				double mttr1St=meantimeJan+meantimeFeb+meantimeMar;
				double mttr2nd=meantimeApr+meantimeMay+meantimeJun;
				double mttr3rd=meantimeJul+meantimeAug+meantimeSep;
				double mttr4th=meantimeOct+meantimeNov+meantimeDec;

			
				double target1st=targetJan+targetFeb+targetMar;
				double target2nd=targetApr+targetMay+targetJun;
				double target3rd=targetJul+targetAug+targetSep;
				double target4th=targetOct+targetNov+targetDec;
				
			
				mTTRReport.setMttr1st(mttr1St);
				mTTRReport.setTarget1st(target1st);
				
				mTTRReport.setMttr2nd(mttr2nd);
				mTTRReport.setTarget2nd(target2nd);
				
				mTTRReport.setMttr3rd(mttr3rd);
				mTTRReport.setTarget3rd(target3rd);
				
				
				mTTRReport.setMttr4th(mttr4th);
				mTTRReport.setTarget4th(target4th);

			
			return  mTTRReport;
		} catch (Exception e) {
			 e.printStackTrace();
			return mTTRReport;
		}

	}
	
	@PostMapping(value = "/getMTTRReports", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MTTRReport getMTTRReports(@RequestBody ReportReqObj reqObj) {
	    MTTRReport mttrReport = new MTTRReport();
	    try {
	        System.out.println("Report For " + reqObj.getMachine());
	        System.out.println("Report By " + reqObj.getReportDuration());

	        int year = Calendar.getInstance().get(Calendar.YEAR);
	        String yearStr = "" + year;
	        DecimalFormat df = new DecimalFormat("#.##");

	        // Month keys
	        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

	        double[] mttrValues = new double[12];
	        double[] targetValues = new double[12];

	        for (int i = 0; i < months.length; i++) {
	            String month = months[i];

	            int noOfBreakdowns = breakdownRepo.getNoOfBreakDownByMachineNameAndMonth(reqObj.getMachine(), month, year);
	            int breakdownCount = breakdownRepo.getCountOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(), month, year);

	            double meanTime = 0;
	            if (breakdownCount > 0 && noOfBreakdowns > 0) {
	                double sumOfTime = breakdownRepo.getSumOfBreakDownTimeByMachineNameAndMonth(reqObj.getMachine(), month, year);
	                meanTime = Double.parseDouble(df.format(sumOfTime / noOfBreakdowns));
	            }

	            double target = Double.parseDouble(getData(reqObj.getMachine(), yearStr, month, "MTTR"));

	            mttrValues[i] = meanTime;
	            targetValues[i] = target;
	        }

	        // Set monthly values
	        mttrReport.setMttrJan(mttrValues[0]); mttrReport.setTargetJan(targetValues[0]);
	        mttrReport.setMttrFeb(mttrValues[1]); mttrReport.setTargetFeb(targetValues[1]);
	        mttrReport.setMttrMar(mttrValues[2]); mttrReport.setTargetMar(targetValues[2]);
	        mttrReport.setMttrApr(mttrValues[3]); mttrReport.setTargetApr(targetValues[3]);
	        mttrReport.setMttrMay(mttrValues[4]); mttrReport.setTargetMay(targetValues[4]);
	        mttrReport.setMttrJun(mttrValues[5]); mttrReport.setTargetJun(targetValues[5]);
	        mttrReport.setMttrJul(mttrValues[6]); mttrReport.setTargetJul(targetValues[6]);
	        mttrReport.setMttrAug(mttrValues[7]); mttrReport.setTargetAug(targetValues[7]);
	        mttrReport.setMttrSep(mttrValues[8]); mttrReport.setTargetSep(targetValues[8]);
	        mttrReport.setMttrOct(mttrValues[9]); mttrReport.setTargetOct(targetValues[9]);
	        mttrReport.setMttrNov(mttrValues[10]); mttrReport.setTargetNov(targetValues[10]);
	        mttrReport.setMttrDec(mttrValues[11]); mttrReport.setTargetDec(targetValues[11]);

	        // Quarterly values
	        mttrReport.setMttr1st(mttrValues[0] + mttrValues[1] + mttrValues[2]);
	        mttrReport.setMttr2nd(mttrValues[3] + mttrValues[4] + mttrValues[5]);
	        mttrReport.setMttr3rd(mttrValues[6] + mttrValues[7] + mttrValues[8]);
	        mttrReport.setMttr4th(mttrValues[9] + mttrValues[10] + mttrValues[11]);

	        mttrReport.setTarget1st(targetValues[0] + targetValues[1] + targetValues[2]);
	        mttrReport.setTarget2nd(targetValues[3] + targetValues[4] + targetValues[5]);
	        mttrReport.setTarget3rd(targetValues[6] + targetValues[7] + targetValues[8]);
	        mttrReport.setTarget4th(targetValues[9] + targetValues[10] + targetValues[11]);

	        return mttrReport;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return mttrReport;
	    }
	}

	
	
/*	@RequestMapping(value = "/getMTTRReport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getMTTRReport(@RequestBody ReportReqObj reqObj ) {
		try {
			
			System.out.println("Report  For "+reqObj.getMachine());
			System.out.println("Report  By "+reqObj.getReportDuration());
			List<Machine> machines= machineRepo.getGetMachinesByName(reqObj.getMachine());
			
			if(reqObj.getReportDuration().equalsIgnoreCase("Monthly")){
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
				int year = Calendar.getInstance().get(Calendar.YEAR);
				Date JanS = formatter.parse("1-Jan-"+year);
				Date JanE = formatter.parse("31-Jan-"+year);
				List<Breakdown> breakdownsJan =breakdownRepo.getBreakDownByMachineNameAndDateBeetween(reqObj.getMachine(),Jan); 

			//	List<Breakdown> breakdownsJan =breakdownRepo.getBreakDownByMachineNameAndDateBeetween(reqObj.getMachine(),JanS,JanE); 
			   	
				for(Breakdown breakdown : breakdownsJan){
			   		
			   	}
				System.out.println("breakdownsJan  "+breakdownsJan.size());
			   	
			   	
			   	
			}
			
			
			

			
			return new Status("maintenance done !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
*/
	
	@PostMapping(value = "/createTaget", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addMachine(@RequestBody Target target) {
		Status status= new Status();
		try {
			if(target.getTarget_id()==0){
				Optional<Target> optional=targetRepo.getTargetByMachineYearnamdMonth(target.getMachineName(), target.getYear(), target.getMonth(), target.getType());
				System.out.println("OPTIONAL "+optional.isPresent()+" "+target.getMachineName()+"  "+target.getYear()+"  "+target.getMonth()+"  "+target.getType());
				if(optional.isPresent()){
					status.setCode(500);
					status.setMessage("Already Exits!");
				}else{
					targetRepo.save(target);
					status.setCode(200);
					status.setMessage("Target added Successfully !");

				}
			}else{
				targetRepo.save(target);
				status.setCode(200);
				status.setMessage("Target Update Successfully !");
			}
			
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@PostMapping(value = "/deleteTaget", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteTaget(@RequestBody Target target) {
		Status status= new Status();
		try {
			targetRepo.delete(target);
			status.setCode(200);
			status.setMessage("Deleted ");
			
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getTargets", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Target> getTargets() {
		List<Target> targets = null;
		try {
			
			targets = targetRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targets;
	}
	
	
	@PostMapping(value = "/addMachineOperation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addMachineOperation(@RequestBody MachienOperation machienOperation) {
		Status status= new Status();
		try {
			if(machienOperation.getMachien_operations_id()==0){
				Optional<MachienOperation> optional=machienOperationRepo.getByMachine(machienOperation.getMachine());
				if(optional.isPresent()){
					status.setCode(500);
					status.setMessage("Already Exits");
				}else{
					status.setCode(200);
					status.setMessage("Saved Successfully");
					machienOperationRepo.save(machienOperation);

				}
			}else{
				status.setCode(200);
				status.setMessage("Update Successfully");
				machienOperationRepo.save(machienOperation);
			}
			
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@PostMapping(value = "/deleteMachineOperation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteMachineOperation(@RequestBody MachienOperation machienOperation) {
		Status status= new Status();
		try {
			
			status.setCode(200);
			status.setMessage("Update Successfully");
			machienOperationRepo.delete(machienOperation);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllMachineOperations", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<MachienOperation> getAllMachineOperations() {
		List<MachienOperation> operations = null;
		try {
			
			operations = machienOperationRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return operations;
	}
	
	
	
	
	
	
	
	
	@GetMapping("/newMttrReport")
	public ResponseEntity<Map<String, Object>> getMTTRReport(
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	        @RequestParam(required = false) Integer machineId) {

	    List<Breakdown> breakdowns = breakdownRepo.findBreakdownsByFilters(fromDate, toDate, machineId);

	    List<Map<String, Object>> tableData = new ArrayList<>();
	    double totalDowntime = 0;
	    int totalRepairs = 0;

	    for (Breakdown b : breakdowns) {
	        if (b.getTicket_closed_time() != null && b.getTicket_raised_time() != null) {
	            double hours = (b.getTicket_closed_time().getTime() - b.getTicket_raised_time().getTime()) / (1000.0 * 60 * 60);
	            totalDowntime += hours;
	            totalRepairs++;

	            Map<String, Object> row = new HashMap<>();
	            row.put("breakdownId", b.getBreakdown_id());
	            row.put("Breakdown Slip Number", b.getBd_slip());
	            row.put("machineId", b.getMachine().getMachine_id());
	            row.put("machineName", b.getMachine().getMachine_name());
	            row.put("LabName", b.getLab().getLabName());
	            row.put("ticketRaised", b.getTicket_raised_time());
	            row.put("ticketClosed", b.getTicket_closed_time());
	            row.put("downtimeHours", String.format("%.2f", hours));
	            tableData.add(row);
	        }
	    }

	    double avgMTTR = totalRepairs > 0 ? totalDowntime / totalRepairs : 0;

	    Map<String, Object> response = new HashMap<>();
	    response.put("data", tableData);
	    response.put("summary", Map.of("totalRepairs", totalRepairs, "avgMTTR", String.format("%.2f", avgMTTR)));

	    return ResponseEntity.ok(response);
	}

	
	
	
	
	@GetMapping("/newMtbfReport")
	public ResponseEntity<Map<String, Object>> getMTBFReport(
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	        @RequestParam(required = false) Integer machineId) {

	    List<Breakdown> breakdowns = breakdownRepo.findBreakdownsByFilters(fromDate, toDate, machineId);
	    breakdowns.sort(Comparator.comparing(Breakdown::getTicket_raised_time));

	    List<Map<String, Object>> tableData = new ArrayList<>();
	    double totalUptime = 0;
	    int failureCount = 0;

	    for (int i = 1; i < breakdowns.size(); i++) {
	        Breakdown prev = breakdowns.get(i - 1);
	        Breakdown current = breakdowns.get(i);

	        if (prev.getTicket_closed_time() != null && current.getTicket_raised_time() != null) {
	            double uptime = (current.getTicket_raised_time().getTime() - prev.getTicket_closed_time().getTime()) / (1000.0 * 60 * 60);
	            totalUptime += uptime;
	            failureCount++;

	            Map<String, Object> row = new HashMap<>();
	            row.put("machineId", current.getMachine().getMachine_id());
	            row.put("breakdownId", current.getBreakdown_id());
	            row.put("machineName", current.getMachine().getMachine_name());
	            row.put("previousBreakdownClosed", prev.getTicket_closed_time());
	            row.put("nextBreakdownRaised", current.getTicket_raised_time());
	            row.put("uptimeHours", String.format("%.2f", uptime));
	            tableData.add(row);
	        }
	    }

	    double avgMTBF = failureCount > 0 ? totalUptime / failureCount : 0;

	    Map<String, Object> response = new HashMap<>();
	    response.put("data", tableData);
	    response.put("summary", Map.of(
	            "totalFailures", failureCount,
	            "averageMTBFHours", String.format("%.2f", avgMTBF)
	    ));

	    return ResponseEntity.ok(response);
	}

	
	
	
	@GetMapping("/newFtrReport")
	public ResponseEntity<Map<String, Object>> getFTRReport(
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	        @RequestParam(required = false) Integer machineId) {

	    List<Breakdown> breakdowns = breakdownRepo.findBreakdownsByFilters(fromDate, toDate, machineId);

	    int totalClosed = 0;
	    int firstTimeRight = 0;
	    List<Map<String, Object>> tableData = new ArrayList<>();

	    for (Breakdown b : breakdowns) {
	        if (b.getStatus() == 3 && b.getTicket_closed_time() != null) {
	            totalClosed++;

	            boolean isFTR = b.getTicket_trial_time() != null;
	            if (isFTR) firstTimeRight++;

	            tableData.add(Map.of(
	                "breakdownId", b.getBreakdown_id(),
	                "machineId", b.getMachine().getMachine_id(),
	                "machineName", b.getMachine().getMachine_name(),
	                "ticketRaised", b.getTicket_raised_time(),
	                "trialDoneTime", b.getTicket_trial_time(),
	                "ticketClosed", b.getTicket_closed_time(),
	                "isFTR", isFTR ? "Yes" : "No"
	            ));
	        }
	    }

	    double ftrPercent = totalClosed > 0 ? ((double) firstTimeRight / totalClosed) * 100 : 0;

	    Map<String, Object> response = Map.of(
	        "summary", Map.of(
	            "totalClosedBreakdowns", totalClosed,
	            "firstTimeRightCount", firstTimeRight,
	            "ftrPercentage", String.format("%.2f", ftrPercent)
	        ),
	        "data", tableData
	    );

	    return ResponseEntity.ok(response);
	}

	

}
