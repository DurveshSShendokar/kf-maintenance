package com.kfMaintenancce.dto;

import java.util.ArrayList;
import java.util.List;

public class DropGraphDTO {
	List<String> data1;
	List<String> data2;
	List<String> label;
	List<String> series;
	public List<String> getData1() {
		return data1;
	}
	public void setData1(List<String> data1) {
		this.data1 = data1;
	}
	public List<String> getData2() {
		return data2;
	}
	public void setData2(List<String> data2) {
		this.data2 = data2;
	}
	public List<String> getLabel() {
		return label;
	}
	public void setLabel(List<String> label) {
		this.label = label;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
	
	
}
