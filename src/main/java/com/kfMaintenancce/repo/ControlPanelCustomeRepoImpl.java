package com.kfMaintenancce.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.kfMaintenancce.model.ControlPanel;
import com.kfMaintenancce.model.EnergyMeterRegister;

public class ControlPanelCustomeRepoImpl implements ControlPanelCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<ControlPanel> getControlPanelByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		  try {
		        int maxResult;
		        Query q = null;
		        long result = ((Long)this.entityManager
		          .createQuery("SELECT count(c) FROM ControlPanel c where c.panelName LIKE :searchText OR  c.panelIpAddress LIKE :searchText OR c.panelPort LIKE :searchText OR c.locationName LIKE :searchText OR c.plantName LIKE :searchText")
		          
		          .setParameter("searchText", "%" + searchText + "%").getSingleResult()).longValue();
		        TypedQuery typedQuery = this.entityManager.createQuery("from ControlPanel c where c.panelName LIKE :searchText OR  c.panelIpAddress LIKE :searchText OR c.panelPort LIKE :searchText OR c.locationName LIKE :searchText OR c.plantName LIKE :searchText", ControlPanel.class);
		        int total_count = (int)result;
		        int firstR = total_count - pageNo * perPage;
		        int maxR = total_count - (pageNo - 1) * perPage;
		        if (firstR < 0)
		          firstR = 0; 
		        if (maxR < 10) {
		          maxResult = maxR;
		        } else {
		          maxResult = perPage;
		        } 
		        typedQuery.setMaxResults(maxResult);
		        typedQuery.setParameter("searchText", "%" + searchText + "%");
		        typedQuery.setFirstResult(firstR);
		        typedQuery.setMaxResults(maxResult);
		        List<ControlPanel> list = typedQuery.getResultList();
		        return list;
		      } finally {
		        this.entityManager.close();
		      }
	}

	@Override
	public int getControlPanelCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		   long result = ((Long)this.entityManager
			          .createQuery("SELECT count(c) FROM ControlPanel c where c.panelName LIKE :searchText OR  c.panelIpAddress LIKE :searchText OR c.panelPort LIKE :searchText OR c.locationName LIKE :searchText OR c.plantName LIKE :searchText")
			          
			          .setParameter("searchText", "%" + searchText + "%").getSingleResult()).longValue();
		return (int) result;
	}

	@Override
	public List<ControlPanel> getControlPanelByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM ControlPanel c ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select c from  ControlPanel c ",
					ControlPanel.class);
			

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<ControlPanel> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCountOfControlPanel() {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		result = (long) entityManager
				.createQuery(
						"SELECT count(*) FROM ControlPanel c ")
				.getSingleResult();
		return (int) result;
	}

}
