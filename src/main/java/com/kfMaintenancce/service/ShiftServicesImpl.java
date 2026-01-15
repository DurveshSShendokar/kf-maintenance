package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Shift;
import com.kfMaintenancce.repo.ShiftRepo;

@Service
public class ShiftServicesImpl implements ShiftServices {

	@Autowired
	ShiftRepo shiftRepo;
	@Override
	public List<Shift> getShiftList() {
		// TODO Auto-generated method stub
		return shiftRepo.findAll();
	}

}
