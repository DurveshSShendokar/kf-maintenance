package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Category;

public interface CategoryService {

	public int getCategoryCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	public List<Category> getCategoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	public int count();
	public List<Category> getCategoryByLimit(int pageNo, int perPage);
	public List<Category> getAllCategories();
	public void deleteCategory(int cat_id);
	public void addCategory(Category category);
	

}
