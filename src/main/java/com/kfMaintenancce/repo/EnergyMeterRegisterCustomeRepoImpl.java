package com.kfMaintenancce.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterRegister;

public class EnergyMeterRegisterCustomeRepoImpl implements EnergyMeterRegisterCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<EnergyMeterRegister> getEnergyMeterRegisterByLimitAndSearch(String searchText, int pageNo, int perPage) {
	    try {
	        int maxResult;
	        Query q = null;
	        String baseQuery;
	        String countQuery;

	        // ✅ If search text is empty → no filtering
	        if (searchText == null || searchText.trim().isEmpty()) {
	            baseQuery = "SELECT e FROM EnergyMeterRegister e LEFT JOIN e.module m ORDER BY e.id DESC";
	            countQuery = "SELECT count(e) FROM EnergyMeterRegister e";
	        } else {
	            baseQuery = "SELECT e FROM EnergyMeterRegister e LEFT JOIN e.module m " +
	                        "WHERE e.registerNo LIKE :searchText OR " +
	                        "e.registerName LIKE :searchText OR " +
	                        "m.companyName LIKE :searchText OR " +
	                        "m.moduleNo LIKE :searchText " +
	                        "ORDER BY e.id DESC";

	            countQuery = "SELECT count(e) FROM EnergyMeterRegister e LEFT JOIN e.module m " +
	                         "WHERE e.registerNo LIKE :searchText OR " +
	                         "e.registerName LIKE :searchText OR " +
	                         "m.companyName LIKE :searchText OR " +
	                         "m.moduleNo LIKE :searchText";
	        }

	        // ✅ Count total records
	        q = this.entityManager.createQuery(countQuery);
	        if (searchText != null && !searchText.trim().isEmpty()) {
	            q.setParameter("searchText", "%" + searchText + "%");
	        }
	        long result = ((Long) q.getSingleResult()).longValue();

	        // ✅ Data query
	        TypedQuery<EnergyMeterRegister> typedQuery = this.entityManager.createQuery(baseQuery, EnergyMeterRegister.class);
	        if (searchText != null && !searchText.trim().isEmpty()) {
	            typedQuery.setParameter("searchText", "%" + searchText + "%");
	        }

	        int total_count = (int) result;

	        // ✅ Correct pagination
	        int firstR = (pageNo - 1) * perPage;
	        if (firstR < 0)
	            firstR = 0;

	        if (perPage > total_count) {
	            maxResult = total_count;
	        } else {
	            maxResult = perPage;
	        }

	        typedQuery.setFirstResult(firstR);
	        typedQuery.setMaxResults(maxResult);

	        List<EnergyMeterRegister> list = typedQuery.getResultList();
	        return list;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    } finally {
	        // ⚠️ Remove this if entityManager is Spring-managed
	        // this.entityManager.close();
	    }
	}



	
	@Override
	public int getEnergyMeterRegisterCountByLimitAndSearch(String searchText) {
	    try {
	        Query q;
	        long result = 0;

	        if (searchText == null || searchText.trim().isEmpty()) {
	            // ✅ No search text → count all
	            q = this.entityManager.createQuery("SELECT count(e) FROM EnergyMeterRegister e");
	        } else {
	            // ✅ With search text (using LEFT JOIN for null-safe module search)
	            q = this.entityManager.createQuery(
	                "SELECT count(e) FROM EnergyMeterRegister e LEFT JOIN e.module m WHERE " +
	                "e.registerNo LIKE :searchText OR " +
	                "e.registerName LIKE :searchText OR " +
	                "m.companyName LIKE :searchText OR " +
	                "m.moduleNo LIKE :searchText"
	            );
	            q.setParameter("searchText", "%" + searchText + "%");
	        }

	        result = (Long) q.getSingleResult();
	        return (int) result;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        // ⚠️ Comment out if EntityManager is container-managed
	        // this.entityManager.close();
	    }
	}


	@Override
	public List<EnergyMeterRegister> getEnergyMeterRegisterByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM EnergyMeterRegister e ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select e from  EnergyMeterRegister e ",
					EnergyMeterRegister.class);
			

		
			 int total_count = (int)result;
			 int maxResult;
		        int firstR = total_count - pageNo * perPage;
		        int maxR = total_count - (pageNo - 1) * perPage;
		        if (firstR < 0)
		          firstR = 0; 
		        if (maxR < 10) {
		          maxResult = maxR;
		        } else {
		          maxResult = perPage;
		        } 
		        q.setMaxResults(maxResult);
		    
		        q.setFirstResult(firstR);
		        q.setMaxResults(maxResult);
		        
		        System.out.println(" pageNo "+pageNo);
		        System.out.println(" perPage "+perPage);
		        
		        
		        System.out.println(" firstR "+firstR);
		        System.out.println(" firstR "+maxResult);
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<EnergyMeterRegister> list = q.getResultList();
			
			System.out.println("Size "+list.size());
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCountOfEnergyMeterRegister() {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		result = (long) entityManager
				.createQuery(
						"SELECT count(*) FROM EnergyMeterRegister e")
				.getSingleResult();
		return (int) result;
	}

}
