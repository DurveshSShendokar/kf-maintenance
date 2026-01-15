package com.kfMaintenancce.service;



import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineMaintSpare;


public interface MachineSpareAssignService {

	  public void addSpareAssign(MachineMaintSpare MachinespareAssign);
	  public Machine findById(int machine_id) ;
	  public void deleteMachineSpareAssignById(int machine_id);

}
