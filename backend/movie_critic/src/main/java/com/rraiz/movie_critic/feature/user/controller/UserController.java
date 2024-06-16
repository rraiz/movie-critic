package com.rraiz.movie_critic.feature.user.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    
    @GetMapping("/hello")
    public String helloUserController() {
        return "Hello from UserController";
    }
    
}
