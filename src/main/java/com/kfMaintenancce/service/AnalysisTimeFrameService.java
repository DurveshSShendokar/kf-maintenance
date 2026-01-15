package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import com.kfMaintenancce.model.Analysis_Time_Frame;

public interface AnalysisTimeFrameService {
	
	
	 public List<Analysis_Time_Frame> getAllAnalysisTimeFrames();
	 public Optional<Analysis_Time_Frame> getAnalysisTimeFrameById(int id);
	 public Analysis_Time_Frame saveAnalysisTimeFrame(Analysis_Time_Frame analysisTimeFrame);
	 public Analysis_Time_Frame updateAnalysisTimeFrame(int id, Analysis_Time_Frame updatedAnalysisTimeFrame);
	 public boolean deleteAnalysisTimeFrame(int id) ;
	public List<Analysis_Time_Frame> getByFor(String analysis_for);
	public Optional<Analysis_Time_Frame> getAciveByFor(String string);
	 

}
