package com.kfMaintenancce.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.kfMaintenancce.dto.BreakdownRepoCountDTO;
import com.kfMaintenancce.dto.BreakdownRepoDTO;
import com.kfMaintenancce.dto.BreakdownReportDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTO;
import com.kfMaintenancce.dto.BreakdownResponseDTOMob;
import com.kfMaintenancce.dto.BreakdownStatusCounts;
import com.kfMaintenancce.dto.MachineDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.BreakdownSpare;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStocking;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.model.Shift;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.BreakdownupdateRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MaintSpareRepo;
import com.kfMaintenancce.repo.MaintsparestockingRepo;
import com.kfMaintenancce.repo.ShiftRepo;
import com.kfMaintenancce.repo.SparesRepo;

@Service
public class BreakdownServicesImpl  implements BreakdownServices{

	
	@Autowired
	BreakdownRepo breakdownRepo;//
	
	@Autowired
	BreakdownupdateRepo breakdownupdateRepo;
	@Autowired
	ShiftRepo shiftRepo;
	
	@Autowired
    MaintsparestockingRepo spareStockingRepo; 
	
	@Autowired
	SparesRepo sparesRepo;
	
	@Autowired
	MaintSpareRepo maintSpareRepo;
	
	@Autowired
	MachineRepo machineRepo;

	@Override
	public List<Breakdown> getBreakDownListByMachine(int machine_id) {
		// TODO Auto-generated method stub 
		return breakdownRepo.getBreakDownListByMachine(machine_id);
	}

	@Override
	public void addBreakdown(Breakdown b) {
		// TODO Auto-generated method stub
		breakdownRepo.save(b);
	}

	@Override
	public List<Breakdown> getBreakdownList() {
		// TODO Auto-generated method stub
		return breakdownRepo.getBreakdownList();
	}

	@Override
	public void deleteBreakdown(int breakdown_id) {
		// TODO Auto-generated method stub
		Optional<Breakdown> optional=breakdownRepo.findById(breakdown_id);
		breakdownRepo.save(optional.get());
		
	}

	@Override
	public Breakdown getBreakdownById(int breakdown_id) {
		// TODO Auto-generated method stub
		Optional<Breakdown> optional =   breakdownRepo.findById(breakdown_id);
		
		if (optional.isPresent()) {
			return optional.get();
		}else{
			return null;	
		}
		
	}

	@Override
	public Breakdownupdate saveBreakdownUpdate(Breakdownupdate breakdownupdate) {
		// TODO Auto-generated method stub
		return breakdownupdateRepo.save(breakdownupdate);
	}

	@Override
	public String getNewBreakDownNo() {
		// TODO Auto-generated method stub
		
		
		
		
		String bdNo="";
		String mnyr="";
		Calendar c = Calendar.getInstance();
		int yr = c.get(Calendar.YEAR);
		int mn = c.get(Calendar.MONTH)+1;
		String year = String.valueOf(yr).substring(2,4);
		  String month = "";
		if(mn<=9){
			month="0"+String.valueOf(mn);
		}else{
			month=String.valueOf(mn);
		}
		mnyr=year+month;
		//System.out.println("Year MOnth"+mnyr);
		
		int count=breakdownRepo.getBreakdownCountByYearMonth(mnyr);
		

		if(count==0){
			bdNo=mnyr+"0001";
		}else{
			String maxNo="1"+breakdownRepo.getMaxBreakdownNoNoByYearMonth(mnyr);
			///System.out.println("MAX NO "+changeManagementRepo.getMaxChangeMangementNoByYearMonth(mnyr));
			int mxint=Integer.parseInt(maxNo);
			mxint++;
			bdNo=String.valueOf(mxint).substring(1,9);
		//	System.out.println("NEW MAX NO "+newmaxNo);

			
		}
		return bdNo;
	}

	@Override
	public List<Breakdown> getBreakdoenById(int breakdown_id) {
		// TODO Auto-generated method stub
		return breakdownRepo.getBreakdoenById(breakdown_id);
	}

	@Override
	public List<Spares> getSpareListByBreakdownUpdate(int breakdown_update_id) {
		// TODO Auto-generated method stub
		return sparesRepo.getSparesByBreakDownUpdate(breakdown_update_id);
	}

	@Override
	public Breakdownupdate getBreakDownUpdateByBreakDown(int breakdown_id) {
		// TODO Auto-generated method stub
		List<Breakdownupdate>optional= breakdownupdateRepo.getBreakDownUpdateByBreakdownId(breakdown_id);
		
		if(optional.size()!=0){
			return optional.get(0);
		}else{
			return null;
		}
			
	}

