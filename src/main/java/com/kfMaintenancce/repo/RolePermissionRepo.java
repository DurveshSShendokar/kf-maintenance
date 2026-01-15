package com.kfMaintenancce.repo;


import com.kfMaintenancce.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepo extends JpaRepository<RolePermission, Integer> {
  @Query("from  RolePermission p where p.role.role_id=?1 and p.permissions.permissionsId=?2")
  Optional<RolePermission> getRolePermissionByRoleAndPermission(int paramInt1, int paramInt2);
  
  @Query("from  RolePermission p where p.role.role_id=?1")
  List<RolePermission> getPermissionsByRole(int paramInt);
  
  @Query("select count(p) from  RolePermission p where  p.permissions.category=?1")
  int getPermissionsCountByCategory(String paramString);
  
  @Query(" from  RolePermission p where p.role.role_id=?1  and  p.permissions.category=?2")
  List<RolePermission> getPermissionsByRoleAndCatrgory(int paramInt, String paramString);
}
