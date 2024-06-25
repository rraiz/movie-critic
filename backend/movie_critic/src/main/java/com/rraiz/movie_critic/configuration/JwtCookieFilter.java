package com.rraiz.movie_critic.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCookieFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_URLS = Arrays.asList("/auth", "/login", "/register");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (PUBLIC_URLS.stream().noneMatch(requestURI::startsWith)) {
            String token = getJwtFromCookie(request);
            if (token != null) {
                HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getHeader(String name) {
                        if ("Authorization".equals(name)) {
                            return "Bearer " + token;
                        }
                        return super.getHeader(name);
                    }
                };
                filterChain.doFilter(wrappedRequest, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
