package com.kfMaintenancce.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kfMaintenancce.repo.General_breakdownRepo;

import com.kfMaintenancce.model.General_breakdown;


@Service
public class GeneralBreakdownServiceImpl implements GeneralBreakdownService{
	
	
	@Autowired
	private General_breakdownRepo General_breakdownRepo;
	
	public General_breakdown findById(int genbreak_id) {
	    return General_breakdownRepo.findById(genbreak_id).orElse(null);
	}

	public General_breakdown saveGeneralBreakdown(General_breakdown general_breakdown) {
	    return General_breakdownRepo.save(general_breakdown);
	}

	
	public List<General_breakdown> getAllGeneralBreakdowns() {
	    return General_breakdownRepo.findAll();
	}
	
	public Optional<General_breakdown> getGeneralBreakdownById(int genbreak_id) {
	    return General_breakdownRepo.findById(genbreak_id);
	}
	
	public boolean deleteGeneralBreakdownById(int genbreak_id) {
	    if (General_breakdownRepo.existsById(genbreak_id)) {
	    	General_breakdownRepo.deleteById(genbreak_id);
	        return true;
	    }
	    return false;
	}
	 public String getNewBreakDownNo() {
	        String bdNo = "";
	        String mnyr = "";
	        Calendar c = Calendar.getInstance();
	        int yr = c.get(Calendar.YEAR);
	        int mn = c.get(Calendar.MONTH) + 1;
	        String year = String.valueOf(yr).substring(2, 4);
	        String month = mn <= 9 ? "0" + mn : String.valueOf(mn);
	        
	        mnyr = year + month;
	        
	        int count = General_breakdownRepo.getBreakdownCountByYearMonth(mnyr);

	        if (count == 0) {
	            bdNo = mnyr + "0001";
	        } else {
	            String maxNo = "1" + General_breakdownRepo.getMaxBreakdownNoNoByYearMonth(mnyr);
	            int mxint = Integer.parseInt(maxNo);
	            mxint++;
	            bdNo = String.valueOf(mxint).substring(1, 9);
	        }
	        
	        return bdNo;
	    }
	 
	 
	 public General_breakdown updateBreakdownStatus(General_breakdown existingBreakdown, int newStatus) {
	        if (existingBreakdown.getStatus() == 0) {
	           
	            throw new IllegalStateException("This breakdown is already closed and cannot be reopened.");
	        }

	        if (newStatus == 0) {
	            // Set closed date and time
	            existingBreakdown.setClosedDate(new Date());
	            existingBreakdown.setClosedTime(new Date());
	        }

	        existingBreakdown.setStatus(newStatus);
	        return saveGeneralBreakdown(existingBreakdown);
	    }
	 
	 
	
}
