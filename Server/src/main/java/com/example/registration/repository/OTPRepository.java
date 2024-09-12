package com.example.registration.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registration.model.OTP;

public interface OTPRepository extends MongoRepository<OTP,String>{
	
	Optional<OTP> findByEmail(String email);
	

}
