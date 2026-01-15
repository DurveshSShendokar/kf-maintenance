package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Checklist;

public interface ChecklistRepoCustom {
	
	List<Checklist> getChecklistByLimit(int pageNo, int perPage);
	List<Checklist> getChecklistByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getChecklistCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
