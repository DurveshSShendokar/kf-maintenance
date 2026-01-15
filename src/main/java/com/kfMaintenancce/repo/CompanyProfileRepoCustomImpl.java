package com.kfMaintenancce.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kfMaintenancce.dto.ColumnSearch;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.CompanyProfile;

public class CompanyProfileRepoCustomImpl implements CompanyProfileRepoCustom {
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<CompanyProfile> getCompanyProfileByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM CompanyProfile a ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select a from  CompanyProfile a ",
					CompanyProfile.class);
			System.out.println("Count  " + result);

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<CompanyProfile> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public List<CompanyProfile> getCompanyProfileByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		int pageNo = groupSearchDTO.getPageNo();
		int perPage = groupSearchDTO.getPerPage();

		Query q = null;

		String queryStr = "from CompanyProfile a where ";
		int i = 0;
		for (ColumnSearch columnSearch : groupSearchDTO.getColumns()) {

			if (columnSearch.getValue() != "" || !columnSearch.getValue().equalsIgnoreCase("")) {
				if (i == 0) {
					queryStr += " a." + columnSearch.getColumnName() + " LIKE : searchText"+i;

				} else {
					queryStr += " AND a." + columnSearch.getColumnName() + " LIKE : searchText"+i;

				}
			}
			i++;
		}


		System.out.println("QUERY STRING " + queryStr);

		q = entityManager.createQuery(queryStr, CompanyProfile.class);

		int j=0;
		for (ColumnSearch columnSearch : groupSearchDTO.getColumns()) {

			if (columnSearch.getValue() != "" || !columnSearch.getValue().equalsIgnoreCase("")) {

				System.out.println("Column  " + columnSearch.getColumnName());
				System.out.println("Value  " + columnSearch.getValue());

				q.setParameter("searchText"+j, "%" + columnSearch.getValue() + "%");

			}
			j++;
		}
		int total_count = q.getResultList().size();
		System.out.println("total_count " + total_count);

		int first=(pageNo-1) * perPage;
		q.setMaxResults(perPage);

		q.setFirstResult(first); // modify this to adjust paging
		q.setMaxResults(perPage);
		List<CompanyProfile> list = q.getResultList();
		System.out.println("Value  " + list.size());

		return list;

	}

	@Override
	public int getCompanyProfileCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub

		int pageNo = groupSearchDTO.getPageNo();
		int perPage = groupSearchDTO.getPerPage();

		Query q = null;

		String queryStr = "from CompanyProfile a  where";
		int i = 0;
		for (ColumnSearch columnSearch : groupSearchDTO.getColumns()) {

			if (columnSearch.getValue() != "" || !columnSearch.getValue().equalsIgnoreCase("")) {
				if (i == 0) {
					queryStr += " a." + columnSearch.getColumnName() + " LIKE : searchText"+i;

				} else {
					queryStr += " AND a." + columnSearch.getColumnName() + " LIKE : searchText"+i;

				}
			}
			i++;
		}


		System.out.println("QUERY STRING " + queryStr);

		q = entityManager.createQuery(queryStr, CompanyProfile.class);

		int j=0;
		for (ColumnSearch columnSearch : groupSearchDTO.getColumns()) {

			if (columnSearch.getValue() != "" || !columnSearch.getValue().equalsIgnoreCase("")) {

				System.out.println("Column  " + columnSearch.getColumnName());
				System.out.println("Value  " + columnSearch.getValue());

				q.setParameter("searchText"+j, "%" + columnSearch.getValue() + "%");

			}
			j++;
		}
		int total_count = q.getResultList().size();
		return total_count;
	}


}
