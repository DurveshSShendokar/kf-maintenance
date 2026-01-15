package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Machine;

public interface MachineRepoCustom {
	
	List<Machine> getMachineByLimit(int pageNo, int perPage);
	List<Machine> getMachineByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMachineCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
