package com.kfMaintenancce.dto;

public class MobileLoginRequest {
 public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
private String userId;
 private String userName;
 private String password;
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public String toString() {
	return "MobileLogin [userId=" + userId + ", userName=" + userName + ", password=" + password + "]";
}
 
 
 
}
