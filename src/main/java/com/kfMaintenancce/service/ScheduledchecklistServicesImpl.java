package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Scheduledchecklist;
import com.kfMaintenancce.repo.ScheduledchecklistRepo;

@Service
public class ScheduledchecklistServicesImpl  implements ScheduledchecklistServices{
	
	@Autowired
	ScheduledchecklistRepo scheduledchecklistRepo;

	@Override
	public List<Scheduledchecklist> getChecklistList(int maint_id) {
		// TODO Auto-generated method stub
		return scheduledchecklistRepo.getChecklistList(maint_id);
	}

}
