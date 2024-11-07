package com.app.dto;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;


public class SignInRequest {
	
	@Email(message = "Please enter valid email.")
	private String email;
	@JsonFormat(pattern = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$" )
	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
