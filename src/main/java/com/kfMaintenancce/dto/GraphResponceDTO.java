package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.UserDetails;

public class GraphResponceDTO {
	private UserDetails user;
	private int open;
	private int trail;
	private int closed;
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getTrail() {
		return trail;
	}
	public void setTrail(int trail) {
		this.trail = trail;
	}
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	
	
	
	
	
	
	

}
