package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.EnergyMeterModule;

public interface EnergyMeterModuleRepo extends JpaRepository<EnergyMeterModule, Integer>,EnergyMeterModuleCustomeRepo{
	@Query("from EnergyMeterModule e where e.moduleNo=?1")
	Optional<EnergyMeterModule> getbyModuleNo(String moduleNo);


	 @Query("SELECT e FROM EnergyMeterModule e WHERE e.companyName = :companyName AND e.moduleNo = :moduleNo")
	    Optional<EnergyMeterModule> findByCompanyNameAndModuleNo(@Param("companyName") String companyName,
	                                                             @Param("moduleNo") String moduleNo);

}
