package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Scheduledchecklist;

public interface ScheduledchecklistServices {

	List<Scheduledchecklist> getChecklistList(int maint_id);

}
