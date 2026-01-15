package com.kfMaintenancce.controller;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.kfMaintenancce.model.Notification;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.ComplaintStatusDTO;
import com.kfMaintenancce.dto.EngComplaintDTO;
import com.kfMaintenancce.dto.EngineerComplaintRecordDTO;
import com.kfMaintenancce.dto.EngineerComplaint_DTO;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.ComplaintEnginneersAllocations;

import com.kfMaintenancce.model.Lab;

import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareConsumption;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.model.Upload_IT_Manual;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.AssetSpareAssignRepository;
import com.kfMaintenancce.repo.ComplaintEnginneersAllocationsRepo;
import com.kfMaintenancce.repo.ComplaintRepo;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.NotificationRepo;

import com.kfMaintenancce.repo.SpareConsumptionRepo;
import com.kfMaintenancce.repo.SpareStockRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.repo.uploadITManualRepo;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.service.ComplaintService;
import com.kfMaintenancce.service.ComplaintServiceOLD;
import com.kfMaintenancce.service.Dashboard1CountService;

@RestController
@CrossOrigin("*")
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
    
    @Autowired
    private NotificationRepo notificationRepo;
    
  @Autowired
  		uploadITManualRepo UploadITManualRepo;
    
    @Autowired
    private AssetSpareAssignRepository assetSpareAssignRepository;
    
    @Autowired
    private SpareConsumptionRepo spareConsumptionRepo;
    
    @Autowired
    private Dashboard1CountService dashboardCountService;
    @Autowired
    ComplaintRepo complaintRepo;
    
    @Autowired
    private ComplaintRepoOLD complaintRepos;
    
    @Autowired
    ComplaintEnginneersAllocationsRepo complaintEnginneersAllocationsRepo;
    
    @Autowired
    SpareStockRepo spareStockRepo;
    
    @Autowired
    UserDetailsRepo userDetailsRepo;

    
	 @GetMapping({"/getAllConsumedSpareByComplaint"})
	  @ResponseBody
	  public List<SpareConsumption> getAllConsumedSpareByComplaint(@RequestParam("compId") int compId) {
		 List<SpareConsumption>  list = new ArrayList<SpareConsumption>();
	    try {
	   list = this.spareConsumptionRepo.getSpareConsumptionByComplaintID(compId);
	    	
	    	
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    return list;
	  }

	 
	 @GetMapping("/complaintByCompId/{comp_id}")
	 public ResponseEntity<Complaint> getComplaintById(@PathVariable int comp_id) {
	     Optional<Complaint> complaintOpt = complaintRepo.findById(comp_id);
	     if (complaintOpt.isPresent()) {
	         return ResponseEntity.ok(complaintOpt.get());
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     }
	 }

	 
	 @GetMapping("/complaintByTicketNo/{ticketNo}")
	 public ResponseEntity<Complaint> getComplaintByTicketNo(@PathVariable String ticketNo) {
	     Complaint complaint = complaintRepo.findByTicketNo(ticketNo);
	     if (complaint != null) {
	         return ResponseEntity.ok(complaint);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     }
	 }

    
    
	 @GetMapping({"/getAllocatedComplaintByEnginner"})
	  @ResponseBody
	  public EngComplaintDTO getAllocatedComplaintByEnginner(@RequestParam("empId") int empId) {
		 EngComplaintDTO  engComplaintDTO = new EngComplaintDTO();
	    try {
	    	List<Complaint>  list1 = this.complaintService.getAllocatedComplaintByEnginner(empId);
    		DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");  

	    	System.out.println("COMPLAINT BY : ENGINEER ");
	    	for(Complaint complaint:list1) {
				List<Spare> spares= new ArrayList<Spare>();
	    		List<SpareConsumption> consumptions=spareConsumptionRepo.getSpareConsumptionByComplaintID(complaint.getComp_id());
	    		for(SpareConsumption spareConsumption:consumptions) {
	    			Spare spare=spareConsumption.getSpare();
	    			spare.setQuantity(spareConsumption.getQuantity());
	    			spares.add(spare);
	    			
	    		}
	    		complaint.setConsumedspareList(spares);
	    		
	    		
	    		
	    		
	    		
	    		System.out.println("COM "+complaint.getTicketNo()+" ::  "+complaint.getCause()+"  R  "+complaint.getRootCause());
	    	}
	    	List<Complaint>  newComplaint= new ArrayList<Complaint>();
	    	List<Complaint>  inprogressComplaint= new ArrayList<Complaint>();
	    	List<Complaint>  closedComplaint= new ArrayList<Complaint>();
	    	for(Complaint complaint:list1) {
	    		String comlaintDate = dateFormat.format(complaint.getComplaintDate());  
	    		complaint.setComplaintTimeStr(comlaintDate);
	    		
	    		
	    		if(complaint.getStatus()==2||complaint.getStatus()==1) {
	    			newComplaint.add(complaint);
	    		}
	    		if(complaint.getStatus()==3) {
	    			String inprocessDate = dateFormat.format(complaint.getInProcessTime());  
	    			System.out.println("IN process TIME ;;;;;;;;;;;;;;;;;;;;;;  "+complaint.getInProcessTime());
		    		complaint.setComplaintInprocessTimeStr(inprocessDate);
		    		
		    		
	    			inprogressComplaint.add(complaint);
	    		}
	    		if(complaint.getStatus()==4) {
	    			String inprocessDate = dateFormat.format(complaint.getInProcessTime());  
		    		complaint.setComplaintInprocessTimeStr(inprocessDate);
		    		
		    		
	    			String closedDate = dateFormat.format(complaint.getClosedTime());  
		    		complaint.setComplaintClosedTimeStr(closedDate);
		    		
	    			closedComplaint.add(complaint);
	    		}
	    		
	    	}
	    	//System.out.println("Complaints ss :: "+list1.size());
	    	engComplaintDTO.setOpenComplaints(newComplaint);
	    	engComplaintDTO.setInProcessComplaints(inprogressComplaint);
	    	engComplaintDTO.setCloedComplaints(closedComplaint);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    return engComplaintDTO;
	  }

	 
	 @GetMapping({"/getOtherComplaintsByAssetInventory"})
	  @ResponseBody
	  public EngComplaintDTO getAllocatedComplaintByEnginner(@RequestParam("equipmentId") String equipmentId) {
		 EngComplaintDTO  engComplaintDTO = new EngComplaintDTO();
	    try {
	    	List<Complaint>  complaints = this.complaintService.getComplaintByEquipmentId(equipmentId);
	    	List<Complaint> openComplaints=new ArrayList<Complaint>();
	    	DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");  
			 List<Complaint> inProcessComplaints=new ArrayList<Complaint>();
			 
			 for(Complaint complaint:complaints) {
					List<Spare> spares= new ArrayList<Spare>();
		    		List<SpareConsumption> consumptions=spareConsumptionRepo.getSpareConsumptionByComplaintID(complaint.getComp_id());
		    		for(SpareConsumption spareConsumption:consumptions) {
		    			spares.add(spareConsumption.getSpare());
		    		}
		    		complaint.setConsumedspareList(spares);
		    		String strDate = dateFormat.format(complaint.getComplaintDate());  
		    		complaint.setComplaintTimeStr(strDate);
		    	}
			 List<Complaint> CloedComplaints=new ArrayList<Complaint>();
	    	for(Complaint complaint:complaints)  {
				if(complaint.getStatus()==2||complaint.getStatus()==1) {
					openComplaints.add(complaint);
	    		}
	    		if(complaint.getStatus()==3) {
	    			inProcessComplaints.add(complaint);
	    		}
	    		if(complaint.getStatus()==4) {
	    			CloedComplaints.add(complaint);
	    		}
	    		String strDate = dateFormat.format(complaint.getComplaintDate());  
	    		complaint.setComplaintTimeStr(strDate);
			}
	    	engComplaintDTO.setOpenComplaints(openComplaints);
	    	engComplaintDTO.setInProcessComplaints(inProcessComplaints);
	    	engComplaintDTO.setCloedComplaints(CloedComplaints);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    return engComplaintDTO;
	  }


	 @PostMapping("/addComplaint")
	 public ResponseEntity<?> createComplaint(@RequestBody Complaint complaint) {
	     try {
	         String newTicketNo = complaintService.getNewTicketNo();
	         complaint.setStatus(1);
	         complaint.setTicketNo(newTicketNo);
	         Complaint createdComplaint = complaintService.createComplaint(complaint);
	         
	      // Debugging statements

	         System.out.println("AssetInventory Model: " + createdComplaint.getAssetInventory().getModel());
	         
	         
	         // Create notification for admin
	         Notification notification = new Notification();
	            String message = "A new complaint has been created, Ticket No: " + createdComplaint.getTicketNo() ;
	         notification.setMessage(message);
	         notification.setNotificationfor("ADMIN");
	         notification.setNotificationDept("IT");
	         notification.setRaisedTime(new Date());
	         notification.setTitle("New Complaint Created: " + createdComplaint.getTicketNo());
	         notification.setViewed(0); // Assuming default value for not viewed
	         
	         
	         System.out.println("NOTIFACTION .............................................. "+notification.toString());
	         notificationRepo.save(notification);

	         return ResponseEntity.ok(createdComplaint);
	     } catch (Exception e) {
	         e.printStackTrace(); 
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add complaint: " + e.getMessage());
	     }
	 }
	 




	 @PostMapping("/updateComplaint")
	 public ResponseEntity<?> updateComplaint(@RequestBody Complaint complaint) {
	     Map<String, Object> response = new HashMap<>();

	     try {
	         // ============================
	         // 1. Validate Complaint Exists
	         // ============================
	         Optional<Complaint> optionalComplaint = complaintRepo.findById(complaint.getComp_id());
	         if (!optionalComplaint.isPresent()) {
	             response.put("status", "error");
	             response.put("message", "Complaint not found.");
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	         }

	         Complaint existingComplaint = optionalComplaint.get();

	         // ============================
	         // 2. Prevent Reopening Closed Complaint
	         // ============================
	         if (existingComplaint.getStatus() == 4 && complaint.getStatus() != 4) {
	             response.put("status", "error");
	             response.put("message", "Complaint is already closed and cannot be modified.");
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	         }

	         // ============================
	         // 3. Spare Consumption Handling
	         // ============================
	         if (complaint.getSpares() != null && !complaint.getSpares().isEmpty()) {

	             for (Spare spare : complaint.getSpares()) {

	                 Optional<SpareStock> optionalStock =
	                         spareStockRepo.getSpareStockBySpare(spare.getSpare_id());

	                 if (!optionalStock.isPresent()) {
	                     response.put("status", "error");
	                     response.put("message", "Spare stock not found for: " + spare.getSpare_name());
	                     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	                 }

	                 SpareStock spareStock = optionalStock.get();

	                 double availableQuantity = spareStock.getAvailableQuantity();

	                 // Stock validation
	                 if (spare.getQuantity() > availableQuantity) {
	                     response.put("status", "error");
	                     response.put("message",
	                             "Insufficient stock for spare: " + spare.getSpare_name() +
	                             ". Available: " + availableQuantity +
	                             ", Requested: " + spare.getQuantity());

	                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	                 }

	                 // Update stock
	                 spareStock.setAvailableQuantity(availableQuantity - spare.getQuantity());
	                 spareStockRepo.save(spareStock);

	                 // Spare consumption update (DO NOT ADD)
	                 Optional<SpareConsumption> optionalConsumption =
	                         spareConsumptionRepo.getSpareConsumptionByComplaintIDAndSpareId(
	                                 complaint.getComp_id(),
	                                 spare.getSpare_id()
	                         );

	                 if (optionalConsumption.isPresent()) {
	                     // Replace quantity (not add)
	                     SpareConsumption existingConsumption = optionalConsumption.get();
	                     existingConsumption.setQuantity(spare.getQuantity());
	                     spareConsumptionRepo.save(existingConsumption);
	                 } else {
	                     // New consumption entry
	                     SpareConsumption spareConsumption = new SpareConsumption();
	                     spareConsumption.setComplaint(existingComplaint);
	                     spareConsumption.setConsuptionDate(new Date());
	                     spareConsumption.setConsuption_by(null);
	                     spareConsumption.setSpare(spare);
	                     spareConsumption.setQuantity(spare.getQuantity());

	                     spareConsumptionRepo.save(spareConsumption);
	                 }
	             }
	         }

	         // ============================
	         // 4. Set Timestamps
	         // ============================
	         if (complaint.getStatus() == 3) {
	             existingComplaint.setInProcessDate(new Date());
	             existingComplaint.setInProcessTime(new Date());
	         }

	         if (complaint.getStatus() == 4) {
	             existingComplaint.setClosedDate(new Date());
	             existingComplaint.setClosedTime(new Date());
	         }

	         // ============================
	         // 5. Update Other Fields
	         // ============================
	         existingComplaint.setStatus(complaint.getStatus());
	         existingComplaint.setPriority(complaint.getPriority());
	         existingComplaint.setCause(complaint.getCause());
	         existingComplaint.setRootCause(complaint.getRootCause());
	         existingComplaint.setConclusion(complaint.getConclusion());
	         existingComplaint.setDescription(complaint.getDescription());
	         existingComplaint.setTicketNo(complaint.getTicketNo());

	         // ============================
	         // 6. Save Complaint
	         // ============================
	         complaintRepo.save(existingComplaint);

	         response.put("status", "success");
	         response.put("message", "Complaint updated successfully.");
	         response.put("complaint", existingComplaint);

	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         e.printStackTrace();
	         response.put("status", "error");
	         response.put("message", "Failed to update complaint: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }

	 
	 
	 @PostMapping("/updateComplaintEng")
	 public ResponseEntity<?> updateComplaintEng(@RequestBody Complaint complaint) {
	     try {
	         if (complaint.getSpares() != null && !complaint.getSpares().isEmpty()) {
	             for (Spare spare : complaint.getSpares()) {
	                 Optional<SpareStock> optionalStock = spareStockRepo.getSpareStockBySpare(spare.getSpare_id());
	                 if (optionalStock.isPresent()) {
	                     SpareStock spareStock = optionalStock.get();
	                     double availableQuantity = spareStock.getAvailableQuantity();

	                     // Check if requested quantity is available
	                     if (spare.getQuantity() > availableQuantity) {
	                         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                 .body("Insufficient stock for spare: " + spare.getSpare_name() +
	                                         ". Available: " + availableQuantity +
	                                         ", Requested: " + spare.getQuantity());
	                     }

	                     // Deduct consumed quantity
	                     spareStock.setAvailableQuantity(availableQuantity - spare.getQuantity());
	                     spareStock.setStockUpdatedDate(new Date());
	                     spareStockRepo.save(spareStock);

	                     // Log spare consumption
	                     SpareConsumption spareConsumption = new SpareConsumption();
	                     spareConsumption.setComplaint(complaint);
	                     spareConsumption.setSpare(spare);
	                     spareConsumption.setQuantity(spare.getQuantity());
	                     spareConsumption.setConsuptionDate(new Date());
	                     spareConsumption.setConsuption_by(complaint.getAllocateTo().getFirstName() + " " + complaint.getAllocateTo().getLastName());

	                     // Check for existing consumption entry
	                     Optional<SpareConsumption> existingConsumption = spareConsumptionRepo
	                             .getSpareConsumptionByComplaintIDAndSpareId(complaint.getComp_id(), spare.getSpare_id());
	                     if (existingConsumption.isPresent()) {
	                         SpareConsumption existing = existingConsumption.get();
	                         existing.setQuantity(existing.getQuantity() + spare.getQuantity());
	                         spareConsumptionRepo.save(existing);
	                     } else {
	                         spareConsumptionRepo.save(spareConsumption);
	                     }
	                 } else {
	                     return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Spare stock not found for spare: " + spare.getSpare_name());
	                 }
	             }
	         }

	         // Update complaint status dates
	         if (complaint.getStatus() == 3) {
	             complaint.setInProcessDate(new Date());
	             complaint.setInProcessTime(new Date());
	         }
	         if (complaint.getStatus() == 4) {
	             complaint.setClosedDate(new Date());
	             complaint.setClosedTime(new Date());
	         }

	         complaintRepo.save(complaint);
	         return ResponseEntity.ok(complaint);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .body("Failed to allocate complaint: " + e.getMessage());
	     }
	 }


	 
	 @PostMapping("/selfAssignComplaint")
	 public ResponseEntity<?> selfAssignComplaint(
	         @RequestParam int complaintId,
	         @RequestParam int engineerId,
	         @RequestParam String allocatedBy) {

	     try {
	         // 1. Fetch complaint
	         Complaint complaint = complaintRepo.findById(complaintId)
	                 .orElseThrow(() -> new RuntimeException("Complaint not found"));

	         // 2. Fetch engineer
	         UserDetails engineer = userDetailsRepo.findById(engineerId)
	                 .orElseThrow(() -> new RuntimeException("Engineer not found"));

	         // 3. Create allocation record
	         ComplaintEnginneersAllocations allocation = new ComplaintEnginneersAllocations();
	         allocation.setComplaint(complaint);
	         allocation.setEngineer(engineer);
	         allocation.setAllocationDate(new Date());
	         allocation.setStatus("Allocated");
	         allocation.setAllocatedBy(allocatedBy);

	         complaintEnginneersAllocationsRepo.save(allocation);

	         // 4. Update complaint allocateTo
	         complaint.setAllocateTo(engineer);
	         complaint.setAllocateDate(new Date());
	         complaint.setAllocateTime(new Date());
	         complaintRepo.save(complaint);
	         
	         // Step 5: Create and save notification
	         Notification notification = new Notification();
	         String message = "A New Complaint Has Been SelfAssign, Ticket No: " + complaint.getTicketNo();
	         notification.setMessage(message);
	         notification.setNotificationfor("Engineer");
	         notification.setNotificationDept("IT");
	         notification.setNotificationForSepeId(engineer.getId());
	         notification.setRaisedTime(new Date());
	         notification.setTitle("New Complaint SelfAssign " + complaint.getTicketNo());

	         notificationRepo.save(notification);


	         // ✅ JSON Response
	         Map<String, Object> response = new HashMap<>();
	         response.put("status", "success");
	         response.put("statusCode", "200");
	         response.put("Message", "Self-Assign Successfully");
	        // response.put("data", complaint);
	        

	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         Map<String, Object> error = new HashMap<>();
	         error.put("status", "error");
	         error.put("message", e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	     }
	 }
	 
	 

@PostMapping("/allocateCompliant")
public ResponseEntity<?> allocateComplaint(@RequestBody Complaint complaint) {
    try {
        // Step 1: Find the existing complaint
        Optional<Complaint> optionalComplaint = complaintRepo.findById(complaint.getComp_id());
        if (!optionalComplaint.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Complaint not found with ID: " + complaint.getComp_id());
        }

        Complaint existingComplaint = optionalComplaint.get();

        // Step 2: Fetch engineer (UserDetails) from DB to avoid TransientPropertyValueException
        int engineerId = complaint.getAllocateTo().getId();
        UserDetails engineer = userDetailsRepo.findById(engineerId)
            .orElseThrow(() -> new RuntimeException("Engineer not found with ID: " + engineerId));

        // Step 3: Update complaint with allocation details
        existingComplaint.setAllocateDate(new Date());
        existingComplaint.setAllocateTime(new Date());
        existingComplaint.setStatus(2); // Allocated
        existingComplaint.setAllocateTo(engineer);

        // Step 4: Save allocation record
        ComplaintEnginneersAllocations complaintEnginneersAllocations = new ComplaintEnginneersAllocations();
        complaintEnginneersAllocations.setAllocationDate(new Date());
        complaintEnginneersAllocations.setComplaint(existingComplaint);
        complaintEnginneersAllocations.setEngineer(engineer);
        complaintEnginneersAllocations.setStatus("Allocated");
        complaintEnginneersAllocationsRepo.save(complaintEnginneersAllocations);

        // Step 5: Save updated complaint
        complaintRepo.save(existingComplaint);

        // Step 6: Create and save notification
        Notification notification = new Notification();
        String message = "A new complaint has been allocated, Ticket No: " + existingComplaint.getTicketNo();
        notification.setMessage(message);
        notification.setNotificationfor("Engineer");
        notification.setNotificationDept("IT");
        notification.setNotificationForSepeId(engineer.getId());
        notification.setRaisedTime(new Date());
        notification.setTitle("New Complaint Allocated " + existingComplaint.getTicketNo());

        notificationRepo.save(notification);

        // Step 7: Return success response
        return ResponseEntity.ok(existingComplaint);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Failed to allocate complaint: " + e.getMessage());
    }
}
///////////////////////////////////////////////////
@GetMapping("/getReAllocateComplaints")
public ResponseEntity<List<ComplaintEnginneersAllocations>> getReAllocateComplaints() {
    try {
        List<ComplaintEnginneersAllocations> allocatedComplaints =
                complaintEnginneersAllocationsRepo.findByStatus("Allocated");

        return ResponseEntity.ok(allocatedComplaints);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

@GetMapping("/getReAllocateComplaintsWithPage")
public ResponseEntity<Page<ComplaintEnginneersAllocations>> getReAllocateComplaints(
        @RequestParam int pageNo,
        @RequestParam int perPage) {

    try {
        PageRequest pageable = PageRequest.of(pageNo - 1, perPage);
        Page<ComplaintEnginneersAllocations> page = complaintEnginneersAllocationsRepo.findByStatusd("Allocated", pageable);
        return ResponseEntity.ok(page);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
@GetMapping("/getReAllocateComplaintsWithSearch")
public ResponseEntity<Page<ComplaintEnginneersAllocations>> getReAllocateComplaintsSearch(
        @RequestParam int pageNo,
        @RequestParam int perPage,
        @RequestParam(defaultValue = "") String keyword) {

    try {
        PageRequest pageable = PageRequest.of(pageNo - 1, perPage);

        Page<ComplaintEnginneersAllocations> page;
        if (keyword == null || keyword.trim().isEmpty()) {
            page = complaintEnginneersAllocationsRepo.findByStatusd("Allocated", pageable);
        } else {
            page = complaintEnginneersAllocationsRepo.findByStatusAndSearchd("Allocated", keyword.toLowerCase(), pageable);
        }

        return ResponseEntity.ok(page);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

@GetMapping("/getReAllocateComplaintsWithCount")
public ResponseEntity<Long> getReAllocateComplaintsCount(@RequestParam(required = false) String keyword) {
    try {
        long count;
        if (keyword == null || keyword.trim().isEmpty()) {
            count = complaintEnginneersAllocationsRepo.countByStatus("Allocated");
        } else {
            count = complaintEnginneersAllocationsRepo.countByStatusAndSearch("Allocated", keyword.toLowerCase());
        }

        return ResponseEntity.ok(count);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
    }
}


////////////////////////////////////////////////////////////////////////

@PostMapping("/reAllocateComplaint")
public ResponseEntity<?> reAllocateComplaint(
        @RequestParam int complaintId,
        @RequestParam int engineerId) {
    try {
        // 1. Fetch existing complaint
        Complaint existingComplaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));

        // 2. Fetch engineer
        UserDetails engineer = userDetailsRepo.findById(engineerId)
                .orElseThrow(() -> new RuntimeException("Engineer not found with ID: " + engineerId));

        // 3. Update complaint allocation
        existingComplaint.setAllocateDate(new Date());
        existingComplaint.setAllocateTime(new Date());
        existingComplaint.setStatus(2); // assuming 2 = allocated
        existingComplaint.setAllocateTo(engineer);

        // 4. Release all previous allocations (not just one!)
        List<ComplaintEnginneersAllocations> allocations =
                complaintEnginneersAllocationsRepo.findByComplaintId(existingComplaint.getComp_id());

        for (ComplaintEnginneersAllocations alloc : allocations) {
            alloc.setStatus("Released");
            alloc.setReleaseDate(new Date());
            complaintEnginneersAllocationsRepo.save(alloc);
        }

        // 5. Save new allocation
        ComplaintEnginneersAllocations newAllocation = new ComplaintEnginneersAllocations();
        newAllocation.setAllocationDate(new Date());
        newAllocation.setComplaint(existingComplaint);
        newAllocation.setEngineer(engineer);
        newAllocation.setStatus("Allocated");
        complaintEnginneersAllocationsRepo.save(newAllocation);

        // 6. Save updated complaint
        complaintRepo.save(existingComplaint);

        // 7. Create notification
        Notification notification = new Notification();
        String message = "A complaint has been reallocated, Ticket No: " + existingComplaint.getTicketNo();
        notification.setMessage(message);
        notification.setNotificationfor("Engineer");
        notification.setNotificationDept("IT");
        notification.setNotificationForSepeId(engineer.getId());
        notification.setRaisedTime(new Date());
        notification.setTitle("Re Complaint Allocated " + existingComplaint.getTicketNo());
        notificationRepo.save(notification);

        // 8. Return JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Reallocated successfully");
        response.put("complaintId", complaintId);
        response.put("newEngineerId", engineerId);

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Failed to reallocate complaint: " + e.getMessage()));
    }
}


    
    // ---------------------------------------------cards api -------------------------------------//
    
    @GetMapping("/open")
    public ResponseEntity<List<Complaint>> getOpenComplaints() {
        try {
            List<Complaint> openComplaints = complaintRepos.findOpenComplaints();
            return ResponseEntity.ok(openComplaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/closed")
    public ResponseEntity<List<Complaint>> getClosedComplaints() {
        try {
            List<Complaint> closedComplaints = complaintRepos.findClosedComplaints();
            return ResponseEntity.ok(closedComplaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
    @GetMapping("/Inprocess")
    public ResponseEntity<List<Complaint>> getInprocessComplaints() {
        try {
            List<Complaint> InprocessComplaints = complaintRepos.findInprocessComplaints();
            return ResponseEntity.ok(InprocessComplaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/Allocate")
    public ResponseEntity<List<Complaint>> getAllocateComplaints() {
        try {
            List<Complaint> AllocateComplaints = complaintRepos.findAllocateComplaints();
            return ResponseEntity.ok(AllocateComplaints);
        } catch (Exception e) {
        	 e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/nonAllocated")
    public ResponseEntity<List<Complaint>> getNonAllocatedComplaints() {
        try {
            List<Complaint> nonAllocatedComplaints = complaintRepos.findNonAllocatedComplaints();
            return ResponseEntity.ok(nonAllocatedComplaints);
        } catch (Exception e) {
        	 e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    
    @GetMapping("/getComplaintByRaisedBy")
    public ResponseEntity<List<Complaint>> getComplaintByRaisedBy(@RequestParam("userId") int userId) {
        List<Complaint> complaints = complaintService.getComplaintByRaisedBy(userId);
   //     System.out.println("CHEM .////////////////////////////////////////");
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    
    
    ////////////////////////////////////////
    @GetMapping("/unallocatedComplaints")
    public ResponseEntity<List<Complaint>> getunallocatedComplaints() {
        List<Complaint> complaints = complaintService.getunallocatedComplaints();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    @GetMapping("/unallocatedComplaintsWithCount")
    public ResponseEntity<Long> getUnallocatedComplaintsCount(
            @RequestParam(required = false) String keyword) {

        long count = complaintService.getUnallocatedComplaintsCount(keyword);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/unallocatedComplaintsWithSearch")
    public ResponseEntity<List<Complaint>> getUnallocatedComplaintsSearch(
            @RequestParam int pageNo,
            @RequestParam int perPage,
            @RequestParam(defaultValue = "") String keyword) {

        List<Complaint> complaints = complaintService.getUnallocatedComplaintsSearch(pageNo, perPage, keyword);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    @GetMapping("/unallocatedComplaintsWithPage")
    public ResponseEntity<Page<Complaint>> getUnallocatedComplaints(
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Page<Complaint> complaintsPage = complaintService.getUnallocatedComplaints(pageNo, perPage);
        return new ResponseEntity<>(complaintsPage, HttpStatus.OK);
    }
/////////////////////////////////////////////////////////

    
	//----------------cards api ------------------------------------------//
    
    
    
    
	@RequestMapping(value = "/getComplaintByLimit", method = RequestMethod.GET)
	public @ResponseBody List<Complaint> getComplaintByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Complaint> list = new ArrayList<Complaint>();
		try {
			list = complaintService.getComplaintByLimit(pageNo,perPage);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getComplaintCount", method = RequestMethod.GET)
	public @ResponseBody int getComplaintCount() {
		int count = 0;
		try {
				count = (int) complaintService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getComplaintByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody List<Complaint> getComplaintByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<Complaint> list = new ArrayList<Complaint>();
		try {
			
			list = complaintService.getComplaintByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getComplaintCountByLimitAndGroupSearch", method = RequestMethod.POST)
	public @ResponseBody int getComplaintCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = complaintService.getComplaintCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
    
    // Update an existing complaint#
    @PutMapping("/update/{comp_id}")
    public ResponseEntity<?> updateComplaint(@PathVariable int comp_id, @RequestBody Complaint updatedComplaint) {
        try {
            Complaint complaint = complaintService.updateComplaint(comp_id, updatedComplaint);
            return ResponseEntity.ok(complaint);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    
  
    
    // ---------------------for export cards-------------------------------//
    @GetMapping("/export/open")
    public ResponseEntity<InputStreamResource> exportOpenComplaints(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return exportComplaints(complaintService.getOpenComplaintsByDate(startDate, endDate), "Open_Complaints.xlsx");
    }

    @GetMapping("/export/closed")
    public ResponseEntity<InputStreamResource> exportClosedComplaints(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return exportComplaints(complaintService.getClosedComplaintsByDate(startDate, endDate), "Closed_Complaints.xlsx");
    }

    @GetMapping("/export/inprocess")
    public ResponseEntity<InputStreamResource> exportInProcessComplaints(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return exportComplaints(complaintService.getInprocessComplaintsByDate(startDate, endDate), "Inprocess_Complaints.xlsx");
    }

    @GetMapping("/export/allocated")
    public ResponseEntity<InputStreamResource> exportAllocatedComplaints(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return exportComplaints(complaintService.getAllocateComplaintsByDate(startDate, endDate), "Allocated_Complaints.xlsx");
    }

    @GetMapping("/export/nonallocated")
    public ResponseEntity<InputStreamResource> exportNonAllocatedComplaints(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return exportComplaints(complaintService.getNonAllocatedComplaintsByDate(startDate, endDate), "NonAllocated_Complaints.xlsx");
    }

    private ResponseEntity<InputStreamResource> exportComplaints(List<Complaint> complaints, String filename) {
        try {
            ByteArrayInputStream in = complaintService.exportComplaintsToExcel(complaints);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + filename);
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(in));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    /// ----------------------------------------------------------------------------------------------//
    
    
    
    
    
    //----------------------------------------- IT reports -----------------------------------------------//
    
    
    //complaint summary report
    @GetMapping("/filter")
    //http://localhost:8082/complaints/filter?status=1&filterType=date&fromDate=2024-07-01&toDate=2024-07-12
    public ResponseEntity<List<Complaint>> filterComplaints(
            @RequestParam int status,
            @RequestParam String filterType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate)
           
          {

        List<Complaint> complaints = complaintService.filterComplaints(status, filterType, date, fromDate, toDate);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/filterWithPageandSearch")
    public ResponseEntity<Map<String, Object>> filterComplaints(
            @RequestParam int status,
            @RequestParam String filterType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String keyword) {

        Map<String, Object> response = complaintService.filterComplaintsed(status, filterType, date, fromDate, toDate, page, size, keyword);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/filterWithcount")
    public ResponseEntity<Long> countComplaints(
            @RequestParam int status,
            @RequestParam String filterType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        long count = complaintService.countComplaints(status, filterType, date, fromDate, toDate);
        return ResponseEntity.ok(count);
    }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    
    
    
    //
    @GetMapping("/filter_export")
    public void exportComplaintsToExcel(
            @RequestParam int status,
            @RequestParam String filterType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            HttpServletResponse response) throws IOException {
        complaintService.exportFilteredComplaints(status, filterType, date, fromDate, toDate, response);
    }
    
    
    
    //ticket report
    @GetMapping("/ticket_report")
    //http://localhost:8082/complaints/ticket_report?fromDate=2024-07-03&toDate=2024-07-10
    //http://localhost:8082/complaints/ticket_report?selectedDate=2024-07-09
    public ResponseEntity<List<TicketResolutionReportDTO>> getTicketResolutionReport(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate) {
    	
    	System.out.println("FROM DATE "+fromDate);
    	
    	System.out.println("To  DATE "+toDate);
    	
    	
    	System.out.println("selectedDate "+selectedDate);
    	
        List<TicketResolutionReportDTO> reportDTOs = complaintService.getTicketResolutionReport(fromDate, toDate, selectedDate);
        return ResponseEntity.ok(reportDTOs);
    }
    
    @GetMapping("/ticket_reportWithPageandSearch")
    public ResponseEntity<Map<String, Object>> getTicketResolutionReport(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String keyword) {

        Map<String, Object> report = complaintService.getTicketResolutionReportPaged(fromDate, toDate, selectedDate, page, size, keyword);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/ticket_reportWithPage")
    public ResponseEntity<Map<String, Object>> getTicketResolutionReportpage(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate,
            @RequestParam int page,
            @RequestParam int size
           ) {

        Map<String, Object> report = complaintService.getTicketResolutionReportPaged(fromDate, toDate, selectedDate, page, size, null);
        return ResponseEntity.ok(report);
    }

    
    @GetMapping("/ticket_reportWithCount")
    public ResponseEntity<Map<String, Long>> getTicketReportCount(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate) {

        long count = complaintService.getTicketResolutionCount(fromDate, toDate, selectedDate);
        Map<String, Long> response = new HashMap<>();
        response.put("totalTickets", count);
        return ResponseEntity.ok(response);
    }

    
    
    
    
    
    
    
    
    @GetMapping("/ticket_export")
    //export for ticket_report
    public ResponseEntity<InputStreamResource> generateExcelReport(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate) {

        List<TicketResolutionReportDTO> reports = complaintService.getTicketResolutionReport(fromDate, toDate, selectedDate);
        ByteArrayInputStream in = complaintService.generateExcelReport(reports);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ticket_resolution_report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }
    
    
    
    
    //engineer wise complaint status report
    @GetMapping("/engineerComplaintRecords/{user_id}")
    public ResponseEntity<List<Complaint>> getEngineerComplaintRecords(@PathVariable int user_id) {
        EngineerComplaintRecordDTO engineerComplaintRecord = dashboardCountService.getEngineerComplaintRecords(user_id);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");  

        if (engineerComplaintRecord != null) {
            List<Complaint> allComplaints = engineerComplaintRecord.getAllComplaints();
            for (Complaint complaint : allComplaints) {
                if (complaint.getComplaintDate() != null) {
                    String strDate = dateFormat.format(complaint.getComplaintDate());  
                    complaint.setComplaintTimeStr(strDate);
                } else {
                    complaint.setComplaintTimeStr("N/A"); // or handle it as needed
                }
                
                if (complaint.getClosedTime() != null) {
                    String closedDate = dateFormat.format(complaint.getClosedTime());  
                    complaint.setComplaintClosedTimeStr(closedDate);
                } else {
                    complaint.setComplaintClosedTimeStr("N/A"); // or handle it as needed
                }
            }
            return ResponseEntity.ok(allComplaints);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/engineerComplaintRecordsWithCount/{user_id}")
    public ResponseEntity<Map<String, Long>> getEngineerComplaintCount(@PathVariable int user_id) {
        Map<String, Long> response = dashboardCountService.getEngineerComplaintCount(user_id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/engineerComplaintRecordsWithSearch/{user_id}")
    public ResponseEntity<Map<String, Object>> searchEngineerComplaintRecords(
            @PathVariable int user_id,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "") String keyword) {

        Map<String, Object> response = dashboardCountService.getEngineerComplaintRecords(user_id, page, size, keyword);
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/engineerComplaintRecordsWithPage/{user_id}")
    public ResponseEntity<Map<String, Object>> getEngineerComplaintRecords(
            @PathVariable int user_id,
            @RequestParam int page,
            @RequestParam int size) {

        Map<String, Object> response = dashboardCountService.getEngineerComplaintRecords(user_id, page, size, null);
        return ResponseEntity.ok(response);
    }

    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Engineer Wise Complaint Status Report
    @GetMapping("/engineerComplaintRecords/{allocateTo}/export")
    public ResponseEntity<InputStreamResource> exportEngineerComplaintRecords(@PathVariable UserDetails allocateTo) {
        // Fetch the report data for the specified engineer
        List<EngineerComplaint_DTO> repo = complaintService.getEngineerComplaintRecords(allocateTo);
        
        // Generate the Excel report
        ByteArrayInputStream in = complaintService.generateExcelRepo(repo);
        
        // Prepare the HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Engineer_Complaints.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    
    //----------------------------------------------------------------------------------------------//
    
    @GetMapping("/complaint_spares/{complaintId}")
    public ResponseEntity<?> getSparesByComplaintId(@PathVariable("complaintId") int complaintId) {
        try {
            List<SpareConsumption> spares = spareConsumptionRepo.findSparesByComplaintId(complaintId);
            if (spares.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No spares found for the specified complaint ID.");
            }
            return ResponseEntity.ok(spares);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retrieve spares: " + e.getMessage());
        }
    }
    
   // complaint assign by asset id (dropdown)
    @GetMapping("/complaint_spare/{asset_inventory_id}")
    public ResponseEntity<?> getSparesByassetInventoryID(@PathVariable("asset_inventory_id") int asset_inventory_id) {
        try {
            List<AssetSpareAssign> spares = assetSpareAssignRepository.findByassetInventoryID(asset_inventory_id);
            if (spares.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No spares found for the specified complaint ID.");
            }
            return ResponseEntity.ok(spares);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retrieve spares: " + e.getMessage());
        }
    }
    
    
    
    @GetMapping("/downloadUserManualByAsset/{assetInventoryId}")
    public ResponseEntity<Resource> downloadUserManualByAsset(@PathVariable("assetInventoryId") int assetInventoryId) {
        // Fetch the list of Upload_IT_Manual based on the AssetInventory ID
        List<Upload_IT_Manual> manuals = UploadITManualRepo.findByAssetInventoryId(assetInventoryId);
        
        if (manuals != null && !manuals.isEmpty()) {
            // You can decide which manual to download (e.g., the first one or some logic based on criteria)
            Upload_IT_Manual manual = manuals.get(0); // Example: taking the first one
            
            // Check if the manual is active (active = 1)
            if (manual.getActive() == 1) {
                File file = new File(manual.getUserManualPath());
                
                if (file.exists()) {
                    Resource resource = new FileSystemResource(file);
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(manual.getFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + manual.getUserManualName() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(null);
                }
            } else {
                // Return 403 Forbidden if the file is inactive
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
    
    
    @GetMapping("/viewUploadedDocumentByAsset/{assetInventoryId}")
    public ResponseEntity<Resource> viewUploadedDocumentByAsset(@PathVariable("assetInventoryId") int assetInventoryId) throws FileNotFoundException {
        List<Upload_IT_Manual> manuals = UploadITManualRepo.findByAssetInventoryIdAndActive(assetInventoryId, 1);
        
        if (manuals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no manuals found
        }

        // Get the first manual or handle accordingly
        Upload_IT_Manual manual = manuals.get(0);
        
        File file = new File(manual.getUserManualPath());
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if the file does not exist
        }
        
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(manual.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + manual.getUserManualName() + "\"")
                .contentLength(file.length())
                .body(resource);
    }


    @GetMapping("/complaint_export")
    public ResponseEntity<byte[]> exportComplaintsToExcel(@RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate,
                                                          HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Ensure the correct format
        
        Date start = null;
        Date end = null;

        if (startDate != null && !startDate.isEmpty()) {
            try {
                start = sdf.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle exception
            }
        }
        
        if (endDate != null && !endDate.isEmpty()) {
            try {
                end = sdf.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle exception
            }
        }

        List<Complaint> complaints;

        if (start != null && end != null) {
            // Fetch complaints using date range filter
            complaints = complaintRepo.findByComplaintDateBetween(start, end);
        } else {
            // No date filters, fetch all complaints
            complaints = complaintRepo.findAll();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Complaints");

        // Create header row.
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Sr No", "Ticket No", "Asset", "Priority", "Cause", "Description", 
                            "Complaint Date", "Complaint Time", "Allocate To", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Populate data rows.
        int rowIndex = 1;
        for (Complaint complaint : complaints) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(rowIndex - 1);
            row.createCell(1).setCellValue(complaint.getTicketNo());
            row.createCell(2).setCellValue(complaint.getAssetInventory().getComapnyName() + "-" + complaint.getAssetInventory().getEquipmentId());
            row.createCell(3).setCellValue(complaint.getPriority());
            row.createCell(4).setCellValue(complaint.getCause());
            row.createCell(5).setCellValue(complaint.getDescription());
            row.createCell(6).setCellValue(complaint.getComplaintDate() != null ? complaint.getComplaintDate().toString() : "");
            row.createCell(7).setCellValue(complaint.getComplaintTime() != null ? complaint.getComplaintTime().toString() : "");
            row.createCell(8).setCellValue(complaint.getAllocateTo() != null ? complaint.getAllocateTo().getFirstName() + "-" + complaint.getAllocateTo().getLastName() : "");
            row.createCell(9).setCellValue(complaint.getStatus());
        }

        // Adjust column widths.
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write data to byte array.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // Prepare response entity.
        HttpHeaders headersForResponse = new HttpHeaders();
        headersForResponse.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=complaints.xlsx");
        return ResponseEntity.ok()
                .headers(headersForResponse)
                .body(out.toByteArray());
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
    
    
    
    @GetMapping("/filtered_cards")
    public Map<String, ComplaintStatusDTO> getFilteredComplaints(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return complaintService.getFilteredComplaints(startDate, endDate);
    }
    
}
