package com.kfMaintenancce.repo;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.kfMaintenancce.model.Permissions;

public class PermissionsCustomeRepoImpl implements PermissionsCustomeRepo {
  @PersistenceContext
  EntityManager entityManager;
  
  public List<Permissions> getPermissionsByPagination(int page_no, int item_per_page) {
    long result = ((Long)this.entityManager.createQuery("SELECT count(p) FROM Permissions p").getSingleResult()).longValue();
    int total_count = (int)result;
    TypedQuery typedQuery = this.entityManager.createQuery("from Permissions", Permissions.class);
    int firstR = total_count - page_no * item_per_page;
    int maxR = total_count - (page_no - 1) * item_per_page;
    if (firstR < 0)
      firstR = 0; 
    typedQuery.setFirstResult(firstR);
    typedQuery.setMaxResults(maxR);
    List<Permissions> list = typedQuery.getResultList();
    return list;
  }
  
  public List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search) {
    long result = ((Long)this.entityManager.createQuery("SELECT count(p) from Permissions p  where p.permissionsName LIKE :searchText").setParameter("searchText", "%" + search + "%").getSingleResult()).longValue();
    int total_count = (int)result;
    TypedQuery typedQuery = this.entityManager.createQuery("from Permissions p  where p.permissionsName LIKE :searchText", Permissions.class);
    int firstR = total_count - page_no * item_per_page;
    int maxR = total_count - (page_no - 1) * item_per_page;
    if (firstR < 0)
      firstR = 0; 
    typedQuery.setParameter("searchText", "%" + search + "%");
    typedQuery.setFirstResult(firstR);
    typedQuery.setMaxResults(maxR);
    List<Permissions> list = typedQuery.getResultList();
    return list;
  }
  
  public int getPermissionsCount() {
    long result = ((Long)this.entityManager.createQuery("SELECT count(p) FROM Permissions p").getSingleResult()).longValue();
    int total_count = (int)result;
    return total_count;
  }
  
  public int getPermissionsCountBySearch(String search) {
    long result = ((Long)this.entityManager.createQuery("SELECT count(p) from Permissions p  where p.permissionsName LIKE :searchText").setParameter("searchText", "%" + search + "%").getSingleResult()).longValue();
    int total_count = (int)result;
    return total_count;
  }
}
