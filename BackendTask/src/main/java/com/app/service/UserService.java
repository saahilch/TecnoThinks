package com.app.service;

import org.springframework.stereotype.Service;

import com.app.dto.SignInResponce;

@Service
public interface UserService {
	public SignInResponce authoriseUser(String email, String password);

	public SignInResponce getUserByEmail(String email);
}
