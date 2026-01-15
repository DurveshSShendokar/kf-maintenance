package com.kfMaintenancce.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.ApiResponse;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.ImageResponseDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.General_breakdown;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.model.genbreak_Image;
import com.kfMaintenancce.repo.GenBreak_ImageRepo;
import com.kfMaintenancce.repo.General_breakdownRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.GenbreakImageService;
import com.kfMaintenancce.service.GeneralBreakdownService;

@RestController
@CrossOrigin("*")
@RequestMapping("/general_breakdowns")
public class GeneralBreakdownController 
{

	@Autowired 
	UserDetailsRepo userDetailsRepo;
	
    @Autowired
    private GeneralBreakdownService generalBreakdownService;
    
    @Autowired
    private GenbreakImageService genbreakImageService;
    
    @Autowired
    private GenBreak_ImageRepo genbreakImageRepo;
    
    @Autowired
    private General_breakdownRepo general_breakdownRepo;
    
    
    
    
    


@GetMapping("/getGeneral_breakdownByLimit")
public @ResponseBody List<General_breakdown> getGeneral_breakdownByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<General_breakdown> list = new ArrayList<General_breakdown>();
	try {
		list = general_breakdownRepo.getGeneral_breakdownByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


@RequestMapping(value = "/getGeneral_breakdownByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<General_breakdown> getGeneral_breakdownByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<General_breakdown> list = new ArrayList<General_breakdown>();
	try {
		
		list = general_breakdownRepo.getGeneral_breakdownByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getGeneral_breakdownCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getGeneral_breakdownCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = general_breakdownRepo.getGeneral_breakdownCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@GetMapping("/getGeneral_breakdownCount")
public @ResponseBody int  getGeneral_breakdownCount() {
    int  count= 0;
    try {
        count= (int) general_breakdownRepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}


    
    
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<General_breakdown>> createBreakdown(@RequestBody General_breakdown general_breakdown) {
        try {  
         
            String breakdownNo = generalBreakdownService.getNewBreakDownNo();
            general_breakdown.setBreakdownNo(breakdownNo); 
            
        
            general_breakdown.setOpenDate(new Date());
            general_breakdown.setOpenTime(new Date());
            general_breakdown.setStatus(1);
            
         
            General_breakdown savedBreakdown = generalBreakdownService.saveGeneralBreakdown(general_breakdown);
            
         
            ApiResponse<General_breakdown> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Breakdown created successfully", savedBreakdown);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
          
            ApiResponse<General_breakdown> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while creating the breakdown", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<List<General_breakdown>> getAllBreakdowns() {
        try {
            List<General_breakdown> breakdowns = generalBreakdownService.getAllGeneralBreakdowns();
            return new ResponseEntity<>(breakdowns, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/get/{genbreak_id}")
    public ResponseEntity<General_breakdown> getBreakdownById(@PathVariable("genbreak_id") int genbreak_id) {
        Optional<General_breakdown> breakdown = generalBreakdownService.getGeneralBreakdownById(genbreak_id);

        if (breakdown.isPresent()) {
            return new ResponseEntity<>(breakdown.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @DeleteMapping("/delete/{genbreak_id}")
    public ResponseEntity<String> deleteBreakdown(@PathVariable("genbreak_id") int genbreak_id) {
        try {
            boolean isDeleted = generalBreakdownService.deleteGeneralBreakdownById(genbreak_id);
            if (isDeleted) {
                return new ResponseEntity<>("Breakdown deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Breakdown not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting breakdown: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<General_breakdown> updateBreakdown(
            @PathVariable int id,
            @RequestBody General_breakdown general_breakdown) {
        try {
          
            General_breakdown existingBreakdown = generalBreakdownService.findById(id);
            
           
            if (existingBreakdown == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            }

            // Update the existing breakdown with new values
            existingBreakdown.setConclusion(general_breakdown.getConclusion());
            existingBreakdown.setRootCause(general_breakdown.getRootCause());
            existingBreakdown.setTitle(general_breakdown.getTitle());
            existingBreakdown.setCause(general_breakdown.getCause());
          
            existingBreakdown.setTrialsheet(general_breakdown.getTrialsheet());
            existingBreakdown.setStatus(general_breakdown.getStatus());
            existingBreakdown.setDescription(general_breakdown.getDescription());
            existingBreakdown.setOpenTime(general_breakdown.getOpenTime());
            existingBreakdown.setOpenDate(general_breakdown.getOpenDate());
            existingBreakdown.setClosedTime(general_breakdown.getClosedTime());
            existingBreakdown.setClosedDate(general_breakdown.getClosedDate());
//            existingBreakdown.setRaisedBy(general_breakdown.getRaisedBy());
//            existingBreakdown.setClosedby(general_breakdown.getClosedby());
            existingBreakdown.setLab(general_breakdown.getLab());
            existingBreakdown.setLocation(general_breakdown.getLocation());

           
            General_breakdown updatedBreakdown = generalBreakdownService.saveGeneralBreakdown(existingBreakdown);

           
            return new ResponseEntity<>(updatedBreakdown, HttpStatus.OK);
        } catch (Exception e) {
          
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    @GetMapping("/closedGeneral_breakdown/{id}")
    public ResponseEntity<General_breakdown> getClosedGeneralBreakdownById(@PathVariable int id) {
        try {
            Optional<General_breakdown> breakdown = general_breakdownRepo.getClosedGeneralBreakdownById(id);
            if (breakdown.isPresent()) {
                return ResponseEntity.ok(breakdown.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    //mobile App
    @PutMapping("/closegeneralBreakdown")
    public ResponseEntity<Map<String, Object>> closeBreakdown(
    		@RequestParam("genbreak_id") int genbreak_id,
            @RequestParam("solveBy") int solveBy) {

        Optional<General_breakdown> optionalBreakdown = general_breakdownRepo.findById(genbreak_id);

        Map<String, Object> response = new HashMap<>();

        if (!optionalBreakdown.isPresent()) {
            response.put("status", 404);
            response.put("message", "Breakdown not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        General_breakdown breakdown = optionalBreakdown.get();

        if (breakdown.getStatus() != 1) {
            response.put("status", 400);
            response.put("message", "Breakdown is already closed.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Fetch the solver
        Optional<UserDetails> solver = userDetailsRepo.findById(solveBy);
        if (!solver.isPresent()) {
            response.put("status", 400);
            response.put("message", "Invalid userId for solveBy.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        breakdown.setStatus(0);
        breakdown.setClosedDate(new Date());
        breakdown.setClosedTime(new Date());
        breakdown.setSolveBy(solver.get());

        general_breakdownRepo.save(breakdown);

        response.put("status", 200);
        response.put("message", "Breakdown closed successfully");
        response.put("closedBy", solver.get().getFirstName() + " " + solver.get().getLastName());
        response.put("closedDate", breakdown.getClosedDate());
        response.put("breakdownId", breakdown.getGenbreak_id());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //mobileApp
    @PostMapping("/createGeneralBreakdown")
    public ResponseEntity<ApiResponse<General_breakdown>> createBreakdownWithUser(
            @RequestParam int addedBy,
            @RequestBody General_breakdown general_breakdown) {
        try {
            // 🔹 Fetch UserDetails by ID
            UserDetails user = userDetailsRepo.findById(addedBy)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + addedBy));

            general_breakdown.setAddedBy(user);

            // Generate Breakdown No
            String breakdownNo = generalBreakdownService.getNewBreakDownNo();
            general_breakdown.setBreakdownNo(breakdownNo);

            // Set Dates/Status
            general_breakdown.setOpenDate(new Date());
            general_breakdown.setOpenTime(new Date());
            general_breakdown.setStatus(1);

            // Save
            General_breakdown savedBreakdown = generalBreakdownService.saveGeneralBreakdown(general_breakdown);

            ApiResponse<General_breakdown> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Breakdown created successfully",
                    savedBreakdown);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // 🔹 log the actual error for debugging

            ApiResponse<General_breakdown> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while creating the breakdown: " + e.getMessage(),
                    null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
    @PutMapping("/close/{id}")
    public ResponseEntity<String> closeBreakdown(@PathVariable("id") int id) { // Match path variable name
        Optional<General_breakdown> optionalBreakdown = general_breakdownRepo.findById(id);
        
        if (optionalBreakdown.isPresent()) {
            General_breakdown breakdown = optionalBreakdown.get();

            // Check if status is open (1), then close it
            if (breakdown.getStatus() == 1) {
                breakdown.setStatus(0); // Close the breakdown
                
                // Set closed date and time
                breakdown.setClosedDate(new Date()); 
                breakdown.setClosedTime(new Date());

                // Save the updated breakdown
                general_breakdownRepo.save(breakdown);

                return ResponseEntity.ok("Breakdown closed successfully.");
            } else {
                return ResponseEntity.badRequest().body("Breakdown is already closed.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
    
    


    @PostMapping("/{genbreak_id}/Storeimages")
    public ResponseEntity<Map<String, Object>> uploadImage1(
            @PathVariable("genbreak_id") int genbreakId,
            @RequestParam("images") MultipartFile[] images) {

        Map<String, Object> response = new HashMap<>();
        List<genbreak_Image> savedImages = new ArrayList<>();
        try {
            for (MultipartFile image : images) {
                // Save each image and add it to the list
                genbreak_Image savedImage = genbreakImageService.saveImages(image, genbreakId);
                savedImages.add(savedImage);
            }

            response.put("status", 201);
            response.put("message", "Images uploaded successfully");
          //  response.put("data", savedImages);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
            response.put("status", 500);
            response.put("message", "An error occurred while uploading images");
            response.put("data", null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/{genbreakId}/Fetch_images")
    public ResponseEntity<List<ImageResponseDTO>> getImagesByBreakdownId(@PathVariable("genbreakId") int genbreakId) {
        try {
            List<genbreak_Image> images = genbreakImageRepo.findByGeneral_breakdown_Genbreak_id(genbreakId);
            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No images found
            }

            List<ImageResponseDTO> imageResponses = images.stream()
                .map(image -> new ImageResponseDTO(image.getId(), image.getImageUrl(), image.getGeneral_breakdown()))
                .collect(Collectors.toList());

            return new ResponseEntity<>(imageResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/view_image/{imageId}")
    public ResponseEntity<byte[]> viewImage(@PathVariable("imageId") int imageId) {
        try {
            // Fetch the image by its ID
            genbreak_Image image = genbreakImageRepo.findById(imageId).orElse(null);
            
            // Check if the image exists
            if (image == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Image not found
            }

            // Load the image file as bytes
            File imgFile = new File(image.getImageUrl());
            byte[] imageBytes = Files.readAllBytes(imgFile.toPath());
            
            // Determine the content type (e.g., PNG, JPEG) based on the file extension
            String contentType = Files.probeContentType(imgFile.toPath());
            
            // Return the image in the response
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors
        }
    }

    
    @GetMapping("/download_image/{imageId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("imageId") int imageId) {
        try {
            // Fetch the image by its ID
            genbreak_Image image = genbreakImageRepo.findById(imageId).orElse(null);
            
            // Check if the image exists
            if (image == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Image not found
            }

            // Load the image file as bytes
            File imgFile = new File(image.getImageUrl());
            byte[] imageBytes = Files.readAllBytes(imgFile.toPath());

            // Determine the content type based on the file extension
            String contentType = Files.probeContentType(imgFile.toPath());
            
            // Set headers for download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.attachment().filename(imgFile.getName()).build());
            headers.setContentLength(imageBytes.length);

            // Return the image as a downloadable response
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors
        }
    }

    
    
    @DeleteMapping("/delete_image/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable("imageId") int imageId) {
        try {
            // Fetch the image by its ID
            genbreak_Image image = genbreakImageRepo.findById(imageId).orElse(null);
            
            // Check if the image exists
            if (image == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Image not found
            }

            // Delete the image file from the filesystem
            File imgFile = new File(image.getImageUrl());
            if (imgFile.exists()) {
                imgFile.delete(); // Delete the image file
            }

            // Delete the image record from the database
            genbreakImageRepo.delete(image);

            // Return a success response
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors
        }
    }

    
    //Status wise general breakdown report
    @GetMapping("/status/{status}")
    public ResponseEntity<List<General_breakdown>> getBreakdownsByStatus(@PathVariable("status") int status) {
        try {
            List<General_breakdown> breakdowns = general_breakdownRepo.findByStatus(status);
            return new ResponseEntity<>(breakdowns, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 
    
    
    
    
    
    
    

    // Date-wise general breakdown report
    @GetMapping("/dateWiseReport")
    public ResponseEntity<List<General_breakdown>> getDateWiseReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        try {
            // Set startDate to the beginning of the day (00:00:00)
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);
            startCal.set(Calendar.HOUR_OF_DAY, 0);
            startCal.set(Calendar.MINUTE, 0);
            startCal.set(Calendar.SECOND, 0);
            startCal.set(Calendar.MILLISECOND, 0);
            Timestamp startOfDay = new Timestamp(startCal.getTimeInMillis());

            // Set endDate to the end of the day (23:59:59)
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            endCal.set(Calendar.HOUR_OF_DAY, 23);
            endCal.set(Calendar.MINUTE, 59);
            endCal.set(Calendar.SECOND, 59);
            endCal.set(Calendar.MILLISECOND, 999);
            Timestamp endOfDay = new Timestamp(endCal.getTimeInMillis());

            // Fetch breakdowns between the adjusted start and end dates
            List<General_breakdown> breakdowns = general_breakdownRepo.findAllByOpenDateBetween(startOfDay, endOfDay);
            
           
            if (!breakdowns.isEmpty()) {
                return new ResponseEntity<>(breakdowns, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    // Utility to normalize start/end dates
    private Timestamp startOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Timestamp(c.getTimeInMillis());
    }

    private Timestamp endOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return new Timestamp(c.getTimeInMillis());
    }
    
    // ✅ 1️⃣ PAGINATION ONLY
    @GetMapping("/dateWiseReportWithPage")
    public ResponseEntity<Page<General_breakdown>> getDateWiseReportPage(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam int page,
            @RequestParam int size) {

        PageRequest pageable = PageRequest.of(page-1, size, Sort.by("openDate").descending());
        Page<General_breakdown> result = general_breakdownRepo.findAllByOpenDateBetween(
                startOfDay(startDate), endOfDay(endDate), pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ✅ 2️⃣ SEARCH + PAGINATION
    @GetMapping("/dateWiseReportWithSearch")
    public ResponseEntity<Page<General_breakdown>> searchDateWiseReport(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam (defaultValue = "")String keyword,
            @RequestParam int page,
            @RequestParam int size) {

    	PageRequest pageable = PageRequest.of(page-1, size, Sort.by("openDate").descending());
        Page<General_breakdown> result = general_breakdownRepo.searchByDateRangeAndKeyword(
                startOfDay(startDate), endOfDay(endDate), keyword, pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ✅ 3️⃣ COUNT API
    @GetMapping("/dateWiseReportWithCount")
    public ResponseEntity<Long> countDateWiseBreakdowns(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        long count = general_breakdownRepo.countByOpenDateBetween(startOfDay(startDate), endOfDay(endDate));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    
  
}
