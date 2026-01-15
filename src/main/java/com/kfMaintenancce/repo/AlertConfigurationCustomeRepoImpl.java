package com.kfMaintenancce.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AlertConfiguration;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.EnergyMeterMaster;

public class AlertConfigurationCustomeRepoImpl implements AlertConfigurationCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<AlertConfiguration> getAlertConfigurationByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM AlertConfiguration a ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select a from  AlertConfiguration a ",
					AlertConfiguration.class);
			System.out.println("Count  " + result);

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<AlertConfiguration> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}
	@Override
	public int getCountAlertConfigurationBySearch(String searchText) {
		// TODO Auto-generated method stub

		long result = (long) entityManager
				.createQuery(
						"SELECT count(a) FROM AlertConfiguration a where  a.alertFor LIKE :searchText OR  a.registerNo LIKE :searchText OR a.registerName LIKE :searchText OR a.fromRange LIKE :searchText OR a.toRange LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
	

	
		return (int) result;
	}
	@Override
	public List<AlertConfiguration> getAlertConfigurationCountAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		 try {
		        int maxResult;
		        Query q = null;
		        long result = ((Long)this.entityManager
		          .createQuery("SELECT count(a) FROM AlertConfiguration a where   a.alertFor LIKE :searchText OR  a.registerNo LIKE :searchText OR a.registerName LIKE :searchText OR a.fromRange LIKE :searchText OR a.toRange LIKE :searchText")
		          
		          .setParameter("searchText", "%" + searchText + "%").getSingleResult()).longValue();
		        TypedQuery typedQuery = this.entityManager.createQuery("from AlertConfiguration a where   a.alertFor LIKE :searchText OR  a.registerNo LIKE :searchText OR a.registerName LIKE :searchText OR a.fromRange LIKE :searchText OR a.toRange LIKE :searchText", AlertConfiguration.class);
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
		        List<AlertConfiguration> list = typedQuery.getResultList();
		        return list;
		      } finally {
		        this.entityManager.close();
		      } 
	}


}
