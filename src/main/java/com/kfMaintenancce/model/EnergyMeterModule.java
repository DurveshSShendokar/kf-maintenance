package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="engergy_meter_module")
public class EnergyMeterModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="module_no")
	private String moduleNo;
	
	
	@Column(name="create_date_time")
	private Date createDateTime;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	
	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getModuleNo() {
		return moduleNo;
	}


	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}


	public Date getCreateDateTime() {
		return createDateTime;
	}


	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}


	@Override
	public String toString() {
		return "EnergyMeterModule [id=" + id + ", companyName=" + companyName + ", moduleNo=" + moduleNo
				+ ", createDateTime=" + createDateTime + "]";
	}
	
	
}
