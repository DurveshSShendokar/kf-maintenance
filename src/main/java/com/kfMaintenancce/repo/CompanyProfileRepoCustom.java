package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;

import com.kfMaintenancce.model.CompanyProfile;

public interface CompanyProfileRepoCustom {
	
	List<CompanyProfile> getCompanyProfileByLimit(int pageNo, int perPage);
	List<CompanyProfile> getCompanyProfileByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getCompanyProfileCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
