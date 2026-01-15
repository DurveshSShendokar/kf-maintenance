package com.kfMaintenancce.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.BreakDownUpdateMobileApp;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Category;

import com.kfMaintenancce.repo.CategoryRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.CategoryService;


@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	CategoryService categoryService;

	
	@GetMapping("/listByIT")
	public @ResponseBody List<Category> getCategoriesByIT() {
	    try {
	        return categoryRepo.findByDepartmentName("IT");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}

	@GetMapping("/listByMaintenance")
	public @ResponseBody List<Category> getCategoriesByMaintenance() {
	    try {
	        return categoryRepo.findByDepartmentName("Maintenance");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}

	
	
	@GetMapping(value = "/list")
	public @ResponseBody
	List<Category> Category() {
		List<Category> list = null;
		try {
			
			list = categoryRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addCategory(@RequestBody Category category ) {
		Status status= new Status();
		try {
			categoryService.addCategory(category);
			status.setCode(200);
			status.setMessage("Category is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@RequestMapping(value = "/getCategoryByLimit", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCategoryByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Category> list = new ArrayList<Category>();
		try {
			list = categoryService.getCategoryByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCategoryCount", method = RequestMethod.GET)
	public @ResponseBody int getCategoryCount() {
		int count = 0;
		try {
				count = (int) categoryService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getCategoryByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Category> getCategoryByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Category> list = new ArrayList<Category>();
		try {
			
			list = categoryService.getCategoryByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getCategoryCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getCategoryCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = categoryService.getCategoryCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{cat_id}")
	    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable int cat_id) {
	        categoryService.deleteCategory(cat_id);
	        String message = "Category with ID " + cat_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	
	
	
	
	
	
}
