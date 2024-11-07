package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.SignUp;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
	Optional<SignUp> findByEmail(String email);

}
