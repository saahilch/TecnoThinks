package com.app.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dao.SignUpRepository;
import com.app.dto.SignUpDto;
import com.app.entities.SignUp;

@Service 
public class SignUpService {

    @Autowired
    private SignUpRepository userRepository; // Inject the SignUpRepository to interact with the database

    @Autowired
    private EmailService emailService; // Inject EmailService to handle email sending

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password encoder for hashing passwords

    // Register a new user
    public String registerUser(SignUpDto userDTO) {
        // Validate the email format
        if (!isValidEmail(userDTO.getEmail())) {
            return "Invalid email format";
        }

        // Validate the password strength
        if (!isValidPassword(userDTO.getPassword())) {
            return "Weak password";
        }

        // Check if a user with the given email already exists
        Optional<SignUp> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            return "User with this email already exists";
        }

        // Create a new SignUp entity
        SignUp user = new SignUp();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encrypt the password
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        // Generate OTP (One-Time Password)
        String otp = generateOtp();
        user.setOtp(otp); // Set the OTP for the user

        // Save the new user to the database
        userRepository.save(user);

        // Send OTP to the user's email
        emailService.sendOtpEmail(userDTO.getEmail(), otp);

        return "User registered successfully. Please verify your email.";
    }

    // Validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // Validate password strength using regex
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999)); // Generate a random 6-digit number
    }

    // Verify the OTP for email verification
    public String verifyOtp(String email, String otp) {
        Optional<SignUp> user = userRepository.findByEmail(email);
        // Check if the user exists and the OTP matches
        if (user.isPresent() && user.get().getOtp().equals(otp)) {
            SignUp verifiedUser = user.get();
            verifiedUser.setIsVerified(true); // Set the user's verification status to true
            userRepository.save(verifiedUser); // Save the updated user
            return "Email verified successfully!";
        }
        return "Invalid OTP or Email."; // Return an error message if OTP or email is invalid
    }
}
