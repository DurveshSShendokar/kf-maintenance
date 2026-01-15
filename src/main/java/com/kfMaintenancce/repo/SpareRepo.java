package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Spare;

public interface SpareRepo  extends JpaRepository<Spare, Integer>,SparesRepoCustom{
	
	
	
	 // 🔹 Pagination + Search
    @Query("SELECT s FROM Spare s WHERE LOWER(s.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Spare> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 🔹 Count + Search
    @Query("SELECT COUNT(s) FROM Spare s WHERE LOWER(s.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    long countByKeyword(@Param("keyword") String keyword);
	}

	



