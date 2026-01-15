package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AlertConfiguration;

public interface AlertConfigurationRepo extends JpaRepository<AlertConfiguration, Integer>,AlertConfigurationCustomeRepo{
	@Query("from AlertConfiguration a where a.registerNo=?1")
	Optional<AlertConfiguration> getAlertConfigurationByRegisterNo(String registerNo);





}
