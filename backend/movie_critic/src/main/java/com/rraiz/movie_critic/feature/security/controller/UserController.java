package com.rraiz.movie_critic.feature.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.security.dto.LoginResponseDTO;
import com.rraiz.movie_critic.feature.security.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloUserController() {
        return "User access level";
    }

    @GetMapping("/check-session")
    public LoginResponseDTO checkSession(HttpServletResponse response) {
        return userService.checkSession(response);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        return userService.logout(response);
    }


}
