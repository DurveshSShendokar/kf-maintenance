package com.kfMaintenancce.dto;

import java.util.Date;

import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.UserDetails;

public class SpareStockingResponseDTO {
	   private int spareStockingId;
	    private Spare spare;
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

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
