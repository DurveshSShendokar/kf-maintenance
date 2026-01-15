package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.Department;


public interface DepartmentRepo  extends JpaRepository<Department, Integer>,DepartmentRepoCustom{
	Optional<Department> findByDepartmentName(String departmentName);

}
