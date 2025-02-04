package com.mycompany.ecommerce_microservices.usermanagementservice.services;

import com.mycompany.ecommerce_microservices.usermanagementservice.config.JwtUtil;
import com.mycompany.ecommerce_microservices.usermanagementservice.exceptions.DuplicateEmailException;
import com.mycompany.ecommerce_microservices.usermanagementservice.exceptions.InvalidCredentialsException;
import com.mycompany.ecommerce_microservices.usermanagementservice.exceptions.UserNotFoundException;
import com.mycompany.ecommerce_microservices.usermanagementservice.models.LoginRequest;
import com.mycompany.ecommerce_microservices.usermanagementservice.models.LoginResponse;
import com.mycompany.ecommerce_microservices.usermanagementservice.models.User;
import com.mycompany.ecommerce_microservices.usermanagementservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(User user) {
        logger.info("Registering user: {}", user.getUsername());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.error("Duplicate email: {}", user.getEmail());
            throw new DuplicateEmailException("Email already exists: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("User registered successfully: {}", user.getUsername());
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username)));
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest.getUsername());
        UserDetails userDetails = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", loginRequest.getUsername());
                    return new UserNotFoundException("User not found with username: " + loginRequest.getUsername());
                });

        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            logger.error("Invalid credentials for user: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(userDetails);
        logger.info("User logged in successfully: {}", userDetails.getUsername());
        return new LoginResponse(token);
    }
}