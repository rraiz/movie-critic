package com.rraiz.movie_critic.configuration;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtCookieFilter is a custom filter that extracts JWT tokens from cookies
 * and injects them into the Authorization header for further processing by
 * Spring Security filters.
 */
public class JwtCookieFilter extends OncePerRequestFilter {

    /** The AntPathMatcher used to match URL patterns */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * Filters incoming HTTP requests. If the request matches any public URL patterns,
     * it is forwarded without JWT validation. Otherwise, it attempts to extract the JWT
     * from cookies and inject it into the Authorization header.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Skip JWT validation for public URLs
        if (Arrays.stream(SecurityConfiguration.PUBLIC_URLS).anyMatch(url -> pathMatcher.match(url, requestURI))) {
            filterChain.doFilter(request, response);  // Forward the request
            return;
        }

        String token = getJwtFromCookie(request); // Extract the JWT token from cookies
        if (token != null) {
            /**
             * The wrapper wraps the request to inject the Authorization header. 
             * The original HttpServletRequest does not allow modifying headers directly, so we need to wrap it.
             */
            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                /**
                 * Returns the value of the specified request header. If the header is
                 * "Authorization", it returns the JWT token prefixed with "Bearer ".
                 *
                 * This method is overridden to ensure that the SPring Security filters,
                 * can access the JWT token as if it were provided in the Authorization header.
                 *
                 * @param name the name of the request header
                 * @return the value of the request header, or the JWT token if the header is "Authorization"
                 */
                @Override
                public String getHeader(String name) {
                    if ("Authorization".equals(name)) { 
                        return "Bearer " + token; 
                    }
                    return super.getHeader(name); 
                }
            };
            filterChain.doFilter(wrappedRequest, response); // Forward the wrapped request
            return;
        }
        filterChain.doFilter(request, response); // Forward the original request
    }

    /**
     * Extracts the JWT token from the cookies in the request.
     *
     * @param request the HTTP request
     * @return the JWT token if found, otherwise null
     */
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
