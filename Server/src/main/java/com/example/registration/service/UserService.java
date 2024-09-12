package com.example.registration.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.registration.model.OTP;
import com.example.registration.model.User;
import com.example.registration.repository.OTPRepository;
import com.example.registration.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private OTPRepository otpRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender emailSender;
	
	
	public String registerUser(User user)
	{
		if(userRepository.findByEmail(user.getEmail()).isPresent())
		{
			return "Email already in use";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		return "User Registered Successfully";
	}
	
	
}
