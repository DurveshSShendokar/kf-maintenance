package com.kfMaintenancce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.kfMaintenancce.dto.SpareReportDTO;
import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import com.kfMaintenancce.dto.UtilizationDetail;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareConsumption;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.repo.AssetAllocationRepo;
import com.kfMaintenancce.repo.AssetInventoryRepo;
import com.kfMaintenancce.repo.ComplaintRepo;
import com.kfMaintenancce.repo.SpareConsumptionRepo;
import com.kfMaintenancce.repo.SpareRepo;
import com.kfMaintenancce.repo.SpareStockRepo;

@Service
public class SpareReportServiceImpl implements SpareReportService {

    @Autowired
    private SpareStockRepo spareStockRepository;

    @Autowired
    private SpareConsumptionRepo spareConsumptionRepository;

    @Autowired
    private SpareRepo spareRepo;


    @Autowired
    private AssetAllocationRepo assetAllocationRepo;

    
    
    
    public List<SpareUtilizationReportDTO> getSpareUtilizationReportById(int spareId) {
        List<SpareUtilizationReportDTO> utilizationReports = new ArrayList<>();

        List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);
        for (SpareConsumption consumption : consumptions) {
            Complaint complaint = consumption.getComplaint();
            AssetInventory assetInventory = complaint.getAssetInventory();
            List<AssetAllocation> assetAllocation = assetAllocationRepo.findByAssetInventory(assetInventory);
            		

            // Create and populate the DTO
            SpareUtilizationReportDTO reportDTO = new SpareUtilizationReportDTO();
            reportDTO.setSpareConsumption(consumption);
            reportDTO.setUtilizedQuantity(consumption.getQuantity());
           
            reportDTO.setAssetAllocation(assetAllocation);
            
            utilizationReports.add(reportDTO);
        }
        return utilizationReports;
    }

    public Page<SpareUtilizationReportDTO> getPaginatedSearchReport(Integer spareId, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<SpareConsumption> pageResult =
                spareConsumptionRepository.searchWithPaginationAndKeyword(spareId, keyword, pageable);

        return pageResult.map(consumption -> {

            Complaint complaint = consumption.getComplaint();
            AssetInventory asset = complaint.getAssetInventory();

            List<AssetAllocation> assetAllocation =
                    assetAllocationRepo.findByAssetInventory(asset);

            SpareUtilizationReportDTO dto = new SpareUtilizationReportDTO();
            dto.setSpareConsumption(consumption);
            dto.setUtilizedQuantity(consumption.getQuantity());
            dto.setAssetAllocation(assetAllocation);

            return dto;
        });
    }


    public List<SpareUtilizationReportDTO> getSpareUtilizationReport() {
        List<SpareUtilizationReportDTO> utilizationReports = new ArrayList<>();

        // Fetch all spare consumptions without filtering by spareId
        List<SpareConsumption> consumptions = spareConsumptionRepository.findAll();
        
        for (SpareConsumption consumption : consumptions) {
            Complaint complaint = consumption.getComplaint();
            AssetInventory assetInventory = complaint.getAssetInventory();
            List<AssetAllocation> assetAllocation = assetAllocationRepo.findByAssetInventory(assetInventory);

            // Create and populate the DTO
            SpareUtilizationReportDTO reportDTO = new SpareUtilizationReportDTO();
            reportDTO.setSpareConsumption(consumption);
            reportDTO.setUtilizedQuantity(consumption.getQuantity());

            reportDTO.setAssetAllocation(assetAllocation);
            utilizationReports.add(reportDTO);
        }
        return utilizationReports;
    }


    public Page<SpareUtilizationReportDTO> getPaginatedReport(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<SpareConsumption> consumptionsPage = spareConsumptionRepository.findAll(pageable);

        return consumptionsPage.map(consumption -> {
            Complaint complaint = consumption.getComplaint();
            AssetInventory asset = complaint.getAssetInventory();
            List<AssetAllocation> allocations = assetAllocationRepo.findByAssetInventory(asset);

            SpareUtilizationReportDTO dto = new SpareUtilizationReportDTO();
            dto.setSpareConsumption(consumption);
            dto.setUtilizedQuantity(consumption.getQuantity());
            dto.setAssetAllocation(allocations);
            return dto;
        });
    }


    public Page<SpareUtilizationReportDTO> getPaginatedSearchReport(Integer spareId, int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<SpareConsumption> consumptionsPage =
                spareConsumptionRepository.searchWithPagination(spareId, pageable);

        return consumptionsPage.map(consumption -> {
            Complaint complaint = consumption.getComplaint();
            AssetInventory asset = complaint.getAssetInventory();
            List<AssetAllocation> allocations = assetAllocationRepo.findByAssetInventory(asset);

            SpareUtilizationReportDTO dto = new SpareUtilizationReportDTO();
            dto.setSpareConsumption(consumption);
            dto.setUtilizedQuantity(consumption.getQuantity());
            dto.setAssetAllocation(allocations);
            return dto;
        });
    }


    // Method to get spare utilization report by ticket number
    public List<SpareUtilizationReportDTO> getSpareUtilizationReportByCompId(Integer compId) {
        List<SpareUtilizationReportDTO> utilizationReports = new ArrayList<>();

        // Fetch spare consumptions associated with the given comp_id
        List<SpareConsumption> consumptions = spareConsumptionRepository.findByComplaintCompId(compId);
        
        for (SpareConsumption consumption : consumptions) {
            Complaint complaint = consumption.getComplaint();
            AssetInventory assetInventory = complaint.getAssetInventory();
            List<AssetAllocation> assetAllocation = assetAllocationRepo.findByAssetInventory(assetInventory);

            // Create and populate the DTO
            SpareUtilizationReportDTO reportDTO = new SpareUtilizationReportDTO();
            reportDTO.setSpareConsumption(consumption);
            reportDTO.setUtilizedQuantity(consumption.getQuantity());
            reportDTO.setAssetAllocation(assetAllocation);
            
            utilizationReports.add(reportDTO);
        }

        return utilizationReports;
    }

    public List<SpareReportDTO> getCombinedSpareStockAndUtilizationReport() {
        List<SpareReportDTO> reportList = new ArrayList<>();
        List<SpareStock> spareStocks = spareStockRepository.findAll();

        for (SpareStock stock : spareStocks) {
            Spare spare = stock.getSpare();
            int spareId = spare.getSpare_id();

            Double totalStock = Optional.ofNullable(spareStockRepository.getTotalStockBySpareId(spareId)).orElse(0.0);
            Double totalUtilized = Optional.ofNullable(spareConsumptionRepository.getTotalUtilizedBySpareId(spareId)).orElse(0.0);
            Double availableQuantity = totalStock - totalUtilized;

            SpareReportDTO dto = new SpareReportDTO();
            dto.setSpare(spare);
            dto.setTotalQuantity(totalStock);
            dto.setUtilizedQuantity(totalUtilized);
            dto.setAvailableQuantity(availableQuantity);

            List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);
            List<UtilizationDetail> utilizationDetails = new ArrayList<>();

            for (SpareConsumption consumption : consumptions) {
                Complaint complaint = consumption.getComplaint();
                utilizationDetails.add(new UtilizationDetail(complaint, consumption.getQuantity()));
            }

            dto.setUtilizationDetails(utilizationDetails);
            reportList.add(dto);
        }

        return reportList;
    }

    public SpareReportDTO getCombinedSpareStockAndUtilizationReportBySpareId(int spareId) {
        Spare spare = spareRepo.findById(spareId).orElse(null);
        if (spare == null) return null;

        Double totalStock = Optional.ofNullable(spareStockRepository.getTotalStockBySpareId(spareId)).orElse(0.0);
        Double totalUtilized = Optional.ofNullable(spareConsumptionRepository.getTotalUtilizedBySpareId(spareId)).orElse(0.0);
        Double availableQuantity = totalStock - totalUtilized;

        SpareReportDTO dto = new SpareReportDTO();
        dto.setSpare(spare);
        dto.setTotalQuantity(totalStock);
        dto.setUtilizedQuantity(totalUtilized);
        dto.setAvailableQuantity(availableQuantity);

        List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);
        List<UtilizationDetail> utilizationDetails = new ArrayList<>();

        for (SpareConsumption consumption : consumptions) {
            Complaint complaint = consumption.getComplaint();
            utilizationDetails.add(new UtilizationDetail(complaint, consumption.getQuantity()));
        }

        dto.setUtilizationDetails(utilizationDetails);
        return dto;
    }
    
    
    ///////////////////

    
    public Map<String, Object> getCombinedSpareReportPaged(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SpareStock> sparePage;

        if (keyword != null && !keyword.isEmpty()) {
            sparePage = spareStockRepository.searchByKeyword(keyword, pageable);
        } else {
            sparePage = spareStockRepository.findAll(pageable);
        }

        List<SpareReportDTO> reportList = new ArrayList<>();

        for (SpareStock stock : sparePage.getContent()) {
            Spare spare = stock.getSpare();
            int spareId = spare.getSpare_id();

            Double totalStock = Optional.ofNullable(spareStockRepository.getTotalStockBySpareId(spareId)).orElse(0.0);
            Double totalUtilized = Optional.ofNullable(spareConsumptionRepository.getTotalUtilizedBySpareId(spareId)).orElse(0.0);
            Double availableQuantity = totalStock - totalUtilized;

            SpareReportDTO dto = new SpareReportDTO();
            dto.setSpare(spare);
            dto.setTotalQuantity(totalStock);
            dto.setUtilizedQuantity(totalUtilized);
            dto.setAvailableQuantity(availableQuantity);

            List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);
            List<UtilizationDetail> utilizationDetails = new ArrayList<>();

            for (SpareConsumption consumption : consumptions) {
                Complaint complaint = consumption.getComplaint();
                utilizationDetails.add(new UtilizationDetail(complaint, consumption.getQuantity()));
            }

            dto.setUtilizationDetails(utilizationDetails);
            reportList.add(dto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("spares", reportList);
        response.put("currentPage", sparePage.getNumber() + 1);
        response.put("totalItems", sparePage.getTotalElements());
        response.put("totalPages", sparePage.getTotalPages());

        return response;
    }

    
    public Map<String, Object> getCombinedSpareReportPaged(Integer spareId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SpareStock> sparePage;

        // ✅ CASE 1: If spareId is given → only one spare (no paging needed)
        if (spareId != null) {
            Spare spare = spareRepo.findById(spareId).orElse(null);
            if (spare == null) {
                throw new RuntimeException("Spare not found with ID: " + spareId);
            }

            Double totalStock = Optional.ofNullable(spareStockRepository.getTotalStockBySpareId(spareId)).orElse(0.0);
            Double totalUtilized = Optional.ofNullable(spareConsumptionRepository.getTotalUtilizedBySpareId(spareId)).orElse(0.0);
            Double availableQuantity = totalStock - totalUtilized;

            SpareReportDTO dto = new SpareReportDTO();
            dto.setSpare(spare);
            dto.setTotalQuantity(totalStock);
            dto.setUtilizedQuantity(totalUtilized);
            dto.setAvailableQuantity(availableQuantity);

            List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);
            List<UtilizationDetail> utilizationDetails = new ArrayList<>();

            for (SpareConsumption consumption : consumptions) {
                Complaint complaint = consumption.getComplaint();
                utilizationDetails.add(new UtilizationDetail(complaint, consumption.getQuantity()));
            }

            dto.setUtilizationDetails(utilizationDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("spares", List.of(dto));
            response.put("currentPage", 1);
            response.put("totalItems", 1);
            response.put("totalPages", 1);
            return response;
        }

        // ✅ CASE 2: Keyword search + pagination
        if (keyword != null && !keyword.isEmpty()) {
            sparePage = spareStockRepository.searchByKeyword(keyword, pageable);
        } else {
            sparePage = spareStockRepository.findAll(pageable);
        }

        List<SpareReportDTO> reportList = new ArrayList<>();

        for (SpareStock stock : sparePage.getContent()) {
            Spare spare = stock.getSpare();
            int sId = spare.getSpare_id();

            Double totalStock = Optional.ofNullable(spareStockRepository.getTotalStockBySpareId(sId)).orElse(0.0);
            Double totalUtilized = Optional.ofNullable(spareConsumptionRepository.getTotalUtilizedBySpareId(sId)).orElse(0.0);
            Double availableQuantity = totalStock - totalUtilized;

            SpareReportDTO dto = new SpareReportDTO();
            dto.setSpare(spare);
            dto.setTotalQuantity(totalStock);
            dto.setUtilizedQuantity(totalUtilized);
            dto.setAvailableQuantity(availableQuantity);

            List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(sId);
            List<UtilizationDetail> utilizationDetails = new ArrayList<>();

            for (SpareConsumption consumption : consumptions) {
                Complaint complaint = consumption.getComplaint();
                utilizationDetails.add(new UtilizationDetail(complaint, consumption.getQuantity()));
            }

            dto.setUtilizationDetails(utilizationDetails);
            reportList.add(dto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("spares", reportList);
        response.put("currentPage", sparePage.getNumber() + 1);
        response.put("totalItems", sparePage.getTotalElements());
        response.put("totalPages", sparePage.getTotalPages());

        return response;
    }

    
    ////////////////////////////////
    

    public List<SpareReportDTO> getSpareStockReportById(int spareId) {
        List<SpareReportDTO> reportList = new ArrayList<>();

        // Get the spare using the spareId
        Spare spare = spareRepo.findById(spareId).orElse(null);
        if (spare == null) {
            return reportList; 
        }

        // Prepare a SpareReportDTO for the specific spare
        SpareReportDTO spareReport = new SpareReportDTO();

        // Get total Quantity stock
        Double totalStock = spareStockRepository.getTotalStockBySpareId(spareId);
        totalStock = (totalStock != null) ? totalStock : 0.0;

        // Get total Quantity utilized
        Double totalUtilized = spareConsumptionRepository.getTotalUtilizedBySpareId(spareId);
        totalUtilized = (totalUtilized != null) ? totalUtilized : 0.0;

        // Calculate available quantity
        Double availableQuantity = totalStock - totalUtilized;

        // Set details in the report
        spareReport.setSpare(spare);
        spareReport.setAvailableQuantity(availableQuantity);
        spareReport.setUtilizedQuantity(totalUtilized);
        spareReport.setTotalQuantity(totalStock);

        // Fetch utilization details for the spare
        List<UtilizationDetail> utilizationDetails = new ArrayList<>();
        List<SpareConsumption> consumptions = spareConsumptionRepository.findSparesBySpareId(spareId);

        for (SpareConsumption consumption : consumptions) {
            // UtilizationDetail has a constructor that accepts a Complaint and quantity
            UtilizationDetail detail = new UtilizationDetail(consumption.getComplaint(), consumption.getQuantity());
            utilizationDetails.add(detail);
        }
        spareReport.setUtilizationDetails(utilizationDetails);

        // Add the report for this spare to the list
        reportList.add(spareReport);

        return reportList; // Return the list containing the single report for the spare
    }
    
    
    public List<SpareReportDTO> getSpareStockReport() {
        List<SpareReportDTO> report = new ArrayList<>();

        // Assume you fetch all spares from the database (you can create a SpareRepository for that)
        List<Spare> spares = spareRepo.findAll();

        for (Spare spare : spares) {
            int spareId = spare.getSpare_id();

            // Get total stock (total available stock in inventory)
            Double totalStock = spareStockRepository.getTotalStockBySpareId(spareId);
            totalStock = (totalStock != null) ? totalStock : 0.0;

            // Get total utilized (total used from the stock)
            Double totalUtilized = spareConsumptionRepository.getTotalUtilizedBySpareId(spareId);
            totalUtilized = (totalUtilized != null) ? totalUtilized : 0.0;

            // Calculate available quantity
            Double availableQuantity = totalStock - totalUtilized;

            // Prepare report data
            SpareReportDTO spareReport = new SpareReportDTO();
            spareReport.setSpare(spare);
            spareReport.setAvailableQuantity(availableQuantity);
            spareReport.setUtilizedQuantity(totalUtilized);

            // Set total quantity as available + utilized
            spareReport.setTotalQuantity(availableQuantity + totalUtilized);

            report.add(spareReport);
        }

        return report;
    }

    @Override
    public Map<String, Object> getSpareStockReportPaged(int page, int size, String keyword) {
    	PageRequest pageable = PageRequest.of(page - 1, size);
    	Page<SpareStock> sparePage;

    	if (keyword != null && !keyword.isEmpty()) {
    	    sparePage = spareStockRepository.searchByKeyword(keyword, pageable); // ✅ Works fine
    	} else {
    	    sparePage = spareStockRepository.findAll(pageable);
    	}

        List<SpareReportDTO> report = new ArrayList<>();

        for (SpareStock stock : sparePage.getContent()) {
            Spare spare = stock.getSpare();
            int spareId = spare.getSpare_id();

            Double totalStock = spareStockRepository.getTotalStockBySpareId(spareId);
            totalStock = (totalStock != null) ? totalStock : 0.0;

            Double totalUtilized = spareConsumptionRepository.getTotalUtilizedBySpareId(spareId);
            totalUtilized = (totalUtilized != null) ? totalUtilized : 0.0;

            Double availableQuantity = totalStock - totalUtilized;

            SpareReportDTO dto = new SpareReportDTO();
            dto.setSpare(spare);
            dto.setAvailableQuantity(availableQuantity);
            dto.setUtilizedQuantity(totalUtilized);
            dto.setTotalQuantity(totalStock);

            report.add(dto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("spares", report);
        response.put("currentPage", sparePage.getNumber() + 1); // pages are 0-based
        response.put("totalItems", sparePage.getTotalElements());
        response.put("totalPages", sparePage.getTotalPages());

        return response;
    }

    @Override
    public long getSpareStockCount(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return spareStockRepository.countByKeyword(keyword);
        } else {
            return spareStockRepository.count();
        }
    }
    
    
    
    
    
    

    public void exportSpareUtilizationReport(HttpServletResponse response, List<SpareUtilizationReportDTO> reports) throws IOException {
        // Create an Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Spare Utilization Report");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Sr No", "Spare Name", "Ticket No.", "Asset Name", "Asset Equipment Id", "Asset Owner", "Complaint Date", "Spare Utilized Date", "Utilized Quantity"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Populate the rows with the report data
        int rowIdx = 1;
        for (int i = 0; i < reports.size(); i++) {
            SpareUtilizationReportDTO report = reports.get(i);
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(i + 1); // Sr No
            row.createCell(1).setCellValue(report.getSpareConsumption().getSpare().getSpare_name()); // Spare Name
            row.createCell(2).setCellValue(report.getSpareConsumption().getComplaint().getTicketNo()); // Ticket No
            row.createCell(3).setCellValue(report.getSpareConsumption().getComplaint().getAssetInventory().getModel()); // Asset Name
            row.createCell(4).setCellValue(report.getSpareConsumption().getComplaint().getAssetInventory().getEquipmentId()); 
            
            if (!report.getAssetAllocation().isEmpty()) {
                AssetAllocation assetAllocation = report.getAssetAllocation().get(0);  // Assuming one allocation per asset
                row.createCell(5).setCellValue(assetAllocation.getAllocateTo().getFirstName());  // Owner Name
            } else {
                row.createCell(5).setCellValue("No Allocation");  // If no allocation exists
            }
            
            row.createCell(6).setCellValue(report.getSpareConsumption().getComplaint().getComplaintDate().toString()); // Complaint Date
            row.createCell(7).setCellValue(report.getSpareConsumption().getConsuptionDate().toString()); // Spare Utilized Date
            row.createCell(8).setCellValue(report.getUtilizedQuantity()); // Utilized Quantity
        }

        // Auto-size the columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the Excel file to the response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=spare_utilization_report.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    
}
