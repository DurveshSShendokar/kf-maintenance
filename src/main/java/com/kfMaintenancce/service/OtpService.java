package com.kfMaintenancce.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.kfMaintenancce.model.CompanyProfile;
import com.kfMaintenancce.model.OtpVerification;
import com.kfMaintenancce.repo.CompanyProfileRepo;
import com.kfMaintenancce.repo.OtpVerificationRepo;

@Service
public class OtpService {

    @Autowired
     OtpVerificationRepo otpRepo;
    
    @Autowired
    CompanyProfileRepo companyRepo;

    @Autowired
     JavaMailSender mailSender;

    public void generateAndSendOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(otpVerification);
        
        CompanyProfile company = companyRepo.getCompanyProfile();

        String companyName = company != null ? company.getCompanyName() 
                                             : "Your Company Name";


        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(" Password Reset OTP");

            // HTML design for the email
            String htmlContent = "<div style='font-family: Arial, sans-serif; color: #333; padding: 20px;'>"
            		+ "<h2 style='color: #2e6c80;'>" + companyName + "</h2>"

                    + "<p>Dear User,</p>"
                    + "<p>You requested to reset your password. Please use the following OTP:</p>"
                    + "<h1 style='background: #f4f4f4; color: #2e6c80; display: inline-block; "
                    + "padding: 10px 20px; border-radius: 5px; letter-spacing: 3px;'>"
                    + otp + "</h1>"
                    + "<p>This OTP is valid for <b>5 minutes</b>. Please do not share it with anyone.</p>"
                    + "<br>"
                    + "<p style='font-size: 12px; color: #888;'>If you didn’t request this, please ignore this email.</p>"
                    + "<hr style='margin-top: 20px;'>"
                    + "<p style='font-size: 12px; color: #888;'>© 2025 " + companyName + "</p>"

                    + "</div>";

            helper.setText(htmlContent, true); // 'true' enables HTML

            helper.setFrom(new InternetAddress("amspa2010@gmail.com", companyName));

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public boolean verifyOtp(String email, String otp) {
        Optional<OtpVerification> otpOpt = otpRepo.findValidOtp(email, otp);

        if (otpOpt.isPresent()) {
            OtpVerification record = otpOpt.get();
            if (!record.isExpired()) {
                record.setVerified(true);
                otpRepo.save(record);
                return true;
            }
        }
        return false;
    }
}
