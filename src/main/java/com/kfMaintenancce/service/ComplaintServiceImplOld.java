package com.kfMaintenancce.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.TicketResolutionReportDTO;
import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Image;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.ImageRepository;
import com.kfMaintenancce.repo.UserDetailsRepo;

@Service
public class ComplaintServiceImplOld implements ComplaintServiceOLD {

    @Autowired
    private ComplaintRepoOLD complaintRepo;
    
    @Autowired
    private ImageRepository imageRepository; 
    
    @Autowired
    private UserDetailsRepo userDetailsRepo;
  
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
	public List<Complaint> getAllocatedComplaintByEnginner(int empId) {
		// TODO Auto-generated method stub
		return complaintRepo.getAllocatedComplaintByEnginner(empId);
	}


	@Override
	public List<Complaint> getComplaintByEquipmentId(String equipmentId) {
		// TODO Auto-generated method stub
		return complaintRepo.getComplaintByEquipmentId(equipmentId);
	}
	
	
	  public List<Complaint> getOpenComplaints() {
	        return complaintRepo.findOpenComplaints();
	    }

	    
	    public List<Complaint> getClosedComplaints() {
	        return complaintRepo.findClosedComplaints();
	    }

	    public List<Complaint> getAllocateComplaints() {
	        return complaintRepo.findAllocateComplaints();
	    }
	    
	    public List<Complaint> getInprocessComplaints() {
	        return complaintRepo.findInprocessComplaints();
	    }

	   
	    public List<Complaint> getNonAllocatedComplaints() {
	        return complaintRepo.findNonAllocatedComplaints();
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
	    public List<Complaint> getComplaintReport(Date fromDate, Date toDate, Date selectedDate) {
	        List<Complaint> complaints =new ArrayList<Complaint>();
	        if (selectedDate != null) {
	        	complaints=complaintRepo.findByDate(selectedDate);
	        	
	        }else {
	        	complaints=complaintRepo.findByDateRange(fromDate, toDate);
	        }
	        return complaints;
	    }

	    
	    // Ticket resolution report with date-wise and day-wise filter
	    public List<TicketResolutionReportDTO> getTicketResolutionReport(Date fromDate, Date toDate, Date selectedDate) {
	        List<Complaint> complaints = complaintRepo.findAll();
	        
	        return complaints.stream()
	                .filter(complaint -> {
	                    if (selectedDate != null) {
	                        return isSameDay(complaint.getComplaintDate(), selectedDate);
	                    } else if (fromDate != null && toDate != null) {
	                        return isWithinDateRange(complaint.getComplaintDate(), fromDate, toDate);
	                    } else {
	                        return true; // If no filter is applied, return all complaints
	                    }
	                })
	                .map(this::mapToTicketResolutionReportDTO)
	                .collect(Collectors.toList());
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
	        if (complaint.getAllocateTime() != null && complaint.getComplaintTime() != null) {
	            long durationInMillis = complaint.getAllocateTime().getTime() - complaint.getComplaintTime().getTime();
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
	                row.createCell(2).setCellValue(report.getEquipmentId());
	                row.createCell(3).setCellValue(report.getAllocateTo());
	                row.createCell(4).setCellValue(report.getStatus());
	                row.createCell(5).setCellValue(report.getComplaintDate().toString());
	               
	                row.createCell(6).setCellValue(report.getAdminProcessHours());
	                row.createCell(7).setCellValue(report.getEngineerProcessHours());
	                row.createCell(8).setCellValue(report.getEngineerSolutionHours());
	                row.createCell(9).setCellValue(report.getTotalHours());
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


		@Override
		public Complaint saveComplaint(Complaint complaint) {
			// TODO Auto-generated method stub
			return complaintRepo.save(complaint);
		}
		  public Optional<Image> findComplaintImageById(int comp_id) {
		        Complaint complaint = complaintRepo.findById(comp_id).orElseThrow(() -> new RuntimeException("Complaint not found"));
		        return imageRepository.findByComplaint(complaint).stream().findFirst();  // Adjust to return all images if needed
		    }
		  
		  // Retrieve all images for a complaint
		    public List<Image> findComplaintsImagesById(int comp_id) {
		        Complaint complaint = complaintRepo.findById(comp_id).orElseThrow(() -> new RuntimeException("Complaint not found"));
		        return imageRepository.findByComplaint(complaint);  // Return all images associated with the complaint
		    }



		@Override
		public Complaint updateComplaint1(Complaint complaint) {
			// TODO Auto-generated method stub
			return complaintRepo.save(complaint);
		}

		public void uploadComplaintImages(int comp_id, MultipartFile[] files, int userId) throws IOException {
		    Optional<Complaint> complaintOptional = complaintRepo.findById(comp_id);

		    if (complaintOptional.isPresent()) {
		        Complaint complaint = complaintOptional.get();
		        UserDetails user = userDetailsRepo.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found"));
		        String fullName = user.getFirstName() + " " + user.getLastName(); 

		        for (MultipartFile file : files) {
		            Image image = new Image();
		            image.setImageData(file.getBytes());  // Store the image as bytes (BLOB)
		            image.setImageUrl(file.getOriginalFilename());
		            image.setComplaint(complaint);
		            image.setUploadDate(new Date());
		            image.setUploadedByUsername(fullName); // Set the username

		            imageRepository.save(image);  // Save the image to the repository
		        }
		    } else {
		        throw new RuntimeException("Complaint not found with id: " + comp_id);
		    }
		}




}
