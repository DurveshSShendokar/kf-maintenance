package com.kfMaintenancce.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Category;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Location;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.repo.AssetInventoryRepo;
import com.kfMaintenancce.repo.CategoryRepo;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.LocationRepo;
@Service
public class AssetInventoryServiceImpl implements AssetInventoryService{
	

	@Autowired
	AssetInventoryRepo assetInventoryRepo;
	
	@Autowired
	private CategoryRepo categoryRepository;
	
	@Autowired
	private LocationRepo locationRepository;
	
	@Autowired
	private LabRepo labRepository;

	@Override
	public void addAssetInventory(AssetInventory assetInventory) {
		// TODO Auto-generated method stub
		assetInventoryRepo.save(assetInventory);
	}

	@Override
	public List<AssetInventory> getAssetInventoryByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return assetInventoryRepo.getAssetInventoryByLimit(pageNo,perPage);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) assetInventoryRepo.count();
	}
	
	
	    public List<AssetInventory> getAllAssets() {
	        return assetInventoryRepo.findAll();
	    } 

	@Override
	public List<AssetInventory> getAssetInventoryByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return assetInventoryRepo.getAssetInventoryByLimitAndGroupSearch(groupSearchDTO);
	}

	@Override
	public int getAssetCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return assetInventoryRepo.getAssetCountByLimitAndGroupSearch(groupSearchDTO);
	}

	 public void deleteassetInventory(int asset_inventory_id) {
	        Optional<AssetInventory> assetInventoryOptional = assetInventoryRepo.findById(asset_inventory_id);
	        if (assetInventoryOptional.isPresent()) {
	        	assetInventoryRepo.deleteById(asset_inventory_id);
	        } else {
	            throw new RuntimeException("Asset_Inventory not found with id: " + asset_inventory_id);
	        }
	    }
	 public AssetInventory findById(int asset_inventory_id) {
	        Optional<AssetInventory> assetInventoryOptional = assetInventoryRepo.findById(asset_inventory_id);
	        return assetInventoryOptional.orElse(null);
	    }

	 
	  @Transactional
	    public boolean processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {
	        Workbook workbook = WorkbookFactory.create(file.getInputStream());
	        Sheet sheet = workbook.getSheetAt(0);

	        boolean duplicatesFound = false;

	        for (Row row : sheet) {
	            // Skip header row
	            if (row.getRowNum() == 0) continue;

	            AssetInventory assetInventory = new AssetInventory();
	            assetInventory.setMachine(getStringValue(row.getCell(1)));
	            assetInventory.setEquipmentId(getStringValue(row.getCell(2)));
	            // Check if equipment ID already exists
	            if (assetInventoryRepo.existsByEquipmentId(assetInventory.getEquipmentId())) {
	                // Skip saving this entry if equipment ID already exists
	                duplicatesFound = true;
	                continue;
	            }
	            assetInventory.setModel(getStringValue(row.getCell(3)));
	            assetInventory.setComapnyName(getStringValue(row.getCell(4)));
	            assetInventory.setLoginName(getStringValue(row.getCell(5)));
	            assetInventory.setOpSystem(getStringValue(row.getCell(6)));
	            assetInventory.setRamCapacity(getStringValue(row.getCell(7)));
	            assetInventory.setHardDiskCap(getStringValue(row.getCell(8)));
	            assetInventory.setCategory(getCategory(getStringValue(row.getCell(9))));
	            assetInventory.setSubCat(getStringValue(row.getCell(10)));
	           // assetInventory.setLocation(getLocation(getStringValue(row.getCell(11))));
	            assetInventory.setLab(getLab(getStringValue(row.getCell(11))));
	            
	            assetInventory.setIt_Dept(getStringValue(row.getCell(12)));
	            assetInventory.setPlant_Loc(getStringValue(row.getCell(13)));
	            assetInventory.setLabCode(getStringValue(row.getCell(14)));
	            assetInventory.setLabInsideLoc(getStringValue(row.getCell(15)));
	         //   assetInventory.setSubCat(getStringValue(row.getCell(16)));
	            assetInventory.setLaserPrint(getStringValue(row.getCell(16)));
	            assetInventory.setBarCodePrint(getStringValue(row.getCell(17)));

	            assetInventoryRepo.save(assetInventory);
	        }
	        workbook.close();

	        return duplicatesFound;
	    }



	 
	 private Category getCategory(String catName) {
		 Optional<Category> categoryOptional = categoryRepository.findByCatName(catName);
		 if (categoryOptional.isPresent()) {
	            return categoryOptional.get();
	        } else {
	        	Category category= new Category();
	        	category.setCatName(catName);
	        return 	categoryRepository.save(category);
	          //  throw new RuntimeException("Category not found for name: " + catName);
	        }	        }

		private Location getLocation(String locationName) {
			Optional<Location> locationOptional = locationRepository.findByLocationName(locationName);
			 if (locationOptional.isPresent()) {
		            return locationOptional.get();
		        } else {
		        	Location location= new Location();
		        	location.setLocationName(locationName);
		        	return locationRepository.save(location);
		           // throw new RuntimeException("Location not found for name: " + locationName);
		        }		}

		private Lab getLab(String labName) {
			 Optional<Lab> labOptional = labRepository.findByLabName(labName);
			 if (labOptional.isPresent()) {
		            return labOptional.get();
		        } else {
		        	Lab lab= new Lab();
		        	lab.setLabName(labName);
		        	return	labRepository.save(lab);
		        	
		          //  throw new RuntimeException("Lab not found for name: " + labName);
		        }
		        }	 
	 
	 private String getStringValue(Cell cell) {
	     if (cell == null) {
	         return null;
	     }
	     cell.setCellType(CellType.STRING); // Ensure cell is treated as string
	     return cell.getStringCellValue();
	 }
	 
	 
	 public byte[] generateExcelFile() throws IOException {
		    List<AssetInventory> assetInventoryList = assetInventoryRepo.findAll();
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("Asset Inventory");
		    int rowNum = 0;

		    // Create the header row
		    Row headerRow = sheet.createRow(rowNum++);
		    String[] headers = {"Sr. No.", "Machine", "Equipment ID", "Model", "Comp Name", "Login Name", "Operating System", "RAM Capacity", "Hard Disk Capacity",
		    		"Category","SubCategory", "Lab", "It_Dept_Code", "Plant_Loc", "Lab_Code", "Lab_Inside_Loc", "Laser_Printer", "BarCode_Printer"};
		    for (int i = 0; i < headers.length; i++) {
		        headerRow.createCell(i).setCellValue(headers[i]);
		    }

		    // Populate data rows
		    int srNo = 1;
		    for (AssetInventory assetInventory : assetInventoryList) {
		        Row row = sheet.createRow(rowNum++);
		        // Populate Sr. No.
		        row.createCell(0).setCellValue(srNo++);
		        // Populate other columns with asset inventory data
		        row.createCell(1).setCellValue(assetInventory.getMachine());
		        row.createCell(2).setCellValue(assetInventory.getEquipmentId());
		        row.createCell(3).setCellValue(assetInventory.getModel());
		        row.createCell(4).setCellValue(assetInventory.getComapnyName());
		        row.createCell(5).setCellValue(assetInventory.getLoginName());
		        row.createCell(6).setCellValue(assetInventory.getOpSystem());
		        row.createCell(7).setCellValue(assetInventory.getRamCapacity());
		        row.createCell(8).setCellValue(assetInventory.getHardDiskCap());
		        row.createCell(9).setCellValue(assetInventory.getCategory().getCatName());
		        row.createCell(10).setCellValue(assetInventory.getSubCat());
		      //  row.createCell(11).setCellValue(assetInventory.getLocation().getLocationName());
		        row.createCell(11).setCellValue(assetInventory.getLab().getLabName());
		        
		        row.createCell(12).setCellValue(assetInventory.getIt_Dept());
		        row.createCell(13).setCellValue(assetInventory.getPlant_Loc());
		        row.createCell(14).setCellValue(assetInventory.getLabCode());
		        row.createCell(15).setCellValue(assetInventory.getLabInsideLoc());
		       // row.createCell(16).setCellValue(assetInventory.getSubCat());
		        row.createCell(16).setCellValue(assetInventory.getLaserPrint());
		        row.createCell(17).setCellValue(assetInventory.getBarCodePrint());
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
	 
	 public byte[] generateExcelTemplate() throws IOException {
		    List<AssetInventory> assetInventoryList = assetInventoryRepo.findAll(); // You may not need this list for a template
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("Asset Inventory");
		    int rowNum = 0;

		    // Create the header row
		    Row headerRow = sheet.createRow(rowNum++);
		    String[] headers = {"Sr. No.", "Machine", "Equipment ID", "Model", "Comp Name", "Login Name", "Operating System", "RAM Capacity", "Hard Disk Capacity",
		            "Category","SubCategory", "Lab", "It_Dept_Code", "Plant_Loc", "Lab_Code", "Lab_Inside_Loc", "Laser_Printer", "BarCode_Printer"};
		    for (int i = 0; i < headers.length; i++) {
		        headerRow.createCell(i).setCellValue(headers[i]);
		    }

		    // Optional: Add a row with sample data or leave it empty for user input

		    // Auto-size columns
		    for (int i = 0; i < headers.length; i++) {
		        sheet.autoSizeColumn(i);
		    }

		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    workbook.write(outputStream);
		    workbook.close();
		    return outputStream.toByteArray();
		}

	 
	 
	 public List<AssetInventory> getAssetsByModel(String model) {
			// TODO Auto-generated method stub
			return assetInventoryRepo.getAssetsByModel(model);
		}
	 
	 public List<AssetInventory> getNonAllocatedAssets() {
	        return assetInventoryRepo.findNonAllocatedAssets();
	    }
	 
	 

	
}
