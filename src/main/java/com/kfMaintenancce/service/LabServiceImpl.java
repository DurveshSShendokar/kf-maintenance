package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.repo.LabRepo;

@Service
public class LabServiceImpl implements LabService {
	
	@Autowired
	LabRepo labRepo;	

	public void addLab(Lab lab) {
		// TODO Auto-generated method stub
		labRepo.save(lab);
	}

	
	public List<Lab> getLabByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return labRepo.getLabByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) labRepo.count();
	}

	
	public List<Lab> getLabByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return labRepo.getLabByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getLabCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return labRepo.getLabCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	 public void deleteLab(int lab_id) {
		 labRepo.deleteById(lab_id);
	    }
	 
	 
	 public List<Lab> getLabs() {
	        return labRepo.findAll();
	    } 

}
