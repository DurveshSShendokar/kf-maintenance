package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.MachineOwner;

public interface MachineOwnerService {

	void addMachineOwner(MachineOwner machineOwner2);

	void deletMachineOwner(MachineOwner department);

	List<MachineOwner> getAllMachineOwners();

	List<MachineOwner> getAllMachineOwnersByUser(int userId);

	Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(int id, int machine_id);

}
