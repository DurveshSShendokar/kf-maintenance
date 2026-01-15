package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Breakdown;

public interface BreakdownRepoCustom {
	
	List<Breakdown> getBreakdownByLimit(int pageNo, int perPage);
	List<Breakdown> getBreakdownByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getBreakdownCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
