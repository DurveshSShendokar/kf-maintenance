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
@Table(name="spare_stock")
public class SpareStock {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int spare_stock_id;
	
	
	@ManyToOne
	@JoinColumn(name="spare_id")
	private Spare spare;




	@Column(name="available_quantity")
	private Double availableQuantity;
	
	
	@Column(name="stock_updated_date")
	private Date stockUpdatedDate;


	public int getSpare_stock_id() {
		return spare_stock_id;
	}


	public void setSpare_stock_id(int spare_stock_id) {
		this.spare_stock_id = spare_stock_id;
	}


	public Spare getSpare() {
		return spare;
	}


	public void setSpare(Spare spare) {
		this.spare = spare;
	}


	public Double getAvailableQuantity() {
		return availableQuantity;
	}


	public void setAvailableQuantity(Double availableQuantity) {
		this.availableQuantity = availableQuantity;
	}


	public Date getStockUpdatedDate() {
		return stockUpdatedDate;
	}


	public void setStockUpdatedDate(Date stockUpdatedDate) {
		this.stockUpdatedDate = stockUpdatedDate;
	}

	
	
	
	
	
	
	

}
