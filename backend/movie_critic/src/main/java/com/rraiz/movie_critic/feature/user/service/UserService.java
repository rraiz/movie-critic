package com.rraiz.movie_critic.feature.user.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.user.model.ApplicationUser;
import com.rraiz.movie_critic.feature.user.model.Role;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("User: " + username);
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(1, "ROLE_USER"));

        return new ApplicationUser(1, username, encoder.encode("password"), roles);
        
    }
    
}
