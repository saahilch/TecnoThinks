package com.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.dto.SignInResponce;
import com.app.service.UserService;

@Service // It Marks this class as a Spring service, indicating that it holds business logic....
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userSer;  // Injects the UserService bean to access user-related data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieves the user by email using UserService
        SignInResponce user = userSer.getUserByEmail(username);
        
        // If user is not found, throws an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        // Returns the found user to Spring Security for further authentication processing
        return user; 
    }
}
