package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.ComplaintEnginneersAllocations;

public interface ComplaintEnginneersAllocationsRepo extends JpaRepository<ComplaintEnginneersAllocations, Integer> {
	@Query("from ComplaintEnginneersAllocations c where c.complaint.comp_id=?1 and c.status='Allocated'")
	Optional<ComplaintEnginneersAllocations> getAllocatedByComplaintID(int comp_id);

	@Query("SELECT c FROM ComplaintEnginneersAllocations c WHERE c.complaint.comp_id = :complaintId AND c.status = 'Allocated'")
	List<ComplaintEnginneersAllocations> findByComplaintId(@Param("complaintId") int complaintId);

	 @Query("SELECT c FROM ComplaintEnginneersAllocations c WHERE c.status = :status")
	    List<ComplaintEnginneersAllocations> findByStatus(@Param("status") String status);
	 
	 @Query("SELECT c FROM ComplaintEnginneersAllocations c WHERE c.status = :status")
	 Page<ComplaintEnginneersAllocations> findByStatusd(@Param("status") String status, Pageable pageable);
	 
	 @Query("SELECT c FROM ComplaintEnginneersAllocations c " +
		       "WHERE c.status = :status AND " +
		       "(LOWER(c.complaint.ticketNo) LIKE %:keyword% OR " +
		       "LOWER(c.complaint.assetInventory.lab.labName) LIKE %:keyword% OR " +
		       "LOWER(c.status) LIKE %:keyword% OR " +
		       "LOWER(c.engineer.userName) LIKE %:keyword%)")
		Page<ComplaintEnginneersAllocations> findByStatusAndSearchd(
		        @Param("status") String status,
		        @Param("keyword") String keyword,
		        Pageable pageable);

	 
	 @Query("SELECT COUNT(c) FROM ComplaintEnginneersAllocations c WHERE c.status = :status")
	 long countByStatus(@Param("status") String status);

	 @Query("SELECT COUNT(c) FROM ComplaintEnginneersAllocations c " +
	        "WHERE c.status = :status AND " +
	        "(LOWER(c.complaint.ticketNo) LIKE %:keyword% OR " +
	        "LOWER(c.complaint.assetInventory.lab.labName) LIKE %:keyword% OR " +
	        "LOWER(c.status) LIKE %:keyword% OR " +
	        "LOWER(c.engineer.userName) LIKE %:keyword%)")
	 long countByStatusAndSearch(@Param("status") String status, @Param("keyword") String keyword);



}
