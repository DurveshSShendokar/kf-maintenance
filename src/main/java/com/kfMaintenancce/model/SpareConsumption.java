package com.kfMaintenancce.model;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Entity
@Table(name="spare_consuptiont")
public class SpareConsumption {
	  @Override
	public String toString() {
		return "SpareConsumption [spare_consuptiont_id=" + spare_consuptiont_id + ", complaint=" + complaint
				+ ", spare=" + spare + ", quantity=" + quantity + ", consuptionDate=" + consuptionDate
				+ ", consuption_by=" + consuption_by + "]";
	}


	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int spare_consuptiont_id;
	

	@ManyToOne
	@JoinColumn(name="complaint_id")
		private Complaint  complaint;
	
	

	@ManyToOne
	@JoinColumn(name="spare_id")
		private Spare spare;
	
	

    @Column(name="quantity")
    private double quantity;
    
    

    @Column(name="consuption_date")
    private Date consuptionDate;
    

    @Column(name="consuption_by")
    private String consuption_by;


	public int getSpare_consuptiont_id() {
		return spare_consuptiont_id;
	}


	public void setSpare_consuptiont_id(int spare_consuptiont_id) {
		this.spare_consuptiont_id = spare_consuptiont_id;
	}


	public Complaint getComplaint() {
		return complaint;
	}


	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}


	public Spare getSpare() {
		return spare;
	}


	public void setSpare(Spare spare) {
		this.spare = spare;
	}


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public Date getConsuptionDate() {
		return consuptionDate;
	}


	public void setConsuptionDate(Date consuptionDate) {
		this.consuptionDate = consuptionDate;
	}


	public String getConsuption_by() {
		return consuption_by;
	}


	public void setConsuption_by(String consuption_by) {
		this.consuption_by = consuption_by;
	}

}
