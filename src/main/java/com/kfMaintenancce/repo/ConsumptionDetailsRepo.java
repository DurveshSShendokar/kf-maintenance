package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.ConsumptionDetails;
import com.kfMaintenancce.model.DailyConsumptionLog;

public interface ConsumptionDetailsRepo extends JpaRepository<ConsumptionDetails, Integer>{
	@Query("select DISTINCT(d.energyMeterRegister.id) from ConsumptionDetails d where d.energyMeterMaster.id=?1")
	List<Integer> getDistinceRegisterIDByMeter(int meterId);
	@Query("from ConsumptionDetails d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2")

	Optional<ConsumptionDetails> getConsumptionByMeterIDAndRegisterId(int meterId, Integer registerId);
	
	
	@Query("SELECT d.registerValue from ConsumptionDetails d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.registerNo=?2  ORDER BY d.id DESC")

	String getMaxValueByDateMeterAndModuleRegisterNo(int meterId, String string);
	@Query("SELECT  COALESCE(d.registerValue, 0)  from ConsumptionDetails d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2  ORDER BY d.id DESC")

	String getMaxValueByDateMeterAndModule(int meterId, int id);
	
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) from ConsumptionDetails d where  d.energyMeterRegister.registerNo=?1  and d.energyMeterMaster.id = ?2 and  MONTH(d.createDateTime) = ?3 AND YEAR(d.createDateTime) = ?4  ORDER BY d.id DESC")

	String getSumConsumptionByMeterYearAndMonth(String registerModuleNo,int meterId, int month, int year);
	@Query("SELECT d FROM ConsumptionDetails d WHERE d.energyMeterMaster.id = ?1 ORDER BY d.id DESC")

	List<ConsumptionDetails> getLastConsumptionByMeterID( int meterId, Pageable pageable);

}
