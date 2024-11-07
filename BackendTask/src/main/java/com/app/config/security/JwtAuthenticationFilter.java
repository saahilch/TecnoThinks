package com.app.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component // Marks this as a Spring-managed component (bean)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsService userDetailsService; // Injects the UserDetailsService to load user info
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper; // Injects a helper class for JWT-related operations

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Get the token from the Authorization header
        String requestHeader = request.getHeader("Authorization");
        
        // 2. Parse the token if it starts with "Bearer"
        String username = null;
        String token = null;
        
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // Extract the JWT token by removing the "Bearer " prefix
            token = requestHeader.substring(7);
            try {
                // 3. Get the username from the JWT token
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given JWT token is expired!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Token has been tampered with! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Invalid Header Value!");
        }

        // 4. Once we have the token, validate it
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details using the username from the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Validate the token with the user details
            Boolean validateToken = jwtTokenHelper.validateToken(token, userDetails);
            
            if (validateToken) {
                // Token is valid, set up the security context for authenticated access
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                // Set additional authentication details (like the current request)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 5. Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
