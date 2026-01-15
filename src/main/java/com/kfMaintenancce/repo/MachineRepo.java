package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.dto.MachinechatbotDTO;
import com.kfMaintenancce.model.Machine;

public interface MachineRepo extends JpaRepository<Machine, Integer> ,MachineRepoCustom{
	@Query("From Machine m where m.machine_name=?1")
	List<Machine> getGetMachinesByName(String machine_name);

	long countByActive(int active);
	
	@Query("SELECT DISTINCT m FROM Machine m WHERE m.lab.lab_id = :labId AND m.active = 1")
	List<Machine> findDistinctMachinesByLabId(@Param("labId") int labId);


	 @Query("FROM Machine m WHERE m.lab.lab_id = :labId AND m.machine_name LIKE %:machineName% AND m.active = 1")
	    List<Machine> findByLabIdAndMachineName(@Param("labId") int labId, @Param("machineName") String machineName);
	 
	 @Query("SELECT m FROM Machine m " +
		       "WHERE TRIM(LOWER(m.lab.labName)) = TRIM(LOWER(:labName)) " +
		       "AND TRIM(LOWER(m.machine_name)) = TRIM(LOWER(:machineName)) " +
		       "AND TRIM(LOWER(m.eqid)) = TRIM(LOWER(:eqid))")
		Optional<Machine> findMachineByLabNameAndNameAndEqid(
		        @Param("labName") String labName,
		        @Param("machineName") String machineName,
		        @Param("eqid") String eqid);

	 @Query("SELECT m FROM Machine m " +
		       "JOIN FETCH m.lab l " +   // fetch lab along with machine
		       "WHERE TRIM(LOWER(m.eqid)) = TRIM(LOWER(:eqid))")
		List<Machine> findByEqided(@Param("eqid") String eqid);


	  @Query("SELECT new com.kfMaintenancce.dto.MachinechatbotDTO(" +
	           "m.machine_name, l.labName, c.catName, m.eqid, m.location) " +
	           "FROM Machine m " +
	           "LEFT JOIN m.lab l " +
	           "LEFT JOIN m.category c")
	    Page<MachinechatbotDTO> findAllMachineDTO(Pageable pageable);
	 
//	 @Query("SELECT m FROM Machine m " +
//		       "WHERE TRIM(LOWER(m.machine_name)) = TRIM(LOWER(:machineName)) " +
//		       "AND TRIM(LOWER(m.eqid)) = TRIM(LOWER(:eqid))")
//		Optional<Machine> findByMachineNameAndMachineEqid(@Param("machineName") String machineName,
//		                                                  @Param("eqid") String eqid);

	// Returns all machines matching name + eqid (case insensitive, trims spaces)
	 @Query("SELECT m FROM Machine m " +
	        "JOIN FETCH m.lab " +
	        "WHERE TRIM(LOWER(m.machine_name)) = TRIM(LOWER(:machineName)) " +
	        "AND TRIM(LOWER(m.eqid)) = TRIM(LOWER(:eqid))")
	 List<Machine> findByMachineNameAndEqid(@Param("machineName") String machineName,
	                                        @Param("eqid") String eqid);

	
//	@Query("FROM Machine m where m.eqid = ?1")
//	Optional<Machine> findByEquipmentId(String eqId);
	@Query("From Machine m where m.location=?1")
	List<Machine> getMachineListByLocation(String location);

	@Query("From Machine m where m.location=?1 and  m.machine_name=?2")
	List<Machine> getMachineListByLocationAndMachine(String location, String machineName);
	
	 @Query("SELECT DISTINCT m.machine_name FROM Machine m WHERE m.active = 1")
	    List<String> findUniqueMachineNames();
	 
	 @Query("SELECT DISTINCT m.machine_name, m.eqid FROM Machine m WHERE m.active = 1")
	    List<Object[]> findUniqueMachineNamesAndEquipmentIds();
	    
	    // Query to fetch equipment IDs by machine name for active machines
	    @Query("SELECT m.eqid FROM Machine m WHERE m.machine_name = ?1 AND m.active = 1")
	    List<String> findEquipmentIdsByMachineName(String machineName);
	    
//	    @Query("SELECT m FROM Machine m WHERE m.eqid = :eqid")
//	    Optional<Machine> findByEqid(@Param("eqid") String eqid);
	    @Query("SELECT m FROM Machine m " +
	            "JOIN FETCH m.lab l " +
	            "WHERE TRIM(LOWER(m.eqid)) = TRIM(LOWER(:eqid))")
	     Optional<Machine> findByEqid(@Param("eqid") String eqid);
	    
	    @Query("SELECT m FROM Machine m WHERE m.eqid = :eqid")
	    List<Machine> findByEqids(@Param("eqid") String eqid);

	    
//	    @Query("SELECT m FROM Machine m WHERE m.machine_name = :machineName")
//	    List<Machine> findByMachineName(@Param("machineName") String machineName);
//	    
	    @Query("SELECT m FROM Machine m WHERE LOWER(TRIM(m.machine_name)) = LOWER(TRIM(:machineName))")
	    List<Machine> findByMachineName(@Param("machineName") String machineName);

	    @Query("SELECT m FROM Machine m " +
	    	       "WHERE LOWER(TRIM(m.machine_name)) = LOWER(TRIM(:machineName)) " +
	    	       "AND m.lab.lab_id = :labId " +
	    	       "AND m.active = 1")
	    	List<Machine> findByMachineNameAndLabId(@Param("machineName") String machineName,
	    	                                        @Param("labId") int labId);

	    
	    @Query("SELECT COUNT(m) FROM Machine m WHERE m.lab.labName = :labName")
	    long countByLabName(@Param("labName") String labName);
	    
	    @Query("SELECT m FROM Machine m")
	    List<Machine> findTop10Machines(Pageable pageable);
	    
	    @Query("SELECT m FROM Machine m")
	    Page<Machine> findMachines(Pageable pageable);
	    

	    @Query("SELECT m FROM Machine m WHERE " +
	    	       "m.active = 1 AND (" +
	    	       "(:searchText IS NULL OR LOWER(COALESCE(m.machine_name, '')) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	    	       "OR LOWER(COALESCE(m.eqid, '')) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	    	       "OR LOWER(COALESCE(m.lab.labName, '')) LIKE LOWER(CONCAT('%', :searchText, '%'))))")
	    	Page<Machine> searchMachines(@Param("searchText") String searchText, Pageable pageable);
	    


	     @Query("SELECT COUNT(m) FROM Machine m WHERE " +
	            "(:searchText IS NULL OR LOWER(COALESCE(m.machine_name, '')) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	            "OR LOWER(COALESCE(m.eqid, '')) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	            "OR LOWER(COALESCE(m.lab.labName, '')) LIKE LOWER(CONCAT('%', :searchText, '%')))")
	     long countMachines(@Param("searchText") String searchText);
	     
	     @Query("SELECT m FROM Machine m WHERE LOWER(m.lab.labName) = LOWER(:labName)")
	     Page<Machine> findByLabName(@Param("labName") String labName, Pageable pageable);


}