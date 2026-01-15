package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.MachineDetailsDTO;
import com.kfMaintenancce.dto.MachinechatbotDTO;
import com.kfMaintenancce.model.Machine;

public interface MachineServices {

	void addMachine(Machine machine);
	public List<Machine> getMachineLists(int pageNo, int pageSize);
	List<Machine> getMachineList();
	public List<Machine> getMachineAllList(int page, int size);
	List<Machine> getGetMachinesByName(String machine_name);
	public Page<MachinechatbotDTO> getMachineDTOs(int pageNo, int pageSize);
	void deleteMachine(int machine_id);

	Optional<Machine> getGetMachinesByEquipemtnId(String equipmentId);

	List<Machine> getMachinesByName(String machineName);

	List<Machine> getMachineListByLocation(String location);

	List<Machine> getMachineListByLocationAndMachine(String location, String machineName);
	 public Optional<Machine> getMachineById(int machineId);
	 public MachineDetailsDTO getMachineDetailsWithUserManual(String eqid);
	 public void uploadMachineExcel(MultipartFile file) throws Exception;

}
