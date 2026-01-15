package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="current_drop_graph_data")
public class CurrentDropGraphData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="time_hrmin")
	private String time_hrmin;
	
	@Column(name="value")
	private String value;
	
	@Column(name="data_no")
	private int dataNo;
	
	@Column(name="meter_id")
	private int meterId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime_hrmin() {
		return time_hrmin;
	}

	public void setTime_hrmin(String time_hrmin) {
		this.time_hrmin = time_hrmin;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getDataNo() {
		return dataNo;
	}

	public void setDataNo(int dataNo) {
		this.dataNo = dataNo;
	}

	public int getMeterId() {
		return meterId;
	}

	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}
	
	
	
	
	
}
