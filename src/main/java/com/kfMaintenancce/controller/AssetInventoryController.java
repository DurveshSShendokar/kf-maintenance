package com.kfMaintenancce.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.QRScanDTO;
import com.kfMaintenancce.dto.ReportReqObj;
import com.kfMaintenancce.dto.Status;
import com.kfMaintenancce.dto.UserManualStatus;

import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Complaint;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Spare;
import com.kfMaintenancce.model.Upload_IT_Manual;

import com.kfMaintenancce.repo.AssetInventoryRepo;
import com.kfMaintenancce.repo.AssetSpareAssignRepository;
import com.kfMaintenancce.repo.AssetSpareRepository;
import com.kfMaintenancce.repo.ComplaintRepoOLD;
import com.kfMaintenancce.repo.SpareRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.repo.uploadITManualRepo;
import com.kfMaintenancce.service.AssetInventoryService;
import com.kfMaintenancce.service.AssetSpareAssignService;
import com.kfMaintenancce.service.SpareServices;
import com.kfMaintenancce.service.UploadITManualService;

@RestController
@CrossOrigin("*")
@RequestMapping("/assetInventory")
public class AssetInventoryController {
	
	@Autowired
    private SpareRepo spareRepo;
	
	
	@Autowired
	AssetSpareAssignService spareAssignService;//AssetSpareAssignRepository
	
	@Autowired
	AssetSpareAssignRepository assetSpareAssignRepository;
	
	@Autowired
	SpareServices spareServices;//
	
	@Autowired
	UploadITManualService uploadITManualService;
	
	@Autowired
	AssetInventoryService assetInventoryService;
	
	 @Autowired
	    private UserDetailsRepo userDetailsRepo;
	 
	 @Autowired
		uploadITManualRepo UploadITManualRepo;
	
	@Autowired
	AssetInventoryRepo assetInventoryRepo;
	
	@Autowired
	ComplaintRepoOLD complaintRepo;
	
	@Autowired
	uploadITManualRepo uploadITManualRepos;
	
