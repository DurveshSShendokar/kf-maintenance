package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.DashboardCardData;

public interface DashboardCardDataRepo extends JpaRepository<DashboardCardData, Integer>{
	@Query("From DashboardCardData d where d.meterId=?1")
	Optional<DashboardCardData> getByMeterId(int meterId);

}
