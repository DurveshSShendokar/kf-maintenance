package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.kfMaintenancce.model.OtpVerification;

public interface OtpVerificationRepo extends JpaRepository<OtpVerification, Integer> {
	
	@Query("SELECT o FROM OtpVerification o " +
		       "WHERE o.email = :email " +
		       "AND o.otp = :otp " +
		       "AND o.verified = false")
		Optional<OtpVerification> findValidOtp(@Param("email") String email, 
		                                       @Param("otp") String otp);


}
