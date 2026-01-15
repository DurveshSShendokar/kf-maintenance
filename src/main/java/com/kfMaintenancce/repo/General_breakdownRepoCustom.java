package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.General_breakdown;

public interface General_breakdownRepoCustom {
	
	List<General_breakdown> getGeneral_breakdownByLimit(int pageNo, int perPage);
	List<General_breakdown> getGeneral_breakdownByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getGeneral_breakdownCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
