package com.kfMaintenancce.model;

import javax.persistence.*;

@Entity
@Table(name = "permission_request")
public class PermissionRequest {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private int id;
	 @ManyToOne
	  @JoinColumn(name = "permission_id")
	  private Permissions  permissions;
	 @ManyToOne
	  @JoinColumn(name = "permission_action_id")
	  private PermissionAction  permissionAction;
	 
	 
	 @ManyToOne
	  @JoinColumn(name = "user_id")
	  private UserDetails userInfo;
	 
	  
	  @Column(name = "remark")
	  private String remark;
	  
	  @Column(name = "approved")
	  private int approved;


	public int getApproved() {
		return approved;
	}


	public void setApproved(int approved) {
		this.approved = approved;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Permissions getPermissions() {
		return permissions;
	}


	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}


	public PermissionAction getPermissionAction() {
		return permissionAction;
	}


	public void setPermissionAction(PermissionAction permissionAction) {
		this.permissionAction = permissionAction;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public UserDetails getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserDetails userInfo) {
		this.userInfo = userInfo;
	}
	  
	  
}
