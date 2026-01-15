package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;

import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.repo.CategoryRepo;

@Service
public class CategoryServiceImpl  implements CategoryService{
	
	@Autowired
	CategoryRepo categoryRepo;
	
	
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		categoryRepo.save(category);
	}

	
	 public void deleteCategory(int cat_id) {
	        categoryRepo.deleteById(cat_id);
	    }

	
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}
	
	

	
	public List<Category> getCategoryByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) categoryRepo.count();
	}

	
	public List<Category> getCategoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryByLimitAndGroupSearch(groupSearchDTO);
	}

	
	public int getCategoryCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryCountByLimitAndGroupSearch(groupSearchDTO);
	}
	


}
