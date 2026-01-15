 package com.kfMaintenancce.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import javax.crypto.Mac;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell; // Import Cell
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.MachineDetailsDTO;
import com.kfMaintenancce.dto.MachinechatbotDTO;
import com.kfMaintenancce.dto.ReportReqObj;
import com.kfMaintenancce.dto.ResponceObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.UserManualStatus;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.MachineMaintSpare;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.MachienOperation;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.Upload_IT_Manual;
import com.kfMaintenancce.model.Upload_Manual;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.CategoryRepo;
import com.kfMaintenancce.repo.ChecklistRepo;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.MachineSpareAssignRepository;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;
import com.kfMaintenancce.repo.uploadManualRepo;
import com.kfMaintenancce.service.MachineServices;
import com.kfMaintenancce.service.MachineSpareAssignService;
import com.kfMaintenancce.service.MaintSpareServices;


@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping(value={"/machine_mst"})

public class MachineController {

	@Autowired
	MachineServices machineServices;//
	
	@Autowired
	MaintSpareServices maintSpareServices;

	@Autowired
	MachineSpareAssignService machineSpareAssignService;
	
	@Autowired
	com.kfMaintenancce.repo.MachineRepo machineRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ChecklistRepo checklistRepo;
	
	
	@Autowired
     MaintenenaceCheckPointRepo maintenenaceCheckPointRepo;
	
	@Autowired
	uploadManualRepo UploadManualRepo;
	
	@Autowired
	MachineSpareAssignRepository machineSpareAssignRepository;
	
	 @Autowired
	     UserDetailsRepo userDetailsRepo;
	
	@Autowired
	MachineOwnerRepo machineOwnerRepo;
	
	@Autowired
	MaintRepo maintRepo;
	
