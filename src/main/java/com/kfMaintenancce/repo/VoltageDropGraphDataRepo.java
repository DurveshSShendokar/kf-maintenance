package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.VoltageDropGraphData;

public interface VoltageDropGraphDataRepo extends JpaRepository<VoltageDropGraphData, Integer> {
	@Query("from VoltageDropGraphData v where v.meterId=?1")
	List<VoltageDropGraphData> findbyMeterId(int id);

}
