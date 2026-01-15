package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.CurrentVoltageCard;

public interface CurrentVoltageCardRepo extends JpaRepository<CurrentVoltageCard, Integer> {
	@Query("from CurrentVoltageCard where meterId=?1")
	Optional<CurrentVoltageCard> findByMeterId(int meterId);

}
