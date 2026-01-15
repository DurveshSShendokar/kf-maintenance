package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Maint;

public interface MaintRepoCustom {
	
	List<Maint> getMaintByLimit(int pageNo, int perPage);
	List<Maint> getMaintByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getMaintCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
