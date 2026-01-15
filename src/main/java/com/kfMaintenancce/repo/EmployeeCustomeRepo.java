package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Employee;

public interface EmployeeCustomeRepo {
	List<Employee> getEmployeeByLimit(int pageNo, int perPage);

	List<Employee> getEmployeeByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
}
