package com.kfMaintenancce.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.MachienOperation;


public interface MachienOperationRepo extends JpaRepository<MachienOperation, Integer>, MachineOperationRepoCustom {
	@Query("From MachienOperation m where m.machine=?1")
	Optional<MachienOperation> getByMachine(String machine);

}
