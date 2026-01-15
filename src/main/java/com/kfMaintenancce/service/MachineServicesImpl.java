package com.kfMaintenancce.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.MachineDetailsDTO;
import com.kfMaintenancce.dto.MachinechatbotDTO;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Upload_Manual;
import com.kfMaintenancce.repo.CategoryRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.uploadManualRepo;





@Service
public class MachineServicesImpl  implements MachineServices{
	@Autowired
	MachineRepo machineRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
    private uploadManualRepo UploadManualRepo;

	@Transactional
	public void addMachine(Machine machine) {
		// TODO Auto-generated method stub
		machineRepo.save(machine);
	}

	@Override
	public List<Machine> getMachineList() {
		// TODO Auto-generated method stub
		List<Machine> machines = machineRepo.findAll();
		return machines;
	}
	
	@Override
	public List<Machine> getMachineLists(int pageNo, int pageSize) {
	    Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // pageNo is 1-based
	    Page<Machine> page = machineRepo.findAll(pageable);
	    return page.getContent();
	}
	
	

@Override
public Page<MachinechatbotDTO> getMachineDTOs(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // pageNo is 1-based
    return machineRepo.findAllMachineDTO(pageable);
}
	
	/*
	 * public Page<Machine> getMachineAllList(int page, int size) { Pageable
	 * pageable = PageRequest.of(page, size); return
	 * machineRepo.findMachines(pageable); }
	 */
	
	
	public List<Machine> getMachineAllList(int page, int size) {
	    Page<Machine> machinePage = machineRepo.findMachines(PageRequest.of(page, size));
	    return machinePage.getContent(); 
	}

	
	  public Optional<Machine> getMachineById(int machineId) {
	        return machineRepo.findById(machineId);
	    }

	@Override
	public List<Machine> getGetMachinesByName(String machine_name) {
		// TODO Auto-generated method stub
		return machineRepo.getGetMachinesByName(machine_name);
	}

	@Override
	public void deleteMachine(int machine_id) {
		// TODO Auto-generated method stub
		Optional<Machine> optional= machineRepo.findById(machine_id);
		machineRepo.delete(optional.get());
	}

	@Override
	public Optional<Machine> getGetMachinesByEquipemtnId(String equipmentId) {
		// TODO Auto-generated method stub
		return machineRepo.findByEqid(equipmentId);
	}

	@Override
	public List<Machine> getMachinesByName(String machineName) {
		// TODO Auto-generated method stub
		return machineRepo.getGetMachinesByName(machineName);
	}

	@Override
	public List<Machine> getMachineListByLocation(String location) {
		// TODO Auto-generated method stub
		return machineRepo.getMachineListByLocation(location);
	}

	@Override
	public List<Machine> getMachineListByLocationAndMachine(String location, String machineName) {
		// TODO Auto-generated method stub
		return machineRepo.getMachineListByLocationAndMachine(location,machineName);
	}
	
	
	public MachineDetailsDTO getMachineDetailsWithUserManual(String eqid) {
	    // Fetch machine by equipment ID
	    Machine machine = machineRepo.findByEqid(eqid)
	            .orElseThrow(() -> new RuntimeException("Machine not found with Eqid: " + eqid));

	    // Fetch manuals by equipment ID
	    List<Upload_Manual> manuals = UploadManualRepo.findByMachineEqid(eqid);

	    // Create and return DTO
	    MachineDetailsDTO dto = new MachineDetailsDTO();
	    dto.setMachine(machine);
	    dto.setUserManuals(manuals);
	    return dto;
	}
	
	
	public void uploadMachineExcel(MultipartFile file) throws Exception {
	    try (InputStream is = file.getInputStream()) {
	        XSSFWorkbook workbook = new XSSFWorkbook(is);
	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rows = sheet.iterator();

	        // Skip the header row
	        if (rows.hasNext()) {
	            rows.next();
	        }

	        while (rows.hasNext()) {
	            Row row = rows.next();
	            Machine machine = new Machine();

	            // Map Excel fields to the Machine entity with null checks
	            machine.setMachine_name(getCellValueAsString(row.getCell(0)));
	            machine.setEqid(getCellValueAsString(row.getCell(1)));
	            machine.setMake(getCellValueAsString(row.getCell(2)));
	            machine.setModel(getCellValueAsString(row.getCell(3)));
	            machine.setCapacity(getCellValueAsString(row.getCell(4)));
	            machine.setLocation(getCellValueAsString(row.getCell(5)));
	            machine.setSrNo(getCellValueAsString(row.getCell(6)));
//	            machine.setType(getCellValueAsInt(row.getCell(7)));
//	            machine.setActive(getCellValueAsInt(row.getCell(8)));

	            // Save Machine
	            machineRepo.save(machine);
	        }
	    }
	}

	private String getCellValueAsString(Cell cell) {
	    if (cell == null) {
	        return null; // Return null if the cell is empty
	    }
	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue();
	        case NUMERIC:
	            return String.valueOf((int) cell.getNumericCellValue());
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case FORMULA:
	            return cell.getCellFormula();
	        default:
	            return null; // Return null for other types
	    }
	}

   

	private int getCellValueAsInt(Cell cell) {
	    if (cell == null) {
	        return 0; // Default value for null cells
	    }
	    if (cell.getCellType() == CellType.NUMERIC) {
	        return (int) cell.getNumericCellValue();
	    } else if (cell.getCellType() == CellType.STRING) {
	        try {
	            return Integer.parseInt(cell.getStringCellValue());
	        } catch (NumberFormatException e) {
	            return 0; // Default value if parsing fails
	        }
	    }
	    return 0;
	}

	

}
