package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity

@Table(name="spare_stoccking")
public class SpareStocking {

	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int spare_stocking_id;
	
	
	@ManyToOne
	@JoinColumn(name="spare_id")
	private Spare spare;

	



	@Column(name="stocking_quantity")
	private Double stockingQuantity;
	
	
	@Column(name="stocking_date")
	private Date stockingDate;
	
	
	

	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails stockingBy;
	
	

	@Column(name="unit_price")
	private double unitPrice;



	public Spare getSpare() {
		return spare;
	}



	public void setSpare(Spare spare) {
		this.spare = spare;
	}



	public Double getStockingQuantity() {
		return stockingQuantity;
	}



	public void setStockingQuantity(Double stockingQuantity) {
		this.stockingQuantity = stockingQuantity;
	}



	public Date getStockingDate() {
		return stockingDate;
	}



	public void setStockingDate(Date stockingDate) {
		this.stockingDate = stockingDate;
	}



	public UserDetails getStockingBy() {
		return stockingBy;
	}



	public void setStockingBy(UserDetails stockingBy) {
		this.stockingBy = stockingBy;
	}



	public double getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}



	public int getSpare_stocking_id() {
		return spare_stocking_id;
	}



	public void setSpare_stocking_id(int spare_stocking_id) {
		this.spare_stocking_id = spare_stocking_id;
	}
	
	
	
	
	
	
}
