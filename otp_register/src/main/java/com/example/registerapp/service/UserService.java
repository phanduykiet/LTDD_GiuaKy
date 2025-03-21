package com.example.registerapp.service;

import com.example.registerapp.dto.LoginDto;
import com.example.registerapp.dto.RegisterDto;
import com.example.registerapp.entity.User;
import com.example.registerapp.repository.UserRepository;
import com.example.registerapp.util.EmailUtil;
import com.example.registerapp.util.OtpUtil;
import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<Map<String, String>> register(RegisterDto registerDto) {
	    String otp = otpUtil.generateOtp();
	    Map<String, String> response = new HashMap<>();
	    
	    try {
	        emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
	    } catch (MessagingException e) {
	        response.put("message", "Unable to send OTP, please try again");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }

	    User user = new User();
	    user.setName(registerDto.getName());
	    user.setEmail(registerDto.getEmail());
	    user.setPassword(registerDto.getPassword());
	    user.setOtp(otp);
	    user.setOtpGeneratedTime(LocalDateTime.now());
	    
	    userRepository.save(user);

	    response.put("message", "User registration successful");
	    return ResponseEntity.ok(response);
	}
	public ResponseEntity<Map<String, String>> verifyAccount(String email, String otp) {
	    Map<String, String> response = new HashMap<>();
	    
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

	    if (user.getOtp() == null || !user.getOtp().equals(otp)) {
	        response.put("message", "Invalid OTP. Please regenerate OTP and try again.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

	    if (Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() >= (1 * 60)) {
	        response.put("message", "OTP expired. Please regenerate OTP.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

	    user.setActive(true);
	    userRepository.save(user);
	    
	    response.put("message", "OTP verified successfully. You can now log in.");
	    return ResponseEntity.ok(response);
	}


	public ResponseEntity<Map<String, String>> regenerateOtp(String email) {
	    Map<String, String> response = new HashMap<>();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

	    String otp = otpUtil.generateOtp();
	    try {
	        emailUtil.sendOtpEmail(email, otp);
	    } catch (MessagingException e) {
	        response.put("message", "Unable to send OTP. Please try again.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }

	    user.setOtp(otp);
	    user.setOtpGeneratedTime(LocalDateTime.now());
	    userRepository.save(user);

	    response.put("message", "OTP sent successfully. Please verify your account within 1 minute.");
	    return ResponseEntity.ok(response);
	}


	public User login(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
		if (!loginDto.getPassword().equals(user.getPassword())) {
			return null;
		} else if (!user.isActive()) {
			return null;
		}
		return user;
	}

	public String resetPassword(String newPassword, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
		user.setPassword(newPassword);
		userRepository.save(user);
		return "Create new password successful";
	}
}
