package com.kfMaintenancce.controller;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.kfMaintenancce.service.OtpService;
import com.kfMaintenancce.dto.MobileLogin;
import com.kfMaintenancce.dto.MobileLoginRequest;
import com.kfMaintenancce.dto.MobileLoginResponce;
import com.kfMaintenancce.dto.ResponceObj;
import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.UserDetailsRepo;
import com.kfMaintenancce.service.UserAndLoginService;



@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {	

	@Autowired
	OtpService otpService;

	@Autowired
	UserAndLoginService loginService;
	@Autowired
	UserDetailsRepo userDetailsRepo;
	
	
	@CrossOrigin(origins = "http://localhost:8083")
	@PostMapping("/authenticate")
    public ResponseEntity<ResponceObj> serviceLogin(@RequestHeader("id") String id,
            @RequestHeader("password") String password) {
        ResponceObj obj = new ResponceObj();
        System.out.println("id pass===" + id + password);

        Optional<UserDetails> optional = userDetailsRepo.getUserByUserName(id);

        if (optional.isPresent()) {
            UserDetails details = optional.get();
            if (details.getPassword().equals(password)) {
                obj.setCode(200);
                obj.setMessage("Login Successfully");
                obj.setData(details);
                return ResponseEntity.ok(obj);
            } else {
                obj.setCode(401); // Unauthorized
                obj.setMessage("Invalid Password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obj);
            }
        } else {
            obj.setCode(404); // Not Found
            obj.setMessage("Invalid UserId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj);
        }
    }
	
	
	@CrossOrigin(origins = "http://localhost:8083")
	@PostMapping("/authenticate2")
	public ResponseEntity<ResponceObj> serviceLogin2(
	        @RequestParam("id") String id,
	        @RequestParam("password") String password) {

	    ResponceObj obj = new ResponceObj();
	    System.out.println("id pass === " + id + " " + password);

	    Optional<UserDetails> optional = userDetailsRepo.getUserByUserName(id);

	    if (optional.isPresent()) {
	        UserDetails details = optional.get();

	        if (details.getPassword().equals(password)) {
	            obj.setCode(200);
	            obj.setMessage("Login Successfully");
	            obj.setData(details);
	            return ResponseEntity.ok(obj);
	        } else {
	            obj.setCode(401);
	            obj.setMessage("Invalid Password");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obj);
	        }
	    } else {
	        obj.setCode(404);
	        obj.setMessage("Invalid UserId");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj);
	    }
	}


	
	@PostMapping("/mobileLogin")
	public ResponseEntity<MobileLoginResponce> mobileLogin(@RequestBody MobileLogin mobileLogin) {
	    System.out.println("Login request === " + mobileLogin.getUserId() + " " + mobileLogin.getPassword());

	    UserDetails returnbody = loginService.getUserByUserNameAndPassword(
	            mobileLogin.getUserId(), mobileLogin.getPassword());

	    MobileLoginResponce loginResponce = new MobileLoginResponce();

	    if (returnbody != null) {
	        if (returnbody.getDepartment().getDepartmentName().equalsIgnoreCase(mobileLogin.getAppName())) {
	            loginResponce.setStatusCode(200);
	            loginResponce.setMessage("Login Successfully");
	            loginResponce.setUser(returnbody);
	            return new ResponseEntity<>(loginResponce, HttpStatus.OK);
	        } else {
	            loginResponce.setStatusCode(403);
	            loginResponce.setMessage("You're Not Allowed To Login " + mobileLogin.getAppName() + " Application");
	            return new ResponseEntity<>(loginResponce, HttpStatus.FORBIDDEN);
	        }
	    } else {
	        loginResponce.setStatusCode(401);
	        loginResponce.setMessage("Login Failed");
	        return new ResponseEntity<>(loginResponce, HttpStatus.UNAUTHORIZED);
	    }
	}






    @PostMapping("/change_password")
    public ResponseEntity<ResponceObj> changePassword(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String newPassword = request.get("newPassword");

        ResponceObj obj = new ResponceObj();

        if (userName == null || userName.trim().isEmpty()) {
            obj.setCode(400);
            obj.setMessage("userName is required.");
            obj.setData(null);
            return ResponseEntity.badRequest().body(obj);
        }

        Optional<UserDetails> optional = userDetailsRepo.getUserByUserNames(userName);
        if (optional.isPresent()) {
            UserDetails user = optional.get();

            if (newPassword == null || newPassword.trim().isEmpty()) {

                obj.setCode(100);
                obj.setMessage("userName valid. Please provide new password.");
                obj.setData(null);
                return ResponseEntity.status(HttpStatus.OK).body(obj);
            } else {

                user.setPassword(newPassword);
                userDetailsRepo.save(user);
                obj.setCode(200);
                obj.setMessage("Password updated successfully.");
                obj.setData(null);
                return ResponseEntity.ok(obj);
            }
        } else {
            obj.setCode(404);
            obj.setMessage("User not found.");
            obj.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj);
        }
    }
    
 

	
	@PostMapping("/forgot_password/request_otp")
	public ResponseEntity<ResponceObj> requestOtp(@RequestBody Map<String, String> request) {
	    String userName = request.get("userName") != null ? request.get("userName").trim() : null;

	    ResponceObj obj = new ResponceObj();

	    // Check if userName is missing or blank
	    if (userName == null || userName.isEmpty()) {
	        obj.setCode(400);
	        obj.setMessage("userName is required.");
	        return ResponseEntity.badRequest().body(obj);
	    }

	    // Check if user exists in DB
	    Optional<UserDetails> optional = userDetailsRepo.getUserByUserNames(userName);

	    if (optional.isPresent()) {
	        String email = optional.get().getEmailId();

	        // If email is missing in DB
	        if (email == null || email.trim().isEmpty()) {
	            obj.setCode(400);
	            obj.setMessage("No email is registered for this user.");
	            return ResponseEntity.badRequest().body(obj);
	        }

	        // Generate and send OTP
	        otpService.generateAndSendOtp(email);
	        obj.setCode(200);
	        obj.setMessage("OTP has been sent to your registered email.");
	        return ResponseEntity.ok(obj);
	    } else {
	        obj.setCode(404);
	        obj.setMessage("Invalid userName. Please check Username and try again.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj);
	    }
	}


	
	@PostMapping("/forgot_password/verify_otp")
	public ResponseEntity<ResponceObj> verifyOtpAndResetPassword(@RequestBody Map<String, String> request) {
	    String userName = request.get("userName");
	    String otp = request.get("otp");
	    String newPassword = request.get("newPassword");

	    ResponceObj obj = new ResponceObj();

	    Optional<UserDetails> optional = userDetailsRepo.getUserByUserNames(userName);
	    if (optional.isPresent()) {
	    	UserDetails user = optional.get();
	        String email = user.getEmailId();

	        if (otpService.verifyOtp(email, otp)) {
	            user.setPassword(newPassword);
	            userDetailsRepo.save(user);

	            obj.setCode(200);
	            obj.setMessage("Password updated successfully.");
	            return ResponseEntity.ok(obj);
	        } else {
	            obj.setCode(400);
	            obj.setMessage("The OTP you entered is incorrect or has expired. Please request a new OTP.");
	            return ResponseEntity.badRequest().body(obj);
	        }
	    } else {
	        obj.setCode(404);
	        obj.setMessage("The userName you entered does not exist. Please check and try again.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj);
	    }
	}

	
	
	}
