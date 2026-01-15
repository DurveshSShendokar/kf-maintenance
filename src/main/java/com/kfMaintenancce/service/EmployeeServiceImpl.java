package com.kfMaintenancce.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.repo.DepartmentRepo;
import com.kfMaintenancce.repo.EmployeeRepo;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.RoleRepo;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;	
	
	@Autowired
	private LabRepo labRepository;
	
	@Autowired
	private RoleRepo roleRepository;
	
	@Autowired
	private DepartmentRepo departmentRepository;

	@Override
	public void addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepo.save(employee);
	}

	@Override
	public List<Employee> getEmployeeByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) employeeRepo.count();
	}

	@Override
	public List<Employee> getEmployeeByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return employeeRepo.getAssetCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	public void deleteEmployee(int employee_id) {
	    if (employeeRepo.existsById(employee_id)) {
	        try {
	            employeeRepo.deleteById(employee_id);
	        } catch (DataIntegrityViolationException ex) {
	            throw new DataIntegrityViolationException("Cannot delete employee due to foreign key constraint.");
	        }
	    } else {
	        throw new IllegalArgumentException("Employee with ID " + employee_id + " does not exist.");
	    }
	}


	    public List<Employee> getAllemps() {
	        return employeeRepo.findAll();
	    } 
	    
	    @Transactional
		 public void processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {
		     Workbook workbook = WorkbookFactory.create(file.getInputStream());
		     Sheet sheet = workbook.getSheetAt(0); 

		     for (Row row : sheet) {
		         // Skip header row
		         if (row.getRowNum() == 0) continue;

		         Employee employee = new Employee();
		         employee.setFirstName(getStringValue(row.getCell(1)));
		         employee.setLastName(getStringValue(row.getCell(2)));
		         employee.setEmail(getStringValue(row.getCell(3)));
		         employee.setMobile(getStringValue(row.getCell(4)));
		         employee.setManager(getStringValue(row.getCell(5)));
		         employee.setRole(getRole(getStringValue(row.getCell(6))));
		         employee.setDepartment(getDepartment(getStringValue(row.getCell(7))));
		         employee.setLab(getLab(getStringValue(row.getCell(8))));
		         
		         employeeRepo.save(employee);
		     }
		     workbook.close();
		 }

		 
		 private Role getRole(String roleName) {
			 Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
			 if (roleOptional.isPresent()) {
		            return roleOptional.get();
		        } else {
		            throw new RuntimeException("Role not found for name: " + roleName);
		        }	        }

			private Department getDepartment(String departmentName) {
				Optional<Department> departmentOptional = departmentRepository.findByDepartmentName(departmentName);
				 if (departmentOptional.isPresent()) {
			            return departmentOptional.get();
			        } else {
			            throw new RuntimeException("department not found for name: " + departmentName);
			        }		}

			private Lab getLab(String labName) {
				 Optional<Lab> labOptional = labRepository.findByLabName(labName);
				 if (labOptional.isPresent()) {
			            return labOptional.get();
			        } else {
			            throw new RuntimeException("Lab not found for name: " + labName);
			        }
			        }	 
		 
		 private String getStringValue(Cell cell) {
		     if (cell == null) {
		         return null;
		     }
		     cell.setCellType(CellType.STRING); // Ensure cell is treated as string
		     return cell.getStringCellValue();
		 }
		 
		 
		 public byte[] generateExcelFile() throws IOException {
			    List<Employee> employeeList = employeeRepo.findAll();
			    Workbook workbook = new XSSFWorkbook();
			    Sheet sheet = workbook.createSheet(" Employee");
			    int rowNum = 0;

			    // Create the header row
			    Row headerRow = sheet.createRow(rowNum++);
			    String[] headers = {"Sr. No.", "FirstName", "LastName", "Email", "Mobile Number", "Role", "Manager", "Lab", "Department"};
			    for (int i = 0; i < headers.length; i++) {
			        headerRow.createCell(i).setCellValue(headers[i]);
			    }

			    // Populate data rows
			    int srNo = 1;
			    for (Employee employee : employeeList) {
			        Row row = sheet.createRow(rowNum++);
			        // Populate Sr. No.
			        row.createCell(0).setCellValue(srNo++);
			        // Populate other columns with asset inventory data
			        row.createCell(1).setCellValue(employee.getFirstName());
			        row.createCell(2).setCellValue(employee.getLastName());
			        row.createCell(3).setCellValue(employee.getEmail());
			        row.createCell(4).setCellValue(employee.getMobile());			       
			        row.createCell(5).setCellValue(employee.getRole().getRoleName());
			        row.createCell(6).setCellValue(employee.getManager());
			        row.createCell(7).setCellValue(employee.getLab().getLabName());
			        row.createCell(8).setCellValue(employee.getDepartment().getDepartmentName());
			    }

			    // Auto-size columns
			    for (int i = 0; i < headers.length; i++) {
			        sheet.autoSizeColumn(i);
			    }

			    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			    workbook.write(outputStream);
			    workbook.close();
			    return outputStream.toByteArray();
			}



		 


}
