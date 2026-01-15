package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="register_coversion_unit_tbl")
public class RegisterCoversionUnit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	@Column(name="register_coversion_unit")
	private String registerCoversionUnit;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRegisterCoversionUnit() {
		return registerCoversionUnit;
	}


	public void setRegisterCoversionUnit(String registerCoversionUnit) {
		this.registerCoversionUnit = registerCoversionUnit;
	}
	
	
}
