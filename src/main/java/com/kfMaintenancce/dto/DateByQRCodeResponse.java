package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Maint;

public class DateByQRCodeResponse {
	
	private List<Breakdown> beakdowns;
	private List<Maint> maints;
	public List<Breakdown> getBeakdowns() {
		return beakdowns;
	}
	public void setBeakdowns(List<Breakdown> beakdowns) {
		this.beakdowns = beakdowns;
	}
	public List<Maint> getMaints() {
		return maints;
	}
	public void setMaints(List<Maint> maints) {
		this.maints = maints;
	}
	
	
	
	

}
