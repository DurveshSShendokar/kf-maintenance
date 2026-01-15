package com.kfMaintenancce.controller;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.Instant;
import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.MachineWeekDTO;
import com.kfMaintenancce.dto.MainCheckPointDTO;
import com.kfMaintenancce.dto.MaintReportResponse;
import com.kfMaintenancce.dto.MaintStatusDTO;
import com.kfMaintenancce.dto.MaintenanceDetailsDTO;
import com.kfMaintenancce.dto.MaintenanceStatusSummaryDTO;
import com.kfMaintenancce.dto.PPMDashboardCountDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.StatusCount;
import com.kfMaintenancce.dto.UserWiseMaintenanceReportDTO;
import com.kfMaintenancce.dto.WeekDataDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.CheckType;
import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintImage;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.ChecklistRepo;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MaintImageRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;
import com.kfMaintenancce.repo.NotificationRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.MachineServices;
import com.kfMaintenancce.service.MaintServices;


@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/maint")

public class MaintController {
	
	

	@Autowired
	private MaintImageRepo maintImageRepository;


	@Autowired
	MaintServices maintServices;
	
	@Autowired
	MaintRepo Maintrepo;
	
	  @Autowired
	    UserDetailsRepo userDetailsRepo; 
	
	@Autowired
	NotificationRepo notificationRepo;
	
	@Autowired
	MachineServices machineServices;
	
	@Autowired
	MaintenenaceCheckPointRepo  maintenenaceCheckPointRepo ; 
	
	@Autowired
	ChecklistRepo checklistRepo;
	
