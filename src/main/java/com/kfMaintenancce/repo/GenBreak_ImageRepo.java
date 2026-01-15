package com.kfMaintenancce.repo;

import com.kfMaintenancce.model.General_breakdown;
import com.kfMaintenancce.model.genbreak_Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenBreak_ImageRepo extends JpaRepository<genbreak_Image, Integer> {
	
	 @Query("SELECT g FROM genbreak_Image g WHERE g.General_breakdown.genbreak_id = :genbreakId")
	    List<genbreak_Image> findByGeneral_breakdown_Genbreak_id(@Param("genbreakId") int genbreakId);
//	 List<genbreak_Image> findByGeneral_breakdown_Genbreak_id(int genbreak_id);
}
