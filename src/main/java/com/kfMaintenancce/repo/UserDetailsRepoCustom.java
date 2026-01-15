package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.model.UserDetails;

public interface UserDetailsRepoCustom {
	
	List<UserDetails> getUserDetailsByLimit(int pageNo, int perPage);
	List<UserDetails> getUserDetailsByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getUserDetailsCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
