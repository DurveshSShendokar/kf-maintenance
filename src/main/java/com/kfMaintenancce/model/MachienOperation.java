package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="machien_operations_mst")
public class MachienOperation {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int machien_operations_id;
	
	@Column(name="machine")
	private String machine;
	
	@Column(name="hour_day")
	private double hourDay;
	
	@Column(name="days_of_week")
	private double daysOfWeek;

	public int getMachien_operations_id() {
		return machien_operations_id;
	}

	public void setMachien_operations_id(int machien_operations_id) {
		this.machien_operations_id = machien_operations_id;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public double getHourDay() {
		return hourDay;
	}

	public void setHourDay(double hourDay) {
		this.hourDay = hourDay;
	}

	public double getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(double daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	

	
	

}
