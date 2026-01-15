package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.kfMaintenancce.model.ConsumptionPriceSlab;
import com.kfMaintenancce.model.PriceSlabRangeDetails;

public interface ConsumptionPriceSlabCustomeRepo {

	List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimitAndSearch(String searchText, int pageNo, int perPage);


	int getConsumptionPriceSlabCountByLimitAndSearch(String searchText);

	List<ConsumptionPriceSlab> getConsumptionPriceSlabByLimit(int pageNo, int perPage);
	ConsumptionPriceSlab getEffectivePriceSlab(Date date);
	double getByConsumptionPriceRateBySlabIdAndUnitValue(int id, double registerValue);

}
