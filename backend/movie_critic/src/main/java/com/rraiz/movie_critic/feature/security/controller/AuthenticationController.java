package com.rraiz.movie_critic.feature.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.security.dto.LoginResponseDTO;
import com.rraiz.movie_critic.feature.security.dto.RegistrationDTO;
import com.rraiz.movie_critic.feature.security.service.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public LoginResponseDTO registerUser(@RequestBody RegistrationDTO body, HttpServletResponse response) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail(), response);
    }

    @PostMapping("/login")
    public LoginResponseDTO LoginUser(@RequestBody RegistrationDTO body, HttpServletResponse response) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword(), body.getRememberMe(), response);
    }
    
    
}
