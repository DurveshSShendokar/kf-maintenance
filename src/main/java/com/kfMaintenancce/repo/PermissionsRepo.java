package com.kfMaintenancce.repo;


import com.kfMaintenancce.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionsRepo extends JpaRepository<Permissions, Integer>, PermissionsCustomeRepo {
  @Query("From Permissions p where trim(p.permissionsName)=?1")
  Optional<Permissions> getPermissionsByName(String paramString);
  
  @Query("From Permissions p where p.category=?1 and p.permissionsName=?2")
  Optional<Permissions> getPermissionsByNameAndCategory(String paramString1, String paramString2);
  
  @Query("From Permissions p where p.category=?1")
  List<Permissions> getPermissionByCategory(String paramString);
  
  @Query("select count(p) From Permissions p where p.category=?1")
  int getPermissionsMasterCategory(String paramString);
  
  @Query("From Permissions p where p.category=?1")
  List<Permissions> getPermissionsByCategory(String paramString);
  @Query("select distinct category From Permissions p ")
    List<String> getAllCateories();
}
