package com.shop.shop.controller;


import com.shop.shop.entity.User;
import com.shop.shop.model.JwtResponse;
import com.shop.shop.model.LoginRequest;
import com.shop.shop.security.JwtUtils;
import com.shop.shop.service.EmailService;
import com.shop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String confirmationUrl = "http://localhost:8080/auth/confirm?token=" + token;
        String message = "Please confirm your registration by clicking the following link: " + confirmationUrl;

        emailService.sentEmail(user.getEmail(), "Registration Confirmation", message);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            if (user.getVerify()){
                String token = jwtUtils.generateJwtToken(user.getEmail());
                return ResponseEntity.ok(new JwtResponse(token));
            } else return ResponseEntity.status(404).body("Please verify your email");
        } else return ResponseEntity.status(404).body("Invalid username or password");
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token){
        boolean isValid = userService.validateVerificationToken(token);

        if (isValid) {
            return ResponseEntity.ok("User confirmed successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }
}
