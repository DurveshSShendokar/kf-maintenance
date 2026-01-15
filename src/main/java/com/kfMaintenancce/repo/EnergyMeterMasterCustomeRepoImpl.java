package com.kfMaintenancce.repo;

import java.util.List;

import com.kfMaintenancce.model.Employee;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class EnergyMeterMasterCustomeRepoImpl implements EnergyMeterMasterCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<EnergyMeterMaster> getEnergyMeterMasterByLimitAndSearch(String searchText, int pageNo, int perPage) {
	    try {
	        Query q = null;
	        int maxResult;

	        long result = ((Long) this.entityManager
	            .createQuery("SELECT COUNT(e) FROM EnergyMeterMaster e WHERE " +
	                    "LOWER(e.panel.panelName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.locationName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.plantName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.panelIpAddress) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.panelPort) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.module.companyName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.module.moduleNo) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.energyMeterNo) LIKE LOWER(:searchText)")
	            .setParameter("searchText", "%" + searchText + "%")
	            .getSingleResult()).longValue();

	        TypedQuery<EnergyMeterMaster> typedQuery = this.entityManager.createQuery(
	            "FROM EnergyMeterMaster e WHERE " +
	                    "LOWER(e.panel.panelName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.locationName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.plantName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.panelIpAddress) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.panel.panelPort) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.module.companyName) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.module.moduleNo) LIKE LOWER(:searchText) OR " +
	                    "LOWER(e.energyMeterNo) LIKE LOWER(:searchText)", 
	            EnergyMeterMaster.class);

	        int firstR = (pageNo - 1) * perPage;
	        maxResult = perPage;

	        typedQuery.setParameter("searchText", "%" + searchText + "%");
	        typedQuery.setFirstResult(firstR);
	        typedQuery.setMaxResults(maxResult);

	        List<EnergyMeterMaster> list = typedQuery.getResultList();
	        return list;

	    } finally {
	        this.entityManager.close();
	    }
	}


	@Override
	public int getEnergyMeterMasterCountByLimitAndSearch(String searchText) {
	    try {
	        Query q;
	        long result = 0;

	        if (searchText == null || searchText.trim().isEmpty()) {
	            // ✅ No search text → count all records
	            q = this.entityManager.createQuery("SELECT COUNT(e) FROM EnergyMeterMaster e");
	        } else {
	            // ✅ With search text → case-insensitive search
	            q = this.entityManager.createQuery(
	                "SELECT COUNT(e) FROM EnergyMeterMaster e WHERE " +
	                "LOWER(e.panel.panelName) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.panel.locationName) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.panel.plantName) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.panel.panelIpAddress) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.panel.panelPort) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.module.companyName) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.module.moduleNo) LIKE LOWER(:searchText) OR " +
	                "LOWER(e.energyMeterNo) LIKE LOWER(:searchText)"
	            );
	            q.setParameter("searchText", "%" + searchText + "%");
	        }

	        result = (Long) q.getSingleResult();
	        return (int) result;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        // ✅ Close only if not managed by Spring
	        this.entityManager.close();
	    }
	}


	@Override
	public List<EnergyMeterMaster> getEnergyMeterMasterByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM EnergyMeterMaster e ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select e from  EnergyMeterMaster e ",
					EnergyMeterMaster.class);
			

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<EnergyMeterMaster> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCountOfEnergyMeterMaster() {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		result = (long) entityManager
				.createQuery(
						"SELECT count(*) FROM EnergyMeterMaster e")
				.getSingleResult();
		return (int) result;
	}

}
