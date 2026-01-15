package com.kfMaintenancce.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterModule;

public class EnergyMeterModuleCustomeRepoImpl implements EnergyMeterModuleCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<EnergyMeterModule> getEnergyMeterModuleByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
	        int maxResult;
	        Query q = null;
	        long result = ((Long)this.entityManager
	          .createQuery("SELECT count(e) FROM EnergyMeterModule e where  e.companyName LIKE :searchText OR  e.moduleNo LIKE :searchText")
	          
	          .setParameter("searchText", "%" + searchText + "%").getSingleResult()).longValue();
	        TypedQuery typedQuery = this.entityManager.createQuery("from EnergyMeterModule e where  e.companyName LIKE :searchText OR  e.moduleNo LIKE :searchText", EnergyMeterModule.class);
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
	        List<EnergyMeterModule> list = typedQuery.getResultList();
	        return list;
	      } finally {
	        this.entityManager.close();
	      } 
	}

	@Override
	public int getEnergyMeterModuleCountByLimitAndSearch(String searchText) {
		  long result = ((Long)this.entityManager
		          .createQuery("SELECT count(e) FROM EnergyMeterModule e where  e.companyName LIKE :searchText OR  e.moduleNo LIKE :searchText")
		          
		          .setParameter("searchText", "%" + searchText + "%").getSingleResult()).longValue();
		// TODO Auto-generated method stub
		return (int) result;
	}

	@Override
	public List<EnergyMeterModule> getEnergyMeterModuleByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM EnergyMeterModule e ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select e from  EnergyMeterModule e ",
					EnergyMeterModule.class);
			

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<EnergyMeterModule> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCountOfEnergyMeterModule() {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		result = (long) entityManager
				.createQuery(
						"SELECT count(*) FROM EnergyMeterModule e ")
				.getSingleResult();
		return (int) result;
	}

}
