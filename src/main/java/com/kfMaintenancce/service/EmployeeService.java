package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Employee;

public interface EmployeeService {

	void addEmployee(Employee employee);

	List<Employee> getEmployeeByLimit(int pageNo, int perPage);

	int count();

	List<Employee> getEmployeeByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteEmployee(int employee_id);
	 public List<Employee> getAllemps();

	 public void processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException;
	 public byte[] generateExcelFile() throws IOException;
}