	@Autowired
     MachineOwnerRepo machineOwnerRepo;

	
	@Autowired
	MachineRepo machineRepo;
	
	
	


@GetMapping("/getMachineByLimit")
public @ResponseBody List<Maint> getMachineByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Maint> list = new ArrayList<Maint>();
	try {
		list = Maintrepo.getMaintByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


@RequestMapping(value = "/getMaintByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Maint> getMachineByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Maint> list = new ArrayList<Maint>();
	try {
		
		list = Maintrepo.getMaintByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getMaintCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getMaintCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = Maintrepo.getMaintCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@GetMapping("/getMaintCount")
public @ResponseBody int  getMaintCount() {
    int  count= 0;
    try {
        count= (int) Maintrepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}



	
	@PostMapping("/uploadMaintImages/{maintId}")
	public ResponseEntity<?> uploadMaintImages(
	        @PathVariable int maintId,
	        @RequestParam("files") MultipartFile[] files) {

	    try {
	        Maint maint = Maintrepo.findById(maintId)
	                .orElseThrow(() -> new EntityNotFoundException("MaintFetch_images not found with ID: " + maintId));

	        String uploadDir = "uploads/maint_images/";
	        File dir = new File(uploadDir);
	        if (!dir.exists()) dir.mkdirs();

	        List<Map<String, Object>> uploadedFiles = new ArrayList<>();

	        for (MultipartFile file : files) {
	            if (!file.isEmpty()) {
	                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	                Path filePath = Paths.get(uploadDir, fileName);
	                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	                MaintImage maintImage = new MaintImage();
	                maintImage.setFileName(fileName);
	                maintImage.setFilePath(filePath.toString());
	                maintImage.setFileType(file.getContentType());
	                maintImage.setMaint(maint);

	                MaintImage savedImage = maintImageRepository.save(maintImage);

	                Map<String, Object> fileDetails = new HashMap<>();
	                fileDetails.put("id", savedImage.getId());
	                fileDetails.put("fileName", savedImage.getFileName());
	                fileDetails.put("fileType", savedImage.getFileType());
	                fileDetails.put("filePath", savedImage.getFilePath());

	                uploadedFiles.add(fileDetails);
	            }
	        }

	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Files uploaded successfully.");
	      //  response.put("uploadedFiles", uploadedFiles);

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("status", "error");
	        errorResponse.put("message", "Upload failed: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	
	@GetMapping("/getMaintImages/{maintId}")
	public ResponseEntity<?> getMaintImagesByMaintId(@PathVariable int maintId) {
	    try {
	        List<MaintImage> images = maintImageRepository.findByMaintId(maintId);
	        if (images.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No images found for Maint ID: " + maintId);
	        }
	        return ResponseEntity.ok(images);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error fetching images: " + e.getMessage());
	    }
	}

	 // ✅ API: View Image by maint_image ID
    @GetMapping("/viewMaintImages/{id}")
    public ResponseEntity<?> viewMaintImage(@PathVariable int id) {
        Optional<MaintImage> optionalImage = maintImageRepository.findById(id);

        if (optionalImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MaintImage maintImage = optionalImage.get();
        Path path = Paths.get(maintImage.getFilePath());

        try {
            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + maintImage.getFileName() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().body("Error loading image file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
        }
    }
	
	
	@GetMapping("/downloadMaintImage")
	public ResponseEntity<Resource> downloadMaintImage(
//	        @RequestParam int maintId,
	        @RequestParam int imageId) {
	    try {
	   
	        MaintImage image = maintImageRepository.findById(imageId)
	                .orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " + imageId));

//	        // 2️⃣ Validate maintId
//	        if (image.getMaint() == null || image.getMaint().getMaint_id() != maintId) {
//	            return ResponseEntity.badRequest().build();
//	        }

	        Path filePath = Paths.get(image.getFilePath()).normalize();

	        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
	            return ResponseEntity.notFound().build();
	        }

	        Resource resource = new UrlResource(filePath.toUri());

	        if (!resource.exists() || !resource.isReadable()) {
	            return ResponseEntity.notFound().build();
	        }

	        String contentType = Files.probeContentType(filePath);
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
	                .body(resource);

	    } catch (EntityNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } catch (MalformedURLException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } catch (IOException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	
	
	@GetMapping("/maint_reports")
	public ResponseEntity<?> getMaintReports(
	        @RequestParam(required = false) String month,
	        @RequestParam(required = false) String year,
	      
	        @RequestParam(required = false) String fromDate,
	        @RequestParam(required = false) String toDate
	        )
				
	{

	    try {
	    	List<Maint> maintList;
	    	if (month != null && year != null) {
	    	    maintList = maintServices.generateMonthlyReport(month, year);
	    	} else if (year != null) {
	    	    maintList = maintServices.generateYearlyReport(year);
	    	} else if (fromDate != null && toDate != null) {
	    	    maintList = maintServices.generateDateRangeReport(fromDate, toDate);
	    	} else {
	    	    return ResponseEntity.badRequest().body("Wrong parameters.");
	    	}


	        if (maintList.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.ok(maintList);
	    } catch (DateTimeParseException e) {
	        return ResponseEntity.badRequest().body("Invalid date format. Please use yyyy-MM-dd.");
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
	    }
	}

	
	@GetMapping("/getMaintReported")
	public ResponseEntity<?> getMaintReported(
	        @RequestParam(required = false) String month,
	        @RequestParam(required = false) String year,
	        @RequestParam(required = false) String fromDate,
	        @RequestParam(required = false) String toDate,
	        @RequestParam(required = false) Integer labId,
	        @RequestParam(required = false) String allPpm

	) {
	    try {
	        List<Maint> maintList;

	        // ✅ Case 1: allPpm = true → return all reports
	        if ("true".equalsIgnoreCase(allPpm)) {
	            maintList = maintServices.getAllReports();
	        }

	        // ✅ Case 2: month + year → monthly report
	        else if (month != null && year != null) {
	            maintList = maintServices.generateMonthlyReport(month, year);
	        }
	        // ✅ Case 3: year only → yearly report
	        else if (year != null) {
	            maintList = maintServices.generateYearlyReport(year);
	        }
	        // ✅ Case 4: fromDate + toDate → custom range
	        else if (fromDate != null && toDate != null) {
	            maintList = maintServices.generateDateRangeReport(fromDate, toDate);
	        }
	        // ✅ Case 5: labId alone → all reports filtered by lab
	        else if (labId != null) {
	            maintList = maintServices.getAllReports()
	                    .stream()
	                    .filter(m -> m.getLab() != null && labId.equals(m.getLab().getLab_id()))
	                    .collect(Collectors.toList());
	        }
	        // ❌ Case 6: no matching params
	        else {
	            return ResponseEntity.badRequest().body("Wrong parameters.");
	        }

	        // ✅ Filter again by labId if needed
	        if (labId != null && (allPpm != null || month != null || year != null || fromDate != null || toDate != null)) {
	            maintList = maintList.stream()
	                    .filter(m -> m.getLab() != null && labId.equals(m.getLab().getLab_id()))
	                    .collect(Collectors.toList());
	        }

	        if (maintList.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        // ✅ Group by machine name
	        Map<String, List<Maint>> groupedByMachine = maintList.stream()
	                .filter(m -> m.getMachine() != null && m.getMachine().getMachine_name() != null)
	                .collect(Collectors.groupingBy(m -> m.getMachine().getMachine_name()));

	        // ✅ Prepare summary
	        Map<String, List<StatusCount>> machineWiseStatusSummary = new HashMap<>();
	        for (Map.Entry<String, List<Maint>> entry : groupedByMachine.entrySet()) {
	            String machineName = entry.getKey();
	            List<Maint> machineMaints = entry.getValue();
	            List<StatusCount> statusCounts = generateStatusCounts(machineMaints);
	            machineWiseStatusSummary.put(machineName, statusCounts);
	        }

	        MaintReportResponse response = new MaintReportResponse();
	        response.setMaintList(maintList);
	        response.setMachineWiseStatusSummary(machineWiseStatusSummary);

	        return ResponseEntity.ok(response);

	    } catch (DateTimeParseException e) {
	        return ResponseEntity.badRequest().body("Invalid date format. Please use yyyy-MM-dd.");
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
	    }
	}

	private List<StatusCount> generateStatusCounts(List<Maint> maintList) {
	    LocalDate today = LocalDate.now();

	    long openCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 1 && m.getApprovalBit() == 0)
	            .count();

	    long closedCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 0 && m.getApprovalBit() == 0)
	            .count();

	    long approvedCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 2 && m.getApprovalBit() == 1)
	            .count();

	    long unapprovedCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 1 && m.getApprovalBit() == 2)
	            .count();

	    // New logic
	    long overdueClosedCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 0 && m.getSchedule_date() != null &&
	                         m.getSchedule_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(today))
	            .count();

	    long openClosedCount = maintList.stream()
	            .filter(m -> m.getStatusCode() == 0 && m.getSchedule_date() != null &&
	                         !m.getSchedule_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(today))
	            .count();

	    long totalCount = openCount + closedCount + approvedCount + unapprovedCount;

	    return Arrays.asList(
	            new StatusCount("TotalOpen", (int) openCount),
	            new StatusCount("TotalClosed", (int) closedCount),  
	            new StatusCount("OpenClosed", (int) openClosedCount),
	            new StatusCount("OverdueClosed", (int) overdueClosedCount),
	            new StatusCount("Approved", (int) approvedCount),
	            new StatusCount("Unapproved", (int) unapprovedCount),
	            new StatusCount("Total", (int) totalCount)
	    );
	}


	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Status addMaint(@RequestBody Maint maint) {
	    try {
	        System.out.println("➡️ Incoming Maint Raised By: " + maint.getRaisedBy());
	        String[] arr1 = maint.getArr();
	        System.out.println("➡️ Total Schedule Dates: " + arr1.length);
	        System.out.println("➡️ Full Maint Payload: " + maint.toString());

	        for (int i = 0; i < arr1.length; i++) {

	            // ✅ --- 1. Parse date correctly in IST ---
	            String dateStr = arr1[i].substring(0, 10);
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
	            Date date1 = sdf.parse(dateStr);

	            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
	            c.setTime(date1);

	            // ✅ --- 2. Create new Maint object ---
	            Maint maint1 = new Maint();
	            maint1.setSchedule_date(c.getTime());
	            maint1.setMaint_id(0);
	            maint1.setStatusCode(1);
	            maint1.setAction(null);
	            maint1.setFrequency(maint.getFrequency());
	            maint1.setMachine(maint.getMachine());
	            maint1.setLab(maint.getLab());
	            maint1.setRaisedBy(maint.getRaisedBy());

	            maintServices.addMaint(maint1);

	            System.out.println("✅ Scheduled Date Saved: " + maint1.getSchedule_date());

	            // ✅ --- 3. Notify machine owners ---
	            try {
	                Machine machine = machineRepo.findById(maint1.getMachine().getMachine_id())
	                        .orElseThrow(() -> new RuntimeException("Machine not found"));

	                if (machine != null) {
	                    List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);
	                    for (MachineOwner owner : owners) {

	                        Notification ownerNotification = new Notification();

	                        // Format schedule date for message
	                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	                        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
	                        String formattedScheduleDate = formatter.format(maint1.getSchedule_date());

	                        ownerNotification.setNotificationDept("Maintenance");
	                        ownerNotification.setNotificationFor("OWNER");
	                        ownerNotification.setNotificationForSpecId(owner.getUser().getId());
	                        ownerNotification.setMachine(machine);
	                        ownerNotification.setRaisedTime(new Date());
	                        ownerNotification.setMessage(
	                                "New " + maint1.getFrequency() + " PPM has been scheduled for machine '" +
	                                machine.getMachine_name() + "' (EQID: " + machine.getEqid() + ") at " +
	                                formattedScheduleDate + "."
	                        );
	                        ownerNotification.setTitle("Maintenance Scheduled");
	                        ownerNotification.setViewed(0);

	                        notificationRepo.save(ownerNotification);
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                System.out.println("❌ Owner Notification Error: " + e.getMessage());
	            }
	        }

	        return new Status("✅ Maintenance created and notifications sent!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status("❌ Error: " + e.getMessage());
	    }
	}

///////////////////////////////////////"""""""""""""""""""""""""""""""""""""""""""""""""""
	
	@GetMapping("/UserWise_Report/{userId}")
	     public UserWiseMaintenanceReportDTO generateUserWiseMaintenanceReport(
	        @PathVariable int userId,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

	    // Fetch user details by userId
	    Optional<UserDetails> userOptional = userDetailsRepo.findById(userId);
	    if (!userOptional.isPresent()) {
	        throw new RuntimeException("User not found with ID: " + userId);
	    }

	    UserDetails user = userOptional.get();
	    UserWiseMaintenanceReportDTO userReport = new UserWiseMaintenanceReportDTO(user.getFirstName() + " " + user.getLastName());

	    List<Maint> maintList = null;
	    if (fromDate != null && toDate != null) {
	        maintList = Maintrepo.findByDoneByIdAndDateBetween(userId, fromDate, toDate);
		} 
	    
	    for (Maint maint : maintList) {
	        List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaint(maint);

	        StringBuilder checkListStr = new StringBuilder();
	        for (MaintenenaceCheckPoint checkpoint : checkpoints) {
	            checkListStr.append(checkpoint.getTask())
	                        .append(" - ")
	                        .append(checkpoint.getStatus())
	                        .append(" / ");
	        }


	        if (checkListStr.length() > 0) {
	            checkListStr.setLength(checkListStr.length() - 2);
	        }

	        String frequency = checkpoints.isEmpty() ? "" : checkpoints.get(0).getFrequency();
	        String acceptableRange = checkpoints.isEmpty() ? "" : checkpoints.get(0).getAcceptableRange();
	        UserWiseMaintenanceReportDTO.MaintenanceReport maintenanceReport =
	                new UserWiseMaintenanceReportDTO.MaintenanceReport(
	                        maint,
	                        checkListStr.toString(),
	                        frequency,
	                        acceptableRange
	                );

	        userReport.addMaintenance(maintenanceReport);
	    }

	    return userReport;
	}
	
	
	
	  // ✅ 1️⃣ PAGINATION ONLY
    @GetMapping("/UserWise_Report_Page/{userId}")
    public ResponseEntity<Page<Maint>> getUserWiseMaintenancePage(
            @PathVariable int userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("closedDate").descending());
        Page<Maint> result = Maintrepo.findByDoneByIdAndDateBetween(userId, fromDate, toDate, pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ✅ 2️⃣ SEARCH + PAGINATION
    @GetMapping("/UserWise_Report_Search/{userId}")
    public ResponseEntity<Page<Maint>> searchUserWiseMaintenance(
            @PathVariable int userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam (defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("closedDate").descending());
        Page<Maint> result = Maintrepo.searchByUserIdDateAndKeyword(userId, fromDate, toDate, keyword, pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ✅ 3️⃣ COUNT API
    @GetMapping("/UserWise_Report_Count/{userId}")
    public ResponseEntity<Long> countUserWiseMaintenance(
            @PathVariable int userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        long count = Maintrepo.countByUserIdAndDateBetween(userId, fromDate, toDate);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping(value = "/updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateMaint(@RequestBody Maint maint) {
		try {

			System.out.println("Hello : " + maint);
			System.out.println("Check List : " + maint.getCheckpointMaaint());
			System.out.println(maint.getDone_by());

			int maintId = maint.getMaint_id();

			List<Maint> maintListById = maintServices.getByMaintId(maintId);

			for (Maint m : maintListById) {

				m.setOverall_status(maint.getOverall_status());
				m.setSpare_used(maint.getSpare_used());
				m.setDone_by(maint.getDone_by());
				m.setStatusCode(0);
				m.setClosedDate(new Date());
				maintServices.addMaint(m);
			}
				for(MainCheckPointDTO mainCheckPointDTO:maint.getCheckpointMaaint()) {
					MaintenenaceCheckPoint checkPoint=new MaintenenaceCheckPoint();
					System.out.println("Check "+mainCheckPointDTO.getCheckpoint()+"  "+mainCheckPointDTO.getValue());
					Optional<Checklist> opCheckPoint=checklistRepo.findById(mainCheckPointDTO.getCheckpoint());
					checkPoint.setCheckpoint(opCheckPoint.get());
					checkPoint.setStatus(mainCheckPointDTO.getValue());
					checkPoint.setDone_by(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName() );
					checkPoint.setDone_date(new Date());
					 Maint savedMaint = maintServices.getByMaintId(maint.getMaint_id()).get(0);
					    checkPoint.setMaint(savedMaint);
					System.out.println("FINL "+checkPoint.toString());
					maintenenaceCheckPointRepo.save(checkPoint);
					
				}
			


			return new Status("maintenance done !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	// for mobile app notification
		@PostMapping(value = "/updatePPMOverdueMaint", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Status updateOverdueMaints(@RequestBody Maint maint) {
		    try {
		        System.out.println("Hello : " + maint);
		        System.out.println("Check List : " + maint.getCheckpointMaaint());
		        System.out.println(maint.getDone_by());

		        int maintId = maint.getMaint_id();

		        // Fetch latest managed Maint entity
		        Maint managedMaint = Maintrepo.findById(maintId)
		                .orElseThrow(() -> new NoSuchElementException("Maint not found with ID: " + maintId));

		        managedMaint.setOverall_status(maint.getOverall_status());
		        managedMaint.setTechmark(maint.getTechmark());
		        managedMaint.setDone_by(maint.getDone_by());
		        managedMaint.setStatusCode(0);
		        managedMaint.setClosedDate(new Date());

		        managedMaint = Maintrepo.save(managedMaint);

		        List<Maint> overdueMaintenances = Maintrepo.getOverdueMaintenancesById(maintId);
		        for (Maint overdue : overdueMaintenances) {
		            if (overdue.getMachine().getMachine_id() == managedMaint.getMachine().getMachine_id()
		                    && overdue.getFrequency().equals(managedMaint.getFrequency())
		                    && overdue.getMaint_id() == maintId) {

		                overdue.setStatusCode(0);
		                overdue.setClosedDate(new Date());

		                overdue = Maintrepo.save(overdue);
		            }
		        }

		        for (MainCheckPointDTO mainCheckPointDTO : maint.getCheckpointMaaint()) {
		            MaintenenaceCheckPoint checkPoint = new MaintenenaceCheckPoint();
		            System.out.println("Check " + mainCheckPointDTO.getCheckpoint() + "  " + mainCheckPointDTO.getValue());

		            Optional<Checklist> opCheckPoint = checklistRepo.findById(mainCheckPointDTO.getCheckpoint());

		            if (opCheckPoint.isPresent()) {
		                checkPoint.setCheckpoint(opCheckPoint.get());
		            } else {
		                System.out.println("Checklist not found for ID: " + mainCheckPointDTO.getCheckpoint());
		                continue;
		            }

		            checkPoint.setStatus(mainCheckPointDTO.getValue());
		            checkPoint.setDone_by(managedMaint.getDone_by() != null ?
		                    managedMaint.getDone_by().getFirstName() + " " + managedMaint.getDone_by().getLastName() : "Unknown");
		            checkPoint.setDone_date(new Date());
		            checkPoint.setMaint(managedMaint);

		            System.out.println("FINAL " + checkPoint.toString());

		            maintenenaceCheckPointRepo.save(checkPoint);
		        }

		    //notification
		        try {
		        	  Maint savedMaint = maintServices.getByMaintId(maintId).get(0);
		  	        Machine machine = savedMaint.getMachine();
		            if (machine != null) {
		                List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);

		                for (MachineOwner owner : owners) {
		                    Notification notification = new Notification();
		                    Date now = new Date();
		                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		                    String formattedDate = formatter.format(now);
		                    
		                    UserDetails doneByUser = userDetailsRepo.findById(maint.getDone_by().getId()).orElse(null);
		                    String doneByFullName = (doneByUser != null) 
		                        ? doneByUser.getFirstName() + " " + doneByUser.getLastName() 
		                        : "Unknown User";

		                    notification.setTitle("Overdue PPM Closed");
		                    notification.setMessage("Overdue PPM for machine '" + machine.getMachine_name() +
		                    	    "' (EQID: " + machine.getEqid() + ") is closed at " + formattedDate +
		                    	    " by " + doneByFullName + ".");
		                    notification.setNotificationFor("OWNER");
	                        notification.setNotificationDept("Maintenance");
		                    notification.setNotificationForSpecId(owner.getUser().getId());
		                    notification.setRaisedTime(now); 


		                    notificationRepo.save(notification);
		                }
		            } else {
		                System.out.println("Machine is null for maint ID: " + maintId);
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            System.out.println("❌ Notification Error: " + ex.getMessage());
		        }

		        return new Status("✅ Overdue PPM done and notifications sent!");

		    } catch (Exception e) {
		        e.printStackTrace();
		        return new Status("❌ Error: " + e.getMessage());
		    }
		}


	
	// for mobile app notification
	@PostMapping(value = "/updatePPMStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateMaints(@RequestBody Maint maint) {
	    try {
	        int maintId = maint.getMaint_id();

	        // Update maintenance status
	        List<Maint> maintListById = maintServices.getByMaintId(maintId);
	        for (Maint m : maintListById) {
	            m.setOverall_status(maint.getOverall_status());
	            m.setDone_by(maint.getDone_by());
	            m.setStatusCode(0);
	            m.setClosedDate(new Date());
	            maintServices.addMaint(m);
	        }

	        // Save maintenance checkpoints
	        for (MainCheckPointDTO mainCheckPointDTO : maint.getCheckpointMaaint()) {
	            MaintenenaceCheckPoint checkPoint = new MaintenenaceCheckPoint();
	            Optional<Checklist> opCheckPoint = checklistRepo.findById(mainCheckPointDTO.getCheckpoint());

	            checkPoint.setCheckpoint(opCheckPoint.get());
	            checkPoint.setStatus(mainCheckPointDTO.getValue());
	            checkPoint.setDone_by(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	            checkPoint.setDone_date(new Date());

	            Maint savedMaint = maintServices.getByMaintId(maintId).get(0);
	            checkPoint.setMaint(savedMaint);
	            maintenenaceCheckPointRepo.save(checkPoint);
	        }

	        Maint savedMaint = maintServices.getByMaintId(maintId).get(0);
	        Machine machine = savedMaint.getMachine();

	        if (machine == null) {
	            return new Status("Machine not found. Notification not sent.");
	        }

	        // ✅ Get owners by machine ID
	        List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);

	        if (owners.isEmpty()) {
	            System.out.println("No owners found for machine ID: " + machine.getMachine_id());
	        }

	        // ✅ Save notifications
	        for (MachineOwner owner : owners) {
	            Notification notification = new Notification();
	            Date now = new Date();
	            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
	            String formattedDate = formatter.format(now);
	            
	            UserDetails doneByUser = userDetailsRepo.findById(maint.getDone_by().getId()).orElse(null);
	            String doneByFullName = (doneByUser != null) 
	                ? doneByUser.getFirstName() + " " + doneByUser.getLastName() 
	                : "Unknown User";


	            notification.setTitle(" PPM Closed");
	            
	            notification.setMessage("PPM for machine '" + machine.getMachine_name() +
	            	    "' (EQID: " + machine.getEqid() + ") is closed at " + formattedDate +
	            	    " by " + doneByFullName + ".");
	            
	            notification.setNotificationFor("OWNER");
                notification.setNotificationDept("Maintenance");
	            notification.setNotificationForSpecId(owner.getUser().getId());
	            notification.setRaisedTime(now); 

	            notificationRepo.save(notification);
	            System.out.println("Notification saved for owner ID: " + owner.getUser().getId());
	        }

	        return new Status("PPM done and notifications sent to owners!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status("Error: " + e.getMessage());
	    }
	}
	
	//for mobile app notification	
	@PostMapping("/unapprovePPMMaintenances")
	public ResponseEntity<String> unapproveMaintenanceses(@RequestParam int maintId, 
	                                                    @RequestParam String executiveRemark,
	                                                    @RequestParam int approvalById) {
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);

	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	        if (maint.getStatusCode() == 0) {
	            maint.setStatusCode(1); 
	            maint.setApprovalBit(2); 
	            maint.setExecutiveRemark(executiveRemark);
	            maint.setUnApprovalDate(new Date());
	            UserDetails approvalBy = userDetailsRepo.findById(approvalById)
	                    .orElseThrow(() -> new RuntimeException("Approver not found"));
	            maint.setApprovalBy(approvalBy);

	            Maintrepo.save(maint);
	            maintenenaceCheckPointRepo.markPreviousCheckPoints(maintId);
	            // Send notification to all machine owners
	            try {
	            	  Maint savedMaint = maintServices.getByMaintId(maintId).get(0);
	      	        Machine machine = savedMaint.getMachine();

	                if (machine != null) {
	                    List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);
	                    String executiveName = approvalBy.getFirstName() + " " + approvalBy.getLastName();

	                    for (MachineOwner owner : owners) {
	                        Notification notification = new Notification();
	                        Date now = new Date();
	        	            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
	        	            String formattedDate = formatter.format(now);
	                        notification.setTitle("PPM Unapproved");
	                        notification.setMessage("PPM for machine '" + machine.getMachine_name() +
	                            "' (EQID: " + machine.getEqid() + ") has been unapproved by " + executiveName + " "+"on"+formattedDate+".");
	                        notification.setNotificationFor("OWNER");
	                        notification.setNotificationDept("Maintenance");
	                        notification.setNotificationForSpecId(owner.getUser().getId());
	                        notification.setRaisedTime(now); 
	                        notificationRepo.save(notification);
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                System.out.println("❌ Notification error: " + e.getMessage());
	            }

	            return ResponseEntity.ok(" PPM record has been unapproved and notification sent successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("Only approved PPM records can be unapproved.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body(" PPM record not found.");
	    }
	}
	
	//mobile app notification
	@PostMapping("/updateClosedPPMApprovals")
	public ResponseEntity<String> updateClosedApprovalses(@RequestParam int maintId, 
	                                                    @RequestParam int approvalById,
	                                                    @RequestParam String executiveRemark) {
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);
	    
	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	        if (maint.getStatusCode() == 0) {
	            maint.setStatusCode(2); // Closed
	            maint.setApprovalBit(1); // Approved
	            maint.setExecutiveRemark(executiveRemark); 
	            maint.setClosedApprovalDate(new Date()); 
	            
	            // Get approver
	            UserDetails approvalBy = userDetailsRepo.findById(approvalById)
	                                         .orElseThrow(() -> new RuntimeException("Approver not found"));
	            maint.setApprovalBy(approvalBy);

	            Maintrepo.save(maint);

	            // Send notification to machine owners
	            try {
	            	  Maint savedMaint = maintServices.getByMaintId(maintId).get(0);
	      	        Machine machine = savedMaint.getMachine();

	                if (machine != null) {
	                    List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);
	                    String executiveName = approvalBy.getFirstName() + " " + approvalBy.getLastName();

	                    for (MachineOwner owner : owners) {
	                        Notification notification = new Notification();
	                        Date now = new Date();
	        	            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
	        	            String formattedDate = formatter.format(now);
	                        notification.setTitle("PPM Approved");
	                        notification.setMessage("PPM for machine '" + machine.getMachine_name() +
	                            "' (EQID: " + machine.getEqid() + ") has been Approved by " + executiveName + " "+ "on"+formattedDate+".");
	                        notification.setNotificationFor("ADMIN");
	                        notification.setNotificationDept("Maintenance");
	                        notification.setNotificationForSpecId(owner.getUser().getId());
	                        notification.setRaisedTime(now);

	                        notificationRepo.save(notification);
	                    }
	                } else {
	                    System.out.println("❌ No machine found for maintenance ID: " + maintId);
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	                System.out.println("❌ Notification error: " + e.getMessage());
	            }

	            return ResponseEntity.ok(" PPM record approved and Notification Sent successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("This record is not in pending status.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body(" PPM record not found.");
	    }
	}



	
	@PostMapping(value = "/updateOverdueMaint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateOverdueMaint(@RequestBody Maint maint) {
	    try {
	        System.out.println("Hello : " + maint);
	        System.out.println("Check List : " + maint.getCheckpointMaaint());
	        System.out.println(maint.getDone_by());

	        int maintId = maint.getMaint_id();

	        // Fetch latest managed Maint entity
	        Maint managedMaint = Maintrepo.findById(maintId)
	                                      .orElseThrow(() -> new NoSuchElementException("Maint not found with ID: " + maintId));

	   
	        managedMaint.setOverall_status(maint.getOverall_status());
	        managedMaint.setSpare_used(maint.getSpare_used());
	        managedMaint.setTechmark(maint.getTechmark());
	        managedMaint.setDone_by(maint.getDone_by());
	        managedMaint.setStatusCode(0);
	        managedMaint.setClosedDate(new Date());

	     
	        managedMaint = Maintrepo.save(managedMaint);

	       
	        List<Maint> overdueMaintenances = Maintrepo.getOverdueMaintenancesById(maintId);
	        for (Maint overdue : overdueMaintenances) {
	            if (overdue.getMachine().getMachine_id() == managedMaint.getMachine().getMachine_id() &&
	                overdue.getFrequency().equals(managedMaint.getFrequency()) &&
	                overdue.getMaint_id() == maintId) {

	                overdue.setStatusCode(0);
	                overdue.setClosedDate(new Date());

	             
	                overdue = Maintrepo.save(overdue);
	            }
	        }

	       
	        for (MainCheckPointDTO mainCheckPointDTO : maint.getCheckpointMaaint()) {
	            MaintenenaceCheckPoint checkPoint = new MaintenenaceCheckPoint();
	            System.out.println("Check " + mainCheckPointDTO.getCheckpoint() + "  " + mainCheckPointDTO.getValue());

	            Optional<Checklist> opCheckPoint = checklistRepo.findById(mainCheckPointDTO.getCheckpoint());

	            if (opCheckPoint.isPresent()) {
	                checkPoint.setCheckpoint(opCheckPoint.get());
	            } else {
	                System.out.println(" Checklist not found for ID: " + mainCheckPointDTO.getCheckpoint());
	                continue; 
	            }

	            checkPoint.setStatus(mainCheckPointDTO.getValue());
	            checkPoint.setDone_by(managedMaint.getDone_by() != null ? 
	                managedMaint.getDone_by().getFirstName() + " " + managedMaint.getDone_by().getLastName() : "Unknown");
	            checkPoint.setDone_date(new Date());

	        
	            checkPoint.setMaint(managedMaint);

	            System.out.println("FINAL " + checkPoint.toString());

	          
	            maintenenaceCheckPointRepo.save(checkPoint);
	        }

	        return new Status(" Maintenance done!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status("❌ Error: " + e.getMessage());
	    }
	}
	
	
	
	@PostMapping("/unapproveMaintenance")
	public ResponseEntity<String> unapproveMaintenance(@RequestParam int maintId, 
	                                                   @RequestParam String executiveRemark) {
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);
	    
	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	     
	        if (maint.getStatusCode() == 0) { 
	            maint.setStatusCode(1); 
	            maint.setApprovalBit(2); 
	            maint.setExecutiveRemark(executiveRemark); 
	            maint.setUnApprovalDate(new Date());
	            
	            Maintrepo.save(maint);
	            return ResponseEntity.ok("Maintenance record has been unapproved successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("Only approved maintenance records can be unapproved.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Maintenance record not found.");
	    }
	}
	

	
	@PostMapping("/updateClosedApproval")
	public ResponseEntity<String> updateClosedApproval(@RequestParam int maintId, 
	                                                   @RequestParam String executiveRemark
	                                                ) {
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);
	    
	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	       
	        if (maint.getStatusCode() == 0) {
	            maint.setStatusCode(2); 
	            maint.setApprovalBit(1);
	            maint.setExecutiveRemark(executiveRemark); 
	            maint.setClosedApprovalDate(new Date()); 


	            Maintrepo.save(maint);
	            return ResponseEntity.ok("Maintenance record closed and approved successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("This record is not in pending status.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Maintenance record not found.");
	    }
	}
	
	// edit the value of checkpoints
	@PostMapping("/updateChecklistStatus")
	public ResponseEntity<?> updateChecklistStatus(@RequestParam int maintCheckPointId, 
	                                                    @RequestParam String status) {
	    Optional<MaintenenaceCheckPoint> optionalChecklist = maintenenaceCheckPointRepo.findById(maintCheckPointId);

	    if (optionalChecklist.isPresent()) {
	        MaintenenaceCheckPoint checklist = optionalChecklist.get();
	        checklist.setStatus(status); 
	        MaintenenaceCheckPoint updatedChecklist = maintenenaceCheckPointRepo.save(checklist); 
	        return ResponseEntity.ok(updatedChecklist);
	    } else {
	        return ResponseEntity.badRequest().body("Checklist not found.");
	    }
	}

	
	//mobile dashboard
	
	@PostMapping("/updateClosedApprovals")
	public ResponseEntity<String> updateClosedApprovals(@RequestParam int maintId, 
	                                                   @RequestParam int approvalById,
	                                                   @RequestParam String executiveRemark) {
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);
	    
	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	       
	        if (maint.getStatusCode() == 0) {
	            maint.setStatusCode(2); 
	            maint.setApprovalBit(1);
	            maint.setExecutiveRemark(executiveRemark); 
	            maint.setClosedApprovalDate(new Date()); 
	            
	            UserDetails approvalBy = userDetailsRepo.findById(approvalById)
	                                             .orElseThrow(() -> new RuntimeException("Approver not found"));
	            maint.setApprovalBy(approvalBy);

	            Maintrepo.save(maint);
	            return ResponseEntity.ok("Maintenance record closed and approved successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("This record is not in pending status.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Maintenance record not found.");
	    }
	}
	
	
	@PostMapping("/unapproveMaintenances")
	public ResponseEntity<String> unapproveMaintenances(@RequestParam int maintId, 
	                                                   @RequestParam String executiveRemark,
	                                                   @RequestParam int approvalById){
	    Optional<Maint> optionalMaint = Maintrepo.findById(maintId);
	    
	    if (optionalMaint.isPresent()) {
	        Maint maint = optionalMaint.get();

	     
	        if (maint.getStatusCode() == 0) { 
	            maint.setStatusCode(1); 
	            maint.setApprovalBit(2); 
	            maint.setExecutiveRemark(executiveRemark); 
	            maint.setUnApprovalDate(new Date());
	            
	            UserDetails approvalBy = userDetailsRepo.findById(approvalById)
                        .orElseThrow(() -> new RuntimeException("Approver not found"));
	            		maint.setApprovalBy(approvalBy);
	            
	            Maintrepo.save(maint);
	            maintenenaceCheckPointRepo.markPreviousCheckPoints(maintId);
	            return ResponseEntity.ok("Maintenance record has been unapproved successfully.");
	        } else {
	            return ResponseEntity.badRequest().body("Only approved maintenance records can be unapproved.");
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Maintenance record not found.");
	    }
	}
	
	@GetMapping("/getChecklistByMaintId/{maintId}")
	public ResponseEntity<Map<String, Object>> getPendingClosedApprovalsByMaintId(@PathVariable("maintId") int maintId) {
	    Optional<Maint> maintOptional = Maintrepo.findById(maintId);

	    if (!maintOptional.isPresent()) {  
	        return ResponseEntity.notFound().build();
	    }

	    Maint maint = maintOptional.get();
	    List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	    maint.setCheckpointlist(checkpoints);

	    Map<String, Object> response = new HashMap<>();
	    response.put("maint_id", maint.getMaint_id());
	    response.put("checklist", checkpoints);

	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("/getChecklistByMaintIds/{maintId}")
	public ResponseEntity<Map<String, Object>> getPendingClosedApprovalsByMaintIds(@PathVariable("maintId") int maintId) {
	    Optional<Maint> maintOptional = Maintrepo.findById(maintId);

	    if (!maintOptional.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Maint maint = maintOptional.get();
	    List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	    maint.setCheckpointlist(checkpoints);

	    boolean hasIssue = false;

	    for (MaintenenaceCheckPoint checkPoint : checkpoints) {
	        Checklist checklist = checkPoint.getCheckpoint();
	        CheckType checkType = checklist.getCheckType();
	        String statusStr = checkPoint.getStatus();

	        if (checkType != null && statusStr != null && !statusStr.trim().isEmpty()) {
	            try {
	                double statusValue = Double.parseDouble(statusStr.trim());
	                Double lower = checklist.getLower_range();
	                Double upper = checklist.getUpper_range();

	                switch (checkType) {
	                    case RANGE:
	                        if (lower != null && upper != null && (statusValue < lower || statusValue > upper)) {
	                            hasIssue = true;
	                        }
	                        break;
	                    case MIN_VALUE:
	                        if (lower != null && statusValue < lower) {
	                            hasIssue = true;
	                        }
	                        break;
	                    case MAX_VALUE:
	                        if (upper != null && statusValue > upper) {
	                            hasIssue = true;
	                        }
	                        break;
	                    case ANY:
	                        // Always valid
	                        break;
	                }
	            } catch (NumberFormatException e) {
	                if (checkType != CheckType.ANY) {
	                    hasIssue = true;
	                }
	            }
	        }
	    }

	    if (hasIssue) {
	        maint.setCheckIssueBit(1);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("maint_id", maint.getMaint_id());
	    response.put("checkIssueBit", maint.getCheckIssueBit());
	    response.put("checklist", checkpoints);

	    return ResponseEntity.ok(response);
	}
	
	
	 // ✅ 1. Pagination only
    @GetMapping("/getChecklistByMaintIdsWithPagination/{maintId}")
    public ResponseEntity<Map<String, Object>> getChecklistWithPagination(
            @PathVariable int maintId,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        Page<MaintenenaceCheckPoint> checkpointPage = maintenenaceCheckPointRepo.findByMaintIdWithPagination(maintId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("checkpoints", checkpointPage.getContent());
        response.put("currentPage", checkpointPage.getNumber());
        response.put("totalItems", checkpointPage.getTotalElements());
        response.put("totalPages", checkpointPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // ✅ 2. Pagination + Search
    @GetMapping("/getChecklistByMaintIdsWithSearchPage/{maintId}")
    public ResponseEntity<Map<String, Object>> searchChecklistWithPagination(
            @PathVariable int maintId,
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        Page<MaintenenaceCheckPoint> checkpointPage = maintenenaceCheckPointRepo.searchCheckpointsByMaintId(maintId, keyword, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("checkpoints", checkpointPage.getContent());
        response.put("currentPage", checkpointPage.getNumber());
        response.put("totalItems", checkpointPage.getTotalElements());
        response.put("totalPages", checkpointPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // ✅ 3. Count API
    @GetMapping("/getChecklistByMaintIdsWithCount/{maintId}")
    public ResponseEntity<Map<String, Object>> countChecklistByMaintId(@PathVariable int maintId) {
        long count = maintenenaceCheckPointRepo.countByMaintId(maintId);

        Map<String, Object> response = new HashMap<>();
        response.put("maintId", maintId);
        response.put("totalChecklists", count);

        return ResponseEntity.ok(response);
    }
    
    ///////////////////////////////////////


	
	@GetMapping("/pendingClosedApprovals")
	public ResponseEntity<Map<String, Object>> getPendingClosedApprovals() {
	    List<Maint> pendingApprovals = Maintrepo.findByStatusCode(0);

	    if (pendingApprovals.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    for (Maint maint : pendingApprovals) {
	        List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	        maint.setCheckpointlist(checkpoints);
	    }

	 
	    Map<String, Object> response = new HashMap<>();
	    response.put("count", pendingApprovals.size());
	    response.put("datas", pendingApprovals);

	    return ResponseEntity.ok(response);
	}
	
	 // 🔁 Common helper: enrich each maintenance record with checkpoints
    private List<Maint> enrichWithCheckpoints(List<Maint> maintList) {
        for (Maint maint : maintList) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }
        return maintList;
    }

    // 1️⃣ Pagination only
    @GetMapping(value = "/pendingClosedApprovalsWithPaginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPaginatedPendingClosedApprovals(
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.findPendingClosedApprovals(pageable);

        List<Maint> enriched = enrichWithCheckpoints(maintPage.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("records", enriched);
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Pagination + Searching
    @GetMapping(value = "/pendingClosedApprovalsWithSearchPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> searchPendingClosedApprovals(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.searchPendingClosedApprovals(keyword, pageable);

        List<Maint> enriched = enrichWithCheckpoints(maintPage.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("records", enriched);
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Count only
    @GetMapping(value = "/pendingClosedApprovalsWithCount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPendingClosedApprovalsCount() {
        long count = Maintrepo.countPendingClosedApprovals();
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
	
	
///

	    @GetMapping("/getClosedApprovals")
	    public ResponseEntity<Map<String, Object>> getClosedApprovals() {
	        List<Maint> closedApprovals = Maintrepo.findClosedApprovals();

	        if (closedApprovals.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        for (Maint maint : closedApprovals) {
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints);
	        }

	      
	        Map<String, Object> response = new HashMap<>();
	        response.put("count", closedApprovals.size());
	        response.put("datas", closedApprovals);

	        return ResponseEntity.ok(response);
	    }
	    @GetMapping("/getClosedApprovalss/{machineId}")
	    public ResponseEntity<Map<String, Object>> getClosedApprovalsByMachine(@PathVariable int machineId) {
	        List<Maint> closedApprovals = Maintrepo.findClosedApprovalsByMachineId(machineId);

	        if (closedApprovals.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        for (Maint maint : closedApprovals) {
	            List<MaintenenaceCheckPoint> checkpoints =
	                    maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints);
	        }

	        Map<String, Object> response = new HashMap<>();
	        response.put("count", closedApprovals.size());
	        response.put("datas", closedApprovals);

	        return ResponseEntity.ok(response);
	    }

	    
	    @GetMapping("/getClosedApproved/{maintId}")
	    public ResponseEntity<Map<String, Object>> getClosedApprovalById(@PathVariable("maintId") int maintId) {
	    
	        Optional<Maint> closedApproval = Maintrepo.findById(maintId);
	        
	        if (!closedApproval.isPresent()) {
	            return ResponseEntity.noContent().build();
	        }

	        Maint maint = closedApproval.get();

	       
	        List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maintId);
	        maint.setCheckpointlist(checkpoints);

	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("count", 1);
	        response.put("datas", maint);

	        return ResponseEntity.ok(response);
	    }

	    
	    @GetMapping("/exportClosedApprovals")
	    public ResponseEntity<Resource> exportClosedApprovals() throws IOException {
	      
	        List<Maint> maintList = Maintrepo.findByStatusCode(2);

	       
	        for (Maint maint : maintList) {
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints); 
	        }

	      
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Closed Approvals");

	      
	        String[] columns = { 
	            "Sr No", "Machine", "Lab Name", "Frequency", "Overall Status", 
	            "Raised By", "Done By", "Schedule Date", "Closed Date", "Approval Date",
	            "Checklist", "Acceptable Range", "Value"
	        };

	   
	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < columns.length; i++) {
	            headerRow.createCell(i).setCellValue(columns[i]);
	        }

	        int rowIdx = 1;
	        int serialNo = 1;

	        for (Maint maint : maintList) {
	            String machineDetails = maint.getMachine().getMachine_name() + " - " + maint.getMachine().getEqid();
	            
	            if (maint.getCheckpointlist() != null && !maint.getCheckpointlist().isEmpty()) {
	                for (MaintenenaceCheckPoint cp : maint.getCheckpointlist()) {
	                    Row row = sheet.createRow(rowIdx++);
	                    row.createCell(0).setCellValue(serialNo++);
	                    row.createCell(1).setCellValue(machineDetails);
	                    row.createCell(2).setCellValue(maint.getLab().getLabName());
	                    row.createCell(3).setCellValue(maint.getFrequency());
	                    row.createCell(4).setCellValue(maint.getOverall_status());
	                    row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	                    row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	                    row.createCell(7).setCellValue(maint.getSchedule_date().toString());
	                    row.createCell(8).setCellValue(maint.getClosedDate().toString());
	                    row.createCell(9).setCellValue(maint.getClosedApprovalDate().toString());
	                    row.createCell(10).setCellValue(cp.getTask());
	                    row.createCell(11).setCellValue(cp.getAcceptableRange());
	                    row.createCell(12).setCellValue(cp.getStatus());
	                }
	            } else {
	              
	                Row row = sheet.createRow(rowIdx++);
	                row.createCell(0).setCellValue(serialNo++);
	                row.createCell(1).setCellValue(machineDetails);
	                row.createCell(2).setCellValue(maint.getLab().getLabName());
	                row.createCell(3).setCellValue(maint.getFrequency());
	                row.createCell(4).setCellValue(maint.getOverall_status());
	                row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	                row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	                row.createCell(7).setCellValue(maint.getSchedule_date().toString());
	                row.createCell(8).setCellValue(maint.getClosedDate().toString());
	                row.createCell(9).setCellValue(maint.getClosedApprovalDate().toString());
	                row.createCell(10).setCellValue("No Checkpoints");
	                row.createCell(11).setCellValue("");
	                row.createCell(12).setCellValue("");
	            }
	        }

	        workbook.write(out);
	        workbook.close();

	        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ClosedApprovals.xlsx")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	    }
		    
	    
	    

	    
	    @GetMapping("/exportunApprovals")
	    public ResponseEntity<Resource> exportunApprovals() throws IOException {
	      
	        List<Maint> maintList = Maintrepo.findUnApprovals();

	       
	        for (Maint maint : maintList) {
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints); 
	        }

	      
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Closed Approvals");

	      
	        String[] columns = { 
	            "Sr No", "Machine", "Lab Name", "Frequency", "Overall Status", 
	            "Raised By", "Done By", "Schedule Date", "Closed Date", "UnApproval date",
	            "Checklist", "Acceptable Range", "Value"
	        };

	   
	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < columns.length; i++) {
	            headerRow.createCell(i).setCellValue(columns[i]);
	        }

	        int rowIdx = 1;
	        int serialNo = 1;

	        for (Maint maint : maintList) {
	            String machineDetails = maint.getMachine().getMachine_name() + " - " + maint.getMachine().getEqid();
	            
	            if (maint.getCheckpointlist() != null && !maint.getCheckpointlist().isEmpty()) {
	                for (MaintenenaceCheckPoint cp : maint.getCheckpointlist()) {
	                    Row row = sheet.createRow(rowIdx++);
	                    row.createCell(0).setCellValue(serialNo++);
	                    row.createCell(1).setCellValue(machineDetails);
	                    row.createCell(2).setCellValue(maint.getLab().getLabName());
	                    row.createCell(3).setCellValue(maint.getFrequency());
	                    row.createCell(4).setCellValue(maint.getOverall_status());
	                    row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	                    row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	                    row.createCell(7).setCellValue(maint.getSchedule_date().toString());
	                    row.createCell(8).setCellValue(maint.getClosedDate().toString());
	                    row.createCell(9).setCellValue(maint.getUnApprovalDate().toString());
	                    row.createCell(10).setCellValue(cp.getTask());
	                    row.createCell(11).setCellValue(cp.getAcceptableRange());
	                    row.createCell(12).setCellValue(cp.getStatus());
	                }
	            } else {
	              
	                Row row = sheet.createRow(rowIdx++);
	                row.createCell(0).setCellValue(serialNo++);
	                row.createCell(1).setCellValue(machineDetails);
	                row.createCell(2).setCellValue(maint.getLab().getLabName());
	                row.createCell(3).setCellValue(maint.getFrequency());
	                row.createCell(4).setCellValue(maint.getOverall_status());
	                row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	                row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	                row.createCell(7).setCellValue(maint.getSchedule_date().toString());
	                row.createCell(8).setCellValue(maint.getClosedDate().toString());
	                row.createCell(9).setCellValue(maint.getUnApprovalDate().toString());
	                row.createCell(10).setCellValue("No Checkpoints");
	                row.createCell(11).setCellValue("");
	                row.createCell(12).setCellValue("");
	            }
	        }

	        workbook.write(out);
	        workbook.close();

	        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=UnApprovals.xlsx")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	    }

	    
	    @GetMapping("/getUnApprovals")
	    public ResponseEntity<Map<String, Object>> getUnApprovals() {
	        List<Maint> unApprovals = Maintrepo.findUnApprovals();

	        if (unApprovals.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        for (Maint maint : unApprovals) {
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints);
	        }

	      
	        Map<String, Object> response = new HashMap<>();
	        response.put("count", unApprovals.size());
	        response.put("datas", unApprovals);

	        return ResponseEntity.ok(response);
	    }
	    @GetMapping("/getUnApprovalss/{machineId}")
	    public ResponseEntity<Map<String, Object>> getUnApprovalsByMachine(@PathVariable int machineId) {
	        List<Maint> unApprovals = Maintrepo.findUnApprovalsByMachineId(machineId);

	        if (unApprovals.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        for (Maint maint : unApprovals) {
	            List<MaintenenaceCheckPoint> checkpoints =
	                    maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints);
	        }

	        Map<String, Object> response = new HashMap<>();
	        response.put("count", unApprovals.size());
	        response.put("datas", unApprovals);

	        return ResponseEntity.ok(response);
	    }

	    
	 
	    
	  


	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getMaints() {
		List<Maint> maintList = null;
		try {

			maintList = maintServices.getMaintList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}

	@GetMapping(value = "/getDoneMaintenanceByMachine/{machineId}", produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody List<Maint> getDoneMaintenanceByMachine(@PathVariable("machineId") int machineId) {
		List<Maint> maintList = null;
		try {

			System.out.println("MAINT ID " + machineId);
			maintList = maintServices.getDoneMaintenanceByMachine(machineId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}

	@PostMapping(value = "/getRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getReport(@RequestBody Maint maint) {
		List<Maint> maintList = null;
		try {
			maintList = maintServices.getReport(maint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}

	@GetMapping(value = "/delete/{maint_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody

			Status deleteCategory(@PathVariable("maint_id") int maint_id) {
		try {
			maintServices.deleteMaint(maint_id);
			return new Status("Shift Deleted Successfully !");
		} catch (Exception e) {
			return new Status(e.toString());
		}
	}



	
	 @PostMapping(value = "/maintenanceDone", consumes = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody Status maintenanceDone(@RequestBody Maint maint) {
	        try {
	            System.out.println("Hii, I am Doing Maintenance: " + maint.toString());

	          
	            maint.setStatusCode(0);
	            maint.setClosedDate(new Date());

	           
	            List<Maint> optional = maintServices.getByMaintId(maint.getMaint_id());
	            if (optional.isEmpty()) {
	                return new Status("Error: Maintenance record not found!");
	            }
	            
	            Maint existingMaint = optional.get(0);
	            maint.setSchedule_date(existingMaint.getSchedule_date());
	            maint.setRaisedBy(existingMaint.getRaisedBy());
	            maint.setLab(existingMaint.getLab());

	          
	            Maint savedMaint = maintServices.saveMaint(maint);

	            
	            if (maint.getCheckpointlist() == null || maint.getCheckpointlist().isEmpty()) {
	                System.out.println("No checklist provided for maintenance.");
	                return new Status("Maintenance updated, but no checklist provided!");
	            }

	           
	            List<MaintenenaceCheckPoint> checkPointsToSave = new ArrayList<>();
	            for (MaintenenaceCheckPoint maintenenaceCheckPoint : maint.getCheckpointlist()) {
	                System.out.println("Saving Checkpoint: " + maintenenaceCheckPoint.toString());

	                MaintenenaceCheckPoint checkPoint = new MaintenenaceCheckPoint();
	                checkPoint.setMaint(savedMaint);
	                checkPoint.setCheckpoint(maintenenaceCheckPoint.getCheckpoint());
	                checkPoint.setStatus(maintenenaceCheckPoint.getStatus());
	                checkPoint.setDone_by(maintenenaceCheckPoint.getDone_by());
	                checkPoint.setDone_date(new Date());

	                checkPointsToSave.add(checkPoint);
	            }

	           
	            maintServices.saveAllMaintenenaceCheckPoints(checkPointsToSave);

	            return new Status("Maintenance and checklist saved successfully!");

	        } catch (Exception e) {
	            e.printStackTrace();
	            return new Status("Error: " + e.getMessage());
	        }
	    }
	
	@GetMapping(value = "/getCheckPointByMaintenacce", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Checklist> getCheckPointByMaintenacce(@RequestParam("maintId") int maintId) {
		 List<Checklist> checkList = new ArrayList<>();
		try {
			Maint maint = maintServices.getMaintById(maintId);
			if (maint != null) {
				String freq="";
				if(maint.getFrequency().equalsIgnoreCase("weekly")) {
					freq="W";
				}
				if(maint.getFrequency().equalsIgnoreCase("monthly")) {
					freq="M";		
				}
				if(maint.getFrequency().equalsIgnoreCase("quarterly")) {
					freq="Q";
				}
				if(maint.getFrequency().equalsIgnoreCase("halfyearly")) {
					freq="H/Y";
				}
				if(maint.getFrequency().equalsIgnoreCase("yearly")) {
					freq="Y";
				}
				
				checkList = maintServices.getMaintenencaceCheckPointByMaintenace(maint.getMachine().getMachine_id(),
						freq);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkList;
	}
	



	@GetMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getOpenMaintenances() {
		try {
			return maintServices.getOpenMaintenances();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "/closed", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getClosedMaintenances() {
		try {
			return maintServices.getClosedMaintenances();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getAllMaintenances() {
		try {
			return maintServices.getAllMaintenances();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "/todayMaintenanceRecords", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Maint> getTodayMaintenanceRecords() {
		try {
			LocalDate today = LocalDate.now();
			Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
			return maintServices.getTodayMaintenanceRecords();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//---------------------PPM cards-----------------//
	@GetMapping(value = "/total_openmaintenance")
	public List<Maint> getOpenMaintenanceRecord() {
	    return Maintrepo.getOpeneMaintenancesByDate(new Date()); 
	}
	
	   // 1️⃣ Pagination only
    @GetMapping("/total_openmaintenanceWithPaginated")
    public ResponseEntity<Map<String, Object>> getPaginatedOpenMaintenance(
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.findOpenMaintenances(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("records", maintPage.getContent());
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Pagination + Searching
    @GetMapping("/total_openmaintenanceWithSearchPage")
    public ResponseEntity<Map<String, Object>> searchOpenMaintenance(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.searchOpenMaintenances(keyword, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("records", maintPage.getContent());
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Count only
    @GetMapping("/total_openmaintenanceWithCount")
    public ResponseEntity<Map<String, Object>> getOpenMaintenanceCount() {
        long count = Maintrepo.countOpenMaintenances();
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    
    
    ///////////////////
	
	@GetMapping(value = "/total_open_maintenance")
	public List<Maint> getOpenMaintenanceRecords(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    return Maintrepo.findOpenMaintenancesByDateRange(fromDate, toDate);
	}

    
    ////////////////////////////////////////////
	
	@GetMapping(value = "/date/total_open_maintenanceByLab")
	public List<Maint> getOpenMaintenanceRecords(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	        @RequestParam("labName") String labName) {
	    return Maintrepo.findOpenMaintenancesByDateRangeAndLabName(fromDate, toDate, labName);
	}

	
	 @GetMapping("/export/total_open_maintenance")
	    public void exportMaintenanceToExcel(
	            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	            HttpServletResponse response) throws IOException {

	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=Maintenance_Data.xlsx");

	        List<Maint> maintenances = (fromDate != null && toDate != null)
	                ? Maintrepo.findOpenMaintenancesByDateRange(fromDate, toDate)
	                : Maintrepo.getOpeneMaintenancesByDate(new Date()); 

	      
	        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Maintenance Records");

	            // Header Row
	            Row headerRow = sheet.createRow(0);
	            String[] headers = {"Sr.No", "Machine Name", "Machine ID", "Location", "Frequency", "Raised By", "Date"};
	            for (int i = 0; i < headers.length; i++) {
	                Cell cell = headerRow.createCell(i);
	                cell.setCellValue(headers[i]);
	                CellStyle headerStyle = workbook.createCellStyle();
	                Font font = workbook.createFont();
	                font.setBold(true);
	                headerStyle.setFont(font);
	                cell.setCellStyle(headerStyle);
	            }

	            
	            int rowIndex = 1;
	            for (Maint maint : maintenances) {
	                Row row = sheet.createRow(rowIndex++);
	                row.createCell(0).setCellValue(rowIndex - 1);
	                row.createCell(1).setCellValue(maint.getMachine().getMachine_name());
	                row.createCell(2).setCellValue(maint.getMachine().getEqid());
	                row.createCell(3).setCellValue(maint.getMachine().getLocation());
	                row.createCell(4).setCellValue(maint.getFrequency());
	                row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + "  " + maint.getRaisedBy().getLastName());
	                row.createCell(6).setCellValue(maint.getSchedule_date().toString());
	            }

	            // Auto-size Columns
	            for (int i = 0; i < headers.length; i++) {
	                sheet.autoSizeColumn(i);
	            }

	        
	            workbook.write(response.getOutputStream());
	        }
	    }

	
	
	
	
	
	@GetMapping(value = "/total_closed_maintenance")
	public List<Maint> getClosedMaintenanceRecords(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    return Maintrepo.findClosedMaintenancesByDateRange(fromDate, toDate);
	}
	
	
	@GetMapping(value = "/total_closedmaintenance")
	public List<Maint> getClosedMaintenanceRecord()
		{
		List<Maint> list= new ArrayList<Maint>();
		
		for(Maint maint:Maintrepo.getClosedMaintenances()) {
			
			List<MaintenenaceCheckPoint> checkPoints=maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
			String checkListstr="";
			int i=1;
			  String newline = System.lineSeparator();
			for(MaintenenaceCheckPoint checkpoint:checkPoints) {
				checkListstr+=i+". "+checkpoint.getCheckpoint().getTask()+"  -  "+checkpoint.getStatus()+"/";
				i++;
			}
			if(maint.getMaint_id()==2) {
				System.out.println("***********************************************************************************************************************");
				System.out.println("CHECK LIST UPDATE "+checkListstr+"  SIZE :: "+checkPoints.size());
			}
			
			maint.setCheckListStr(checkListstr);
			list.add(maint);
			
		}
	    return list;     
	}
	
	@GetMapping(value = "/TotalClosedPPM")
	public List<Maint> TotalClosedPPM() {
	    List<Maint> list = new ArrayList<>();

	    for (Maint maint : Maintrepo.getClosedMaintenances()) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());

	       
	        maint.setCheckpointlist(checkPoints); 

	        list.add(maint);
	    }

	    return list;
	}
	
	@GetMapping("/getMaintenanceWithCheckValidation")
	public ResponseEntity<List<Maint>> getMaintenanceWithCheckValidation() {

	    // 1️⃣ Fetch all closed Maint records
	    List<Maint> maintList = Maintrepo.getClosedMaintenances();

	    if (maintList.isEmpty()) {
	        return ResponseEntity.ok(Collections.emptyList());
	    }

	    // 2️⃣ Get all Maint IDs
	    List<Integer> maintIds = maintList.stream()
	                                      .map(Maint::getMaint_id)
	                                      .collect(Collectors.toList());

	    // 3️⃣ Bulk fetch all checkpoints for these Maint IDs
	    List<MaintenenaceCheckPoint> allCheckPoints = maintenenaceCheckPointRepo.findByMaintIds(maintIds);

	    // 4️⃣ Group checkpoints by Maint ID
	    Map<Integer, List<MaintenenaceCheckPoint>> checkpointMap = allCheckPoints.stream()
	        .collect(Collectors.groupingBy(c -> c.getMaint().getMaint_id()));

	    // 5️⃣ Create a fixed thread pool for parallel processing
	    ExecutorService executor = Executors.newFixedThreadPool(20);

	    // 6️⃣ Process each Maint in parallel
	    List<CompletableFuture<Maint>> futures = maintList.stream()
	        .map(maint -> CompletableFuture.supplyAsync(() -> {
	            List<MaintenenaceCheckPoint> checkPoints = checkpointMap.getOrDefault(maint.getMaint_id(), Collections.emptyList());
	            maint.setCheckpointlist(checkPoints);

	            // Check if any checkpoint has an issue
	            boolean hasIssue = checkPoints.stream().anyMatch(checkPoint -> {
	                Checklist checklist = checkPoint.getCheckpoint();
	                if (checklist == null) return false;

	                CheckType checkType = checklist.getCheckType();
	                String statusStr = checkPoint.getStatus();

	                if (checkType != null && statusStr != null && !statusStr.trim().isEmpty()) {
	                    try {
	                        double statusValue = Double.parseDouble(statusStr.trim());
	                        Double lower = checklist.getLower_range();
	                        Double upper = checklist.getUpper_range();

	                        switch (checkType) {
	                            case RANGE: return lower != null && upper != null && (statusValue < lower || statusValue > upper);
	                            case MIN_VALUE: return lower != null && statusValue < lower;
	                            case MAX_VALUE: return upper != null && statusValue > upper;
	                            case ANY: return false;
	                        }
	                    } catch (NumberFormatException e) {
	                        return checkType != CheckType.ANY;
	                    }
	                }
	                return false;
	            });

	            if (hasIssue) {
	                maint.setCheckIssueBit(1);
	            }

	            return maint;
	        }, executor))
	        .collect(Collectors.toList());

	    // 7️⃣ Wait for all parallel tasks to complete
	    List<Maint> result = futures.stream()
	                                .map(CompletableFuture::join)
	                                .collect(Collectors.toList());

	    // 8️⃣ Shutdown the executor
	    executor.shutdown();

	    return ResponseEntity.ok(result);
	}

	
	

	// labwise closed ppm Software
	@GetMapping(value = "/total_closedmaintenanceByLab")
	public List<Maint> getClosedMaintenanceRecord(@RequestParam String labName) {
	    List<Maint> list = new ArrayList<>();

	    for (Maint maint : Maintrepo.getClosedMaintenancesByLab(labName)) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        StringBuilder checkListStr = new StringBuilder();
	        int i = 1;
	        String newline = System.lineSeparator();

	        for (MaintenenaceCheckPoint checkpoint : checkPoints) {
	            checkListStr.append(i)
	                        .append(". ")
	                        .append(checkpoint.getCheckpoint().getTask())
	                        .append("  -  ")
	                        .append(checkpoint.getStatus())
	                        .append("/");
	            i++;
	        }

	        if (maint.getMaint_id() == 2) {
	            System.out.println("***********************************************************************************************************************");
	            System.out.println("CHECK LIST UPDATE " + checkListStr + "  SIZE :: " + checkPoints.size());
	        }

	        maint.setCheckListStr(checkListStr.toString());
	        list.add(maint);
	    }

	    return list;
	}
	
	//for pending ppm mobileApp
	@GetMapping(value = "/getClosedMaintenanceLabWiseRecord")
	public List<Maint> getClosedMaintenanceLabWiseRecord(@RequestParam String labName) {
	    List<Maint> list = new ArrayList<>();

	    for (Maint maint : Maintrepo.getClosedMaintenancesByLab(labName)) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        maint.setCheckpointlist(checkPoints); 
	        list.add(maint);
	    }

	    return list;
	}

	

	 @GetMapping("/GetPPMbyLab/{labId}")
	    public Map<String, Object> getMaintenanceByLab(@PathVariable int labId) {
	        Map<String, Object> response = new HashMap<>();
	        
	        List<Maint> maintenances = Maintrepo.findByLabId(labId);
	        
	        response.put("count", maintenances.size());
	        response.put("records", maintenances);
	        
	        return response;
	    }
	
	@GetMapping(value = "/totalsclosedmaintenanceByLab")
	public Map<String, Object> getClosedMaintenanceCountandRecord(@RequestParam String labName) {
	    Map<String, Object> response = new HashMap<>();
	    List<Maint> list = new ArrayList<>();

	    for (Maint maint : Maintrepo.getClosedMaintenancesByLab(labName)) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        StringBuilder checkListStr = new StringBuilder();
	        int i = 1;

	        for (MaintenenaceCheckPoint checkpoint : checkPoints) {
	            checkListStr.append(i)
	                        .append(". ")
	                        .append(checkpoint.getCheckpoint().getTask())
	                        .append("  -  ")
	                        .append(checkpoint.getStatus())
	                        .append("/");
	            i++;
	        }

	        if (maint.getMaint_id() == 2) {
	            System.out.println("***********************************************************************************************************************");
	            System.out.println("CHECK LIST UPDATE " + checkListStr + "  SIZE :: " + checkPoints.size());
	        }

	        maint.setCheckListStr(checkListStr.toString());
	        list.add(maint);
	    }

	    // Add list and count to the response
	    response.put("count", list.size());
	    response.put("records", list);

	    return response;
	}
	

	@GetMapping(value = "/totalsclosedmaintenanceByLabAndUser")
	public Map<String, Object> getClosedMaintenanceCountByLabAndUser(
	        @RequestParam String labName,
	        @RequestParam int userId) {

	    Map<String, Object> response = new HashMap<>();
	    List<Maint> resultList = new ArrayList<>();

	    // Fetch all machines assigned to the user
	    List<MachineOwner> machineOwners = machineOwnerRepo.findByUserId(userId);
	    Set<Integer> userMachineIds = machineOwners.stream()
	                                               .map(mo -> mo.getMachine().getMachine_id())
	                                               .collect(Collectors.toSet());

	    // Fetch all closed maintenance by lab
	    List<Maint> allLabMaints = Maintrepo.getClosedMaintenancesByLab(labName);

	    for (Maint maint : allLabMaints) {
	        if (userMachineIds.contains(maint.getMachine().getMachine_id())) {
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkpoints); // same as in getClosedApprovalsByLab
	            resultList.add(maint);
	        }
	    }

	    response.put("count", resultList.size());
	    response.put("records", resultList);

	    return response;
	}

	
	@GetMapping(value = "/totalsclosedmaintenanceByLabAndUsers")
	public Map<String, Object> getClosedMaintenanceCountByLabAndUsers(
	        @RequestParam String labName,
	        @RequestParam int userId) {

	    Map<String, Object> response = new HashMap<>();
	    List<Maint> resultList = new ArrayList<>();

	    // Fetch all machines assigned to the user
	    List<MachineOwner> machineOwners = machineOwnerRepo.findByUserId(userId);
	    Set<Integer> userMachineIds = machineOwners.stream()
	                                               .map(mo -> mo.getMachine().getMachine_id())
	                                               .collect(Collectors.toSet());

	    // Fetch all closed maintenance by lab
	    List<Maint> allLabMaints = Maintrepo.getClosedMaintenancesByLab(labName);

	    for (Maint maint : allLabMaints) {
	        if (userMachineIds.contains(maint.getMachine().getMachine_id())) {
	            List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
	            maint.setCheckpointlist(checkPoints);

	            boolean hasIssue = false;

	            for (MaintenenaceCheckPoint checkPoint : checkPoints) {
	                Checklist checklist = checkPoint.getCheckpoint();
	                CheckType checkType = checklist.getCheckType();
	                String statusStr = checkPoint.getStatus();

	                if (checkType != null && statusStr != null && !statusStr.trim().isEmpty()) {
	                    try {
	                        double statusValue = Double.parseDouble(statusStr.trim());

	                        Double lower = checklist.getLower_range();
	                        Double upper = checklist.getUpper_range();

	                        switch (checkType) {
	                            case RANGE:
	                                if (lower != null && upper != null && (statusValue < lower || statusValue > upper)) {
	                                    hasIssue = true;
	                                }
	                                break;

	                            case MIN_VALUE:
	                                if (lower != null && statusValue < lower) {
	                                    hasIssue = true;
	                                }
	                                break;

	                            case MAX_VALUE:
	                                if (upper != null && statusValue > upper) {
	                                    hasIssue = true;
	                                }
	                                break;

	                            case ANY:
	                                // Always valid
	                                break;
	                        }
	                    } catch (NumberFormatException e) {
	                        if (checkType != CheckType.ANY) {
	                            hasIssue = true;
	                        }
	                    }
	                }
	            }

	            if (hasIssue) {
	                maint.setCheckIssueBit(1);
	            }

	            resultList.add(maint);
	        }
	    }

	    response.put("count", resultList.size());
	    response.put("records", resultList);

	    return response;
	}



	
    @GetMapping("/exportclosedMaintaince")
    public ResponseEntity<Resource> exportclosedMaintaince() throws IOException {
      
        List<Maint> maintList = Maintrepo.getClosedMaintenances();

       
        for (Maint maint : maintList) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints); 
        }

      
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Closed Maintaince");

      
        String[] columns = { 
            "Sr No", "Machine", "Lab Name", "Frequency", "Overall Status", 
            "Raised By", "Done By", "Schedule Date", "Closed Date",
            "Checklist", "Acceptable Range", "Value"
        };

   
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        int serialNo = 1;

        for (Maint maint : maintList) {
            String machineDetails = maint.getMachine().getMachine_name() + " - " + maint.getMachine().getEqid();
            
            if (maint.getCheckpointlist() != null && !maint.getCheckpointlist().isEmpty()) {
                for (MaintenenaceCheckPoint cp : maint.getCheckpointlist()) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(serialNo++);
                    row.createCell(1).setCellValue(machineDetails);
                    row.createCell(2).setCellValue(maint.getLab() != null ? maint.getLab().getLabName() : "N/A");
                    row.createCell(3).setCellValue(maint.getFrequency());
                    row.createCell(4).setCellValue(maint.getOverall_status());
                    row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
                    row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
                    row.createCell(7).setCellValue(maint.getSchedule_date().toString());
                    row.createCell(8).setCellValue(maint.getClosedDate().toString());
                  
                    row.createCell(9).setCellValue(cp.getTask());
                    row.createCell(10).setCellValue(cp.getAcceptableRange());
                    row.createCell(11).setCellValue(cp.getStatus());
                }
            } else {
              
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(serialNo++);
                row.createCell(1).setCellValue(machineDetails);
                row.createCell(2).setCellValue(maint.getLab() != null ? maint.getLab().getLabName() : "N/A");
                row.createCell(3).setCellValue(maint.getFrequency());
                row.createCell(4).setCellValue(maint.getOverall_status());
                row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
                row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
                row.createCell(7).setCellValue(maint.getSchedule_date().toString());
                row.createCell(8).setCellValue(maint.getClosedDate().toString());
               
                row.createCell(9).setCellValue("No Checkpoints");
                row.createCell(10).setCellValue("");
                row.createCell(11).setCellValue("");
            }
        }

        workbook.write(out);
        workbook.close();

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ClosedMaintaince.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    
    
    @GetMapping("/exportDateWiseclosedMaintaince")
    public ResponseEntity<Resource> exportclosedMaintaince(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate
    ) throws IOException {

        List<Maint> maintList = Maintrepo.getClosedMaintenancesBetweenDates(fromDate, toDate);

        for (Maint maint : maintList) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Closed Maintaince");

        String[] columns = {
            "Sr No", "Machine", "Lab Name", "Frequency", "Overall Status",
            "Raised By", "Done By", "Schedule Date", "Closed Date",
            "Checklist", "Acceptable Range", "Value"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        int serialNo = 1;

        for (Maint maint : maintList) {
            String machineDetails = maint.getMachine().getMachine_name() + " - " + maint.getMachine().getEqid();

            if (maint.getCheckpointlist() != null && !maint.getCheckpointlist().isEmpty()) {
                for (MaintenenaceCheckPoint cp : maint.getCheckpointlist()) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(serialNo++);
                    row.createCell(1).setCellValue(machineDetails);
                    row.createCell(2).setCellValue(maint.getLab() != null ? maint.getLab().getLabName() : "N/A");
                    row.createCell(3).setCellValue(maint.getFrequency());
                    row.createCell(4).setCellValue(maint.getOverall_status());
                    row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
                    row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
                    row.createCell(7).setCellValue(maint.getSchedule_date().toString());
                    row.createCell(8).setCellValue(maint.getClosedDate().toString());
                    row.createCell(9).setCellValue(cp.getTask());
                    row.createCell(10).setCellValue(cp.getAcceptableRange());
                    row.createCell(11).setCellValue(cp.getStatus());
                }
            } else {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(serialNo++);
                row.createCell(1).setCellValue(machineDetails);
                row.createCell(2).setCellValue(maint.getLab() != null ? maint.getLab().getLabName() : "N/A");
                row.createCell(3).setCellValue(maint.getFrequency());
                row.createCell(4).setCellValue(maint.getOverall_status());
                row.createCell(5).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
                row.createCell(6).setCellValue(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
                row.createCell(7).setCellValue(maint.getSchedule_date().toString());
                row.createCell(8).setCellValue(maint.getClosedDate().toString());
                row.createCell(9).setCellValue("No Checkpoints");
                row.createCell(10).setCellValue("");
                row.createCell(11).setCellValue("");
            }
        }

        workbook.write(out);
        workbook.close();

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ClosedMaintaince.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @GetMapping("/getClosedApprovalsByLab")
    public ResponseEntity<Map<String, Object>> getClosedApprovals(@RequestParam String labName) {
        List<Maint> closedApprovals = Maintrepo.findClosedApprovalsByLab(labName);

        if (closedApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : closedApprovals) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", closedApprovals.size());
        response.put("datas", closedApprovals);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getClosedApprovalsByLabAndUser")
    public ResponseEntity<Map<String, Object>> getClosedApprovalsByLabAndUser(
            @RequestParam String labName,
            @RequestParam int userId) {

        // Get all machine IDs assigned to the user
        List<MachineOwner> machineOwners = machineOwnerRepo.findByUserId(userId);
        Set<Integer> userMachineIds = machineOwners.stream()
                .map(mo -> mo.getMachine().getMachine_id())
                .collect(Collectors.toSet());

        // Fetch all closed approvals by lab
        List<Maint> closedApprovals = Maintrepo.findClosedApprovalsByLab(labName);

        // Filter by user-assigned machines and attach checkpoints
        List<Maint> filteredClosedApprovals = new ArrayList<>();
        for (Maint maint : closedApprovals) {
            if (userMachineIds.contains(maint.getMachine().getMachine_id())) {
                List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
                maint.setCheckpointlist(checkpoints);
                filteredClosedApprovals.add(maint);
            }
        }

        if (filteredClosedApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", filteredClosedApprovals.size());
        response.put("datas", filteredClosedApprovals);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getUnApprovalsByLab")
    public ResponseEntity<Map<String, Object>> getUnApprovals(@RequestParam String labName) {
        List<Maint> unApprovals = Maintrepo.findUnApprovalsByLab(labName);

        if (unApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Maint maint : unApprovals) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", unApprovals.size());
        response.put("datas", unApprovals);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getUnApprovalsByLabAndUser")
    public ResponseEntity<Map<String, Object>> getUnApprovalsByLabAndUser(
            @RequestParam String labName,
            @RequestParam int userId) {
    	//Fetch machines assigned to user
        List<MachineOwner> machineOwners = machineOwnerRepo.findByUserId(userId);
        Set<Integer> userMachineIds = machineOwners.stream()
                .map(mo -> mo.getMachine().getMachine_id())
                .collect(Collectors.toSet());

        List<Maint> unApprovals = Maintrepo.findUnApprovalsByLab(labName);

        //Filter maintenances for machines owned by user
        List<Maint> filteredUnApprovals = new ArrayList<>();
        for (Maint maint : unApprovals) {
            if (userMachineIds.contains(maint.getMachine().getMachine_id())) {
                List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintIds(maint.getMaint_id());
                maint.setCheckpointlist(checkpoints);
                filteredUnApprovals.add(maint);
            }
        }

        if (filteredUnApprovals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("count", filteredUnApprovals.size());
        response.put("datas", filteredUnApprovals);

        return ResponseEntity.ok(response);
    }


	
	@GetMapping(value = "/export/total_closed_maintenance")
	public ResponseEntity<byte[]> exportMaintenanceRecords(
	        @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

	    // Fetch records based on the date range, if present
	    List<Maint> maintList;
	    if (fromDate != null && toDate != null) {
	        maintList = Maintrepo.findClosedMaintenancesByDateRange(fromDate, toDate);
	    } else {
	        maintList = Maintrepo.getClosedMaintenances();
	    }

	    // Create an Excel workbook
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Maintenance Records");

	    // Create header row
	    Row headerRow = sheet.createRow(0);
	    headerRow.createCell(0).setCellValue("Sr.No");
	    headerRow.createCell(1).setCellValue("Machine Name");
	    headerRow.createCell(2).setCellValue("Machine Eqid");
	    headerRow.createCell(3).setCellValue("Location");
	    headerRow.createCell(4).setCellValue("Frequency");
	    headerRow.createCell(5).setCellValue("Done By");
	    headerRow.createCell(6).setCellValue("Overall Status");
	    headerRow.createCell(7).setCellValue("Check Points");
	    headerRow.createCell(8).setCellValue("Schedule Date");
	    headerRow.createCell(9).setCellValue("Closed Date");

	    // Fill data rows
	    int rowNum = 1;
	    for (Maint maint : maintList) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(rowNum - 1);
	        row.createCell(1).setCellValue(maint.getMachine().getMachine_name());
	        row.createCell(2).setCellValue(maint.getMachine().getEqid());
	        row.createCell(3).setCellValue(maint.getMachine().getLocation());
	        row.createCell(4).setCellValue(maint.getFrequency());

	        // Handling Done By field
	        UserDetails doneBy = maint.getDone_by();
	        if (doneBy != null) {
	            row.createCell(5).setCellValue(doneBy.getFirstName() + " " + doneBy.getLastName()); 
	        } else {
	            row.createCell(5).setCellValue("N/A"); 
	        }
	        row.createCell(6).setCellValue(maint.getStatusCode() == 2 ? "Open" : "Closed");

	        // Add Check Points
	        StringBuilder checkListStr = new StringBuilder();
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        int i = 1;
	        for (MaintenenaceCheckPoint checkpoint : checkPoints) {
	            // Add each checkpoint with a newline after it
	            checkListStr.append(i++).append(". ").append(checkpoint.getCheckpoint().getTask())
	                        .append(" - ").append(checkpoint.getStatus()).append("\n"); // Use \n for line breaks
	        }

	       
	        Cell checkListCell = row.createCell(7);
	        checkListCell.setCellValue(checkListStr.toString());
	       
	        CellStyle style = workbook.createCellStyle();
	        style.setWrapText(true);
	        checkListCell.setCellStyle(style);

	        row.createCell(8).setCellValue(maint.getSchedule_date() != null ? maint.getSchedule_date().toString() : "");
	        row.createCell(9).setCellValue(maint.getClosedDate() != null ? maint.getClosedDate().toString() : "");
	    }

	    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
	        workbook.write(bos);
	        workbook.close();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=maintenance_records.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	                .body(bos.toByteArray());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).body(null);
	    }
	}
	
	
	@GetMapping(value = "/date/total_closedmaintenanceByLab")
	public List<Maint> getClosedMaintenanceRecordByLab(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
	        @RequestParam("labName") String labName) {
	    
	    List<Maint> list = new ArrayList<>();

	    List<Maint> closedMaintenances = Maintrepo.findClosedMaintenancesByDateRangeAndLab(fromDate, toDate, labName);
	    
	    for (Maint maint : closedMaintenances) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        StringBuilder checkListStr = new StringBuilder();
	        int i = 1;

	        for (MaintenenaceCheckPoint checkpoint : checkPoints) {
	            checkListStr.append(i)
	                        .append(". ")
	                        .append(checkpoint.getCheckpoint().getTask())
	                        .append("  -  ")
	                        .append(checkpoint.getStatus())
	                        .append("/");
	            i++;
	        }

	        maint.setCheckListStr(checkListStr.toString());
	        list.add(maint);
	    }

	    return list;
	}

	
	
	
	@GetMapping(value = "/date/total_closedmaintenance")
	public List<Maint> getClosedMaintenanceRecord(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    
	    List<Maint> list = new ArrayList<>();

	    // Fetch closed maintenances within the specified date range
	    List<Maint> closedMaintenances = Maintrepo.findClosedMaintenancesByDateRange(fromDate, toDate);
	    
	    for (Maint maint : closedMaintenances) {
	        List<MaintenenaceCheckPoint> checkPoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	        StringBuilder checkListStr = new StringBuilder();
	        int i = 1;

	        for (MaintenenaceCheckPoint checkpoint : checkPoints) {
	            checkListStr.append(i)
	                        .append(". ")
	                        .append(checkpoint.getCheckpoint().getTask())
	                        .append("  -  ")
	                        .append(checkpoint.getStatus())
	                        .append("/");
	            i++;
	        }

	        maint.setCheckListStr(checkListStr.toString());
	        list.add(maint);
	    }

	    return list;
	}

	
	
	  @GetMapping("/closed_maintenance")
	    public ResponseEntity<List<MaintStatusDTO>> getClosedMaintenanceStatuses() {
	        List<MaintStatusDTO> maintStatusDTOList = Maintrepo.getClosedMaintenanceStatuses();
	        return new ResponseEntity<>(maintStatusDTOList, HttpStatus.OK);
	    }


	 


	@GetMapping(value = "/getCurrentWeekOpenMaintenence", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekOpenMaintenence() {
		List<Maint> openMaintenance= new ArrayList<Maint>();
		try {
			PPMDashboardCountDTO pPMDashboardCountDTO = new PPMDashboardCountDTO();
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
		
		openMaintenance= maintServices.getCurrentWeekOpenMaintenance(startOfWeek, endOfWeek);
			int i=1;
			 for (Maint maint:openMaintenance) {
			     calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);
	            if(maint.getRaisedBy()!=null) {
	            	   maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());
	            	   System.out.println("   I "+i+"   "+maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());
	            }
	         
	        	maint.setWeek(weekOfYear);
	        	  DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
		            String strDate = dateFormat.format(maint.getSchedule_date()); 
		            maint.setScheduleDateStr(strDate);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return openMaintenance;
	}
	
	
	@GetMapping(value = "/getCurrentWeekOpenMaintenenceByLab", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekOpenMaintenenceByLab(@RequestParam String labName) {
	    List<Maint> openMaintenance = new ArrayList<>();
	    try {
	        Calendar calendar = Calendar.getInstance();

	        // Set the calendar to the start of the week (Sunday)
	        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
	        Date startOfWeek = calendar.getTime();

	        // Set the calendar to the end of the week (Saturday)
	        calendar.add(Calendar.DAY_OF_WEEK, 6);
	        Date endOfWeek = calendar.getTime();

	        System.out.println("Start of the week: " + new SimpleDateFormat("yyyy-MM-dd").format(startOfWeek));
	        System.out.println("End of the week: " + new SimpleDateFormat("yyyy-MM-dd").format(endOfWeek));

	        // Fetch maintenance by labName
	        openMaintenance = Maintrepo.getCurrentWeekOpenMaintenanceByLab(startOfWeek, endOfWeek, labName);

	        int i = 1;
	        for (Maint maint : openMaintenance) {
	            calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);

	            if (maint.getRaisedBy() != null) {
	                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	                System.out.println(" I " + i + " " + maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            }

	            maint.setWeek(weekOfYear);
	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String strDate = dateFormat.format(maint.getSchedule_date());
	            maint.setScheduleDateStr(strDate);
	            i++;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return openMaintenance;
	}

	
	
	@GetMapping(value = "/getCurrentWeekOpenMaintenenceByMachineName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekOpenMaintenenceByMachineName(@RequestParam("machineName") String machineName) {
		List<Maint> openMaintenance= new ArrayList<Maint>();
		try {
			PPMDashboardCountDTO pPMDashboardCountDTO = new PPMDashboardCountDTO();
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
		
		openMaintenance= maintServices.getCurrentWeekOpenMaintenanceByMachineName(startOfWeek, endOfWeek,machineName);
			int i=1;
			for (Maint maint:openMaintenance) {
			     calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            Date date = Calendar.getInstance().getTime();  
	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
	            String strDate = dateFormat.format(maint.getSchedule_date()); 
	            maint.setScheduleDateStr(strDate);
	            maint.setSrNo(i);
	            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());
	        	maint.setWeek(weekOfYear);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return openMaintenance;
	}

	@GetMapping(value = "/getCurrentWeekClosedMaintenence", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekClosedMaintenence() {
		List<Maint> closedMaintenance= new ArrayList<Maint>();
		try {
			
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			closedMaintenance	=maintServices.getCurrentWeekClosedMaintenance(startOfWeek,endOfWeek);
			int i=1;
			 for (Maint maint:closedMaintenance) {
			     calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);
	            if(maint.getRaisedBy()!=null) {
		            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

	            }
	            if(maint.getDone_by()!=null) {
		            maint.setDoneName(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName());

	            }
	            i++;
	        	maint.setWeek(weekOfYear);
	        	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
	        	 String strDate = dateFormat.format(maint.getSchedule_date()); 
		            maint.setScheduleDateStr(strDate);
		            if(maint.getClosedDate()!=null) {
			            String closedDate = dateFormat.format(maint.getClosedDate()); 
			            maint.setClosedDateStr(closedDate);
		            }
		            maint.setScheduleDateStr(strDate);
		        
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
		return closedMaintenance;
	}
	
	
	@GetMapping(value = "/getCurrentWeekClosedMaintenanceByLab", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekClosedMaintenanceByLab(@RequestParam String labName) {
	    List<Maint> closedMaintenance = new ArrayList<>();
	    try {
	        Calendar calendar = Calendar.getInstance();

	        // Set the calendar to the start of the week (Sunday)
	        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
	        Date startOfWeek = calendar.getTime();

	        // Set the calendar to the end of the week (Saturday)
	        calendar.add(Calendar.DAY_OF_WEEK, 6);
	        Date endOfWeek = calendar.getTime();

	        System.out.println("Start of the week: " + new SimpleDateFormat("yyyy-MM-dd").format(startOfWeek));
	        System.out.println("End of the week: " + new SimpleDateFormat("yyyy-MM-dd").format(endOfWeek));

	        // Fetch maintenance by labName
	        closedMaintenance = Maintrepo.getCurrentWeekClosedMaintenanceByLab(startOfWeek, endOfWeek, labName);

	        int i = 1;
	        for (Maint maint : closedMaintenance) {
	            calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);

	            if (maint.getRaisedBy() != null) {
	                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            }
	            if (maint.getDone_by() != null) {
	                maint.setDoneName(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	            }
	            maint.setWeek(weekOfYear);

	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String strDate = dateFormat.format(maint.getSchedule_date());
	            maint.setScheduleDateStr(strDate);

	            if (maint.getClosedDate() != null) {
	                String closedDate = dateFormat.format(maint.getClosedDate());
	                maint.setClosedDateStr(closedDate);
	            }

	            i++;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return closedMaintenance;
	}

	
	@GetMapping(value = "/getCurrentWeekClosedMaintenenceByMachine", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekClosedMaintenenceByMachine(@RequestParam("machineName") String machineName) {
		List<Maint> closedMaintenance= new ArrayList<Maint>();
		try {
			
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			closedMaintenance	=maintServices.getCurrentWeekClosedMaintenanceByMachineName(startOfWeek,endOfWeek,machineName);
			int i=1;
			 for (Maint maint:closedMaintenance) {
			     calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);
	            if(maint.getRaisedBy()!=null) {
		            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

	            }
	            if(maint.getDone_by()!=null) {
		            maint.setDoneName(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName());

	            }
	            i++;
	        	maint.setWeek(weekOfYear);
	        	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
		            String strDate = dateFormat.format(maint.getSchedule_date()); 
		            maint.setScheduleDateStr(strDate);
		            String closedDate = dateFormat.format(maint.getClosedDate()); 
		            maint.setScheduleDateStr(strDate);
		            maint.setClosedDateStr(closedDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
		}
		return closedMaintenance;
	}
	@GetMapping(value = "/getCurrentWeekOverduesMaintenence", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekOverduesMaintenence() {
		List<Maint> overduesMaintList=new ArrayList<Maint>();
		try {
			PPMDashboardCountDTO pPMDashboardCountDTO = new PPMDashboardCountDTO();
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			
			overduesMaintList=	Maintrepo.getCurrentWeekOverudesMaintenance(startOfWeek);
			int i=1;
			  for (Maint maint:overduesMaintList) {
				     calendar.setTime(maint.getSchedule_date());
		            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		            maint.setSrNo(i);
		            if(maint.getRaisedBy()!=null) {
			            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

		            }

		        	maint.setWeek(weekOfYear);
		        	  Date endDate = new Date();  // year is offset by 1900 and month is zero-based

				        long diffInMillis = endDate.getTime() - maint.getSchedule_date().getTime();
				        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
				        
				        maint.setOverdues(diffInDays+" Day");
                      System.out.println("diffInDays    "+diffInDays);
					i++;
					   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
			            String strDate = dateFormat.format(maint.getSchedule_date()); 
			            maint.setScheduleDateStr(strDate);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return overduesMaintList;
	}
	
	  // 🧭 Common helper to enrich data (optional but matches your logic)
    private List<Maint> enrichMaintData(List<Maint> maintList) {
        Calendar calendar = Calendar.getInstance();
        int i = 1;

        for (Maint maint : maintList) {
            calendar.setTime(maint.getSchedule_date());
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            maint.setSrNo(i++);
            if (maint.getRaisedBy() != null) {
                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
            }
            maint.setWeek(weekOfYear);

            Date endDate = new Date();
            long diffInMillis = endDate.getTime() - maint.getSchedule_date().getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            maint.setOverdues(diffInDays + " Day");

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            maint.setScheduleDateStr(dateFormat.format(maint.getSchedule_date()));
        }
        return maintList;
    }

    // 1️⃣ Pagination only
    @GetMapping(value = "/getCurrentWeekOverduesMaintenenceWithPaginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPaginatedOverdues(
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.findCurrentWeekOverdues(pageable);
        List<Maint> enriched = enrichMaintData(maintPage.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("records", enriched);
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 2️⃣ Pagination + Search
    @GetMapping(value = "/getCurrentWeekOverduesMaintenenceWithSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> searchOverdues(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Maint> maintPage = Maintrepo.searchCurrentWeekOverdues(keyword, pageable);
        List<Maint> enriched = enrichMaintData(maintPage.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("records", enriched);
        response.put("currentPage", maintPage.getNumber());
        response.put("totalItems", maintPage.getTotalElements());
        response.put("totalPages", maintPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Count only
    @GetMapping(value = "/getCurrentWeekOverduesMaintenenceWithCount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getOverdueCount() {
        long count = Maintrepo.countCurrentWeekOverdues();
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
	
	
	
	
	/////
	@GetMapping(value = "/getOverduesMaintenanceByDateRange", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getOverduesMaintenanceByDateRange(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    List<Maint> overduesMaintList = new ArrayList<>();
	    try {
	        overduesMaintList = Maintrepo.getOverduesMaintenanceByDateRange(fromDate, toDate);
	        int i = 1;
	        for (Maint maint : overduesMaintList) {
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            maint.setSrNo(i);
	            if (maint.getRaisedBy() != null) {
	                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            }

	            maint.setWeek(weekOfYear);
	            Date endDate = new Date(); 
	            long diffInMillis = endDate.getTime() - maint.getSchedule_date().getTime();
	            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

	            maint.setOverdues(diffInDays + " Day");
	            System.out.println("diffInDays: " + diffInDays);
	            i++;

	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String strDate = dateFormat.format(maint.getSchedule_date());
	            maint.setScheduleDateStr(strDate);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return overduesMaintList;
	}

	
	/// export 
	
	
	
	  @GetMapping(value = "/export/getCurrentWeekOverduesMaintenence", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> exportMaintenanceData(
	            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws IOException {

	        List<Maint> maintenanceData;
	        
	        
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
	        Date startOfWeek = calendar.getTime();
	        
	      
	        if (fromDate != null && toDate != null) {
	            maintenanceData = Maintrepo.getOverduesMaintenanceByDateRange(fromDate, toDate);
	        } else {
	           
	            maintenanceData = Maintrepo.getCurrentWeekOverudesMaintenance(startOfWeek);
	        }

	        // Creating the Excel file
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Maintenance Data");

	        // Set up header row
	        Row headerRow = sheet.createRow(0);
	        String[] columns = {"Sr No", "Maintenance Eqid", "Machine Name","Frequency","Machine Location", "Scheduled Date", "Raised By", "Overdue Days"};
	        for (int i = 0; i < columns.length; i++) {
	            headerRow.createCell(i).setCellValue(columns[i]);
	        }

	        // Populating the Excel sheet with maintenance data
	        int rowNum = 1;
	        int srNo = 1;
	        for (Maint maint : maintenanceData) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(srNo++);
	            row.createCell(1).setCellValue(maint.getMachine().getEqid());
	            row.createCell(2).setCellValue(maint.getMachine().getMachine_name());
	          
		        row.createCell(3).setCellValue(maint.getFrequency());
		        row.createCell(4).setCellValue(maint.getMachine().getLocation());
		       
	            row.createCell(5).setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(maint.getSchedule_date()));

	            // Check if RaisedBy exists to avoid null pointer exceptions
	            if (maint.getRaisedBy() != null) {
	                row.createCell(6).setCellValue(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            } else {
	                row.createCell(6).setCellValue("N/A");
	            }

	           

	            // Calculate Overdue days if status is "Open"
	            if (maint.getStatusCode() == 1) {
	                long diffInMillis = new Date().getTime() - maint.getSchedule_date().getTime();
	                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
	                row.createCell(7).setCellValue(diffInDays + " Day(s)");
	            } else {
	                row.createCell(7).setCellValue("N/A");
	            }
	        }

	        // Convert the workbook to a byte array
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        workbook.write(byteArrayOutputStream);
	        workbook.close();

	        // Setting up response headers to trigger the download of the Excel file
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=MaintenanceData.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(byteArrayOutputStream.toByteArray());
	    }

	
	/////////////////------------------------------------//////////////////////////////
	

	@GetMapping(value = "/getCurrentWeekOverduesMaintenenceByMachine", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekOverduesMaintenenceByMachine(@RequestParam("machineName") String machineName) {
		List<Maint> overduesMaintList=new ArrayList<Maint>();
		try {
			PPMDashboardCountDTO pPMDashboardCountDTO = new PPMDashboardCountDTO();
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			
			overduesMaintList=	maintServices.getCurrentWeekOverudesMaintenanceByMachineName(startOfWeek,machineName);
			int i=1;
			  for (Maint maint:overduesMaintList) {
				     calendar.setTime(maint.getSchedule_date());
		            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		            maint.setSrNo(i);
		            if(maint.getRaisedBy()!=null) {
			            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

		            }
		        	maint.setWeek(weekOfYear);
					i++;
					 // year is offset by 1900 and month is zero-based
				        Date endDate = new Date();  // year is offset by 1900 and month is zero-based

			        long diffInMillis = endDate.getTime() - maint.getSchedule_date().getTime();
			        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
			        
			        maint.setOverdues(diffInDays+" Day");
			        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
		            String strDate = dateFormat.format(maint.getSchedule_date()); 
		            maint.setScheduleDateStr(strDate);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return overduesMaintList;
	}
	

	// ppm cards
	
    @GetMapping("/Totalrecords")
    public ResponseEntity<MaintenanceStatusSummaryDTO> getCurrentWeekMaintenanceSummary() {
        
        // Get the current date (today's date)
        Date currentDate = new Date();
        List<Maint> overdueRecords = Maintrepo.getOverduesMaintenaceByDates(new Date());
        List<Maint> openRecords = Maintrepo.getOpeneMaintenancesByDate(new Date());
        List<Maint> closedRecords = Maintrepo.getClosedMaintenances();
        MaintenanceStatusSummaryDTO summaryDTO = new MaintenanceStatusSummaryDTO();
        
        summaryDTO.setOverdueCount(overdueRecords.size());
        summaryDTO.setOpenCount(openRecords.size());
        summaryDTO.setClosedCount(closedRecords.size());
        summaryDTO.setOverdueRecords(overdueRecords);
        summaryDTO.setOpenRecords(openRecords);
        summaryDTO.setClosedRecords(closedRecords);
        long totalCount = overdueRecords.size() + openRecords.size() + closedRecords.size();
        summaryDTO.setTotalCount(totalCount);
        List<Maint> totalRecords = new ArrayList<>();
        totalRecords.addAll(overdueRecords);
        totalRecords.addAll(openRecords);
        totalRecords.addAll(closedRecords);

     
        summaryDTO.setTotalRecords(totalRecords);

       
        return ResponseEntity.ok(summaryDTO);
    }
	
    @GetMapping("/TotalrecordsBylab")
    public ResponseEntity<MaintenanceStatusSummaryDTO> TotalrecordsBylab(@RequestParam("labName") String labName) {
        List<Maint> overdueRecords = Maintrepo.getOverduesMaintenaceByDatesAndLab(new Date(), labName);
        List<Maint> openRecords = Maintrepo.getOpeneMaintenancesByLab(labName);
        List<Maint> closedRecords = Maintrepo.getClosedMaintenancesByLab(labName);
        List<Maint> approvedRecords = Maintrepo.findClosedApprovalsByLab(labName);

        // Populate checkpoints for approvedRecords
        for (Maint maint : approvedRecords) {
            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findCheckpointsByMaintId(maint.getMaint_id());
            maint.setCheckpointlist(checkpoints);
        }

        MaintenanceStatusSummaryDTO summaryDTO = new MaintenanceStatusSummaryDTO();
        summaryDTO.setOverdueCount(overdueRecords.size());
        summaryDTO.setOpenCount(openRecords.size());
        summaryDTO.setClosedCount(closedRecords.size());
        summaryDTO.setApprovedCount(approvedRecords.size());

        summaryDTO.setOverdueRecords(overdueRecords);
        summaryDTO.setOpenRecords(openRecords);
        summaryDTO.setClosedRecords(closedRecords);
        summaryDTO.setApprovedRecords(approvedRecords);

        long totalCount = overdueRecords.size() + openRecords.size() + closedRecords.size() + approvedRecords.size();
        summaryDTO.setTotalCount(totalCount);

        List<Maint> totalRecords = new ArrayList<>();
        totalRecords.addAll(overdueRecords);
        totalRecords.addAll(openRecords);
        totalRecords.addAll(closedRecords);
        totalRecords.addAll(approvedRecords);
        summaryDTO.setTotalRecords(totalRecords);

        return ResponseEntity.ok(summaryDTO);
    }

    
    
    @GetMapping("/MachineWiseRecords")
    public ResponseEntity<MaintenanceStatusSummaryDTO> getMachineWiseMaintenanceSummary(@RequestParam("eqid") String eqid) {

        List<Maint> overdueRecords = Maintrepo.getOverdueMaintenances(eqid,1);
        List<Maint> openRecords = Maintrepo.getOpenMaintenances(eqid, 1);
        List<Maint> closedRecords = Maintrepo.getMaintenancesByEqidAndStatus(eqid, 0);

        // Adjust dates to IST
        adjustDatesToIST(overdueRecords);
        adjustDatesToIST(openRecords);
        adjustDatesToIST(closedRecords);

        MaintenanceStatusSummaryDTO summaryDTO = new MaintenanceStatusSummaryDTO();
        summaryDTO.setOverdueCount(overdueRecords.size());
        summaryDTO.setOpenCount(openRecords.size());
        summaryDTO.setClosedCount(closedRecords.size());
        summaryDTO.setOverdueRecords(overdueRecords);
        summaryDTO.setOpenRecords(openRecords);
        summaryDTO.setClosedRecords(closedRecords);

        long totalCount = overdueRecords.size() + openRecords.size() + closedRecords.size();
        summaryDTO.setTotalCount(totalCount);

        List<Maint> totalRecords = new ArrayList<>();
        totalRecords.addAll(overdueRecords);
        totalRecords.addAll(openRecords);
        totalRecords.addAll(closedRecords);
        summaryDTO.setTotalRecords(totalRecords);

        return ResponseEntity.ok(summaryDTO);
    }

    // Utility method to adjust scheduleDate to IST
    private void adjustDatesToIST(List<Maint> records) {
        if (records != null) {
            ZoneId istZone = ZoneId.of("Asia/Kolkata"); // IST TimeZone
            records.forEach(record -> {
                if (record.getSchedule_date() != null) {
                    Instant instant = record.getSchedule_date().toInstant();
                    ZonedDateTime istDateTime = instant.atZone(ZoneId.of("UTC"))
                                                       .withZoneSameInstant(istZone);
                    record.setSchedule_date(Date.from(istDateTime.toInstant())); // Adjust to IST
                }
            });
        }
    }

    
    
	
	 @GetMapping("/current_week_summary")
	    public ResponseEntity<MaintenanceStatusSummaryDTO> getCurrentWeekMaintenanceSummary(
	            @RequestParam("startOfWeek") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startOfWeek,
	            @RequestParam("endOfWeek") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endOfWeek) {
	        
	        // Get the maintenance records and counts
	        List<Maint> overdueRecords = Maintrepo.getCurrentWeekOverudesMaintenance(startOfWeek);
	        List<Maint> openRecords = Maintrepo.getCurrentWeekOpenMaintenance(startOfWeek, endOfWeek);
	        List<Maint> closedRecords = Maintrepo.getCurrentWeekClosedMaintenance(startOfWeek, endOfWeek);

	        MaintenanceStatusSummaryDTO summaryDTO = new MaintenanceStatusSummaryDTO();
	        
	        // Set counts (size of the lists)
	        summaryDTO.setOverdueCount(overdueRecords.size());
	        summaryDTO.setOpenCount(openRecords.size());
	        summaryDTO.setClosedCount(closedRecords.size());

	        // Set the records in the DTO
	        summaryDTO.setOverdueRecords(overdueRecords);
	        summaryDTO.setOpenRecords(openRecords);
	        summaryDTO.setClosedRecords(closedRecords);

	        // Calculate the total count (sum of overdue, open, and closed records)
	        long totalCount = overdueRecords.size() + openRecords.size() + closedRecords.size();
	        summaryDTO.setTotalCount(totalCount);

	        // Combine all records into a single list for the total records
	        List<Maint> totalRecords = new ArrayList<>();
	        totalRecords.addAll(overdueRecords);
	        totalRecords.addAll(openRecords);
	        totalRecords.addAll(closedRecords);

	        // Set the total records in the DTO
	        summaryDTO.setTotalRecords(totalRecords);

	        // Return the response entity
	        return ResponseEntity.ok(summaryDTO);
	    }
	
	
	
	@GetMapping(value = "/getCurrentWeekTotoalMaintenence", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekTotoalMaintenence() {
		List<Maint> totoal= new ArrayList<Maint>();
		try {
	
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			
			

		     
		        
		     System.out.println("S "+startOfWeek);
		     System.out.println("E "+endOfWeek);  
			
			List<Maint> openMaintList=maintServices.getCurrentWeekOpenMaintenance(startOfWeek, endOfWeek);
			List<Maint> overduesMaintList=maintServices.getCurrentWeekOverudesMaintenance(startOfWeek);
			List<Maint> closedMaintList=maintServices.getCurrentWeekClosedMaintenance(startOfWeek, endOfWeek);
			
			
			totoal.addAll(openMaintList);
			totoal.addAll(overduesMaintList);
			totoal.addAll(closedMaintList);
			int i=1;
			   for (Maint maint:totoal) {
				     calendar.setTime(maint.getSchedule_date());
		            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		            maint.setSrNo(i);
		            
		            if(maint.getRaisedBy()!=null) {
			            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

		            }
		            if(maint.getDone_by()!=null) {
		            	maint.setDoneName(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName());
		            }
		            i++;
		        	maint.setWeek(weekOfYear);
		        	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
			            String strDate = dateFormat.format(maint.getSchedule_date()); 
			            maint.setScheduleDateStr(strDate);
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return totoal;
	}
	
	@GetMapping(value = "/getMaintenanceByDateRange", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getMaintenanceByDateRange(
	        @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	        @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	    List<Maint> totalMaintenance = new ArrayList<>();
	    try {
	        // Validate input dates
	        if (fromDate == null || toDate == null) {
	            throw new IllegalArgumentException("Both fromDate and toDate must be provided.");
	        }

	        System.out.println("Fetching data between dates: " + fromDate + " and " + toDate);

	        // Fetch maintenance data for the specified date range
	        List<Maint> maintenanceList = Maintrepo.findByScheduleDateBetween(fromDate, toDate);

	        // Process the data
	        int i = 1;
	        Calendar calendar = Calendar.getInstance();
	        for (Maint maint : maintenanceList) {
	            calendar.setTime(maint.getSchedule_date());
	            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

	            maint.setSrNo(i);

	            if (maint.getRaisedBy() != null) {
	                maint.setRaisedName(maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName());
	            }
	            if (maint.getDone_by() != null) {
	                maint.setDoneName(maint.getDone_by().getFirstName() + " " + maint.getDone_by().getLastName());
	            }
	            i++;

	            maint.setWeek(weekOfYear);

	            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            String strDate = dateFormat.format(maint.getSchedule_date());
	            maint.setScheduleDateStr(strDate);
	        }

	        totalMaintenance.addAll(maintenanceList);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return totalMaintenance;
	}

	
		
	@GetMapping(value = "/getCurrentWeekTotoalMaintenenceByMachine", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getCurrentWeekTotoalMaintenenceByMachine(@RequestParam("machineName") String machineName) {
		List<Maint> totoal= new ArrayList<Maint>();
		try {
	
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startOfWeek = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startOfWeek));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endOfWeek = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endOfWeek));
			
			

		     
		        
		     
		        
			
			List<Maint> openMaintList=maintServices.getCurrentWeekOpenMaintenanceByMachineName(startOfWeek, endOfWeek, machineName);
			List<Maint> overduesMaintList=maintServices.getCurrentWeekOverudesMaintenanceByMachineName(startOfWeek,machineName);
			List<Maint> closedMaintList=maintServices.getCurrentWeekClosedMaintenanceByMachineName(startOfWeek, endOfWeek,machineName);
			
			
			totoal.addAll(openMaintList);
			totoal.addAll(overduesMaintList);
			totoal.addAll(closedMaintList);
			int i=1;
			   for (Maint maint:totoal) {
				     calendar.setTime(maint.getSchedule_date());
		            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
 maint.setSrNo(i);
		            
		            if(maint.getRaisedBy()!=null) {
			            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

		            }
		            if(maint.getDone_by()!=null) {
		            	maint.setDoneName(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName());
		            }
		            i++;
		        	maint.setWeek(weekOfYear);
		        	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyyy");  
			            String strDate = dateFormat.format(maint.getSchedule_date()); 
			            maint.setScheduleDateStr(strDate);
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return totoal;
	}
	  public static Date[] getStartAndEndDatesOfWeek(int year, int weekNumber) {
	        // Create a Calendar instance
	        Calendar calendar = Calendar.getInstance();

	        // Set the year and week number
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);

	        // Set the day to the first day of the week
	        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
	        Date startDate = calendar.getTime();

	        // Add 6 days to get the end date of the week
	        calendar.add(Calendar.DAY_OF_WEEK, 6);
	        Date endDate = calendar.getTime();

	        // Return the start and end dates
	        return new Date[] { startDate, endDate };
	    }
	  
	  
	@GetMapping(value = "/get52WeekMaintenence", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MachineWeekDTO> get52WeekMaintenence(@RequestParam("weekNo") int weekNo) {
		List<MachineWeekDTO> list= new ArrayList<MachineWeekDTO>();
		try {
			Calendar calendar = Calendar.getInstance();

			 int year = calendar.get(Calendar.YEAR);
		        int weekNumber = weekNo;  // Specify the week number (1-52 or 1-53, depending on the year)

		        // Get the first day of the year
		        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);

		        // Create WeekFields for the given locale (e.g., default locale)
		        WeekFields weekFields = WeekFields.of(Locale.getDefault());

		        // Get the start date of the week
		        LocalDate startOfWeek = firstDayOfYear
		            .with(weekFields.weekOfYear(), weekNumber)
		            .with(weekFields.dayOfWeek(), 1);  // Monday as the start of the week

		        // Get the end date of the week (Sunday)
		        LocalDate endOfWeek = startOfWeek.plusDays(6);  // Adding 6 days to get Sunday

		        // Convert to java.util.Date
		        Date startDate = java.sql.Date.valueOf(startOfWeek);
		        Date endDate = java.sql.Date.valueOf(endOfWeek);

			Set<String> machineNames = new HashSet<String>();
			List<Machine> machineList = machineServices.getMachineList();
			

			for(Machine machine: machineList){
				machineNames.add(machine.getMachine_name());
				 	     
			}			
			for(String machineName:machineNames ) {
				MachineWeekDTO machineWeekDTO= new MachineWeekDTO();
				
				List<WeekDataDTO> dataDTOs= new ArrayList<WeekDataDTO>();
				

				
					 WeekDataDTO weekDataDTO= new WeekDataDTO();
				
				        List<Maint> openMaintList=maintServices.getCurrentWeekOpenMaintenanceByMachineName(startDate, endDate,machineName);
					
						List<Maint> closedMaintList=maintServices.getCurrentWeekClosedMaintenanceByMachineName(startDate, endDate,machineName);
					 
					 System.out.println("START DATE :: "+startDate);
					 System.out.println("END DATE :: "+endDate);
					 
					 System.out.println("Open :: "+openMaintList.size());
					 
					 System.out.println("Closed:: "+closedMaintList.size());
					 weekDataDTO.setClosed(closedMaintList.size());
					 weekDataDTO.setOpen(openMaintList.size());
					 weekDataDTO.setWeekNo(weekNo);
					 dataDTOs.add(weekDataDTO);
					 System.out.println("-----------------------------------------------------------------");
				 	
				
				 machineWeekDTO.setMachineName(machineName);
				 machineWeekDTO.setWeekDatas(dataDTOs);
				 list.add(machineWeekDTO);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return list;
	}
	
	
	
	
	@GetMapping(value = "/get52WeekMaintenenceByLab", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MachineWeekDTO> get52WeekMaintenenceByLab(@RequestParam("weekNo") int weekNo, @RequestParam("labName") String labName) {
	    List<MachineWeekDTO> list = new ArrayList<>();
	    try {
	        Calendar calendar = Calendar.getInstance();
	        int year = calendar.get(Calendar.YEAR);

	        // Get the first day of the specified week
	        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
	        WeekFields weekFields = WeekFields.of(Locale.getDefault());
	        LocalDate startOfWeek = firstDayOfYear.with(weekFields.weekOfYear(), weekNo).with(weekFields.dayOfWeek(), 1);
	        LocalDate endOfWeek = startOfWeek.plusDays(6);

	        // Convert to java.util.Date
	        Date startDate = java.sql.Date.valueOf(startOfWeek);
	        Date endDate = java.sql.Date.valueOf(endOfWeek);

	        Set<String> machineNames = new HashSet<>();
	        List<Machine> machineList = machineServices.getMachineList();

	        for (Machine machine : machineList) {
	            machineNames.add(machine.getMachine_name());
	        }

	        for (String machineName : machineNames) {
	            MachineWeekDTO machineWeekDTO = new MachineWeekDTO();
	            List<WeekDataDTO> dataDTOs = new ArrayList<>();
	            WeekDataDTO weekDataDTO = new WeekDataDTO();

	            // Fetch maintenance filtered by machine and labName
	            List<Maint> openMaintList = Maintrepo.getCurrentWeekOpenMaintenanceByMachineAndLab(startDate, endDate, machineName, labName);
	            List<Maint> closedMaintList = Maintrepo.getCurrentWeekClosedMaintenanceByMachineAndLab(startDate, endDate, machineName, labName);

	            System.out.println("START DATE :: " + startDate);
	            System.out.println("END DATE :: " + endDate);
	            System.out.println("Lab Name :: " + labName);
	            System.out.println("Open :: " + openMaintList.size());
	            System.out.println("Closed :: " + closedMaintList.size());

	            weekDataDTO.setClosed(closedMaintList.size());
	            weekDataDTO.setOpen(openMaintList.size());
	            weekDataDTO.setWeekNo(weekNo);
	            dataDTOs.add(weekDataDTO);

	            machineWeekDTO.setMachineName(machineName);
	            machineWeekDTO.setWeekDatas(dataDTOs);
	            list.add(machineWeekDTO);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	
	@GetMapping(value = "/getMaintenanceDatailsByWeekAndMachienName", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> getMaintenanceDatailsByWeekAndMachienName(@RequestParam("week") int week,@RequestParam("machineName") String machineName,@RequestParam("status") String status) {
		List<Maint> totoal= new ArrayList<Maint>();
		try {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			  Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
			  Date startDate = startEndDates[0];
		        Date endDate = startEndDates[1];
		        int statusCode;
		        if(status.equalsIgnoreCase("Open")){
					List<Maint> openMaintList=maintServices.getCurrentWeekOpenMaintenanceByMachineName(startDate, endDate,machineName);
					totoal.addAll(openMaintList);

		        }else {
					List<Maint> closedMaintList=maintServices.getCurrentWeekClosedMaintenanceByMachineName(startDate, endDate,machineName);
					totoal.addAll(closedMaintList);

		        }
			
			int i=1;
			  for (Maint maint:totoal) {
				     calendar.setTime(maint.getSchedule_date());
		            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		            maint.setSrNo(i);
		            
		            if(maint.getRaisedBy()!=null) {
			            maint.setRaisedName(maint.getRaisedBy().getFirstName()+""+maint.getRaisedBy().getLastName());

		            }
		            if(maint.getDone_by()!=null) {
		            	maint.setDoneName(maint.getDone_by().getFirstName()+" "+maint.getDone_by().getLastName());
		            }
		            i++;
		        	maint.setWeek(weekOfYear);
					
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return totoal;
	}
	
	
	
	
	
	
	
		
	
	 @GetMapping(value = "/export/TotalMaintenance", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> exportMaintenance(
	            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        
	        System.out.println("Exporting maintenance data...");
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Maintenance Data");
	        int srNo = 1; // Initialize the serial number

	        try {
	            if (fromDate != null && toDate != null) {
	                System.out.println("Fetching maintenance data between dates: " + fromDate + " and " + toDate);
	                List<Maint> maintenanceList = Maintrepo.findByScheduleDateBetween(fromDate, toDate);
	                
	                System.out.println("Records found: " + maintenanceList.size());

	                // Create header row
	                Row headerRow = sheet.createRow(0);
	                String[] columns = {"SrNo", "Maintenance Eqid", "Machine Name", "Scheduled Date", "Raised By", "Location", "Frequency"};
	                for (int i = 0; i < columns.length; i++) {
	                    headerRow.createCell(i).setCellValue(columns[i]);
	                }

	                int rowNum = 1;
	                for (Maint maint : maintenanceList) {
	                    Row row = sheet.createRow(rowNum++);
	                    row.createCell(0).setCellValue(srNo++);
	                    row.createCell(1).setCellValue(maint.getMachine() != null ? maint.getMachine().getEqid() : "N/A");
	                    row.createCell(2).setCellValue(maint.getMachine() != null ? maint.getMachine().getMachine_name() : "N/A");
	                    row.createCell(3).setCellValue(maint.getSchedule_date() != null ?
	                            new SimpleDateFormat("dd-MM-yyyy").format(maint.getSchedule_date()) : "N/A");
	                    row.createCell(4).setCellValue(maint.getRaisedBy() != null ?
	                            maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName() : "N/A");
	                    row.createCell(5).setCellValue(maint.getMachine() != null ? maint.getMachine().getLocation() : "N/A");
	                    row.createCell(6).setCellValue(maint.getFrequency() != null ? maint.getFrequency() : "N/A");
	                }

	            } else {
	                System.out.println("Fetching overdue, open, and closed maintenance records.");

	                Date currentDate = new Date();
	                List<Maint> overdueRecords = Maintrepo.getOverduesMaintenaceByDates(currentDate);
	                List<Maint> openRecords = Maintrepo.getOpeneMaintenancesByDate(currentDate);
	                List<Maint> closedRecords = Maintrepo.getClosedMaintenances();

	                System.out.println("Overdue records: " + overdueRecords.size());
	                System.out.println("Open records: " + openRecords.size());
	                System.out.println("Closed records: " + closedRecords.size());

	                Row headerRow = sheet.createRow(0);
	                String[] columns = {"SrNo", "Status", "Maintenance Eqid", "Machine Name", "Scheduled Date", "Raised By", "Location", "Frequency"};
	                for (int i = 0; i < columns.length; i++) {
	                    headerRow.createCell(i).setCellValue(columns[i]);
	                }

	                int rowNum = 1;
	                for (Maint maint : overdueRecords) {
	                    createDataRow(sheet, maint, rowNum++, srNo++, "Overdue");
	                }
	                for (Maint maint : openRecords) {
	                    createDataRow(sheet, maint, rowNum++, srNo++, "Open");
	                }
	                for (Maint maint : closedRecords) {
	                    createDataRow(sheet, maint, rowNum++, srNo++, "Closed");
	                }
	            }

	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            workbook.write(byteArrayOutputStream);
	            workbook.close();

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Content-Disposition", "attachment; filename=MaintenanceData.xlsx");

	            System.out.println("File successfully generated.");
	            return ResponseEntity.ok().headers(headers).body(byteArrayOutputStream.toByteArray());

	        } catch (Exception e) {
	            System.err.println("Error occurred during export: " + e.getMessage());
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    private void createDataRow(Sheet sheet, Maint maint, int rowNum, int srNo, String status) {
	        Row row = sheet.createRow(rowNum);
	        row.createCell(0).setCellValue(srNo);
	        row.createCell(1).setCellValue(status);
	        row.createCell(2).setCellValue(maint.getMachine() != null ? maint.getMachine().getEqid() : "N/A");
	        row.createCell(3).setCellValue(maint.getMachine() != null ? maint.getMachine().getMachine_name() : "N/A");
	        row.createCell(4).setCellValue(maint.getSchedule_date() != null ?
	                new SimpleDateFormat("dd-MM-yyyy").format(maint.getSchedule_date()) : "N/A");
	        row.createCell(5).setCellValue(maint.getRaisedBy() != null ?
	                maint.getRaisedBy().getFirstName() + " " + maint.getRaisedBy().getLastName() : "N/A");
	        row.createCell(6).setCellValue(maint.getMachine() != null ? maint.getMachine().getLocation() : "N/A");
	        row.createCell(7).setCellValue(maint.getFrequency() != null ? maint.getFrequency() : "N/A");
	    }
	
	
	
}
