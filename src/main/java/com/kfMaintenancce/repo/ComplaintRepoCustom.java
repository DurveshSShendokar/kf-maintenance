package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Complaint;

public interface ComplaintRepoCustom {
	
	List<Complaint> getComplaintByLimit(int pageNo, int perPage);
	List<Complaint> getComplaintByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getComplaintCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
