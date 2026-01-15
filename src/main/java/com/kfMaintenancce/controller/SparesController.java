package com.kfMaintenancce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.*;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.SpareReportDTO;
import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.SpareConsumptionRepo;
import com.kfMaintenancce.repo.SpareRepo;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.service.SpareReportService;
import com.kfMaintenancce.service.SpareServices;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;


import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/spares")
public class SparesController {
	@Autowired
	SpareServices spareService;
	
	@Autowired
    private SpareReportService spareReportService;
	
	@Autowired
    private SpareRepo spareRepo;
	
	@Autowired
	ComplaintRepoOLD complaintRepo;
	
	  @Autowired
	    private SpareConsumptionRepo spareConsumptionRepository;

   

	@PostMapping(value = "/addSpares", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addSpares(@RequestBody Spare spares ) {
		Status status= new Status();
		try {
			spareService.addSpare(spares);
			status.setCode(200);
			status.setMessage("Spares is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@GetMapping("/getSparesByLimit")
	public @ResponseBody List<Spare> getSpareByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Spare> list = new ArrayList<Spare>();
		try {
			list = spareService.getSpareByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getSparesCount", method = RequestMethod.GET)
	public @ResponseBody int getSpareCount() {
		int count = 0;
		try {
				count = (int) spareService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getSparesByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Spare> getSpareByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Spare> list = new ArrayList<Spare>();
		try {
			
			list = spareService.getSpareByLimitAndGroupSearch(groupSearchDTO);


//			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllSapres", method = RequestMethod.GET)
	public @ResponseBody List<Spare> getAllSapres() {
		List<Spare> list = new ArrayList<Spare>();
		try {
			
			list = spareService.getAllSapres();


//			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getSparesCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getSpareCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = spareService.getSpareCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	 @DeleteMapping("/delete/{spare_id}")
	    public ResponseEntity<Map<String, String>> deleteSpare(@PathVariable int spare_id) {
		 spareService.deleteSpare(spare_id);
	        String message = "Spares with ID " + spare_id + " has been deleted successfully.";
	        Map<String, String> response = new HashMap<>();
	        response.put("message", message);
	        return ResponseEntity.ok(response);
	    }

	 
	  @PostMapping("/uploadExcel")
	    public ResponseEntity<Status> uploadExcel(@RequestPart("file") MultipartFile file) throws EncryptedDocumentException, InvalidFormatException {
	        Status status = new Status();
	        try {
	        	spareService.processExcelFile(file);
	            status.setCode(200);
	            status.setMessage("Excel file uploaded and processed successfully");
	        } catch (IOException e) {
	            e.printStackTrace();
	            status.setCode(500);
	            status.setMessage("Error occurred while processing the Excel file");
	        }
	        return ResponseEntity.ok(status);
	    }
	  
	  
	  @GetMapping("/exportExcel")
	    public ResponseEntity<byte[]> exportExcel() {
	        try {
	            byte[] excelBytes = spareService.generateExcelFile();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	            headers.setContentDispositionFormData("attachment", "Spare.xlsx");
	            headers.setContentLength(excelBytes.length);
	            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	 // Update the active status of a spare by spare_id
	    @PutMapping("/updateSpareStatus/{spare_id}")
	    public ResponseEntity<Status> updateSpareStatus(@PathVariable("spare_id") int spare_id, @RequestParam("active") int active) {
	        Status status = new Status();
	        try {
	            Optional<Spare> spareOptional = spareRepo.findById(spare_id); // Assuming you have a method to find a spare by its ID
	            if (spareOptional.isPresent()) {
	                Spare spare = spareOptional.get();

	                if (active == 1 || active == 0) {
	                    spare.setActive(active);
	                    spareRepo.save(spare); 
	                    
	                    status.setCode(200);
	                    status.setMessage("Spare status updated successfully.");
	                } else {
	                    status.setCode(400);
	                    status.setMessage("Invalid status value. Only 1 (active) or 0 (inactive) are allowed.");
	                }
	            } else {
	                status.setCode(404);
	                status.setMessage("Spare not found for the given spare ID.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            status.setCode(500);
	            status.setMessage("Error updating spare status: " + e.getMessage());
	        }
	        return ResponseEntity.status(HttpStatus.valueOf(status.getCode())).body(status);
	    }
	    
	

	    @GetMapping("/utilization-report/comp/{compId}")
	    public ResponseEntity<List<SpareUtilizationReportDTO>> getSpareUtilizationReportByCompId(@PathVariable Integer compId) {
	        List<SpareUtilizationReportDTO> report = spareReportService.getSpareUtilizationReportByCompId(compId);
	        if (report.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(report);
	    }


	    

	    //utilization spare report by Id
	    @GetMapping("/utilization-report/{spareId}")
	    public ResponseEntity<List<SpareUtilizationReportDTO>> getSpareUtilizationReport(@PathVariable int spareId) {
	        List<SpareUtilizationReportDTO> report = spareReportService.getSpareUtilizationReportById(spareId);
	        if (report.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(report);
	    }





    @GetMapping("/utilization-report")
    public ResponseEntity<List<SpareUtilizationReportDTO>> getSpareUtilizationReport(
            @RequestParam(required = false) Integer spareId) {

        List<SpareUtilizationReportDTO> report;

        if (spareId != null) {
            // call method with spareId
            report = spareReportService.getSpareUtilizationReportById(spareId);
        } else {
            // call method without spareId
            report = spareReportService.getSpareUtilizationReport();
        }

        if (report.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(report);
    }




    @GetMapping("/utilization-reportWithPage")
    public ResponseEntity<Page<SpareUtilizationReportDTO>> searchPaginatedReport(
            @RequestParam(required = false) Integer spareId,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(spareReportService.getPaginatedSearchReport(spareId, page, size));
    }

    @GetMapping("/utilization-reportWithSearch")
    public ResponseEntity<Page<SpareUtilizationReportDTO>> searchPaginatedReport(
            @RequestParam(required = false) Integer spareId,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Page<SpareUtilizationReportDTO> report =
                spareReportService.getPaginatedSearchReport(spareId, keyword, page, size);

        return ResponseEntity.ok(report);
    }


    @GetMapping("/combinedSpareReport")
	    public ResponseEntity<?> getCombinedSpareReport(@RequestParam(required = false) Integer spareId) {
	        try {
	            if (spareId != null) {
	                // Fetch single spare report
	                SpareReportDTO report = spareReportService.getCombinedSpareStockAndUtilizationReportBySpareId(spareId);
	                if (report == null) {
	                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                            .body(Map.of("message", "Spare not found with ID: " + spareId));
	                }
	                return ResponseEntity.ok(report);
	            } else {
	                // Fetch all spares report
	                List<SpareReportDTO> reports = spareReportService.getCombinedSpareStockAndUtilizationReport();
	                return ResponseEntity.ok(reports);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("message", "Error while generating report", "error", e.getMessage()));
	        }
	    }

	    
	    @GetMapping("/combinedSpareReportPaged")
	    public ResponseEntity<?> getCombinedSpareReportPaged(
	            
	            @RequestParam int page,
	            @RequestParam int size) {

	        try {
	            Map<String, Object> report = spareReportService.getCombinedSpareReportPaged(null, page, size);
	            return ResponseEntity.ok(report);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("message", "Error while fetching paged report", "error", e.getMessage()));
	        }
	    }

	    
	    @GetMapping("/combinedSpareReportPagedandSearch")
	    public ResponseEntity<?> getCombinedSpareReportPaged(
	            @RequestParam(required = false) Integer spareId,
	            @RequestParam(defaultValue = "") String keyword,
	            @RequestParam int page,
	            @RequestParam int size) {

	        try {
	            Map<String, Object> report = spareReportService.getCombinedSpareReportPaged(spareId, keyword, page, size);
	            return ResponseEntity.ok(report);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Map.of("message", e.getMessage()));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("message", "Error while fetching combined report", "error", e.getMessage()));
	        }
	    }

	    
	    
	    
	//spare stock report by id    
	  @GetMapping("/spare_stock/{spareId}")
	    public ResponseEntity<List<SpareReportDTO>> getSpareStockReport(@PathVariable("spareId") int spareId) {
	        try {
	            List<SpareReportDTO> report = spareReportService.getSpareStockReportById(spareId);
	            if (report.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(report);
	            }
	            return ResponseEntity.ok(report);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }  
	  ////////////////////////////////////////////////////////////////////////////////////////////////
	//spare stock report   
	    @GetMapping("/spare_stock")
	    public ResponseEntity<List<SpareReportDTO>> getSpareStockReport() {
	        try {
	            List<SpareReportDTO> report = spareReportService.getSpareStockReport();
	            if (report.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(report);
	            }
	            return ResponseEntity.ok(report);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body(null);
	        }
	    }
	 // 🔹 Pagination + Search + Count APIs
	    @GetMapping("/spare_stockWithPageandSearch")
	    public ResponseEntity<Map<String, Object>> getSpareStockReport(
	            @RequestParam int page,
	            @RequestParam int size,
	            @RequestParam(defaultValue = "") String keyword) {

	        Map<String, Object> report = spareReportService.getSpareStockReportPaged(page, size, keyword);
	        return ResponseEntity.ok(report);
	    }
	    
	    @GetMapping("/spare_stockWithPage")
	    public ResponseEntity<Map<String, Object>> getSpareStockReportpage(
	            @RequestParam int page,
	            @RequestParam int size)
	       {

	        Map<String, Object> report = spareReportService.getSpareStockReportPaged(page, size, null);
	        return ResponseEntity.ok(report);
	    }

	    // 🔹 Count API
	    @GetMapping("/spare_stockWithCount")
	    public ResponseEntity<Map<String, Long>> getSpareStockCount(
	            @RequestParam(required = false) String keyword) {
	        long count = spareReportService.getSpareStockCount(keyword);
	        Map<String, Long> response = new HashMap<>();
	        response.put("totalSpareRecords", count);
	        return ResponseEntity.ok(response);
	    }

	    ///////////////////////////////////////////////////////////////////////////////////

	    	
	    
	    @GetMapping("/spares_export")
	    public ResponseEntity<byte[]> exportSpareReportToExcel(HttpServletResponse response,
	                                                           @RequestParam(name = "spareId", required = false) Integer spareId) {
	        List<SpareReportDTO> spareReportDTOList;

	        // If spareId is provided, get specific spare report; otherwise, get all spares
	        if (spareId != null) {
	            spareReportDTOList = spareReportService.getSpareStockReportById(spareId);
	        } else {
	            spareReportDTOList = spareReportService.getSpareStockReport();
	        }

	        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Spare Report");

	            // Create header row
	            Row headerRow = sheet.createRow(0);
	            CellStyle headerStyle = workbook.createCellStyle();
	            headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	            String[] headers = {"Sr No", "Spare Name", "Available Quantity", "Utilized Quantity", "Total Quantity"};
	            for (int i = 0; i < headers.length; i++) {
	                Cell cell = headerRow.createCell(i);
	                cell.setCellValue(headers[i]);
	                cell.setCellStyle(headerStyle);
	            }

	            // Fill data rows
	            int rowNum = 1;
	            for (SpareReportDTO report : spareReportDTOList) {
	                Row row = sheet.createRow(rowNum++);
	                row.createCell(0).setCellValue(rowNum - 1); // Sr No
	                row.createCell(1).setCellValue(report.getSpare().getSpare_name()); // Spare Name
	                row.createCell(2).setCellValue(report.getAvailableQuantity()); // Available Quantity
	                row.createCell(3).setCellValue(report.getUtilizedQuantity()); // Utilized Quantity
	                row.createCell(4).setCellValue(report.getTotalQuantity()); // Total Quantity
	            }

	            // Auto-size columns
	            for (int i = 0; i < headers.length; i++) {
	                sheet.autoSizeColumn(i);
	            }

	            // Write the output to a byte array
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            workbook.write(byteArrayOutputStream);

	            // Set response headers
	            HttpHeaders headersOutput = new HttpHeaders();
	            headersOutput.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SpareReport.xlsx");
	            headersOutput.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headersOutput, HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	    
	    @GetMapping("/Utilized_export")
	    public void exportSpareUtilizationReport(HttpServletResponse response) throws IOException {
	        List<SpareUtilizationReportDTO> reports = spareReportService.getSpareUtilizationReport();
	        if (!reports.isEmpty()) {
	           
	            spareReportService.exportSpareUtilizationReport(response, reports);
	        } else {
	           
	            response.sendError(HttpServletResponse.SC_NO_CONTENT, "No utilization reports found.");
	        }
	    }
	

}
