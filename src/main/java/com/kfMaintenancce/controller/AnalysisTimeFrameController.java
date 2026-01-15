package com.kfMaintenancce.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import com.kfMaintenancce.dto.BreakdownRepoCountDTO;
import com.kfMaintenancce.dto.BreakdownRepoDTO;
import com.kfMaintenancce.dto.BreakdownSummaryDTO;
import com.kfMaintenancce.model.Analysis_Time_Frame;
import com.kfMaintenancce.service.AnalysisTimeFrameService;
import com.kfMaintenancce.service.BreakdownServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TooManyListenersException;

@RestController
@CrossOrigin("*")
@RequestMapping("/analysis_time_frames")
public class AnalysisTimeFrameController {

    @Autowired
    private AnalysisTimeFrameService analysisTimeFrameService;
    
	@Autowired
	BreakdownServices breakdownServices; 

    
    @GetMapping("/list")
    public ResponseEntity<List<Analysis_Time_Frame>> getAllAnalysisTimeFrames() {
        List<Analysis_Time_Frame> analysisTimeFrames = analysisTimeFrameService.getAllAnalysisTimeFrames();
        return new ResponseEntity<>(analysisTimeFrames, HttpStatus.OK);
    }

   
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Analysis_Time_Frame> getAnalysisTimeFrameById(@PathVariable int id) {
        Optional<Analysis_Time_Frame> analysisTimeFrame = analysisTimeFrameService.getAnalysisTimeFrameById(id);
        return analysisTimeFrame.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

   
    @PostMapping("/create")
    public ResponseEntity<Analysis_Time_Frame> createAnalysisTimeFrame(@RequestBody Analysis_Time_Frame analysisTimeFrame) {
    	List<Analysis_Time_Frame> others=analysisTimeFrameService.getByFor(analysisTimeFrame.getAnalysis_for());
    	
    	for(Analysis_Time_Frame analysis:others) {
    		analysis.setStatus(0);
//    		analysisTimeFrameService.saveAnalysisTimeFrame(analysis);
    	}
    	
    	analysisTimeFrame.setStatus(1);
    	
        Analysis_Time_Frame created = analysisTimeFrameService.saveAnalysisTimeFrame(analysisTimeFrame);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

   
    @PutMapping("/update/{id}")
    public ResponseEntity<Analysis_Time_Frame> updateAnalysisTimeFrame(
            @PathVariable int id,
            @RequestBody Analysis_Time_Frame updatedAnalysisTimeFrame) {
        Analysis_Time_Frame updated = analysisTimeFrameService.updateAnalysisTimeFrame(id, updatedAnalysisTimeFrame);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK)
                               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Map<String, String>> deleteAnalysisTimeFrame(@PathVariable int id) {
        boolean deleted = analysisTimeFrameService.deleteAnalysisTimeFrame(id);
        Map<String, String> response = new HashMap<>();
        
        if (deleted) {
            response.put("message", "Analysis Time Frame deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Analysis Time Frame not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    
 
    
    @GetMapping("/report/{machine_id}")
    public ResponseEntity<Map<String, Object>> getBreakdownReport(
            @PathVariable int machine_id,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        BreakdownRepoCountDTO breakdownReport = breakdownServices.getBreakdownsAndDaysBetween(machine_id, fromDate, toDate);
        List<BreakdownRepoDTO> list = new ArrayList<>();
        int breakdownCount = 0;

        Optional<Analysis_Time_Frame> op = analysisTimeFrameService.getAciveByFor("Breakdown");
        if (op.isPresent()) {
            for (BreakdownRepoDTO reportDTO : breakdownReport.getBreakdowns()) {
                Long noOfDays = reportDTO.getNoOfDays();
                if (Optional.ofNullable(noOfDays).orElse(0L) <= op.get().getNo_Of_Days()) {
                    list.add(reportDTO);
                    breakdownCount++;
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("breakdowns", list);
        response.put("count", breakdownCount);

        return ResponseEntity.ok(response);
    }


    
    
    
    
    
    
    @GetMapping("/summary_report/{machine_id}")
    public ResponseEntity<BreakdownSummaryDTO> getBreakdownSummary(@PathVariable int machine_id) {
        BreakdownRepoCountDTO breakdownReport = breakdownServices.getBreakdownsAndDaysBetween(machine_id);

        if (breakdownReport.getBreakdowns().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }  
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        int numberOfBreakdowns = breakdownReport.getBreakdowns().size();

        for (BreakdownRepoDTO reportDTO : breakdownReport.getBreakdowns()) {
            LocalDateTime ticketRaisedTime = reportDTO.getTicketRaisedTime();
            
            if (fromDate == null || ticketRaisedTime.isBefore(fromDate)) {
                fromDate = ticketRaisedTime;
            }
            
            if (toDate == null || ticketRaisedTime.isAfter(toDate)) {
                toDate = ticketRaisedTime;
            }
        }

        
        BreakdownSummaryDTO summary = new BreakdownSummaryDTO(fromDate, toDate, numberOfBreakdowns);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/summary_report/{machine_id}/export")
    public ResponseEntity<byte[]> exportBreakdownSummary(@PathVariable int machine_id) {
        BreakdownRepoCountDTO breakdownReport = breakdownServices.getBreakdownsAndDaysBetween(machine_id);

        if (breakdownReport.getBreakdowns().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }

        // Initialize date range and breakdown count
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        int numberOfBreakdowns = breakdownReport.getBreakdowns().size();

        for (BreakdownRepoDTO reportDTO : breakdownReport.getBreakdowns()) {
            LocalDateTime ticketRaisedTime = reportDTO.getTicketRaisedTime();
            
            if (fromDate == null || ticketRaisedTime.isBefore(fromDate)) {
                fromDate = ticketRaisedTime;
            }

            if (toDate == null || ticketRaisedTime.isAfter(toDate)) {
                toDate = ticketRaisedTime;
            }
        }

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Breakdown Summary");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Sr. No.", "From Date", "To Date", "Number of Breakdowns"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Populate data (starting from row 1)
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(1);  // Sr. No. is hardcoded to 1, you can update it dynamically in a loop
        dataRow.createCell(1).setCellValue(fromDate != null ? fromDate.toString() : "N/A");
        dataRow.createCell(2).setCellValue(toDate != null ? toDate.toString() : "N/A");
        dataRow.createCell(3).setCellValue(numberOfBreakdowns);

        // Write to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Set response headers for file download
        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.add("Content-Disposition", "attachment; filename=Analysis_Time_Report.xlsx");
        
        return new ResponseEntity<>(outputStream.toByteArray(), headersResponse, HttpStatus.OK);
    }
    
    
    
}
