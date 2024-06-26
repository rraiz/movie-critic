package com.rraiz.movie_critic.feature.security.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.security.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin("*")
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
    public Map<String, Object> checkSession() {
        return userService.checkSession();
    }
    
    
}
