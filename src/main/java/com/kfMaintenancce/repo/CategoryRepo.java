package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>, CategoryRepoCustom{
	
	Optional<Category> findByCatName(String catName);
	
	 @Query("FROM Category c WHERE catName = ?1")
	    List<Category> findAllByCategoryName(String catName);
	 
	  @Query("SELECT c FROM Category c WHERE c.catName = :name")
	    Category findByName(@Param("name") String name);
	  
	  @Query("SELECT c FROM Category c WHERE c.department.departmentName = :departmentName AND c.deletes = 1")
	  List<Category> findByDepartmentName(@Param("departmentName") String departmentName);

}
