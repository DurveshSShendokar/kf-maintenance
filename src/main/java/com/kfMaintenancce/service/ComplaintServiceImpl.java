package com.kfMaintenancce.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.ComplaintStatusDTO;
import com.kfMaintenancce.dto.EngineerComplaint_DTO;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.repo.ComplaintRepo;
import com.kfMaintenancce.repo.ComplaintRepoOLD;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepoOLD complaintRepo;
    
    @Autowired
    private ComplaintRepo complaintrepo;
    
  
public String getNewTicketNo() {
		
		String newTicketNo="";
		
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
		
		int count=complaintRepo.getComplaintCountByYearMonth(mnyr);
		

		if(count==0){
			newTicketNo=mnyr+"0001";
		}else{
			String maxNo="1"+complaintRepo.getMaxComplaintNoByYearMonth(mnyr);
			///System.out.println("MAX NO "+changeManagementRepo.getMaxChangeMangementNoByYearMonth(mnyr));
			int mxint=Integer.parseInt(maxNo);
			mxint++;
			newTicketNo=String.valueOf(mxint).substring(1,9);
		//	System.out.println("NEW MAX NO "+newmaxNo);

			
		}
		
		
		return newTicketNo;
	}


   @Override
   public Complaint createComplaint(Complaint complaint) {
	   
       // Set complaint date and time to current date and time
       complaint.setComplaintDate(new Date());
       complaint.setComplaintTime(new Date());

       // Set default status to 1
      // complaint.setStatus(1);

       // Set work start date and time to current date and time
       complaint.setWorkstartDate(new Date());
       complaint.setWorkstartTime(new Date());

       // Save the complaint
       return complaintRepo.save(complaint);
   }


    
   @Override
   public Complaint updateComplaint(int comp_id, Complaint updatedComplaint) {
       Optional<Complaint> optionalComplaint = complaintRepo.findById(comp_id);
       if (optionalComplaint.isPresent()) {
           Complaint complaint = optionalComplaint.get();
           
           // Check if the complaint status is closed
           if (complaint.getStatus() == 4) {
               throw new RuntimeException("Complaint with ID " + comp_id + " is closed and cannot be updated.");
           }

           
           // Update conclusion, root cause, and spare stock
           complaint.setConclusion(updatedComplaint.getConclusion());
           complaint.setRootCause(updatedComplaint.getRootCause());
          // complaint.setSpareStock(updatedComplaint.getSpareStock());

           // Validate and set the new status
           int newStatus = updatedComplaint.getStatus();
           if (newStatus != 1 && newStatus != 2 && newStatus != 3 && newStatus != 4) {
               throw new IllegalArgumentException("Invalid status value. Allowed values are 1, 2, 3, or 4.");
           }

           // Update status and relevant fields based on new status
           complaint.setStatus(newStatus);
           if (newStatus == 4) { // Closed
               complaint.setClosedDate(new Date());
               complaint.setClosedTime(new Date());
           } else if (newStatus == 2) { // Allocate
               complaint.setAllocateDate(new Date());
               complaint.setAllocateTime(new Date());
           } else if (newStatus == 3) { // In-Process
               complaint.setInProcessDate(new Date());
               complaint.setInProcessTime(new Date());
           }

           // Save and return the updated complaint
           return complaintRepo.save(complaint);
       } else {
           throw new RuntimeException("Complaint not found with id: " + comp_id);
       }
   }
    @Override
    public List<Complaint> getAllComplaints() {
        return complaintRepo.findAll();
    } 
    
    public List<Complaint> getComplaintByLimit(int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return complaintRepo.getComplaintByLimit(pageNo,perPage);
	}

	
	public int count() {
		// TODO Auto-generated method stub
		return (int) complaintRepo.count();
	}

	
	public List<Complaint> getComplaintByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return complaintRepo.getComplaintByLimitAndGroupSearch(groupSearchDTO);
	}

	
	public int getComplaintCountByLimitAndGroupSearch(GroupSearchDTO groupSearchDTO) {
		// TODO Auto-generated method stub
		return complaintRepo.getComplaintCountByLimitAndGroupSearch(groupSearchDTO);
	}


	@Override
	public List<Complaint> getunallocatedComplaints() {
		// TODO Auto-generated method stub
		return complaintRepo.getunallocatedComplaints();
	}

	@Override
	public long getUnallocatedComplaintsCount(String keyword) {
	    if (keyword == null || keyword.trim().isEmpty()) {
	        return complaintRepo.countUnallocatedComplaints();
	    }
	    return complaintRepo.countUnallocatedComplaintsBySearch(keyword.toLowerCase());
	}
	@Override
	public List<Complaint> getUnallocatedComplaintsSearch(int pageNo, int perPage, String keyword) {
	    Pageable pageable = PageRequest.of(pageNo - 1, perPage);
	    if (keyword == null || keyword.trim().isEmpty()) {
	        return complaintRepo.findUnallocatedComplaints(pageable).getContent();
	    }
	    return complaintRepo.findUnallocatedComplaintsBySearch(keyword.toLowerCase(), pageable).getContent();
	}

	@Override
	public Page<Complaint> getUnallocatedComplaints(int pageNo, int perPage) {
	    Pageable pageable = PageRequest.of(pageNo - 1, perPage);
	    return complaintRepo.findUnallocatedComplaints(pageable);
	}

	
	
	

	@Override
	public List<Complaint> getAllocatedComplaintByEnginner(int empId) {
		// TODO Auto-generated method stub
		return complaintRepo.getAllocatedComplaintByEnginner(empId);
	}


	@Override
	public List<Complaint> getComplaintByEquipmentId(String equipmentId) {
		// TODO Auto-generated method stub
		return complaintRepo.getComplaintByEquipmentId(equipmentId);
	}
	
	
	 public List<Complaint> getOpenComplaintsByDate(Date startDate, Date endDate) {
	        if (startDate == null || endDate == null) {
	            return complaintRepo.findOpenComplaints(); // Fetch all open complaints
	        }
	        return complaintrepo.findOpenComplaintsByDate(startDate, endDate);
	    }

	    public List<Complaint> getClosedComplaintsByDate(Date startDate, Date endDate) {
	        if (startDate == null || endDate == null) {
	            return complaintRepo.findClosedComplaints(); // Fetch all closed complaints
	        }
	        return complaintrepo.findClosedComplaintsByDate(startDate, endDate);
	    }

	    public List<Complaint> getAllocateComplaintsByDate(Date startDate, Date endDate) {
	        if (startDate == null || endDate == null) {
	            return complaintRepo.findAllocateComplaints(); // Fetch all allocated complaints
	        }
	        return complaintrepo.findAllocateComplaintsByDate(startDate, endDate);
	    }

	    public List<Complaint> getInprocessComplaintsByDate(Date startDate, Date endDate) {
	        if (startDate == null || endDate == null) {
	            return complaintRepo.findInprocessComplaints(); // Fetch all in-process complaints
	        }
	        return complaintrepo.findInprocessComplaintsByDate(startDate, endDate);
	    }

	    public List<Complaint> getNonAllocatedComplaintsByDate(Date startDate, Date endDate) {
	        if (startDate == null || endDate == null) {
	            return complaintRepo.findNonAllocatedComplaints(); // Fetch all non-allocated complaints
	        }
	        return complaintrepo.findNonAllocatedComplaintsByDate(startDate, endDate);
	    }
	    
	  
	   //complaint summary report
	    public List<Complaint> filterComplaints(int status, String filterType, Date date, Date fromDate, Date toDate) {
	        switch (filterType.toLowerCase()) {
	            case "day":
	                return complaintRepo.findByStatusAndDate(status, date);
	            case "date":
	                return complaintRepo.findByStatusAndDateRange(status, fromDate, toDate);
	             
	            
	            default:
	                throw new IllegalArgumentException("Invalid filter type");
	        }
	    }
	    
	    //
	    
	    public Map<String, Object> filterComplaintsed(int status, String filterType, Date date, Date fromDate, Date toDate,
				                int page, int size, String keyword) {
				Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "complaintDate"));
				Page<Complaint> complaintPage;
				
				if (keyword != null && !keyword.isEmpty()) {
				// Search + Filter
				switch (filterType.toLowerCase()) {
				case "day":
				complaintPage = complaintRepo.searchByStatusAndDate(status, date, keyword, pageable);
				break;
				case "date":
				complaintPage = complaintRepo.searchByStatusAndDateRange(status, fromDate, toDate, keyword, pageable);
				break;
				default:
				throw new IllegalArgumentException("Invalid filter type");
				}
				} else {
				// Only Filter
				switch (filterType.toLowerCase()) {
				case "day":
				complaintPage = complaintRepo.findByStatusAndDate(status, date, pageable);
				break;
				case "date":
				complaintPage = complaintRepo.findByStatusAndDateRange(status, fromDate, toDate, pageable);
				break;
				default:
				throw new IllegalArgumentException("Invalid filter type");
				}
					}
					
					Map<String, Object> response = new HashMap<>();
					response.put("complaints", complaintPage.getContent());
					response.put("currentPage", complaintPage.getNumber());
					response.put("totalItems", complaintPage.getTotalElements());
					response.put("totalPages", complaintPage.getTotalPages());
					
					return response;
}

	    
	    //export for complaint summary report
	    public void exportFilteredComplaints(int status, String filterType, Date date, Date fromDate, Date toDate, HttpServletResponse response) {
	        List<Complaint> complaints = filterComplaints(status, filterType, date, fromDate, toDate);

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Complaints Summary Report");

	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("SrNo");
	        headerRow.createCell(1).setCellValue("Complaint No");
	        headerRow.createCell(2).setCellValue("Asset ID");
	        headerRow.createCell(3).setCellValue("Complaint Date");
	        headerRow.createCell(4).setCellValue("Status");
	        headerRow.createCell(5).setCellValue("Assigned Engineer");

	        int rowNum = 1;
	        int srNo= 1;
	        for (Complaint complaint : complaints) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(srNo++);
	            row.createCell(1).setCellValue(complaint.getTicketNo());
	            row.createCell(2).setCellValue(complaint.getAssetInventory().getEquipmentId());
	            row.createCell(3).setCellValue(complaint.getComplaintDate().toString());
	            
	            String statusText = getStatusText(complaint.getStatus());
	            row.createCell(4).setCellValue(statusText);
	            
	            row.createCell(5).setCellValue(complaint.getAllocateTo() != null ? complaint.getAllocateTo().getFirstName ()
	            		+ " " + complaint.getAllocateTo().getLastName() : "Unassigned");
	        }

	        response.setHeader("Content-Disposition", "attachment; filename=ComplaintsReport.xlsx");
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        try {
				workbook.write(response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }  
	    
	    private String getStatusText(int status) {
	        switch (status) {
	            case 1:
	                return "Open";
	            case 2:
	                return "Allocate";
	            case 3:
	                return "In Process";
	            case 4:
	                return "Closed";
	            default:
	                return "Unknown";
	        }
	    }

	    
	    // Ticket resolution report with date-wise and day-wise filter
	    public List<TicketResolutionReportDTO> getTicketResolutionReport(Date fromDate, Date toDate, Date selectedDate) {
	    	 List<Complaint> complaints;
	    	if(selectedDate==null) {
	    		  complaints = complaintRepo.findeByBeeterrnDates(fromDate,toDate);
	    	}else {
	    		  complaints = complaintRepo.findeByDate(selectedDate);
	    	}
	    
	        
	        return complaints.stream().map(this::mapToTicketResolutionReportDTO)
	                .collect(Collectors.toList());
	    }
	    
	    
	    @Override
	    public Map<String, Object> getTicketResolutionReportPaged(Date fromDate, Date toDate, Date selectedDate, int page, int size, String keyword) {
	        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "complaintDate"));
	        Page<Complaint> complaintPage;

	        if (selectedDate != null) {
	            if (keyword != null && !keyword.isEmpty()) {
	                complaintPage = complaintRepo.searchByDateAndKeyword(selectedDate, keyword, pageable);
	            } else {
	                complaintPage = complaintRepo.findByDatePaged(selectedDate, pageable);
	            }
	        } else {
	            if (keyword != null && !keyword.isEmpty()) {
	                complaintPage = complaintRepo.searchByDateRangeAndKeyword(fromDate, toDate, keyword, pageable);
	            } else {
	                complaintPage = complaintRepo.findByDateRangePaged(fromDate, toDate, pageable);
	            }
	        }

	        List<TicketResolutionReportDTO> dtoList = complaintPage.getContent()
	                .stream()
	                .map(this::mapToTicketResolutionReportDTO)
	                .collect(Collectors.toList());

	        Map<String, Object> response = new HashMap<>();
	        response.put("tickets", dtoList);
	        response.put("currentPage", complaintPage.getNumber());
	        response.put("totalItems", complaintPage.getTotalElements());
	        response.put("totalPages", complaintPage.getTotalPages());

	        return response;
	    }

	    
	    
	    
	    
	    @Override
	    public long getTicketResolutionCount(Date fromDate, Date toDate, Date selectedDate) {
	        if (selectedDate != null) {
	            return complaintRepo.countByDate(selectedDate);
	        } else {
	            return complaintRepo.countByDateRange(fromDate, toDate);
	        }
	    }

	    
	    
	    
	    


	    public boolean isWithinDateRange(Date complaintDate, Date fromDate, Date toDate) {
	        return !complaintDate.before(fromDate) && !complaintDate.after(toDate);
	    }


	    // Method to check if two dates are the same day
	    public boolean isSameDay(Date date1, Date date2) {
	        Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(date1);
	        cal2.setTime(date2);
	        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	    }

	 // Map Complaint entity to TicketResolutionReportDTO
	    public TicketResolutionReportDTO mapToTicketResolutionReportDTO(Complaint complaint) {
	        String engineerName = complaint.getAllocateTo() != null 
	                ? complaint.getAllocateTo().getFirstName() + " " + complaint.getAllocateTo().getLastName() 
	                : "N/A";
	        String equipmentId = complaint.getAssetInventory() != null ? complaint.getAssetInventory().getEquipmentId() : "N/A";
	        String comapnyName = complaint.getAssetInventory() != null ? complaint.getAssetInventory().getComapnyName() : "N/A";
	        
	        // Directly use the calculation methods for time differences
	        double adminProcessHours = calculateAdminProcessHours(complaint);
	        double engineerProcessHours = calculateEngineerProcessHours(complaint);
	        double engineerSolutionHours = calculateEngineerSolutionHours(complaint);
	        double totalHours = calculateTotalHours(complaint);

	        System.out.println("------------------------------------------------"+complaint.getTicketNo()+"---------------------------------------------------------");
	        System.out.println(" ADMIN  Hours"+adminProcessHours);
	        System.out.println(" Enginner  Peocess  "+adminProcessHours);
	        System.out.println(" Enginner  Solution "+adminProcessHours);
	        System.out.println(" Totoal  "+adminProcessHours);
	        System.out.println("---------------------------------------------------------------------------------------------------------");
	        return new TicketResolutionReportDTO(
	                complaint.getTicketNo(),
	                equipmentId,
	                comapnyName,
	                engineerName,
	                complaint.getStatus(),
	                complaint.getComplaintDate(),
	             
	                adminProcessHours,
	                engineerProcessHours,
	                engineerSolutionHours,
	                totalHours
	        );
	    }


	    private double calculateAdminProcessHours(Complaint complaint) {
	    	
	    	System.out.println("Allocted : "+complaint.getAllocateTime());
	    	System.out.println("Complaint : "+complaint.getComplaintTime());
	        if (complaint.getAllocateTime() != null && complaint.getComplaintTime() != null) {
	            long durationInMillis = complaint.getAllocateTime().getTime() - complaint.getComplaintTime().getTime();
	            System.out.println("durationInMillis : "+durationInMillis);
	            double hours = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) / 60.0; // Convert milliseconds to hours with decimals
	            return hours;
	        }
	        return 0.0;
	    }

	    private double calculateEngineerProcessHours(Complaint complaint) {
	        if (complaint.getInProcessTime() != null && complaint.getAllocateTime() != null) {
	            long durationInMillis = complaint.getInProcessTime().getTime() - complaint.getAllocateTime().getTime();
	            double hours = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) / 60.0; // Convert milliseconds to hours with decimals
	            return hours;
	        }
	        return 0.0;
	    }

	    private double calculateEngineerSolutionHours(Complaint complaint) {
	        if (complaint.getClosedTime() != null && complaint.getAllocateTime() != null) {
	            long durationInMillis = complaint.getClosedTime().getTime() - complaint.getAllocateTime().getTime();
	            double hours = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) / 60.0; // Convert milliseconds to hours with decimals
	            return hours;
	        }
	        return 0.0;
	    }

	    private double calculateTotalHours(Complaint complaint) {
	        if (complaint.getClosedTime() != null && complaint.getComplaintTime() != null) {
	            long durationInMillis = complaint.getClosedTime().getTime() - complaint.getComplaintTime().getTime();
	            double hours = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) / 60.0; // Convert milliseconds to hours with decimals
	            return hours;
	        }
	        return 0.0;
	    }
	    
	    private static final DecimalFormat df = new DecimalFormat("#.##");
	    
	    //export for ticket report
	    public ByteArrayInputStream generateExcelReport(List<TicketResolutionReportDTO> reports) {
	        String[] columns = {"SrNo", "Ticket No", "Equipment ID", "Engineer Name", "Status", "Complaint Date & Time",  "Admin Process Hours", "Engineer Process Hours", "Engineer Solution Hours", "Total Hours"};
	        
	        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	            Sheet sheet = workbook.createSheet("Ticket Resolution Report");
	            
	            Font headerFont = workbook.createFont();
	            headerFont.setBold(true);
	            headerFont.setColor(IndexedColors.BLACK.getIndex());
	            
	            CellStyle headerCellStyle = workbook.createCellStyle();
	            headerCellStyle.setFont(headerFont);
	            
	            // Create Header Row
	            Row headerRow = sheet.createRow(0);
	            
	            for (int col = 0; col < columns.length; col++) {
	                Cell cell = headerRow.createCell(col);
	                cell.setCellValue(columns[col]);
	                cell.setCellStyle(headerCellStyle);
	            }
	            
	            int rowIdx = 1;
	            int srNo = 1;
	            for (TicketResolutionReportDTO report : reports) {
	                Row row = sheet.createRow(rowIdx++);
	                
	                row.createCell(0).setCellValue(srNo++);
	                row.createCell(1).setCellValue(report.getTicketNo());
	                row.createCell(2).setCellValue(
	                	    report.getEquipmentId() + " - " + report.getComapnyName()
	                	);
	                row.createCell(3).setCellValue(report.getAllocateTo());
	               
	                String statusText = getStatusText(report.getStatus());
		            row.createCell(4).setCellValue(statusText);
	                
	                row.createCell(5).setCellValue(report.getComplaintDate().toString());
	               
	                
	                row.createCell(6).setCellValue(df.format(report.getAdminProcessHours()));
	                row.createCell(7).setCellValue(df.format(report.getEngineerProcessHours()));
	                row.createCell(8).setCellValue(df.format(report.getEngineerSolutionHours()));
	                row.createCell(9).setCellValue(df.format(report.getTotalHours()));
	            }
	            
	            workbook.write(out);
	            return new ByteArrayInputStream(out.toByteArray());
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to generate Excel file", e);
	        }
	    }

	    public List<EngineerComplaint_DTO> getEngineerComplaintRecords(UserDetails allocateTo) {
	        // Fetch complaints assigned to the given engineer
	        List<Complaint> complaints = complaintRepo.findByAllocateTo(allocateTo.getId() );
	        
	        // Map complaints to DTOs
	        return complaints.stream()
	                .map(this::mapToEngineerComplaint_DTO)
	                .collect(Collectors.toList());
	    }
	    
	    public EngineerComplaint_DTO mapToEngineerComplaint_DTO(Complaint complaint) {
	        String engineerName = complaint.getAllocateTo() != null 
	            ? complaint.getAllocateTo().getFirstName() + " " + complaint.getAllocateTo().getLastName() 
	            : "N/A";
	        String equipmentId = complaint.getAssetInventory() != null ? complaint.getAssetInventory().getEquipmentId() : "N/A";
	        String priority = complaint.getPriority(); // Assuming priority is a field in Complaint entity
	        String cause = complaint.getCause(); // Assuming cause is a field in Complaint entity
	        String description = complaint.getDescription(); // Assuming description is a field in Complaint entity
	        Date allocateDate = complaint.getAllocateDate(); // Assuming allocateDate is a field in Complaint entity
	        Date inProcessDate = complaint.getInProcessDate(); // Assuming inProcessDate is a field in Complaint entity
	        Date closedDate = complaint.getClosedDate(); // Assuming closedDate is a field in Complaint entity

	        return new EngineerComplaint_DTO(
	                complaint.getTicketNo(),
	                equipmentId,
	                priority,
	                cause,
	                description,
	                complaint.getComplaintDate(),
	                allocateDate,
	                inProcessDate,
	                closedDate,
	                engineerName,
	                complaint.getStatus()
	        );
	    }
	    
	    
	    public ByteArrayInputStream generateExcelRepo(List<EngineerComplaint_DTO> repo) {
	        String[] columns = {
	            "Sr No", "Ticket No", "Asset", "Priority", "Cause", 
	            "Description", "Complaint Date", "Allocate Date", 
	            "In-process Date", "Closed Date", "Assigned Engineer", "Status"
	        };

	        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	            Sheet sheet = workbook.createSheet("Engineer complaint Report");

	            Font headerFont = workbook.createFont();
	            headerFont.setBold(true);
	            headerFont.setColor(IndexedColors.BLACK.getIndex());

	            CellStyle headerCellStyle = workbook.createCellStyle();
	            headerCellStyle.setFont(headerFont);

	            // Create Header Row
	            Row headerRow = sheet.createRow(0);
	            for (int col = 0; col < columns.length; col++) {
	                Cell cell = headerRow.createCell(col);
	                cell.setCellValue(columns[col]);
	                cell.setCellStyle(headerCellStyle);
	            }

	            // Create Data Rows
	            int rowIdx = 1;
	            int srNo = 1;
	            for (EngineerComplaint_DTO report : repo) {
	                Row row = sheet.createRow(rowIdx++);

	                row.createCell(0).setCellValue(srNo++);
	                row.createCell(1).setCellValue(report.getTicketNo());
	                row.createCell(2).setCellValue(report.getEquipmentId());
	                row.createCell(3).setCellValue(report.getPriority());
	                row.createCell(4).setCellValue(report.getCause());
	                row.createCell(5).setCellValue(report.getDescription());
	                row.createCell(6).setCellValue(report.getComplaintDate().toString());
	                row.createCell(7).setCellValue(report.getAllocateDate() != null ? report.getAllocateDate().toString() : "N/A");
	                row.createCell(8).setCellValue(report.getInProcessDate() != null ? report.getInProcessDate().toString() : "N/A");
	                row.createCell(9).setCellValue(report.getClosedDate() != null ? report.getClosedDate().toString() : "N/A");
	                row.createCell(10).setCellValue(report.getAllocateTo());
	                row.createCell(11).setCellValue(getStatusText(report.getStatus()));
	            }

	            workbook.write(out);
	            return new ByteArrayInputStream(out.toByteArray());
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to generate Excel file", e);
	        }
	    }


	    


		@Override
		public List<Complaint> getComplaintByRaisedBy(int userId) {
			// TODO Auto-generated method stub
			return complaintRepo.getComplaintByRaisedBy(userId);
		}


		
		 public ByteArrayInputStream exportComplaintsToExcel(List<Complaint> complaints) throws IOException {
		        String[] headers = { "Sr No", "Ticket No", "Asset", "Priority", "Cause", "Description", "Complaint Date", 
		                              "Complaint Time", "Allocate To", "Status" };

		        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
		            Sheet sheet = workbook.createSheet("Complaints");

		            // Create header row
		            Row headerRow = sheet.createRow(0);
		            for (int i = 0; i < headers.length; i++) {
		                Cell cell = headerRow.createCell(i);
		                cell.setCellValue(headers[i]);
		                cell.setCellStyle(createHeaderCellStyle(workbook));
		            }

		            // Fill data rows
		            int rowIdx = 1;
		            for (Complaint complaint : complaints) {
		                Row row = sheet.createRow(rowIdx++);

		                row.createCell(0).setCellValue(rowIdx - 1); // Sr No
		                row.createCell(1).setCellValue(complaint.getTicketNo());
		                row.createCell(2).setCellValue(complaint.getAssetInventory().getComapnyName() + "-" + complaint.getAssetInventory().getEquipmentId());
		                row.createCell(3).setCellValue(complaint.getPriority());
		                row.createCell(4).setCellValue(complaint.getCause());
		                row.createCell(5).setCellValue(complaint.getDescription());
		                row.createCell(6).setCellValue(complaint.getComplaintDate().toString());
		                row.createCell(7).setCellValue(complaint.getComplaintTime().toString());
		                row.createCell(8).setCellValue(
		                    complaint.getAllocateTo() != null ? complaint.getAllocateTo().getFirstName()+" "+ complaint.getAllocateTo().getLastName(): "Not Allocated");
		                row.createCell(9).setCellValue(getStatusText(complaint.getStatus()));
		            }

		            workbook.write(out);
		            return new ByteArrayInputStream(out.toByteArray());
		        }
		    }

		    private CellStyle createHeaderCellStyle(Workbook workbook) {
		        CellStyle style = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBold(true);
		        style.setFont(font);
		        return style;
		    }

		    private String getStatusText1(int status) {
		        switch (status) {
		            case 1: return "Open";
		            case 2: return "Allocated";
		            case 3: return "In Process";
		            case 4: return "Closed";
		            default: return "Unknown";
		        }
		    }
		    
		    
		    
		    public Map<String, ComplaintStatusDTO> getFilteredComplaints(Date startDate, Date endDate) {
		        // Adjust the end date to include the full day
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(endDate);
		        cal.set(Calendar.HOUR_OF_DAY, 23);
		        cal.set(Calendar.MINUTE, 59);
		        cal.set(Calendar.SECOND, 59);
		        cal.set(Calendar.MILLISECOND, 999);
		        endDate = cal.getTime();

		        // Rest of your logic remains unchanged
		        Map<String, ComplaintStatusDTO> response = new HashMap<>();
		        List<Complaint> totalComplaints = complaintrepo.findComplaintsByDateRange(startDate, endDate);
		        response.put("totalComps", new ComplaintStatusDTO(totalComplaints.size(), totalComplaints));

		        String[] statuses = {"allocated", "non_allocated", "open", "closed", "inprocess"};
		        for (String status : statuses) {
		            List<Integer> statusValues = getStatusValue(status);
		            List<Complaint> complaintsByStatus = new ArrayList<>();
		            for (Integer statusValue : statusValues) {
		                complaintsByStatus.addAll(
		                    complaintrepo.findComplaintsByStatusAndDateRange(statusValue, startDate, endDate)
		                );
		            }
		            response.put(status, new ComplaintStatusDTO(complaintsByStatus.size(), complaintsByStatus));
		        }

		        return response;
		    }


		    private List<Integer> getStatusValue(String status) {
		        // Map status names to integer values
		        switch (status.toLowerCase()) {
		            case "open":
		                return Arrays.asList(1, 2);  
		            case "closed":
		                return Arrays.asList(4);      
		            case "allocated":
		                return Arrays.asList(2);      
		            case "non_allocated":
		                return Arrays.asList(1);      
		            case "inprocess":
		                return Arrays.asList(3);      
		            default:
		                throw new IllegalArgumentException("Unknown status: " + status);
		        }
		    }


		    public long countComplaints(int status, String filterType, Date date, Date fromDate, Date toDate) {
		        switch (filterType.toLowerCase()) {
		            case "day":
		                return complaintRepo.countByStatusAndDate(status, date);
		            case "date":
		                return complaintRepo.countByStatusAndDateRange(status, fromDate, toDate);
		            default:
		                throw new IllegalArgumentException("Invalid filter type");
		        }
		    }



		

}
