package com.kfMaintenancce.dto;

import java.util.Date;

public class ConsumptionReportRequest {
	private int meeterId;
	private Date fromDate;
	private Date toDate;
	private String reportFor;
	private String reportType;
	private int month;
	private int year;
	public int getMeeterId() {
		return meeterId;
	}
	public void setMeeterId(int meeterId) {
		this.meeterId = meeterId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	@Override
	public String toString() {
		return "ConsumptionReportRequest [meeterId=" + meeterId + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}
	public String getReportFor() {
		return reportFor;
	}
	public void setReportFor(String reportFor) {
		this.reportFor = reportFor;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

}
