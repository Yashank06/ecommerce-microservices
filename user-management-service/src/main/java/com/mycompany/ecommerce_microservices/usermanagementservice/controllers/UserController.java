package com.mycompany.ecommerce_microservices.usermanagementservice.controllers;

import com.mycompany.ecommerce_microservices.usermanagementservice.models.LoginRequest;
import com.mycompany.ecommerce_microservices.usermanagementservice.models.LoginResponse;
import com.mycompany.ecommerce_microservices.usermanagementservice.models.User;
import com.mycompany.ecommerce_microservices.usermanagementservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Get user by username")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role")
    @PutMapping("/{username}/role")
    public ResponseEntity<User> updateRole(@PathVariable String username, @RequestBody Map<String, String> roleMap) {
        User updatedUser = userService.updateRole(username, roleMap.get("role"));
        return ResponseEntity.ok(updatedUser);
    }
}