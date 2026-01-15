package com.kfMaintenancce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.Lab;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.model.UserLabMapping;
import com.kfMaintenancce.repo.UserLabMappingRepository;

@Service
public class UserLabMappingServiceImpl {



	    @Autowired
	    private UserLabMappingRepository mappingRepo;

	    public String assignLabToUser(UserLabMapping mapping) {
	        if (mappingRepo.existsByUserAndLab(mapping.getUser(), mapping.getLab())) {
	            return "This lab is already assigned to the user.";
	        }

	        mappingRepo.save(mapping);
	        return "Lab assigned successfully.";
	    }


	    public List<UserLabMapping> getLabsByUser(UserDetails user) {
	        return mappingRepo.findByUser(user);
	    }


	    public void deleteMapping(int id) {
	        mappingRepo.deleteById(id);
	    }

	   
	}
