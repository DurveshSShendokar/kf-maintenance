package com.kfMaintenancce.repo;


import com.kfMaintenancce.model.Permissions;

import java.util.List;

public interface PermissionsCustomeRepo {
  List<Permissions> getPermissionsByPagination(int paramInt1, int paramInt2);
  
  List<Permissions> getPermissionsByPaginationAndSerach(int paramInt1, int paramInt2, String paramString);
  
  int getPermissionsCount();
  
  int getPermissionsCountBySearch(String paramString);
}
