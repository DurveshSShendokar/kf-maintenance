package com.kfMaintenancce.service;



import com.kfMaintenancce.model.PermissionAction;
import com.kfMaintenancce.model.Permissions;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.model.RolePermission;

import java.util.List;
import java.util.Optional;

public interface AccessService {
  Optional<Permissions> getPermissionsByName(String paramString);
  
  void addPermission(Permissions paramPermissions);
  
  List<Permissions> getPermissionsByPagination(int paramInt1, int paramInt2);
  
  List<Permissions> getPermissionsByPaginationAndSerach(int paramInt1, int paramInt2, String paramString);
  
  int getPermissionsCount();
  
  int getPermissionsCountBySearch(String paramString);
  
  void deletePermission(Permissions paramPermissions);
  
  List<Permissions> getAllPermission();
  
  Optional<RolePermission> getRolePermissionByRoleAndPermission(int paramInt1, int paramInt2);
  
  void saveRolePermission(RolePermission paramRolePermission);
  
  void deleteRolePermission(RolePermission paramRolePermission);
  
  List<RolePermission> getPermissionsByRole(int paramInt);
  
  Optional<Permissions> getPermissionsByNameAndCategory(String paramString1, String paramString2);
  
  Optional<Role> getRoleByName(String paramString);
  
  void addRole(Role paramRole);
  
  void savePermissionAction(PermissionAction paramPermissionAction);
  
  void deleteAllPermissionAction(List<PermissionAction> paramList);
  
  List<Permissions> getPermissionByCategory(String paramString);
  
  int getPermissionsCountByCategory(String paramString);
  
  int getPermissionsMasterCategory(String paramString);
  
  List<RolePermission> getPermissionsByRoleAndCatrgory(int paramInt, String paramString);
  
  List<Permissions> getPermissionsByCategory(String paramString);
  
  List<PermissionAction> getPermissionActionBYPermissionId(int paramInt);
  
  List<Role> getAllRoles();
  
  Role getRoleById(int paramInt);
  
  Permissions getPermissionsById(int paramInt);
  
  Optional<PermissionAction> getPermissionActionBYPermissionIdAndActionName(int paramInt, String paramString);
  
  List<Permissions> getAllPermissions();

  List<String> getAllCateories();
}
