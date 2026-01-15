package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;

public interface CommanService {
	public List<Date> getDatesBetween(Date startDate, Date endDate);
}
