package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.config.security.CustomUserDetailService;

@Configuration // Marks this class as a source of configuration settings for the application
@EnableWebSecurity // Enables Spring Security for the application
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService userDetailsService;

    // Constructor-based dependency injection of CustomUserDetailService
    public SecurityConfig(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Configures authentication with CustomUserDetailService and specifies the password encoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Configures HTTP security, specifying that all requests require authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated() // Requires authentication for any request
            .and()
            .formLogin().permitAll() // Enables form-based login and allows all users to access the login page
            .and()
            .logout().permitAll(); // Allows all users to access the logout functionality
    }

    // Creates a bean for BCryptPasswordEncoder to hash passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
