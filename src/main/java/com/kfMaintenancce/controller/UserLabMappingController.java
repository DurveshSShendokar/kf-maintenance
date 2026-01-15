package com.kfMaintenancce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.model.UserLabMapping;
import com.kfMaintenancce.repo.LabRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.repo.UserLabMappingRepository;
import com.kfMaintenancce.service.UserLabMappingServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/UserLabPPM")
public class UserLabMappingController {

    @Autowired
    private UserLabMappingServiceImpl service;
    @Autowired
    private UserDetailsRepo userRepo; 


    @Autowired
    private LabRepo labRepo;

    @PostMapping("/assign")
    public ResponseEntity<Map<String, Object>> assignLab(@RequestBody UserLabMapping mapping) {
        String response = service.assignLabToUser(mapping);

        Map<String, Object> result = new HashMap<>();
        if (response.contains("already assigned")) {
            result.put("code", 500);
            result.put("message", response);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        result.put("code", 200);
        result.put("message", response);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/user_labs/{userId}")
    public ResponseEntity<?> getLabsByUserId(@PathVariable int userId) {
        UserDetails user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<UserLabMapping> mappings = service.getLabsByUser(user);

        List<Lab> labs = mappings.stream()
            .map(UserLabMapping::getLab)
            .collect(Collectors.toList());

        return ResponseEntity.ok(labs);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMapping(@PathVariable int id) {
        service.deleteMapping(id);
        return ResponseEntity.ok("Deleted");
    }
}

