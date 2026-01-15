package com.kfMaintenancce.service;


import com.kfMaintenancce.dto.BreakdownCounts;
import com.kfMaintenancce.dto.EngineerComplaintCountDTO;
import com.kfMaintenancce.dto.EngineerComplaintRecordDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.AssetAllocationRepo;
import com.kfMaintenancce.repo.AssetInventoryRepo;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.MachineRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class Dashboard1CountServiceImpl implements Dashboard1CountService {

    @Autowired
    private MachineRepo machineRepository;
    
    @Autowired
    private ComplaintRepoOLD complaintRepository;
    
    //AssetAllocationRepo
    
    @Autowired
    private AssetAllocationRepo AssetAllocationRepository;
    
    @Autowired
    private AssetInventoryRepo assetInventoryRepository;

    
   
    @Autowired
    private BreakdownRepo breakdownRepo;

    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private MaintRepo maintRepo;
    
    @Autowired
	MachineOwnerRepo machineOwnerRepo;
    

    public int getAllocateComplaints() {
        return complaintRepository.countAllocateComplaints();// allocate complaints count
    }
    
    public int getInprocessComplaints() {
        return complaintRepository.countInprocessComplaints();// Inprocess complaints count
    }
    
   
    public int getNonAllocatedComplaintsCount() {
        return complaintRepository.countNonAllocatedComplaints(); // non-allocated complaints
    }

    @Override
    public long countMachines() {
        return machineRepository.count(); // Count all Machines
    }
    
    
    public long countComplaints() {
        return complaintRepository.count(); // Count all Complaints
    }
    
    
    public long countAssets() {
        return assetInventoryRepository.count(); // Count all Complaints
    }
    
    
    public int getOpenComplaintCount() {
        return complaintRepository.countOpenComplaints();// open complaint
    }
    
   
    public int getClosedComplaintCount() {
        return complaintRepository.countClosedComplaints();// closed complaints
    }


	public int gettodayCounts(Date date) {
	
	return complaintRepository.gettodayCounts(date);//today's complaint
}

	
	 public long countAssetsWithAllocate() {
	        return AssetAllocationRepository.countAssetssWithAllocate();//assets with Allocate 
	    }

	 public long countAssetsWithoutAllocate() {
	        long totalAssetCount = assetInventoryRepository.count();
	        long allocatedAssetCount = AssetAllocationRepository.countAssetssWithAllocate();
	        return totalAssetCount - allocatedAssetCount;
	    }

    
    public long countActiveMachines() {
        return machineRepository.countByActive(1); // 1 means the machine is active
    }

    public long countInactiveMachines() {
        return machineRepository.countByActive(0); // 0 means the machine is inactive
    }

    @Override
    public long countUsers() {
        return userDetailsRepo.count(); // Count all users
    }

    public int getBreakdownCount() {
        return (int) breakdownRepo.count(); // Count all Breakdowns
    }

   
	    public int getActiveMaintCount() {
	        return maintRepo.countActiveMaintenances();// open maintaince count
	    }
	    
	   
	    public int getInactiveMaintCount() {
	        return maintRepo.countInactiveMaintenances();// closed maintaince count
	    }
   
    public int countMaintenances() {
        return maintRepo.countTotalMaintenances(); // All count of maintenance machines
    }

    
    
	// find a machine owner by a given user id and machine id.	
	public Optional<MachineOwner> getAllMachineOwnersByUserAndMachineId(int userId, int machine_id) {
		// TODO Auto-generated method stub
		return machineOwnerRepo.getMachineOwnerByUserAndMachineId(userId,machine_id);
	}
	
	
	 public List<Maint> getDoneMaintenanceByMachineOwners(List<MachineOwner> machineOwners) {
        List<Maint> allMaintenances = new ArrayList<>();

        // Iterate over each machine owner
        for (MachineOwner machineOwner : machineOwners) {
            // Retrieve machine associated with this machine owner
            Machine machine = machineOwner.getMachine();

            // Fetch maintenance records for this machine
            List<Maint> maintenances = maintRepo.getDoneMaintenanceByMachine(machine.getMachine_id());

            // Add the maintenance records to the list
            allMaintenances.addAll(maintenances);
        }

        return allMaintenances;
    }
	   
	   //retrieving maintenance records for a given machine id
	   public List<Maint> getDoneMaintenanceByMachine(int machineId) {
	        return maintRepo.getDoneMaintenanceByMachine(machineId);
	    }
	   
		 
			public List<MachineOwner> getAllMachineOwners() {
				return machineOwnerRepo.findAll();
			}
			

			@Override
			public List<Breakdown> getBreakdownList() {
				// TODO Auto-generated method stub
				 return  breakdownRepo.findAll();
			}
				

			 public Map<String, Long> countBreakdownStatuses(List<Breakdown> breakdowns) {
			        // Group breakdowns by status and count occurrences
			        Map<Integer, Long> statusCounts = breakdowns.stream()
			                .collect(Collectors.groupingBy(Breakdown::getStatus, Collectors.counting()));

			        // Map status counts to desired keys
			        Map<String, Long> counts = new HashMap<>();
			        counts.put("closedCount", statusCounts.getOrDefault(2, 0L));
			        counts.put("openCount", statusCounts.getOrDefault(1, 0L));
			        counts.put("trialCount", statusCounts.getOrDefault(3, 0L));

			        return counts;
			    }

			
			    public int getTodayCount(LocalDate date) {
			        return maintRepo.getTodayCount(date);
			    }


				@Override
				public int getOpenMaintenanceByDates(Date startOfWeek, Date endOfWeek) {
					// TODO Auto-generated method stub
					return maintRepo.getOpenMaintenanceByDates(startOfWeek,endOfWeek);
				}


				@Override
				public int getOverduesMaintenaceByDate(Date startOfWeek) {
					// TODO Auto-generated method stub
					return maintRepo.getOverduesMaintenaceByDate(startOfWeek);
				}


				@Override
				public int getClosedMaintenanceByDate(Date startOfWeek, Date endOfWeek) {
					// TODO Auto-generated method stub
					return maintRepo.getClosedMaintenanceByDate(startOfWeek,endOfWeek);
				}


				@Override
				public List<EngineerComplaintCountDTO> getEngineersWithComplaintCounts() {
					// TODO Auto-generated method stub
					  List<UserDetails> employees = userDetailsRepo.getIIEngineers();
				        return employees.stream().map(employee -> {
				            int openComplaints = complaintRepository.countByAllocateToAndStatus(employee, 1); 
				            int allocateComplaints = complaintRepository.countByAllocateToAndStatus(employee, 2);
				            int inprocessComplaints = complaintRepository.countByAllocateToAndStatus(employee, 3);
				            int closedComplaints = complaintRepository.countByAllocateToAndStatus(employee, 4); 
				            int totalComplaints = complaintRepository.countTotalComplaintsByEngineer(employee);
				            
				            return new EngineerComplaintCountDTO(employee.getFirstName() + " " + employee.getLastName(), openComplaints, allocateComplaints, inprocessComplaints, closedComplaints, totalComplaints);
				        }).collect(Collectors.toList());
				}

				@Override
				public EngineerComplaintRecordDTO getEngineerComplaintRecords(int user_id) {
					// TODO Auto-generated method stub
					  Optional<UserDetails> optional = userDetailsRepo.findById(user_id);
				        if (optional.isPresent()) {
				        	UserDetails userDetails = optional.get();

				            List<Complaint> allComplaints = new ArrayList<>();

				            // Fetch and add open complaints
				            List<Complaint> openComplaints = complaintRepository.findByAllocateToAndStatus(userDetails, 1);
				            allComplaints.addAll(openComplaints);

				            // Fetch and add allocate complaints
				            List<Complaint> allocateComplaints = complaintRepository.findByAllocateToAndStatus(userDetails, 2);
				            allComplaints.addAll(allocateComplaints);

				            // Fetch and add in-process complaints
				            List<Complaint> inprocessComplaints = complaintRepository.findByAllocateToAndStatus(userDetails, 3);
				            allComplaints.addAll(inprocessComplaints);

				            // Fetch and add closed complaints
				            List<Complaint> closedComplaints = complaintRepository.findByAllocateToAndStatus(userDetails, 4);
				            allComplaints.addAll(closedComplaints);

				            return new EngineerComplaintRecordDTO(userDetails.getFirstName() + " " + userDetails.getLastName(), allComplaints);
				        } else {
				            return null;
				        }
				}
			
				
				
				
				@Override
				public Map<String, Object> getEngineerComplaintRecords(int user_id, int page, int size, String keyword) {
				    Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "complaintDate"));
				    Optional<UserDetails> optional = userDetailsRepo.findById(user_id);

				    if (optional.isEmpty()) {
				        throw new RuntimeException("Engineer not found with ID: " + user_id);
				    }

				    UserDetails userDetails = optional.get();
				    Page<Complaint> complaintPage;

				    if (keyword != null && !keyword.isEmpty()) {
				        // 🔍 Search + Pagination
				        complaintPage = complaintRepository.searchByEngineerAndKeyword(userDetails, keyword, pageable);
				    } else {
				        // 🔹 Simple Pagination
				        complaintPage = complaintRepository.findByEngineerPaginated(userDetails, pageable);
				    }

				    // Format time fields
				    DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
				    for (Complaint complaint : complaintPage.getContent()) {
				        if (complaint.getComplaintDate() != null) {
				            complaint.setComplaintTimeStr(dateFormat.format(complaint.getComplaintDate()));
				        } else {
				            complaint.setComplaintTimeStr("N/A");
				        }

				        if (complaint.getClosedTime() != null) {
				            complaint.setComplaintClosedTimeStr(dateFormat.format(complaint.getClosedTime()));
				        } else {
				            complaint.setComplaintClosedTimeStr("N/A");
				        }
				    }

				    Map<String, Object> response = new HashMap<>();
				    response.put("engineerName", userDetails.getFirstName() + " " + userDetails.getLastName());
				    response.put("complaints", complaintPage.getContent());
				    response.put("currentPage", complaintPage.getNumber());
				    response.put("totalItems", complaintPage.getTotalElements());
				    response.put("totalPages", complaintPage.getTotalPages());

				    return response;
				}

				@Override
				public Map<String, Long> getEngineerComplaintCount(int user_id) {
				    Optional<UserDetails> optional = userDetailsRepo.findById(user_id);
				    if (optional.isEmpty()) {
				        throw new RuntimeException("Engineer not found with ID: " + user_id);
				    }

				    UserDetails userDetails = optional.get();

				    Map<String, Long> counts = new HashMap<>();
				    counts.put("open", complaintRepository.countByAllocateToAndStatused(userDetails, 1));
				    counts.put("allocated", complaintRepository.countByAllocateToAndStatused(userDetails, 2));
				    counts.put("inProcess", complaintRepository.countByAllocateToAndStatused(userDetails, 3));
				    counts.put("closed", complaintRepository.countByAllocateToAndStatused(userDetails, 4));

				    return counts;
				}

		
}
