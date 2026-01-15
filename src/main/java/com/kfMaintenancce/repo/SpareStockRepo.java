package com.kfMaintenancce.repo;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareStock;


@Repository
public interface SpareStockRepo extends JpaRepository<SpareStock, Integer>, SpareStockRepoCustom{
	
	// To get the total quantity for each spare from the SpareStock
	@Query("SELECT SUM(ss.availableQuantity) FROM SpareStock ss WHERE ss.spare.spare_id = :spareId")
	Double getTotalStockBySpareId(@Param("spareId") int spareId);

    
    @Query("FROM SpareStock ss WHERE ss.spare.spare_id = :spareId")
    Optional<SpareStock> getSpareStockBySpare(@Param("spareId") int spareId);
    
    Optional<SpareStock> findBySpare(Spare spare);
    
    // 🔹 Search + Pagination
    @Query("SELECT s FROM SpareStock s WHERE LOWER(s.spare.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SpareStock> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 🔹 Count + Search
    @Query("SELECT COUNT(s) FROM SpareStock s WHERE LOWER(s.spare.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    long countByKeyword(@Param("keyword") String keyword);
    
   

	
	  
}