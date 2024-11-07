package com.app.config.security;


import com.app.dto.SignInResponce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponce {

	
	private String token;

	
	private SignInResponce user;


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public SignInResponce getUser() {
		return user;
	}


	public void setUser(SignInResponce user) {
		this.user = user;
	}
	
}
