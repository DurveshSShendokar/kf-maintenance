package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.UserDetails;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> ,EmployeeCustomeRepo{

	@Query("From UserDetails u where u.role.roleName='Engineer' and department.departmentName='IT' ")
	List<UserDetails> getEngineeruserForMachine();

}
