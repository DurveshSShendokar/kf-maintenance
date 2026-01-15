package com.kfMaintenancce.model;

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
@Table(name="engergy_meter_register")
public class EnergyMeterRegister {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private EnergyMeterModule module;
	
	
	@Column(name="register_name")
	private String registerName;
	
	
	@Column(name="register_no")
	private String registerNo;
	
	
	@Column(name="register_format")
	private String registerFormat;
	
	
	@Column(name="uom")
	private String uom;
	
	@Column(name="scale")
	private String scale;
	
	
	@Column(name="multiplier")
	private String multiplier;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="create_date_time")
	private Date createDateTime;

	
	@Column(name="register_active")
	private int registerActive;
	
	@Column(name="register_byte_type")
	private int registerByteType;
	
	
	@Column(name="register_coversion_unit")
	private String registerCoversionUnit;
	
	
	@Column(name="register_mask_char")
	private String registerMaskChar;
	
	public String getUom() {
		return uom;
	}


	public void setUom(String uom) {
		this.uom = uom;
	}


	public String getScale() {
		return scale;
	}


	public void setScale(String scale) {
		this.scale = scale;
	}


	public String getMultiplier() {
		return multiplier;
	}


	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}


	public String getUnit() {
		return unit;
	}


	public int getRegisterActive() {
		return registerActive;
	}


	public void setRegisterActive(int registerActive) {
		this.registerActive = registerActive;
	}


	public int getRegisterByteType() {
		return registerByteType;
	}


	public void setRegisterByteType(int registerByteType) {
		this.registerByteType = registerByteType;
	}


	public String getRegisterCoversionUnit() {
		return registerCoversionUnit;
	}


	public void setRegisterCoversionUnit(String registerCoversionUnit) {
		this.registerCoversionUnit = registerCoversionUnit;
	}


	public String getRegisterMaskChar() {
		return registerMaskChar;
	}


	public void setRegisterMaskChar(String registerMaskChar) {
		this.registerMaskChar = registerMaskChar;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public EnergyMeterModule getModule() {
		return module;
	}


	public void setModule(EnergyMeterModule module) {
		this.module = module;
	}


	public String getRegisterName() {
		return registerName;
	}


	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}


	public String getRegisterNo() {
		return registerNo;
	}


	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}


	public String getRegisterFormat() {
		return registerFormat;
	}


	public void setRegisterFormat(String registerFormat) {
		this.registerFormat = registerFormat;
	}


	public Date getCreateDateTime() {
		return createDateTime;
	}


	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
	
}
