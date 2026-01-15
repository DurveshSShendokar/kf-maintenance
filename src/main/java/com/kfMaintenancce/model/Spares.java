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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity

@Table(name="spares_mst")

public class Spares implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int spare_id;
	
	
	
	
	
	@ManyToOne
    @JoinColumn(name="breakdown_update_id")
    private Breakdownupdate breakdownupdate;
 
    public Spares() {}
 


	@Column(name="spare_name", length = 200)
	private String spare_name;
	
	
	@Column(name="suuplier_name", length = 200)
	private String suuplier_name;
	
	@Column(name="code")
	private String code;
	
	
	@Column(name="deletes", length = 2)
	private int deletes;

	
	@Column(name="qty", length = 11)
	private long qty;

	

	public long getQty() {
		return qty;
	}


	public void setQty(long qty) {
		this.qty = qty;
	}


	public int getDeletes() {
		return deletes;
	}


	public void setDeletes(int deletes) {
		this.deletes = deletes;
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


	public Breakdownupdate getBreakdownupdate() {
		return breakdownupdate;
	}


	public void setBreakdownupdate(Breakdownupdate breakdownupdate) {
		this.breakdownupdate = breakdownupdate;
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
	
	
}
