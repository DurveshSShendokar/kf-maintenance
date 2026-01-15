package com.kfMaintenancce.model;

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
@Table(name="employee_mst")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employee_id;
	
	
	
	

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile")
	private String mobile;
	
	
	@Column(name="manager")
	private String manager;
	
	@Column(name="user_created")
	private int userCreated;
	

	@ManyToOne
	@JoinColumn(name="lab_id")
	private Lab lab;
	
	

	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	
	

	
	
	

	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;








	public int getEmployee_id() {
		return employee_id;
	}








	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}








	public String getFirstName() {
		return firstName;
	}








	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}








	public String getLastName() {
		return lastName;
	}








	public void setLastName(String lastName) {
		this.lastName = lastName;
	}








	public String getEmail() {
		return email;
	}








	public void setEmail(String email) {
		this.email = email;
	}








	public String getMobile() {
		return mobile;
	}








	public void setMobile(String mobile) {
		this.mobile = mobile;
	}








	public String getManager() {
		return manager;
	}








	public void setManager(String manager) {
		this.manager = manager;
	}








	public int getUserCreated() {
		return userCreated;
	}








	public void setUserCreated(int userCreated) {
		this.userCreated = userCreated;
	}








	public Lab getLab() {
		return lab;
	}








	public void setLab(Lab lab) {
		this.lab = lab;
	}








	public Department getDepartment() {
		return department;
	}








	public void setDepartment(Department department) {
		this.department = department;
	}








	public Role getRole() {
		return role;
	}








	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	
	

}
