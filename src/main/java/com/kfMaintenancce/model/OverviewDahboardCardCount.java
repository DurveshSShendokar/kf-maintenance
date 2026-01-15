package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="overview_dahboard_card_count")
public class OverviewDahboardCardCount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="no_of_device")
	private int noOfDevice;
	
	@Column(name="total_consumption")
	private String totalConsumption;

	
	@Column(name="last_month_consumption")
	private String lastMonthConsumption;
	
	@Column(name="last_day_consumption")
	private String lastDayConsumption;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoOfDevice() {
		return noOfDevice;
	}

	public void setNoOfDevice(int noOfDevice) {
		this.noOfDevice = noOfDevice;
	}

	public String getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(String totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public String getLastMonthConsumption() {
		return lastMonthConsumption;
	}

	public void setLastMonthConsumption(String lastMonthConsumption) {
		this.lastMonthConsumption = lastMonthConsumption;
	}

	public String getLastDayConsumption() {
		return lastDayConsumption;
	}

	public void setLastDayConsumption(String lastDayConsumption) {
		this.lastDayConsumption = lastDayConsumption;
	}
	
	
	

}

	
