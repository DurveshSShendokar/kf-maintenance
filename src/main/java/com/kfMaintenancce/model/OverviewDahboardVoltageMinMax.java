package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="overview_dahboard_voltage_min_max")
public class OverviewDahboardVoltageMinMax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="day_name")
	private int dayName;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="min_value")
	private String minValue;
	
	@Column(name="max_value")
	private String maxValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDayName() {
		return dayName;
	}

	public void setDayName(int dayName) {
		this.dayName = dayName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return "OverviewDahboardVoltageMinMax [id=" + id + ", dayName=" + dayName + ", date=" + date + ", minValue="
				+ minValue + ", maxValue=" + maxValue + "]";
	}


	
	
}
