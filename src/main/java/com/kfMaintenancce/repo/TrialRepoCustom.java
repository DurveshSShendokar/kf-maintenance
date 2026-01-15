package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Trial;

public interface TrialRepoCustom {
	
	List<Trial> getTrialByLimit(int pageNo, int perPage);
	List<Trial> getTrialByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getTrialCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
