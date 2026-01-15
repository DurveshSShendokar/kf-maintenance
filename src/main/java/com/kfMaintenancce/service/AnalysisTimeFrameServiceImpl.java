package com.kfMaintenancce.service;

import com.kfMaintenancce.model.Analysis_Time_Frame;
import com.kfMaintenancce.repo.Analysis_Time_FrameRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalysisTimeFrameServiceImpl implements AnalysisTimeFrameService {

    @Autowired
    private Analysis_Time_FrameRepo Analysis_Time_FrameRepo;

    public List<Analysis_Time_Frame> getAllAnalysisTimeFrames() {
        return Analysis_Time_FrameRepo.findAll();
    }

    public Optional<Analysis_Time_Frame> getAnalysisTimeFrameById(int id) {
        return Analysis_Time_FrameRepo.findById(id);
    }

    public Analysis_Time_Frame saveAnalysisTimeFrame(Analysis_Time_Frame analysisTimeFrame) {
        return Analysis_Time_FrameRepo.save(analysisTimeFrame);
    }

    public Analysis_Time_Frame updateAnalysisTimeFrame(int id, Analysis_Time_Frame updatedAnalysisTimeFrame) {
        return Analysis_Time_FrameRepo.findById(id)
            .map(existing -> {
                existing.setStatus(updatedAnalysisTimeFrame.getStatus());
                existing.setAnalysis_for(updatedAnalysisTimeFrame.getAnalysis_for());
                existing.setNo_Of_Days(updatedAnalysisTimeFrame.getNo_Of_Days());
                return Analysis_Time_FrameRepo.save(existing);
            })
            .orElse(null);
    }

    public boolean deleteAnalysisTimeFrame(int id) {
        if (Analysis_Time_FrameRepo.existsById(id)) {
        	Analysis_Time_FrameRepo.deleteById(id);
            return true;
        }
        return false;
    }

	@Override
	public List<Analysis_Time_Frame> getByFor(String analysis_for) {
		// TODO Auto-generated method stub
		return         	Analysis_Time_FrameRepo.getByFor(analysis_for);
	}

	@Override
	public Optional<Analysis_Time_Frame> getAciveByFor(String string) {
		// TODO Auto-generated method stub
		return Analysis_Time_FrameRepo.gettAciveByFor(string);
	}
}
