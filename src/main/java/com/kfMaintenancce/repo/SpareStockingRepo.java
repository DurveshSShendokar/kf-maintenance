package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareStocking;

public interface SpareStockingRepo extends JpaRepository<SpareStocking, Integer>{
	
	 @Query("SELECT ss FROM SpareStocking ss WHERE ss.spare.spare_id = :spareId")
	    Optional<SpareStocking> findBySpareId(@Param("spareId") int spareId);
	 
	 @Query(value = "SELECT * FROM spare_stoccking ORDER BY spare_stocking_id DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	 List<SpareStocking> findSpareStockingByLimit(@Param("offset") int offset, @Param("limit") int limit);


	 @Query("SELECT s FROM SpareStocking s WHERE s.spare = :spare ORDER BY s.spare_stocking_id DESC")
	 List<SpareStocking> findBySpare(@Param("spare") Spare spare);

}
