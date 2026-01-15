	package com.kfMaintenancce.model;

import java.io.Serializable;

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
import com.fasterxml.jackson.annotation.JsonInclude;



@Entity

@Table(name="spare_mst")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spare implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int spare_id;
 
    public Spare() {}
 

	 @Column(name = "active")
	    private Integer active = 1;
    
	@Column(name="spare_name", length = 200)
	private String spare_name;
	
	@Column(name="spare_type")
	private String spare_type;
	
	
	@Column(name="suuplier_name", length = 200)
	private String suuplier_name;
	
	@Column(name="code")
	private String code;
	

	
	public String getSpare_type() {
		return spare_type;
	}


	public void setSpare_type(String spare_type) {
		this.spare_type = spare_type;
	}


	@Transient
	private Double quantity;
	
	
	
	@Transient
	private Double avialableQuantity;



	public Double getQuantity() {
		return quantity;
	}


	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}


	public int getSpare_id() {
		return spare_id;
	}


	public void setSpare_id(int spare_id) {
		this.spare_id = spare_id;
	}


	public String getSpare_name() {
		return spare_name;
	}


	public void setSpare_name(String spare_name) {
		this.spare_name = spare_name;
	}




	public String getSuuplier_name() {
		return suuplier_name;
	}


	public void setSuuplier_name(String suuplier_name) {
		this.suuplier_name = suuplier_name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	




	public Double getAvialableQuantity() {
		return avialableQuantity;
	}


	public void setAvialableQuantity(Double avialableQuantity) {
		this.avialableQuantity = avialableQuantity;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	
}
