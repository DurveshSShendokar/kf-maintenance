package com.kfMaintenancce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dashboard_meter_voltage_current_card")
public class CurrentVoltageCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	String currentR;
	String currentY;
	String currentB;
	String currentN;
	int meterId;

	String voltageRY;
	String voltageYB;
	String voltageBR;
	String voltageRB;
	String voltageLN;
	
	String voltageLL;
	String voltageRN;
	String voltageYN;
	String voltageBN;
	
	
	
	
	
	
	
	
	
	
	public String getVoltageLL() {
		return voltageLL;
	}
	public void setVoltageLL(String voltageLL) {
		this.voltageLL = voltageLL;
	}
	public String getVoltageRN() {
		return voltageRN;
	}
	public void setVoltageRN(String voltageRN) {
		this.voltageRN = voltageRN;
	}
	public String getVoltageYN() {
		return voltageYN;
	}
	public void setVoltageYN(String voltageYN) {
		this.voltageYN = voltageYN;
	}
	public String getVoltageBN() {
		return voltageBN;
	}
	public void setVoltageBN(String voltageBN) {
		this.voltageBN = voltageBN;
	}
	
	public String getCurrentR() {
		return currentR;
	}
	public void setCurrentR(String currentR) {
		this.currentR = currentR;
	}
	public String getCurrentY() {
		return currentY;
	}
	public void setCurrentY(String currentY) {
		this.currentY = currentY;
	}
	public String getCurrentB() {
		return currentB;
	}
	public void setCurrentB(String currentB) {
		this.currentB = currentB;
	}
	public String getCurrentN() {
		return currentN;
	}
	public void setCurrentN(String currentN) {
		this.currentN = currentN;
	}
	public String getVoltageRY() {
		return voltageRY;
	}
	public void setVoltageRY(String voltageRY) {
		this.voltageRY = voltageRY;
	}
	public String getVoltageYB() {
		return voltageYB;
	}
	public void setVoltageYB(String voltageYB) {
		this.voltageYB = voltageYB;
	}
	public String getVoltageBR() {
		return voltageBR;
	}
	public void setVoltageBR(String voltageBR) {
		this.voltageBR = voltageBR;
	}
	public String getVoltageRB() {
		return voltageRB;
	}
	public void setVoltageRB(String voltageRB) {
		this.voltageRB = voltageRB;
	}
	public String getVoltageLN() {
		return voltageLN;
	}
	public void setVoltageLN(String voltageLN) {
		this.voltageLN = voltageLN;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMeterId() {
		return meterId;
	}
	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}

}