	@PostMapping("/getAssetInventoryDetailsByQrcode")
	public @ResponseBody QRScanDTO getAssetInventoryDetailsByQrcode(@RequestBody ReportReqObj reportReqObj) {
		QRScanDTO qrScanDTO = new QRScanDTO();
		try {
			List<Complaint>  openComplaints=new ArrayList<Complaint>();
			List<Complaint>  allocatedComplaints=new ArrayList<Complaint>();
			List<Complaint>  inProcessComplaints=new ArrayList<Complaint>();
			List<Complaint>  CloedComplaints=new ArrayList<Complaint>();
			Optional<AssetInventory>  optional = assetInventoryRepo.getAssetInventoryByEquipmentId(reportReqObj.getEquipmentId());
			if (optional.isPresent()) {
				qrScanDTO.setAssetInventory(optional.get());
			}
			List<Complaint> allComlaint=complaintRepo.getComplaintByEquipmentId(reportReqObj.getEquipmentId());
			for(Complaint complaint:allComlaint) {
				if(complaint.getStatus()==1 && complaint.getStatus()==1 ) {
					openComplaints.add(complaint);
				}
				if(complaint.getStatus()==3) {
					inProcessComplaints.add(complaint);
				}
				if(complaint.getStatus()==4) {
					CloedComplaints.add(complaint);
				}
				
			}
			
			qrScanDTO.setCloedComplaints(CloedComplaints);
			qrScanDTO.setInProcessComplaints(inProcessComplaints);
			qrScanDTO.setOpenComplaints(openComplaints);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return qrScanDTO;
	}
	
	
	
	
	
	
	
	
	
	
	

	@PostMapping(value = "/addAssetInventory", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addAssetInventory(@RequestBody AssetInventory assetInventory ) {
		Status status= new Status();
		try {
			assetInventoryService.addAssetInventory(assetInventory);
			status.setCode(200);
			status.setMessage("AssetInventory is added successfully");
			return status;
		} catch (Exception e) {
			// e.printStackTrace();
			e.printStackTrace();
			return new Status(e.toString());
		}

	}
	
	
	@GetMapping("/getAssetInventoryByLimit")
	public @ResponseBody List<AssetInventory> getAssetsByLimit(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<AssetInventory> list = new ArrayList<AssetInventory>();
		try {
			list = assetInventoryService.getAssetInventoryByLimit(pageNo,perPage);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/getAssetInventoryCount")
	public @ResponseBody int getAssetInventoryCount() {
		int count = 0;
		try {
				count = (int) assetInventoryService.count();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@PostMapping("/getAssetInventoryByLimitAndGroupSearch")
	public @ResponseBody List<AssetInventory> getAssetInventoryByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		List<AssetInventory> list = new ArrayList<AssetInventory>();
		try {
			
			list = assetInventoryService.getAssetInventoryByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@PostMapping("/getCountAssetByLimitAndGroupSearch")
		public @ResponseBody int getAssetCountByLimitAndGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
		int count =0;
		try {
			
			count = assetInventoryService.getAssetCountByLimitAndGroupSearch(groupSearchDTO);


			int srNo=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@DeleteMapping("/delete/{asset_inventory_id}")
	   public ResponseEntity<Map<String, String>> deleteassetInventory(@PathVariable int asset_inventory_id) {
		assetInventoryService.deleteassetInventory(asset_inventory_id);
	       String message = "Asset_Inventory with ID " + asset_inventory_id + " has been deleted successfully.";
	       Map<String, String> response = new HashMap<>();
	       response.put("message", message);
	       return ResponseEntity.ok(response);
	   }
	

	@GetMapping("/non-allocated")
	public ResponseEntity<Map<String, Object>> getNonAllocatedAssets() {
	    List<AssetInventory> nonAllocatedAssets = assetInventoryService.getNonAllocatedAssets();

	    Map<String, Object> response = new HashMap<>();
	    response.put("count", nonAllocatedAssets.size());
	    response.put("data", nonAllocatedAssets);

	    return ResponseEntity.ok(response);
	}

	 @GetMapping("/AllocatedAssets")
	 public ResponseEntity<Map<String, Object>> getAllocatedAssets() {
	     List<AssetInventory> allocatedAssets = assetInventoryRepo.findAllocatedAssets();

	     Map<String, Object> response = new HashMap<>();
	     response.put("count", allocatedAssets.size());
	     response.put("data", allocatedAssets);

	     return ResponseEntity.ok(response);
	 }

	 
 @GetMapping("/all")
 public ResponseEntity<List<AssetInventory>> getAllAssets() {
     List<AssetInventory> assets = assetInventoryRepo.findAll();
     return new ResponseEntity<>(assets, HttpStatus.OK);
 }
 
	@GetMapping("/Pagelist")
	public Page<AssetInventory> getAllAssets(@RequestParam int page, @RequestParam int size) {
	    Pageable pageable = PageRequest.of(page-1, size);
	    return assetInventoryRepo.findAll(pageable);
	}
    
    
    @PostMapping("/uploadExcel")
    public ResponseEntity<Status> uploadExcel(@RequestPart("file") MultipartFile file) throws IOException {
        Status status = new Status();
        try {
            boolean duplicatesFound = assetInventoryService.processExcelFile(file);
            if (duplicatesFound) {
                status.setCode(400); // Bad Request
                status.setMessage(" duplicate equipement id found.");
            } else {
                status.setCode(200);
                status.setMessage("Excel file uploaded and processed successfully");
            }
        } catch (EncryptedDocumentException | InvalidFormatException e) {
            e.printStackTrace();
            status.setCode(500);
            status.setMessage("Error occurred while processing the Excel file");
        }
        return ResponseEntity.status(HttpStatus.valueOf(status.getCode())).body(status);
    }
    
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportExcel() {
        try {
            byte[] excelBytes = assetInventoryService.generateExcelFile();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "asset_inventory.xlsx");
            headers.setContentLength(excelBytes.length);
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadExcelTemplate() {
        try {
            byte[] excelBytes = assetInventoryService.generateExcelTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=asset_inventory_template.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //upload user manual
    @PostMapping(value = "/uploadUserManual", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody UserManualStatus uploadUserManual(@RequestParam("files") MultipartFile[] files,
                                                           @RequestParam("model") String model
                                                           //@RequestHeader("userId") int userId
                                                           ) 
    {
        UserManualStatus status = new UserManualStatus();
        
       // String uploadDirPath = "D:\\eclipse-workspace\\KFMaintenanceAP\\KFMaintenanceAP\\src\\main\\resources\\static\\ITUploads";
      String uploadDirPath = "/home/zionit/KFMaintenance/User_manual";
       // String uploadDirPath = "D:\\MaintenaceApplication\\Uploads_IT";
        
        File dir = new File(uploadDirPath);
        
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Fetch all machines with the specified name
        List<AssetInventory> assets = assetInventoryService.getAssetsByModel(model);
        
//        UserDetails user = userDetailsRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        String fullName = user.getFirstName() + " " + user.getLastName();
        
        if (!assets.isEmpty()) {
            try {
                // Iterate through each file and process
                for (MultipartFile file : files) {
                    String originalFileName = file.getOriginalFilename();
                    String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
                    
                    File uploadedFile = new File(dir, uniqueFileName);
                    file.transferTo(uploadedFile);
                    
                    // Save each file for every machine
                    for (AssetInventory selectedAsset : assets) {
                        Upload_IT_Manual uploadITManual = new Upload_IT_Manual();
                        uploadITManual.setUserManualName(originalFileName);
                        uploadITManual.setUserManualPath(uploadedFile.getAbsolutePath());
                        uploadITManual.setFileType(file.getContentType());
                        uploadITManual.setUploadDate(new Date()); 
                        uploadITManual.setAssetInventory(selectedAsset);
                       // uploadITManual.setUploadedByUsername(fullName);
                        
                        UploadITManualRepo.save(uploadITManual);
                    }
                }
                
                status.setCode(200);
                status.setMessage("User manuals uploaded successfully for Assets named: " + model);
            } catch (IOException e) {
                e.printStackTrace();
                status.setCode(500);
                status.setMessage("Error uploading files: " + e.getMessage());
            }
        } else {
            status.setCode(404);
            status.setMessage("No machines found with the name: " + model);
        }
        
        return status;
    }
    
    // Fetch uploaded document by ID ( AssetInventory ID)
    @GetMapping("/fetchUploadedDocuments/{asset_inventory_id}")
    public ResponseEntity<List<Upload_IT_Manual>> fetchUploadedDocuments(@PathVariable("asset_inventory_id") int asset_inventory_id) {
       
        List<Upload_IT_Manual> uploadedDocuments = UploadITManualRepo.findByassetInventoryID(asset_inventory_id);

        if (uploadedDocuments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); 
        }

        return ResponseEntity.ok(uploadedDocuments); 
    }
    

    // ✅ 1️⃣ Pagination API
    @GetMapping("/fetchUploadedDocumentsWithPage")
    public ResponseEntity<Page<Upload_IT_Manual>> fetchUploadedDocumentsPage(
            @RequestParam int asset_inventory_id,
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Pageable pageable = PageRequest.of(pageNo - 1, perPage);
        Page<Upload_IT_Manual> uploadedDocuments = uploadITManualRepos.findByAssetInventoryIdPage(asset_inventory_id, pageable);
        return ResponseEntity.ok(uploadedDocuments);
    }

    // ✅ 2️⃣ Searching + Pagination API
    @GetMapping("/fetchUploadedDocumentsWithSearch")
    public ResponseEntity<Page<Upload_IT_Manual>> fetchUploadedDocumentsSearch(
            @RequestParam int asset_inventory_id,
            @RequestParam( defaultValue = "") String keyword,
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Pageable pageable = PageRequest.of(pageNo - 1, perPage);
        Page<Upload_IT_Manual> uploadedDocuments = uploadITManualRepos.findByAssetInventoryIdAndSearch(asset_inventory_id, keyword, pageable);
        return ResponseEntity.ok(uploadedDocuments);
    }

    // ✅ 3️⃣ Count API
    @GetMapping("/fetchUploadedDocumentsWithCount")
    public ResponseEntity<Map<String, Object>> fetchUploadedDocumentsCount(@RequestParam int asset_inventory_id) {
        long count = uploadITManualRepos.countByAssetInventoryId(asset_inventory_id);
        Map<String, Object> response = new HashMap<>();
        response.put("asset_inventory_id", asset_inventory_id);
        response.put("totalDocuments", count);
        return ResponseEntity.ok(response);
    }


    
    //view upload document (user manual )
    @GetMapping("/viewUploadedDocument/{upload_id}")
    public ResponseEntity<Resource> viewUploadedDocument(@PathVariable("upload_id") int upload_id) {
        Optional<Upload_IT_Manual> manualOptional = UploadITManualRepo.findById(upload_id);
        if (manualOptional.isPresent()) {
            Upload_IT_Manual manual = manualOptional.get();
            // Check if the manual is active (1)
            if (manual.getActive() == 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);  
            }

            File file = new File(manual.getUserManualPath());
            if (file.exists()) {
                Resource resource = new FileSystemResource(file);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(manual.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + manual.getUserManualName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
    }


    // delete user manual ( document)
    @DeleteMapping("/{id}/document_delete")
    public ResponseEntity<?> deleteUserDocument(@PathVariable int id) {
        try {
        	UploadITManualRepo.deleteById(id);
        	 return ResponseEntity.noContent().build(); 
        	 } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
    
 // Update the active status of a user manual(documents) by upload_id
    @PutMapping("/updateManualStatus/{upload_id}")
    public ResponseEntity<Status> updateManualStatus(@PathVariable("upload_id") int upload_id, @RequestParam("active") int active) {
        Status status = new Status();
        try {
            Optional<Upload_IT_Manual> manualOptional = UploadITManualRepo.findById(upload_id);
            if (manualOptional.isPresent()) {
                Upload_IT_Manual manual = manualOptional.get();
                
               
                if (active == 1 || active == 0) {
                    manual.setActive(active); 
                    UploadITManualRepo.save(manual);  
                    
                    status.setCode(200);
                    status.setMessage("Manual status updated successfully.");
                } else {
                    status.setCode(400);
                    status.setMessage("Invalid status value. Only 1 (active) or 0 (inactive) are allowed.");
                }
            } else {
                status.setCode(404);
                status.setMessage("Manual not found for the given upload ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.setCode(500);
            status.setMessage("Error updating manual status: " + e.getMessage());
           
        }
        return ResponseEntity.status(HttpStatus.valueOf(status.getCode())).body(status);
    }

    
 // Update the active status of a spare by spare_id
    @PutMapping("/updateSpareStatus/{spare_id}")
    public ResponseEntity<Status> updateSpareStatus(@PathVariable("spare_id") int spare_id, @RequestParam("active") int active) {
        Status status = new Status();
        try {
            Optional<Spare> spareOptional = spareRepo.findById(spare_id); 
            if (spareOptional.isPresent()) {
                Spare spare = spareOptional.get();

                if (active == 1 || active == 0) {
                    spare.setActive(active);
                    spareRepo.save(spare); 
                    
                    status.setCode(200);
                    status.setMessage("Spare status updated successfully.");
                } else {
                    status.setCode(400);
                    status.setMessage("Invalid status value. Only 1 (active) or 0 (inactive) are allowed.");
                }
            } else {
                status.setCode(404);
                status.setMessage("Spare not found for the given spare ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.setCode(500);
            status.setMessage("Error updating spare status: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.valueOf(status.getCode())).body(status);
    }
    

    //download user manual
    
    @GetMapping("/downloadUserManual/{upload_id}")
    public ResponseEntity<Resource> downloadUserManual(@PathVariable("upload_id") int upload_id) {
        Optional<Upload_IT_Manual> manualOptional = UploadITManualRepo.findById(upload_id);

        if (manualOptional.isPresent()) {
            Upload_IT_Manual manual = manualOptional.get();
            
            // Check if the manual is active (active = 1)
            if (manual.getActive() == 1) {
                File file = new File(manual.getUserManualPath());

                if (file.exists()) {
                    Resource resource = new FileSystemResource(file);
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(manual.getFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + manual.getUserManualName() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(null);
                }
            } else {
                // Return 403 Forbidden if the file is inactive
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    

    //spare assign for asset
    @PostMapping("/{assetInventoryId}/Assetspares")
    public ResponseEntity<Status> addSparesToAsset(
            @PathVariable int assetInventoryId, 
            @RequestBody List<Spare> spares) {
        Status status = new Status();
        try {
            AssetInventory asset = assetInventoryService.findById(assetInventoryId);
            if (asset != null) {
                for (Spare spare : spares) {
                	
                	// Check if the spare is already assigned to the asset
                    if (assetSpareAssignRepository.findByAssetInventoryIdAndSpareId(assetInventoryId, spare.getSpare_id()).isPresent()) {
                        throw new RuntimeException("Spare ID " + spare.getSpare_id() + " is already assigned to the asset with ID " + assetInventoryId);
                    }
                	
                	AssetSpareAssign spareAssign = new AssetSpareAssign();
                	spareAssign.setAssetinventory(asset);  // Associate the asset
                    spareAssign.setSpare(spare);  // Associate the spare
                    spareAssignService.addSpareAssign(spareAssign);
                }
                status.setCode(200);
                status.setMessage("Spares assigned to asset successfully");
            } else {
                status.setCode(404);
                status.setMessage("Asset not found");
            }
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok(status);
    }


 // Fetch AssetSpareAssign by ID ( AssetInventory ID)
    @GetMapping("/fetch/{asset_inventory_id}")
    public  List<AssetSpareAssign>  getSpareAssignById(@PathVariable int asset_inventory_id) {
       List<AssetSpareAssign> assetSpareAssign = assetSpareAssignRepository.findByassetInventoryID(asset_inventory_id);
       return assetSpareAssign;
    }
    
 // Fetch AssetSpareAssign by ID ( AssetInventory ID)
    @GetMapping("/fetchAssetSpare/{asset_inventory_id}")
    public  List<AssetSpareAssign>  fetchAssetSpare(@PathVariable int asset_inventory_id) {
       List<AssetSpareAssign> assetSpareAssign = assetSpareAssignRepository.findByassetInventoryID(asset_inventory_id);
       return assetSpareAssign;
    }
    
    // ✅ 1️⃣ Pagination API
    @GetMapping("/fetchAssetSpareWithPage")
    public ResponseEntity<Page<AssetSpareAssign>> fetchAssetSparePage(
            @RequestParam int asset_inventory_id,
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Pageable pageable = PageRequest.of(pageNo - 1, perPage);
        Page<AssetSpareAssign> page = assetSpareAssignRepository.findByAssetInventoryIdPage(asset_inventory_id, pageable);

        return ResponseEntity.ok(page);
    }

    // ✅ 2️⃣ Search + Pagination API
    @GetMapping("/fetchAssetSpareWithSearch")
    public ResponseEntity<Page<AssetSpareAssign>> fetchAssetSpareSearch(
            @RequestParam int asset_inventory_id,
            @RequestParam( defaultValue = "") String keyword,
            @RequestParam int pageNo,
            @RequestParam int perPage) {

        Pageable pageable = PageRequest.of(pageNo - 1, perPage);
        Page<AssetSpareAssign> page = assetSpareAssignRepository.findByAssetInventoryIdAndSearch(asset_inventory_id, keyword, pageable);

        return ResponseEntity.ok(page);
    }

    // ✅ 3️⃣ Count API
    @GetMapping("/fetchAssetSpareWithCount")
    public ResponseEntity<Map<String, Object>> fetchAssetSpareCount(
            @RequestParam int asset_inventory_id) {

        long count = assetSpareAssignRepository.countByAssetInventoryId(asset_inventory_id);

        Map<String, Object> response = new HashMap<>();
        response.put("asset_inventory_id", asset_inventory_id);
        response.put("totalSpares", count);

        return ResponseEntity.ok(response);
    }
    
    
    
    

    // delete assign spare
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAssetSpareAssign(@PathVariable int id) {
        try {
        	spareAssignService.deleteAssetSpareAssignById(id);
        	 return ResponseEntity.noContent().build(); 
        	 } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
    
    
    
    
    
    // active document Asset Inventory id
    @GetMapping("/active/{assetInventoryId}")//
    public List<Upload_IT_Manual> getManualsByAssetInventoryId(@PathVariable int assetInventoryId) {
        return uploadITManualService.getActiveManualsByAssetInventoryId(assetInventoryId);
    }
    
    // Fetch only active spares by AssetInventory ID
    @GetMapping("/fetch/active/{asset_inventory_id}")
    public List<AssetSpareAssign> getActiveSpareAssignById(@PathVariable int asset_inventory_id) {
        return assetSpareAssignRepository.findActiveSparesByAssetInventoryId(asset_inventory_id);
    }


   
    

}
