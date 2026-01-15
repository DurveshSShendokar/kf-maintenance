package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;

public interface LabService {

	void addLab(Lab lab);

	List<Lab> getLabByLimit(int pageNo, int perPage);

	int count();

	List<Lab> getLabByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getLabCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteLab(int lab_id);

	 
	 public List<Lab> getLabs() ;
}
