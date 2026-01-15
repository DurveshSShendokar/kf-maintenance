package com.kfMaintenancce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.kfMaintenancce.model.General_breakdown;

public interface GeneralBreakdownService {

	public General_breakdown saveGeneralBreakdown(General_breakdown general_breakdown);
	public List<General_breakdown> getAllGeneralBreakdowns() ;
	public Optional<General_breakdown> getGeneralBreakdownById(int genbreak_id);
	public boolean deleteGeneralBreakdownById(int genbreak_id);
	public General_breakdown findById(int genbreak_id);
	 public String getNewBreakDownNo() ;
	 public General_breakdown updateBreakdownStatus(General_breakdown existingBreakdown, int newStatus);
		
}
