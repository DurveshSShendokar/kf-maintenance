package com.kfMaintenancce.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.kfMaintenancce.dto.UserDetailsDTO;
import com.kfMaintenancce.model.CompanyProfile;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.CompanyProfileRepo;
import com.kfMaintenancce.repo.UserDetailsRepo;

@Service
public class UserAndLoginServiceImpl implements UserAndLoginService {
	@Autowired
	UserDetailsRepo userDetailsRepo;
	
	 @Autowired
     CompanyProfileRepo companyRepo;

//	@Override
//	public UserDetails getUserByuserNameAndPassword(String userId, String password) {
//		// TODO Auto-generated method stub
//		Optional<UserDetails> optional = userDetailsRepo.getUserByUserName(userId);
//		System.out.println("userName "+userId+"  "+optional.isPresent());
//		if(optional.isPresent()){
//			
//			
//			if(optional.get().getPassword().equals(password)){
//				return optional.get();
//			}else{
//				
//			}return null;
//			
//		}else{
//			return null;	
//		}
//		
//	}
//	
	
	@Override
	public UserDetails getUserByUserNameAndPassword(String userName, String password) {
	    Optional<UserDetails> optional = userDetailsRepo.findByUserName(userName);
	    System.out.println("Looking for userName=" + userName + ", found=" + optional.isPresent());

	    if (optional.isPresent()) {
	        System.out.println("DB Password=" + optional.get().getPassword() + ", Input Password=" + password);
	        if (optional.get().getPassword().equals(password)) {
	            return optional.get();
	        } else {
	            System.out.println("❌ Password mismatch!");
	        }
	    } else {
	        System.out.println("❌ User not found or inactive");
	    }
	    return null;
	}

	
//	@Override
//	public UserDetails getUserByIdAndPassword(String userId, String password) {
//	    Optional<UserDetails> optional = userDetailsRepo.getUserById(userId); // now correctly using userId
//	    System.out.println("ID " + userId + "  Found: " + optional.isPresent());
//
//	    if (optional.isPresent()) {
//	        UserDetails user = optional.get();
//
//	        // If you are storing plain text passwords
//	        if (user.getPassword().equals(password)) {
//	            return user;
//	        } else {
//	            System.out.println("Incorrect password for userId: " + userId);
//	            return null;
//	        }
//	    } else {
//	        System.out.println("User not found for ID: " + userId);
//	        return null;
//	    }
//	}

	
	public UserDetails getUserDetailsById(int userId) {
	    return userDetailsRepo.findById(userId).orElse(null); // Adjust this based on your repo
	}


	public Optional<UserDetails> getUserById(String id) {
		// TODO Auto-generated method stub
		return userDetailsRepo.getUserById(id);
	}

	@Override
	public List<UserDetails> getUsers() {
		// TODO Auto-generated method stub
		return userDetailsRepo.findAll();
	}

    public UserDetails getUserByIds(int id) {
        return userDetailsRepo.findById(id).orElse(null);
    }



    @Override
	public Page<UserDetailsDTO> getUserss(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
	    return userDetailsRepo.findAllUserDTO(pageable);
	}

	@Override
	public void addUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		 System.out.println("Saving user: " + userDetails);
		 
		  // generate next empId
		    Integer maxEmpId = userDetailsRepo.getMaxEmpId();
		    Integer nextEmpId = maxEmpId + 1;

		    userDetails.setEmpId(nextEmpId);
		 
		 userDetails.setEmpCode(generateEmpCode());
		    userDetailsRepo.save(userDetails);
		    System.out.println("User saved.");
	}


	
	private String generateEmpCode() {

	    // Get company prefix
	    CompanyProfile profile = companyRepo.findTopByOrderByIdAsc()
	            .orElseThrow(() -> new RuntimeException("Company profile not found"));

	    String prefix = profile.getCompanyCodePrefix().toUpperCase();

	    // Current year & month
	    LocalDate today = LocalDate.now();
	    String yearMonth = today.format(DateTimeFormatter.ofPattern("yyyyMM"));

	    // Count employees created in SAME YEAR + MONTH
	    long monthlyCount = userDetailsRepo.countByCreatedAtStartsWith(yearMonth);

	    // 3-digit sequence (001, 002, ...)
	    String sequence = String.format("%03d", monthlyCount + 1);

	    // Final code
	    return prefix + "-" + yearMonth + "-" + sequence;
	}
	
	
	
	@Override
	public List<UserDetails> getAlluserForMachine() {
		// TODO Auto-generated method stub
		return userDetailsRepo.getAlluserForMachine();
	}

	@Override
	public List<UserDetails> getAllUserForComplaintAllocation() {
		// TODO Auto-generated method stub
		return userDetailsRepo.getAllUserForComplaintAllocation();
	}

	@Override
	public void deleteUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		userDetailsRepo.delete(userDetails);
	}

	@Override
	public List<UserDetails> getITEnginner() {
		// TODO Auto-generated method stub
		return userDetailsRepo.getAllUserForComplaintAllocation();
	}

}
