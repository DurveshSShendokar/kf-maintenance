package com.kfMaintenancce.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import com.kfMaintenancce.dto.BreakDownUpdateMobileApp;
import com.kfMaintenancce.dto.Break_DashboardDTO;
import com.kfMaintenancce.dto.BreakdownRepoCountDTO;
import com.kfMaintenancce.dto.BreakdownReportDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTOMob;
import com.kfMaintenancce.dto.BreakdownStatusCounts;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.MachineWeekDTO;
import com.kfMaintenancce.dto.SpareConsumptionReportDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.StatusCounts;
import com.kfMaintenancce.dto.WeekDataDTO;
import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.BreakdownAttendee;
import com.kfMaintenancce.model.BreakdownImage;
import com.kfMaintenancce.model.BreakdownSpare;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineMaintSpare;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownAttendeeRepo;
import com.kfMaintenancce.repo.BreakdownImageRepo;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.BreakdownSpareRepo;
import com.kfMaintenancce.repo.BreakdownupdateRepo;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MachineSpareAssignRepository;
import com.kfMaintenancce.repo.MaintSpareRepo;
import com.kfMaintenancce.repo.MaintSpareStockRepo;
import com.kfMaintenancce.repo.MaintUtilizedSpareRepo;
import com.kfMaintenancce.repo.MaintsparestockingRepo;
import com.kfMaintenancce.repo.NotificationRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.BreakdownupdateServices;
import com.kfMaintenancce.service.MachineServices;


@RestController
@CrossOrigin("*")
@RequestMapping("/breakdown")
public class BreakdownController {

	@Autowired
	BreakdownServices breakdownServices; 
	
	@Autowired 
	BreakdownAttendeeRepo breakdownAttendeeRepo;
	
	@Autowired
	BreakdownupdateServices breakdownUpdateServices;
	@Autowired
	NotificationRepo notificationRepo;
	@Autowired
	MachineServices machineServices;
	@Autowired
	UserDetailsRepo userDetailsRepo;
	@Autowired
	MaintUtilizedSpareRepo maintUtilizedSpareRepo;
	@Autowired
	BreakdownSpareRepo breakdownSpareRepo;
	
	@Autowired
	MaintSpareRepo maintSpareRepo;
	
	@Autowired
	LabRepo labRepository;
	
	@Autowired
    MachineOwnerRepo machineOwnerRepo;

	
	@Autowired
	MaintSpareStockRepo maintSpareStockRepo;
	
