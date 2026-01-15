package com.kfMaintenancce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="consumption_price_slab")
public class ConsumptionPriceSlab {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="price_slab_id")
	private String priceSlabId;
	
	
	@Column(name="effective_from")
	private Date effectiveFrom;
	
	@Column(name="effective_upto_date")
	private Date effectiveUptoDate;
	
	@Column(name="added_date")
	private Date addedDate;
	
	@Column(name="active")
	private int active;
	
	
	public Date getEffectiveUptoDate() {
		return effectiveUptoDate;
	}

	public void setEffectiveUptoDate(Date effectiveUptoDate) {
		this.effectiveUptoDate = effectiveUptoDate;
	}

	@Transient
	List<PriceSlabRangeDetails> list;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPriceSlabId() {
		return priceSlabId;
	}

	public void setPriceSlabId(String priceSlabId) {
		this.priceSlabId = priceSlabId;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<PriceSlabRangeDetails> getList() {
		return list;
	}

	public void setList(List<PriceSlabRangeDetails> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ConsumptionPriceSlab [id=" + id + ", priceSlabId=" + priceSlabId + ", effectiveFrom=" + effectiveFrom
				+ ", effectiveUptoDate=" + effectiveUptoDate + ", addedDate=" + addedDate + ", active=" + active
				+ ", list=" + list + "]";
	}
	
	
	
}
