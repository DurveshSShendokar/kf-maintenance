package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Spare;

public interface SparesRepoCustom {
	
	List<Spare> getSpareByLimit(int pageNo, int perPage);
	List<Spare> getSpareByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getSpareCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
