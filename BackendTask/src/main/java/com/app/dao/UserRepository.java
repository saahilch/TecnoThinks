package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.SignInResponce;

public interface UserRepository extends JpaRepository<SignInResponce, Long> {
	SignInResponce findbyusername(String username);

	

}
