package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.DayWiseConsumption;

public interface DayWiseConsumptionRepo extends JpaRepository<DayWiseConsumption, Integer> {
	@Query("from DayWiseConsumption d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2  and Date(d.consumptionDate)=?3  ORDER BY d.id DESC")
	Optional<DayWiseConsumption> getConsumptionByMeterIDAndDateAndModule(int id, int id2, Date yesterday);
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) FROM DayWiseConsumption d WHERE d.energyMeterMaster.id = ?1 AND MONTH(d.consumptionDate) = ?2 AND YEAR(d.consumptionDate) = ?3")
	String getSumConsumptionByMeterYearAndMonth(int id, int i, int year);
	@Query("SELECT COUNT(d) from DayWiseConsumption d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2  and Date(d.consumptionDate)=?3  ORDER BY d.id DESC")

	int getCountConsumptionByMeterIDAndDateAndModule(int id, int id2, Date date);
	
	
	@Query("from DayWiseConsumption d where  d.energyMeterRegister.registerNo=?1  and Date(d.consumptionDate)=?2  ORDER BY d.id DESC")
	Optional<DayWiseConsumption>  getConssumptionByModuleNoAndDate(String registerModuleNo, Date date);
	@Query("from DayWiseConsumption d where  d.energyMeterRegister.registerNo=?1  and d.energyMeterMaster.id = ?2 and Date(d.consumptionDate)=?3  ORDER BY d.id DESC")

	Optional<DayWiseConsumption> getConssumptionByModuleNoAndDateAndMeterId(String powerFactorRegisterNo, int id,
			Date date);
	
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) from DayWiseConsumption d where  d.energyMeterRegister.registerNo=?1  and d.energyMeterMaster.id = ?2 and  MONTH(d.consumptionDate) = ?3 AND YEAR(d.consumptionDate) = ?4  ORDER BY d.id DESC")

	String getConssumptionByModuleNoAndMonth(String apparentRegisterNo, int meterId,int month, int year);
	
    @Query("SELECT MAX(d.registerValue) FROM DayWiseConsumption d WHERE d.energyMeterMaster.id=?1 and d.energyMeterRegister.id =?2 and Date(d.consumptionDate)=?3")
	String getMaxValueByDateMeterAndModule(int meterId, int id, Date date);
    
    @Query("SELECT MAX(d.registerValue) FROM DayWiseConsumption d WHERE d.energyMeterMaster.id=?1 and d.energyMeterRegister.registerNo =?2 and Date(d.consumptionDate)=?3")

    
	String getMaxValueByDateMeterAndModuleRegisterNo(int meterId, String string, Date date);
    
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) FROM DayWiseConsumption d WHERE d.energyMeterRegister.registerNo = ?1 AND MONTH(d.consumptionDate) = ?2 AND YEAR(d.consumptionDate) = ?3")

	String getSumConsumptionByRegisterYearAndMonth(String registerModuleNo, int month, int year);
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) FROM DayWiseConsumption d where d.energyMeterRegister.registerNo = ?1 and Date(d.consumptionDate)=?2")
	double getTotalConsumptionByDate(String registerNo,Date daysBefore);
	@Query("SELECT COALESCE(SUM(d.registerValue), 0) FROM DayWiseConsumption d ")

	String getSumConsumption(String string);
	@Query("select DISTINCT(d.energyMeterRegister.id) from DayWiseConsumption d where Date(d.consumptionDate)=?1")

	List<Integer> getActiveMeterByDate(Date date);
	@Query(" from DayWiseConsumption d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2  and Date(d.consumptionDate)=?3")
	Optional<DayWiseConsumption> getConsumptionByMeterIDAndDateAndRegisterId(int meterid, int registerId, Date date);
	
	@Query("SELECT d FROM DayWiseConsumption d WHERE d.energyMeterMaster.id = ?1 ORDER BY d.id DESC")
	List<DayWiseConsumption> getLastConsumptionByMeterID(int materId, Pageable pageable);

}
