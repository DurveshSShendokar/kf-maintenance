package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Role;

public interface RoleRepoCustom {
	
	List<Role> getRoleByLimit(int pageNo, int perPage);
	List<Role> getRoleByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getRoleCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
