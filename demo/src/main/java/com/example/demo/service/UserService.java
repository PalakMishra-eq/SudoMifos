package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(String name, String email, String password) {
        // Check if email is already in use
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Hash the password before saving
        String hashedPassword = bCryptPasswordEncoder.encode(password);

        // Create and save new user
        User newUser = new User(name, email, hashedPassword);
        return userRepository.save(newUser);
    }
}
