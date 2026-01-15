package com.kfMaintenancce.dto;
import java.util.List;

import com.kfMaintenancce.model.Breakdown;

public class BreakdownStatusCounts {
    private long openCount;
    private long trialCount;
    private long closedCount;

    private List<Breakdown> openDetails;
    private List<Breakdown> trialDetails;
    private List<Breakdown> closedDetails;

    public BreakdownStatusCounts(long openCount, long trialCount, long closedCount,
                                 List<Breakdown> openDetails, List<Breakdown> trialDetails, List<Breakdown> closedDetails) {
        this.openCount = openCount;
        this.trialCount = trialCount;
        this.closedCount = closedCount;
        this.openDetails = openDetails;
        this.trialDetails = trialDetails;
        this.closedDetails = closedDetails;
    }

	public long getOpenCount() {
		return openCount;
	}

	public void setOpenCount(long openCount) {
		this.openCount = openCount;
	}

	public long getTrialCount() {
		return trialCount;
	}

	public void setTrialCount(long trialCount) {
		this.trialCount = trialCount;
	}

	public long getClosedCount() {
		return closedCount;
	}

	public void setClosedCount(long closedCount) {
		this.closedCount = closedCount;
	}

	public List<Breakdown> getOpenDetails() {
		return openDetails;
	}

	public void setOpenDetails(List<Breakdown> openDetails) {
		this.openDetails = openDetails;
	}

	public List<Breakdown> getTrialDetails() {
		return trialDetails;
	}

	public void setTrialDetails(List<Breakdown> trialDetails) {
		this.trialDetails = trialDetails;
	}

	public List<Breakdown> getClosedDetails() {
		return closedDetails;
	}

	public void setClosedDetails(List<Breakdown> closedDetails) {
		this.closedDetails = closedDetails;
	}

	public BreakdownStatusCounts() {
	
		// TODO Auto-generated constructor stub
	}

    
}
