package com.kfMaintenancce.model;

import java.math.BigDecimal;
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
@Table(name="consumption_price_slab_item")
public class PriceSlabRangeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="from_range")
	private BigDecimal fromRange;
	
	@Column(name="to_range")
	private BigDecimal toRange;
	
	
	@Column(name="price")
	private double price;
	
	@Column(name="effective_from")
	private Date effectiveFrom;
	
	@ManyToOne
	@JoinColumn(name="price_slab_id")
	private ConsumptionPriceSlab consumptionPriceSlab;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public BigDecimal getFromRange() {
		return fromRange;
	}

	public void setFromRange(BigDecimal fromRange) {
		this.fromRange = fromRange;
	}

	public BigDecimal getToRange() {
		return toRange;
	}

	public void setToRange(BigDecimal toRange) {
		this.toRange = toRange;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public ConsumptionPriceSlab getConsumptionPriceSlab() {
		return consumptionPriceSlab;
	}

	public void setConsumptionPriceSlab(ConsumptionPriceSlab consumptionPriceSlab) {
		this.consumptionPriceSlab = consumptionPriceSlab;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