	@Autowired
	MaintsparestockingRepo maintsparestockingRepo;
	

@Autowired
BreakdownRepo breakdownRepo; 

@Autowired
MachineSpareAssignRepository machineSpareAssignRepo; 

@Autowired
BreakdownupdateRepo breakdownupdateRepo; //


@Autowired
LabRepo labRepo; 

@Autowired
MachineRepo machineRepo; 


@Autowired
BreakdownImageRepo breakdownImageRepo;


@GetMapping("/getBreakdownByLimit")
public @ResponseBody List<Breakdown> getAssetAllocationByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Breakdown> list = new ArrayList<Breakdown>();
	try {
		list = breakdownRepo.getBreakdownByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}

@GetMapping("/geBreakdownCount")
public @ResponseBody int getBreakdownCount() {
	int count = 0;
	try {
			count = (int) breakdownRepo.count();

	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@RequestMapping(value = "/getBreakdownByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Breakdown> getBreakdownByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Breakdown> list = new ArrayList<Breakdown>();
	try {
		
		list = breakdownRepo.getBreakdownByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getBreakdownCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getAssetAllocationCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = breakdownRepo.getBreakdownCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}

// Upload Images
@PostMapping("/uploadImages/{breakdownId}")
public ResponseEntity<?> uploadBreakdownImages(
        @PathVariable int breakdownId,
        @RequestParam("files") MultipartFile[] files) {

    try {
        Breakdown breakdown = breakdownRepo.findById(breakdownId)
                .orElseThrow(() -> new EntityNotFoundException("Breakdown not found with ID: " + breakdownId));

        String uploadDir = "uploads/breakdown_images/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        List<Map<String, Object>> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                BreakdownImage breakdownImage = new BreakdownImage();
                breakdownImage.setFileName(fileName);
                breakdownImage.setFilePath(filePath.toString());
                breakdownImage.setFileType(file.getContentType());
                breakdownImage.setBreakdown(breakdown);

                System.out.println("*****************************************************************###################File Upload :: " + fileName );
                
                BreakdownImage savedImage = breakdownImageRepo.save(breakdownImage);

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
       // response.put("uploadedFiles", uploadedFiles);

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", "Upload failed: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

// Download Image
@GetMapping("/downloadImage")
public ResponseEntity<Resource> downloadBreakdownImage(@RequestParam int imageId) throws MalformedURLException {
    try {
        BreakdownImage image = breakdownImageRepo.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " + imageId));

        Path filePath = Paths.get(image.getFilePath()).normalize();
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            return ResponseEntity.notFound().build();
        }

        UrlResource resource = new UrlResource(filePath.toUri());
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
    } catch (IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

// Get Images by BreakdownId
@GetMapping("/getImages/{breakdownId}")
public ResponseEntity<?> getBreakdownImagesByBreakdownId(@PathVariable int breakdownId) {
    try {
        List<BreakdownImage> images = breakdownImageRepo.getImagesByBreakdownId(breakdownId);
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No images found for Breakdown ID: " + breakdownId);
        }
        return ResponseEntity.ok(images);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching images: " + e.getMessage());
    }
}




@PostMapping(value = "/create")
public @ResponseBody Status addBreakdown(@RequestBody Breakdown breakdown) {
    Status status = new Status();
    try {
        System.out.println("BREAK DOWN ADD:: " + breakdown.getAddedBy());

        String bdNo = breakdownServices.getNewBreakDownNo();

        // Validate and set lab
        if (breakdown.getLab() != null && breakdown.getLab().getLab_id() != 0) {
            Lab lab = labRepository.findById(breakdown.getLab().getLab_id())
                    .orElseThrow(() -> new RuntimeException("Lab not found"));
            breakdown.setLab(lab);
        } else {
            throw new RuntimeException("Lab information is missing in the request");
        }

        Machine machine = machineRepo.findById(breakdown.getMachine().getMachine_id())
                .orElseThrow(() -> new RuntimeException("Machine not found"));
        breakdown.setMachine(machine);
        breakdown.setTicket_raised_time(new Date());
        SimpleDateFormat f = new SimpleDateFormat("MMM");
        String m2 = f.format(breakdown.getTicket_raised_time());
        breakdown.setTr_month(m2);

       
        breakdown.setBd_slip(bdNo);
        breakdown.setTc_tr_hr(0);
        breakdown.setTotal_trial_hr(0);
        breakdown.setTicket_closed_flag(0);
        breakdown.setStatus(1);
        breakdown.setDeletes(1);

       
        System.out.println(breakdown.toString());
        breakdownServices.addBreakdown(breakdown);

       
        List<MachineOwner> machineOwners = machineOwnerRepo.findByMachine(breakdown.getMachine());
        for (MachineOwner owner : machineOwners) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            UserDetails user = userDetailsRepo.findById(breakdown.getAddedBy().getId())
            	    .orElseThrow(() -> new RuntimeException("User not found"));
            	breakdown.setAddedBy(user);
            String createdBy = user.getFirstName() + " " + user.getLastName();

            String raisedTimeStr = sdf.format(new Date());

            String message = "New Breakdown has been created with Breakdown Slip No: "
            	    + breakdown.getBd_slip() + " for Machine "
            	    + breakdown.getMachine().getMachine_name() + "-"
            	    + breakdown.getMachine().getEqid() + " by " + createdBy
            	    + " on " + raisedTimeStr;


            Notification notification = new Notification();
            notification.setNotificationDept("Maintenance");
            notification.setNotificationFor("OWNER");
            notification.setNotificationForSepeId(owner.getUser().getId());
            notification.setMachine(breakdown.getMachine());
            notification.setMessage(message);
            notification.setRaisedTime(new Date());
            notification.setTitle("New Breakdown Created");
            notification.setViewed(0);

            notificationRepo.save(notification);
        }

        status.setCode(200);
        status.setMessage("Breakdown added successfully");

    } catch (Exception e) {
        e.printStackTrace();
        status.setCode(500);
        status.setMessage("Error while adding breakdown: " + e.getMessage());
    }

    return status;
}

	
	
	

	
	@PostMapping(value = "/updateBreakdownMobile/{updateBy}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status updateBreakdownMobile(@PathVariable int updateBy, @RequestBody BreakDownUpdateMobileApp breakDownUpdateMobileApp) {
	    try {
	        System.out.println("Call Create MOBIWEL: " + breakDownUpdateMobileApp.toString());
	        System.out.println("Action Taken : " + breakDownUpdateMobileApp.getActionTakenBy());

	        Breakdown breakdown = breakdownServices.getBreakdownById(breakDownUpdateMobileApp.getBreakdown().getBreakdown_id());

	
	        if (breakdown.getStatus() != 1) {
	            return new Status("Breakdown cannot be updated. It is already updated (statusCode = " + breakdown.getStatus() + ").");
	        }

	        Optional<UserDetails> updateByUser = userDetailsRepo.findById(updateBy);
	        if (!updateByUser.isPresent()) {
	            return new Status("Invalid user ID for updateBy: " + updateBy);
	        }
	        UserDetails updatedByUser = updateByUser.get();

	        // ✅ Track attended users
	        BreakdownAttendee attendee = new BreakdownAttendee();
	        attendee.setBreakdown(breakdown);
	        attendee.setUser(updatedByUser);
	        attendee.setAttendedTime(new Date());
	        attendee.setType("UpdateBreakdown");
	        breakdown.getAttendees().add(attendee);

	 

	        breakdown.setTicket_trial_time(new Date());
	        breakdown.setAction_taken(breakDownUpdateMobileApp.getActionTaken());
	        breakdown.setPrev_action_plan(breakDownUpdateMobileApp.getPreventiveactionPlan());
	        breakdown.setRoot_cause(breakDownUpdateMobileApp.getRootClause());
	        breakdown.setStatus(2); 
	        breakdown.setUpdateBy(updatedByUser);
	        breakdown.setShowBit(1); 
	        
	        String fullName = updatedByUser.getFirstName() + " " + updatedByUser.getLastName();
	        String existingUsers = breakdown.getAttendedUsers();

	        if (existingUsers == null || existingUsers.trim().isEmpty()) {
	            breakdown.setAttendedUsers(fullName);
	        } else {
	            breakdown.setAttendedUsers(existingUsers + ", " + fullName);
	        }


	        breakdown.setSpare_used(breakDownUpdateMobileApp.getSpares());
	        breakdownServices.addBreakdown(breakdown);

	        
	        Breakdownupdate breakdownupdate = new Breakdownupdate();
	        breakdownupdate.setAction_by(updatedByUser.getFirstName()+" "+updatedByUser.getLastName());
	        breakdownupdate.setAction_taken(breakDownUpdateMobileApp.getActionTaken());
	        breakdownupdate.setBreakdown_date(breakdown.getTicket_raised_time());
	        breakdownupdate.setDeletes(1);
	        breakdownupdate.setShift(breakDownUpdateMobileApp.getBreakdown().getShift());
	        breakdownupdate.setObservation(breakDownUpdateMobileApp.getBreakdown().getObservation());
	        breakdownupdate.setPrev_action_plan(breakDownUpdateMobileApp.getPreventiveactionPlan());
	        breakdownupdate.setRoot_cause(breakDownUpdateMobileApp.getRootClause());
	        breakdownupdate.setBreakdown(breakdown);
	        breakdownupdate.setMachine(breakDownUpdateMobileApp.getBreakdown().getMachine());
	        breakdownupdate.setShift(breakDownUpdateMobileApp.getShift());
	        
	        breakdownUpdateServices.addBreakdownupdate(breakdownupdate);


	        Machine machine = machineRepo.findById(breakdown.getMachine().getMachine_id()).orElse(null);
	        breakdown.setMachine(machine);
	        
	        List<UserDetails> machineOwners = userDetailsRepo.findMachineOwnersByMachineId(breakdown.getMachine().getMachine_id());
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        String raisedTimeStr = sdf.format(new Date());

	       
	        String message = "Breakdown : " + breakdown.getBd_slip() +
	                " for Machine " + breakdown.getMachine().getMachine_name() +
	                " - " + breakdown.getMachine().getEqid() +
	                " has been updated by " + updatedByUser.getFirstName() + " " + updatedByUser.getLastName() +
	                " on " + raisedTimeStr;



	        for (UserDetails owner : machineOwners) {
	            Notification notification = new Notification();
	            notification.setNotificationDept("Maintenance");
	            notification.setNotificationFor("OWNER");
	            notification.setMachine(breakdown.getMachine());
	            notification.setNotificationForSepeId(owner.getId());
	            notification.setMessage(message);
	            notification.setRaisedTime(new Date());
	            notification.setTitle("Breakdown Updated");
	            notification.setViewed(0);
	            notificationRepo.save(notification);
	        }
	        return new Status("Breakdown updated by " + updatedByUser.getFirstName() + " " + updatedByUser.getLastName() + " and notification sent successfully!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status(e.toString());
	    }
	}
	
	
	
	
	@PostMapping(value = "/startBreakdownWork")
	public ResponseEntity<?> startBreakdownWork(
	        @RequestParam("breakdownId") int breakdownId,
	        @RequestParam("attendBy") int attendBy) {
	    try {
	        Breakdown breakdown = breakdownServices.getBreakdownById(breakdownId);
	        if (breakdown == null) {
	            return ResponseEntity.badRequest().body(Map.of(
	                "success", false,
	                "message", "Invalid Breakdown ID: " + breakdownId
	            ));
	        }

	        Optional<UserDetails> userOpt = userDetailsRepo.findById(attendBy);
	        if (userOpt.isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of(
	                "success", false,
	                "message", "Invalid User ID: " + attendBy
	            ));
	        }

	        UserDetails user = userOpt.get();
	        String fullName = user.getFirstName() + " " + user.getLastName();

	        // ✅ Set start time ONLY for the first user
	        if (breakdown.getActualWorkingStartTime() == null) {
	            breakdown.setActualWorkingStartTime(new Date());
	        }

	        // ✅ Append user name (don’t overwrite previous names)
	        String existingUsers = breakdown.getAttendedUsers();
	        if (existingUsers == null || existingUsers.trim().isEmpty()) {
	            breakdown.setAttendedUsers(fullName);
	        } else if (!existingUsers.contains(fullName)) { // prevent duplicate names
	            breakdown.setAttendedUsers(existingUsers + ", " + fullName);
	        }

	        // ✅ Add attendee record (for history / auditing)
	        BreakdownAttendee attendee = new BreakdownAttendee();
	        attendee.setBreakdown(breakdown);
	        attendee.setUser(user);
	        attendee.setAttendedTime(new Date());
	        attendee.setType("AttendBreakdown");
	        breakdown.getAttendees().add(attendee);

	        breakdownServices.addBreakdown(breakdown);

	        return ResponseEntity.ok(Map.of(
	            "success", true,
	            "message", "Work started/attended for breakdown " + breakdown.getBd_slip(),
	            "breakdownSlip", breakdown.getBd_slip(),
	            "attendedUsers", breakdown.getAttendedUsers(),   // now shows all users
	            "startTime", breakdown.getActualWorkingStartTime()
	        ));

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body(Map.of(
	            "success", false,
	            "message", e.getMessage()
	        ));
	    }
	}



	@PostMapping("/updateBreakdown")
	public ResponseEntity<?> updateBreakdown1(@RequestBody Breakdown breakdown) {
	    try {
	        System.out.println("Update ......." + breakdown.toString());
	        Optional<Breakdown> optionalBreakdown = breakdownRepo.findById(breakdown.getBreakdown_id());
	        if (optionalBreakdown.isPresent()) {
	            Breakdown existingBreakdown = optionalBreakdown.get();

	            if (existingBreakdown.getActualWorkingStartTime() == null) {
	                existingBreakdown.setActualWorkingStartTime(new Date());
	            }
	            
	            if (existingBreakdown.getStatus() == 3 && breakdown.getStatus() != 3) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Breakdown is already closed and cannot be changed to another status.");
	            }

	          
	            existingBreakdown.setAction_taken(breakdown.getAction_taken());
	            existingBreakdown.setRoot_cause(breakdown.getRoot_cause());
	            existingBreakdown.setPrev_action_plan(breakdown.getPrev_action_plan());
	            
	            

	            if (breakdown.getSpare() != null && !breakdown.getSpare().isEmpty()) {
	                for (MaintSpare maintSpare : breakdown.getSpare()) {
	                    Optional<MaintSpareStock> optionalStock = maintSpareStockRepo.getMaintSpareStockByMaintSpare(maintSpare.getMaintspare_id());
	                    if (optionalStock.isPresent()) {
	                        MaintSpareStock maintSpareStock = optionalStock.get();

	                        if (maintSpareStock.getAvailableQuantity() < maintSpare.getQuantity()) {
	                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                    .body("Insufficient stock for spare: " + maintSpare.getSpare_name() +
	                                            ". Available quantity: " + maintSpareStock.getAvailableQuantity());
	                        }

	                        maintSpareStock.setAvailableQuantity(maintSpareStock.getAvailableQuantity() - maintSpare.getQuantity());
	                        maintSpareStockRepo.save(maintSpareStock);

	                        BreakdownSpare breakdownSpare = new BreakdownSpare();
	                        breakdownSpare.setBreakdown(existingBreakdown);
	                        breakdownSpare.setConsumptionDate(new Date());
	                        breakdownSpare.setSpare(maintSpare);
	                        breakdownSpare.setConsumedquantity(maintSpare.getQuantity());

	                        Optional<BreakdownSpare> optional = breakdownSpareRepo.getBreakdownSpareByBreakdownIDAndMaintSpareId(breakdown.getBreakdown_id(), maintSpare.getMaintspare_id());
	                        if (optional.isPresent()) {
	                            BreakdownSpare existingConsumption = optional.get();
	                            existingConsumption.setConsumedquantity(existingConsumption.getConsumedquantity() + maintSpare.getQuantity());
	                            breakdownSpareRepo.save(existingConsumption);
	                        } else {
	                            breakdownSpareRepo.save(breakdownSpare);
	                        }
	                    } else {
	                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                .body("Stock not found for spare: " + maintSpare.getSpare_name());
	                    }
	                }
	            }

	            if (breakdown.getStatus() == 3) {
	                existingBreakdown.setTicket_closed_time(new Date());
	            }

	            breakdownRepo.save(existingBreakdown);
	            return ResponseEntity.ok(existingBreakdown);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("Breakdown not found.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to update Breakdown: " + e.getMessage());
	    }
	}

	
	@GetMapping(value = "/list")
	public @ResponseBody List<Breakdown> getBreakdowns() {
		List<Breakdown> breakdownList = null;
		try {
			
			breakdownList = breakdownServices.getBreakdownList();
			for(Breakdown breakdown: breakdownList) {
				
			List<Breakdownupdate> breakdwnUpdates=breakdownServices.getBrekdownUpdatesByBreakdown(breakdown.getBd_slip());
					String extingAction="";
					String extingRootC=breakdown.getRoot_cause();
					if(breakdwnUpdates.size()!=0) {
						for(Breakdownupdate breakUpdate:breakdwnUpdates) {
							if(extingAction.equalsIgnoreCase("")) {
								extingAction+=breakUpdate.getAction_taken();
							}else {
								extingAction+=" , "+breakUpdate.getAction_taken();
							}
							
						}
					}
					breakdown.setExtingAction(extingAction);
					breakdown.setExtingRootC(extingRootC);
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
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
	@GetMapping(value = "/getBreakDownForWeek")
	public @ResponseBody
	List<Breakdown> getBreakDownForWeek(){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			breakdownList = breakdownServices.getBreakdownByDates(startDate,endDate);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	
	
	
		
	
	
	@GetMapping(value = "/getBreakDownForWeekByMachien")
	public @ResponseBody
	List<Breakdown> getBreakDownForWeekByMachien(@RequestParam("machineName") String machineName){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			breakdownList = breakdownServices.getBreakdownByDatesAndMachineName(startDate,endDate,machineName);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	@GetMapping(value = "/getOpenBreakDownForWeek")
	public @ResponseBody
	List<Breakdown> getOpenBreakDownForWeek(){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			
			breakdownList = breakdownServices.getOpenBreakdownByDates(startDate,endDate);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	@GetMapping(value = "/getOpenBreakDownForWeekByMachien")
	public @ResponseBody
	List<Breakdown> getOpenBreakDownForWeekByMachien(@RequestParam("machineName") String machineName){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			
			breakdownList = breakdownServices.getOpenBreakdownByDatesAndMachine(startDate,endDate,machineName);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	@GetMapping(value = "/getClosedBreakDownForWeek")
	public @ResponseBody
	List<Breakdown> getClosedBreakDownForWeek(){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			breakdownList = breakdownServices.getClosedBreakdownByDates(startDate,endDate);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Closed");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				String ttr="";
				DecimalFormat decfor = new DecimalFormat("0.00000");  
				if(breakdown.getTc_tr_hr()<=0) {
					ttr="0";
				}else {
				 ttr=decfor.format(breakdown.getTotal_trial_hr());
				}
			//	System.out.println("ttr........................................... "+ttr);
				System.out.println("ttr "+ttr);
				breakdown.setTtr(Double.valueOf(ttr));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	@GetMapping(value = "/getClosedBreakDownForWeekByMachien")
	public @ResponseBody
	List<Breakdown> getClosedBreakDownForWeekByMachien(@RequestParam("machineName") String machineName){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			breakdownList = breakdownServices.getClosedBreakdownByDatesAndMachine(startDate,endDate,machineName);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				String ttr="";
				DecimalFormat decfor = new DecimalFormat("0.00000");  
				if(breakdown.getTc_tr_hr()<=0) {
					ttr="0";
				}else {
				 ttr=decfor.format(breakdown.getTotal_trial_hr());
				}
			//	System.out.println("ttr........................................... "+ttr);
				System.out.println("ttr "+ttr);
				breakdown.setTtr(Double.valueOf(ttr));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	
	
	@GetMapping(value = "/getTrailBreakDownForUser")
	public @ResponseBody
	List<Breakdown> getTrailBreakDownForUser(@RequestParam("userId") int userId){
		List<Breakdown> breakdownList = null;
		try {
			
			breakdownList = breakdownServices.getTrailBreakdownByUser(userId);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
				if(breakdown.getRoot_cause()==null ||breakdown.getAction_taken()==null) {
					System.out.println("ROOO IS NULL ");
					List<Breakdownupdate> breakdownupdates=breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown.getBreakdown_id());
					breakdown.setRoot_cause(breakdownupdates.get(0).getRoot_cause());
					breakdown.setAction_taken(breakdownupdates.get(0).getAction_taken());
					breakdown.setPrev_action_plan(breakdownupdates.get(0).getPrev_action_plan());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	@GetMapping(value = "/getTrailBreakDownForWeek")
	public @ResponseBody
	List<Breakdown> getTrailBreakDownForWeek(){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			breakdownList = breakdownServices.getTrailBreakdownByDates(startDate,endDate);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
				if(breakdown.getRoot_cause()==null ||breakdown.getAction_taken()==null) {
					System.out.println("ROOO IS NULL ");
					List<Breakdownupdate> breakdownupdates=breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown.getBreakdown_id());
					breakdown.setRoot_cause(breakdownupdates.get(0).getRoot_cause());
					breakdown.setAction_taken(breakdownupdates.get(0).getAction_taken());
					breakdown.setPrev_action_plan(breakdownupdates.get(0).getPrev_action_plan());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	@GetMapping(value = "/getTrailBreakDownForWeekByMachie")
	public @ResponseBody
	List<Breakdown> getTrailBreakDownForWeekByMachie(@RequestParam("machineName") String machineName){
		List<Breakdown> breakdownList = null;
		try {
			Calendar calendar = Calendar.getInstance();

			// Set the calendar to the start of the week (Sunday)
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			Date startDate = calendar.getTime();

			// Format the date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Start of the week: " + sdf.format(startDate));

			// Set the calendar to the end of the week (Saturday)
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			Date endDate = calendar.getTime();
			System.out.println("End of the week: " + sdf.format(endDate));
			breakdownList = breakdownServices.getTrailBreakdownByDatesAndMachine(startDate,endDate,machineName);
			int i=1;
			for(Breakdown breakdown:breakdownList) {
				breakdown.setRaisedName(breakdown.getAddedBy().getFirstName()+" "+breakdown.getAddedBy().getLastName());
				if(breakdown.getStatus()==1) {
					breakdown.setStatusStr("Waiting for Maintenance");
				}
				if(breakdown.getStatus()==2) {
					breakdown.setStatusStr("Waiting for Trial");
				}
				if(breakdown.getStatus()==3) {
					breakdown.setStatusStr("Approved");
				}
				if(breakdown.getStatus()==4) {
					breakdown.setStatusStr("Approved with deviation");
				}
				breakdown.setSrNo(i);
				i++;
				
				if(breakdown.getRoot_cause()==null ||breakdown.getAction_taken()==null) {
					System.out.println("ROOO IS NULL ");
					List<Breakdownupdate> breakdownupdates=breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown.getBreakdown_id());
					breakdown.setRoot_cause(breakdownupdates.get(0).getRoot_cause());
					breakdown.setAction_taken(breakdownupdates.get(0).getAction_taken());
					breakdown.setPrev_action_plan(breakdownupdates.get(0).getPrev_action_plan());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	@GetMapping(value = "/listByUserId")
	public @ResponseBody
	List<Breakdown> listByUserId(@RequestParam("id") int id) {
		List<Breakdown> breakdownList = null;
		try {
			
			breakdownList = breakdownServices.listByUserId(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownList;
	}
	
	
	
	@GetMapping(value= "/delete/{breakdown_id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	
	Status deleteCategory(@PathVariable("breakdown_id") int breakdown_id){
		try{
			breakdownServices.deleteBreakdown(breakdown_id);
			return new Status("Breakdown Deleted Successfully !");
		} catch(Exception e){
			return new Status(e.toString());
		}
	}
	

	
	
	/////////////////--- open Breakdowns-----////////////////
	
	  @GetMapping("/open")
	    public @ResponseBody List<Breakdown> getOpenBreakdowns() {
	        try {
	            return breakdownServices.getOpenBreakdowns();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	  @GetMapping("/getUtilizedSpare")
	    public @ResponseBody List<BreakdownSpare> getUtilizedSpare(@RequestParam("breakdownId") int breakdownId) {
		  List<BreakdownSpare> sparelist=new ArrayList<BreakdownSpare>();
	       
		  try {
	        	
	        	
			  sparelist=breakdownSpareRepo.getBreakdownSpareByBreakdownId(breakdownId);
	            return sparelist;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  
	  
	  @GetMapping("/datewise/open")
	  public @ResponseBody List<Breakdown> getOpenBreakdowns(
			  @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getOpenBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  
	  
@GetMapping("/export/openbreakdowns")
public ResponseEntity<byte[]> exportToExcelOpen(
        @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
        @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws IOException {
    List<Breakdown> breakdowns;
    if (fromDate != null && toDate != null) {
        breakdowns = breakdownRepo.getOpenBreakdownsByDateRange(fromDate, toDate);
    } else {
        breakdowns = breakdownRepo.getOpenBreakdowns();
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Breakdowns");

   
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("Sr No");
    headerRow.createCell(1).setCellValue("Slip Number");
    headerRow.createCell(2).setCellValue("Shift Name");
    headerRow.createCell(3).setCellValue("Machine Eqid");
    headerRow.createCell(4).setCellValue("Complaint Cause");
    headerRow.createCell(5).setCellValue("Root Cause");
    headerRow.createCell(6).setCellValue("Action Taken");
    headerRow.createCell(7).setCellValue("Status");
    headerRow.createCell(8).setCellValue("Raised Time");

    int serialNumber = 1; 

   
    for (Breakdown breakdown : breakdowns) {
        Row row = sheet.createRow(serialNumber); 
        row.createCell(0).setCellValue(serialNumber); 
        row.createCell(1).setCellValue(breakdown.getBd_slip());
        row.createCell(2).setCellValue(breakdown.getShift().getShift_name());
        row.createCell(3).setCellValue(breakdown.getMachine().getMachine_name() + "-" + breakdown.getMachine().getEqid());
        row.createCell(4).setCellValue(breakdown.getObservation());
        row.createCell(5).setCellValue(breakdown.getRoot_cause());
        row.createCell(6).setCellValue(breakdown.getAction_taken());
        row.createCell(7).setCellValue(getStatusText(breakdown.getStatus())); 
        row.createCell(8).setCellValue(breakdown.getTicket_raised_time().toString());
        serialNumber++; 
    }

    workbook.write(baos);
    workbook.close();

    byte[] excelBytes = baos.toByteArray();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=open_breakdowns.xlsx");

    return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
}
String getStatusText(int status) {
    switch (status) {
        case 1:
            return "Open";
        case 2:
            return "Trial";
        case 3:
            return "Closed";
        default:
            return "Unknown";
    }
}
	  

private Date adjustEndDate(Date toDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(toDate);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTime();
}
	  
	  

	/////////////////------------trial breakdowns--------/////////////////  
	    
	    @GetMapping("/trial")
	    public @ResponseBody List<Breakdown> getTrialBreakdowns() {
	        try {
	            return breakdownServices.getTrialBreakdowns();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    @GetMapping("/datewise/trial")
	    public @ResponseBody List<Breakdown> getTrialBreakdowns(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getTrialBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    
	    @GetMapping("/export/trialbreakdowns")
	    public ResponseEntity<byte[]> exportToExcelTrial(
	            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws IOException {
	        List<Breakdown> breakdowns;
	        if (fromDate != null && toDate != null) {
	            breakdowns = breakdownRepo.getTrialBreakdownsByDateRange(fromDate, toDate);
	        } else {
	            breakdowns = breakdownRepo.getTrialBreakdowns();
	        }

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Breakdowns");

	       
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Sr No");
	        headerRow.createCell(1).setCellValue("Slip Number");
	        headerRow.createCell(2).setCellValue("Shift Name");
	        headerRow.createCell(3).setCellValue("Machine Eqid");
	        headerRow.createCell(4).setCellValue("Complaint Cause");
	        headerRow.createCell(5).setCellValue("Root Cause");
	        headerRow.createCell(6).setCellValue("Action Taken");
	        headerRow.createCell(7).setCellValue("Status");
	        headerRow.createCell(8).setCellValue("Raised Time");

	        headerRow.createCell(9).setCellValue("Trial Time");
	        int serialNumber = 1; 

	       
	        for (Breakdown breakdown : breakdowns) {
	            Row row = sheet.createRow(serialNumber); 
	            row.createCell(0).setCellValue(serialNumber); 
	            row.createCell(1).setCellValue(breakdown.getBd_slip());
	            row.createCell(2).setCellValue(breakdown.getShift().getShift_name());
	            row.createCell(3).setCellValue(breakdown.getMachine().getMachine_name() + "-" + breakdown.getMachine().getEqid());
	            row.createCell(4).setCellValue(breakdown.getObservation());
	            row.createCell(5).setCellValue(breakdown.getRoot_cause());
	            row.createCell(6).setCellValue(breakdown.getAction_taken());
	            row.createCell(7).setCellValue(getStatusText(breakdown.getStatus())); 
	            row.createCell(8).setCellValue(breakdown.getTicket_raised_time().toString());
	            if (breakdown.getTicket_trial_time() != null) {
	                row.createCell(9).setCellValue(breakdown.getTicket_trial_time().toString());
	            } else {
	                row.createCell(9).setCellValue("N/A"); 
	            }
	            serialNumber++; 
	        }

	        workbook.write(baos);
	        workbook.close();

	        byte[] excelBytes = baos.toByteArray();
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=Trial_breakdowns.xlsx");

	        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	    }
	    
	    ////////////////////---------------closed breakdowns-------------------/////////////////
	    
	    @GetMapping("/datewise/closed")
	    public @ResponseBody List<Breakdown> getClosedBreakdowns(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getClosedBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	   
	    @GetMapping("/closed")
	    public @ResponseBody List<Breakdown> getClosedBreakdowns() {
	        try {
	            return breakdownServices.getClosedBreakdowns();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    //closed breakdown by breakdown id for Breakdown sheet.
	    @GetMapping("/closed/{breakdownId}")
	    public ResponseEntity<Map<String, Object>> getClosedBreakdownById(@PathVariable int breakdownId) {
	        Optional<Breakdown> breakdownOpt = breakdownRepo.getClosedBreakdownById(breakdownId);
	        Breakdown breakdown = breakdownOpt.get();
	        double totalConsumedCost = breakdownServices.calculateTotalConsumedCost(breakdown);

	        // getting time into  hours
	        long totalTime = 0;
	        if (breakdown.getTicket_closed_time() != null && breakdown.getTicket_raised_time() != null) {
	            totalTime = ChronoUnit.MINUTES.between(
	                breakdown.getTicket_raised_time().toInstant(), 
	                breakdown.getTicket_closed_time().toInstant()
	            );
	        }

	        long totalTrialTime = 0;
	        if (breakdown.getTicket_trial_time() != null && breakdown.getTicket_raised_time() != null) {
	            totalTrialTime = ChronoUnit.MINUTES.between(
	                breakdown.getTicket_raised_time().toInstant(), 
	                breakdown.getTicket_trial_time().toInstant()
	            );
	        }
	       
	        Map<String, Object> response = new HashMap<>();
	        response.put("breakdown", breakdown);
	        response.put("totalTime", totalTime/60);  // Total time in minutes
	        response.put("totalTrialTime", totalTrialTime/60);
	        response.put("totalConsumedCost", totalConsumedCost);

	        return ResponseEntity.ok(response);
	    }

	    
	
	    @GetMapping("/export/closedbreakdowns")
	    public ResponseEntity<byte[]> exportToExcelClosed(
	            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws IOException {
	        List<Breakdown> breakdowns;
	        if (fromDate != null && toDate != null) {
	            breakdowns = breakdownRepo.getClosedBreakdownsByDateRange(fromDate, toDate);
	        } else {
	            breakdowns = breakdownRepo.getClosedBreakdowns();
	        }

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Breakdowns");

	       
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Sr No");
	        headerRow.createCell(1).setCellValue("Slip Number");
	        headerRow.createCell(2).setCellValue("Shift Name");
	        headerRow.createCell(3).setCellValue("Machine Eqid");
	        headerRow.createCell(4).setCellValue("Complaint Cause");
	        headerRow.createCell(5).setCellValue("Root Cause");
	        headerRow.createCell(6).setCellValue("Action Taken");
	        headerRow.createCell(7).setCellValue("Status");
	        headerRow.createCell(8).setCellValue("Raised Time");

	        headerRow.createCell(9).setCellValue("Closed Time");
	        int serialNumber = 1; 

	       
	        for (Breakdown breakdown : breakdowns) {
	            Row row = sheet.createRow(serialNumber); 
	            row.createCell(0).setCellValue(serialNumber); 
	            row.createCell(1).setCellValue(breakdown.getBd_slip());
	            row.createCell(2).setCellValue(breakdown.getShift().getShift_name());
	            row.createCell(3).setCellValue(breakdown.getMachine().getMachine_name() + "-" + breakdown.getMachine().getEqid());
	            row.createCell(4).setCellValue(breakdown.getObservation());
	            row.createCell(5).setCellValue(breakdown.getRoot_cause());
	            row.createCell(6).setCellValue(breakdown.getAction_taken());
	            row.createCell(7).setCellValue(getStatusText(breakdown.getStatus())); 
	            row.createCell(8).setCellValue(breakdown.getTicket_raised_time().toString());
	            if (breakdown.getTicket_closed_time() != null) {
	                row.createCell(10).setCellValue(breakdown.getTicket_closed_time().toString());
	            } else {
	                row.createCell(10).setCellValue("N/A"); 
	            }
	            serialNumber++; 
	        }

	        workbook.write(baos);
	        workbook.close();

	        byte[] excelBytes = baos.toByteArray();
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=Closed_breakdowns.xlsx");

	        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	    }

	    
	    
	    
	    
	    ////////------------- All breakdowns-------------///////////////
	    
	    
	    @GetMapping("/all")
	    public @ResponseBody List<Breakdown> getAllBreakdowns() {
	    	 try {
	        return breakdownRepo.getAllBreakdowns();
	    	 } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
	    }	    
	    @GetMapping("/datewise/all")
	    public @ResponseBody List<Breakdown> getAllBreakdownsByDateRange(
	            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
	        try {
	            toDate = adjustEndDate(toDate);
	            return breakdownRepo.getAllBreakdownsByDateRange(fromDate, toDate);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
    
	    @GetMapping("/export/Allbreakdowns")
	    public ResponseEntity<byte[]> exportToExcelAll(
	            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
	            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws IOException {
	        List<Breakdown> breakdowns;
	        if (fromDate != null && toDate != null) {
	            breakdowns = breakdownRepo.getAllBreakdownsByDateRange(fromDate, toDate);
	        } else {
	            breakdowns = breakdownRepo.getAllBreakdowns();
	        }

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Breakdowns");

	       
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Sr No");
	        headerRow.createCell(1).setCellValue("Slip Number");
	        headerRow.createCell(2).setCellValue("Shift Name");
	        headerRow.createCell(3).setCellValue("Machine Eqid");
	        headerRow.createCell(4).setCellValue("Complaint Cause");
	        headerRow.createCell(5).setCellValue("Root Cause");
	        headerRow.createCell(6).setCellValue("Action Taken");
	        headerRow.createCell(7).setCellValue("Status");
	        headerRow.createCell(8).setCellValue("Raised Time");
	        headerRow.createCell(9).setCellValue("Trial Time");
	        headerRow.createCell(10).setCellValue("Closed Time");
	        int serialNumber = 1; 

	       
	        for (Breakdown breakdown : breakdowns) {
	            Row row = sheet.createRow(serialNumber); 
	            row.createCell(0).setCellValue(serialNumber); 
	            row.createCell(1).setCellValue(breakdown.getBd_slip());
	            row.createCell(2).setCellValue(breakdown.getShift().getShift_name());
	            row.createCell(3).setCellValue(breakdown.getMachine().getMachine_name() + "-" + breakdown.getMachine().getEqid());
	            row.createCell(4).setCellValue(breakdown.getObservation());
	            row.createCell(5).setCellValue(breakdown.getRoot_cause());
	            row.createCell(6).setCellValue(breakdown.getAction_taken());
	            row.createCell(7).setCellValue(getStatusText(breakdown.getStatus())); 
	            row.createCell(8).setCellValue(breakdown.getTicket_raised_time().toString());
	            
	            if (breakdown.getTicket_trial_time() != null) {
	                row.createCell(9).setCellValue(breakdown.getTicket_trial_time().toString());
	            } else {
	                row.createCell(9).setCellValue("N/A"); 
	            }
	            
	            if (breakdown.getTicket_closed_time() != null) {
	                row.createCell(10).setCellValue(breakdown.getTicket_closed_time().toString());
	            } else {
	                row.createCell(10).setCellValue("N/A"); 
	            }
	            
	            serialNumber++; 
	        }

	        workbook.write(baos);
	        workbook.close();

	        byte[] excelBytes = baos.toByteArray();
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=Overall_breakdowns.xlsx");

	        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	    }


	    
	    
	    
	    @GetMapping("/breakdownstatusCounts")
	    public StatusCounts getBreakdownStatusCounts() {
	        List<Object[]> counts = breakdownRepo.getStatusBreakdownCounts();

	        // Initialize counts to 0
	        int openCount = 0;
	        int trialCount = 0;
	        int closedCount = 0;

	        // Iterate over the results and extract the values
	        for (Object[] result : counts) {
	            Integer status = (Integer) result[0];  // Status value (1, 2, 3)
	            Long count = (Long) result[1];  // Count of breakdowns for the status

	            // Assign counts based on status
	            switch (status) {
	                case 1:
	                    openCount = count.intValue();
	                    break;
	                case 2:
	                    trialCount = count.intValue();
	                    break;
	                case 3:
	                    closedCount = count.intValue();
	                    break;
	                default:
	                    break;
	            }
	        }

	        // Calculate the total breakdowns (sum of open, trial, and closed breakdowns)
	        int totalBreakdowns = openCount + trialCount + closedCount;

	        // Create the StatusCounts object and set the counts
	        StatusCounts statusCounts = new StatusCounts();
	        statusCounts.setOpenCount(openCount);
	        statusCounts.setTrialCount(trialCount);
	        statusCounts.setClosedCount(closedCount);
	        statusCounts.setTotalBreakdowns(totalBreakdowns);  // Set total breakdowns

	        return statusCounts;
	    }
	    
	    
	    //for mobile Application
	    @GetMapping("/breakdownstatusAndRecords")
	    public Map<String, Object> getBreakdownStatusAndRecords(@RequestParam String eqid) {

	        // Fetch status counts
	        List<Object[]> counts = breakdownRepo.getStatusCountsByEqId(eqid);

	        int openCount = 0;
	        int trialCount = 0;
	        int closedCount = 0;

	        for (Object[] result : counts) {
	            Integer status = (Integer) result[0]; 
	            Long count = (Long) result[1]; 

	            switch (status) {
	                case 1:
	                    openCount = count.intValue();
	                    break;
	                case 2:
	                    trialCount = count.intValue();
	                    break;
	                case 3:
	                    closedCount = count.intValue();
	                    break;
	                default:
	                    break;
	            }
	        }

	        int totalBreakdowns = openCount + trialCount + closedCount;

	        // Fetch breakdown records
	        List<Breakdown> openRecords = breakdownRepo.getOpenBreakDownByEqId(eqid);
	        List<Breakdown> trialRecords = breakdownRepo.getTrialBreakDownByEqId(eqid);
	        List<Breakdown> closedRecords = breakdownRepo.getClosedBreakDownByEqId(eqid);

	        // Create counts map
	        Map<String, Integer> countsMap = new HashMap<>();
	        countsMap.put("open", openCount);
	        countsMap.put("trial", trialCount);
	        countsMap.put("closed", closedCount);
	        countsMap.put("total", totalBreakdowns);

	        // Create records map
	        Map<String, List<Breakdown>> recordsMap = new HashMap<>();
	        recordsMap.put("open", openRecords);
	        recordsMap.put("trial", trialRecords);
	        recordsMap.put("closed", closedRecords);

	        // Create response map
	        Map<String, Object> response = new HashMap<>();
	        response.put("counts", countsMap);
	        response.put("records", recordsMap);

	        return response;
	    }
	    
	    
	    
	    
	    
	    

	    
	    
	    
	    @GetMapping(value = "/get52WeekBreakDown", produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<MachineWeekDTO> get52WeekBreakDown(@RequestParam("weekNo") int weekNo) {
	        List<MachineWeekDTO> list = new ArrayList<>();
	        try {
	            Calendar calendar = Calendar.getInstance();
	            int year = calendar.get(Calendar.YEAR);
	            int weekNumber = weekNo;  // Week number (1-52 or 1-53)

	            // Calculate start and end dates of the week
	            LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
	            WeekFields weekFields = WeekFields.of(Locale.getDefault());
	            LocalDate startOfWeek = firstDayOfYear
	                .with(weekFields.weekOfYear(), weekNumber)
	                .with(weekFields.dayOfWeek(), 1); // Monday
	            LocalDate endOfWeek = startOfWeek.plusDays(6); // Sunday


Date startDate = java.util.Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
Date endDate = java.util.Date.from(endOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
	            // Fetch all machine names
	            Set<String> machineNames = new HashSet<>();
	            List<Machine> machineList = machineServices.getMachineList();
	            for (Machine machine : machineList) {
	                machineNames.add(machine.getMachine_name());
	            }

	            // Process each machine
	            for (String machineName : machineNames) {
	                MachineWeekDTO machineWeekDTO = new MachineWeekDTO();
	                List<WeekDataDTO> dataDTOs = new ArrayList<>();

	                WeekDataDTO weekDataDTO = new WeekDataDTO();

	                // Fetch breakdown data
	                List<Breakdown> openBreakdowns = breakdownServices.getOpenBreakdownByDatesAndMachine(startDate, endDate, machineName);
	                List<Breakdown> trailBreakdowns = breakdownServices.getTrailBreakdownByDatesAndMachine(startDate, endDate, machineName);
	                List<Breakdown> closedBreakdowns = breakdownServices.getClosedBreakdownByDatesAndMachine(startDate, endDate, machineName);

	                // Populate WeekDataDTO
	                weekDataDTO.setWeekNo(weekNo);
	                weekDataDTO.setOpen(openBreakdowns.size());
	                weekDataDTO.setTrail(trailBreakdowns.size());
	                weekDataDTO.setClosed(closedBreakdowns.size());
	                dataDTOs.add(weekDataDTO);

	                // Populate MachineWeekDTO
	                machineWeekDTO.setMachineName(machineName);
	                machineWeekDTO.setWeekDatas(dataDTOs);

	                // Add to the result list
	                list.add(machineWeekDTO);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
		
		@GetMapping(value = "/getBreakdownDatailsByWeekAndMachienName", produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody List<Breakdown> getBreakdownDatailsByWeekAndMachienName(@RequestParam("week") int week,@RequestParam("machineName") String machineName,@RequestParam("status") String status) {
			List<Breakdown> totoal= new ArrayList<Breakdown>();
			try {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				  Date[] startEndDates = getStartAndEndDatesOfWeek(year, week);
				  Date startDate = startEndDates[0];
			        Date endDate = startEndDates[1];
			        
			        
			        int statusCode;
			        if(status.equalsIgnoreCase("Open")){
			        	  List<Breakdown> openMaintList=breakdownServices.getOpenBreakdownByDatesAndMachine(startDate, endDate,machineName);

						totoal.addAll(openMaintList);

			        }else if (status.equalsIgnoreCase("Trial")){
						List<Breakdown> trailMaintList=breakdownServices.getTrailBreakdownByDatesAndMachine(startDate, endDate,machineName);
						totoal.addAll(trailMaintList);

			        }else if (status.equalsIgnoreCase("Closed")){
						List<Breakdown> closedMaintList=breakdownServices.getClosedBreakdownByDatesAndMachine(startDate, endDate,machineName);
						totoal.addAll(closedMaintList);
			        }
			        
				  for (Breakdown  breakdown:totoal) {
					     calendar.setTime(breakdown.getTicket_raised_time());
			            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

			            breakdown.setWeek(weekOfYear);
						
					}
			
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return totoal;
		}
	
		@GetMapping("/Breakdown_report")
		public List<BreakdownReportDTO> getBreakdownReportsByDateRange(
		        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
		        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {

		    // Adjust to start of day and end of day using LocalDateTime
		    LocalDateTime startOfDay = startDateTime.toLocalDate().atStartOfDay();
		    LocalDateTime endOfDay = endDateTime.toLocalDate().atTime(LocalTime.MAX);

		    // Convert to Date for service compatibility
		    Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		    Date endOfDayDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

		    return breakdownServices.generateBreakdownReportByDateRange(startOfDayDate, endOfDayDate);
		}

		  
		  
		  
		  @GetMapping("/Breakdown_report/export")
		  public ResponseEntity<byte[]> exportBreakdownReports(
		          @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
		          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
			  
			  
			  // Adjust endDate to include the entire day
			    Calendar calendar = Calendar.getInstance();
			    calendar.setTime(endDate);
			    calendar.set(Calendar.HOUR_OF_DAY, 23);
			    calendar.set(Calendar.MINUTE, 59);
			    calendar.set(Calendar.SECOND, 59);
			    calendar.set(Calendar.MILLISECOND, 999);
			    endDate = calendar.getTime();

		      // Generate the breakdown report data
		      List<BreakdownReportDTO> breakdownReports = breakdownServices.generateBreakdownReportByDateRange(startDate, endDate);

		      // Create a workbook and a sheet
		      Workbook workbook = new XSSFWorkbook();
		      Sheet sheet = workbook.createSheet("Breakdown Report");

		      // Create header row
		      Row headerRow = sheet.createRow(0);
		      headerRow.createCell(0).setCellValue("Sr. No.");
		      headerRow.createCell(1).setCellValue("Machine Name");
		      headerRow.createCell(2).setCellValue("Equipment ID");
		      headerRow.createCell(3).setCellValue("Category Name");
		      headerRow.createCell(4).setCellValue("Shift Name");
		      headerRow.createCell(5).setCellValue("Status");
		      headerRow.createCell(6).setCellValue("Breakdown Slip No");
		      headerRow.createCell(7).setCellValue("Root Cause");
		      headerRow.createCell(8).setCellValue("Raised Time");
		      headerRow.createCell(9).setCellValue("Trial Time");
		      headerRow.createCell(10).setCellValue("Closed Time");
		      headerRow.createCell(11).setCellValue("Raise To Trial Time (In Hours:minutes)");
		      headerRow.createCell(12).setCellValue("Trial To Closed Time (In Hours:minutes)");
		      headerRow.createCell(13).setCellValue("Raise To Closed Time (In Hours:minutes)");

		      int rowNum = 1;
		      int serialNumber = 1;
		      for (BreakdownReportDTO report : breakdownReports) {
		          Row row = sheet.createRow(rowNum++);
		          Breakdown breakdown = report.getBreakdown();

		          // Add Sr. No. column
		          row.createCell(0).setCellValue(serialNumber++);

		          row.createCell(1).setCellValue(breakdown.getMachine() != null ? breakdown.getMachine().getMachine_name() : ""); 
		          row.createCell(2).setCellValue(breakdown.getMachine() != null ? breakdown.getMachine().getEqid() : ""); 
		    //      row.createCell(3).setCellValue(breakdown.getCategory() != null ? breakdown.getCategory().getCat_name() : ""); 
		          row.createCell(4).setCellValue(breakdown.getShift() != null ? breakdown.getShift().getShift_name() : ""); 
		          row.createCell(5).setCellValue(breakdown.getStatusStr()); 
		          row.createCell(6).setCellValue(breakdown.getBd_slip()); 
		          row.createCell(7).setCellValue(breakdown.getRoot_cause()); 
		          row.createCell(8).setCellValue(report.getTicketRaisedTime() != null ? report.getTicketRaisedTime().toString() : ""); 
		          row.createCell(9).setCellValue(report.getTicketTrialTime() != null ? report.getTicketTrialTime().toString() : ""); 
		          row.createCell(10).setCellValue(report.getTicketClosedTime() != null ? report.getTicketClosedTime().toString() : ""); 

		          // Handle Raise to Trial time (as decimal hours)
		          if (report.getTicketTrialTime() != null) {
		              double raiseToTrial = report.getRaiseToTrial() / 60.0; 
		              row.createCell(11).setCellValue(String.format("%.2f", raiseToTrial)); 
		          } else {
		              row.createCell(11).setCellValue("N/A"); 
		          }

		          // Handle Trial to Closed time (as decimal hours)
		          if (report.getTicketClosedTime() != null && report.getTicketTrialTime() != null) {
		              double trialToClosed = report.getTrialToClosed() / 60.0; 
		              row.createCell(12).setCellValue(String.format("%.2f", trialToClosed)); 
		          } else {
		              row.createCell(12).setCellValue("N/A"); 
		          }

		          // Handle Raise to Closed time (as decimal hours)
		          if (report.getTicketClosedTime() != null) {
		              double totalRaiseToClosed = report.getTotalTime() / 60.0; 
		              row.createCell(13).setCellValue(String.format("%.2f", totalRaiseToClosed)); 
		          } else {
		              row.createCell(13).setCellValue("N/A"); 
		          }
		      }

		      // Write the output to a byte array
		      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		      try {
		          workbook.write(outputStream);
		          workbook.close();
		      } catch (IOException e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }

		      // Set the headers for the response
		      HttpHeaders headers = new HttpHeaders();
		      headers.add("Content-Disposition", "attachment; filename=BreakdownReport.xlsx");
		      return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		  }
		  
		  
		  
		  
		  @GetMapping("/Breakdown_report/download-template")
		  public ResponseEntity<byte[]> downloadBreakdownReportTemplate() {
		      // Create a workbook and a sheet for the template
		      Workbook workbook = new XSSFWorkbook();
		      Sheet sheet = workbook.createSheet("Breakdown Report Template");

		      // Create header row with the required column names
		      Row headerRow = sheet.createRow(0);
		      headerRow.createCell(0).setCellValue("Machine Name");
		      headerRow.createCell(1).setCellValue("Equipment ID");
		      headerRow.createCell(2).setCellValue("Category Name");
		      headerRow.createCell(3).setCellValue("Shift Name");
		      headerRow.createCell(4).setCellValue("Status");
		      headerRow.createCell(5).setCellValue("Breakdown Slip No");
		      headerRow.createCell(6).setCellValue("Root Cause");
		      headerRow.createCell(7).setCellValue("Raised Time");
		      headerRow.createCell(8).setCellValue("Trial Time");
		      headerRow.createCell(9).setCellValue("Closed Time");
		      headerRow.createCell(10).setCellValue("Raise To Trial Time (In Hours:minutes)");
		      headerRow.createCell(11).setCellValue("Trial To Closed Time (In Hours:minutes)");
		      headerRow.createCell(12).setCellValue("Raise To Closed Time (In Hours:minutes)");

		      // Write the output to a byte array
		      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		      try {
		          workbook.write(outputStream);
		          workbook.close();
		      } catch (IOException e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }

		      // Set the headers for the response
		      HttpHeaders headers = new HttpHeaders();
		      headers.add("Content-Disposition", "attachment; filename=BreakdownReportTemplate.xlsx");
		      return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		  }
		  // breakdown report
		  @GetMapping("/Breakdown_report/all")
		  public List<BreakdownReportDTO> getAllBreakdownReports() {
		    
		      return breakdownServices.generateAllBreakdownReports();
		  }
		  
		  ////////////////////////////////////////////Breakdown Report/////////////////////////////////////
		  @GetMapping("/full_Breakdown_report")
		  public ResponseEntity<List<BreakdownReportDTO>> getBreakdownReports(
		          @RequestParam(value = "startDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,

		          @RequestParam(value = "endDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		      try {
		          List<Breakdown> breakdowns;

		          // 🔹 Case 1: Both dates provided → filter by range
		          if (startDate != null && endDate != null) {
		              LocalDateTime startOfDay = startDate.atStartOfDay();
		              LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);

		              Date startDateTime = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		              Date endDateTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

		              breakdowns = breakdownRepo.findByTicketRaisedTimeBetween(startDateTime, endDateTime);
		          }
		          // 🔹 Case 2: Only start date → from startDate till now
		          else if (startDate != null) {
		              LocalDateTime startOfDay = startDate.atStartOfDay();
		              Date startDateTime = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		              Date now = new Date();

		              breakdowns = breakdownRepo.findByTicketRaisedTimeBetween(startDateTime, now);
		          }
		          // 🔹 Case 3: Only end date → until endDate (everything before)
		          else if (endDate != null) {
		              LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);
		              Date endDateTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

		              breakdowns = breakdownRepo.findByTicketRaisedTimeBefore(endDateTime);
		          }
		          // 🔹 Case 4: No filters → all data
		          else {
		              breakdowns = breakdownRepo.findAll();
		          }

		          // 🔹 Convert to DTO
		          List<BreakdownReportDTO> reportList = breakdowns.stream()
		                  .map(breakdownServices::generateBreakdownReport)
		                  .toList();

		          return new ResponseEntity<>(reportList, HttpStatus.OK);

		      } catch (Exception e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
		  }
		  @GetMapping("/full_Breakdown_report/count")
		  public ResponseEntity<Long> getBreakdownCount(
		          @RequestParam(defaultValue = "") String keyword,
		          @RequestParam(value = "startDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		          @RequestParam(value = "endDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		      try {
		          Date startDateTime = null, endDateTime = null;
		          if (startDate != null) startDateTime = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		          if (endDate != null) endDateTime = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

		          Long count = breakdownRepo.countFilteredBreakdowns(keyword, startDateTime, endDateTime);
		          return new ResponseEntity<>(count, HttpStatus.OK);

		      } catch (Exception e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
		  }
		  @GetMapping("/full_Breakdown_report/search")
		  public ResponseEntity<Page<BreakdownReportDTO>> searchBreakdowns(
		          @RequestParam(defaultValue = "") String keyword,
		          @RequestParam int page,
		          @RequestParam int size,
		          @RequestParam(value = "startDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		          @RequestParam(value = "endDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		      try {
		          Pageable pageable = PageRequest.of(page - 1, size); // 🔹 minus 1 if frontend is 1-based

		          // Convert LocalDate to Date
		          Date startDateTime = null, endDateTime = null;
		          if (startDate != null) {
		              startDateTime = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		          }
		          if (endDate != null) {
		              endDateTime = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
		          }

		          Page<Breakdown> breakdownPage = breakdownRepo.searchBreakdowns(keyword, startDateTime, endDateTime, pageable);
		          Page<BreakdownReportDTO> dtoPage = breakdownPage.map(breakdownServices::generateBreakdownReport);

		          return new ResponseEntity<>(dtoPage, HttpStatus.OK);

		      } catch (Exception e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
		  }


		  @GetMapping("/full_Breakdown_report/page")
		  public ResponseEntity<Page<BreakdownReportDTO>> getBreakdownReportsWithPagination(
		          @RequestParam int page,
		          @RequestParam int size,
		          @RequestParam(value = "startDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		          @RequestParam(value = "endDate", required = false)
		          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		      try {
		          Pageable pageable = PageRequest.of(page - 1, size); // no need for Sort.by() now
		          Page<Breakdown> breakdownPage;

		          if (startDate != null && endDate != null) {
		              LocalDateTime startOfDay = startDate.atStartOfDay();
		              LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);

		              Date startDateTime = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		              Date endDateTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

		              breakdownPage = breakdownRepo.findByTicketRaisedTimeBetween(startDateTime, endDateTime, pageable);
		          } 
		          else if (startDate != null) {
		              LocalDateTime startOfDay = startDate.atStartOfDay();
		              Date startDateTime = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		              breakdownPage = breakdownRepo.findByTicketRaisedTimeAfter(startDateTime, pageable);
		          } 
		          else if (endDate != null) {
		              LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);
		              Date endDateTime = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		              breakdownPage = breakdownRepo.findByTicketRaisedTimeBefore(endDateTime, pageable);
		          } 
		          else {
		              breakdownPage = breakdownRepo.findAll(pageable);
		          }

		          Page<BreakdownReportDTO> dtoPage = breakdownPage.map(breakdownServices::generateBreakdownReport);
		          return new ResponseEntity<>(dtoPage, HttpStatus.OK);

		      } catch (Exception e) {
		          e.printStackTrace();
		          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		      }
		  }

		  
		  
		  
/////////////////////////////////////////////////////////////////////////////////////////
		  
		  @GetMapping("/Breakdown_report/userwise/{userId}")
		  public List<BreakdownReportDTO> getBreakdownReportsByUser(
		      @PathVariable("userId") int userId,
		      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		      
		      return breakdownServices.generateBreakdownReportsByUserAndDate(userId, fromDate, toDate);
		  }
		  // ✅ 1️⃣ Pagination
		    @GetMapping("/Breakdown_report/userwise/page/{userId}")
		    public ResponseEntity<Page<BreakdownReportDTO>> getUserWiseBreakdownReportPage(
		            @PathVariable("userId") int userId,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam int page,
		            @RequestParam int size) {

		        Page<BreakdownReportDTO> report = breakdownServices.getPaginatedBreakdownReportsByUser(userId, fromDate, toDate, page-1, size);
		        return new ResponseEntity<>(report, HttpStatus.OK);
		    }

		    // ✅ 2️⃣ Search + Pagination
		    @GetMapping("/Breakdown_report/userwise/search/{userId}")
		    public ResponseEntity<Page<BreakdownReportDTO>> searchUserWiseBreakdownReport(
		            @PathVariable("userId") int userId,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam (defaultValue = "")String keyword,
		            @RequestParam int page,
		            @RequestParam int size) {

		        Page<BreakdownReportDTO> report = breakdownServices.searchPaginatedBreakdownReportsByUser(userId, fromDate, toDate, keyword, page-1, size);
		        return new ResponseEntity<>(report, HttpStatus.OK);
		    }

		    // ✅ 3️⃣ Count API
		    @GetMapping("/Breakdown_report/userwise/count/{userId}")
		    public ResponseEntity<Long> countUserWiseBreakdowns(
		            @PathVariable("userId") int userId,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

		        long count = breakdownServices.countBreakdownsByUserAndDate(userId, fromDate, toDate);
		        return new ResponseEntity<>(count, HttpStatus.OK);
		    }


///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		  
		  @GetMapping("/report/{machine_id}")
		  public ResponseEntity<BreakdownRepoCountDTO> getBreakdownReport(
		          @PathVariable int machine_id,
		          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

		      BreakdownRepoCountDTO breakdownReport = breakdownServices.getBreakdownsAndDaysBetween(machine_id, fromDate, toDate);
		      return ResponseEntity.ok(breakdownReport);
		  }


		  @GetMapping("/Break_dashboard/{machine_id}")
		    public List<Break_DashboardDTO> getBreakdownReportByMachine(@PathVariable int machine_id) {		       
		        List<Breakdown> breakdowns = breakdownRepo.findByMachineId(machine_id);		       
		        List<MachineMaintSpare> machineSpareAssignments = machineSpareAssignRepo.findBymachineyID(machine_id);		      
		        return breakdowns.stream()
		                .map(breakdown -> new Break_DashboardDTO(breakdown, machineSpareAssignments))
		                .collect(Collectors.toList());
		    }
		    
		// machine    
		    
		    @GetMapping("/Breakdowncounts/{machineId}")
		    public BreakdownStatusCounts getBreakdownCounts(@PathVariable int machineId) {
		        return breakdownServices.getBreakdownCountsByMachineId(machineId);
		    }
		    
		    @GetMapping("/BreakdowncountsFiler/{machineId}")
		    public BreakdownStatusCounts getBreakdownCounts(
		            @PathVariable int machineId,
		            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		        return breakdownServices.getBreakdownCountsByMachineId(machineId, fromDate, toDate);
		    }


		  
		
		    
		    @PostMapping("/duplicates_breakdowns")
		    public ResponseEntity<List<BreakdownResponseDTO>> findAndMarkDuplicates() {
		        List<BreakdownResponseDTO> duplicates = breakdownServices.findAndMarkDuplicates();
		        return ResponseEntity.ok(duplicates);
		    }
		    
		    
		    @GetMapping("/breakdown_spare_report")
		    public ResponseEntity<List<BreakdownSpare>> getSpareConsumptionReport() {
		        try {
		            List<BreakdownSpare> report = breakdownSpareRepo.findAll();
		            return new ResponseEntity<>(report, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
		    
		   
		  
		    //mobile api( utilized spares)
		    @GetMapping("/breakdownSpare/byBreakdownId")
		    public List<BreakdownSpare> getBreakdownsByBreaksownId(@RequestParam int breakdownId) {
		        return breakdownSpareRepo.getBreakdownSpareByBreakdownId(breakdownId);
		    }
		    
		    
		    
		    @GetMapping("/breakdown_spare_report/{breakbownId}")
		    public ResponseEntity<List<BreakdownSpare>> getSpareConsumptionReportBySpareId(
		            @PathVariable("breakbownId") int breakbownId) {
		        try {
		            List<BreakdownSpare> report = breakdownSpareRepo.getBreakdownSpareByBreakdownId(breakbownId);
		            if (report.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		            }
		            return new ResponseEntity<>(report, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
		    
		  

		    // spare consumption report/////
		    
		    @GetMapping("/breakdownConsumption_report/byspare/{maintspareId}")
		    public ResponseEntity<List<SpareConsumptionReportDTO>> getSpareConsumptionReportByMaintSpareId(
		            @PathVariable("maintspareId") int maintspareId) {
		        try {
		            List<SpareConsumptionReportDTO> report = breakdownSpareRepo.getBreakdownSpareDetailsByMaintSpareId(maintspareId);
		            if (report.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		            }
		            return new ResponseEntity<>(report, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
		    
		    
		    @GetMapping("/breakdownConsumption_report")
		    public ResponseEntity<List<SpareConsumptionReportDTO>> getAllSpareConsumptionReport() {
		        try {
		            List<SpareConsumptionReportDTO> report = breakdownSpareRepo.getAllBreakdownSpareDetails();
		            
		            if (report.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		            }

		            return new ResponseEntity<>(report, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }

////////---------------////////////////

		    
		    @GetMapping("/breakdownSpare/byBreakdownId/{breakdownId}")
		    public ResponseEntity<BreakdownResponseDTOMob> getBreakdownDetails(@PathVariable int breakdownId) {
		        BreakdownResponseDTOMob response = breakdownServices.getBreakdownDetailsById(breakdownId);
		        return ResponseEntity.ok(response);
		    }

//labName
		    
		    
		    @GetMapping("/breakdownstatusCountsByLab")
		    public StatusCounts getBreakdownStatusCountsByLab(@RequestParam String labName) {
		        List<Object[]> counts = breakdownRepo.getStatusBreakdownCountsByLabName(labName);

		        // Initialize counts to 0
		        int openCount = 0;
		        int trialCount = 0;
		        int closedCount = 0;

		        // Iterate over the results and extract the values
		        for (Object[] result : counts) {
		            Integer status = (Integer) result[0];  // Status value (1, 2, 3)
		            Long count = (Long) result[1];  // Count of breakdowns for the status

		            // Assign counts based on status
		            switch (status) {
		                case 1:
		                    openCount = count.intValue();
		                    break;
		                case 2:
		                    trialCount = count.intValue();
		                    break;
		                case 3:
		                    closedCount = count.intValue();
		                    break;
		                default:
		                    break;
		            }
		        }

		        // Calculate the total breakdowns (sum of open, trial, and closed breakdowns)
		        int totalBreakdowns = openCount + trialCount + closedCount;

		        // Create the StatusCounts object and set the counts
		        StatusCounts statusCounts = new StatusCounts();
		        statusCounts.setOpenCount(openCount);
		        statusCounts.setTrialCount(trialCount);
		        statusCounts.setClosedCount(closedCount);
		        statusCounts.setTotalBreakdowns(totalBreakdowns);  // Set total breakdowns

		        return statusCounts;
		    }


		    @GetMapping(value = "/get52WeekBreakDownByLab", produces = MediaType.APPLICATION_JSON_VALUE)
		    public @ResponseBody List<MachineWeekDTO> get52WeekBreakDownByLab(@RequestParam("weekNo") int weekNo, @RequestParam("labName") String labName) {
		        List<MachineWeekDTO> list = new ArrayList<>();
		        try {
		            Calendar calendar = Calendar.getInstance();
		            int year = calendar.get(Calendar.YEAR);
		            int weekNumber = weekNo;  // Week number (1-52 or 1-53)

		            // Calculate start and end dates of the week
		            LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
		            WeekFields weekFields = WeekFields.of(Locale.getDefault());
		            LocalDate startOfWeek = firstDayOfYear
		                .with(weekFields.weekOfYear(), weekNumber)
		                .with(weekFields.dayOfWeek(), 1); // Monday
		            LocalDate endOfWeek = startOfWeek.plusDays(6); // Sunday


Date startDate = java.util.Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
Date endDate = java.util.Date.from(endOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());

		            // Fetch all machine names
		            Set<String> machineNames = new HashSet<>();
		            List<Machine> machineList = machineServices.getMachineList();
		            for (Machine machine : machineList) {
		                machineNames.add(machine.getMachine_name());
		            }

		            // Process each machine
		            for (String machineName : machineNames) {
		                MachineWeekDTO machineWeekDTO = new MachineWeekDTO();
		                List<WeekDataDTO> dataDTOs = new ArrayList<>();

		                WeekDataDTO weekDataDTO = new WeekDataDTO();

		                // Fetch breakdown data filtered by labName
		                List<Breakdown> openBreakdowns = breakdownRepo.getOpenBreakdownByDatesAndMachineAndLab(startDate, endDate, machineName, labName);
		                List<Breakdown> trailBreakdowns = breakdownRepo.getTrailBreakdownByDatesAndMachineAndLab(startDate, endDate, machineName, labName);
		                List<Breakdown> closedBreakdowns = breakdownRepo.getClosedBreakdownByDatesAndMachineAndLab(startDate, endDate, machineName, labName);

		                // Populate WeekDataDTO
		                weekDataDTO.setWeekNo(weekNo);
		                weekDataDTO.setOpen(openBreakdowns.size());
		                weekDataDTO.setTrail(trailBreakdowns.size());
		                weekDataDTO.setClosed(closedBreakdowns.size());
		                dataDTOs.add(weekDataDTO);

		                // Populate MachineWeekDTO
		                machineWeekDTO.setMachineName(machineName);
		                machineWeekDTO.setWeekDatas(dataDTOs);

		                // Add to the result list
		                list.add(machineWeekDTO);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return list;
		    }

		  
		 // Get open breakdowns without date filter, and filtered by labName
		    @GetMapping("/openByLab")
		    public @ResponseBody List<Breakdown> getOpenBreakdownsByLab(@RequestParam("labName") String labName) {
		        try {
		            return breakdownRepo.getOpenBreakdownsByLabName(labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get open breakdowns with date range and labName filter
		    @GetMapping("/datewise/openByLab")
		    public @ResponseBody List<Breakdown> getOpenBreakdownsByLabDate(
		            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam("labName") String labName) {
		        try {
		            toDate = adjustEndDate(toDate);
		            return breakdownRepo.getOpenBreakdownsByDateRangeAndLab(fromDate, toDate, labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get trial breakdowns without date filter, and filtered by labName
		    @GetMapping("/trialByLab")
		    public @ResponseBody List<Breakdown> getTrialBreakdownsByLab(@RequestParam("labName") String labName) {
		        try {
		            return breakdownRepo.getTrialBreakdownsByLabName(labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get trial breakdowns with date range and labName filter
		    @GetMapping("/datewise/trialByLab")
		    public @ResponseBody List<Breakdown> getTrialBreakdownsByLabDate(
		            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam("labName") String labName) {
		        try {
		            toDate = adjustEndDate(toDate);
		            return breakdownRepo.getTrialBreakdownsByDateRangeAndLab(fromDate, toDate, labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get closed breakdowns without date filter, and filtered by labName
		    @GetMapping("/closedByLab")
		    public @ResponseBody List<Breakdown> getClosedBreakdownsByLab(@RequestParam("labName") String labName) {
		        try {
		            return breakdownRepo.getClosedBreakdownsByLabName(labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get closed breakdowns with date range and labName filter
		    @GetMapping("/datewise/closedByLab")
		    public @ResponseBody List<Breakdown> getClosedBreakdownsByLabDate(
		            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam("labName") String labName) {
		        try {
		            toDate = adjustEndDate(toDate);
		            return breakdownRepo.getClosedBreakdownsByDateRangeAndLab(fromDate, toDate, labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
		    
		 // Get all breakdowns filtered by labName
		    @GetMapping("/allByLab")
		    public @ResponseBody List<Breakdown> getAllBreakdownsByLab(@RequestParam("labName") String labName) {
		        try {
		            return breakdownRepo.getAllBreakdownsByLabName(labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    // Get all breakdowns within a date range and filtered by labName
		    @GetMapping("/datewise/allByLab")
		    public @ResponseBody List<Breakdown> getAllBreakdownsByDateRangeByLab(
		            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
		            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
		            @RequestParam("labName") String labName) {
		        try {
		            toDate = adjustEndDate(toDate);
		            return breakdownRepo.getAllBreakdownsByDateRangeAndLab(fromDate, toDate, labName);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }

		    
		    @GetMapping(value = "/getOpenBreakDownForWeekByLab")
		    public @ResponseBody List<Breakdown> getOpenBreakDownForWeekByLab(
		            @RequestParam("labName") String labName) {
		        List<Breakdown> breakdownList = null;
		        try {
		            Calendar calendar = Calendar.getInstance();

		            // Set the calendar to the start of the week (Sunday)
		            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		            Date startDate = calendar.getTime();

		            // Format the date
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            System.out.println("Start of the week: " + sdf.format(startDate));

		            // Set the calendar to the end of the week (Saturday)
		            calendar.add(Calendar.DAY_OF_WEEK, 6);
		            Date endDate = calendar.getTime();
		            System.out.println("End of the week: " + sdf.format(endDate));

		            // Pass labName to the service layer
		            breakdownList = breakdownRepo.getOpenBreakdownByDatesAndLab(startDate, endDate, labName);

		            int i = 1;
		            for (Breakdown breakdown : breakdownList) {
		                breakdown.setRaisedName(breakdown.getAddedBy().getFirstName() + " " + breakdown.getAddedBy().getLastName());
		                
		               
//		                Lab lab = labRepo.findByLabNames(labName); 
//		                breakdown.setLab(lab);

		                if (breakdown.getStatus() == 1) {
		                    breakdown.setStatusStr("Waiting for Maintenance");
		                }
		                if (breakdown.getStatus() == 2) {
		                    breakdown.setStatusStr("Waiting for Trial");
		                }
		                if (breakdown.getStatus() == 3) {
		                    breakdown.setStatusStr("Approved");
		                }
		                if (breakdown.getStatus() == 4) {
		                    breakdown.setStatusStr("Approved with deviation");
		                }
		                breakdown.setSrNo(i);
		                i++;
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return breakdownList;
		    }


		    
		    
		    @GetMapping(value = "/getTrailBreakDownForWeekByLab")
		    public @ResponseBody List<Breakdown> getTrailBreakDownForWeekByLab(
		            @RequestParam("labName") String labName) {
		        List<Breakdown> breakdownList = null;
		        try {
		            Calendar calendar = Calendar.getInstance();

		            // Set the calendar to the start of the week (Sunday)
		            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		            Date startDate = calendar.getTime();

		            // Format the date
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            System.out.println("Start of the week: " + sdf.format(startDate));

		            // Set the calendar to the end of the week (Saturday)
		            calendar.add(Calendar.DAY_OF_WEEK, 6);
		            Date endDate = calendar.getTime();
		            System.out.println("End of the week: " + sdf.format(endDate));

		            // Pass labName to the service layer
		            breakdownList = breakdownRepo.getTrailBreakdownByDatesAndLab(startDate, endDate, labName);

		            int i = 1;
		            for (Breakdown breakdown : breakdownList) {
		                breakdown.setRaisedName(breakdown.getAddedBy().getFirstName() + " " + breakdown.getAddedBy().getLastName());

		                if (breakdown.getStatus() == 1) {
		                    breakdown.setStatusStr("Waiting for Maintenance");
		                }
		                if (breakdown.getStatus() == 2) {
		                    breakdown.setStatusStr("Waiting for Trial");
		                }
		                if (breakdown.getStatus() == 3) {
		                    breakdown.setStatusStr("Approved");
		                }
		                if (breakdown.getStatus() == 4) {
		                    breakdown.setStatusStr("Approved with deviation");
		                }

		                breakdown.setSrNo(i);
		                i++;

		                if (breakdown.getRoot_cause() == null || breakdown.getAction_taken() == null) {
		                    System.out.println("Root Cause or Action Taken is NULL");
		                    List<Breakdownupdate> breakdownupdates = breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown.getBreakdown_id());
		                    breakdown.setRoot_cause(breakdownupdates.get(0).getRoot_cause());
		                    breakdown.setAction_taken(breakdownupdates.get(0).getAction_taken());
		                    breakdown.setPrev_action_plan(breakdownupdates.get(0).getPrev_action_plan());
		                }
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return breakdownList;
		    }

		    
		    @GetMapping(value = "/getClosedBreakDownForWeekByLab")
		    public @ResponseBody List<Breakdown> getClosedBreakDownForWeekByLab(@RequestParam("labName") String labName) {
		        List<Breakdown> breakdownList = null;
		        try {
		            Calendar calendar = Calendar.getInstance();

		            // Set the calendar to the start of the week (Sunday)
		            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		            Date startDate = calendar.getTime();

		            // Format the date
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            System.out.println("Start of the week: " + sdf.format(startDate));

		            // Set the calendar to the end of the week (Saturday)
		            calendar.add(Calendar.DAY_OF_WEEK, 6);
		            Date endDate = calendar.getTime();
		            System.out.println("End of the week: " + sdf.format(endDate));

		            // Pass labName to the service layer
		            breakdownList = breakdownRepo.getClosedBreakdownByDatesAndLab(startDate, endDate, labName);

		            int i = 1;
		            for (Breakdown breakdown : breakdownList) {
		                breakdown.setRaisedName(breakdown.getAddedBy().getFirstName() + " " + breakdown.getAddedBy().getLastName());

		                if (breakdown.getStatus() == 1) {
		                    breakdown.setStatusStr("Waiting for Maintenance");
		                }
		                if (breakdown.getStatus() == 2) {
		                    breakdown.setStatusStr("Waiting for Trial");
		                }
		                if (breakdown.getStatus() == 3) {
		                    breakdown.setStatusStr("Closed");
		                }
		                if (breakdown.getStatus() == 4) {
		                    breakdown.setStatusStr("Approved with deviation");
		                }

		                breakdown.setSrNo(i);
		                String ttr = "";
		                DecimalFormat decfor = new DecimalFormat("0.00000");

		                if (breakdown.getTc_tr_hr() <= 0) {
		                    ttr = "0";
		                } else {
		                    ttr = decfor.format(breakdown.getTotal_trial_hr());
		                }

		                System.out.println("ttr " + ttr);
		                breakdown.setTtr(Double.valueOf(ttr));
		                i++;
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return breakdownList;
		    }

		    
		    
}
