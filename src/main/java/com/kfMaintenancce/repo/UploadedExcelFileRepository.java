package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.UploadedExcelFile;

@Repository
public interface UploadedExcelFileRepository extends JpaRepository<UploadedExcelFile, Long> {
	
	
	@Query("SELECT u FROM UploadedExcelFile u WHERE u.lab.lab_id = :labId")
	List<UploadedExcelFile> findByLabId(@Param("labId") int labId);
	
	  @Query("SELECT u FROM UploadedExcelFile u WHERE u.lab.id = :labId ORDER BY u.uploadedAt DESC")
	    List<UploadedExcelFile> findByLabIdOrderByUploadedAtDesc(@Param("labId") int labId);

}



