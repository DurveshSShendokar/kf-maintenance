package com.kfMaintenancce.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.kfMaintenancce.dto.DateByQRCodeResponse;
import com.kfMaintenancce.dto.ReportReqObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.Maint;
import com.kfMaintenancce.repo.BreakdownRepo;
import com.kfMaintenancce.repo.MaintRepo;

@CrossOrigin("*")
@RestController
@Component
@Service
@RequestMapping("/qrscan")
public class QRCodeController {

	@Autowired 
	BreakdownRepo breakdownRepo;
	
	@Autowired 
	MaintRepo maintRepo;
	
	
	@PostMapping(value = "/getDataByQrCode", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	DateByQRCodeResponse addMaint(@RequestBody ReportReqObj reportReqObj ) {
		DateByQRCodeResponse dateByQRCodeResponse= new DateByQRCodeResponse();
		try {
			System.out.println("GET QR CODE APIA "+reportReqObj.getEquipmentId());
			List<Breakdown> breakdowns= breakdownRepo.getBreakdownByEquipMenID(reportReqObj.getEquipmentId());
			List<Maint> maints=maintRepo.getByMaintEqipId(reportReqObj.getEquipmentId());
			System.out.println("Breakdoen "+breakdowns.size());
			System.out.println("maints    "+maints.size());
			//List<Maint> maints=maintRepo.getByMaintEqipId(reportReqObj.getEquipmentId());
			dateByQRCodeResponse.setBeakdowns(breakdowns);
			dateByQRCodeResponse.setMaints(maints);
			return dateByQRCodeResponse;
		} catch (Exception e) {
			 e.printStackTrace();
			return  dateByQRCodeResponse;
		}

	}
}
