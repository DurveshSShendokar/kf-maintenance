package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.repo.MachineOwnerRepo;
@Service
public class MachineOwnerServiceImpl implements MachineOwnerService {

	@Autowired
	MachineOwnerRepo machineOwnerRepo;
	@Override
	public void addMachineOwner(MachineOwner machineOwner2) {
		// TODO Auto-generated method stub
		machineOwnerRepo.save(machineOwner2);
	}

	@Override
	public void deletMachineOwner(MachineOwner machineOwner) {
		// TODO Auto-generated method stub
		machineOwnerRepo.delete(machineOwner);
	}

	@Override
	public List<MachineOwner> getAllMachineOwners() {
		// TODO Auto-generated method stub
		return machineOwnerRepo.findAll();
	}

	@Override
	public List<MachineOwner> getAllMachineOwnersByUser(int userId) {
		// TODO Auto-generated method stub
		return machineOwnerRepo.getAllMachineOwnersByUser(userId);
	}

	@Override
	public Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(int id, int machine_id) {
		// TODO Auto-generated method stub
		return machineOwnerRepo.getAllMachineOwnersByUserAndMachineId(id,machine_id);
	}

}
