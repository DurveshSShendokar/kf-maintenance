package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Notification;

public interface NotificationRepoCustom {
	
	List<Notification> getNotificationByLimit(int pageNo, int perPage);
	List<Notification> getNotificationByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	int getNotificationCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

}
