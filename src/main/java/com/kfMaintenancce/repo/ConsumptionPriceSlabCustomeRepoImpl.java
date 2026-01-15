package com.kfMaintenancce.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.PriceSlabRangeDetails;

public class ConsumptionPriceSlabCustomeRepoImpl implements ConsumptionPriceSlabCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimitAndSearch(String searchText, int pageNo,
			int perPage) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			long result = 0;
			Query q = null;
			
			
			result = (long) entityManager
					.createQuery(
							"SELECT count(a) FROM ConsumptionPriceSlab a where  a.priceSlabId LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			 q = entityManager.createQuery("from ConsumptionPriceSlab a where a.priceSlabId LIKE :searchText ",
					 ConsumptionPriceSlab.class);
				
				
				int total_count = (int) result;
				
				int firstR = total_count - (pageNo * perPage);
				int maxR = total_count - ((pageNo - 1) * perPage);
				if (firstR < 0) {
					firstR = 0;
				}
				q.setParameter("searchText", "%" + searchText + "%");
				q.setFirstResult(firstR); // modify this to adjust paging
				q.setMaxResults(maxR);
				List<ConsumptionPriceSlab> list = q.getResultList();
				return list;
			
			
			
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getConsumptionPriceSlabCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		long result = 0;
		Query q = null;
		
		
		result = (long) entityManager
				.createQuery(
						"SELECT count(a) FROM ConsumptionPriceSlab a where  a.priceSlabId LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		 q = entityManager.createQuery("from ConsumptionPriceSlab a where a.priceSlabId LIKE :searchText ",
				 ConsumptionPriceSlab.class);
			
			
			int total_count = (int) result;
			
		return total_count;
	}

	@Override
	public List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			long result = 0;
			Query q = null;
			result = (long) entityManager
					.createQuery(
							"SELECT count(*) FROM ConsumptionPriceSlab a ")
					.getSingleResult();
			q = entityManager.createQuery(
					"select a from  ConsumptionPriceSlab a ",
					ConsumptionPriceSlab.class);
			System.out.println("Count  " + result);

		
			
			int first=(pageNo-1) * perPage;
			q.setFirstResult(first); // modify this to adjust paging
			q.setMaxResults(perPage);

			List<ConsumptionPriceSlab> list = q.getResultList();
			return list;

		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public ConsumptionPriceSlab getEffectivePriceSlab(Date date) {
		ConsumptionPriceSlab consumptionPriceSlabR= new ConsumptionPriceSlab();
		Query q = null;
		q = entityManager.createQuery(
				"select a from  ConsumptionPriceSlab a where a.active=1",
				ConsumptionPriceSlab.class);
		
		List<ConsumptionPriceSlab> list = q.getResultList();
		ConsumptionPriceSlab consumptionPriceSlab =list.get(0);
		
		System.out.println("Given "+date);
		System.out.println("                                                                       ");
		
		if(consumptionPriceSlab.getEffectiveFrom().compareTo(date) * date.compareTo(new Date()) >= 0)
	      {
	        System.out.println("Current Price Slab Applied");
	        consumptionPriceSlabR=consumptionPriceSlab;
	      }
	      else
	      {
	    	 
	    	  
	    		Query q1 = null;
	    		q1 = entityManager.createQuery(
	    				"select a from  ConsumptionPriceSlab a where a.active=0 and Date(a.effectiveUptoDate)>= :fromDate and Date(a.effectiveFrom) <=:fromDate",
	    				ConsumptionPriceSlab.class)	.setParameter("fromDate", date);
	    		
	    		List<ConsumptionPriceSlab> list1 = q1.getResultList();
	    		 System.out.println("Check Other Price Slab   "+list1.size());

	    		if (list1.size()!=0) {
	    			ConsumptionPriceSlab consumptionPriceSlab1 =list1.get(0);
	    			consumptionPriceSlabR=consumptionPriceSlab1;
		    	
				}
	    		 System.out.println("Check Other Price Slab   "+consumptionPriceSlabR.getPriceSlabId());
	      }
		 System.out.println("------------------------------------------");
		return consumptionPriceSlabR;
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public double getByConsumptionPriceRateBySlabIdAndUnitValue(int id, double registerValue) {
		// TODO Auto-generated method stub
		BigDecimal registerValuelog =new BigDecimal(registerValue);
		Query q1 = null;
		q1 = entityManager.createQuery(
				"select a from  PriceSlabRangeDetails a where a.consumptionPriceSlab.id=:slabId and a.toRange>= :registerValue and a.fromRange <=:registerValue",
				PriceSlabRangeDetails.class).setParameter("slabId",id).setParameter("registerValue", registerValuelog);
		
		List<PriceSlabRangeDetails> list1 = q1.getResultList();
		
		double price;
		if(list1.size()==0) {
			double result = 0;
			Query q = null;
			result = (double) entityManager
					.createQuery(
							"SELECT MAX(a.price) FROM PriceSlabRangeDetails a where a.consumptionPriceSlab.id=:slabId ").setParameter("slabId",id)
					.getSingleResult();
			price=Double.valueOf(result);
		}else {
			price=list1.get(0).getPrice();
		}
		System.out.println("Price "+price);
		
		return price;
	}



}
