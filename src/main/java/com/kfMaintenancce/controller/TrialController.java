package com.kfMaintenancce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.MachineOwner;
import com.kfMaintenancce.model.Notification;
import com.kfMaintenancce.model.Role;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.model.Trial;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.BreakdownupdateRepo;
import com.kfMaintenancce.repo.MachineOwnerRepo;
import com.kfMaintenancce.repo.NotificationRepo;
import com.kfMaintenancce.repo.SparesRepo;
import com.kfMaintenancce.repo.TrialRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.TrialServices;

import ch.qos.logback.classic.Logger;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/trial")

public class TrialController {

	@Autowired
	TrialServices trialServices;

	@Autowired
	BreakdownServices breakdownServices;
@Autowired
UserDetailsRepo userDetailsRepo;

@Autowired
 NotificationRepo notificationRepo;

@Autowired
MachineOwnerRepo machineOwnerRepo;

@Autowired
SparesRepo sparesRepo;

@Autowired
BreakdownupdateRepo breakdownupdateRepo; 

@Autowired
TrialRepo trialRepo;






@GetMapping("/getTrialByLimit")
public @ResponseBody List<Trial> getTrialByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
	List<Trial> list = new ArrayList<Trial>();
	try {
		list = trialRepo.getTrialByLimit(pageNo,perPage);

		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}


@RequestMapping(value = "/getTrialByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody List<Trial> getTrialByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	List<Trial> list = new ArrayList<Trial>();
	try {
		
		list = trialRepo.getTrialByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
@RequestMapping(value = "/getTrialCountByLimitAndGroupSearch", method = RequestMethod.POST)
public @ResponseBody int getTrialCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
	int count =0;
	try {
		
		count = trialRepo.getTrialCountByLimitAndGroupSearch(groupSearchDTO);


		int srNo=0;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


@GetMapping("/getTrialCount")
public @ResponseBody int  getTrialCount() {
    int  count= 0;
    try {
        count= (int) trialRepo.count();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}


	@PostMapping(value = "/create22", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial22(@RequestBody Trial trial) {
		try {
			
			trial.setDeletes(1);
			trial.setTicket_raised_time(trial.getBreakdownupdate().getBreakdown().getTicket_raised_time());
			
			trial.setBreakdown(trial.getBreakdownupdate().getBreakdown());
			trial.setMachine(trial.getBreakdownupdate().getMachine());
			trial.setSent_to_trial_time(trial.getBreakdownupdate().getBreakdown_date());
		
			SimpleDateFormat f = new SimpleDateFormat("MMM");
			System.out.println("Trail TIME  :::::::::::::::::::::   "+trial.getTrial_date());
			String m = f.format(trial.getTrial_date());

			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getBreakdownupdate().getBreakdown_date().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);
			
			if(trial_time<0) {
				trial_time=0.0;
			}
			List<Breakdown> breakdownList = breakdownServices.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {
				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}
				
				br.setTicket_closed_time(new Date());
				System.out.println("CLOSED TIME ====== "+br.getTicket_closed_time());

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

					br.setTc_tr_hr(tc_tr_hr);
					br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);
					System.out.println("tc_tr_hr "+br.getTc_tr_hr());
					System.out.println("Total_trial_hr "+br.getTotal_trial_hr());
				br.setTicket_closed_flag(1);
				br.setStatus(3);



				
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);
			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(3);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status create(@RequestBody Trial trial) {
	try {
			List<Breakdownupdate> breakdownupdates=breakdownupdateRepo.getBreakDownUpdateByBreakdownId(trial.getBreakdown().getBreakdown_id());
			trial.setBreakdownupdate(breakdownupdates.get(0));
			
			trial.setDeletes(1);
			trial.setTicket_raised_time(trial.getBreakdownupdate().getBreakdown().getTicket_raised_time());
			
			trial.setBreakdown(trial.getBreakdownupdate().getBreakdown());
			trial.setMachine(trial.getBreakdownupdate().getMachine());
			trial.setSent_to_trial_time(trial.getBreakdownupdate().getBreakdown_date());
		
			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());

			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);
			
			
			System.out.println("trial_done_time  "+d);
			System.out.println("sent_to_trial_time "+ trial.getBreakdownupdate().getBreakdown_date()+"   "+sent_to_trial_time);
			System.out.println("trial_time   "+trial_time);
			
			if(trial_time<0) {
				trial_time=0.0;
			}
			List<Breakdown> breakdownList = breakdownServices.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {
				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}
				
				br.setTicket_closed_time(new Date());

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();
				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				System.out.println("TC RAISED TIME  "+br.getTicket_raised_time()+"      "+ticke_raised_hr);
				System.out.println("TC CLOSED TIME"+d+"      "+ticket_closed_hr);
				br.setTc_tr_hr(tc_tr_hr);
				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);
				
				
				System.out.println("tc_tr_hr "+br.getTc_tr_hr());
				System.out.println("Total_trial_hr "+br.getTotal_trial_hr());
				
				
				
				br.setTicket_closed_flag(1);
				br.setStatus(3);


			
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);
			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(3);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
// trial api for mobile App.
	@PostMapping(value = "/approve/{trial_by}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status approve(@RequestBody Trial trial, @PathVariable("trial_by") int trialById) {
	    try {
	        if (trial == null || trial.getBreakdown() == null) {
	            return new Status("Error: Trial or Breakdown is null");
	        }

	        List<Breakdownupdate> breakdownupdates = breakdownupdateRepo
	                .getBreakDownUpdateByBreakdownId(trial.getBreakdown().getBreakdown_id());

	        if (breakdownupdates == null || breakdownupdates.isEmpty()) {
	            return new Status("Error: No BreakdownUpdate found for Breakdown ID: " + trial.getBreakdown().getBreakdown_id());
	        }

	        Breakdownupdate update = breakdownupdates.get(0);
	        trial.setBreakdownupdate(update);
	        trial.setDeletes(1);
	        trial.setTicket_raised_time(update.getBreakdown().getTicket_raised_time());
	        trial.setBreakdown(update.getBreakdown());
	        trial.setMachine(update.getMachine());
	        trial.setSent_to_trial_time(update.getBreakdown_date());

	        Optional<UserDetails> userOptional = userDetailsRepo.findById(trialById);
	        if (!userOptional.isPresent()) {
	            return new Status("Error: trial_by UserDetails not found");
	        }

	        UserDetails trialByUser = userOptional.get();
	        trial.setTrial_By(trialByUser);

	        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
	        String currentMonth = monthFormat.format(new Date());
	        String ticketMonth = monthFormat.format(trial.getTicket_raised_time());

	        Date now = new Date();
	        long trial_done_time = now.getTime();
	        long sent_to_trial_time = trial.getSent_to_trial_time() != null ? trial.getSent_to_trial_time().getTime() : 0;
	        double trial_time = Math.max((double) (trial_done_time - sent_to_trial_time) / 3600000, 0.0);

	        List<Breakdown> breakdownList = breakdownServices.getBreakdoenById(trial.getBreakdown().getBreakdown_id());
	        List<Spares> sparesList = breakdownServices.getSpareListByBreakdownUpdate(update.getBreakdown_update_id());

	        for (Breakdown br : breakdownList) {
	            // Build spares_used string safely
	            if (sparesList != null && !sparesList.isEmpty()) {
	                StringBuilder sparesUsedBuilder = new StringBuilder(br.getSpares_used() != null ? br.getSpares_used() : "");
	                for (Spares s : sparesList) {
	                    sparesUsedBuilder.append(" ").append(s.getSpare_name()).append("-").append(s.getQty());
	                }
	                br.setSpares_used(sparesUsedBuilder.toString().trim());
	            }

	            br.setTicket_closed_time(now);
	            double tc_tr_hr = Math.max((double) (now.getTime() - br.getTicket_raised_time().getTime()) / 3600000, 0.0);
	            br.setTc_tr_hr(tc_tr_hr);
	            br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);
	            br.setTicket_closed_flag(1);
	            br.setStatus(3);
	            br.setTrialBy(trialByUser);
	            breakdownServices.addBreakdown(br);

	            Machine machine = br.getMachine();
	            if (machine != null) {
	                List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);
	                for (MachineOwner owner : owners) {
	                    Notification notification = new Notification();
	                    notification.setNotificationDept("Maintenance");
	                    notification.setNotificationFor("OWNER");
	                    notification.setNotificationForSpecId(owner.getUser().getId());
	                    notification.setMachine(machine);
	                    notification.setRaisedTime(now);
	                    notification.setTitle("Breakdown Trial Approved");

	                    String msg = "Breakdown Slip No: " + br.getBd_slip()
	                            + " for Machine " + machine.getMachine_name() + " (EQID: " + machine.getEqid() + ")"
	                            + " has been approved after trial by "
	                            + trialByUser.getFirstName() + " " + trialByUser.getLastName()
	                            + " on " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(now);

	                    notification.setMessage(msg);
	                    notification.setViewed(0);

	                    notificationRepo.save(notification);
	                }
	            }
	        }

	        List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

	        trial.setFtrstatus(ftrList.isEmpty() ? 1 : 0);
	        trial.setTicket_raised_flag(ftrList.isEmpty() ? 1 : 0);
	        trial.setTicket_closed_time(now);
	        trial.setTrial_date(now);
	        trial.setTr_month(ticketMonth);
	        trial.setTicket_closed_flag(1);
	        trial.setStatus(3);
	        trial.setMonth(currentMonth);

	        trialServices.addTrial(trial);
	        return new Status("✅ Trial added and notifications sent successfully!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status("❌ Error: " + e.getMessage());
	    }
	}

	
	@PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status approve(@RequestBody Trial trial) {
	    try {
	        System.out.println("TRIAL: " + trial);

	        if (trial == null || trial.getBreakdown() == null) {
	            return new Status("Error: Trial or Breakdown is null");
	        	}

	        List<Breakdownupdate> breakdownupdates = breakdownupdateRepo.getBreakDownUpdateByBreakdownId(
	                trial.getBreakdown().getBreakdown_id());

	        if (breakdownupdates == null || breakdownupdates.isEmpty()) {
	            return new Status("Error: No BreakdownUpdate found for Breakdown ID: " + trial.getBreakdown().getBreakdown_id());
	        }

	        trial.setBreakdownupdate(breakdownupdates.get(0));
	        trial.setDeletes(1);

	        if (trial.getBreakdownupdate() == null || trial.getBreakdownupdate().getBreakdown() == null) {
	            return new Status("Error: BreakdownUpdate or Breakdown is null");
	        }

	        trial.setTicket_raised_time(trial.getBreakdownupdate().getBreakdown().getTicket_raised_time());
	        trial.setBreakdown(trial.getBreakdownupdate().getBreakdown());
	        trial.setMachine(trial.getBreakdownupdate().getMachine());
	        trial.setSent_to_trial_time(trial.getBreakdownupdate().getBreakdown_date());

	        SimpleDateFormat f = new SimpleDateFormat("MMM");
	        String m = f.format(new Date());
	        String m2 = f.format(trial.getTicket_raised_time());

	        Date d = new Date();
	        long trial_done_time = d.getTime();
	        long sent_to_trial_time = (trial.getBreakdownupdate().getBreakdown_date() != null) ?
	                trial.getBreakdownupdate().getBreakdown_date().getTime() : 0;

	        double trial_time = (double) (trial_done_time - sent_to_trial_time) / 3600000;
	        trial_time = Math.max(trial_time, 0.0);

	        List<Breakdown> breakdownList = breakdownServices.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());
	        List<Spares> sparesList = breakdownServices.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

	        for (Breakdown br : breakdownList) {
	            if (sparesList != null && !sparesList.isEmpty()) {
	                for (Spares s : sparesList) {
	                    if (br.getSpares_used() == null) {
	                        br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
	                    } else {
	                        br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());
	                    }
	                }
	            }

	            br.setTicket_closed_time(d);
	            long ticke_raised_hr = (br.getTicket_raised_time() != null) ? br.getTicket_raised_time().getTime() : 0;
	            long ticket_closed_hr = d.getTime();
	            double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / 3600000;

	            br.setTc_tr_hr(tc_tr_hr);
	            br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);
	            br.setTicket_closed_flag(1);
	            br.setStatus(3);

//	            if (br.getDone_by() == null) {
//	                br.setDone_by(trial.getBreakdownupdate().getAction_by());
//	            } else {
//	                Optional<Breakdown> optionalBre = breakdownServices.getBreakdownID(br.getBreakdown_id());
//	                if (optionalBre.isPresent()) {
//	                    br.setDone_by("[Update By: " + optionalBre.get().getDone_by() + "]\n\n[Trial By: " +
//						/* trial.getTrialBy().replace("null", "") + */ "]");
//	                }
	         //   }

	            breakdownServices.addBreakdown(br);
	        }

