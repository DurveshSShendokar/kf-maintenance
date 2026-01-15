package com.kfMaintenancce.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="breakdown_spare")
public class BreakdownSpare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "breakdown_id")
    @JsonBackReference
    private Breakdown breakdown;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maint_spare_id", nullable = false)
    private MaintSpare maintspare;
    
    
    
    
    @Column(name="Consumed_quantity")
    private Double Consumedquantity;
    
    @Column(name="consumption_date")
    private Date consumptionDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Breakdown getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}

	public MaintSpare getSpare() {
		return maintspare;
	}

	public void setSpare(MaintSpare spare) {
		this.maintspare = spare;
	}

	

	public Double getConsumedquantity() {
		return Consumedquantity;
	}

	public void setConsumedquantity(Double consumedquantity) {
		Consumedquantity = consumedquantity;
	}

	public Date getConsumptionDate() {
		return consumptionDate;
	}

	public void setConsumptionDate(Date consumptionDate) {
		this.consumptionDate = consumptionDate;
	}
    
	@Transient
	public Double getConsumedCost(List<MaintSpareStocking> spareStockings) {
	    if (Consumedquantity == null || maintspare == null) {
	        return 0.0;
	    }

	    // Fetch the latest unit price from MaintSpareStocking for this MaintSpare
	    Optional<Double> latestUnitPrice = spareStockings.stream()
	        .filter(stock -> stock.getMaintspare().getMaintspare_id() == this.maintspare.getMaintspare_id())
	        .map(MaintSpareStocking::getUnitPrice)
	        .findFirst(); 

	    return latestUnitPrice.orElse(0.0) * Consumedquantity;
	}


    
    
       
}

