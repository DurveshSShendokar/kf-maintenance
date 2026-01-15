	package com.kfMaintenancce.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity

@Table(name="Maintspare_mst")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintSpare  {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maintspare_id;
 
	@Column(name = "active")
	private Integer active = 1;
    
	@Column(name="spare_name", length = 200)
	private String spare_name;
	
	
	@Column(name="supplier_name", length = 200)
	private String supplier_name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="spare_type")
	private String spare_type;
	
	@JsonProperty("quantity")
	@Transient
	private Double quantity;
	
	
	
	@Transient
	private Double avialableQuantity;



	public int getMaintspare_id() {
		return maintspare_id;
	}



	public String getSpare_type() {
		return spare_type;
	}



	public void setSpare_type(String spare_type) {
		this.spare_type = spare_type;
	}



	public void setMaintspare_id(int maintspare_id) {
		this.maintspare_id = maintspare_id;
	}



	public Integer getActive() {
		return active;
	}



	public void setActive(Integer active) {
		this.active = active;
	}



	public String getSpare_name() {
		return spare_name;
	}



	public void setSpare_name(String spare_name) {
		this.spare_name = spare_name;
	}



	public String getSupplier_name() {
		return supplier_name;
	}



	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public Double getQuantity() {
		return quantity;
	}



	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}



	public Double getAvialableQuantity() {
		return avialableQuantity;
	}



	public void setAvialableQuantity(Double avialableQuantity) {
		this.avialableQuantity = avialableQuantity;
	}
	
	
	


	
	
}
