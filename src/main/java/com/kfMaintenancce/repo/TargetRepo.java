package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Target;


public interface TargetRepo extends JpaRepository<Target, Integer>{
	//@Query("select COALESCE(hour, 0) from Target t where t.machineName=?1 and t.year=?2 and t.month=?3 and t.type=?4")
	
//	@Query(value = "FROM target_mst t WHERE t.machine_name = :machineName AND t.year = :year AND t.month = :month AND t.type = :type", nativeQuery = true)
//
//	Optional<Target> getTargetHourByMachineYearnamdMonth(String machineName, String year, String month,String type);
	
	@Query("from Target t where t.machineName=?1 and t.year=?2 and t.month=?3 and t.type=?4")
	Optional<Target> getTargetByMachineYearnamdMonth(String machine, String year, String month,String type);

}
