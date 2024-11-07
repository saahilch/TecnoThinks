package com.app.dao;

import javax.management.relation.RelationNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Profile;

public interface ForgotPasswordDao extends JpaRepository<Profile, Long> {
	public Profile findAdminByEmail(String email);

	String forgotPassword(String email, String newPassword) throws RelationNotFoundException;

}
