package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.PowerFactorGraphData;

public interface PowerFactorGraphDataRepo  extends JpaRepository<PowerFactorGraphData, Integer>{
	@Query("From PowerFactorGraphData c where c.meterId=?1")

	List<PowerFactorGraphData> findbyMeterId(int id);

}
