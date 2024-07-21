package com.rraiz.movie_critic.feature.security.service;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.security.dto.LoginResponseDTO;
import com.rraiz.movie_critic.feature.security.model.ApplicationUser;
import com.rraiz.movie_critic.feature.security.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, HttpServletRequest request, TokenService tokenService) {
        this.userRepository = userRepository;
        this.request = request;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }

    public LoginResponseDTO checkSession(HttpServletResponse response) {
        try {

            String username = getUsername();
            ApplicationUser user = (ApplicationUser) loadUserByUsername(username);

            return new LoginResponseDTO(user, "Session is valid", true);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new LoginResponseDTO(null, e.getMessage(), false);
        }
    }

    public String logout(HttpServletResponse response) {
            ResponseCookie clearCookie = ResponseCookie.from("accessToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0) // Delete the cookie
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, clearCookie.toString());
            return "Logout successful";
    }

    public String getUsername() {
        String jwt = tokenService.getJwtFromCookie(request);
        Map<String, Object> claims = tokenService.decodeJwt(jwt);

        if (claims == null || !claims.containsKey("sub")) {
            throw new UsernameNotFoundException("Invalid JWT token");
        }

        return (String) claims.get("sub");
    }
}
