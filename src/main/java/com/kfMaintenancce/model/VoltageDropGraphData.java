package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="voltage_drop_graph_data")
public class VoltageDropGraphData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="time_hrmin")
	private String time_hrmin;
	
	@Column(name="value_1")
	private String value1;
	
	
	
	@Column(name="value_2")
	private String value2;  
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


	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
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
