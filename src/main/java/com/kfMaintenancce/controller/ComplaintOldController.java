package com.kfMaintenancce.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.EngComplaintDTO;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Image;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.SpareConsumption;
import com.kfMaintenancce.model.SpareStock;
import com.kfMaintenancce.repo.ComplaintRepo;
import com.kfMaintenancce.repo.ImageRepository;
import com.kfMaintenancce.repo.NotificationRepo;
import com.kfMaintenancce.repo.SpareConsumptionRepo;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.service.ComplaintServiceOLD;

@RestController
@CrossOrigin("*")
@RequestMapping("/complaintsOLD")
public class ComplaintOldController {

    @Autowired
    private ComplaintServiceOLD complaintService;
    
    @Autowired
    private ComplaintRepo complaintRepo;
    
	@Autowired
	NotificationRepo notificationRepo;
	
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private SpareConsumptionRepo spareConsumptionRepo;

//    @PostMapping("/create")
//    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) {
//        Complaint createdComplaint = complaintService.createComplaint(complaint);
//        return new ResponseEntity<>(createdComplaint, HttpStatus.CREATED);
//    }
    
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

    
    
	 @GetMapping({"/getAllocatedComplaintByEnginner"})
	  @ResponseBody
	  public EngComplaintDTO getAllocatedComplaintByEnginner(@RequestParam("empId") int empId) {
		 EngComplaintDTO  engComplaintDTO = new EngComplaintDTO();
	    try {
	    	List<Complaint>  list1 = this.complaintService.getAllocatedComplaintByEnginner(empId);
	    	
	    	for(Complaint complaint:list1) {
				List<Spare> spares= new ArrayList<Spare>();
	    		List<SpareConsumption> consumptions=spareConsumptionRepo.getSpareConsumptionByComplaintID(complaint.getComp_id());
	    		for(SpareConsumption spareConsumption:consumptions) {
	    			Spare spare=spareConsumption.getSpare();
	    			spare.setQuantity(spareConsumption.getQuantity());
	    			spares.add(spare);
	    			
	    		}
	    		complaint.setConsumedspareList(spares);
	    		System.out.println("Complaints ss :: "+complaint.getStatus());
	    	}
	    	List<Complaint>  newComplaint= new ArrayList<Complaint>();
	    	List<Complaint>  inprogressComplaint= new ArrayList<Complaint>();
	    	List<Complaint>  closedComplaint= new ArrayList<Complaint>();
	    	for(Complaint complaint:list1) {
	    		if(complaint.getStatus()==2||complaint.getStatus()==1) {
	    			newComplaint.add(complaint);
	    		}
	    		if(complaint.getStatus()==3) {
	    			inprogressComplaint.add(complaint);
	    		}
	    		if(complaint.getStatus()==4) {
	    			closedComplaint.add(complaint);
	    		}
	    	}
	    	System.out.println("Complaints ss :: "+list1.size());
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
			
			 List<Complaint> inProcessComplaints=new ArrayList<Complaint>();
			 
			 for(Complaint complaint:complaints) {
					List<Spare> spares= new ArrayList<Spare>();
		    		List<SpareConsumption> consumptions=spareConsumptionRepo.getSpareConsumptionByComplaintID(complaint.getComp_id());
		    		for(SpareConsumption spareConsumption:consumptions) {
		    			spares.add(spareConsumption.getSpare());
		    		}
		    		complaint.setConsumedspareList(spares);
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
           String newTicketNo=complaintService.getNewTicketNo();
           System.out.println("CoMPAINT "+complaint.toString());
           complaint.setStatus(1);
            // Now create the complaint
           System.out.println("newTicketNo "+newTicketNo);
           System.out.println("TICKET NO "+complaint.getTicketNo());
           complaint.setTicketNo(newTicketNo);
            Complaint createdComplaint = complaintService.createComplaint(complaint);
            String message="New Complaint has created with  Ticket No : "+newTicketNo+" for Machine "+complaint.getAssetInventory().getComapnyName()+" "+complaint.getAssetInventory().getModel()+"-"+complaint.getAssetInventory().getEquipmentId()+" on "+new Date();
			  Notification notification= new Notification();
			  notification.setNotificationDept("IT");
			  notification.setNotificationFor("Admin");
			  notification.setMessage(message);
			  notification.setRaisedTime(new Date());
			  notification.setTitle("New Complaint Createded");
			  notification.setViewed(0);
			  notificationRepo.save(notification);
            
            return ResponseEntity.ok(createdComplaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add complaint: " + e.getMessage());
        }
    }
    @PostMapping("/updateComplaint")
    public ResponseEntity<?> updateComplaint(@RequestBody Complaint complaint) {
        try {
            // Set the status to 2 (Allocate)
            //complaint.setStatus(2);
        	if(complaint.getSpares()==null) {
        		 System.out.println("Spa");
        	}
            System.out.println("size "+complaint.getSpares());
        	 if(complaint.getSpares().size()!=0) {
                 for(Spare spare : complaint.getSpares()) {
                     SpareConsumption spareConsumption = new SpareConsumption(); 
                     spareConsumption.setComplaint(complaint);
                     spareConsumption.setConsuptionDate(new Date());
                     spareConsumption.setConsuption_by(null);
                     spareConsumption.setSpare(spare);
                     spareConsumption.setQuantity(spare.getQuantity());
                     System.out.println("Spare "+spare.toString());
                    // spareConsumption.setSpare(spare.getSpare());
                     Optional<SpareConsumption> optional= spareConsumptionRepo.getSpareConsumptionByComplaintIDAndSpareId(complaint.getComp_id(),spare.getSpare_id());
                     // Save the spare consumption
                     if(!optional.isPresent()) {
                    	  spareConsumption = spareConsumptionRepo.save(spareConsumption);
                     }else {
                    	 SpareConsumption consumption=optional.get();
                    	 consumption.setQuantity(spare.getQuantity());
                    	 spareConsumptionRepo.save(consumption);
                     }
                   
                 }
             }else {
            	 System.out.println("SPARES IS NULL");
             }
            Complaint updatedComplaint = complaintService.updateComplaint1(complaint);
            
            return ResponseEntity.ok(updatedComplaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to allocate complaint: " + e.getMessage());
        }
    }
    
    @PostMapping("/updateComplaintEng")
    public ResponseEntity<?> updateComplaintEng(@RequestBody Complaint complaint) {
        try {
            // Set the status to 2 (Allocate)
            //complaint.setStatus(2);
            System.out.println("size "+complaint.getSpares().size());
        	 if(complaint.getSpares().size() != 0) {
                 for(Spare spare : complaint.getSpares()) {
                     SpareConsumption spareConsumption = new SpareConsumption(); 
                     spareConsumption.setComplaint(complaint);
                     spareConsumption.setConsuptionDate(new Date());
                     spareConsumption.setSpare(spare);
                     System.out.println("QTY............................................. "+spare.getQuantity());
                     spareConsumption.setQuantity(spare.getQuantity());
                     spareConsumption.setConsuption_by(complaint.getAllocateTo().getFirstName()+" "+complaint.getAllocateTo().getLastName());
                     Optional<SpareConsumption>  optional1=spareConsumptionRepo.getSpareConsumptionByComplaintIDAndSpareId(complaint.getComp_id(), spare.getSpare_id());
                    // spareConsumption.setSpare(spare.getSpare());
                     System.out.println("size "+spareConsumption.toString());
                     if(!optional1.isPresent()) {
                    	 spareConsumption = spareConsumptionRepo.save(spareConsumption);
                     }
                     // Save the spare consumption
                   
                 }
             }
        	 
        	 System.out.println("Complaint Description "+complaint.toString());
            Complaint updatedComplaint = complaintService.saveComplaint(complaint);
            System.out.println("updated ,.......................");
            return ResponseEntity.ok(updatedComplaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to allocate complaint: " + e.getMessage());
        }
    }

    @PostMapping("/allocateCompliant")
    public ResponseEntity<?> allocateComplaint(@RequestBody Complaint complaint) {
        try {
        	complaint.setAllocateDate(new Date());
        	complaint.setAllocateTime(new Date());
        	complaint.setStatus(2);
        	complaint.setAllocateTo(complaint.getAllocateTo());
            complaintService.createComplaint(complaint);
            String message="New Complaint has Allocate to you with  Ticket No : "+complaint.getTicketNo()+" for Machine "+complaint.getAssetInventory().getComapnyName()+" "+complaint.getAssetInventory().getModel()+"-"+complaint.getAssetInventory().getEquipmentId()+" on "+new Date();
			  Notification notification= new Notification();
			  notification.setNotificationDept("IT");
			  notification.setNotificationFor("User");
			  notification.setNotificationForSpecId(complaint.getAllocateTo().getId());
			  notification.setMessage(message);
			  notification.setRaisedTime(new Date());
			  notification.setTitle("New Complaint Createded");
			  notification.setViewed(0);
			  notificationRepo.save(notification);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to allocate complaint: " + e.getMessage());
        }
    }
    @GetMapping("/Allocate")
    public ResponseEntity<List<Complaint>> getAllocateComplaints() {
        try {
            List<Complaint> AllocateComplaints = complaintService.getAllocateComplaints();
            return ResponseEntity.ok(AllocateComplaints);
        } catch (Exception e) {
        	 e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/nonAllocated")
    public ResponseEntity<List<Complaint>> getNonAllocatedComplaints() {
        try {
            List<Complaint> nonAllocatedComplaints = complaintService.getNonAllocatedComplaints();
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
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    
    
    
    @GetMapping("/unallocatedComplaints")
    public ResponseEntity<List<Complaint>> getunallocatedComplaints() {
        List<Complaint> complaints = complaintService.getunallocatedComplaints();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }
    
	
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

    
    
    
    
    
    
    // Update an existing complaint

    @PutMapping("/update/{comp_id}")
    public ResponseEntity<?> updateComplaint(@PathVariable int comp_id, @RequestBody Complaint updatedComplaint) {
        try {
            Complaint complaint = complaintService.updateComplaint(comp_id, updatedComplaint);
            return ResponseEntity.ok(complaint);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/open")
    public ResponseEntity<List<Complaint>> getOpenComplaints() {
        try {
            List<Complaint> openComplaints = complaintService.getOpenComplaints();
            return ResponseEntity.ok(openComplaints);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/closed")
    public ResponseEntity<List<Complaint>> getClosedComplaints() {
        try {
            List<Complaint> closedComplaints = complaintService.getClosedComplaints();
            return ResponseEntity.ok(closedComplaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
    @GetMapping("/Inprocess")
    public ResponseEntity<List<Complaint>> getInprocessComplaints() {
        try {
            List<Complaint> InprocessComplaints = complaintService.getInprocessComplaints();
            return ResponseEntity.ok(InprocessComplaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
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
    
    
    
    
    @GetMapping("/ticket_report")
    //http://localhost:8082/complaints/ticket_report?fromDate=2024-07-03&toDate=2024-07-10
    //http://localhost:8082/complaints/ticket_report?selectedDate=2024-07-09
    public List<Complaint> getTicketResolutionReport(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate) {
        List<Complaint> reportDTOs = complaintService.getComplaintReport(fromDate, toDate, selectedDate);
        return reportDTOs;
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
    
    // Upload multiple images associated with a complaint
    @PostMapping("/{comp_id}/upload-images")
    public ResponseEntity<String> uploadImages(
            @PathVariable int comp_id,
            @RequestParam("files") MultipartFile[] files,
            @RequestHeader("userId") int userId) {
        
        try {
            complaintService.uploadComplaintImages(comp_id, files, userId);
            return new ResponseEntity<>("Images uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error uploading images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Retrieve images for a complaint
    @GetMapping("/{comp_id}/images")
    public ResponseEntity<byte[]> getComplaintImage(@PathVariable int comp_id) {
        Optional<Image> imageOptional = complaintService.findComplaintImageById(comp_id);

        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  
                    .body(image.getImageData());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

 
    }
