package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.repo.DepartmentRepo;

@Service
public class DepartmentServiceImpl  implements DepartmentService{
	
	@Autowired
	DepartmentRepo departmentRepo;

	@Override
	public void addDepartment(Department department) {
		// TODO Auto-generated method stub
		departmentRepo.save(department);
	}

//	@Override
//	public void deletDepartment(Department department) {
//		// TODO Auto-generated method stub
//		departmentRepo.delete(department);
//	}
	
	 public void deletDepartment(int departmentId) {
		 departmentRepo.deleteById(departmentId);
	    }

	@Override
	public List<Department> getAllDepartments() {
		// TODO Auto-generated method stub
		return departmentRepo.findAll();
	}
	
	public void addDepartment1(Department department) {
		// TODO Auto-generated method stub
		departmentRepo.save(department);
	}

	
	public List<Department> getDepartmentByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return departmentRepo.getDepartmentByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) departmentRepo.count();
	}

	
	public List<Department> getDepartmentByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return departmentRepo.getDepartmentByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getDepartmentCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return departmentRepo.getDepartmentCountByLimitAndGroupSearch(groupSearchDTO);
	}
	


}
