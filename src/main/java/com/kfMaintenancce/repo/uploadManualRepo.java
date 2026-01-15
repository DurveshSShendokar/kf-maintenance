package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Upload_Manual;

public interface uploadManualRepo extends JpaRepository<Upload_Manual, Integer> {
	
	
	 @Query("FROM Upload_Manual a WHERE a.machine.machine_id = ?1 ")
		List<Upload_Manual> findBymachineID(int machine_id);
		
		
		    
		    @Query("SELECT u FROM Upload_Manual u WHERE u.machine.machine_id = :machineId")
		    List<Upload_Manual> findByMachine_MachineId(@Param("machineId") int machineId);

		    @Query("SELECT u FROM Upload_Manual u WHERE u.machine.eqid = :eqid")
		    List<Upload_Manual> findByMachineEqid(@Param("eqid") String eqid);

		    
		   

    
}
