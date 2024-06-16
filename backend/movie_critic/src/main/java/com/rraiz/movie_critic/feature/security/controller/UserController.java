package com.rraiz.movie_critic.feature.security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    
    @GetMapping("/")
    public String helloUserController() {
        return "User access level";
    }
    
}
