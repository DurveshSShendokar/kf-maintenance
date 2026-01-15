package com.kfMaintenancce.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "access_user_details")
public class UserDetails {



	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "gender")
	private String gender;

	@Column(name = "password")
	private String password;

    @Transient
    private String newPassword;

	@Column(name = "emp_id")
	private Integer empId;

	
	@Column(name = "emp_code")
	private String empCode;

	
	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "user_name",unique = true)
	private String userName;

	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	
    @Column(name = "active")
    private Integer active = 1;


	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	  @CreationTimestamp
	  
	    private LocalDateTime addedDateTime;
	     
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<UserLabMapping> userLabMappings;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public List<UserLabMapping> getUserLabMappings() {
		return userLabMappings;
	}

	public void setUserLabMappings(List<UserLabMapping> userLabMappings) {
		this.userLabMappings = userLabMappings;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}



	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	

	

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", gender=" + gender + ", password=" + password + ", empId=" + empId + ", contactNo=" + contactNo
				+ ", userName=" + userName + ", role=" + role + ", active=" + active + ", department=" + department
				+ ", userLabMappings=" + userLabMappings + "]";
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public LocalDateTime getAddedDateTime() {
		return addedDateTime;
	}

	public void setAddedDateTime(LocalDateTime addedDateTime) {
		this.addedDateTime = addedDateTime;
	}

	

	

}
