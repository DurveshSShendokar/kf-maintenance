package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.MaintSpare;

public interface MaintSpareRepoCustom {
	
	List<MaintSpare> getMaintSpareByLimit(int pageNo, int perPage);
	List<MaintSpare> getMaintSpareByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMaintSpareCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
