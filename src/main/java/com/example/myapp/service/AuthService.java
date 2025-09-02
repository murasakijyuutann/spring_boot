package com.example.myapp.service;

import com.example.myapp.dto.*;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // ✅ Register new user
    public void register(UserRegisterDTO dto) {
        if (userMapper.emailExists(dto.getEmail())) {
            throw new RuntimeException("❌ Email already registered.");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setProvider("local");

        userMapper.insertUser(user);
    }

    // ✅ Authenticate user on login
    public User login(UserLoginDTO dto) {
        User user = userMapper.findByEmail(dto.getEmail());

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("❌ Invalid email or password.");
        }

        return user;
    }
}
