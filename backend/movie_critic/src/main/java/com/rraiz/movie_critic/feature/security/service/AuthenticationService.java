package com.rraiz.movie_critic.feature.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO registerUser(String username, String password, String email, HttpServletResponse response) {
        if (userRepository.findByUsername(username).isPresent()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return new LoginResponseDTO(null, "Username already exists.", false);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);

            return new LoginResponseDTO(null, "Email already exists.", false);
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        ApplicationUser newUser = new ApplicationUser(0, username, encodedPassword, email, authorities);
        userRepository.save(newUser);

        return new LoginResponseDTO(newUser, "Registration successful", true);
    }

    public LoginResponseDTO loginUser(String username, String password, boolean rememberMe,
            HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);
            int age = rememberMe ? 2592000 : -1;
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(age)
                    .build();

            ApplicationUser user = userRepository.findByUsername(username).get();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new LoginResponseDTO(user, "Login successful", true);
        } catch (AuthenticationException e) {
            // Clear the cookie on authentication failure
            ResponseCookie clearCookie = ResponseCookie.from("accessToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0) // Delete the cookie
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, clearCookie.toString());

            // Set the response status to UNAUTHORIZED and return an error message
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new LoginResponseDTO(null, "Invalid username or password", false);
        }
    }

}
