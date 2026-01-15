package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.PriceSlabRangeDetails;

public interface ConsumptionPriceSlabRepo extends JpaRepository<ConsumptionPriceSlab, Integer>,ConsumptionPriceSlabCustomeRepo{
	@Query("from ConsumptionPriceSlab c where c.active=1")
Optional<ConsumptionPriceSlab> getActivePriceSlab();

	


}
