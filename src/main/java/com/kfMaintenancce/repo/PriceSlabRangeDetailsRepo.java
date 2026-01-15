package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.PriceSlabRangeDetails;

public interface PriceSlabRangeDetailsRepo extends JpaRepository<PriceSlabRangeDetails, Integer> {
	@Query("from PriceSlabRangeDetails p where p.consumptionPriceSlab.id=?1")
	List<PriceSlabRangeDetails> getByConsumptionPriceSlab(int id);

}
