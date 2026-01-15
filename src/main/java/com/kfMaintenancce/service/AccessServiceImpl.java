package com.kfMaintenancce.service;


import com.kfMaintenancce.model.*;

import com.kfMaintenancce.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessServiceImpl implements AccessService {
  @Autowired
  PermissionsRepo permissionsRepo;
  
  @Autowired
  RolePermissionRepo rolPermissionsRepo;
  
  @Autowired
  RoleRepo roleRepo;
  
  @Autowired
  PermissionActionRepo permissionActionRepo;
  
  public Optional<Permissions> getPermissionsByName(String permissionsName) {
    return this.permissionsRepo.getPermissionsByName(permissionsName);
  }
  
  public void addPermission(Permissions permissions) {
    this.permissionsRepo.save(permissions);
  }
  
  public List<Permissions> getPermissionsByPagination(int page_no, int item_per_page) {
    return this.permissionsRepo.getPermissionsByPagination(page_no, item_per_page);
  }
  
  public List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search) {
    return this.permissionsRepo.getPermissionsByPaginationAndSerach(page_no, item_per_page, search);
  }
  
  public int getPermissionsCount() {
    return this.permissionsRepo.getPermissionsCount();
  }
  
  public int getPermissionsCountBySearch(String search) {
    return this.permissionsRepo.getPermissionsCountBySearch(search);
  }
  
  public void deletePermission(Permissions permissions) {
    this.permissionsRepo.delete(permissions);
  }
  
  public List<Permissions> getAllPermission() {
    return this.permissionsRepo.findAll();
  }
  
  public Optional<RolePermission> getRolePermissionByRoleAndPermission(int roleId, int permissionsId) {
    System.out.println("ROle " + roleId + "  " + permissionsId);
    return this.rolPermissionsRepo.getRolePermissionByRoleAndPermission(roleId, permissionsId);
  }
  
  public void saveRolePermission(RolePermission rolepermission) {
    this.rolPermissionsRepo.save(rolepermission);
  }
  
  public void deleteRolePermission(RolePermission rolePermission) {
    this.rolPermissionsRepo.delete(rolePermission);
  }
  
  public List<RolePermission> getPermissionsByRole(int roleId) {
    return this.rolPermissionsRepo.getPermissionsByRole(roleId);
  }
  
  public Optional<Permissions> getPermissionsByNameAndCategory(String category, String permissionsName) {
    return this.permissionsRepo.getPermissionsByNameAndCategory(category, permissionsName);
  }
  
  public Optional<Role> getRoleByName(String roleName) {
    return this.roleRepo.getRoleByName(roleName);
  }
  
  public void addRole(Role role) {
    this.roleRepo.save(role);
  }
  
  public void savePermissionAction(PermissionAction action) {
    this.permissionActionRepo.save(action);
  }
  
  public void deleteAllPermissionAction(List<PermissionAction> permissionActions) {
    this.permissionActionRepo.deleteAll(permissionActions);
  }
  
  public List<Permissions> getPermissionByCategory(String category) {
    return this.permissionsRepo.getPermissionByCategory(category);
  }
  
  public int getPermissionsCountByCategory(String category) {
    return this.rolPermissionsRepo.getPermissionsCountByCategory(category);
  }
  
  public int getPermissionsMasterCategory(String category) {
    return this.permissionsRepo.getPermissionsMasterCategory(category);
  }
  
  public List<RolePermission> getPermissionsByRoleAndCatrgory(int roleId, String category) {
    return this.rolPermissionsRepo.getPermissionsByRoleAndCatrgory(roleId, category);
  }
  
  public List<Permissions> getPermissionsByCategory(String category) {
    return this.permissionsRepo.getPermissionsByCategory(category);
  }
  
  public List<PermissionAction> getPermissionActionBYPermissionId(int permissionsId) {
    return this.permissionActionRepo.getPermissionActionBYPermissionId(permissionsId);
  }
  
  public List<Role> getAllRoles() {
    return this.roleRepo.findAll();
  }
  
  public Role getRoleById(int roleId) {
    return (Role)this.roleRepo.getById(Integer.valueOf(roleId));
  }
  
  public Permissions getPermissionsById(int permissionId) {
    return (Permissions)this.permissionsRepo.getById(Integer.valueOf(permissionId));
  }
  
  public Optional<PermissionAction> getPermissionActionBYPermissionIdAndActionName(int permissionsId, String actionName) {
    return this.permissionActionRepo.getPermissionActionBYPermissionIdAndActionName(permissionsId, actionName);
  }
  
  public List<Permissions> getAllPermissions() {
    return this.permissionsRepo.findAll();
  }

  @Override
  public List<String> getAllCateories() {
    return permissionsRepo.getAllCateories();
  }
}
