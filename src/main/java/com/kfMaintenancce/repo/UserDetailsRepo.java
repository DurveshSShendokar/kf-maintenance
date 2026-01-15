package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.dto.UserDetailsDTO;
import com.kfMaintenancce.model.UserDetails;


public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> , UserDetailsRepoCustom{
	@Query("From UserDetails u where u.id=?1 and u.active=1")
	Optional<UserDetails> getUserById(String id);
	
	@Query("From UserDetails u where u.userName=?1 and u.active=1")
	Optional<UserDetails> getUserByUserName(String userId);

    @Query("FROM UserDetails u WHERE LOWER(u.userName) = LOWER(:userName) AND u.active = 1")
    Optional<UserDetails> getUserByUserNames(@Param("userName") String userName);
 
    @Query("SELECT COALESCE(MAX(u.empId), 0) FROM UserDetails u")
    Integer getMaxEmpId();

	
	@Query("FROM UserDetails u WHERE u.userName = ?1 AND u.active = 1")
	Optional<UserDetails> findByUserName(String userName);

	@Query("SELECT u FROM UserDetails u WHERE TRIM(LOWER(u.emailId)) = TRIM(LOWER(:emailId))")
	Optional<UserDetails> findByEmailId(@Param("emailId") String emailId);

	 @Query("SELECT new com.kfMaintenancce.dto.UserDetailsDTO(u.firstName, u.lastName, u.emailId, u.userName, u.contactNo, r.roleName, d.departmentName) " +
	           "FROM UserDetails u " +
	           "LEFT JOIN u.role r " +
	           "LEFT JOIN u.department d")
	    Page<UserDetailsDTO> findAllUserDTO(Pageable pageable);
	
	@Query("FROM UserDetails u " +
		       "WHERE (u.role.roleName = 'Engineer' OR u.role.roleName = 'Executive') " +
		       "AND u.department.departmentName = 'Maintenance' " +
		       "AND u.active = 1")
		List<UserDetails> getAlluserForMachine();

	@Query("From UserDetails u where (u.role.roleName='Engineer') and department.departmentName='IT' and u.active=1")
	List<UserDetails> getAllUserForComplaintAllocation();
	@Query("From UserDetails u where (u.role.roleName='Engineer') and department.departmentName='IT' and u.active=1")
	List<UserDetails> getIIEngineers();
	@Query("select MAX(id) from UserDetails")
	String getMaxId();
	
	@Query("SELECT mo.user FROM MachineOwner mo WHERE mo.machine.machine_id = :machineId")
	List<UserDetails> findMachineOwnersByMachineId(@Param("machineId") int machineId);

	
	 @Query("SELECT COUNT(u) FROM UserDetails u WHERE FUNCTION('DATE_FORMAT', u.addedDateTime, '%Y%m') = :yearMonth")
	    long countByCreatedAtStartsWith(@Param("yearMonth") String yearMonth);

	
@Query("from UserDetails u where userId=?1")
	UserDetails getUserByIds(int userId);
}
