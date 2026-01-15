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
@Table(name="maint_sparestock_mst")
public class MaintSpareStock {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maintspare_stock_id;
	
	
	@ManyToOne
	@JoinColumn(name="maintspare_id")
	private MaintSpare maintSpare;

	@Column(name="available_quantity")
	private Double availableQuantity;
	
	
	@Column(name="stock_updated_date")
	private Date stockUpdatedDate;


	public int getMaintspare_stock_id() {
		return maintspare_stock_id;
	}


	public void setMaintspare_stock_id(int maintspare_stock_id) {
		this.maintspare_stock_id = maintspare_stock_id;
	}


	public MaintSpare getMaintSpare() {
		return maintSpare;
	}


	public void setMaintSpare(MaintSpare maintSpare) {
		this.maintSpare = maintSpare;
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
