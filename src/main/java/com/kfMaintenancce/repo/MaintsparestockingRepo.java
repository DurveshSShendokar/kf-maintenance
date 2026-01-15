package com.kfMaintenancce.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.MaintSpareStocking;

import com.kfMaintenancce.model.MaintSpareStock;


public interface MaintsparestockingRepo extends JpaRepository<MaintSpareStocking, Integer>{
	@Query("from MaintSpareStocking m where m.maintspare.maintspare_id=?1")
	Optional<MaintSpareStocking> findByMaintSpareId(int maintspare_id);
	
	@Query("SELECT COALESCE(SUM(ms.stockingQuantity), 0) FROM MaintSpareStocking ms WHERE ms.maintspare.maintspare_id = :spareId")
	Double findStockingQuantityByMaintSpareId(@Param("spareId") int spareId);

	

    
   

}