	@Override
	public List<Breakdown> getOpenBreakDownByMachine(int machine_id) {
		// TODO Auto-generated method stub
		return breakdownRepo.getOpenBreakDownByMachine(machine_id);
	}

	@Override
	public List<Breakdown> getTraikBreakDownByMachine(int machine_id) {
		// TODO Auto-generated method stub
		return breakdownRepo.getTraikBreakDownByMachine(machine_id);
	}

	@Override
	public List<Breakdown> geClosedBreakDownByUser(int userId) {
		// TODO Auto-generated method stub
		return breakdownRepo.geClosedBreakDownByUser(userId);
	}
	
	 public List<Breakdown> getOpenBreakdowns() {
	        return breakdownRepo.getOpenBreakdowns();
	    }

	    
	    public List<Breakdown> getTrialBreakdowns() {
	        return breakdownRepo.getTrialBreakdowns();
	    }

	    
	    public List<Breakdown> getClosedBreakdowns() {
	        return breakdownRepo.getClosedBreakdowns();
	    }
	
	    public List<Breakdown> getAllBreakdowns() {
	        return breakdownRepo.findAll();
	    }

		@Override
		public List<Breakdown> listByUserId(int id) {
			// TODO Auto-generated method stub
			return breakdownRepo.listByUserId(id);
		}

		@Override
		public List<Breakdown> getClosedBreakDownByMachine(int machine_id) {
			// TODO Auto-generated method stub
			return breakdownRepo.getClosedBreakDownByMachine(machine_id);
		}

		@Override
		public List<Breakdown> getBreakdownByDates(Date startDate, Date endDate) {
			// TODO Auto-generated method stub
			return breakdownRepo.getBreakdownByDates(startDate, endDate);
		}

		@Override
		public List<Breakdown> getOpenBreakdownByDates(Date startDate, Date endDate) {
			// TODO Auto-generated method stub
			return breakdownRepo.getOpenBreakdownByDates(startDate, endDate);
		}

		@Override
		public List<Breakdown> getClosedBreakdownByDates(Date startDate, Date endDate) {
			// TODO Auto-generated method stub
			return breakdownRepo.getClosedBreakdownByDates(startDate,endDate);
		}

		@Override
		public List<Breakdown> getTrailBreakdownByDates(Date startDate, Date endDate) {
			// TODO Auto-generated method stub
			return breakdownRepo.getTrailBreakdownByDates(startDate,endDate);
		}

