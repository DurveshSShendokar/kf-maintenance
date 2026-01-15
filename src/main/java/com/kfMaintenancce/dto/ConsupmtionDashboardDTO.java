package com.kfMaintenancce.dto;

import java.util.List;

public class ConsupmtionDashboardDTO {
	private List<String> strings;
	private List<Double> doubleList;
	private List<String> stringList;
	
	
	private List<String> min;
	
	private List<String> max;
	
	
	private String date;
	private String month;
	private double Value;
	private String ValueStr;
	public List<String> getStrings() {
		return strings;
	}
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	public List<Double> getDoubleList() {
		return doubleList;
	}
	public void setDoubleList(List<Double> doubleList) {
		this.doubleList = doubleList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getValue() {
		return Value;
	}
	public void setValue(double value) {
		Value = value;
	}
	public List<String> getStringList() {
		return stringList;
	}
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	public String getValueStr() {
		return ValueStr;
	}
	public void setValueStr(String valueStr) {
		ValueStr = valueStr;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public List<String> getMin() {
		return min;
	}
	public void setMin(List<String> min) {
		this.min = min;
	}
	public List<String> getMax() {
		return max;
	}
	public void setMax(List<String> max) {
		this.max = max;
	}

}
