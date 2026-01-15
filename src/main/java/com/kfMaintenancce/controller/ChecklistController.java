package com.kfMaintenancce.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.CheckType;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.repo.ChecklistRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.service.ChecklistServices;
import com.kfMaintenancce.service.MachineServices;
import com.kfMaintenancce.service.MaintServices;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/checklist")

public class ChecklistController {

	@Autowired
	ChecklistServices checklistServices;
	
	@Autowired
	MachineServices machineServices;
	
	@Autowired
	ChecklistRepo checklistRepo;

	@Autowired
	MaintServices maintServices;

	@Autowired
	MachineRepo machineRepo;
	
	
	
	
	
	

@GetMapping("/getChecklistByLimit")
public @ResponseBody List<Checklist> getChecklistByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Checklist> list = new ArrayList<Checklist>();
	try {
		list = checklistRepo.getChecklistByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geChecklistCount")
public @ResponseBody int getChecklistCount() {
	int count = 0;
	try {
			count = (int) checklistRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getChecklistByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Checklist> getChecklistByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Checklist> list = new ArrayList<Checklist>();
	try {
		
		list = checklistRepo.getChecklistByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getChecklistCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getChecklistCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = checklistRepo.getChecklistCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}
	
	
	
	
	
	@PutMapping("/checklistReorder/{id}")
	public ResponseEntity<Map<String, Object>> reorderChecklist(
	        @PathVariable int id,
	        @RequestParam int newPosition) {

	    Map<String, Object> response = new HashMap<>();

	    Checklist checklist = checklistRepo.findById(id).orElse(null);

	    if (checklist == null) {
	        response.put("status", "error");
	        response.put("message", "Checklist not found.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }

	    int machineId = checklist.getMachine().getMachine_id();
	    String frequency = checklist.getFrequency();
	    int oldPosition = checklist.getSerialNumber();

	    List<Checklist> checklistList = checklistRepo.arrangechecklist(machineId, frequency);

	    if (newPosition < oldPosition) {
	        for (Checklist c : checklistList) {
	            if (c.getSerialNumber() >= newPosition && c.getSerialNumber() < oldPosition) {
	                c.setSerialNumber(c.getSerialNumber() + 1);
	                checklistRepo.save(c);
	            }
	        }
	    } else if (newPosition > oldPosition) {
	        for (Checklist c : checklistList) {
	            if (c.getSerialNumber() > oldPosition && c.getSerialNumber() <= newPosition) {
	                c.setSerialNumber(c.getSerialNumber() - 1);
	                checklistRepo.save(c);
	            }
	        }
	    }

	    checklist.setSerialNumber(newPosition);
	    checklistRepo.save(checklist);

	    response.put("status", "success");
	    response.put("message", "Checklist reordered successfully.");
	    response.put("checklistId", checklist.getChecklist_id());
	    response.put("newSerialNumber", checklist.getSerialNumber());

	    return ResponseEntity.ok(response);
	}

	
	// import checklist
	@PostMapping("/uploadFullChecklist")
	public Status uploadFullChecklist(@RequestPart MultipartFile file) throws IOException {
	    Status status = new Status();

	    File dir = new File(System.getProperty("catalina.base"), "uploads");
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    File uploadedFile = new File(dir, file.getOriginalFilename());
	    file.transferTo(uploadedFile);

	    try (FileInputStream excelFile = new FileInputStream(uploadedFile);
	         Workbook workbook = new XSSFWorkbook(excelFile)) {

	        Sheet sheet = workbook.getSheetAt(0);

	        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            String machineName = getStringValue(row.getCell(0));
	            String type = getStringValue(row.getCell(1));
	            String frequency = getStringValue(row.getCell(2));
	            String task = getStringValue(row.getCell(3));
	            String operation = getStringValue(row.getCell(4));
	            String range = getStringValue(row.getCell(5));
	            String checkTypeStr = getStringValue(row.getCell(6));
	            String lowerRangeStr = getStringValue(row.getCell(7));
	            String upperRangeStr = getStringValue(row.getCell(8));
	            String checkUnit = getStringValue(row.getCell(9));

	            if (machineName == null || type == null || frequency == null || task == null) {
	                System.out.println("Skipping row " + (i + 1) + " due to missing mandatory fields.");
	                continue;
	            }

	            machineName = machineName.trim().toLowerCase();
	            List<Machine> machines = machineRepo.findByMachineName(machineName);

	            if (machines != null && !machines.isEmpty()) {
	                for (Machine machine : machines) {
	                    int machineId = machine.getMachine_id();

	                    Checklist checklist = new Checklist();
	                    checklist.setMachine(machine);
	                    checklist.setAcceptableRange(range);
	                    checklist.setFrequency(frequency);
	                    checklist.setOperation(operation);
	                    checklist.setTask(task);
	                    checklist.setType(type);

	                    if (checkTypeStr != null && !checkTypeStr.isEmpty()) {
	                        try {
	                            checklist.setCheckType(CheckType.valueOf(checkTypeStr.toUpperCase()));
	                        } catch (IllegalArgumentException e) {
	                            System.out.println("Invalid CheckType enum at row " + (i + 1));
	                            continue;
	                        }
	                    }

	                    try {
	                        if (lowerRangeStr != null && !lowerRangeStr.isEmpty()) {
	                            checklist.setLower_range(Double.parseDouble(lowerRangeStr));
	                        }
	                        if (upperRangeStr != null && !upperRangeStr.isEmpty()) {
	                            checklist.setUpper_range(Double.parseDouble(upperRangeStr));
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("Invalid range value at row " + (i + 1) + ": " + e.getMessage());
	                        continue;
	                    }

	                    if (checkUnit != null && !checkUnit.isEmpty()) {
	                        checklist.setCheck_unit(checkUnit);
	                    }

	                    checklistRepo.save(checklist);
	                    System.out.println("Saved full checklist for machine_id " + machineId);
	                }
	            } else {
	                System.out.println("No machines found for name: " + machineName);
	            }
	        }

	    } catch (Exception e) {
	        System.err.println("Error processing Excel file: " + e.getMessage());
	        e.printStackTrace();
	        status.setCode(500);
	        status.setMessage("Error during full import: " + e.getMessage());
	        return status;
	    }

	    status.setCode(200);
	    status.setMessage("Full checklist imported successfully");
	    return status;
	}

	
	
	
	
	// new import api.
	
	@PostMapping("/uploadFullChecklists")
	public Status uploadFullChecklists(@RequestPart MultipartFile file) throws IOException {
	    Status status = new Status();

	    File dir = new File(System.getProperty("catalina.base"), "uploads");
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    File uploadedFile = new File(dir, file.getOriginalFilename());
	    file.transferTo(uploadedFile);

	    int importedCount = 0;
	    int duplicateCount = 0;

	    try (FileInputStream excelFile = new FileInputStream(uploadedFile);
	         Workbook workbook = new XSSFWorkbook(excelFile)) {

	        Sheet sheet = workbook.getSheetAt(0);

	        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            String machineEqid = getStringValue(row.getCell(1));
	            String type = getStringValue(row.getCell(2));
	            String frequency = getStringValue(row.getCell(3));
	            String task = getStringValue(row.getCell(4));
	            String acceptableRange = getStringValue(row.getCell(5));
	            String checkTypeStr = getStringValue(row.getCell(6));
	            String lowerRangeStr = getStringValue(row.getCell(7));
	            String upperRangeStr = getStringValue(row.getCell(8));
	            String checkUnit = getStringValue(row.getCell(9));

	            if (machineEqid == null || type == null || frequency == null || task == null) {
	                System.out.println("Skipping row " + (i + 1) + " due to missing mandatory fields.");
	                continue;
	            }

	            machineEqid = machineEqid.trim();
	            List<Machine> machines = machineRepo.findByEqids(machineEqid);

	            if (machines != null && !machines.isEmpty()) {
	                for (Machine machine : machines) {
	                    Checklist checklist = new Checklist();
	                    checklist.setMachine(machine);
	                    checklist.setAcceptableRange(acceptableRange);
	                    checklist.setFrequency(frequency);
	                    checklist.setOperation(""); // Default empty
	                    checklist.setTask(task);
	                    checklist.setType(type);

	                    // Set checkType
	                    if (checkTypeStr != null && !checkTypeStr.isEmpty()) {
	                        try {
	                            checklist.setCheckType(CheckType.valueOf(checkTypeStr.toUpperCase()));
	                        } catch (IllegalArgumentException e) {
	                            System.out.println("Invalid CheckType enum at row " + (i + 1));
	                            continue;
	                        }
	                    }

	                    // Set lower and upper range
	                    try {
	                        if (lowerRangeStr != null && !lowerRangeStr.isEmpty()) {
	                            checklist.setLower_range(Double.parseDouble(lowerRangeStr));
	                        }
	                        if (upperRangeStr != null && !upperRangeStr.isEmpty()) {
	                            checklist.setUpper_range(Double.parseDouble(upperRangeStr));
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("Invalid range value at row " + (i + 1) + ": " + e.getMessage());
	                        continue;
	                    }

	                    if (checkUnit != null && !checkUnit.isEmpty()) {
	                        checklist.setCheck_unit(checkUnit);
	                    }

	                    // Check for duplicate
	                    List<Checklist> existingChecklists = checklistRepo.findExistingChecklist(
	                            machine.getMachine_id(), task, type, frequency);

	                    if (existingChecklists.isEmpty()) {
	                        checklistRepo.save(checklist);
	                        importedCount++;
	                        System.out.println("Saved checklist for machine eqid: " + machineEqid);
	                    } else {
	                        duplicateCount++;
	                        System.out.println("Skipping duplicate checklist for machine eqid: " + machineEqid + " at row " + (i + 1));
	                    }
	                }
	            } else {
	                System.out.println("No machine found for eqid: " + machineEqid);
	            }
	        }

	    } catch (Exception e) {
	        System.err.println("Error processing Excel file: " + e.getMessage());
	        e.printStackTrace();
	        status.setCode(500);
	        status.setMessage("Error during import: " + e.getMessage());
	        return status;
	    }

	    status.setCode(200);
	    if (duplicateCount > 0) {
	        status.setMessage("Already Checklist Exist: " + importedCount + ",  duplicates Found : " + duplicateCount + ".");
	    } else {
	        status.setMessage("Checklist imported successfully by eqid. Imported: " + importedCount + ".");
	    }

	    return status;
	}


	
	
	
	@GetMapping("/downloadChecklistTemplate")
	public void downloadChecklistTemplate(HttpServletResponse response) {
	    try (Workbook workbook = new XSSFWorkbook()) {
	        Sheet sheet = workbook.createSheet("Checklist Template");

	        // Create header row
	        Row headerRow = sheet.createRow(0);
	        String[] columns = {
	                "SrNo", // numbering column
	                "machineEqid", "type", "frequency", "task",
	                "acceptableRange", "checkType", "lowerRange",
	                "upperRange", "checkUnit"
	        };

	        for (int i = 0; i < columns.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columns[i]);
	        }

	        // Example: fill 5 sample rows with SrNo for demonstration
	        for (int i = 1; i <= 5; i++) {
	            Row row = sheet.createRow(i);
	            row.createCell(0).setCellValue(i); // SrNo
	          
	        }

	        // Auto-size columns for readability
	        for (int i = 0; i < columns.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Set response headers for file download
	        String fileName = "Checklist_Import_Template.xlsx";
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	        workbook.write(response.getOutputStream());
	        response.flushBuffer();

	    } catch (IOException e) {
	        throw new RuntimeException("Failed to generate checklist template: " + e.getMessage(), e);
	    }
	}

	
//	// complete full checklist Upload
//	@PostMapping("/uploadFullChecklist")
//	public Status uploadFullChecklist(@RequestPart MultipartFile file) throws IOException {
//	    Status status = new Status();
//
//	    File dir = new File(System.getProperty("catalina.base"), "uploads");
//	    if (!dir.exists()) {
//	        dir.mkdirs();
//	    }
//	    File uploadedFile = new File(dir, file.getOriginalFilename());
//	    file.transferTo(uploadedFile);
//
//	    try (FileInputStream excelFile = new FileInputStream(uploadedFile);
//	         Workbook workbook = new XSSFWorkbook(excelFile)) {
//
//	        Sheet sheet = workbook.getSheetAt(0);
//
//	        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//	            Row row = sheet.getRow(i);
//	            if (row == null) continue;
//
//	            String machineName = getStringValue(row.getCell(0));
//	            String type = getStringValue(row.getCell(1));
//	            String frequency = getStringValue(row.getCell(2));
//	            String task = getStringValue(row.getCell(3));
//	            String operation = getStringValue(row.getCell(4));
//	            String range = getStringValue(row.getCell(5));
//	            String checkTypeStr = getStringValue(row.getCell(6));
//	            String lowerRangeStr = getStringValue(row.getCell(7));
//	            String upperRangeStr = getStringValue(row.getCell(8));
//	            String checkUnit = getStringValue(row.getCell(9));
//
//	            if (machineName == null || type == null || frequency == null || task == null) {
//	                System.out.println("Skipping row " + (i + 1) + " due to missing mandatory fields.");
//	                continue;
//	            }
//
//	            machineName = machineName.trim().toLowerCase();
//	            List<Machine> machines = machineRepo.findByMachineName(machineName);
//
//	            if (machines != null && !machines.isEmpty()) {
//	                for (Machine machine : machines) {
//	                    int machineId = machine.getMachine_id();
//
//	                    List<Checklist> existingList = checklistServices.getExistingList(
//	                        task, machineId, frequency, type);
//
//	                    if (existingList.isEmpty()) {
//	                        Checklist checklist = new Checklist();
//	                        checklist.setMachine(machine);
//	                        checklist.setAcceptableRange(range);
//	                        checklist.setFrequency(frequency);
//	                        checklist.setOperation(operation);
//	                        checklist.setTask(task);
//	                        checklist.setType(type);
//	                        if (checkTypeStr != null && !checkTypeStr.isEmpty()) {
//	                            try {
//	                                checklist.setCheckType(CheckType.valueOf(checkTypeStr.toUpperCase()));
//	                            } catch (IllegalArgumentException e) {
//	                                System.out.println("Invalid CheckType enum at row " + (i + 1));
//	                                continue;
//	                            }
//	                        }
//
//	                        try {
//	                            if (lowerRangeStr != null && !lowerRangeStr.isEmpty()) {
//	                                checklist.setLower_range(Double.parseDouble(lowerRangeStr));
//	                            }
//	                            if (upperRangeStr != null && !upperRangeStr.isEmpty()) {
//	                                checklist.setUpper_range(Double.parseDouble(upperRangeStr));
//	                            }
//	                        } catch (NumberFormatException e) {
//	                            System.out.println("Invalid range value at row " + (i + 1) + ": " + e.getMessage());
//	                            continue;
//	                        }
//
//	                        if (checkUnit != null && !checkUnit.isEmpty()) {
//	                            checklist.setCheck_unit(checkUnit);
//	                        }
//
//	                        checklistRepo.save(checklist);
//	                        System.out.println("Saved full checklist for machine_id " + machineId);
//	                    } else {
//	                        System.out.println("Checklist already exists for machine_id " + machineId);
//	                    }
//	                }
//	            } else {
//	                System.out.println("No machines found for name: " + machineName);
//	            }
//	        }
//
//	    } catch (Exception e) {
//	        System.err.println("Error processing Excel file: " + e.getMessage());
//	        e.printStackTrace();
//	        status.setCode(500);
//	        status.setMessage("Error during full import: " + e.getMessage());
//	        return status;
//	    }
//
//	    status.setCode(200);
//	    status.setMessage("Full checklist imported successfully");
//	    return status;
//	}
//	
	
	

	//***********************************************************************************************************//


	private String getStringValue(Cell cell) {
	    if (cell == null) {
	        return null;
	    }
	    System.out.println("Raw value in cell: " + cell.toString());
	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue().trim(); 
	        case NUMERIC:
	            return String.valueOf(cell.getNumericCellValue()).trim();
	        case FORMULA:
	            return cell.getCellFormula().trim(); 
	        default:
	            return null;
	    }
	}

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Status addChecklist(@RequestBody Checklist checklist) {
		Status  status= new Status();
		List<Object> ExistingChecklist = null;
		ExistingChecklist = new ArrayList<>();
		try {
			List<Checklist> arr = checklist.getChecklist();	
			for (Checklist c : arr) {
			    List<Checklist> existingList = checklistServices.getExistingList(
			        c.getTask(),
			        checklist.getMachine().getMachine_id(),
			        checklist.getFrequency(),
			        checklist.getType()
			    );

			    if (existingList.size() == 0) {
			        checklist.setTask(c.getTask());
			        checklist.setOperation(c.getOperation());
			        checklist.setAcceptableRange(c.getAcceptableRange());
			        checklist.setChecklist_id(0);

			        // ✅ ADD THESE:
			        checklist.setCheckType(c.getCheckType());
			        checklist.setLower_range(c.getLower_range());
			        checklist.setUpper_range(c.getUpper_range());
			        checklist.setCheck_unit(c.getCheck_unit());

			        checklistServices.addChecklist1(checklist);
			    } else {
			        ExistingChecklist.add(c.getTask());
			        status.setMessage("exist");
			        status.getDatas(ExistingChecklist);
			    }
			}

				status.setMessage("success");
				status.getDatas(ExistingChecklist);
			} catch (Exception e) {	
			}
			return status;
		}


	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> getChecklist() {
		List<Checklist> checklistList = null;
		try {
			checklistList = checklistServices.getChecklistList();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checklistList;
	}
	
	
	@GetMapping(value = "/listByMachineName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> listByMachineName(@RequestParam("machineName") String machineName) {
		List<Checklist> checklistList = null;
		try {
			System.out.println("machineName  "+machineName);
			checklistList = checklistServices.listByMachineName(machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checklistList;
	}
	
	
	@GetMapping(value = "/listByMachineEquipId", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> listByMachineEquipId(@RequestParam("equipId") String equipId) {
		List<Checklist> checklistList = null;
		try {
			
			checklistList = checklistServices.listByMachineEquipId(equipId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checklistList;
	}
	
	
	@GetMapping(value = "/listByMachineEquipIdAndFreq", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Checklist> listByMachineEquipId(@RequestParam("equipId") String equipId,@RequestParam("frequency") String frequency) {
		List<Checklist> checklistList = null;
		try {
			String freq = null;
			if(frequency.equalsIgnoreCase("weekly")) {
				freq="W";
			}
			if(frequency.equalsIgnoreCase("monthly")) {
				freq="M";		
			}
			if(frequency.equalsIgnoreCase("quarterly")) {
				freq="Q";
			}
			if(frequency.equalsIgnoreCase("halfyearly")) {
				freq="H/Y";
			}
			if(frequency.equalsIgnoreCase("yearly")) {
				freq="Y";
			}
			
			checklistList = checklistServices.listByMachineEquipIdAndFrequency(equipId,freq);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checklistList;
	}
	
	
	@GetMapping(value= "/delete/{checklist_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteChecklist(@PathVariable("checklist_id") int checklist_id){
		try{
			checklistServices.deleteChecklist(checklist_id);
			return new Status("Checklist Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	@GetMapping(value = "/getchecklist", produces = MediaType.APPLICATION_JSON_VALUE)
	
	public List<Checklist> getChecklists(@RequestParam("machine_id") int machine_id, @RequestParam("freq") String freq) {
		List<Checklist> checkList = new ArrayList<>();;
	
		try {
			
			System.out.println(machine_id);
			String frequency="";
			System.out.println(freq);
			if(freq.equalsIgnoreCase("quarterly")) {
				frequency="Q";
			}
			if(freq.equalsIgnoreCase("weekly")) {
				frequency="W";
			}
			if(freq.equalsIgnoreCase("halfyearly")) {
				frequency="H/Y";
			}
			if(freq.equalsIgnoreCase("monthly")) {
				frequency="M";
			}
			if(freq.equalsIgnoreCase("yearly")) {
				frequency="Y";
			}
			System.out.println("fre "+frequency);
			checkList = checklistRepo.getChecklistByMachineNFrequency(machine_id, frequency);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkList;
	}
	
	
	@PutMapping("/Update/{id}")
	public ResponseEntity<?> updateChecklist(@PathVariable int id, @RequestBody Checklist updatedChecklist) {
	    Optional<Checklist> optionalChecklist = checklistRepo.findById(id);

	    if (!optionalChecklist.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Checklist checklist = optionalChecklist.get();
	    
	    checklist.setTask(updatedChecklist.getTask());
	    checklist.setOperation(updatedChecklist.getOperation());
	    checklist.setAcceptableRange(updatedChecklist.getAcceptableRange());
	    checklist.setFrequency(updatedChecklist.getFrequency());
	
	    checklist.setCheckType(updatedChecklist.getCheckType());
	    checklist.setLower_range(updatedChecklist.getLower_range());
	    checklist.setUpper_range(updatedChecklist.getUpper_range());
	    checklist.setCheck_unit(updatedChecklist.getCheck_unit());

	    if (updatedChecklist.getMachine() != null) {
	        checklist.setMachine(updatedChecklist.getMachine());
	    }

	    checklistRepo.save(checklist);

	    return ResponseEntity.ok(updatedChecklist);

	}


	
	
}
