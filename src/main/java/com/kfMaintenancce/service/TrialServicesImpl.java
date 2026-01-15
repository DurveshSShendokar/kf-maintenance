package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.TrialRepo;
@Service
public class TrialServicesImpl  implements TrialServices{
	
	
	@Autowired
	TrialRepo trialRepo;
	@Autowired
	BreakdownRepo breakdownRepo;

	@Override
	public List<Trial> getListByBreakdownUpdate(Breakdown breakdown) {
		// TODO Auto-generated method stub
		return trialRepo.getListByBreakdownUpdate(breakdown.getBreakdown_id());
	}

	@Override
	public void addTrial(Trial trial) {
		// TODO Auto-generated method stub
		trialRepo.save(trial);
	}

	@Override
	public List<Trial> getTrialList() {
		// TODO Auto-generated method stub
		return trialRepo.findAll();
	}

	@Override
	public List<Breakdown> getHistory(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return breakdownRepo.getHistory(startDate,endDate);
	}

	@Override
	public List<Breakdownupdate> getReport(Trial trial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trial> getTrialsByBreakdown(int breakdownId) {
		// TODO Auto-generated method stub
		return trialRepo.getListByBreakdownUpdate(breakdownId);
	}

	@Override
	public List<Trial> getTrialListByBreakdown(int breakdownId) {
		// TODO Auto-generated method stub
		return trialRepo.getListByBreakdownUpdate(breakdownId);
	}

}
