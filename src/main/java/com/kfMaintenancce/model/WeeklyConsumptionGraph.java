package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="weekly_consuption_graph")
public class WeeklyConsumptionGraph {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
		
		 @Column(name="meter_id")
		 private int meterId;
		 
	    @Column(name="today_date")
	    private String todayDate;
	    
	    @Column(name="today_consumption")
	    private String todayConsumption;
	    
	    
	    @Column(name="day1_date")
	    private String day1Date;
	    
	    @Column(name="day1_consumption")
	    private String day1Consumption;
	    
	    
	    
	    @Column(name="day2_date")
	    private String day2Date;
	    
	    @Column(name="day2_consumption")
	    private String day2Consumption;
	    
	    
	    
	    @Column(name="day3_date")
	    private String day3Date;
	    
	    @Column(name="day3_consumption")
	    private String day3Consumption;
	    
	    
	    @Column(name="day4_date")
	    private String day4Date;
	    
	    @Column(name="day4_consumption")
	    private String day4Consumption;
	    
	    
	    
	    
	    
	    @Column(name="day5_date")
	    private String day5Date;
	    @Column(name="day5_consumption")
	    private String day5Consumption;
	    
	    
	    
	    @Column(name="day6_date")
	    private String day6Date;
	    
	    @Column(name="day6_consumption")
	    private String day6Consumption;

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

	
		public String getTodayDate() {
			return todayDate;
		}

		public void setTodayDate(String todayDate) {
			this.todayDate = todayDate;
		}

		public String getDay2Date() {
			return day2Date;
		}

		public void setDay2Date(String day2Date) {
			this.day2Date = day2Date;
		}

		public String getDay3Consumption() {
			return day3Consumption;
		}

		public void setDay3Consumption(String day3Consumption) {
			this.day3Consumption = day3Consumption;
		}

		public String getDay4Date() {
			return day4Date;
		}

		public void setDay4Date(String day4Date) {
			this.day4Date = day4Date;
		}

		public String getDay5Consumption() {
			return day5Consumption;
		}

		public void setDay5Consumption(String day5Consumption) {
			this.day5Consumption = day5Consumption;
		}

		public String getDay6Date() {
			return day6Date;
		}

		public void setDay6Date(String day6Date) {
			this.day6Date = day6Date;
		}

		
	

		public String getTodayConsumption() {
			return todayConsumption;
		}

		public void setTodayConsumption(String todayConsumption) {
			this.todayConsumption = todayConsumption;
		}

		public String getDay1Date() {
			return day1Date;
		}

		public String getDay1Consumption() {
			return day1Consumption;
		}

		public void setDay1Consumption(String day1Consumption) {
			this.day1Consumption = day1Consumption;
		}

		public void setDay1Date(String day1Date) {
			this.day1Date = day1Date;
		}

		public String getDay2Consumption() {
			return day2Consumption;
		}

		public void setDay2Consumption(String day2Consumption) {
			this.day2Consumption = day2Consumption;
		}

		public String getDay3Date() {
			return day3Date;
		}

		public void setDay3Date(String day3Date) {
			this.day3Date = day3Date;
		}

		public String getDay4Consumption() {
			return day4Consumption;
		}

		public void setDay4Consumption(String day4Consumption) {
			this.day4Consumption = day4Consumption;
		}

		public String getDay5Date() {
			return day5Date;
		}

		public void setDay5Date(String day5Date) {
			this.day5Date = day5Date;
		}

		public String getDay6Consumption() {
			return day6Consumption;
		}

		public void setDay6Consumption(String day6Consumption) {
			this.day6Consumption = day6Consumption;
		}

		
	    
	  
}
