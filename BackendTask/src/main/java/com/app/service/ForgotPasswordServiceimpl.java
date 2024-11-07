package com.app.service;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.dao.ForgotPasswordDao;
import com.app.entities.Profile;

public abstract class ForgotPasswordServiceimpl implements ForgotPasswordDao {

	@Autowired
	private ForgotPasswordDao forgotpasswordDao;

	@Override
	public String forgotPassword(String email, String newPassword) throws RelationNotFoundException {
		Profile admin = forgotpasswordDao.findAdminByEmail(email);
		if (admin != null) {
			admin.setPassword(newPassword);
			return "Password changed successfully!";
		} else {
			throw new RelationNotFoundException("Failed to change password.");
		}
	}
}
