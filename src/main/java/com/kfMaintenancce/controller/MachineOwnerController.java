package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.MachineOwnerDto;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.service.MachineOwnerService;
import com.kfMaintenancce.service.MachineServices;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/machineOwner")
public class MachineOwnerController {
	@Autowired
	MachineOwnerService machineOwnerService;
	@Autowired
	MachineServices machineServices;

	@PostMapping(value = "/addMachineOwner", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addMachineOwner(@RequestBody MachineOwner machineOwner) {
		try {
			List<Machine> list= machineServices.getGetMachinesByName(machineOwner.getMachineName());
			System.out.println("SIZE  "+list.size());
			for(Machine machine:list){
				MachineOwner machineOwner2= new MachineOwner();
				//machineOwner2.setAddedBy(machineOwner.getAddedBy());
				machineOwner2.setAddedDate(machineOwner.getAddedDate());
				machineOwner2.setMachine(machine);
				machineOwner2.setUser(machineOwner.getUser());
				Optional<MachineOwner> optional=machineOwnerService.getAllMachineOwnersByUserAndMachineId(machineOwner.getUser().getId(),machine.getMachine_id());
				
				if(!optional.isPresent()){
					machineOwnerService.addMachineOwner(machineOwner2);

				}
			}
			//System.out.println("addd"+machineOwner.toString());
		//	
			return new Status("Machine Owner added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@PostMapping(value = "/addMachineOwners", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addMachineOwners(@RequestBody MachineOwner machineOwner) {
	    try {
	        // Get the machine using machine_id from machineOwner.machine.machine_id
	        Optional<Machine> machineOptional = machineServices.getMachineById(machineOwner.getMachine().getMachine_id());
	        if (!machineOptional.isPresent()) {
	            return new Status("Machine not found for id: " + machineOwner.getMachine().getMachine_id());
	        }
	        Machine machine = machineOptional.get();

	        Optional<MachineOwner> existing = machineOwnerService
	            .getAllMachineOwnersByUserAndMachineId(machineOwner.getUser().getId(), machine.getMachine_id());

	        if (existing.isPresent()) {
	            return new Status("This user is already assigned to the selected machine.");
	        }

	        MachineOwner machineOwnerToSave = new MachineOwner();
	        machineOwnerToSave.setMachine(machine);
	        machineOwnerToSave.setUser(machineOwner.getUser());
	        machineOwnerToSave.setAddedDate(machineOwner.getAddedDate());

	        machineOwnerService.addMachineOwner(machineOwnerToSave);

	        return new Status("Machine Owner added Successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}

	
	@RequestMapping(value = "/deletMachineOwner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deletMachineOwner(@RequestBody MachineOwner department) {
		try {
			
			machineOwnerService.deletMachineOwner(department);
			return new Status("Machine Owner deleted  Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllMachineOwners", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<MachineOwner> getAllMachineOwners() {
		List<MachineOwner> list = null;
		try {
			
			list = machineOwnerService.getAllMachineOwners();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping(value = "/getAllMachineOwners1", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Set<MachineOwnerDto> getAllMachineOwners1() {
		Set<MachineOwnerDto> list = new HashSet<MachineOwnerDto>();
		Set<Integer> users = new HashSet<Integer>();
		try {
			
			List<MachineOwner> list1 = machineOwnerService.getAllMachineOwners();
			for(MachineOwner owner:list1){
				/*MachineOwnerDto dto= new MachineOwnerDto();
				String machineName=owner.getMachine().getMachine_name();
				String userName=owner.getUser().getFirstName()+" "+owner.getMachineName();
				dto.setMachineName(machineName);
				dto.setUserName(userName);*/
			users.add(owner.getUser().getId());
				//list.add(dto);
			}
			for(Integer userId:users){
				List<MachineOwner> machineOwners= machineOwnerService.getAllMachineOwnersByUser(userId);
				for(MachineOwner machineOwner:machineOwners){
					MachineOwnerDto dto= new MachineOwnerDto();
					String machineName=machineOwner.getMachine().getMachine_name();
					String userName=machineOwner.getUser().getFirstName()+" "+machineOwner.getMachineName();
					dto.setMachineName(machineName);
					dto.setUserName(userName);
					list.add(dto);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
