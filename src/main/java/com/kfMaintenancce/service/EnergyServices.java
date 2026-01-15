package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import com.kfMaintenancce.model.ControlPanel;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterModule;
import com.kfMaintenancce.model.EnergyMeterRegister;

public interface EnergyServices {

	void addEnergyMeterMaster(EnergyMeterMaster energyMeterMaster);

	void deleteEnergyMeterMaster(EnergyMeterMaster energyMeterMaster);

	List<EnergyMeterMaster> getEnergyMeterMasterByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterMasterCountByLimitAndSearch(String searchText);

	List<EnergyMeterMaster> getEnergyMeterMasterByLimit(int pageNo, int perPage);

	int getCountOfEnergyMeterMaster();

	void addEnergyMeterRegister(EnergyMeterRegister energyMeterRegister);

	void deleteEnergyMeterRegister(EnergyMeterRegister energyMeterRegister);

	List<EnergyMeterRegister> getEnergyMeterRegisterByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterRegisterCountByLimitAndSearch(String searchText);

	List<EnergyMeterRegister> getEnergyMeterRegisterByLimit(int pageNo, int perPage);

	int getCountOfEnergyMeterRegister();

	void addEnergyMeterModule(EnergyMeterModule engergyMeterModule);

	void deleteEnergyMeterModule(EnergyMeterModule engergyMeterModule);

	List<EnergyMeterModule> getEnergyMeterModuleByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEnergyMeterModuleCountByLimitAndSearch(String searchText);

	List<EnergyMeterModule> getEnergyMeterModuleByLimit(int pageNo, int perPage);

	int getCountOfEnergyMeterModule();

	void addControlPanel(ControlPanel controlPanel);

	void deleteControlPanel(ControlPanel controlPanel);

	List<ControlPanel> getControlPanelByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getControlPanelCountByLimitAndSearch(String searchText);

	List<ControlPanel> getControlPanelByLimit(int pageNo, int perPage);

	int getCountOfControlPanel();

	List<EnergyMeterRegister> getAllEnergyMeterRegisters();

	List<EnergyMeterModule> getAllEnergyMeterModule();

	List<ControlPanel> getAllControlPanels();

	List<EnergyMeterMaster> getAllEnergyMeterMasters();

	List<EnergyMeterMaster> getAllEnergyMeterMastersByPanels(int id);

	Optional<EnergyMeterMaster> getEnergyMeterMasterById(int id);

	Optional<EnergyMeterRegister> getEnergyMeterRegisterById(Integer registerId);

	List<String> getAllRegisters();

	List<String>  getRegisterNameByRegisterNo(String str);


	Optional<EnergyMeterRegister> getEnergyMeterRegisterByRegisterNoandModuleId(String registerNo, int meterId);

	List<EnergyMeterMaster> getAllMeters();

	List<String> getAllRegistersByModule(int moduleId);

}
