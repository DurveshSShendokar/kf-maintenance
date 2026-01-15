package com.kfMaintenancce.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.BreakdownCountMobileDashboard;
import com.kfMaintenancce.dto.MaintenanceCountMobileDashboard;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.model.MaintenenaceCheckPoint;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.MaintRepo;
import com.kfMaintenancce.repo.MaintenenaceCheckPointRepo;
import com.kfMaintenancce.repo.TrialRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.MachineOwnerService;
import com.kfMaintenancce.service.MaintServices;
import com.kfMaintenancce.service.SpareServices;
import com.kfMaintenancce.service.TrialServices;
import com.kfMaintenancce.service.UserAndLoginService;


@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/mobileDashboard")
public class MobileDashboardController {
	@Autowired
	BreakdownServices breakdownServices;
	@Autowired
	MachineOwnerService machineOwnerService;
	@Autowired
	UserAndLoginService userAndLoginService;
	@Autowired
	MaintServices maintServices;
	@Autowired
	SpareServices spareServices;
	@Autowired
	TrialRepo trialRepo;
	

@Autowired
BreakdownRepo breakdownRepo; 

	@Autowired
	MaintenenaceCheckPointRepo  maintenenaceCheckPointRepo ; 
	
	@Autowired
	MaintRepo maintRepo;
	
