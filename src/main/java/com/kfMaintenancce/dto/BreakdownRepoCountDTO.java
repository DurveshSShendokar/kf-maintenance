package com.kfMaintenancce.dto;

import java.util.List;

public class BreakdownRepoCountDTO {

    private List<BreakdownRepoDTO> breakdowns;
    private int totalBreakdownCount;

    public BreakdownRepoCountDTO(List<BreakdownRepoDTO> breakdowns, int totalBreakdownCount) {
        this.breakdowns = breakdowns;
        this.totalBreakdownCount = totalBreakdownCount;
    }

    public BreakdownRepoCountDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<BreakdownRepoDTO> getBreakdowns() {
        return breakdowns;
    }

    public void setBreakdowns(List<BreakdownRepoDTO> breakdowns) {
        this.breakdowns = breakdowns;
    }

    public int getTotalBreakdownCount() {
        return totalBreakdownCount;
    }

    public void setTotalBreakdownCount(int totalBreakdownCount) {
        this.totalBreakdownCount = totalBreakdownCount;
    }
}
