package com.kfMaintenancce.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.hibernate.jpa.internal.ManagedFlushCheckerLegacyJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.FTRReport;
import com.kfMaintenancce.dto.GroupSearchDTO;
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
@RequestMapping("/machine_operation")
public class MachineOperationController {
	@Autowired
	MachineRepo machineRepo;
	
	@Autowired
	BreakdownRepo breakdownRepo;
	@Autowired
	MachienOperationRepo machienOperationRepo;
	
	
	@Autowired
	TargetRepo targetRepo;
	
	

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

	
	

@GetMapping("/getMachienOperationByLimit")
public @ResponseBody List<MachienOperation> getMachienOperationByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<MachienOperation> list = new ArrayList<MachienOperation>();
	try {
		list = machienOperationRepo.getMachienOperationByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geMachienOperationCount")
public @ResponseBody int getMachienOperationCount() {
	int count = 0;
	try {
			count = (int) machienOperationRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getMachienOperationByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<MachienOperation> getMachienOperationByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<MachienOperation> list = new ArrayList<MachienOperation>();
	try {
		
		list = machienOperationRepo.getMachienOperationByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getMachienOperationCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getMachienOperationCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = machienOperationRepo.getMachienOperationCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}
	
	
}
