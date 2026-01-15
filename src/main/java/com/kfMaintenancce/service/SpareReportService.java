package com.kfMaintenancce.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.kfMaintenancce.dto.SpareReportDTO;
import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import org.springframework.data.domain.Page;

public interface SpareReportService {
	 public List<SpareReportDTO> getSpareStockReportById(int spareId);
	  public List<SpareReportDTO> getSpareStockReport();
	  public List<SpareUtilizationReportDTO> getSpareUtilizationReportById(int spareId);
	  public List<SpareUtilizationReportDTO> getSpareUtilizationReport();
	  public void exportSpareUtilizationReport(HttpServletResponse response, List<SpareUtilizationReportDTO> reports) throws IOException;
	  public List<SpareUtilizationReportDTO> getSpareUtilizationReportByCompId(Integer compId);
	  
	  public long getSpareStockCount(String keyword);
	  public Map<String, Object> getSpareStockReportPaged(int page, int size, String keyword);
	  
	  public SpareReportDTO getCombinedSpareStockAndUtilizationReportBySpareId(int spareId);
	  public List<SpareReportDTO> getCombinedSpareStockAndUtilizationReport() ;
	  public Map<String, Object> getCombinedSpareReportPaged(String keyword, int page, int size);
	  public Map<String, Object> getCombinedSpareReportPaged(Integer spareId, String keyword, int page, int size);
      public Page<SpareUtilizationReportDTO> getPaginatedSearchReport(Integer spareId, int page, int size);
         public Page<SpareUtilizationReportDTO> getPaginatedReport(int page, int size);
    public Page<SpareUtilizationReportDTO> getPaginatedSearchReport(Integer spareId, String keyword, int page, int size);
}
