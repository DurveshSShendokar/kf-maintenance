package com.kfMaintenancce.dto;

import java.util.List;

public class UserManualStatus {

	private int code;
	private String message;
	

	public UserManualStatus() {
	}

	public UserManualStatus(String message) {
		
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	
	
}
