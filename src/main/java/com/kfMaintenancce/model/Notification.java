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
@Table(name="notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="title")
	private String title;
	
	@Column(name="message")
	private String message;
	
	
	@Column(name="notification_for")
	private String notificationFor;
	
	@Column(name="notification_for_spec_id" ,columnDefinition = "int default 0")
	private int notificationForSpecId;
	
	@Column(name="notification_dept")
	private String notificationDept;
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	
	@Column(name="raised_time")
	private Date raisedTime;
	
	@Column(name="view_time")
	private Date viewedTime;
	
	
	@Column(name="viewed" , columnDefinition = "int default 0")
	private int viewed;
	
	
	public String getNotificationFor() {
		return notificationFor;
	}


	public void setNotificationFor(String notificationFor) {
		this.notificationFor = notificationFor;
	}


	public int getNotificationForSpecId() {
		return notificationForSpecId;
	}


	public void setNotificationForSpecId(int notificationForSpecId) {
		this.notificationForSpecId = notificationForSpecId;
	}


	public String getNotificationDept() {
		return notificationDept;
	}


	public void setNotificationDept(String notificationDept) {
		this.notificationDept = notificationDept;
	}


	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public Date getRaisedTime() {
		return raisedTime;
	}


	public void setRaisedTime(Date raisedTime) {
		this.raisedTime = raisedTime;
	}


	public Date getViewedTime() {
		return viewedTime;
	}


	public void setViewedTime(Date viewedTime) {
		this.viewedTime = viewedTime;
	}


	public int getViewed() {
		return viewed;
	}


	public void setViewed(int viewed) {
		this.viewed = viewed;
	}


	public void setNotificationfor(String notificationFor) {
		// TODO Auto-generated method stub
		this.notificationFor = notificationFor;
	}


	public void setNotificationForSepeId(int notificationForSpecId) {
		// TODO Auto-generated method stub
		this.notificationForSpecId=notificationForSpecId;
	}
	
	
	
	
}
