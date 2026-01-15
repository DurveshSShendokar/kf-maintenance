package com.kfMaintenancce.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.ControlPanel;
import com.kfMaintenancce.model.EnergyMeterMaster;
import com.kfMaintenancce.model.EnergyMeterModule;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.repo.ControlPanelRepo;
import com.kfMaintenancce.repo.EnergyMeterMasterRepo;
import com.kfMaintenancce.repo.EnergyMeterModuleRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
@Service
public class EnergyServicesImpl implements EnergyServices {
	
	
	@Autowired
	EnergyMeterMasterRepo energyMeterMasterRepo;
	
	
	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	
	
	@Autowired
	EnergyMeterModuleRepo energyMeterModuleRepo;
	
	@Autowired
	ControlPanelRepo controlPanelRepo;
	
	

	@Override
	public void addEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		// TODO Auto-generated method stub
		energyMeterMasterRepo.save(energyMeterMaster);
	}

	@Override
	public void deleteEnergyMeterMaster(EnergyMeterMaster energyMeterMaster) {
		// TODO Auto-generated method stub
		energyMeterMasterRepo.delete(energyMeterMaster);
	}

	@Override
	public List<EnergyMeterMaster> getEnergyMeterMasterByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.getEnergyMeterMasterByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getEnergyMeterMasterCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.getEnergyMeterMasterCountByLimitAndSearch(searchText);
	}

	@Override
	public List<EnergyMeterMaster> getEnergyMeterMasterByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.getEnergyMeterMasterByLimit(pageNo,perPage);
	}

	@Override
	public int getCountOfEnergyMeterMaster() {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.getCountOfEnergyMeterMaster();
	}

	@Override
	public void addEnergyMeterRegister(EnergyMeterRegister energyMeterRegister) {
		// TODO Auto-generated method stub
		energyMeterRegisterRepo.save(energyMeterRegister);
	}

	@Override
	public void deleteEnergyMeterRegister(EnergyMeterRegister energyMeterRegister) {
		// TODO Auto-generated method stub
		energyMeterRegisterRepo.delete(energyMeterRegister);
	}

	@Override
	public List<EnergyMeterRegister> getEnergyMeterRegisterByLimitAndSearch(String searchText, int pageNo,
			int perPage) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getEnergyMeterRegisterByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getEnergyMeterRegisterCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getEnergyMeterRegisterCountByLimitAndSearch(searchText);
	}

	@Override
	public List<EnergyMeterRegister> getEnergyMeterRegisterByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getEnergyMeterRegisterByLimit(pageNo,perPage);
	}

	@Override
	public int getCountOfEnergyMeterRegister() {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getCountOfEnergyMeterRegister();
	}

	@Override
	public void addEnergyMeterModule(EnergyMeterModule engergyMeterModule) {
		// TODO Auto-generated method stub
		energyMeterModuleRepo.save(engergyMeterModule);
	}

	@Override
	public void deleteEnergyMeterModule(EnergyMeterModule engergyMeterModule) {
		// TODO Auto-generated method stub
		energyMeterModuleRepo.delete(engergyMeterModule);
	}

	@Override
	public List<EnergyMeterModule> getEnergyMeterModuleByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return energyMeterModuleRepo.getEnergyMeterModuleByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getEnergyMeterModuleCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return energyMeterModuleRepo.getEnergyMeterModuleCountByLimitAndSearch(searchText);
	}

	@Override
	public List<EnergyMeterModule> getEnergyMeterModuleByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return energyMeterModuleRepo.getEnergyMeterModuleByLimit(pageNo,perPage);
	}

	@Override
	public int getCountOfEnergyMeterModule() {
		// TODO Auto-generated method stub
		return energyMeterModuleRepo.getCountOfEnergyMeterModule();
	}

	@Override
	public void addControlPanel(ControlPanel controlPanel) {
		// TODO Auto-generated method stub
		controlPanelRepo.save(controlPanel);
	}

	@Override
	public void deleteControlPanel(ControlPanel controlPanel) {
		// TODO Auto-generated method stub
		controlPanelRepo.delete(controlPanel);
	}

	@Override
	public List<ControlPanel> getControlPanelByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return controlPanelRepo.getControlPanelByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getControlPanelCountByLimitAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return controlPanelRepo.getControlPanelCountByLimitAndSearch(searchText);
	}

	@Override
	public List<ControlPanel> getControlPanelByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return controlPanelRepo.getControlPanelByLimit(pageNo,perPage);
	}

	@Override
	public int getCountOfControlPanel() {
		// TODO Auto-generated method stub
		return controlPanelRepo.getCountOfControlPanel();
	}

	@Override
	public List<EnergyMeterRegister> getAllEnergyMeterRegisters() {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.findAll();
	}

	@Override
	public List<EnergyMeterModule> getAllEnergyMeterModule() {
		// TODO Auto-generated method stub
		return energyMeterModuleRepo.findAll();
	}

	@Override
	public List<ControlPanel> getAllControlPanels() {
		// TODO Auto-generated method stub
		return controlPanelRepo.findAll();
	}

	@Override
	public List<EnergyMeterMaster> getAllEnergyMeterMasters() {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.findAll();
	}

	@Override
	public List<EnergyMeterMaster> getAllEnergyMeterMastersByPanels(int id) {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.getAllEnergyMeterMastersByPanels(id);
	}

	@Override
	public Optional<EnergyMeterMaster> getEnergyMeterMasterById(int id) {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.findById(id);
	}

	@Override
	public Optional<EnergyMeterRegister> getEnergyMeterRegisterById(Integer registerId) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.findById(registerId);
		}

	@Override
	public List<String> getAllRegisters() {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getAllRegisters();
	}

	@Override
	public List<String>  getRegisterNameByRegisterNo(String registerNo) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getRegisterNameByRegisterNo(registerNo);
	}

	@Override
	public Optional<EnergyMeterRegister> getEnergyMeterRegisterByRegisterNoandModuleId(String registerNo, int moduleId) {
		// TODO Auto-generated method stub
		System.out.println("Register "+registerNo+" Mod "+moduleId);
		return energyMeterRegisterRepo.getEnergyMeterRegisterByRegisterNoandModuleId(registerNo,moduleId);
	}

	@Override
	public List<EnergyMeterMaster> getAllMeters() {
		// TODO Auto-generated method stub
		return energyMeterMasterRepo.findAll();
	}

	@Override
	public List<String> getAllRegistersByModule(int moduleId) {
		// TODO Auto-generated method stub
		return energyMeterRegisterRepo.getAllRegistersByModule(moduleId);
	}

	

}