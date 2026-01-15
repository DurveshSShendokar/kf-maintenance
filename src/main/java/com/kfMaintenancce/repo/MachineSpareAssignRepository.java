package com.kfMaintenancce.repo;

import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.MachineMaintSpare;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineSpareAssignRepository extends JpaRepository<MachineMaintSpare, Integer> {

	@Query("FROM MachineMaintSpare a WHERE a.machine.machine_id = ?1 ")
	List<MachineMaintSpare> findBymachineyID(int machine_id);
	
	
	
    
	   
}
