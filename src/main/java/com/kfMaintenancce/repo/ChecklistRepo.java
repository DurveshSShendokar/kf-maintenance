package com.kfMaintenancce.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Checklist;

public interface ChecklistRepo extends JpaRepository<Checklist, Integer> ,ChecklistRepoCustom {
//	@Query("from Checklist c where c.machine.machine_id=?1 and  c.frequency=?2")
//	List<Checklist> getMaintenencaceCheckPointByMaintenace(int machine_id, String frequency);
	
	@Query("SELECT c FROM Checklist c WHERE c.machine.machine_id = :machineId AND c.task = :task AND c.type = :type AND c.frequency = :frequency")
	List<Checklist> findExistingChecklist(@Param("machineId") int machineId,
	                                      @Param("task") String task,
	                                      @Param("type") String type,
	                                      @Param("frequency") String frequency);

	 @Modifying
	    @Transactional
	    @Query("UPDATE Checklist c SET c.activeBit = 0 WHERE c.machine.machine_id = :machineId")
	    void deactivateChecklistsByMachineId(@Param("machineId") int machineId);
	
	

//	@Query("from Checklist c where c.machine.machine_id=?1 and c.frequency=?2 and c.activeBit=1 ")
//	List<Checklist> arrangechecklist(int machine_id, String frequency);
	
	@Query("from Checklist c where c.machine.machine_id = ?1 and c.frequency = ?2 and c.activeBit = 1 order by c.serialNumber asc")
	List<Checklist> arrangechecklist(int machine_id, String frequency);

	
	@Query("from Checklist c where c.task=?1 and  c.machine.machine_id=?2 and  c.frequency=?3 and c.type=?4")
	List<Checklist> getExistingList(String task, int machine_id, String frequency, String type);
	
	@Query("from Checklist c where  c.activeBit = 1 order by c.serialNumber asc")
	List<Checklist> getChecklistList();
	
	@Query("from Checklist c where c.machine.machine_id=?1 and c.frequency=?2 and c.activeBit=1 order by c.serialNumber asc")
	List<Checklist> getChecklistByMachineNFrequency(int machine_id, String freq);
	
	@Query("from Checklist c where c.machine.machine_name = ?1 and c.activeBit = 1 order by c.serialNumber asc")
	List<Checklist> listByMachineName(String machineName);

	
	@Query("from Checklist c where TRIM(LOWER(c.machine.eqid)) = LOWER(TRIM(?1)) and c.activeBit = 1 order by c.serialNumber asc")
	List<Checklist> listByMachineEquipId(String equipId);

	
	@Query("SELECT c FROM Checklist c JOIN c.machine m WHERE m.eqid = :eqid order by c.serialNumber asc")
    List<Checklist> findChecklistsByMachineEqid(String eqid);
	
	
	@Query("from Checklist c where c.machine.eqid = ?1 and c.frequency = ?2 and c.activeBit = 1 order by c.serialNumber asc")
	List<Checklist> listByMachineEquipIdAndFrequency(String equipId, String frequency);

}
