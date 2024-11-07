package com.app.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component // Marks this class as a Spring component to be auto-detected and managed by the Spring container
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method is triggered when an unauthenticated user tries to access a secured REST resource
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // Sets the HTTP response status to 401 (Unauthorized) when authentication fails
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied!!");

        // Writes a detailed error message to the response body
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied !! " + authException.getMessage());
    }

}
