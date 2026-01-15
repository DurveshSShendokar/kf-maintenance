package com.kfMaintenancce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.BreakdownSpare;
import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.MaintSpareStock;
import com.kfMaintenancce.model.MaintSpareStocking;
import com.kfMaintenancce.model.Spares;
import com.kfMaintenancce.repo.BreakdownSpareRepo;
import com.kfMaintenancce.repo.MaintSpareStockRepo;
import com.kfMaintenancce.repo.MaintsparestockingRepo;
import com.kfMaintenancce.service.BreakdownServices;
import com.kfMaintenancce.service.BreakdownupdateServices;
import com.kfMaintenancce.service.SpareServices;



@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/breakdownupdate")

public class BreakdownupdateController {

	@Autowired
	BreakdownupdateServices breakdownupdateServices;

	@Autowired
	BreakdownServices breakdownServices;

	@Autowired
	SpareServices spareServices;
	@Autowired
	BreakdownSpareRepo breakdownSpareRepo; 
	@Autowired
	MaintsparestockingRepo maintsparestockingRepo;
	@Autowired
	MaintSpareStockRepo maintsparestockRepo;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addBreakdownupdate(@RequestBody Breakdownupdate breakdownupdate) {
		try {
			System.out.println("CREATE .....Breakdown :: "+breakdownupdate.toString());
			List<Breakdownupdate> breakdownupdateList = breakdownupdate.getBreakdownupdate();
			breakdownupdate.setBreakdown_date(new Date());
			breakdownupdateServices.addBreakdownupdate(breakdownupdate);
			
			
			if(breakdownupdate.getSelectedSpare().size()!=0) {
				for(MaintSpare maintSpare:breakdownupdate.getSelectedSpare()) {
					BreakdownSpare breakdownSpare = new BreakdownSpare ();
					breakdownSpare.setBreakdown(breakdownupdate.getBreakdown());
					breakdownSpare.setSpare(maintSpare);
					breakdownSpare.setConsumedquantity(maintSpare.getQuantity());
					breakdownSpare.setConsumptionDate(new Date());
					
					breakdownSpareRepo.save(breakdownSpare);
					
					Optional<MaintSpareStock> optionalStocking=maintsparestockRepo.getMaintSpareStockByMaintSpare(maintSpare.getMaintspare_id());
					if(optionalStocking.isPresent()) {
						MaintSpareStock maintSpareStocking=optionalStocking.get();
						maintSpareStocking.setAvailableQuantity(maintSpareStocking.getAvailableQuantity()-maintSpare.getQuantity());
						maintsparestockRepo.save(maintSpareStocking);
						
					}
					
					
				}
				
				
				
				
				
			}

			Set<Spares> spareSet = null;

			List<Breakdown> breakdownList = breakdownServices
					.getBreakdoenById(breakdownupdate.getBreakdown().getBreakdown_id());

			for (Breakdown br : breakdownList) {
				System.out.println("Breakdown :: "+br.toString());
				br.setStatus(2);
//				br.setDone_by(breakdownupdate.getDone_by());
//				System.out.println("Update Done By "+br.getDone_by());
				
				  // Set ticket_trial_time when status is 2
	            if (br.getStatus() == 2) {
	                br.setTicket_trial_time(new Date()); 
	            }

				
				if (br.getEngg_finding() == null) {

					br.setEngg_finding(breakdownupdate.getRoot_cause());
				}

				else {
					
					br.setEngg_finding(br.getEngg_finding()+"\n"+breakdownupdate.getRoot_cause());

					
				}
				
				breakdownServices.addBreakdown(br);
			}			
			  for (Breakdownupdate bd : breakdownupdateList) {
			  
			  Spares s = new Spares();
			  
			  s.setSpare_name(bd.getSpare());
			  
			  s.setQty(bd.getQty()); s.setDeletes(1);
			  
			 s.setBreakdownupdate(breakdownupdate);
			 
			  spareServices.addSpares(s);
			 
			  }
			 

			return new Status("Breakdownupdate added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(e.toString());
		}

	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Breakdownupdate> getBreakdownupdates() {
		List<Breakdownupdate> breakdownupdateList = null;
		try {

			breakdownupdateList = breakdownupdateServices.getBreakdownupdateList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakdownupdateList;
	}

	@GetMapping(value = "/delete/{breakdown_update_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody

			Status deleteCategory(@PathVariable("breakdown_update_id") int breakdown_update_id) {
		try {
			breakdownupdateServices.deleteBreakdownupdate(breakdown_update_id);
			return new Status("Breakdownupdate Deleted Successfully !");
		} catch (Exception e) {
			return new Status(e.toString());
		}
	}
	
	
	
	
	@GetMapping(value = "/getSpares", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Spares> getMachineSpares(@RequestParam("breakdownUpdateId") int breakdownUpdateId) {
	
		List<Spares> spareList =null;
		try {
			
			spareList = breakdownupdateServices.getSpares(breakdownUpdateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spareList;
	}
	
	
	
	

}
