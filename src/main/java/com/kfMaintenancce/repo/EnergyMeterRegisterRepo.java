package com.kfMaintenancce.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.EnergyMeterRegisterGroup;

public interface EnergyMeterRegisterRepo extends JpaRepository<EnergyMeterRegister, Integer>,EnergyMeterRegisterCustomeRepo {
	@Query("from EnergyMeterRegister e where e.module.id=?1 and e.registerName=?2")
	Optional<EnergyMeterRegister> getByModuleAndRegister(int id, String registerName);
	@Query("select distinct(registerNo) from EnergyMeterRegister")
	List<String> getAllRegisters();
	@Query("select registerName from EnergyMeterRegister e where e.registerNo=?1")

	List<String>  getRegisterNameByRegisterNo(String registerNo);
	@Query(" from EnergyMeterRegister e where e.registerNo=?1 and e.module.id=?2")

	Optional<EnergyMeterRegister> getEnergyMeterRegisterByRegisterNoandModuleId(String registerNo, int meterId);
	@Query(" from EnergyMeterRegister e where e.registerNo=?1")
	List<EnergyMeterRegister> getEnergyMeterRegisterByRegisterNo(String registerNo);
	@Query("select distinct(registerNo) from EnergyMeterRegister e where e.module.id=?1")
	List<String> getAllRegistersByModule(int moduleId);

	
}
