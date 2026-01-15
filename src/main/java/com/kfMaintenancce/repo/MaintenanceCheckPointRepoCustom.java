package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;

public interface MaintenanceCheckPointRepoCustom {
	
	List<MaintenenaceCheckPoint> getMaintenenaceCheckPointByLimit(int pageNo, int perPage);
	List<MaintenenaceCheckPoint> getMaintenenaceCheckPointByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMaintenenaceCheckPointCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
