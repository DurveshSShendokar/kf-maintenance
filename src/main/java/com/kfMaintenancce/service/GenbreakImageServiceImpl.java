package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.model.General_breakdown;
import com.kfMaintenancce.model.genbreak_Image;
import com.kfMaintenancce.repo.GenBreak_ImageRepo;
import com.kfMaintenancce.repo.General_breakdownRepo;

@Service
public class GenbreakImageServiceImpl  implements GenbreakImageService{
	
	 @Autowired
	    private GenBreak_ImageRepo genbreakImageRepo;
	 @Autowired
	    private General_breakdownRepo generalBreakdownRepo;
//
//	 public genbreak_Image saveImage(MultipartFile images, int genbreakId) throws IOException {
//	        genbreak_Image image = new genbreak_Image();
//	        image.setImageData(images.getBytes()); // Save the image as bytes
//	        image.setImageUrl(images.getOriginalFilename()); // Set the image URL to the original file name
//
//	        // Set the General_breakdown
//	        General_breakdown breakdown = generalBreakdownRepo.findById(genbreakId).orElse(null);
//	        if (breakdown != null) {
//	            image.setGeneral_breakdown(breakdown);
//	        }
//
//	        return genbreakImageRepo.save(image); // Save the image
//	    }
//	 
	 
	 
	 
	  private final String IMAGE_DIRECTORY ="D:\\eclipse-workspace\\KFMaintenanceAP\\KFMaintenanceAP\\src\\main\\resources\\static\\BreakImages"; // Change to your desired directory

	  public genbreak_Image saveImages(MultipartFile images, int genbreakId) throws IOException {
		    // Create the directory if it doesn't exist
		    File directory = new File(IMAGE_DIRECTORY);
		    if (!directory.exists()) {
		        directory.mkdirs();
		    }

		    // Save the image file to the filesystem
		    String originalFilename = images.getOriginalFilename();
		    String newFilename = System.currentTimeMillis() + "_" + originalFilename; // Avoid overwriting files
		    Path filePath = Paths.get(IMAGE_DIRECTORY, newFilename);
		    Files.copy(images.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // Replace existing file if needed

		    // Create a new genbreak_Image object
		    genbreak_Image genImage = new genbreak_Image();
		    genImage.setImageUrl(filePath.toString()); // Save the path to the image in the database

		    // Set the General_breakdown
		    General_breakdown breakdown = generalBreakdownRepo.findById(genbreakId).orElse(null);
		    if (breakdown != null) {
		        genImage.setGeneral_breakdown(breakdown);
		    }

		    return genbreakImageRepo.save(genImage); // Save the image record
		}

	
}
