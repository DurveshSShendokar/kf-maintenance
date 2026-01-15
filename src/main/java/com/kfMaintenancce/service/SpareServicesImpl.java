package com.kfMaintenancce.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.repo.SpareRepo;
import com.kfMaintenancce.repo.SparesRepo;

@Service
public class SpareServicesImpl  implements SpareServices{

	@Autowired
	SpareRepo sparesReposi;
	
	@Autowired
	SparesRepo spareoldRepo;

	public void addSpare(Spare s) {
		// TODO Auto-generated method stub
		sparesReposi.save(s);
	}
	
	public void addSpares(Spares s) {
		// TODO Auto-generated method stub
		spareoldRepo.save(s);
	}
	
	public List<Spares> getSpareByBreakdownId(int breakdown_id) {
		// TODO Auto-generated method stub
		return spareoldRepo.getSpareByBreakdownId(breakdown_id);
	}
	
	
	

	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) sparesReposi.count();
	}
	
	public List<Spare> getSpareByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return sparesReposi.getSpareByLimit(pageNo,perPage);
	}


	
	public List<Spare> getSpareByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return sparesReposi.getSpareByLimitAndGroupSearch(groupSearchDTO);
	}


	public int getSpareCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return sparesReposi.getSpareCountByLimitAndGroupSearch(groupSearchDTO);
	}
	
	 public void deleteSpare(int spare_id) {
		 sparesReposi.deleteById(spare_id);
	    }
	 
	 @Transactional
	 public void processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {
	     Workbook workbook = WorkbookFactory.create(file.getInputStream());
	     Sheet sheet = workbook.getSheetAt(0); 

	     for (Row row : sheet) {
	         // Skip header row
	         if (row.getRowNum() == 0) continue;

	         Spare spare = new Spare();
	         spare.setSpare_name(getStringValue(row.getCell(1)));
	         spare.setSuuplier_name(getStringValue(row.getCell(2)));
	         spare.setCode(getStringValue(row.getCell(3)));
	        
	         
	         sparesReposi.save(spare);
	     }
	     workbook.close();
	 }
	 
 
	 
	 private String getStringValue(Cell cell) {
	     if (cell == null) {
	         return null;
	     }
	     cell.setCellType(CellType.STRING); // Ensure cell is treated as string
	     return cell.getStringCellValue();
	 }
	 
	 
	 public byte[] generateExcelFile() throws IOException {
		    List<Spare> spareList = sparesReposi.findAll();
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("Spare");
		    int rowNum = 0;

		    // Create the header row
		    Row headerRow = sheet.createRow(rowNum++);
		    String[] headers = {"Sr. No.", "Spare Name", "Code", "Supplier Name"};
		    for (int i = 0; i < headers.length; i++) {
		        headerRow.createCell(i).setCellValue(headers[i]);
		    }

		    // Populate data rows
		    int srNo = 1;
		    for (Spare spare : spareList) {
		        Row row = sheet.createRow(rowNum++);
		        // Populate Sr. No.
		        row.createCell(0).setCellValue(srNo++);
		        // Populate other columns with spare data
		        row.createCell(1).setCellValue(spare.getSpare_name());
		        row.createCell(2).setCellValue(spare.getCode());
		        row.createCell(3).setCellValue(spare.getSuuplier_name());
		       
		       
		    }

		    // Auto-size columns
		    for (int i = 0; i < headers.length; i++) {
		        sheet.autoSizeColumn(i);
		    }

		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    workbook.write(outputStream);
		    workbook.close();
		    return outputStream.toByteArray();
		}

	@Override
	public List<Spare> getAllSapres() {
		// TODO Auto-generated method stub
		return sparesReposi.findAll();
	}
	
	 public Spare findById(int spare_id) {
	        return sparesReposi.findById(spare_id)
	                .orElseThrow(() -> new EntityNotFoundException("Spare not found with ID: " + spare_id));
	    }


	 

}
