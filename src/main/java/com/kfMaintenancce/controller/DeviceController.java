package com.kfMaintenancce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.DeviceModelAddDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Department;
import com.kfMaintenancce.model.Device;
import com.kfMaintenancce.model.DeviceModel;
import com.kfMaintenancce.model.ModBusAddress;
import com.kfMaintenancce.model.ModelAddressMapping;
import com.kfMaintenancce.model.UnitLocation;
import com.kfMaintenancce.repo.DeviceModelRepo;
import com.kfMaintenancce.repo.DeviceRepo;
import com.kfMaintenancce.repo.ModBusAddressRepo;
import com.kfMaintenancce.repo.ModelAddressMappingRepo;
import com.kfMaintenancce.repo.UnitLocationRepo;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/device")
public class DeviceController {
	@Autowired
	UnitLocationRepo unitLocationRepo;
	
	@Autowired
	ModBusAddressRepo modBusAddressRepo;
	@Autowired
	DeviceRepo deviceRepo;
	@Autowired
	DeviceModelRepo deviceModelRepo;
	@Autowired
	ModelAddressMappingRepo modelAddressMappingRepo;
	

	@RequestMapping(value = "/deleteDeviceModAddress", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteDeviceModAddress(@RequestBody ModelAddressMapping modelAddressMapping ) {
		try {
			modelAddressMappingRepo.delete(modelAddressMapping);
		
			return new Status("ModelAddressMapping deleted Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@RequestMapping(value = "/addDeviceModelAddress", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addDeviceModelAddress(@RequestBody DeviceModelAddDTO deviceModelAddDTO ) {
		try {
			
		System.out.println("ASDDD "+deviceModelAddDTO.toString());
		DeviceModel deviceMode;
		if(deviceModelAddDTO.isNewDeviceMode()) {
			DeviceModel deviceModel1= new DeviceModel();
			deviceModel1.setModelName(deviceModelAddDTO.getDeviceModelName());
			deviceMode=deviceModelRepo.save(deviceModel1);
		}else {
			deviceMode=deviceModelAddDTO.getDeviceModel();
		}
		for(ModBusAddress address:deviceModelAddDTO.getAddress()) {
			ModelAddressMapping modelAddressMapping=new ModelAddressMapping();
			modelAddressMapping.setAddress(address);
			modelAddressMapping.setDeviceModel(deviceMode);
			modelAddressMappingRepo.save(modelAddressMapping);
		}
		
			return new Status("ModelAddressMapping added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@GetMapping(value = "/getAllModelAddressMapping", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<ModelAddressMapping> getAllModelAddressMapping() {
		List<ModelAddressMapping> list = null;
		try {
			
			list = modelAddressMappingRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	@GetMapping(value = "/getAllDeviceModels", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<DeviceModel> getAllDeviceModels() {
		List<DeviceModel> list = null;
		try {
			
			list = deviceModelRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@RequestMapping(value = "/addUnitLocation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addUnitLocation(@RequestBody UnitLocation unitLocation ) {
		try {
			
			unitLocationRepo.save(unitLocation);
			return new Status("Unit Location added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	
	@RequestMapping(value = "/deleteUnitLocation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteUnitLocation(@RequestBody UnitLocation unitLocation ) {
		try {
			
			unitLocationRepo.delete(unitLocation);
			return new Status("Unit Location deleted Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllUnitLocation", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<UnitLocation> getAllUnitLocation() {
		List<UnitLocation> list = null;
		try {
			
			list = unitLocationRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/addModBusAddress", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addModBusAddress(@RequestBody ModBusAddress modBusAddress) {
		try {
			
			modBusAddressRepo.save(modBusAddress);
			return new Status("ModBusAddress added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@RequestMapping(value = "/deleteModBusAddresse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status deleteModBusAddresse(@RequestBody ModBusAddress modBusAddress) {
		try {
			
			modBusAddressRepo.delete(modBusAddress);
			return new Status("ModBusAddress deleted Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllModBusAddress", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<ModBusAddress> getAlModBusAddress() {
		List<ModBusAddress> list = null;
		try {
			
			list = modBusAddressRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@RequestMapping(value = "/addDevices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addDevices(@RequestBody Device device) {
		try {
			
			System.out.println("Device STR :  "+device.toString());
			device.setActive(1);
			
			deviceRepo.save(device);
			return new Status("Device added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping(value = "/getAllDevice", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Device> getAllDevice() {
		List<Device> list = null;
		try {
			
			list = deviceRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
