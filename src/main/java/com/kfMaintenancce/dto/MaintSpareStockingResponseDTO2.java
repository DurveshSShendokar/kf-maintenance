package com.kfMaintenancce.dto;

import java.util.Date;

import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.UserDetails;

public class MaintSpareStockingResponseDTO2 {
	   private int spareStockingId;
	    private MaintSpare maintspare;
	    private Double stockingQuantity;
	    private Date stockingDate;
	    private UserDetails stockingBy;
	    private double unitPrice;
	    private Double availableQuantity; 

    
    public int getSpareStockingId() {
        return spareStockingId;
    }

    public void setSpareStockingId(int spareStockingId) {
        this.spareStockingId = spareStockingId;
    }

   

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

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
