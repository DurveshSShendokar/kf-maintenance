package com.kfMaintenancce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.DepartmentRepo;
import com.kfMaintenancce.repo.EmployeeRepo;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.RoleRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.EmployeeService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	UserDetailsRepo userDetailsRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	
	@RequestMapping(value = "/userCreated", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status userCreated(@RequestBody Employee employee ) {
		Status status= new Status();
		try {
			System.out.println("Creating User");
			UserDetails userDetails= new UserDetails();
			userDetails.setContactNo(employee.getMobile());
			userDetails.setEmailId(employee.getEmail());
			//userDetails.setEmpId(employee.getEmployee_id());
			userDetails.setFirstName(employee.getFirstName());
			userDetails.setLastName(employee.getLastName());
			userDetails.setPassword("Reset@123");
			userDetails.setRole(employee.getRole());
			String  idcode=generateRandomCode();
			Optional<UserDetails> optional=userDetailsRepo.getUserById(idcode);
			/*
			 * while (!optional.isPresent()) { idcode=generateRandomCode(); }
			 */
		//	userDetails.setId(idcode);
			employee.setUserCreated(1);
			System.out.println(" User ID  "+idcode);
			employeeService.addEmployee(employee);
			userDetailsRepo.save(userDetails);
			status.setCode(200);
			status.setMessage("User  is Created  successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	public static String generateRandomCode() {
        // Create an instance of Random class
        Random random = new Random();

        // Generate random integer between 1000 and 9999 (inclusive)
        int randomCode = random.nextInt(9000) + 1000;

        // Convert integer to string
        return Integer.toString(randomCode);
    }

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addEmployee(@RequestBody Employee employee ) {
		Status status= new Status();
		try {
			employeeService.addEmployee(employee);
			status.setCode(200);
			status.setMessage("Employee is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@RequestMapping(value = "/getEmployeeByLimit", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getEmployeeByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Employee> list = new ArrayList<Employee>();
		try {
			list = employeeService.getEmployeeByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getEmployeeCount", method = RequestMethod.GET)
	public @ResponseBody int getEmployeeCount() {
		int count = 0;
		try {
				count = (int) employeeService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getEmployeeByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Employee> getEmployeeByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Employee> list = new ArrayList<Employee>();
		try {
			
			list = employeeService.getEmployeeByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getCountAssetByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getAssetCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = employeeService.getAssetCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
//	 @DeleteMapping("/delete/{employee_id}")
//	    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable int employee_id) {
//	        employeeService.deleteEmployee(employee_id);
//	        String message = "Employee with ID " + employee_id + " has been deleted successfully.";
//	        Map<String, String> response = new HashMap<>();
//	        response.put("message", message);
//	        return ResponseEntity.ok(response);
//	    }
	
	@DeleteMapping("/delete/{employee_id}")
	public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable int employee_id) {
	    try {
	        employeeService.deleteEmployee(employee_id);
	        String message = "Employee with ID " + employee_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    } catch (DataIntegrityViolationException ex) {
	        String errorMessage = "Cannot delete employee with ID " + employee_id + " due to existing dependencies.";
	        Map<String, String> response = new HashMap<>();
	        response.put("error", errorMessage);
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	    } catch (Exception ex) {
	        String errorMessage = "An error occurred while deleting the employee.";
	        Map<String, String> response = new HashMap<>();
	        response.put("error", errorMessage);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	/*
	 * @GetMapping("/all") public ResponseEntity<List<UserDetails>> getAllemps() {
	 * List<UserDetails> emps = employeeRepo.getEngineeruserForMachine(); return new
	 * ResponseEntity<>(emps, HttpStatus.OK); }
	 */
	 
	 @GetMapping("/all")
	    public ResponseEntity<List<UserDetails>> getAllemps() {
	        List<UserDetails> emps = employeeRepo.getEngineeruserForMachine();
	        return new ResponseEntity<>(emps, HttpStatus.OK);
	    }
	
	 
	 @PostMapping("/uploadExcel")
	    public ResponseEntity<Status> uploadExcel(@RequestPart("file") MultipartFile file) throws EncryptedDocumentException, InvalidFormatException {
	        Status status = new Status();
	        try {
	            employeeService.processExcelFile(file);
	            status.setCode(200);
	            status.setMessage("Excel file uploaded and processed successfully");
	        } catch (IOException e) {
	            e.printStackTrace();
	            status.setCode(500);
	            status.setMessage("Error occurred while processing the Excel file");
	        }
	        return ResponseEntity.ok(status);
	    }

	 
	 @GetMapping("/exportExcel")
	    public ResponseEntity<byte[]> exportExcel() {
	        try {
	            byte[] excelBytes = employeeService.generateExcelFile();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	            headers.setContentDispositionFormData("attachment", "Employee.xlsx");
	            headers.setContentLength(excelBytes.length);
	            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