	@Autowired
	BreakdownRepo breakdownRepo;
	
	
	
	

@GetMapping("/getMachineByLimit")
public @ResponseBody List<Machine> getMachineByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Machine> list = new ArrayList<Machine>();
	try {
		list = machineRepo.getMachineByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geMachineCount")
public @ResponseBody int getMachineCount() {
	int count = 0;
	try {
			count = (int) machineRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getMachineByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Machine> getMachineByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Machine> list = new ArrayList<Machine>();
	try {
		
		list = machineRepo.getMachineByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getMachineCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getMachineCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = machineRepo.getMachineCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}
	

	   @GetMapping("/machine_history")
	    public ResponseEntity<?> getMachineHistory(
	            @RequestParam int machineId,
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {

	        try {
	            List<Breakdown> breakdowns = breakdownRepo.findByMachineAndDateRange(machineId, fromDate, toDate);
	            List<Maint> maints = maintRepo.findByMachineAndDateRange(machineId, fromDate, toDate);

	            Map<String, Object> response = new HashMap<>();
	            response.put("breakdowns", breakdowns);
	            response.put("maintenances", maints);

	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error fetching machine history: " + e.getMessage());
	        }
	    }
	
	
	
	
	@PutMapping("/deactivateMachine/{machineId}")
	public ResponseEntity<Map<String, Object>> deactivateMachine(@PathVariable int machineId) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        Machine machine = machineRepo.findById(machineId)
	                .orElseThrow(() -> new EntityNotFoundException("Machine not found with ID: " + machineId));

	        // Deactivate machine
	        machine.setActive(0);
	        machineRepo.save(machine);

	        // Deactivate related checklists
	        checklistRepo.deactivateChecklistsByMachineId(machineId);

	        response.put("status", "success");
	        response.put("message", "Machine and associated checklists deactivated successfully.");
	        response.put("machineId", machineId);

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("status", "error");
	        response.put("message", "An error occurred while deactivating the machine.");
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@GetMapping("/uniqueMachines/{labId}")
	public ResponseEntity<?> getUniqueMachinesByLabId(@PathVariable int labId) {
	    try {
	        List<Machine> uniqueMachines = machineRepo.findDistinctMachinesByLabId(labId);
	        return ResponseEntity.ok(uniqueMachines);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error fetching unique machines: " + e.getMessage());
	    }
	}

	
	@GetMapping("/uniqueMachiness/{labId}")
	public ResponseEntity<?> getUniqueMachinesByLabIds(@PathVariable int labId) {
	    try {
	        List<Machine> uniqueMachines = machineRepo.findDistinctMachinesByLabId(labId);
	        if (uniqueMachines == null) {
	            uniqueMachines = new ArrayList<>();
	        }
	        return ResponseEntity.ok(uniqueMachines);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("message", "Error fetching unique machines: " + e.getMessage()));
	    }
	}


	
	
	
    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            machineServices.uploadMachineExcel(file);
            return ResponseEntity.ok("Excel file uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
	
	
	@PostMapping("/uploadMachineExcel")
	public Status uploadMachineExcel(@RequestPart MultipartFile file) {
	    Status status = new Status();
	    try {
	        // Create directory to store uploads if it doesn't exist
	        File dir = new File(System.getProperty("catalina.base"), "uploads");
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // Save uploaded file temporarily
	        File uploadedFile = new File(dir, file.getOriginalFilename());
	        file.transferTo(uploadedFile);

	        // Open Excel workbook
	        try (FileInputStream excelFile = new FileInputStream(uploadedFile);
	             Workbook workbook = new XSSFWorkbook(excelFile)) {

	            Sheet sheet = workbook.getSheetAt(0);
	            Set<String> processedEquipmentIds = new HashSet<>();

	            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Start from row 1 (skip headers)
	                Row row = sheet.getRow(i);
	                if (row == null) continue;

	                // Extract data from Excel cells
	                String machineName = getStringValue(row.getCell(1));
	                String categoryName = getStringValue(row.getCell(2)).trim().toLowerCase();


	                String type = getStringValue(row.getCell(3));
	                String equipmentId = getStringValue(row.getCell(4));
	                String make = getStringValue(row.getCell(5));
	                String model = getStringValue(row.getCell(6));
	                String capacity = getStringValue(row.getCell(7));
	                String location = getStringValue(row.getCell(8));
	                String srNo = getStringValue(row.getCell(9));


	                // Skip empty rows or duplicate equipment IDs
	                if (equipmentId == null || processedEquipmentIds.contains(equipmentId)) {
	                    continue;
	                }
	                processedEquipmentIds.add(equipmentId);

	                // Find category by name
	                List<Category> categoryList = categoryRepo.findAllByCategoryName(categoryName);
	                if (categoryList.isEmpty()) {
	                    System.err.println("Category not found for row " + i + ": " + categoryName);
	                    continue;
	                }
	                Category category = categoryList.get(0);

	                // Determine machine type
	                int machineType = "Auxiliary".equalsIgnoreCase(type) ? 2 : "Machine".equalsIgnoreCase(type) ? 1 : -1;

	                // Check if the machine already exists
	                Optional<Machine> optionalMachine = machineServices.getGetMachinesByEquipemtnId(equipmentId);
	                Machine machine = optionalMachine.orElse(new Machine());

	                // Set machine properties
	                machine.setMachine_name(machineName);
	                machine.setCategory(category);
	                machine.setType(machineType);
	                machine.setEqid(equipmentId);
	                machine.setMake(make);
	                machine.setModel(model);
	                machine.setCapacity(capacity);
	                machine.setLocation(location);
	                machine.setSrNo(srNo);
	                machine.setDeletes(1);
	                machine.setActive(1); // Mark as active

	                // Save machine to the database
	                machineRepo.save(machine);
	                System.out.println("Saved Machine: " + machine);
	            }
	        }

	        status.setCode(200);
	        status.setMessage("Machines imported successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        status.setCode(500);
	        status.setMessage("Failed to import machines: " + e.getMessage());
	    }

	    return status;
	}

	private String getStringValue(Cell cell) {
	    if (cell == null) {
	        return null;
	    }
	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue();
	        case NUMERIC:
	            return String.valueOf((int) cell.getNumericCellValue());
	        default:
	            return null;
	    }
	}


	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Status addMachine(@RequestBody Machine machine) {
		Status   status= new Status();
		try {
			if(machine.getMachine_id()!=0) {
			 Optional<Machine> optional= machineServices.getGetMachinesByEquipemtnId(machine.getEqid());
			 System.out.println(" MACHINE Updated");
			 	Machine machine1=optional.get();
            	machine1.setActive(machine.getActive());
            	machine1.setCapacity(machine.getCapacity());
            	machine1.setCategory(machine.getCategory());
            	machine1.setDeletes(machine.getDeletes());
            	machine1.setLocation(machine.getLocation());
            	machine1.setMachine_name(machine.getMachine_name());
            	machine1.setMake(machine.getMake());
            	machine1.setModel(machine.getModel());
            	machine1.setSrNo(machine.getSrNo());
            	machine1.setType(machine.getType());
            	machineServices.addMachine(machine);
            	status.setCode(200);
    			status.setMessage("Machine Detials Upadted Successfully !");
            	return status;
			}else {
				System.out.println("NEW MACHINE ADDED ");
				Optional<Machine> optional= machineServices.getGetMachinesByEquipemtnId(machine.getEqid());
	            if(optional.isPresent()) {
	            	status.setCode(500);
	    			status.setMessage("Equipemtnt Id  Already Exits!");
	            	return status;
	            }else {
	            	machine.setDeletes(1);
	    			System.out.println(machine.toString());
	    			machineServices.addMachine(machine);
	    			status.setCode(200);
	    			status.setMessage("Machine Added Successfully !");
	    			return status ;
	            }
			}
			   
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}


	 @GetMapping("/unique_names")
	    public ResponseEntity<List<String>> getUniqueMachineNames() {
	        List<String> uniqueNames = machineRepo.findUniqueMachineNames();
	        return ResponseEntity.ok(uniqueNames);
	    }
	 
	  @GetMapping("/unique_equipment_ids")
	  public ResponseEntity<List<Map<String, String>>> getUniqueMachineNamesAndEquipmentIds() {
	        List<Map<String, String>> uniqueMachineData = machineRepo.findUniqueMachineNamesAndEquipmentIds().stream()
	            .map(result -> {
	                Map<String, String> machineMap = new HashMap<>();
	                machineMap.put("machineName", (String) result[0]);
	                machineMap.put("equipmentId", (String) result[1]);
	                return machineMap;
	            })
	            .collect(Collectors.toList());

	        return ResponseEntity.ok(uniqueMachineData);
	    }
	  
	  @GetMapping("/equipment_ids_by_machine_name")
	    public ResponseEntity<List<String>> getEquipmentIdsByMachineName(@RequestParam String machineName) {
	        List<String> equipmentIds = machineRepo.findEquipmentIdsByMachineName(machineName);
	        return ResponseEntity.ok(equipmentIds);
	    }
	  
	  
	
	
	@GetMapping(value = "/listByName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getlistByName(@RequestParam("machineName") String machineName) {
		List<Machine> machineList = null;
		try {
			
			machineList = machineServices.getMachinesByName(machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineList;
	}
	/////////////////-----Total machines card-------/////////////////
	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getMachines() {
		List<Machine> machineList = null;
		try {
			
			machineList = machineServices.getMachineList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineList;
	}
	
	@GetMapping("/listWithPaginationDTO")
	public ResponseEntity<Map<String, Object>> getMachinesDTO(
	        @RequestParam(defaultValue = "1") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize) {

	    Page<MachinechatbotDTO> page = machineServices.getMachineDTOs(pageNo, pageSize);

	    Map<String, Object> response = new HashMap<>();
	    response.put("machines", page.getContent());
	    response.put("currentPage", pageNo);
	    response.put("totalItems", page.getTotalElements());
	    response.put("totalPages", page.getTotalPages());

	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("/Pagelist")
	public Page<Machine> getMachinesByLab(
	        @RequestParam int page,
	        @RequestParam int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    return machineRepo.findByLabName("GH1", pageable);
	}

	
	
	@GetMapping("/PagelistSearch")
	public ResponseEntity<Map<String, Object>> getMachines(
	        @RequestParam int page,
	        @RequestParam int size,
	        @RequestParam(defaultValue = "") String searchText) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Machine> machinePage = machineRepo.searchMachines(searchText, pageable);

	    Map<String, Object> response = new HashMap<>();
	    response.put("machines", machinePage.getContent());
	    response.put("currentPage", machinePage.getNumber());
	    response.put("totalItems", machinePage.getTotalElements());
	    response.put("totalPages", machinePage.getTotalPages());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@GetMapping("/Pagelist/totalCount")
	public ResponseEntity<Map<String, Object>> getTotalCount(@RequestParam(defaultValue = "") String searchText) {
	    long total = machineRepo.countMachines(searchText);
	    Map<String, Object> response = new HashMap<>();
	    response.put("totalCount", total);
	    return ResponseEntity.ok(response);
	}


	
	@GetMapping(value = "/export/list", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public void exportMachinesToExcel(HttpServletResponse response) {
	    try {
	       
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=machines.xlsx");

	      
	        List<Machine> machineList = machineServices.getMachineList();

	        
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Machines");

	       
	        Row headerRow = sheet.createRow(0);
	        String[] headers = {"SrNo", "Machine Name", "Eqid", "Location","make","Model","Capacity","category"}; 
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	        }

	       
	        int rowIndex = 1;
	        for (Machine machine : machineList) {
	            Row row = sheet.createRow(rowIndex++);
	            row.createCell(0).setCellValue(rowIndex - 1);
	            row.createCell(1).setCellValue(machine.getMachine_name());
	            row.createCell(2).setCellValue(machine.getEqid());
	           
	            row.createCell(3).setCellValue(machine.getLocation());
	            row.createCell(4).setCellValue(machine.getMake());
	            row.createCell(5).setCellValue(machine.getModel());
	            row.createCell(6).setCellValue(machine.getCapacity());
	            row.createCell(7).setCellValue(machine.getCategory().getCatName());
	        }

	      
	        workbook.write(response.getOutputStream());
	        workbook.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to export Excel file: " + e.getMessage());
	    }
	}
//////////////-----------///////////////////
	
	
	@GetMapping(value = "/getMachineListByLocation", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getMachineListByLocation(@RequestParam("location") String location) {
		List<Machine> machineList = null;
		try {
			
			machineList = machineServices.getMachineListByLocation(location);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineList;
	}
	
	@GetMapping(value = "/getMachineListByLocationAndMachine", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getMachineListByLocationAndMachine(@RequestParam("location") String location,@RequestParam("machineName") String machineName) {
		List<Machine> machineList = null;
		try {
			
			machineList = machineServices.getMachineListByLocationAndMachine(location,machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineList;
	}
	@GetMapping(value = "/getMachineLocationslist", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Set<String> getMachineLocationslist() {
		Set<String> locations = new HashSet<String>();
		try {
			
			List<Machine>		machineList = machineServices.getMachineList();
			for(Machine  machine:machineList) {
				locations.add(machine.getLocation());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locations;
	}
	@PostMapping(value = "/getGetMachinesByName", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Machine> getGetMachinesByName(@RequestBody Machine machine) {
		List<Machine> list= new ArrayList<>();
		try {
			
			
			System.out.println("MAINT ID "+machine.getMachine_name());
			list = machineServices.getGetMachinesByName(machine.getMachine_name());
		} catch (Exception e) {
			e.printStackTrace();
			// new Status(e.toString());
		}
			return list;
	}
	
	
	 @GetMapping("/getMachinesByLabAndName")
	    public ResponseEntity<Map<String, Object>> getMachinesByLabAndName(
	            @RequestParam int labId,
	            @RequestParam String machineName) {

	        Map<String, Object> response = new HashMap<>();

	        try {
	            List<Machine> machines = machineRepo.findByLabIdAndMachineName(labId, machineName);

	            if (machines.isEmpty()) {
	                response.put("status", "error");
	                response.put("message", "No machines found for the provided labId and machineName.");
	                response.put("data", Collections.emptyList());
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	            response.put("status", "success");
	            response.put("message", "Machines fetched successfully.");
	            response.put("data", machines);
	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.put("status", "error");
	            response.put("message", "Error fetching machines: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	
	@PostMapping(value = "/getGetMachinesByEqipId", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponceObj getGetMachinesByEqipId(@RequestBody ReportReqObj reportReqObj) {
		ResponceObj responceObj= new ResponceObj();
		try {
			
			Optional<Machine> optional=machineServices.getGetMachinesByEquipemtnId(reportReqObj.getEquipmentId());
			if (optional.isPresent()) {
				responceObj.setCode(200);
				responceObj.setMessage("Valid QR CODE ");
				responceObj.setData(optional.get());
			}else {
				responceObj.setCode(500);
				responceObj.setMessage("Invalid Valid QR CODE ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// new Status(e.toString());
		}
			return responceObj;
	}

	/*@GetMapping(value = "/getGetMachinesByName/{machineName}", produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody
	List<Machine> getGetMachinesByName(@PathVariable("machineName") String machineName) {
		List<Machine> machines = null;
		try {
			
			System.out.println("MAINT ID "+machineName);
			machines = machineServices.getGetMachinesByName(machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machines;
	}
	
	
*/
	@GetMapping(value = "/machineName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Set<String> getMachineNames() {
		Set<String> machineNames = new HashSet<String>();
		
		try {
			
			List<Machine> machineList = machineServices.getMachineList();
			for(Machine machine: machineList){
				machineNames.add(machine.getMachine_name());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineNames;
	}
	
	@GetMapping(value = "/machineEqidsByNameID", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Map<String, Object>> getMachineEqidsByNameandLabId(
	        @RequestParam String machineName,
	        @RequestParam int labId) {

	    List<Map<String, Object>> resultList = new ArrayList<>();
	    try {
	        List<Machine> machines = machineRepo.findByMachineNameAndLabId(machineName, labId);
	        for (Machine machine : machines) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("machine_id", machine.getMachine_id());
	            map.put("eqid", machine.getEqid());
	            resultList.add(map);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultList;
	}


	@GetMapping(value = "/machineEqidsByName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Map<String, Object>> getMachineEqidsByName(@RequestParam String machineName) {
	    List<Map<String, Object>> resultList = new ArrayList<>();
	    try {
	        List<Machine> machines = machineRepo.findByMachineName(machineName);
	        for (Machine machine : machines) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("machine_id", machine.getMachine_id());
	            map.put("eqid", machine.getEqid());
	            resultList.add(map);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultList;
	}

	
	@GetMapping(value = "/getMachineNameForUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Set<String> getMachineNameForUser(@RequestParam("userId") int userId) {
		Set<String> machineNames = new HashSet<String>();
		
		try {
			List<MachineOwner> machineOwners=machineOwnerRepo.getAllMachineOwnersByUser(userId);
			List<Machine> machineList = machineServices.getMachineList();
			for(Machine machine: machineList){
				machineNames.add(machine.getMachine_name());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineNames;
	}
	
	
	@GetMapping(value= "/delete/{machine_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteMachine(@PathVariable("machine_id") int machine_id){
		try{
			machineServices.deleteMachine(machine_id);
			return new Status("Machine Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	
	
	
//upload user manual
	@PostMapping(value = "/uploadUserManual", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody UserManualStatus uploadUserManual(@RequestParam("files") MultipartFile[] files,
	                                                       @RequestParam("machine_name") String machineName
	                                                       //@RequestHeader("userId") int userId
	                                                       ) 
	{
	    UserManualStatus status = new UserManualStatus();//
	    
//	    String uploadDirPath = "D:\\eclipse-workspace\\KFMaintenanceAP\\KFMaintenanceAP\\src\\main\\resources\\static\\Uploads";
	    String uploadDirPath = "/home/zionit/KFMaintenance/User_manual";
	  //  String uploadDirPath = "D:\\MaintenaceApplication\\Uploads_Maintenance";

	    File dir = new File(uploadDirPath);
	    
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }
	    
	    // Fetch all machines with the specified name
	    List<Machine> machines = machineServices.getMachinesByName(machineName);
	    
//	    UserDetails user = userDetailsRepo.findById(userId)
//	            .orElseThrow(() -> new RuntimeException("User not found"));
//	    String fullName = user.getFirstName() + " " + user.getLastName();
//	    
	    if (!machines.isEmpty()) {
	        try {
	            // Iterate through each file and process
	            for (MultipartFile file : files) {
	                String originalFileName = file.getOriginalFilename();
	                String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
	                
	                File uploadedFile = new File(dir, uniqueFileName);
	                file.transferTo(uploadedFile);
	                
	                // Save each file for every machine
	                for (Machine selectedMachine : machines) {
	                    Upload_Manual uploadManual = new Upload_Manual();
	                    uploadManual.setUserManualName(originalFileName);
	                    uploadManual.setUserManualPath(uploadedFile.getAbsolutePath());
	                    uploadManual.setFileType(file.getContentType());
	                    uploadManual.setUploadDate(new Date());
	                    uploadManual.setMachine(selectedMachine);
	                    //uploadManual.setUploadedByUsername(fullName);
	                    
	                    UploadManualRepo.save(uploadManual);
	                }
	            }
	            
	            status.setCode(200);
	            status.setMessage("User manuals uploaded successfully for machines named: " + machineName);
	        } catch (IOException e) {
	            e.printStackTrace();
	            status.setCode(500);
	            status.setMessage("Error uploading files: " + e.getMessage());
	        }
	    } else {
	        status.setCode(404);
	        status.setMessage("No machines found with the name: " + machineName);
	    }
	    
	    return status;
	}
	
	
	
	// mobile application
	  
	@GetMapping("/Machine_details")
	public ResponseEntity<MachineDetailsDTO> getMachineWithManuals(@RequestParam("eqid") String eqid) {
	    MachineDetailsDTO machineWithManuals = machineServices.getMachineDetailsWithUserManual(eqid);
	    return ResponseEntity.ok(machineWithManuals);
	}

	  // for check points by maint_id 
    @GetMapping("/MachinecheckpointsByID")
    public List<MaintenenaceCheckPoint> getCheckpoints(@RequestParam int maint_id) {
        return maintenenaceCheckPointRepo.findByMaintId(maint_id);
    }

	
	
	//download user manual
	@GetMapping("/downloadUserManual/{upload_id}")
	public ResponseEntity<Resource> downloadUserManual(@PathVariable("upload_id") int upload_id) {
	    Optional<Upload_Manual> manualOptional = UploadManualRepo.findById(upload_id);

	    if (manualOptional.isPresent()) {
	        Upload_Manual manual = manualOptional.get();
	        
	        // Check if the manual is active (active = 1)
	        if (manual.getActive() == 1) {
	            File file = new File(manual.getUserManualPath());

	            if (file.exists()) {
	                Resource resource = new FileSystemResource(file);
	                return ResponseEntity.ok()
	                        .contentType(MediaType.parseMediaType(manual.getFileType()))
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + manual.getUserManualName() + "\"")
	                        .body(resource);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(null);
	            }
	        } else {
	            // Return 403 Forbidden if the file is inactive
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(null);
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(null);
	    }
	}


	//spare assign to machine machineSpareAssignService
	@PostMapping("/{machine_id}/Machinespares")
	public ResponseEntity<Status> addSparesToMachines(
	        @PathVariable int machine_id, 
	        @RequestBody List<Map<String, Integer>> maintspares) { 
	    Status status = new Status();
	    try {
	        Machine machine = machineSpareAssignService.findById(machine_id);
	        if (machine != null) {
	            for (Map<String, Integer> spareData : maintspares) {
	                int maintSpareId = spareData.get("maintspare_id"); 
	                MaintSpare maintSpare = maintSpareServices.findById(maintSpareId); 

	                if (maintSpare != null) {
	                    MachineMaintSpare machineSpareAssign = new MachineMaintSpare();
	                    machineSpareAssign.setMachine(machine); 
	                    machineSpareAssign.setMaintSpare(maintSpare); 
	                    machineSpareAssignService.addSpareAssign(machineSpareAssign);
	                } else {
	                  
	                    status.setCode(404);
	                    status.setMessage("Spare with id " + maintSpareId + " not found");
	                    return ResponseEntity.ok(status);
	                }
	            }
	            status.setCode(200);
	            status.setMessage("Spares assigned to machine successfully");
	        } else {
	            status.setCode(404);
	            status.setMessage("Machine not found");
	        }
	    } catch (Exception e) {
	        status.setCode(500);
	        status.setMessage("Error occurred: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return ResponseEntity.ok(status);
	}

	 
	 
	 
	// Fetch AssetSpareAssign by ID
	    @GetMapping("/fetch/{machine_id}")
	    public  List<MachineMaintSpare>  getSpareAssignById(@PathVariable int machine_id) {
	       List<MachineMaintSpare> machineSpareAssign = machineSpareAssignRepository.findBymachineyID(machine_id);
	       return machineSpareAssign;
	    }
	    
	    
		// Fetch AssetSpareAssign by ID
		    @GetMapping("/fetchMachineSpare/{machine_id}")
		    public  List<MachineMaintSpare>  getMaintSpareAssignById(@PathVariable int machine_id) {
		       List<MachineMaintSpare> machineSpareAssign = machineSpareAssignRepository.findBymachineyID(machine_id);
		       return machineSpareAssign;
		    }
	    
	    
	    // Fetch all AssetSpareAssign 
	    @GetMapping("/fetchAll")
	    public List<MachineMaintSpare> getAllSpareAssigns() {
	        return machineSpareAssignRepository.findAll();
	    }

	    
	    // delete assign spare
	    @DeleteMapping("/{machine_id}/delete")
	    public ResponseEntity<?> deleteAssetSpareAssign(@PathVariable int machine_id) {
	        try {
	        	machineSpareAssignService.deleteMachineSpareAssignById(machine_id);
	            return ResponseEntity.ok().body("MachineSpareAssign with ID " + machine_id + " deleted successfully.");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(e.getMessage());
	        }
	    }
	    
	    
	    
	   
	    // Fetch uploaded document by ID ( machine ID)
	    @GetMapping("/fetchUploadedDocuments/{machine_id}")
	    public ResponseEntity<List<Upload_Manual>> fetchUploadedDocuments(@PathVariable("machine_id") int machine_id) {
	       
	        List<Upload_Manual> uploadedDocuments = UploadManualRepo.findBymachineID(machine_id);

	        if (uploadedDocuments.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(null); 
	        }

	        return ResponseEntity.ok(uploadedDocuments); 
	    }

	    
	    //view upload document (user manual )
	    @GetMapping("/viewUploadedDocument/{upload_id}")
	    public ResponseEntity<Resource> viewUploadedDocument(@PathVariable("upload_id") int upload_id) {
	        Optional<Upload_Manual> manualOptional = UploadManualRepo.findById(upload_id);
	        if (manualOptional.isPresent()) {
	            Upload_Manual manual = manualOptional.get();
	            // Check if the manual is active (1)
	            if (manual.getActive() == 0) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                        .body(null);  
	            }

	            File file = new File(manual.getUserManualPath());
	            if (file.exists()) {
	                Resource resource = new FileSystemResource(file);

	                return ResponseEntity.ok()
	                        .contentType(MediaType.parseMediaType(manual.getFileType()))
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + manual.getUserManualName() + "\"")
	                        .body(resource);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
	        }
	    }


	    // delete user manual ( document)
	    @DeleteMapping("/{id}/document_delete")
	    public ResponseEntity<?> deleteUserDocument(@PathVariable int id) {
	        try {
	        	UploadManualRepo.deleteById(id);
	        	 return ResponseEntity.noContent().build(); 
	        	 } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(e.getMessage());
	        }
	    }
	    
	    
	 // Update the active status of a user manual by upload_id
	    @PutMapping("/updateManualStatus/{upload_id}")
	    public ResponseEntity<Status> updateManualStatus(@PathVariable("upload_id") int upload_id, @RequestParam("active") int active) {
	        Status status = new Status();
	        try {
	            Optional<Upload_Manual> manualOptional = UploadManualRepo.findById(upload_id);
	            if (manualOptional.isPresent()) {
	                Upload_Manual manual = manualOptional.get();
	                
	               
	                if (active == 1 || active == 0) {
	                    manual.setActive(active); 
	                    UploadManualRepo.save(manual);  
	                    
	                    status.setCode(200);
	                    status.setMessage("Manual status updated successfully.");
	                } else {
	                    status.setCode(400);
	                    status.setMessage("Invalid status value. Only 1 (active) or 0 (inactive) are allowed.");
	                }
	            } else {
	                status.setCode(404);
	                status.setMessage("Manual not found for the given upload ID.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            status.setCode(500);
	            status.setMessage("Error updating manual status: " + e.getMessage());
	           
	        }
	        return ResponseEntity.status(HttpStatus.valueOf(status.getCode())).body(status);
	    }

	   
	 

	    
//	    // for checklist by Eqid
//	    @GetMapping("/checklistByEqid")
//	    public List<Checklist> getChecklists(@RequestParam String eqid) {
//	        return checklistRepo.findChecklistsByMachineEqid(eqid);
//	    }
	  
	
}
