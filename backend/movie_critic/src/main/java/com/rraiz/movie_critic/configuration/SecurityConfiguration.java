package com.rraiz.movie_critic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.rraiz.movie_critic.feature.security.service.TokenService;

/**
 * SecurityConfiguration class configures the security settings for the
 * application,
 * including JWT-based authentication and authorization.
 */
@Configuration
public class SecurityConfiguration {

    /** List of public URLs that do not require authentication. */
    public static final String[] PUBLIC_URLS = { "/auth/**", "/api/**" };

    private final TokenService tokenService;

    /**
     * Constructor to initialize RSA key properties.
     *
     * @param keys         RSA key properties for JWT encoding and decoding
     * @param tokenService the token service for handling JWT tokens
     */
    public SecurityConfiguration(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Uses BCryptPasswordEncoder for hashing passwords.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationManager with a DaoAuthenticationProvider.
     *
     * @param userService the user details service for loading user-specific data
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService userService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    /**
     * Configures HTTP security settings including URL authorization and JWT
     * handling.
     * This method sets up the security filter chain, which includes configuring
     * CSRF protection,
     * URL authorization rules, JWT handling, and session management.
     *
     * @param http the HttpSecurity to modify
     * @return SecurityFilterChain configured security filter chain
     * @throws Exception if an error occurs while configuring security settings
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection since this is a stateless REST API
                .csrf(csrf -> csrf.disable())

                // Configure URL authorization rules
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PUBLIC_URLS).permitAll(); // Permit all requests to public URLs
                    auth.requestMatchers("/admin/**").hasRole("ADMIN"); // Only users with the role "ADMIN" can access
                                                                        // URLs under /admin/**
                    auth.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN"); // Users with either the "USER" or
                                                                                  // "ADMIN" roles can access URLs under
                                                                                  // /user/**
                    auth.anyRequest().authenticated(); // All other requests require authentication
                })

                // Configure the application to use OAuth2 Resource Server for JWT handling.
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))

                // Configure session management to be stateless for JWT-based authentication
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add the custom JwtCookieFilter before the BearerTokenAuthenticationFilter.
        // This filter extracts JWT tokens from cookies and injects them into the
        // Authorization header.
        http.addFilterBefore(new JwtCookieFilter(tokenService), BearerTokenAuthenticationFilter.class);

        // Return the configured SecurityFilterChain
        return http.build();
    }

    /**
     * Configures the JwtAuthenticationConverter to convert JWT claims to
     * GrantedAuthorities.
     * This is used to extract roles and permissions from the JWT token and convert
     * them into
     * Spring Security's GrantedAuthority objects, which can then be used for
     * authorization
     * decisions within the application.
     *
     * @return JwtAuthenticationConverter configured JWT authentication converter
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Set the claim name that contains the roles. In this case, it's "roles".
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        // Set the prefix to be added to each role. In this case, it's "ROLE_".
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        // Set the JwtGrantedAuthoritiesConverter on the JwtAuthenticationConverter
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

}
