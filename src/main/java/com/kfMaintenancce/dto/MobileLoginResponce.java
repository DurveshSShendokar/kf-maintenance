package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.UserDetails;

public class MobileLoginResponce {
	private int statusCode;
	private String  message;
	private UserDetails user;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
	
	
	
	
}
