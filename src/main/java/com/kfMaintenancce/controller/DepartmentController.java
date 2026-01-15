package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.service.DepartmentService;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;

//	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Status addDepartment(@RequestBody Department department) {
//		try {
//			
//			departmentService.addDepartment(department);
//			return new Status("Consumption Device added Successfully !");
//		} catch (Exception e) {
//			// e.printStackTrace();
//			return new Status(e.toString());
//		}
//
//	}
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status changeStatus(@RequestBody Department department) {
		try {
			if (department.getActive()==1) {
				department.setActive(0);
			}else {
				department.setActive(1);
			}
			
			departmentService.addDepartment(department);
			return new Status("Department status changed Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
//	@RequestMapping(value = "/deletDepartment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Status deletDepartment(@RequestBody Department department) {
//		try {
//			
//			departmentService.deletDepartment(department);
//			return new Status("Department deleted  Successfully !");
//		} catch (Exception e) {
//			// e.printStackTrace();
//			return new Status(e.toString());
//		}
//
//	}
	@GetMapping(value = "/getAllDepartments", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Department> getAllDepartment() {
		List<Department> list = null;
		try {
			
			list = departmentService.getAllDepartments();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/addDepartment1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addDepartment1(@RequestBody Department department ) {
		Status status= new Status();
		try {
			departmentService.addDepartment1(department);
			status.setCode(200);
			status.setMessage("Department is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@RequestMapping(value = "/getDepartmentByLimit", method = RequestMethod.GET)
	public @ResponseBody List<Department> getDepartmentByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Department> list = new ArrayList<Department>();
		try {
			list = departmentService.getDepartmentByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getDepartmentCount", method = RequestMethod.GET)
	public @ResponseBody int getDepartmentCount() {
		int count = 0;
		try {
				count = (int) departmentService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getDepartmentByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Department> getDepartmentByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Department> list = new ArrayList<Department>();
		try {
			
			list = departmentService.getDepartmentByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getDepartmentCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getDepartmentCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = departmentService.getDepartmentCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{departmentId}")
	    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable int departmentId) {
		 departmentService.deletDepartment(departmentId);
	        String message = "Department with ID " + departmentId + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	
	
	 @GetMapping("/all")
	    public ResponseEntity<List<Department>> getAllDepartments() {
	        List<Department> deps = departmentService.getAllDepartments();
	        return new ResponseEntity<>(deps, HttpStatus.OK);
	    }
	

	
}
