package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="target_mst")
public class Target {

	@Override
	public String toString() {
		return "Target [target_id=" + target_id + ", hour=" + hour + ", month=" + month + ", year=" + year
				+ ", machineName=" + machineName + ", type=" + type + "]";
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int target_id;
	

	@Column(name="hour")
	private String hour;
	
	@Column(name="month")
	private String month;

	
	@Column(name="year")
	private String year;

	
	@Column(name="machine_name")
	private String machineName;
	
	
	@Column(name="type")
	private String type;


	public int getTarget_id() {
		return target_id;
	}


	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMachineName() {
		return machineName;
	}


	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	
	
}
