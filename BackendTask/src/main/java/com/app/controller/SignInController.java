package com.app.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.security.JwtAuthResponce;
import com.app.config.security.JwtTokenHelper;
import com.app.dto.SignInRequest;
import com.app.dto.SignInResponce;

@RestController // Marks this class as a REST controller for handling HTTP requests
@Validated // Enables validation for request parameters
@RequestMapping("/api") // Sets the base URL for this controller's endpoints
public class SignInController {

	@Autowired
	private ModelMapper mapper; // Injects ModelMapper to map between DTOs and entities

	@Autowired
	private JwtTokenHelper jwtTokenHelper; // Injects a utility class for handling JWT token operations

	@Autowired
	private UserDetailsService userDetailsService; // Injects the service for loading user details

	@Autowired
	private AuthenticationManager authManager; // Injects Spring's authentication manager to handle authentication

	// Endpoint to authenticate the user and generate a JWT token
	@PostMapping("/signin") // Handles POST requests to the /api/signin endpoint
	public ResponseEntity<JwtAuthResponce> createToken(@RequestBody @Valid SignInRequest request) {
		// Calls the authenticate method to check user credentials
		authenticate(request.getEmail(), request.getPassword());

		// Loads user details from the provided email (username)
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

		// Generates a JWT token using the loaded user details
		String token = jwtTokenHelper.generateToken(userDetails);

		// Creates a JwtAuthResponce object to hold the token and user details
		JwtAuthResponce responce = new JwtAuthResponce();
		responce.setToken(token);

		// Maps the UserDetails (which implements SignInResponce) to SignInResponce DTO
		responce.setUser(mapper.map((SignInResponce) userDetails, SignInResponce.class));

		// Returns the response with the generated token and user details
		return new ResponseEntity<JwtAuthResponce>(responce, HttpStatus.OK);
	}

	// Method to authenticate the user credentials
	private void authenticate(String email, String password) {
		// Creates an authentication token using the provided email and password
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

		// Authenticates the user using the authentication manager
		authManager.authenticate(authenticationToken);
	}
}