		@Override
		public List<Breakdown> getOpenBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName) {
			// TODO Auto-generated method stub
			return breakdownRepo.getOpenBreakdownByDatesAndMachine(startDate,endDate,machineName);
		}

		@Override
		public List<Breakdown> getTrailBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName) {
			// TODO Auto-generated method stub
			return breakdownRepo.getTrailBreakdownByDatesAndMachine(startDate,endDate,machineName);
		}

		@Override
		public List<Breakdown> getClosedBreakdownByDatesAndMachine(Date startDate, Date endDate, String machineName) {
			// TODO Auto-generated method stub
			return breakdownRepo.getClosedBreakdownByDatesAndMachine(startDate,endDate,machineName);
		}

		@Override
		public List<Breakdown> getBreakdownByDatesAndMachineName(Date startDate, Date endDate, String machineName) {
			// TODO Auto-generated method stub
			return breakdownRepo.getBreakdownByDatesAndMachineName(startDate,endDate,machineName);
		}

		
		public void saveSpare(MaintSpare spare) {
		
			maintSpareRepo.save(spare);
		}

		@Override
		public List<Breakdown> getTrailBreakdownByUser(int userId) {
		
			return breakdownRepo.getTrailBreakdownByUser(userId);
		}
		
		
		  public List<BreakdownReportDTO> generateBreakdownReportByDateRange(Date startDate, Date endDate) {
		       
		        List<Breakdown> breakdowns = breakdownRepo.findByTicketRaisedTimeBetween(startDate, endDate);
		        
		       
		        List<BreakdownReportDTO> reportList = new ArrayList<>();
		        for (Breakdown breakdown : breakdowns) {
		            BreakdownReportDTO reportDTO = generateBreakdownReport(breakdown); 
		            reportList.add(reportDTO);
		        }

		        return reportList;
		    }
		//breakdown report
	 public BreakdownReportDTO generateBreakdownReport(Breakdown breakdown) {
			    BreakdownReportDTO dto = new BreakdownReportDTO();

			    dto.setBreakdown(breakdown); 
			    dto.setTicketRaisedTime(breakdown.getTicket_raised_time());
			    dto.setTicketTrialTime(breakdown.getTicket_trial_time());
			    dto.setTicketClosedTime(breakdown.getTicket_closed_time());

			    // Perform time calculations based on status
			    switch (breakdown.getStatus()) {
			        case 1: // Open
			            if (breakdown.getTicket_trial_time() != null) {
			            	double raiseToTrial = calculateTimeDifference(breakdown.getTicket_raised_time(), breakdown.getTicket_trial_time());
			                dto.setRaiseToTrial(raiseToTrial);
			            }
			           
			            break;

			        case 2: // Trial
			            if (breakdown.getTicket_trial_time() != null) {
			            	double raiseToTrial = calculateTimeDifference(breakdown.getTicket_raised_time(), breakdown.getTicket_trial_time());
			                dto.setRaiseToTrial(raiseToTrial);
			            }
			          
			            break;

			        case 3: // Closed
			            if (breakdown.getTicket_trial_time() != null) {
			            	double raiseToTrial = calculateTimeDifference(breakdown.getTicket_raised_time(), breakdown.getTicket_trial_time());
			                dto.setRaiseToTrial(raiseToTrial);
			                if (breakdown.getTicket_closed_time() != null) {
			                	double trialToClosed = calculateTimeDifference(breakdown.getTicket_trial_time(), breakdown.getTicket_closed_time());
			                    dto.setTrialToClosed(trialToClosed);
			                    double totalTime = calculateTimeDifference(breakdown.getTicket_raised_time(), breakdown.getTicket_closed_time());
			                    dto.setTotalTime(totalTime);
			                    
			                    // Calculate TBF (Time Between Failure) in days
			                    double tbfInDays = calculateDaysBetwen(breakdown.getTicket_raised_time(), breakdown.getTicket_closed_time());
			                    dto.setTBF(tbfInDays);
			                    
			                }
			            }
			            break;

			        default:
			          
			            break;
			    }

			    return dto;
			}


	    // calculate time difference in hours with minutes as decimal
	    private double calculateTimeDifference(Date start, Date end) {
	        long diffInMillis = end.getTime() - start.getTime();
	        long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
	        long hours = totalMinutes / 60;
	        long minutes = totalMinutes % 60;
	       
	       
	        // Convert to the format "hours.minutes"
	        return hours + (minutes / 100.0);
	    }
	    
	 // Calculate time difference in days (TBF)
	    private double calculateDaysBetwen(Date start, Date end) {
	        long diffInMillis = end.getTime() - start.getTime();
	        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

	        return diffInDays;
	    }
		
	    
	    public List<BreakdownReportDTO> generateAllBreakdownReports() {
	      
	        List<Breakdown> breakdowns = breakdownRepo.findAll();
	        
	     
	        List<BreakdownReportDTO> reportDTOs = new ArrayList<>();
	        for (Breakdown breakdown : breakdowns) {
	            BreakdownReportDTO dto = generateBreakdownReport(breakdown);
	            reportDTOs.add(dto);
	        }
	        
	        return reportDTOs;
	    }
	    
	    public List<BreakdownReportDTO> generateBreakdownReportsByUserAndDate(int userId, Date fromDate, Date toDate) {
	        List<Breakdown> breakdowns = breakdownRepo.findBreakdownsByUserIdAndDateRange(userId, fromDate, toDate);
	        
	        List<BreakdownReportDTO> reportDTOs = new ArrayList<>();
	        for (Breakdown breakdown : breakdowns) {
	            BreakdownReportDTO dto = generateBreakdownReport(breakdown);
	            reportDTOs.add(dto);
	        }
	        
	        return reportDTOs;
	    }

	    // ✅ 1️⃣ Pagination
	    public Page<BreakdownReportDTO> getPaginatedBreakdownReportsByUser(
	            int userId, Date fromDate, Date toDate, int page, int size) {

	        Pageable pageable = PageRequest.of(page, size, Sort.by("ticket_raised_time").descending());
	        Page<Breakdown> breakdownPage = breakdownRepo.findBreakdownsByUserIdAndDateRange(userId, fromDate, toDate, pageable);

	        // Convert to DTO
	        List<BreakdownReportDTO> dtos = breakdownPage.getContent().stream()
	                .map(this::generateBreakdownReport)
	                .toList();

	        return new PageImpl<>(dtos, pageable, breakdownPage.getTotalElements());
	    }

	    // ✅ 2️⃣ Search + Pagination
	    public Page<BreakdownReportDTO> searchPaginatedBreakdownReportsByUser(
	            int userId, Date fromDate, Date toDate, String keyword, int page, int size) {

	        Pageable pageable = PageRequest.of(page, size, Sort.by("ticket_raised_time").descending());
	        Page<Breakdown> breakdownPage = breakdownRepo.searchBreakdownsByUserIdDateAndKeyword(userId, fromDate, toDate, keyword, pageable);

	        List<BreakdownReportDTO> dtos = breakdownPage.getContent().stream()
	                .map(this::generateBreakdownReport)
	                .toList();

	        return new PageImpl<>(dtos, pageable, breakdownPage.getTotalElements());
	    }

	    // ✅ 3️⃣ Count
	    public long countBreakdownsByUserAndDate(int userId, Date fromDate, Date toDate) {
	        return breakdownRepo.countBreakdownsByUserIdAndDateRange(userId, fromDate, toDate);
	    }
	    
	    
	    
	    
	    
	    
	    
		
	    //yy analyse
	    public BreakdownRepoCountDTO getBreakdownsAndDaysBetween(int machine_id) {
	        List<Breakdown> breakdowns = breakdownRepo.getBreakdownsByMachineOrdered(machine_id);
	        List<BreakdownRepoDTO> reportList = new ArrayList<>();

	        for (int i = 0; i < breakdowns.size(); i++) {
	            Breakdown currentBreakdown = breakdowns.get(i);
	            BreakdownRepoDTO dto = new BreakdownRepoDTO();

	            dto.setBreakdown(currentBreakdown);  
	            dto.setTicketRaisedTime(currentBreakdown.getTicket_raised_time().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

	          
	            switch (currentBreakdown.getStatus()) {
	                case 1:
	                    dto.setStatus("Open");
	                    break;
	                case 2:
	                    dto.setStatus("Trial");
	                    break;
	                case 3:
	                    dto.setStatus("Closed");
	                    break;
	                default:
	                 
	                    break;
	            }

	            
	            dto.setMachineName(currentBreakdown.getMachine().getMachine_name());

	            if (i > 0) {
	                Breakdown previousBreakdown = breakdowns.get(i - 1);
	                long daysBetween = calculateDaysBetween(previousBreakdown.getTicket_raised_time(), currentBreakdown.getTicket_raised_time());
	                dto.setNoOfDays(daysBetween);
	            } else {
	                dto.setNoOfDays(0L); 
	            }

	            reportList.add(dto);
	        }

	        // Return the wrapper DTO with breakdown list and total count
	        return new BreakdownRepoCountDTO(reportList, reportList.size());
	    }
	    // for filter
	    public BreakdownRepoCountDTO getBreakdownsAndDaysBetween(int machine_id, Date fromDate, Date toDate) {
	        List<Breakdown> breakdowns;

	        if (fromDate != null && toDate != null) {
	            breakdowns = breakdownRepo.getBreakdownsByMachineOrdered(machine_id, fromDate, toDate);
	        } else {
	            breakdowns = breakdownRepo.getBreakdownsByMachineOrdered(machine_id);
	        }

	        List<BreakdownRepoDTO> reportList = new ArrayList<>();

	        for (int i = 0; i < breakdowns.size(); i++) {
	            Breakdown currentBreakdown = breakdowns.get(i);
	            BreakdownRepoDTO dto = new BreakdownRepoDTO();

	            dto.setBreakdown(currentBreakdown);
	            dto.setTicketRaisedTime(currentBreakdown.getTicket_raised_time()
	                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

	            switch (currentBreakdown.getStatus()) {
	                case 1: dto.setStatus("Open"); break;
	                case 2: dto.setStatus("Trial"); break;
	                case 3: dto.setStatus("Closed"); break;
	            }

	            dto.setMachineName(currentBreakdown.getMachine().getMachine_name());

	            if (i > 0) {
	                Breakdown previousBreakdown = breakdowns.get(i - 1);
	                long daysBetween = calculateDaysBetween(previousBreakdown.getTicket_raised_time(),
	                        currentBreakdown.getTicket_raised_time());
	                dto.setNoOfDays(daysBetween);
	            } else {
	                dto.setNoOfDays(0L);
	            }

	            reportList.add(dto);
	        }

	        return new BreakdownRepoCountDTO(reportList, reportList.size());
	    }

	    
	    
	    
	    
	    
	    
	    
	    

	    private long calculateDaysBetween(Date start, Date end) {
	        long diffInMillies = end.getTime() - start.getTime();
	        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    }
	    
	    
	    
	    public BreakdownStatusCounts getBreakdownCountsByMachineId(int machineId) {
	        long openCount = breakdownRepo.countByMachineIdAndStatus(machineId, 1);
	        long trialCount = breakdownRepo.countByMachineIdAndStatus(machineId, 2);
	        long closedCount = breakdownRepo.countByMachineIdAndStatus(machineId, 3);

	        List<Breakdown> openDetails = breakdownRepo.findByMachineIdAndStatus(machineId, 1);
	        List<Breakdown> trialDetails = breakdownRepo.findByMachineIdAndStatus(machineId, 2);
	        List<Breakdown> closedDetails = breakdownRepo.findByMachineIdAndStatus(machineId, 3);

	        return new BreakdownStatusCounts(openCount, trialCount, closedCount, openDetails, trialDetails, closedDetails);
	    }
	    
	    
	    public BreakdownStatusCounts getBreakdownCountsByMachineId(int machineId, Date fromDate, Date toDate) {
	        long openCount = breakdownRepo.countByMachineIdAndStatusWithDate(machineId, 1, fromDate, toDate);
	        long trialCount = breakdownRepo.countByMachineIdAndStatusWithDate(machineId, 2, fromDate, toDate);
	        long closedCount = breakdownRepo.countByMachineIdAndStatusWithDate(machineId, 3, fromDate, toDate);

	        List<Breakdown> openDetails = breakdownRepo.findByMachineIdAndStatusWithDate(machineId, 1, fromDate, toDate);
	        List<Breakdown> trialDetails = breakdownRepo.findByMachineIdAndStatusWithDate(machineId, 2, fromDate, toDate);
	        List<Breakdown> closedDetails = breakdownRepo.findByMachineIdAndStatusWithDate(machineId, 3, fromDate, toDate);

	        return new BreakdownStatusCounts(openCount, trialCount, closedCount, openDetails, trialDetails, closedDetails);
	    }

	    
	

	    @Transactional
	    public List<BreakdownResponseDTO> findAndMarkDuplicates() {
	        List<Breakdown> allBreakdowns = breakdownRepo.findAll();
	        List<BreakdownResponseDTO> responseDTOs = new ArrayList<>();
	        Set<Integer> processedBreakdowns = new HashSet<>(); 
	        
	        

	        for (Breakdown breakdown : allBreakdowns) {
	          
	            if (processedBreakdowns.contains(breakdown.getBreakdown_id())) {
	                continue;
	            }

	            // Find all duplicates for this breakdown
	            List<Breakdown> duplicates = breakdownRepo.findExactDuplicates(
	                    breakdown.getMachine().getMachine_id(),
	                    breakdown.getRoot_cause(),
	                    breakdown.getTicket_raised_time()
	            );

	            if (duplicates.size() > 1) {
	                // The first breakdown in the list is the original
	                Breakdown original = duplicates.get(0);
	                original.setDuplicateFlag(0);  
	                breakdownRepo.save(original);  
	                
	           System.out.println("Original Breakdown: " + original.getBreakdown_id() + ", Machine: " 
	                + original.getMachine().getMachine_name());

	                // Add the original breakdown to the response list
	                responseDTOs.add(convertToDTO(original, false)); 

	                // Loop through the remaining duplicates and mark them
	                for (int i = 1; i < duplicates.size(); i++) {
	                    Breakdown duplicate = duplicates.get(i);
	                    duplicate.setDuplicateFlag(1); 
	                    breakdownRepo.save(duplicate);  
	                    
	                    
	                  System.out.println("Duplicate Breakdown: " + duplicate.getBreakdown_id() + ", Machine: " 
	                    + duplicate.getMachine().getMachine_name());

	                    // Add the duplicate breakdown to the response list
	                    responseDTOs.add(convertToDTO(duplicate, true));  
	                }

	                // Mark all these breakdowns as processed to avoid adding them again
	                for (Breakdown bd : duplicates) {
	                    processedBreakdowns.add(bd.getBreakdown_id());
	                }
	            }
	        }

	        return responseDTOs;
	    }

	    public BreakdownResponseDTO convertToDTO(Breakdown breakdown, boolean isDuplicate) {
	        BreakdownResponseDTO dto = new BreakdownResponseDTO();
	        dto.setBreakdown_id(breakdown.getBreakdown_id());
	        dto.setRootCause(breakdown.getRoot_cause());
	        dto.setTicketRaisedTime(breakdown.getTicket_raised_time().toString());
	        dto.setDuplicateFlag(breakdown.getDuplicateFlag());
	        dto.setAddedBy(breakdown.getAddedBy());

	        // Set machine details
	        MachineDTO machineDTO = new MachineDTO();
	        machineDTO.setMachine_id(breakdown.getMachine().getMachine_id());
	        machineDTO.setMachine_name(breakdown.getMachine().getMachine_name());
	        dto.setMachine(machineDTO);

	        return dto;
	    }
	    
	    
	    
	    public BreakdownResponseDTOMob getBreakdownDetailsById(int breakdownId) {
	      
	        Breakdown breakdown = breakdownRepo.findById(breakdownId)
	                .orElseThrow(() -> new RuntimeException("Breakdown not found"));

	       
	        Machine machine = machineRepo.findById(breakdown.getMachine().getMachine_id()) 
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        
	    
	        Shift shift = shiftRepo.findById(breakdown.getShift().getShift_id()) 
	                .orElseThrow(() -> new RuntimeException("Shift not found"));

	       
	        BreakdownResponseDTOMob breakdownDTO = new BreakdownResponseDTOMob();
	        breakdownDTO.setBreakdownId(breakdown.getBreakdown_id());
	        breakdownDTO.setBdSlip(breakdown.getBd_slip());
	        breakdownDTO.setStatus(breakdown.getStatus());
	        breakdownDTO.setTicketClosedFlag(breakdown.getTicket_closed_flag());
	        breakdownDTO.setDetectedBy(breakdown.getDetected_by());
	     //   breakdownDTO.setDoneBy(breakdown.getDone_by());
	        breakdownDTO.setRootCause(breakdown.getRoot_cause());
	        breakdownDTO.setObservation(breakdown.getObservation());
	        breakdownDTO.setActionTaken(breakdown.getPrev_action_plan());
	        
	        breakdownDTO.setRaisedDate(breakdown.getTicket_raised_time());
	        breakdownDTO.setClosedDate(breakdown.getTicket_closed_time());
	        breakdownDTO.setTrialDate(breakdown.getTicket_trial_time());

	       
	        List<BreakdownResponseDTOMob.BreakdownSpareDTO> spareDTOs = breakdown.getBreakdownSpares().stream()
	                .map(this::mapToBreakdownSpareDTO)
	                .collect(Collectors.toList());
	        breakdownDTO.setBreakdownSpares(spareDTOs);

	         
	        breakdownDTO.setMachine(machine);
	        breakdownDTO.setShift(shift);

	        return breakdownDTO;
	    }

	    // Helper method for mapping a BreakdownSpare to DTO
	    private BreakdownResponseDTOMob.BreakdownSpareDTO mapToBreakdownSpareDTO(BreakdownSpare spare) {
	        BreakdownResponseDTOMob.SpareDTO spareDTO = new BreakdownResponseDTOMob.SpareDTO();
	        spareDTO.setMaintspareId(spare.getSpare().getMaintspare_id());
	        spareDTO.setSpareName(spare.getSpare().getSpare_name());
	        spareDTO.setSupplierName(spare.getSpare().getSupplier_name());
	        spareDTO.setCode(spare.getSpare().getCode());

	        BreakdownResponseDTOMob.BreakdownSpareDTO breakdownSpareDTO = new BreakdownResponseDTOMob.BreakdownSpareDTO();
	        breakdownSpareDTO.setId(spare.getId());
	        breakdownSpareDTO.setSpare(spareDTO);

	        return breakdownSpareDTO;
	    }

		@Override
		public List<Breakdownupdate> getBrekdownUpdatesByBreakdown(String bd_slip) {
			// TODO Auto-generated method stub
			return breakdownupdateRepo.getBrekdownUpdatesByBreakdown(bd_slip);
		}

		@Override
		public Optional<Breakdown> getBreakdownID(int breakdown_id) {
			// TODO Auto-generated method stub
			return breakdownRepo.findById(breakdown_id);
		}
		
		
		
		

		public Double calculateTotalConsumedCost(Breakdown breakdown) {
		    List<MaintSpareStocking> spareStockings = spareStockingRepo.findAll(); 
		    double totalCost = 0.0;

		    for (BreakdownSpare spare : breakdown.getBreakdownSpares()) {
		        totalCost += spare.getConsumedCost(spareStockings);
		    }

		    return totalCost;
		}

		
}
