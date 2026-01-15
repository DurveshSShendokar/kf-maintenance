package com.kfMaintenancce.model;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name="maint_sparestocking_mst")
public class MaintSpareStocking {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "maintsparestocking_id")
	    private int maintsparestocking_id; 

	@ManyToOne
	@JsonProperty("maintSpare")
	@JoinColumn(name="spare_id")
	private MaintSpare maintspare;

	@Column(name="stocking_quantity")
	private Double stockingQuantity;
	
	
	@Column(name="stocking_date")
	private Date stockingDate;
	

	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails stockingBy;
	
	

	@Column(name="unit_price")
	private double unitPrice;



	public MaintSpare getMaintspare() {
		return maintspare;
	}



	public void setMaintspare(MaintSpare maintspare) {
		this.maintspare = maintspare;
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



	public int getMaintsparestocking_id() {
		return maintsparestocking_id;
	}



	public void setMaintsparestocking_id(int maintsparestocking_id) {
		this.maintsparestocking_id = maintsparestocking_id;
	}



	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}


	
	
	
	
}
