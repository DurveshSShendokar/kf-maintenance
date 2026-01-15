package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Category;

public interface CategoryRepoCustom {
	
	List<Category> getCategoryByLimit(int pageNo, int perPage);
	List<Category> getCategoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getCategoryCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
