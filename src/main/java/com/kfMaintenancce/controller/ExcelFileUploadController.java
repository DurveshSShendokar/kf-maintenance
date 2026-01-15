package com.kfMaintenancce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.model.UploadedExcelFile;
import com.kfMaintenancce.repo.UploadedExcelFileRepository;
import com.kfMaintenancce.service.ExcelFileUploadServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/PPM")
public class ExcelFileUploadController {

	@Autowired
	ExcelFileUploadServiceImpl excelFileUploadService;
	
	@Autowired
	UploadedExcelFileRepository UploadedExcelFileRepo;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file,
                                              @RequestParam("labId") int labId) {
        try {
            String response = excelFileUploadService.uploadExcelFile(file, labId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/download/{labId}")
    public ResponseEntity<InputStreamResource> downloadFileByLab(@PathVariable int labId, HttpServletResponse response) {
        try {
          
            List<UploadedExcelFile> files = UploadedExcelFileRepo.findByLabIdOrderByUploadedAtDesc(labId);
            
            if (files.isEmpty()) {
                return ResponseEntity.status(404).body(null);
            }

            UploadedExcelFile fileRecord = files.get(0);
            File file = new File(fileRecord.getFilePath());

            if (!file.exists()) {
                return ResponseEntity.status(404).body(null);
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileRecord.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(Files.probeContentType(file.toPath())))
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/viewByLab/{labId}")
    public ResponseEntity<List<UploadedExcelFile>> getFilesByLab(@PathVariable int labId) {
        List<UploadedExcelFile> files = excelFileUploadService.getFilesByLab(labId);
        return ResponseEntity.ok(files);
    }
    
    @GetMapping("/viewAll")
    public ResponseEntity<List<UploadedExcelFile>> getAllFiles() {
        List<UploadedExcelFile> files = UploadedExcelFileRepo.findAll();
        return ResponseEntity.ok(files);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUploadedExcelFile(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UploadedExcelFile> optionalFile = UploadedExcelFileRepo.findById(id);
            if (!optionalFile.isPresent()) {
                response.put("status", "error");
                response.put("message", "UploadedExcelFile with id " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            UploadedExcelFile file = optionalFile.get();

            // Optional: delete physical file
            try {
                Path path = Paths.get(file.getFilePath());
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                response.put("status", "error");
                response.put("message", "Failed to delete physical file: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            // Delete from database
            UploadedExcelFileRepo.deleteById(id);

            response.put("status", "success");
            response.put("message", "UploadedExcelFile with id " + id + " deleted successfully.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    

    
}
