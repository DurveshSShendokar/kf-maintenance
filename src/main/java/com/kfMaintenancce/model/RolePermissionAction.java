package com.kfMaintenancce.model;

import javax.persistence.*;

@Entity
@Table(name = "role_permissions_action")
public class RolePermissionAction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_permissions_action_id")
  private int rolePermissionAsactionId;
  
  @ManyToOne
  @JoinColumn(name = "permissions_action_id")
  private PermissionAction permissionsAction;
  
  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
  
  public int getRolePermissionAsactionId() {
    return this.rolePermissionAsactionId;
  }
  
  public void setRolePermissionAsactionId(int rolePermissionAsactionId) {
    this.rolePermissionAsactionId = rolePermissionAsactionId;
  }
  
  public PermissionAction getPermissionsAction() {
    return this.permissionsAction;
  }
  
  public void setPermissionsAction(PermissionAction permissionsAction) {
    this.permissionsAction = permissionsAction;
  }
  
  public Role getRole() {
    return this.role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
}
