package com.kfMaintenancce.repo;

import com.kfMaintenancce.model.RolePermissionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolePermissionActionRepo extends JpaRepository<RolePermissionAction, Integer> {
  @Query("from RolePermissionAction r where r.permissionsAction.permissionAsactionId=?1 and r.role.role_id=?2")
  Optional<RolePermissionAction> getRolePermissionActionByRoleAndPermission(int paramInt1, int paramInt2);
}
