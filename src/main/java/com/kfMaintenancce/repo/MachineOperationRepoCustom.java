package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.MachienOperation;

public interface MachineOperationRepoCustom {
	
	List<MachienOperation> getMachienOperationByLimit(int pageNo, int perPage);
	List<MachienOperation> getMachienOperationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMachienOperationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
