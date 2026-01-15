package com.kfMaintenancce.dto;

public class MeterConsumptionDTO {
private String meterNo;
private int meterId;
private double consumptionValue;
private double todayconsumption;
private double monthlyconsumption;
public String getMeterNo() {
	return meterNo;
}
public void setMeterNo(String meterNo) {
	this.meterNo = meterNo;
}
public double getConsumptionValue() {
	return consumptionValue;
}
public void setConsumptionValue(double consumptionValue) {
	this.consumptionValue = consumptionValue;
}
public int getMeterId() {
	return meterId;
}
public void setMeterId(int meterId) {
	this.meterId = meterId;
}
public double getTodayconsumption() {
	return todayconsumption;
}
public void setTodayconsumption(double todayconsumption) {
	this.todayconsumption = todayconsumption;
}
public double getMonthlyconsumption() {
	return monthlyconsumption;
}
public void setMonthlyconsumption(double monthlyconsumption) {
	this.monthlyconsumption = monthlyconsumption;
}


}
