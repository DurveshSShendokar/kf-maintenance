package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;

public interface MachineOwnerRepo extends JpaRepository<MachineOwner, Integer>, MachineOwnerRepoCustom {
	@Query("from MachineOwner m where m.user.id=?1")
	List<MachineOwner> getAllMachineOwnersByUser(int userId);
	@Query("from MachineOwner m where m.user.id=?1 and m.machine.machine_id=?2")

	Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(int id, int machine_id);
	
	 @Query("SELECT m.machine.machine_id FROM MachineOwner m   WHERE m.user.id = ?1")
	    List<Integer> getMachineIdsByUserOwner(int userId);
	 
	 @Query("SELECT m FROM MachineOwner m JOIN FETCH m.machine WHERE "
	    		+ "m.user.id = ?1 AND m.machine.machine_id = ?2")
	    Optional<MachineOwner> getMachineOwnerByUserAndMachineId(int userId, int machineId);

	   @Query("SELECT mo FROM MachineOwner mo WHERE mo.user.id = :userId")
	    List<MachineOwner> findByUserId(@Param("userId") int userId);
	   
	   @Query("SELECT mo FROM MachineOwner mo WHERE mo.machine = :machine")
	   List<MachineOwner> findByMachine(@Param("machine") Machine machine);

	 
}
