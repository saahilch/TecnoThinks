package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // Marks this class as a service component in Spring, making it eligible for dependency injection
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender; // Injects the JavaMailSender to handle sending emails

    // Method to send OTP email to the provided recipient
    public void sendOtpEmail(String toEmail, String otp) {
        // Creates a new email message
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Sets the recipient's email address
        message.setTo(toEmail);
        
        // Sets the subject of the email
        message.setSubject("OTP Verification");
        
        // Sets the body of the email with the provided OTP
        message.setText("Your OTP for email verification is: " + otp);
        
        // Sends the email using the injected JavaMailSender
        mailSender.send(message);
    }
}
