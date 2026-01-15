package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Department;


public interface DepartmentService {

	void addDepartment(Department department);

//	void deletDepartment(Department department);
	 public void deletDepartment(int departmentId);

	List<Department> getAllDepartments();
	
	void addDepartment1(Department department);

	List<Department> getDepartmentByLimit(int pageNo, int perPage);

	int count();

	List<Department> getDepartmentByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getDepartmentCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	

}
