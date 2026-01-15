package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role_mst")
public class Role {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id;
	
	
	
	

	@Column(name="role_name")
	private String roleName;




	// Constructor with role_id parameter
    public Role(int role_id) {
        this.role_id = role_id;
    }
    
    // Default constructor
    public Role() {
        // Default constructor is needed by Hibernate
    }

	public int getRole_id() {
		return role_id;
	}





	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}





	public String getRoleName() {
		return roleName;
	}





	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", roleName=" + roleName + "]";
	}
	
	
	
	
	

}
