package com.example.registration.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registration.model.User;

public interface UserRepository extends MongoRepository<User,String>{
	Optional<User> findByEmail(String email);

}