	@GetMapping(value = "/breakdownDashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	BreakdownCountMobileDashboard breakdownDashboard(@RequestParam("userId") int userId) {
		BreakdownCountMobileDashboard mobileDashboard = new BreakdownCountMobileDashboard();
		try {
			Set<Breakdown> openbreakdowns=new HashSet<>();
			Set<Breakdown> trailbreakdowns=new HashSet<>();
			Set<Breakdown> closedbreakdowns=new HashSet<>();
			int openCount=0;
			int closedCount=0;
			int trailCount=0;
			
			List<MachineOwner> machineOwner=machineOwnerService.getAllMachineOwnersByUser(userId);
			for(MachineOwner owner:machineOwner){
				List<Breakdown> openBreakDoen= breakdownServices.getOpenBreakDownByMachine(owner.getMachine().getMachine_id());
				List<Breakdown> tarilBreakDoen= breakdownServices.getTraikBreakDownByMachine(owner.getMachine().getMachine_id());
				List<Breakdown> closedBreakDoen= breakdownServices.getClosedBreakDownByMachine(owner.getMachine().getMachine_id());
				System.out.println("OPEN "+openBreakDoen.size());
				
			for(Breakdown breakdown:openBreakDoen){
				openbreakdowns.add(breakdown);
			}
			for(Breakdown breakdown:tarilBreakDoen){
				trailbreakdowns.add(breakdown);
			}
			for(Breakdown breakdown:closedBreakDoen){
				closedbreakdowns.add(breakdown);
			}
				
			}
			for(Breakdown bd:openbreakdowns){
				openCount++;
			}
			for(Breakdown bd:trailbreakdowns){
				trailCount++;
			}
			for(Breakdown bd:closedbreakdowns){
				closedCount++;
			}
			
	
			mobileDashboard.setClosedCount(closedCount);
			mobileDashboard.setOpenCount(openCount);
			mobileDashboard.setTrailCount(trailCount);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mobileDashboard;
	}
	
	@GetMapping(value = "/breakdownDashboards", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BreakdownCountMobileDashboard breakdownDashboard(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {

	    BreakdownCountMobileDashboard mobileDashboard = new BreakdownCountMobileDashboard();

	    try {
	        Set<Breakdown> openBreakdowns = new HashSet<>();
	        Set<Breakdown> trailBreakdowns = new HashSet<>();
	        Set<Breakdown> closedBreakdowns = new HashSet<>();

	        List<MachineOwner> machineOwners = machineOwnerService.getAllMachineOwnersByUser(userId);

	        for (MachineOwner owner : machineOwners) {
	            int machineId = owner.getMachine().getMachine_id();

	            List<Breakdown> openList = breakdownServices.getOpenBreakDownByMachine(machineId);
	            openList.stream()
	                    .filter(bd -> bd.getLab() != null && bd.getLab().getLab_id() == labId)
	                    .forEach(openBreakdowns::add);

	            List<Breakdown> trailList = breakdownServices.getTraikBreakDownByMachine(machineId);
	            trailList.stream()
	                    .filter(bd -> bd.getLab() != null && bd.getLab().getLab_id() == labId)
	                    .forEach(trailBreakdowns::add);

	            List<Breakdown> closedList = breakdownServices.getClosedBreakDownByMachine(machineId);
	            closedList.stream()
	                    .filter(bd -> bd.getLab() != null && bd.getLab().getLab_id() == labId)
	                    .forEach(closedBreakdowns::add);
	        }

	        mobileDashboard.setOpenCount(openBreakdowns.size());
	        mobileDashboard.setTrailCount(trailBreakdowns.size());
	        mobileDashboard.setClosedCount(closedBreakdowns.size());

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return mobileDashboard;
	}

	
	
	
	
	
	
	
	
	@GetMapping(value = "/openBreakdown", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Breakdown> openBreakdown(@RequestParam("userId") int userId) {
		List<Breakdown> breakdowns = new ArrayList<Breakdown>();
		try {
			  
			
			List<MachineOwner> machineOwner=machineOwnerService.getAllMachineOwnersByUser(userId);
			for(MachineOwner owner:machineOwner){
				List<Breakdown> openBreakDoen= breakdownServices.getOpenBreakDownByMachine(owner.getMachine().getMachine_id());
				for(Breakdown breakdown:openBreakDoen){
				
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
					breakdown.setAction_taken("");
					breakdown.setRoot_cause("");
					
					
					List<Trial> list=trialRepo.getListByBreakdownUpdate(breakdown.getBreakdown_id());
					if(list.size()!=0) {
						breakdown.setTrialResult(list.get(list.size()-1).getTrial_result());	
					}
					
					breakdowns.add(breakdown);
					
					
				}
				
			}
			
	        Collections.sort(breakdowns, Comparator.comparingInt(Breakdown::getBreakdown_id));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdowns;
	}
	
	@GetMapping(value = "/openBreakdowns", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdown> openBreakdown(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {

	    List<Breakdown> breakdowns = new ArrayList<>();

	    try {
	        List<MachineOwner> machineOwners = machineOwnerService.getAllMachineOwnersByUser(userId);

	        for (MachineOwner owner : machineOwners) {
	            List<Breakdown> openList = breakdownServices.getOpenBreakDownByMachine(owner.getMachine().getMachine_id());

	            for (Breakdown bd : openList) {
	                if (bd.getLab() != null && bd.getLab().getLab_id() == labId) {
	                    List<Breakdownupdate> updates = breakdownServices.getBrekdownUpdatesByBreakdown(bd.getBd_slip());
	                    String existingActions = updates.stream()
	                            .map(Breakdownupdate::getAction_taken)
	                            .filter(action -> action != null && !action.trim().isEmpty())
	                            .collect(Collectors.joining(", "));

	                    bd.setExtingAction(existingActions);
	                    bd.setExtingRootC(bd.getRoot_cause());
	                    bd.setAction_taken("");
	                    bd.setRoot_cause("");

	                    List<Trial> trials = trialRepo.getListByBreakdownUpdate(bd.getBreakdown_id());
	                    if (!trials.isEmpty()) {
	                        bd.setTrialResult(trials.get(trials.size() - 1).getTrial_result());
	                    }

	                    breakdowns.add(bd);
	                }
	            }
	        }

	        breakdowns.sort(Comparator.comparingInt(Breakdown::getBreakdown_id));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return breakdowns;
	}


	
	
	
	
	
	@GetMapping(value = "/closedBreakdown", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdown> closedBreakdown(@RequestParam("userId") int userId) {
	    List<Breakdown> breakdowns = new ArrayList<>();
	    try {
	        // Fetch all closed breakdowns where the user is assigned to the machine
	        List<Breakdown> closedBreakdowns = breakdownRepo.getClosedBreakdownsForAssignedEngineers(userId);
	        
	        for (Breakdown breakdown : closedBreakdowns) {
	            // Fetch spares used in the breakdown
	            List<Spares> sparesList = spareServices.getSpareByBreakdownId(breakdown.getBreakdown_id());
	            
	            List<Spares> formattedSpares = new ArrayList<>();
	            for (Spares spare : sparesList) {
	                Spares sp = new Spares();
	                sp.setSpare_name(spare.getSpare_name());
	                sp.setQty(spare.getQty());
	                formattedSpares.add(sp);
	            }
	            
	            breakdown.setSpares(formattedSpares);
	            
	            // Fetch latest trial result, if available
	            List<Trial> trialResults = trialRepo.getListByBreakdownUpdate(breakdown.getBreakdown_id());
	            if (!trialResults.isEmpty()) {
	                breakdown.setTrialResult(trialResults.get(trialResults.size() - 1).getTrial_result());
	            }
	            
	            breakdowns.add(breakdown);
	        }
	        
	        // Sort breakdowns by breakdown_id
	        breakdowns.sort(Comparator.comparingInt(Breakdown::getBreakdown_id));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return breakdowns;
	}
	
	@GetMapping(value = "/closedBreakdowns", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdown> closedBreakdown(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {

	    List<Breakdown> breakdowns = new ArrayList<>();

	    try {
	        List<Breakdown> closedList = breakdownRepo.getClosedBreakdownsForAssignedEngineers(userId);

	        for (Breakdown bd : closedList) {
	            if (bd.getLab() != null && bd.getLab().getLab_id() == labId) {

	                List<Spares> sparesList = spareServices.getSpareByBreakdownId(bd.getBreakdown_id());
	                List<Spares> formattedSpares = sparesList.stream()
	                        .map(sp -> {
	                            Spares s = new Spares();
	                            s.setSpare_name(sp.getSpare_name());
	                            s.setQty(sp.getQty());
	                            return s;
	                        })
	                        .collect(Collectors.toList());
	                bd.setSpares(formattedSpares);

	                List<Trial> trials = trialRepo.getListByBreakdownUpdate(bd.getBreakdown_id());
	                if (!trials.isEmpty()) {
	                    bd.setTrialResult(trials.get(trials.size() - 1).getTrial_result());
	                }

	                breakdowns.add(bd);
	            }
	        }

	        breakdowns.sort(Comparator.comparingInt(Breakdown::getBreakdown_id));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return breakdowns;
	}

	
	
	
	

	
	@GetMapping(value = "/trailBreakdown", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Breakdown> trailBreakdown(@RequestParam("userId") int userId) {
		List<Breakdown> breakdowns = new ArrayList<Breakdown>();
		try {
		
			
			List<MachineOwner> machineOwner=machineOwnerService.getAllMachineOwnersByUser(userId);
			for(MachineOwner owner:machineOwner){
				List<Breakdown> openBreakDoen= breakdownServices.getTraikBreakDownByMachine(owner.getMachine().getMachine_id());
				for(Breakdown breakdown:openBreakDoen){
					breakdowns.add(breakdown);
				}
				
			}
			 Collections.sort(breakdowns, Comparator.comparingInt(Breakdown::getBreakdown_id));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdowns;
	}
	
	@GetMapping(value = "/trailBreakdowns", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdown> trailBreakdown(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {

	    List<Breakdown> breakdowns = new ArrayList<>();

	    try {
	        List<MachineOwner> machineOwners = machineOwnerService.getAllMachineOwnersByUser(userId);

	        for (MachineOwner owner : machineOwners) {
	            List<Breakdown> trailList = breakdownServices.getTraikBreakDownByMachine(owner.getMachine().getMachine_id());
	            trailList.stream()
	                    .filter(bd -> bd.getLab() != null && bd.getLab().getLab_id() == labId)
	                    .forEach(breakdowns::add);
	        }

	        breakdowns.sort(Comparator.comparingInt(Breakdown::getBreakdown_id));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return breakdowns;
	}

	//**********************************************************************************************************************************************
	


	@GetMapping(value = "/maintenanceDashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaintenanceCountMobileDashboard maintenanceDashboard(@RequestParam("userId") int userId) {
	    MaintenanceCountMobileDashboard mobileDashboard = new MaintenanceCountMobileDashboard();
	    try {
	        System.out.println("USER " + userId);
	        int todayCount = 0;
	        int thisWeekCount = 0;
	        int doneCount = 0;
	        int overdueCount = 0;

	        Set<Maint> todayMaintenance1 = new HashSet<>();
	        Set<Maint> thisWeekMaintenance1 = new HashSet<>();
	        Set<Maint> overdueMaintenance1 = new HashSet<>();

	        List<MachineOwner> machineOwner = machineOwnerService.getAllMachineOwnersByUser(userId);

	        System.out.println("Machine OWN " + machineOwner.size());
	        for (MachineOwner owner : machineOwner) {
	            List<Maint> todayMaintenance = maintServices.getTodayMaintenanceByMachine(owner.getMachine().getMachine_id());
	            System.out.println("TODAY COUNT :: " + todayMaintenance.size() + " for  " + owner.getMachine().getMachine_id());

	            List<Maint> thisWeekMaintenance = maintServices.getThisWeekMaintenanceDownByMachine(owner.getMachine().getMachine_id());
	            System.out.println("THIS WEEK  COUNT :: " + thisWeekMaintenance.size() + " for  " + owner.getMachine().getMachine_id());

	            List<Maint> overdueMaintenance = maintRepo.getOverdueMaintenanceByMachine(owner.getMachine().getMachine_id());
	            System.out.println("OVERDUE COUNT :: " + overdueMaintenance.size() + " for " + owner.getMachine().getMachine_id());

	            todayMaintenance1.addAll(todayMaintenance);
	            thisWeekMaintenance1.addAll(thisWeekMaintenance);
	            overdueMaintenance1.addAll(overdueMaintenance);
	        }

	        // Fetching done maintenance count directly by userId
	        List<Maint> doneMaintenance = maintRepo.getDoneMaintenanceByUser(userId);
	        doneCount = doneMaintenance.size();

	        todayCount = todayMaintenance1.size();
	        thisWeekCount = thisWeekMaintenance1.size();
	        overdueCount = overdueMaintenance1.size();

	        mobileDashboard.setDoneCount(doneCount);
	        mobileDashboard.setThisWeekcount(thisWeekCount);
	        mobileDashboard.setTodayCount(todayCount);
	        mobileDashboard.setOverdueCount(overdueCount);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return mobileDashboard;
	}
	
	@GetMapping(value = "/maintenanceDashboards", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MaintenanceCountMobileDashboard maintenanceDashboard(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {

	    MaintenanceCountMobileDashboard mobileDashboard = new MaintenanceCountMobileDashboard();

	    try {
	        int todayCount = 0;
	        int thisWeekCount = 0;
	        int doneCount = 0;
	        int overdueCount = 0;

	        Set<Maint> todayMaintenanceSet = new HashSet<>();
	        Set<Maint> thisWeekMaintenanceSet = new HashSet<>();
	        Set<Maint> overdueMaintenanceSet = new HashSet<>();

	        List<MachineOwner> machineOwners = machineOwnerService.getAllMachineOwnersByUser(userId);

	        for (MachineOwner owner : machineOwners) {
	            int machineId = owner.getMachine().getMachine_id();

	            // === Fetch and filter today maintenance ===
	            List<Maint> todayMaintList = maintServices.getTodayMaintenanceByMachine(machineId);
	            todayMaintList.stream()
	                    .filter(maint -> maint.getLab() != null && maint.getLab().getLab_id() == labId)
	                    .forEach(todayMaintenanceSet::add);

	            // === Fetch and filter this week maintenance ===
	            List<Maint> thisWeekMaintList = maintServices.getThisWeekMaintenanceDownByMachine(machineId);
	            thisWeekMaintList.stream()
	                    .filter(maint -> maint.getLab() != null && maint.getLab().getLab_id() == labId)
	                    .forEach(thisWeekMaintenanceSet::add);

	            // === Fetch and filter overdue maintenance ===
	            List<Maint> overdueMaintList = maintRepo.getOverdueMaintenanceByMachine(machineId);
	            overdueMaintList.stream()
	                    .filter(maint -> maint.getLab() != null && maint.getLab().getLab_id() == labId)
	                    .forEach(overdueMaintenanceSet::add);
	        }

	        // === Fetch and filter done maintenance ===
	        List<Maint> doneMaintList = maintRepo.getDoneMaintenanceByUser(userId);
	        doneCount = (int) doneMaintList.stream()
	                .filter(maint -> maint.getLab() != null && maint.getLab().getLab_id() == labId)
	                .count();

	        // Set counts
	        todayCount = todayMaintenanceSet.size();
	        thisWeekCount = thisWeekMaintenanceSet.size();
	        overdueCount = overdueMaintenanceSet.size();

	        // Populate dashboard
	        mobileDashboard.setTodayCount(todayCount);
	        mobileDashboard.setThisWeekcount(thisWeekCount);
	        mobileDashboard.setDoneCount(doneCount);
	        mobileDashboard.setOverdueCount(overdueCount);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return mobileDashboard;
	}

	
	
	
	
	
	
	

	
	@GetMapping(value = "/todayMaintenance", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Maint> todayMaintenance(@RequestParam("userId") int userId) {
		List<Maint> todayMaintenance = new ArrayList<Maint>();
		try {
		
			
			List<MachineOwner> machineOwner=machineOwnerService.getAllMachineOwnersByUser(userId);
			for(MachineOwner owner:machineOwner){
				List<Maint> list= maintServices.getTodayMaintenanceByMachine(owner.getMachine().getMachine_id());
				for(Maint maint:list){
					
					System.out.println("Date :: "+maint.getStartDate());
			
					todayMaintenance.add(maint);
				}
				
				
			}
			  Collections.sort(todayMaintenance, Comparator.comparingInt(Maint::getMaint_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todayMaintenance;
	}
	
	@GetMapping(value = "/todayMaintenances", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> todayMaintenance(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {
	    List<Maint> todayMaintenance = new ArrayList<>();
	    try {
	        List<MachineOwner> machineOwner = machineOwnerService.getAllMachineOwnersByUser(userId);
	        for (MachineOwner owner : machineOwner) {
	            List<Maint> list = maintServices.getTodayMaintenanceByMachine(owner.getMachine().getMachine_id());
	            for (Maint maint : list) {
	                if (maint.getLab() != null && maint.getLab().getLab_id() == labId) {
	                    todayMaintenance.add(maint);
	                }
	            }
	        }
	        Collections.sort(todayMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return todayMaintenance;
	}

	
	
	
	
	
	@GetMapping(value = "/thisWeekMaintenance", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Maint> thisWeekMaintenance(@RequestParam("userId") int userId) {
		List<Maint> thisWeekMaintenance = new ArrayList<Maint>();
		try {
		
			
			List<MachineOwner> machineOwner=machineOwnerService.getAllMachineOwnersByUser(userId);
			
			for(MachineOwner owner:machineOwner){
				List<Maint> list= maintServices.getThisWeekMaintenanceDownByMachine(owner.getMachine().getMachine_id());
				for(Maint maint:list){
					
					thisWeekMaintenance.add(maint);
					 
				}
				System.out.println(" SIXE  "+thisWeekMaintenance.size()+" Machine "+owner.getMachine().getMachine_name());
				 Collections.sort(thisWeekMaintenance, Comparator.comparingInt(Maint::getMaint_id));
				
			}
			System.out.println(" SIXE  "+thisWeekMaintenance.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return thisWeekMaintenance;
	}
	
	@GetMapping(value = "/thisWeekMaintenances", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> thisWeekMaintenance(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {
	    List<Maint> thisWeekMaintenance = new ArrayList<>();
	    try {
	        List<MachineOwner> machineOwner = machineOwnerService.getAllMachineOwnersByUser(userId);
	        for (MachineOwner owner : machineOwner) {
	            List<Maint> list = maintServices.getThisWeekMaintenanceDownByMachine(owner.getMachine().getMachine_id());
	            for (Maint maint : list) {
	                if (maint.getLab() != null && maint.getLab().getLab_id() == labId) {
	                    thisWeekMaintenance.add(maint);
	                }
	            }
	        }
	        Collections.sort(thisWeekMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return thisWeekMaintenance;
	}

	
	
	

	

	
	@GetMapping(value = "/doneMaintenance", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> doneMaintenance(@RequestParam("userId") int userId) {
	    List<Maint> doneMaintenance = new ArrayList<>();
	    try {
	        List<Maint> list = maintRepo.getDoneMaintenanceByUser(userId); // Fetch done maintenance for assigned machines
	        
	        for (Maint main : list) {
	           
	            List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(main.getMaint_id());
	            main.setCheckpointlist(checkpoints); 
	            
	            doneMaintenance.add(main);
	        }

	     
	        Collections.sort(doneMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return doneMaintenance;
	}
	
	@GetMapping(value = "/doneMaintenances", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> doneMaintenance(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {
	    List<Maint> doneMaintenance = new ArrayList<>();
	    try {
	        List<Maint> list = maintRepo.getDoneMaintenanceByUser(userId);
	        for (Maint maint : list) {
	            if (maint.getLab() != null && maint.getLab().getLab_id() == labId) {
	                List<MaintenenaceCheckPoint> checkpoints = maintenenaceCheckPointRepo.findByMaintId(maint.getMaint_id());
	                maint.setCheckpointlist(checkpoints);
	                doneMaintenance.add(maint);
	            }
	        }
	        Collections.sort(doneMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return doneMaintenance;
	}
	
	




	@GetMapping(value = "/overdueMaintenance", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> overdueMaintenance(@RequestParam("userId") int userId) {
	    List<Maint> overdueMaintenance = new ArrayList<>();
	    try {
	        List<MachineOwner> machineOwner = machineOwnerService.getAllMachineOwnersByUser(userId);
	        Set<Integer> uniqueMachineIds = new HashSet<>();
	        for (MachineOwner owner : machineOwner) {
	            uniqueMachineIds.add(owner.getMachine().getMachine_id());
	        }
	        for (Integer machineId : uniqueMachineIds) {
	            List<Maint> list = maintRepo.getOverdueMaintenanceByMachine(machineId);
	            overdueMaintenance.addAll(list);
	        }
	        Collections.sort(overdueMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return overdueMaintenance;
	}
	
	@GetMapping(value = "/overdueMaintenances", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Maint> overdueMaintenance(
	        @RequestParam("userId") int userId,
	        @RequestParam("labId") int labId) {
	    List<Maint> overdueMaintenance = new ArrayList<>();
	    try {
	        List<MachineOwner> machineOwner = machineOwnerService.getAllMachineOwnersByUser(userId);
	        Set<Integer> uniqueMachineIds = new HashSet<>();
	        for (MachineOwner owner : machineOwner) {
	            uniqueMachineIds.add(owner.getMachine().getMachine_id());
	        }
	        for (Integer machineId : uniqueMachineIds) {
	            List<Maint> list = maintRepo.getOverdueMaintenanceByMachine(machineId);
	            for (Maint maint : list) {
	                if (maint.getLab() != null && maint.getLab().getLab_id() == labId) {
	                    overdueMaintenance.add(maint);
	                }
	            }
	        }
	        Collections.sort(overdueMaintenance, Comparator.comparingInt(Maint::getMaint_id));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return overdueMaintenance;
	}



}
