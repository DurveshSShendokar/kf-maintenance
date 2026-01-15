package com.kfMaintenancce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.BreakdownupdateRepo;
import com.kfMaintenancce.repo.SparesRepo;

@Service
public class BreakdownupdateServicesImpl implements BreakdownupdateServices {

	@Autowired
	BreakdownupdateRepo breakdownupdateRepo;
	
	@Autowired
	BreakdownRepo breakdownRepo;
	
	@Autowired
	SparesRepo sparesRepo;
	
	
	@Override
	public void addBreakdownupdate(Breakdownupdate breakdownupdate) {
		// TODO Auto-generated method stub
		breakdownupdateRepo.save(breakdownupdate);
	}

	@Override
	public List<Breakdownupdate> getBreakdownupdateList() {
		// TODO Auto-generated method stub
		List<Breakdownupdate> list = new ArrayList<Breakdownupdate>();
		List<Breakdown> breakdowns= breakdownRepo.getBreakBodownByStatus(2);
		
		for(Breakdown breakdown:breakdowns){
			List<Breakdownupdate> breakdownupdates=breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown.getBreakdown_id());
			list.addAll(breakdownupdates);
		}
	
	return list;
	
	}

	@Override
	public void deleteBreakdownupdate(int breakdown_update_id) {
		// TODO Auto-generated method stub
	Optional<Breakdownupdate>	 optional= breakdownupdateRepo.findById(breakdown_update_id);
	breakdownupdateRepo.delete(optional.get());
	}

	@Override
	public List<Spares> getSpares(int breakdownUpdateId) {
		// TODO Auto-generated method stub
		return sparesRepo.getSparesByBreakDownUpdate(breakdownUpdateId);
	}

}
