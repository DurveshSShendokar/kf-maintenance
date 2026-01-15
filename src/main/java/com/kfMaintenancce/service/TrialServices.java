package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Trial;

public interface TrialServices {

	List<Trial> getListByBreakdownUpdate(Breakdown breakdown);

	void addTrial(Trial trial);

	List<Trial> getTrialList();

	List<Breakdown> getHistory(Date startDate, Date endDate);

	List<Breakdownupdate> getReport(Trial trial);

	List<Trial> getTrialsByBreakdown(int breakdownId);

	List<Trial> getTrialListByBreakdown(int breakdownId);

}
