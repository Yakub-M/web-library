package com.yakub.weblibrary.controller;

import com.yakub.weblibrary.model.User;
import com.yakub.weblibrary.security.JWTUtil;
import com.yakub.weblibrary.service.UserService;
import com.yakub.weblibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JWTUtil jwtUtil,
                          UserService userService,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check if user already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }
        // If no roles provided, default to ROLE_USER.
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER"));
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("/cleanup/{username}")
    @PreAuthorize("hasRole('ADMIN')") // Ensure only admins can delete users
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok("User " + username + " deleted successfully.");
    }

    // Request and Response DTOs
    static class AuthRequest {
        private String username;
        private String password;
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class AuthResponse {
        private String token;
        public AuthResponse(String token) {
            this.token = token;
        }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}

