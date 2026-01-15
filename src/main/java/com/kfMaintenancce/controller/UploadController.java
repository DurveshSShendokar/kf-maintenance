package com.kfMaintenancce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Device;
import com.kfMaintenancce.model.DeviceModel;
import com.kfMaintenancce.model.EnergyMeterModule;
import com.kfMaintenancce.model.EnergyMeterRegister;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.ModBusAddress;
import com.kfMaintenancce.model.ModelAddressMapping;
import com.kfMaintenancce.model.Target;
import com.kfMaintenancce.model.UnitLocation;
import com.kfMaintenancce.repo.DeviceModelRepo;
import com.kfMaintenancce.repo.DeviceRepo;
import com.kfMaintenancce.repo.EnergyMeterModuleRepo;
import com.kfMaintenancce.repo.EnergyMeterRegisterRepo;
import com.kfMaintenancce.repo.ModBusAddressRepo;
import com.kfMaintenancce.repo.ModelAddressMappingRepo;
import com.kfMaintenancce.repo.TargetRepo;
import com.kfMaintenancce.repo.UnitLocationRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload")
public class UploadController {
	
	
	@Autowired
	TargetRepo targetRepo;
	
	
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
	@Autowired
	EnergyMeterModuleRepo energyMeterModuleRepo;
	
	
	@Autowired
	EnergyMeterRegisterRepo energyMeterRegisterRepo;
	
	
	@RequestMapping(value = "/uploadModuleRegister", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadModuleRegister(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String moduleNo = row.getCell(1).getStringCellValue();
			String registerName = row.getCell(2).getStringCellValue();
			String registerNo = removeAfterPeriod(String.valueOf(row.getCell(3).getNumericCellValue()));
			String format = row.getCell(4).getStringCellValue();
			String uom = row.getCell(5).getStringCellValue();
			String scale = String.valueOf(row.getCell(6).getNumericCellValue());
			String multiplier = String.valueOf(row.getCell(7).getNumericCellValue());
			
			Optional<EnergyMeterModule> meterModules = energyMeterModuleRepo.getbyModuleNo(moduleNo);
			EnergyMeterModule energyMeterModule;
			EnergyMeterRegister energyMeterRegister=new EnergyMeterRegister();
			if (meterModules.isPresent()) {
				energyMeterModule=meterModules.get();
				
			}else {
				EnergyMeterModule energyMeterModule2= new EnergyMeterModule();
				energyMeterModule2.setCompanyName("NA");
				energyMeterModule2.setModuleNo(moduleNo);
				energyMeterModule=energyMeterModuleRepo.save(energyMeterModule2);
			}
			energyMeterRegister.setCreateDateTime(new Date());
			energyMeterRegister.setModule(energyMeterModule);
			energyMeterRegister.setRegisterFormat(format);
			energyMeterRegister.setRegisterName(registerName);
			energyMeterRegister.setRegisterNo(registerNo);
			energyMeterRegister.setUom(uom);
			energyMeterRegister.setScale(scale);
			energyMeterRegister.setMultiplier(multiplier);
			Optional<EnergyMeterRegister> optional=energyMeterRegisterRepo.getByModuleAndRegister(energyMeterModule.getId(),registerName);
			if (!optional.isPresent()) {
				energyMeterRegisterRepo.save(energyMeterRegister);

			}
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}
	
	
	
	
	@RequestMapping(value = "/uploadUnitLocation", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadUnitLocation(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String plantName = row.getCell(1).getStringCellValue();
			String LocationName = row.getCell(2).getStringCellValue();
			String address = row.getCell(3).getStringCellValue();
			Optional<UnitLocation> unitLocOp = unitLocationRepo.getUnitLocationByPlantNameAndLocation(plantName,LocationName);

			if (!unitLocOp.isPresent()) {
				UnitLocation location = new UnitLocation();
				location.setAddress(address);
				location.setLocationName(LocationName);
				location.setPlantName(plantName);
				location.setAddedDate(new Date());
				location.setActive(1);
				location.setAddedBy("Upload");
				unitLocationRepo.save(location);
				
			}
			
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}
	
	

	@RequestMapping(value = "/uploadModBusAddress", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadModBusAddress(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String Name = row.getCell(1).getStringCellValue();
			String modAddress = removeAfterPeriod(String.valueOf(row.getCell(2).getNumericCellValue()));
			Optional<ModBusAddress> unitLocOp = modBusAddressRepo.getModBusAddByNameAndAdd(Name,modAddress);

			if (!unitLocOp.isPresent()) {
				ModBusAddress modBusAddress = new ModBusAddress();
				modBusAddress.setActive(1);
				modBusAddress.setModAddress(modAddress);
				modBusAddress.setName(Name);
				modBusAddress.setAddedDate(new Date());
				modBusAddressRepo.save(modBusAddress);
				
			}
			
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}
	
	
	

	@RequestMapping(value = "/uploadDevice", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadDevice(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String deviceName = row.getCell(1).getStringCellValue();
			String deviceId= removeAfterPeriod(String.valueOf(row.getCell(2).getNumericCellValue()));
			String locationName = row.getCell(3).getStringCellValue();
			String deviceModel = row.getCell(4).getStringCellValue();
			
			
			Optional<Device> deviceOp = deviceRepo.getDeviceByDeviceId(deviceId);
System.out.println("Device ID "+deviceId+" Device Name "+deviceName+" Location "+locationName+"  Model  "+deviceModel);
			if (!deviceOp.isPresent()) {
				Device device = new Device();
				Optional<UnitLocation> optionalLocation=unitLocationRepo.getUnitLocationByLocationName(locationName);
				Optional<DeviceModel> optionalMod=deviceModelRepo.getDeviceModelByDevice(deviceModel);
				
				if(optionalLocation.isPresent()) {
					device.setLocation(optionalLocation.get());
				}else {
					UnitLocation unitLocation=new UnitLocation();
					unitLocation.setActive(1);
					unitLocation.setAddedBy("NA");
					unitLocation.setAddedDate(new Date());
					unitLocation.setLocationName(locationName);
					unitLocation.setPlantName("NA");
					UnitLocation unitLocation1 =unitLocationRepo.save(unitLocation);
					device.setLocation(unitLocation1);
				}
				if(optionalMod.isPresent()) {
					device.setModel(optionalMod.get());
				}else {
					DeviceModel model2= new DeviceModel();
					
					model2.setAddedDate(new Date());
					model2.setModelName(deviceModel);
					
					DeviceModel deviceModel2=deviceModelRepo.save(model2);
					device.setModel(deviceModel2);
				}
				
				
			
				device.setActive(1);
				device.setAddedBy("Upload");
				device.setAddedDate(new Date());
				
			
				device.setDeviceId(deviceId);
				device.setDeviceName(deviceName);
				deviceRepo.save(device);
				
			}
			
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}
	
	
	
	@RequestMapping(value = "/uploadDeviceModel", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadDeviceModel(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String deviceModelName = row.getCell(1).getStringCellValue();
			String address= removeAfterPeriod(String.valueOf(row.getCell(2).getNumericCellValue()));
				
			DeviceModel deviceModel;
			ModBusAddress modBusAddress;
			Optional<DeviceModel> deviceModelOp = deviceModelRepo.getDeviceModelByDevice(deviceModelName);
			Optional<ModBusAddress> addressOp= modBusAddressRepo.getModBusAddByAdd(address);
			if (deviceModelOp.isPresent()) {
				deviceModel=deviceModelOp.get();
			}else {
				DeviceModel deviceModel2= new DeviceModel();
				deviceModel2.setModelName(deviceModelName);
				deviceModel2.setAddedBy("Upload Mapping");
				deviceModel2.setAddedDate(new Date());
				deviceModel=deviceModelRepo.save(deviceModel2);
			}
			
			if (addressOp.isPresent()) {
				modBusAddress=addressOp.get();
			}else {
				ModBusAddress modBusAddress1= new ModBusAddress();
				modBusAddress1.setModAddress(address);
				modBusAddress1.setName("NA");
				modBusAddress1.setAddedDate(new Date());
				modBusAddress=modBusAddressRepo.save(modBusAddress1);
			}
			ModelAddressMapping modelAddressMapping= new ModelAddressMapping();
			modelAddressMapping.setAddress(modBusAddress);
			modelAddressMapping.setDeviceModel(deviceModel);
			modelAddressMappingRepo.save(modelAddressMapping);
			
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String removeAfterPeriod(String input) {
	    if (input == null) {
	        return null;
	    }
	    int periodIndex = input.indexOf('.');
	    if (periodIndex == -1) {
	        return input; // No period found, return the original string
	    }
	    return input.substring(0, periodIndex);
	}
	@RequestMapping(value = "/uploadTarget", method = RequestMethod.POST)
	@ResponseBody
	public Status uploadTarget(@RequestPart MultipartFile file) throws IOException{
		Status  status= new Status();

		File dir = new File(System.getProperty("catalina.base"), "uploads");
		File uplaodedFile = new File(dir + file.getOriginalFilename());
		file.transferTo(uplaodedFile);
		FileInputStream excelFile = new FileInputStream(uplaodedFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			String machineName = row.getCell(1).getStringCellValue();
			String year = removeAfterPeriod(String.valueOf(row.getCell(2).getNumericCellValue()));
			String month = row.getCell(3).getStringCellValue();
			String type = row.getCell(4).getStringCellValue();
			String hour = removeAfterPeriod(String.valueOf(row.getCell(5).getNumericCellValue()));
			Optional<Target> targetOp = targetRepo.getTargetByMachineYearnamdMonth(machineName, year, month, type);
			System.out.println("Target  "+machineName +" "+year+" "+month+" "+type+" "+targetOp.isPresent());

			if (!targetOp.isPresent()) {
				Target target = new Target();
				target.setMachineName(machineName);
				target.setYear(year);
				target.setMonth(month);
				target.setType(type);
				target.setHour(hour);
					targetRepo.save(target);
				
			}
			
		}

		try {
			workbook.close();
		} catch (Exception e) {}
		
		status.setCode(200);
		status.setMessage("Imported successfully");
		return status;
	}


	@RequestMapping(value = "/uploadMachine", method = RequestMethod.POST)
	public @ResponseBody Status uploadMachine(@ModelAttribute(value = "file") MultipartFile file,
			HttpServletRequest request,@RequestParam("addedBy") String addedBy) throws ParseException {
		Status  status= new Status();
		int uploadedCount=0;
		int totalCount=0;
		String responceMsg="";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
					//logger.info("File not found");
				} else {
					//logger.info(file.getOriginalFilename());
					
					
					System.out.println("Stock Uploading");
					try {
						File dir = new File(System.getProperty("catalina.base"), "uploads");
						File uplaodedFile = new File(dir + file.getOriginalFilename());
						file.transferTo(uplaodedFile);
						FileInputStream excelFile = new FileInputStream(uplaodedFile);
						//logger.info("hiiii@  :: " + excelFile);
						Workbook workbook = new XSSFWorkbook(excelFile);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						DataFormatter formatter = new DataFormatter();
						
						Sheet datatypeSheet = workbook.getSheetAt(0); 
						int i = 1;
						totalCount	=datatypeSheet.getLastRowNum();
							while (i <= datatypeSheet.getLastRowNum()) { 
							if(totalCount==0){
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0="";
								responceMsg0="Uploading...... ";
								responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
								responceMsg0+=" \r\n Upaloading Date :: "+new Date();
								responceMsg0+=" \r\n Upaload  By:: "+addedBy;
								responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
								
								responceMsg0+="\r\n Data Not Found in sheet";
								String newResMsg=responceMsg0+" \r\n "+responceMsg;
								status.setResmessage(newResMsg);
								
								return status; 
							}
							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
							/*String str = row.getCell(0).toString();
							if(str.length()==0) {
								continue;
							}*/
							
							System.out.println("Uploading ..............................");
													
						}						
						//logger.info("Successfully imported");
						workbook.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		status.setResmessage(responceMsg);
		String responceMsg0="";
		responceMsg0="Uploading...... ";
		responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
		responceMsg0+=" \r\n Upaloading Date :: "+new Date();
		responceMsg0+=" \r\n Upaload  By:: "+addedBy;
		responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
		if(uploadedCount==totalCount){
			responceMsg0+=" \r\n No Constrain Found ";
		}else{
			responceMsg0+=" \r\n Found Following Constrain";
		}
		
		String newResMsg=responceMsg0+" \r\n "+responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
}
