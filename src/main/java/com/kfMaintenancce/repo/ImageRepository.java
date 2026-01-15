package com.kfMaintenancce.repo;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
	List<Image> findByComplaint(Complaint complaint);
	 
}
