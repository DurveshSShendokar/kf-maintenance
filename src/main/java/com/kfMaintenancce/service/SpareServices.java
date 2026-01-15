package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;

import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.Spares;

public interface SpareServices {

	void addSpare(Spare s);

	void addSpares(Spares s);
	public List<Spares> getSpareByBreakdownId(int breakdown_id);
	List<Spare> getSpareByLimit(int pageNo, int perPage);

	int count();

	List<Spare> getSpareByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);

	int getSpareCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO);
	
	 public void deleteSpare(int spare_id);
	 public void processExcelFile(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException;
	 public byte[] generateExcelFile() throws IOException;
	 public Spare findById(int spare_id);
	List<Spare> getAllSapres();
	

}
