package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.BreakdownSpareReport;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.MaintSpare;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.Spares;

public interface MaintSpareServices {

	 public List<MaintSpare> getAllSpares();
	 public Optional<MaintSpare> getSpareById(int id);
	 public MaintSpare saveSpare(MaintSpare maintSpare);
	 public MaintSpare findById(int maintspare_id);
	 public MaintSpare updateSpare(int id, MaintSpare updatedSpare) ;
	 public void deleteSpare(int id) ;
	// public void exportBreakdownSpareReport(HttpServletResponse response, List<BreakdownSpareReport> reports) throws IOException;
	

}
