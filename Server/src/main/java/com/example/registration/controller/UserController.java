package com.example.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.registration.model.User;
import com.example.registration.service.OTPService;
import com.example.registration.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OTPService otpService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user)
	{
		String response=userService.registerUser(user);
		
		if(response.equals("User registered successfully"))
		{
			otpService.createAndSendOTP(user.getEmail());
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp)
	{
		boolean isValid=otpService.validateOTP(email, otp);
		if(isValid)
		{
			return ResponseEntity.ok("OTP is valid");
		}
		
		else
		{
			return ResponseEntity.status(400).body("Invalid or expired OTP");
		}
	}

}
