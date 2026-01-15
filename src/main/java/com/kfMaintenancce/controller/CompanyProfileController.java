package com.kfMaintenancce.controller;

   
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.config.FileConfig;
import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.dto.ResponceObj;
import com.kfMaintenancce.model.CompanyProfile;
import com.kfMaintenancce.repo.CompanyProfileRepo;

import java.io.File;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @CrossOrigin("*")
    @RequestMapping("company_profile")
    public class CompanyProfileController {

        @Autowired
        CompanyProfileRepo companyRepo;

        @Autowired
        FileConfig fileConfig;

        @PostMapping(value = "/addCompanyProfile", consumes = {"multipart/form-data"})
        public ResponseEntity<ResponceObj> addCompanyProfile(
                @RequestParam String companyName,
                @RequestParam String address,
                @RequestParam String city,
                @RequestParam String state,
                @RequestParam String country,
                @RequestParam(required = false) MultipartFile logo
        ) {

        	ResponceObj response = new ResponceObj();

            try {
                CompanyProfile company = new CompanyProfile();
                company.setCompanyName(companyName);

                company.setAddress(address);
                company.setCity(city);
                company.setState(state);
                company.setCountry(country);

                if (logo != null && !logo.isEmpty()) {
                    String savedPath = fileConfig.saveFile(logo, "company-logo", "logo");
                    company.setLogoPath(savedPath);
                }

                companyRepo.save(company);

                response.setCode(200);
                response.setMessage("Company profile added successfully");
                response.setData(company);
                return ResponseEntity.ok(response);

            } catch (Exception e) {
                e.printStackTrace();
                response.setCode(500);
                response.setMessage("Something went wrong");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }




        @GetMapping({"/getAllCompanyProfileCount"})
        @ResponseBody
        public int getAllCompanyCount() {
            int count = 0;
            try {

                count = (int) this.companyRepo.count();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;
        }



       


        @PutMapping(value = "/updateCompanyProfile/{id}", consumes = {"multipart/form-data"})
        public ResponseEntity<?> updateCompanyProfile(
                @PathVariable int id,
                @RequestParam(required = false) String companyName,
                @RequestParam(required = false) String address,
                @RequestParam(required = false) String city,
                @RequestParam(required = false) String state,
                @RequestParam(required = false) String country,
                @RequestParam(required = false) MultipartFile logo
        ) {
            try {
                CompanyProfile profile = companyRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Company profile not found"));

                // Update only provided fields
                if (companyName != null) profile.setCompanyName(companyName);

                if (address != null) profile.setAddress(address);
                if (city != null) profile.setCity(city);
                if (state != null) profile.setState(state);
                if (country != null) profile.setCountry(country);

                // Upload new logo if provided
                if (logo != null && !logo.isEmpty()) {
                    String savedPath = fileConfig.saveFile(logo, "company-logo", "logo");
                    profile.setLogoPath(savedPath);
                }

                companyRepo.save(profile);

                return ResponseEntity.ok(profile);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update company profile: " + e.getMessage());
            }
        }



        @DeleteMapping("/deleteCompanyProfile/{id}")
        public @ResponseBody ResponceObj deleteCompanyProfile(@PathVariable int id) {
        	ResponceObj responceObject = new ResponceObj();
            try {
                Optional<CompanyProfile> optionalCompany = companyRepo.findById(id);
                if (optionalCompany.isPresent()) {
                    companyRepo.deleteById(id);
                    responceObject.setCode(200);
                    responceObject.setMessage("CompanyProfile Deleted Successfully");
                } else {
                    responceObject.setCode(404);
                    responceObject.setMessage("CompanyProfile Not Found");
                }
            } catch (Exception e) {
                responceObject.setCode(500);
                responceObject.setMessage("Something Went Wrong");
                e.printStackTrace();
            }
            return responceObject;
        }

        @RequestMapping(value = "/getCompanyProfileListByLimit", method = RequestMethod.GET)
        public @ResponseBody List<CompanyProfile> getCompanyProfileListByLimit(@RequestParam("pageNo") int pageNo,
                                                           @RequestParam("perPage") int perPage) {
            List<CompanyProfile> list = new ArrayList<>();
            try {
            	list = this.companyRepo.getCompanyProfileByLimit(pageNo, perPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }


        @PostMapping({"/getCompanyProfileListByGroupSearch"})
        @ResponseBody
        public List<CompanyProfile> getCompanyProfileListByGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
            List<CompanyProfile> list = new ArrayList<>();
            try {
            	list = this.companyRepo.getCompanyProfileByLimitAndGroupSearch(groupSearchDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        
        @PostMapping({"/getCompanyProfileCountByGroupSearch"})
        @ResponseBody
        public int getCompanyProfileCountByGroupSearch(@RequestBody GroupSearchDTO groupSearchDTO) {
            int count = 0;
            try {

            	count = this.companyRepo.getCompanyProfileCountByLimitAndGroupSearch(groupSearchDTO);
                boolean bool = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;
        }



        @GetMapping({"/getAllCompanyProfileList"})
        @ResponseBody
        public List<CompanyProfile> getGoalList() {
            List<CompanyProfile> list = new ArrayList<>();
            try {
                list = this.companyRepo.findAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }






    }
