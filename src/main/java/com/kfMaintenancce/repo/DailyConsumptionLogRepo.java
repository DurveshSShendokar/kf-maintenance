package com.kfMaintenancce.repo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.DailyConsumptionLog;
import com.kfMaintenancce.model.EnergyMeterRegister;

public interface DailyConsumptionLogRepo extends JpaRepository<DailyConsumptionLog, Integer> {
	@Query("from DailyConsumptionLog d where d.energyMeterMaster.id=?1 and d.panel.id=?2 and Date(d.createDateTime)=?3 and d.energyMeterRegister.registerName='Current' and d.otherDetails='success'")
	Optional<DailyConsumptionLog> getTodayConsumptionByMeterIDAndPanelId(int id, int id2, Date date);
	@Query("from DailyConsumptionLog d where d.energyMeterRegister.id=?1 and Date(d.createDateTime)=?2 and d.otherDetails='success'")
	Optional<DailyConsumptionLog> getConsumptionByMeterIDAndDate(int id, Date date);
	@Query("select SUM(d.registerValue) from DailyConsumptionLog d where d.energyMeterMaster.id=?1  and month(d.createDateTime)=?2 and year(d.createDateTime)=?3 and d.otherDetails='success'")
	String getSumConsumptionByMeterYearAndMonth(int id, int i, int year);
	@Query("select DISTINCT(d.energyMeterRegister.id) from DailyConsumptionLog d where d.energyMeterMaster.id=?1")

	List<Integer> getDistinceRegisterIDByMeter(int meterId);
	@Query("from DailyConsumptionLog d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.id=?2 and Date(d.createDateTime)=?3   ORDER BY d.id DESC")

	List<DailyConsumptionLog> getConsumptionByMeterIDAndDateAndRegisterId(int meterId, Integer registerId,
			Date date);
	@Query("from DailyConsumptionLog d where d.energyMeterMaster.id=?1 and d.energyMeterRegister.registerNo=?2  and Date(d.createDateTime)=?3 and d.otherDetails='success'  ORDER BY d.id DESC")

	List<DailyConsumptionLog> getConsumptionByMeterIDAndDateAndModule(int meterId, String registerModuleNo,Date date);
	/*@Query("from DailyConsumptionLog d where  Date(d.createDateTime)=?1 and Time(d.createDateTime)=?2")

	List<DailyConsumptionLog> getValueByDateAndTime(Date date, Date timeMinus30Minutes);*/
   // @Query("from DailyConsumptionLog d where d.energyMeterRegister.id :=registerId   and  FUNCTION('TIME', d.createDateTime) BETWEEN :startTime AND :endTime")
    @Query("FROM DailyConsumptionLog d WHERE d.energyMeterRegister.id = :registerId AND FUNCTION('TIME', d.createDateTime) BETWEEN :startTime AND :endTime")

	List<DailyConsumptionLog> getValueByDateAndTime(@Param("registerId") int registerId ,@Param("startTime") String startTime,@Param("endTime") String endTime);
    
    @Query("SELECT COALESCE(MAX(d.registerValue), 0)  FROM DailyConsumptionLog d WHERE d.energyMeterRegister.id = :registerId AND FUNCTION('TIME', d.createDateTime) BETWEEN :startTime AND :endTime")
	String getMaxValueByDateAndTime(@Param("registerId") int registerId ,@Param("startTime") String startTime,@Param("endTime") String endTime);
    
    @Query("SELECT COALESCE(MAX(d.registerValue), 0)  FROM DailyConsumptionLog d WHERE d.energyMeterMaster.id=?1 and d.energyMeterRegister.id =?2 and Date(d.createDateTime)=?3")
	String getMaxValueByDateMeterAndModule(int meterId, int id, Date date);
    
    @Query("SELECT COALESCE(MAX(d.registerValue), 0)  FROM DailyConsumptionLog d WHERE d.energyMeterMaster.id=?1 and d.energyMeterRegister.registerNo =?2 and Date(d.createDateTime)=?3")

	String getMaxValueByDateMeterAndModuleRegisterNo(int meterId, String string, Date date);
    
    
	@Query("SELECT COALESCE(MAX(d.registerValue), 0)  from DailyConsumptionLog d  where  d.energyMeterRegister.registerNo=?1  and Date(d.createDateTime)=?2")
	String getConssumptionByModuleNoAndDate(String apparentRegisterNo, Date date);
    @Query("SELECT  COALESCE(MAX(d.registerValue), 0)  FROM DailyConsumptionLog d WHERE d.energyMeterRegister.id = :registerId AND d.energyMeterMaster.id=:meterId AND FUNCTION('TIME', d.createDateTime) BETWEEN :startTime AND :endTime")
   
	String getMaxValueByMeeterRegDateAndTime(@Param("registerId") int registerId ,@Param("meterId") int meterId ,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Query("SELECT r FROM DailyConsumptionLog r WHERE r.energyMeterMaster.id = :meterId ORDER BY r.createDateTime DESC")
 List<DailyConsumptionLog> getLastConsumptionByMeterID(@Param("meterId") int meterId);
    @Query("from DailyConsumptionLog d where Date(d.createDateTime) < ?1")

	List<DailyConsumptionLog> getConsumptionByDate(Date date, Pageable pageable);
	@Query("select DISTINCT(d.energyMeterRegister.id) from DailyConsumptionLog d where Date(d.createDateTime)=?1")

	List<Integer> getActiveMeterByDate(Date date);
    @Query("SELECT MAX(d.registerValue)  FROM DailyConsumptionLog d WHERE   d.energyMeterRegister.registerNo =?1 and Date(d.createDateTime)=?2")
	String getMaxValueByDate(String registerNo, Date monthdDayDate);
    @Query("SELECT MIN(d.registerValue)  FROM DailyConsumptionLog d WHERE   d.energyMeterRegister.registerNo =?1 and Date(d.createDateTime)=?2 AND d.registerValue <> '-'")

	String getMinValueByDate(String registerNo, Date monthdDayDate);
    @Query(" FROM DailyConsumptionLog d WHERE   d.energyMeterRegister.registerNo =?1 and Date(d.createDateTime)=?2")

	List<DailyConsumptionLog> getByDate(String registerNo,Date monthdDayDate);
    @Query(" FROM DailyConsumptionLog d WHERE   d.energyMeterMaster.id=?1  and Date(d.createDateTime)=?2")
	List<DailyConsumptionLog> getLastConsumptionByMeterID(int materId, Pageable pageable);
    

}
