package com.kfMaintenancce.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.EnergyMeterRegisterGroup;

public class EnergyMeterRegisterGroupCustomeRepoImpl implements EnergyMeterRegisterGroupCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimitAndSearch(String searchText, int pageNo,
			int perPage) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				try {
					// TODO Auto-generated method stub
					long result = 0;
					Query q = null;
					
					
					result = (long) entityManager
							.createQuery(
									"SELECT count(a) FROM EnergyMeterRegisterGroup a where  a.groupName LIKE :searchText")
							.setParameter("searchText", "%" + searchText + "%").getSingleResult();
					 q = entityManager.createQuery("from EnergyMeterRegisterGroup a where a.groupName LIKE :searchText ",
							 EnergyMeterRegisterGroup.class);
						
						
						int total_count = (int) result;
						
						int firstR = total_count - (pageNo * perPage);
						int maxR = total_count - ((pageNo - 1) * perPage);
						if (firstR < 0) {
							firstR = 0;
						}
						q.setParameter("searchText", "%" + searchText + "%");
						q.setFirstResult(firstR); // modify this to adjust paging
						q.setMaxResults(maxR);
						List<EnergyMeterRegisterGroup> list = q.getResultList();
						return list;
					
					
					
				} finally {
					// TODO: handle finally clause
					entityManager.close();
				}
	}

	@Override
	public int getEnergyMeterRegisterGroupCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		
		
		result = (long) entityManager
				.createQuery(
						"SELECT count(a) FROM EnergyMeterRegisterGroup a where  a.groupName LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		 q = entityManager.createQuery("from EnergyMeterRegisterGroup a where a.groupName LIKE :searchText ",
				 EnergyMeterRegisterGroup.class);
			
			
			
			int total_count = (int) result;
			
		return total_count;
	}

	@Override
	public List<EnergyMeterRegisterGroup> getEnergyMeterRegisterGroupByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM EnergyMeterRegisterGroup a ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select a from  EnergyMeterRegisterGroup a ",
					EnergyMeterRegisterGroup.class);
			System.out.println("Count  " + result);

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<EnergyMeterRegisterGroup> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

}
