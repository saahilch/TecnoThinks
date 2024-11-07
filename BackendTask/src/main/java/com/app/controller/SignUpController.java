package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.SignUpDto;
import com.app.service.SignUpService; // Import the SignUpService to handle business logic

@RestController // Marks the class as a REST controller, handling HTTP requests and returning responses as JSON
@RequestMapping("/api/users") // Base URL for the controller, mapping to /api/users for all endpoints in this class
public class SignUpController {

	@Autowired // Injects the SignUpService to handle user registration and OTP verification
	private SignUpService signUpService;

	// Endpoint for user registration
	@PostMapping("/register") // Handles POST requests to /api/users/register
	public String registerUser(@RequestBody SignUpDto userDTO) {
		// Calls the service to register the user and returns the result as a string
		return signUpService.registerUser(userDTO);
	}

	// Endpoint to verify OTP
	@PostMapping("/verify-otp") // Handles POST requests to /api/users/verify-otp
	public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
		// Calls the service to verify the OTP for the given email and returns the result as a string
		return signUpService.verifyOtp(email, otp);
	}
}