	        List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

	        trial.setFtrstatus(ftrList.isEmpty() ? 1 : 0);
	        trial.setTicket_raised_flag(ftrList.isEmpty() ? 1 : 0);

	        trial.setTicket_closed_time(d);
	        trial.setTrial_date(d);
	        trial.setTr_month(m2);
	        trial.setTicket_closed_flag(1);
	        trial.setStatus(3);
	        trial.setMonth(m);

	        trialServices.addTrial(trial);
	        return new Status("Trial added Successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new Status("Error: " + e.getMessage());
	    }
	}

	

		@PostMapping(value = "/create1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial1(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE  1111111111111 +++++++++++++++++++++++++++++++++");

			trial.setDeletes(1);
			System.out.println(trial.toString());

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(trial.getTrial_date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setStatus(1);

//				if (br.getDone_by() == null) {
//
//					br.setDone_by(trial.getBreakdownupdate().getAction_by());
//				}
//
//				else {
//
//					br.setDone_by(br.getDone_by() + " " + trial.getBreakdownupdate().getAction_by());
//				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(trial.getBreakdownupdate().getAction_taken());
				}

				else {

					br.setAction_taken( trial.getBreakdownupdate().getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setTicket_raised_flag(0);

			}

			trial.setTr_month(m2);
			trial.setTicket_closed_flag(0);
			trial.setTrial_date(d);
			trial.setStatus(1);
			trial.setFtrstatus(0);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new Status(e.toString());
		}

	}

	

	@PostMapping(value = "/create2", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addTrial2(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE 222222222222+++++++++++++++++++++++++++++++++");

			trial.setDeletes(1);
			System.out.println(trial.toString());

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(trial.getTrial_date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(trial.getBreakdownupdate().getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(trial.getBreakdownupdate().getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);
				br.setTicket_closed_flag(1);
				br.setStatus(4);

//				if (br.getDone_by() == null) {
//
//					br.setDone_by(trial.getBreakdownupdate().getAction_by());
//				}
//
//				else {
//
//					br.setDone_by(br.getDone_by() + " " + trial.getBreakdownupdate().getAction_by());
//				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(trial.getBreakdownupdate().getAction_taken());
				}

				else {
					br.setAction_taken(br.getAction_taken() );
				//	br.setAction_taken(br.getAction_taken() + " " + trial.getBreakdownupdate().getAction_taken());
				}

				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(4);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Trial> getTrials() {
		List<Trial> trialList = null;
		try {

			trialList = trialServices.getTrialList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trialList;
	}
	@GetMapping(value = "/getTrialsByBreakdown", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Trial> getTrialsByBreakdown(@RequestParam("breakdownId") int breakdownId) {
		List<Trial> trialList = null;
		try {

			trialList = trialServices.getTrialsByBreakdown(breakdownId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trialList;
	}


	@PostMapping(value = "/getRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdownupdate> getReport(@RequestBody Trial trial) {
		List<Breakdownupdate> maintList = null;
		try {
			maintList = trialServices.getReport(trial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
//**********************************************************************************************************************

// Send to Maintenance Button Mobile App API
	@PostMapping(value = "/sendToMaintenance", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Status sendToMaintenance(@RequestBody Trial trial) {
		
			try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE  1111111111111 +++++++++++++++++++++++++++++++++");

			  String trialResult = trial.getTrial_result();

			Breakdownupdate breakdownupdate= breakdownServices.getBreakDownUpdateByBreakDown(trial.getBreakdown().getBreakdown_id());
			trial.setDeletes(1);
			trial.setDeletes(1);
			System.out.println(trial.toString());
			// Fetch actual breakdown from DB
			Breakdown dbBreakdown = breakdownServices.getBreakdownById(trial.getBreakdown().getBreakdown_id());

			trial.setTicket_raised_time(dbBreakdown.getTicket_raised_time());  // ✅ now real value
			trial.setBreakdown(dbBreakdown);  // ✅ attach managed entity

			trial.setMachine(breakdownupdate.getMachine());
			trial.setSent_to_trial_time(breakdownupdate.getBreakdown_date());
			 trial.setTrial_result(trialResult); 
		
			 
			 System.out.println("Trial Result"+ trialResult);

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = breakdownupdate.getBreakdown_date().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			//System.out.println();
			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(breakdownupdate.getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				br.setTotal_trial_hr(br.getTotal_trial_hr() + trial_time);

				br.setStatus(1);

				if (br.getAction_taken() == null) {

					br.setAction_taken(breakdownupdate.getAction_taken());
				}

				else {

				}
				  br.setTrialResult(trialResult); 
				  br.setShowBit(2);
				  
				  System.out.println("Trial Result"+ trialResult);
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setTicket_raised_flag(0);

			}

			trial.setTr_month(m2);
			trial.setTicket_closed_flag(0);
			trial.setTrial_date(d);
			trial.setStatus(1);
			trial.setFtrstatus(0);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			Machine machine = breakdownupdate.getMachine();
			if (machine != null) {
			    List<MachineOwner> owners = machineOwnerRepo.findByMachine(machine);
			    for (MachineOwner owner : owners) {
			        Notification notification = new Notification();
			        notification.setNotificationDept("Maintenance");
			        notification.setNotificationFor("OWNER");
			        notification.setNotificationForSpecId(owner.getUser().getId());
			        notification.setMachine(machine);
			        notification.setRaisedTime(d); 
			        notification.setTitle("Breakdown Sent Back to Maintenance");

			        String msg = "Breakdown Slip No: " + breakdownupdate.getBreakdown().getBd_slip()
			                   + " for Machine " + machine.getMachine_name()
			                   + " (EQID: " + machine.getEqid() + ")"
			                   + " has been sent back to maintenance after trial."
			                   + (trialResult != null ? " Trial Result: " + trialResult : "");

			        notification.setMessage(msg);
			        notification.setViewed(0);

			        notificationRepo.save(notification);
			    }
			}

			return new Status("Sending To Maintenance  !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	@GetMapping("/getTrialListByBreakdown")
	//@RequestMapping(value = "/getTrialListByBreakdown", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Trial> getTrialListByBreakdown(@RequestParam("breakdownId") int breakdownId) {
		List<Trial> maintList = null;
		try {
			maintList = trialServices.getTrialListByBreakdown(breakdownId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maintList;
	}
	
	
	
	@PostMapping(value = "/approveWithDeviation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status approveWithDeviation(@RequestBody Trial trial) {
		try {
			System.out.println("+++++++++++++++++++++++++++++++++++++IND TRAIL CREATE 222222222222+++++++++++++++++++++++++++++++++");
			
			Breakdownupdate breakdownupdate= breakdownServices.getBreakDownUpdateByBreakDown(trial.getBreakdown().getBreakdown_id());

			trial.setDeletes(1);
			trial.setDeletes(1);
			System.out.println(trial.toString());
			trial.setTicket_raised_time(breakdownupdate.getBreakdown().getTicket_raised_time());
			trial.setBreakdown(breakdownupdate.getBreakdown());
			trial.setMachine(trial.getBreakdown().getMachine());
			trial.setSent_to_trial_time(breakdownupdate.getBreakdown_date());
		

			SimpleDateFormat f = new SimpleDateFormat("MMM");

			String m = f.format(new Date());
			String m2 = f.format(trial.getTicket_raised_time());

			Date d = new Date();

			long trial_done_time = d.getTime();
			long sent_to_trial_time = trial.getSent_to_trial_time().getTime();

			double trial_time = (double) (trial_done_time - sent_to_trial_time) / (3600000);

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			List<Spares> sparesList = breakdownServices
					.getSpareListByBreakdownUpdate(breakdownupdate.getBreakdown_update_id());

			for (Breakdown br : breakdownList) {

				if (sparesList.size() != 0) {

					for (Spares s : sparesList) {

						if (br.getSpares_used() == null) {

							br.setSpares_used(s.getSpare_name() + "-" + s.getQty());
						}

						else {

							br.setSpares_used(br.getSpares_used() + " " + s.getSpare_name() + "-" + s.getQty());

						}

					}
				}

				long ticke_raised_hr = br.getTicket_raised_time().getTime();
				long ticket_closed_hr = d.getTime();

				double tc_tr_hr = (double) (ticket_closed_hr - ticke_raised_hr) / (3600000);

				br.setTc_tr_hr(tc_tr_hr);
				br.setTicket_closed_flag(1);
				br.setStatus(4);

//				if (br.getDone_by() == null) {
//
//					br.setDone_by(breakdownupdate.getAction_by());
//				}
//
//				else {
//
//					br.setDone_by(br.getDone_by() + " " + breakdownupdate.getAction_by());
//				}

				if (br.getAction_taken() == null) {

					br.setAction_taken(breakdownupdate.getAction_taken());
				}

				else {

					br.setAction_taken(br.getAction_taken() + " " + breakdownupdate.getAction_taken());
				}
				 
				
				breakdownServices.addBreakdown(br);
			}

			List<Trial> ftrList = trialServices.getListByBreakdownUpdate(trial.getBreakdown());

			if (ftrList.size() == 0) {

				trial.setFtrstatus(1);
				trial.setTicket_raised_flag(1);

			}

			else {

				trial.setFtrstatus(0);
				trial.setTicket_raised_flag(0);

			}

			trial.setTicket_closed_time(d);
			trial.setTrial_date(d);
			trial.setTr_month(m2);
			trial.setTicket_closed_flag(1);
			trial.setStatus(4);
			trial.setMonth(m);
			trialServices.addTrial(trial);
			return new Status("Trial added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
}
