package com.kfMaintenancce.service;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.BreakdownSpareReport;
import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.repo.MaintSpareRepo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

@Service
public class MaintSpareServiceImpl  implements MaintSpareServices {

    @Autowired
    private MaintSpareRepo maintSpareRepository;

    public List<MaintSpare> getAllSpares() {
        return maintSpareRepository.findAll();
    }

    public Optional<MaintSpare> getSpareById(int id) {
        return maintSpareRepository.findById(id);
    }

    public MaintSpare saveSpare(MaintSpare maintSpare) {
        return maintSpareRepository.save(maintSpare);
    }

    public MaintSpare updateSpare(int id, MaintSpare updatedSpare) {
        return maintSpareRepository.findById(id).map(spare -> {
            spare.setSpare_name(updatedSpare.getSpare_name());
            spare.setSupplier_name(updatedSpare.getSupplier_name());
            spare.setCode(updatedSpare.getCode());
            spare.setActive(updatedSpare.getActive());
            return maintSpareRepository.save(spare);
        }).orElseThrow(() -> new RuntimeException("Spare not found with id " + id));
    }

    public void deleteSpare(int id) {
        maintSpareRepository.deleteById(id);
    }
    
    public MaintSpare findById(int maintspare_id) {
        return maintSpareRepository.findById(maintspare_id).orElse(null); // Return null if not found
    }
    
    
    
//    public void exportBreakdownSpareReport(HttpServletResponse response, List<BreakdownSpareReport> reports) throws IOException {
//        
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Breakdown Spare Report");
//
//       
//        Row headerRow = sheet.createRow(0);
//        String[] columns = {
//                "Sr No", "Spare Name", "Breakdown Slip No.", "Machine Name", 
//                "Machine Equipment Id", "Breakdown Raised Date", "Shift Name", 
//                "Root Cause", "Action Plan", "Spare Cost", "Spare Available Quantity"
//        };
//        for (int i = 0; i < columns.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(columns[i]);
//        }
//
//        
//        int rowIdx = 1;
//        for (int i = 0; i < reports.size(); i++) {
//            BreakdownSpareReport report = reports.get(i);
//            Row row = sheet.createRow(rowIdx++);
//
//            row.createCell(0).setCellValue(i + 1); 
//            row.createCell(1).setCellValue(report.getSpareName()); 
//            row.createCell(2).setCellValue(report.getBreakdown().getBd_slip()); 
//            row.createCell(3).setCellValue(report.getBreakdown().getMachine().getMachine_name()); 
//            row.createCell(4).setCellValue(report.getBreakdown().getMachine().getEqid()); 
//            row.createCell(5).setCellValue(report.getBreakdown().getTicket_raised_time().toString()); 
//            row.createCell(6).setCellValue(report.getBreakdown().getShift().getShift_name()); 
//            row.createCell(7).setCellValue(report.getBreakdown().getRoot_cause()); 
//            row.createCell(8).setCellValue(report.getBreakdown().getPrev_action_plan()); 
//            row.createCell(9).setCellValue(report.getMaintSpareStock().getUnitPrice()); 
//            row.createCell(10).setCellValue(report.getMaintSpareStock().getQuantity()); 
//        }
//
//        
//        for (int i = 0; i < columns.length; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=breakdown_spare_report.xlsx");
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }

    
}

