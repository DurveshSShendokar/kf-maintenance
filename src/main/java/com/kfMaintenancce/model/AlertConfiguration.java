package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alert_configuration")
public class AlertConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="alert_for")
	private String alertFor;
	
	
	@Column(name="register_no")
	private String registerNo;
	
	@Column(name="register_name")
	private String registerName;
	
	@Column(name="from_range")
	private double fromRange;
	
	
	@Column(name="to_range")
	private double toRange;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAlertFor() {
		return alertFor;
	}


	public void setAlertFor(String alertFor) {
		this.alertFor = alertFor;
	}


	public String getRegisterNo() {
		return registerNo;
	}


	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}


	public String getRegisterName() {
		return registerName;
	}


	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}


	public double getFromRange() {
		return fromRange;
	}


	public void setFromRange(double fromRange) {
		this.fromRange = fromRange;
	}


	public double getToRange() {
		return toRange;
	}


	public void setToRange(double toRange) {
		this.toRange = toRange;
	}


	@Override
	public String toString() {
		return "AlertConfiguration [id=" + id + ", alertFor=" + alertFor + ", registerNo=" + registerNo
				+ ", registerName=" + registerName + ", fromRange=" + fromRange + ", toRange=" + toRange + "]";
	}


	
}
