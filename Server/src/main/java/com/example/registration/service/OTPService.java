package com.example.registration.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.registration.model.OTP;
import com.example.registration.repository.OTPRepository;

@Service
public class OTPService {
	
	
	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	private JavaMailSender emailSender;
	
	private static final int OTP_LENGTH=6;
	
	public String generateOTP()
	{
		SecureRandom random=new SecureRandom();
		int otp=100000+random.nextInt(900000);
		return String.valueOf(otp);
		
	}
	
	public void sendOTPEmail(String email, String otp)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your OTP Code");
		message.setText("Your OTP code is "+otp+ ". It is valid for 5 minutes");
		emailSender.send(message);
	}
	
	public String createAndSendOTP(String email)
	{
		String otp=generateOTP();
		OTP otpEntry=new OTP();
		otpEntry.setEmail(email);
		otpEntry.setOtp(otp);
		otpEntry.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		
		otpRepository.save(otpEntry);
		sendOTPEmail(email,otp);
		return "OTP sent to email.";
	}
	
	public boolean validateOTP(String email, String otp)
	{
		Optional<OTP> otpEntry=otpRepository.findByEmail(email);
		
		if(otpEntry.isPresent())
		{
			OTP entry=otpEntry.get();
			
			if(entry.getOtp().equals(otp) && LocalDateTime.now().isBefore(entry.getExpiryTime()))
			{
				otpRepository.delete(entry);
				return true;
			}
		}
		
		return false;
		
	}
}
