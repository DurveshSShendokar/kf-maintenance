package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.CurrentDropGraphData;

public interface CurrentDropGraphDataRepo extends JpaRepository<CurrentDropGraphData, Integer>{
	@Query("From CurrentDropGraphData c where c.meterId=?1")
	List<CurrentDropGraphData> findbyMeterId(int id);

}
