package com.kfMaintenancce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kfMaintenancce.dto.UserDetailsDTO;
import com.kfMaintenancce.model.UserDetails;

public interface UserAndLoginService {

	//UserDetails getUserByuserNameAndPassword(String userId, String password);
	
	public UserDetails getUserByUserNameAndPassword(String userName, String password) ;
	
	public Page<UserDetailsDTO> getUserss(int page, int size)  ;

    public UserDetails getUserByIds(int id);

	List<UserDetails> getUsers();

	void addUser(UserDetails userDetails);

	List<UserDetails> getAlluserForMachine();

	List<UserDetails> getAllUserForComplaintAllocation();

	void deleteUser(UserDetails userDetails);

	List<UserDetails> getITEnginner();
	
	public UserDetails getUserDetailsById(int userId);

}
