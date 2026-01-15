package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Department;

public interface DepartmentRepoCustom {
	
	List<Department> getDepartmentByLimit(int pageNo, int perPage);
	List<Department> getDepartmentByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getDepartmentCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
