package com.kfMaintenancce.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineMaintSpare;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MachineSpareAssignRepository;


@Service
public class MachineSpareAssignServiceImpl  implements MachineSpareAssignService{

	
	
	 @Autowired
	    private MachineSpareAssignRepository machineSpareAssignRepository;
	 
	 @Autowired
	    private MachineRepo machineRepo;


    public void addSpareAssign(MachineMaintSpare MachinespareAssign) {
    	machineSpareAssignRepository.save(MachinespareAssign);
    }	 
    public Machine findById(int machine_id) {
        Optional<Machine> machineOptional = machineRepo.findById(machine_id);
        return machineOptional.orElse(null);
    }
    
    public void deleteMachineSpareAssignById(int machine_id) {
        if (machineSpareAssignRepository.existsById(machine_id)) {
        	machineSpareAssignRepository.deleteById(machine_id);
        } else {
            throw new RuntimeException("MachineSpareAssign with ID " + machine_id + " not found");
        }
    }


}
