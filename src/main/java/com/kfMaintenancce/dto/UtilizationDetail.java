package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Complaint; 

public class UtilizationDetail
{
    private Complaint complaint;
    private double quantity;

   
    public UtilizationDetail(Complaint complaint, double quantity) {
        this.complaint = complaint;
        this.quantity = quantity;
    }

    
    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
