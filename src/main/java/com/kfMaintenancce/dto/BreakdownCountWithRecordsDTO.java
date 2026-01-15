package com.kfMaintenancce.dto;

import java.util.List;
import com.kfMaintenancce.model.Breakdown;

public class BreakdownCountWithRecordsDTO {
  
	
	 // ---- Weekly counts ----
    private int weeklyOpen;
    private int weeklyTrial;
    private int weeklyClosed;
    private int weeklyTotal;

    // ---- Overall counts ----
    private int totalOpen;
    private int totalTrial;
    private int totalClosed;
    private int totalBreakdowns;

    // ---- Weekly records ----
    private List<Breakdown> weeklyOpenRecords;
    private List<Breakdown> weeklyTrialRecords;
    private List<Breakdown> weeklyClosedRecords;

    // ---- Overall records ----
    private List<Breakdown> totalOpenRecords;
    private List<Breakdown> totalTrialRecords;
    private List<Breakdown> totalClosedRecords;
    private List<Breakdown> totalBreakdownRecords;
	public int getWeeklyOpen() {
		return weeklyOpen;
	}
	public void setWeeklyOpen(int weeklyOpen) {
		this.weeklyOpen = weeklyOpen;
	}
	public int getWeeklyTrial() {
		return weeklyTrial;
	}
	public void setWeeklyTrial(int weeklyTrial) {
		this.weeklyTrial = weeklyTrial;
	}
	public int getWeeklyClosed() {
		return weeklyClosed;
	}
	public void setWeeklyClosed(int weeklyClosed) {
		this.weeklyClosed = weeklyClosed;
	}
	public int getWeeklyTotal() {
		return weeklyTotal;
	}
	public void setWeeklyTotal(int weeklyTotal) {
		this.weeklyTotal = weeklyTotal;
	}
	public int getTotalOpen() {
		return totalOpen;
	}
	public void setTotalOpen(int totalOpen) {
		this.totalOpen = totalOpen;
	}
	public int getTotalTrial() {
		return totalTrial;
	}
	public void setTotalTrial(int totalTrial) {
		this.totalTrial = totalTrial;
	}
	public int getTotalClosed() {
		return totalClosed;
	}
	public void setTotalClosed(int totalClosed) {
		this.totalClosed = totalClosed;
	}
	public int getTotalBreakdowns() {
		return totalBreakdowns;
	}
	public void setTotalBreakdowns(int totalBreakdowns) {
		this.totalBreakdowns = totalBreakdowns;
	}
	public List<Breakdown> getWeeklyOpenRecords() {
		return weeklyOpenRecords;
	}
	public void setWeeklyOpenRecords(List<Breakdown> weeklyOpenRecords) {
		this.weeklyOpenRecords = weeklyOpenRecords;
	}
	public List<Breakdown> getWeeklyTrialRecords() {
		return weeklyTrialRecords;
	}
	public void setWeeklyTrialRecords(List<Breakdown> weeklyTrialRecords) {
		this.weeklyTrialRecords = weeklyTrialRecords;
	}
	public List<Breakdown> getWeeklyClosedRecords() {
		return weeklyClosedRecords;
	}
	public void setWeeklyClosedRecords(List<Breakdown> weeklyClosedRecords) {
		this.weeklyClosedRecords = weeklyClosedRecords;
	}
	public List<Breakdown> getTotalOpenRecords() {
		return totalOpenRecords;
	}
	public void setTotalOpenRecords(List<Breakdown> totalOpenRecords) {
		this.totalOpenRecords = totalOpenRecords;
	}
	public List<Breakdown> getTotalTrialRecords() {
		return totalTrialRecords;
	}
	public void setTotalTrialRecords(List<Breakdown> totalTrialRecords) {
		this.totalTrialRecords = totalTrialRecords;
	}
	public List<Breakdown> getTotalClosedRecords() {
		return totalClosedRecords;
	}
	public void setTotalClosedRecords(List<Breakdown> totalClosedRecords) {
		this.totalClosedRecords = totalClosedRecords;
	}
	public List<Breakdown> getTotalBreakdownRecords() {
		return totalBreakdownRecords;
	}
	public void setTotalBreakdownRecords(List<Breakdown> totalBreakdownRecords) {
		this.totalBreakdownRecords = totalBreakdownRecords;
	}

    
    
    
	
}
