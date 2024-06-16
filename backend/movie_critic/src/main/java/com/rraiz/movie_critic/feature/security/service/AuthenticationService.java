package com.rraiz.movie_critic.feature.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rraiz.movie_critic.feature.security.dto.LoginResponseDTO;
import com.rraiz.movie_critic.feature.security.model.ApplicationUser;
import com.rraiz.movie_critic.feature.security.model.Role;
import com.rraiz.movie_critic.feature.security.repository.RoleRepository;
import com.rraiz.movie_critic.feature.security.repository.UserRepository;


@Service
@Transactional
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ApplicationUser registerUser(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            return null;
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        ApplicationUser newUser = new ApplicationUser(0, username, encodedPassword, authorities);
        return userRepository.save(newUser);
    }

    public LoginResponseDTO loginUser(String username, String password) {

        System.out.println("Login user: " + username + " " + password);

        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        }
        catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
    
}
