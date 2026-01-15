package com.kfMaintenancce.service;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.UploadedExcelFile;
import com.kfMaintenancce.repo.LabRepo;

import com.kfMaintenancce.repo.UploadedExcelFileRepository;




@Service
public class ExcelFileUploadServiceImpl {
	
	
	@Autowired
	LabRepo labRepository;
	
	@Autowired
	UploadedExcelFileRepository fileRepository;
	
	
//	 public String uploadExcelFile(MultipartFile file, int labId) throws Exception {
//	       
//	        Optional<Lab> labOptional = labRepository.findById(labId);
//	        if (labOptional.isEmpty()) {
//	            throw new Exception("Lab not found with ID: " + labId);
//	        }
//	        Lab lab = labOptional.get();
//
//	        String uploadDir = "uploads/excel/";  
//	        File directory = new File(uploadDir);
//	        if (!directory.exists()) {
//	            directory.mkdirs();
//	        }
//
//	        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//	        Path destinationPath = Path.of(filePath);
//	        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
//
//	 
//	        UploadedExcelFile uploadedFile = new UploadedExcelFile(
//	                file.getOriginalFilename(),
//	                filePath,
//	                lab,
//	                LocalDateTime.now()
//	        );
//	        fileRepository.save(uploadedFile);
//
//	        return "File uploaded successfully: " + file.getOriginalFilename();
//	    }
//	 
	 
	
	
	public String uploadExcelFile(MultipartFile file, int labId) throws Exception {
	    Optional<Lab> labOptional = labRepository.findById(labId);
	    if (!labOptional.isPresent()) {  // Java 8 compatible check
	        throw new Exception("Lab not found with ID: " + labId);
	    }
	    Lab lab = labOptional.get();

	    String uploadDir = "uploads/excel/";  
	    File directory = new File(uploadDir);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
	    Path destinationPath = Paths.get(filePath); 
	    Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

	    UploadedExcelFile uploadedFile = new UploadedExcelFile(
	        file.getOriginalFilename(),
	        filePath,
	        lab,
	        LocalDateTime.now()
	    );
	    fileRepository.save(uploadedFile);

	    return "File uploaded successfully: " + file.getOriginalFilename();
	}

	 
	 public List<UploadedExcelFile> getFilesByLab(int labId) {
		    return fileRepository.findByLabId(labId);
		}
	 
	 
	 


}
