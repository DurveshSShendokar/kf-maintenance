package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="register_type_tbl")
public class RegisterType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	@Column(name="register_type")
	private String registerType;

	@Column(name="register_type_name")
	private String registerTypeName;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRegisterType() {
		return registerType;
	}


	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}


	public String getRegisterTypeName() {
		return registerTypeName;
	}


	public void setRegisterTypeName(String registerTypeName) {
		this.registerTypeName = registerTypeName;
	}
	
	

}
