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
import javax.persistence.Transient;



@Entity

@Table(name="machine_owner")
public class MachineOwner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int machine_owner_id;
	
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine  machine;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails  user;
	
	@Transient
	private String machineName;
	
	
	@Column(name="added_by")
	private String addedBy;
	
	@Column(name="added_date")
	private Date addedDate;

	public int getMachine_owner_id() {
		return machine_owner_id;
	}

	public void setMachine_owner_id(int machine_owner_id) {
		this.machine_owner_id = machine_owner_id;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	
}
