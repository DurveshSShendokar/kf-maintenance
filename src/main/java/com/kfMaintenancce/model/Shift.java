package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity

@Table(name="shift_mst")

public class Shift implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shift_id;
	
	
	
	@Column(name="shift", length = 200)
	private String shift_name;
	
	@Column(name="up_date")
	private Date upDate;
	
	
	@Column(name="status")
	private String status;
	
	
	@Column(name="created_user_id")
	private long uId;
	
	
	
	public long getuId() {
		return uId;
	}

	public void setuId(long uId) {
		this.uId = uId;
	}


	@Column(name="start_time")
	private String startTime;
	
	@Column(name="end_time")
	private String endTime;



	public int getShift_id() {
		return shift_id;
	}

	public void setShift_id(int shift_id) {
		this.shift_id = shift_id;
	}
	

	public String getShift_name() {
		return shift_name;
	}

	public void setShift_name(String shift_name) {
		this.shift_name = shift_name;
	}

	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	
}
