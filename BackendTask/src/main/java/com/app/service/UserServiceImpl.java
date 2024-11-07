package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UserRepository;
import com.app.dto.SignInResponce;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public SignInResponce authoriseUser(String email, String password) {
        // Implement your authorization logic
        SignInResponce user = userRepository.findbyusername(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return user if authorized
        }
        return null; // Or throw an exception if unauthorized
    }

    @Override
    public SignInResponce getUserByEmail(String email) {
        return userRepository.findbyusername(email);
    }
}
