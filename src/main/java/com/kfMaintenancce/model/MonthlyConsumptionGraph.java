package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monthly_consuption_graph")
public class MonthlyConsumptionGraph {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @Column(name="month_name")
	    private String monthName;
	    
	    
	    @Column(name="month")
	    private int month;
	    
	    @Column(name="year")
	    private int year;
	    
	    
	    @Column(name="meterId")
	    private int meterId;

	    @Column(name="consumption_value")
	    private String consumptionValue;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMonthName() {
			return monthName;
		}

		public int getMeterId() {
			return meterId;
		}

		public void setMeterId(int meterId) {
			this.meterId = meterId;
		}

		public void setMonthName(String monthName) {
			this.monthName = monthName;
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

		public String getConsumptionValue() {
			return consumptionValue;
		}

		public void setConsumptionValue(String consumptionValue) {
			this.consumptionValue = consumptionValue;
		}
	    
	    
	    
}
