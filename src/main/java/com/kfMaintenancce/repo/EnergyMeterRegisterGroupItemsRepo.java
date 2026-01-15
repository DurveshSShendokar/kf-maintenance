package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kfMaintenancce.model.EnergyMeterRegisterGroup;
import com.kfMaintenancce.model.EnergyMeterRegisterGroupItems;

public interface EnergyMeterRegisterGroupItemsRepo extends JpaRepository<EnergyMeterRegisterGroupItems, Integer>{
	@Query("From EnergyMeterRegisterGroupItems e where e.group.id=?1")
	List<EnergyMeterRegisterGroupItems> getEnergyMeterRegisterGroupItemsByGroup(int id);
	
	 @Modifying
	    @Transactional
	    @Query("DELETE FROM EnergyMeterRegisterGroupItems e WHERE e.group = :group")
	    void deleteAllByGroup(@Param("group") EnergyMeterRegisterGroup group);

}
