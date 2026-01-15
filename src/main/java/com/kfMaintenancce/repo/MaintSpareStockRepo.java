package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.model.SpareStock;


@Repository
public interface MaintSpareStockRepo extends JpaRepository<MaintSpareStock, Integer>{
	
	 @Query("FROM MaintSpareStock ms WHERE ms.maintSpare.maintspare_id = :maintspareId")
	    Optional<MaintSpareStock> getMaintSpareStockByMaintSpare(@Param("maintspareId") int maintspareId);
	 
	  @Query("SELECT m FROM MaintSpareStock m WHERE m.maintSpare.maintspare_id = :maintspareId")
	    MaintSpareStock findByMaintSpareId(@Param("maintspareId") int maintspareId);
	 
	
	

		   
		
	
	  
}