package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;

public interface MachineOwnerRepoCustom {
	
	List<MachineOwner> getMachineOwnerByLimit(int pageNo, int perPage);
	List<MachineOwner> getMachineOwnerByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMachineOwnerCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
