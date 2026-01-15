package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.MaintImage;

@Repository
public interface MaintImageRepo extends JpaRepository<MaintImage, Integer> {

	
	@Query("SELECT mi FROM MaintImage mi WHERE mi.maint.maint_id = :maintId")
	List<MaintImage> findByMaintId(@Param("maintId") int maintId);

}
