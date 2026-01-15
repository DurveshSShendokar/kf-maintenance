package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;

public interface LabRepoCustom {
	
	List<Lab> getLabByLimit(int pageNo, int perPage);
	List<Lab> getLabByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getLabCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
