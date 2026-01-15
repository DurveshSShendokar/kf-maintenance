package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.MediaType
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.BreakdownChatbotDTO;
import com.kfMaintenancce.dto.BreakdownCreateDTO;
import com.kfMaintenancce.dto.MaintDashboardDTO;
import com.kfMaintenancce.dto.MaintenanceCountMobileDashboard;
import com.kfMaintenancce.dto.MaintenanceDashboardResponse;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.MachineOwnerService;
import com.kfMaintenancce.service.MaintServices;



@RestController
@CrossOrigin("*")
@RequestMapping("/breakdown_chatbot")
public class BreakdownChatbotController {
	
	
	  @Autowired
	    private BreakdownRepo breakdownRepository;
	  
	  @Autowired
	  BreakdownServices breakdownServices;
	  
	  @Autowired 
	  MachineRepo machineRepo;
	  
	  @Autowired
	  UserDetailsRepo userDetailsRepo;
	  
	  @Autowired
	  LabRepo labRepository;
	  

		@Autowired
		MaintRepo maintRepo;
		
		@Autowired
		MaintServices maintServices;
		
		@Autowired
		MaintenenaceCheckPointRepo  maintenenaceCheckPointRepo ; 
		
		@Autowired
		MachineOwnerService machineOwnerService;
		
		
	
		
		@GetMapping(value = "/maintenanceDashboardsChatbot", produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody MaintenanceDashboardResponse maintenanceDashboards(
		        @RequestParam("emailId") String emailId,
		        @RequestParam(defaultValue = "1") int page,       // page number, 0-based
		        @RequestParam(defaultValue = "10") int size    // page size
		) {
		    MaintenanceDashboardResponse response = new MaintenanceDashboardResponse();

		    try {
		        // Fetch user by email
		        UserDetails user = userDetailsRepo.findByEmailId(emailId)
		                .orElseThrow(() -> new RuntimeException("User not found with email: " + emailId));

		        List<MachineOwner> machineOwners = machineOwnerService.getAllMachineOwnersByUser(user.getId());

		        // Sets to avoid duplicates
		        Set<Maint> todaySet = new HashSet<>();
		        Set<Maint> thisWeekSet = new HashSet<>();
		        Set<Maint> overdueSet = new HashSet<>();

		        for (MachineOwner owner : machineOwners) {
		            todaySet.addAll(maintServices.getTodayMaintenanceByMachine(owner.getMachine().getMachine_id()));
		            thisWeekSet.addAll(maintServices.getThisWeekMaintenanceDownByMachine(owner.getMachine().getMachine_id()));
		            overdueSet.addAll(maintRepo.getOverdueMaintenanceByMachine(owner.getMachine().getMachine_id()));
		        }

		        List<Maint> doneList = maintRepo.getDoneMaintenanceByUser(user.getId());

		        // Helper method to convert Maint -> MaintDashboardDTO with pagination
		        List<MaintDashboardDTO> todayRecords = getPagedRecords(todaySet, page, size);
		        List<MaintDashboardDTO> thisWeekRecords = getPagedRecords(thisWeekSet, page, size);
		        List<MaintDashboardDTO> overdueRecords = getPagedRecords(overdueSet, page, size);
		        List<MaintDashboardDTO> doneRecords = getPagedRecords(doneList, page, size);

		        // Set counts (total counts, not paginated)
		        response.setTodayCount(todaySet.size());
		        response.setThisWeekCount(thisWeekSet.size());
		        response.setOverdueCount(overdueSet.size());
		        response.setDoneCount(doneList.size());

		        // Set paginated records
		        response.setTodayRecords(todayRecords);
		        response.setThisWeekRecords(thisWeekRecords);
		        response.setOverdueRecords(overdueRecords);
		        response.setDoneRecords(doneRecords);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return response;
		}

		
		private List<MaintDashboardDTO> getPagedRecords(Collection<Maint> maintList, int page, int size) {
		    List<MaintDashboardDTO> dtoList = maintList.stream()
		            .map(m -> new MaintDashboardDTO(
		                    m.getSchedule_date() != null ? m.getSchedule_date().toString() : null,
		                    m.getClosedDate() != null ? m.getClosedDate().toString() : null,
		                    m.getMachine() != null ? m.getMachine().getEqid() : null,
		                    m.getLab() != null ? m.getLab().getLabName() : null
		            ))
		            .sorted(Comparator.comparing(MaintDashboardDTO::getScheduleDate))
		            .collect(Collectors.toList());

		    int fromIndex = (page-1) * size;
		    int toIndex = Math.min(fromIndex + size, dtoList.size());

		    if (fromIndex > dtoList.size()) {
		        return Collections.emptyList();
		    }

		    return dtoList.subList(fromIndex, toIndex);
		}


	
	
	  
	  
	  
	  
	  
	  @PostMapping("/createBreakdownChatBot")
	  public ResponseEntity<Status> createBreakdownChatBot(@RequestBody BreakdownCreateDTO dto) {
	      Status status = new Status();
	      try {
	          // 1️⃣ Validate DTO
	          if (dto.getMachineEqid() == null || dto.getMachineEqid().trim().isEmpty() ||
	              dto.getUserEmailId() == null || dto.getUserEmailId().trim().isEmpty()) {
	              throw new RuntimeException("machineEqid and userEmailId are required");
	          }

	          String eqid = dto.getMachineEqid().trim();
	          String email = dto.getUserEmailId().trim();
	          String observation = dto.getObservation() != null ? dto.getObservation().trim() : "";

	          // Fetch machine by eqid
	          Machine machine = machineRepo.findByEqid(eqid)
	                  .orElseThrow(() -> new RuntimeException("Machine not found for Eqid: " + eqid));

	          Lab lab = machine.getLab();
	          if (lab == null) {
	              throw new RuntimeException("Lab not found for Machine Eqid: " + eqid);
	          }

	          // 3️⃣ Fetch User
	          UserDetails user = userDetailsRepo.findByEmailId(email)
	                  .orElseThrow(() -> new RuntimeException("User not found for email: " + email));

	          // 4️⃣ Create Breakdown
	          Breakdown breakdown = new Breakdown();
	          breakdown.setMachine(machine);
	          breakdown.setLab(lab);
	          breakdown.setAddedBy(user);
	          breakdown.setObservation(observation);
	          breakdown.setTicket_raised_time(new Date());
	          breakdown.setStatus(1); // Open
	          breakdown.setDeletes(1);

	          breakdownServices.addBreakdown(breakdown);

	          status.setCode(200);
	          status.setMessage("Breakdown created successfully for Machine Eqid: " + eqid +
	                            ", Lab: " + lab.getLabName());

	      } catch (Exception e) {
	          e.printStackTrace();
	          status.setCode(500);
	          status.setMessage("Error: " + e.getMessage());
	      }

	      return ResponseEntity.ok(status);
	  }

	  // ✅ Get All Breakdowns
	    @GetMapping("/all")
	    public ResponseEntity<Map<String, Object>> getAllBreakdowns(
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("breakdown_id").descending());
	        Page<BreakdownChatbotDTO> breakdownPage = breakdownRepository.findAllBreakdowns(pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("content", breakdownPage.getContent());
	        response.put("totalElements", breakdownPage.getTotalElements());
	        response.put("totalPages", breakdownPage.getTotalPages());
	        response.put("currentPage", breakdownPage.getNumber());

	        return ResponseEntity.ok(response);
	    }

	    // ✅ Get Open Breakdowns (Status 1)
	    @GetMapping("/open")
	    public ResponseEntity<Map<String, Object>> getOpenBreakdowns(
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("breakdown_id").descending());
	        Page<BreakdownChatbotDTO> breakdownPage = breakdownRepository.findOpenBreakdowns(pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("content", breakdownPage.getContent());
	        response.put("totalElements", breakdownPage.getTotalElements());
	        response.put("totalPages", breakdownPage.getTotalPages());
	        response.put("currentPage", breakdownPage.getNumber());

	        return ResponseEntity.ok(response);
	    }

	    // ✅ Get Trial Breakdowns (Status 2)
	    @GetMapping("/trial")
	    public ResponseEntity<Map<String, Object>> getTrialBreakdowns(
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("breakdown_id").descending());
	        Page<BreakdownChatbotDTO> breakdownPage = breakdownRepository.findTrialBreakdowns(pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("content", breakdownPage.getContent());
	        response.put("totalElements", breakdownPage.getTotalElements());
	        response.put("totalPages", breakdownPage.getTotalPages());
	        response.put("currentPage", breakdownPage.getNumber());

	        return ResponseEntity.ok(response);
	    }

	    // ✅ Get Closed Breakdowns (Status 3)
	    @GetMapping("/closed")
	    public ResponseEntity<Map<String, Object>> getClosedBreakdowns(
	            @RequestParam int page,
	            @RequestParam int size) {

	        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("breakdown_id").descending());
	        Page<BreakdownChatbotDTO> breakdownPage = breakdownRepository.findClosedBreakdowns(pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("content", breakdownPage.getContent());
	        response.put("totalElements", breakdownPage.getTotalElements());
	        response.put("totalPages", breakdownPage.getTotalPages());
	        response.put("currentPage", breakdownPage.getNumber());

	        return ResponseEntity.ok(response);
	    }
}
