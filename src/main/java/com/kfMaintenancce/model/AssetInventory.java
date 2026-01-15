package com.kfMaintenancce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="asset_inventory")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetInventory {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int asset_inventory_id;
	
	
	@Column(name="machine")
	private String machine;
	
	@Column(name="equipment_id")
	private String equipmentId;
	
	
	@Column(name="model")
	private String model;
	
	
	@Column(name="comapny_name")
	private String comapnyName;
	
	
	@Column(name="login_Name")
	private String loginName;
	
	
	
	@Column(name="op_system")
	private String opSystem;
	
	

	@Column(name="ram_capacity")
	private String ramCapacity;
	
	
	

	@Column(name="hard_disk_cap")
	private String hardDiskCap;
	
	
	
	@ManyToOne
	@JoinColumn(name="cat_id")
	private Category category;
	
	

	
	
	@ManyToOne
	@JoinColumn(name="lab_id")
	private Lab lab;
	
	
	//new 
	

	@Column(name="It_Dept_Code")
	private String it_Dept;

	
	@Column(name="Plant_Loc")
	private String plant_Loc;
	
	
	@Column(name="Lab_Code")
	private String labCode;
	
	
	@Column(name="Lab_Inside_Loc")
	private String labInsideLoc;
	
	@Column(name="Category")
	private String subCat;
	
	
	@Column(name="Laser_Printer")
	private String laserPrint;
	
	
	@Column(name="BarCode_Printer")
	private String barCodePrint;
	

	public int getAsset_inventory_id() {
		return asset_inventory_id;
	}



	public void setAsset_inventory_id(int asset_inventory_id) {
		this.asset_inventory_id = asset_inventory_id;
	}



	public String getMachine() {
		return machine;
	}



	public void setMachine(String machine) {
		this.machine = machine;
	}





	public String getEquipmentId() {
		return equipmentId;
	}



	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
	}



	public String getComapnyName() {
		return comapnyName;
	}



	public void setComapnyName(String comapnyName) {
		this.comapnyName = comapnyName;
	}



	public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public String getOpSystem() {
		return opSystem;
	}



	public void setOpSystem(String opSystem) {
		this.opSystem = opSystem;
	}



	public String getRamCapacity() {
		return ramCapacity;
	}



	public void setRamCapacity(String ramCapacity) {
		this.ramCapacity = ramCapacity;
	}



	public String getHardDiskCap() {
		return hardDiskCap;
	}






	public void setHardDiskCap(String hardDiskCap) {
		this.hardDiskCap = hardDiskCap;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	


	public Lab getLab() {
		return lab;
	}



	public void setLab(Lab lab) {
		this.lab = lab;
	}



	public String getIt_Dept() {
		return it_Dept;
	}



	public void setIt_Dept(String it_Dept) {
		this.it_Dept = it_Dept;
	}



	public String getPlant_Loc() {
		return plant_Loc;
	}



	public void setPlant_Loc(String plant_Loc) {
		this.plant_Loc = plant_Loc;
	}



	public String getLabCode() {
		return labCode;
	}



	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}



	public String getLabInsideLoc() {
		return labInsideLoc;
	}



	public void setLabInsideLoc(String labInsideLoc) {
		this.labInsideLoc = labInsideLoc;
	}



	public String getSubCat() {
		return subCat;
	}



	public void setSubCat(String subCat) {
		this.subCat = subCat;
	}



	public String getLaserPrint() {
		return laserPrint;
	}



	public void setLaserPrint(String laserPrint) {
		this.laserPrint = laserPrint;
	}



	public String getBarCodePrint() {
		return barCodePrint;
	}



	public void setBarCodePrint(String barCodePrint) {
		this.barCodePrint = barCodePrint;
	}



	@Override
	public String toString() {
		return "AssetInventory [asset_inventory_id=" + asset_inventory_id + ", machine=" + machine + ", equipmentId="
				+ equipmentId + ", model=" + model + ", comapnyName=" + comapnyName + ", loginName=" + loginName
				+ ", opSystem=" + opSystem + ", ramCapacity=" + ramCapacity + ", hardDiskCap=" + hardDiskCap
				+ ", category=" + category + ", lab=" + lab + ", it_Dept=" + it_Dept + ", plant_Loc=" + plant_Loc
				+ ", labCode=" + labCode + ", labInsideLoc=" + labInsideLoc + ", subCat=" + subCat + ", laserPrint="
				+ laserPrint + ", barCodePrint=" + barCodePrint + "]";
	}



	

	
	
	
	
	
}
