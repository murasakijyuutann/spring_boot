package com.example.myapp.service;

import com.example.myapp.dto.*;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.example.myapp.exception.UserNotFoundException;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    // 🔹 Convert User → UserDTO
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .provider(user.getProvider())
                .build();
    }

    // ✅ 1. Get all users
    public List<UserDTO> getAllUsers() {
        return userMapper.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ 2. Get user by ID
    public UserDTO getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        return toDTO(user); // ✅ cleaner
    }

    // ✅ 3. Create new user
    public void createUser(UserCreateDTO dto) {
        if (userMapper.emailExists(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setProvider("local");
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        userMapper.insertUser(user);
    }

    // ✅ 4. Update user
    public void updateUser(Long id, UserUpdateDTO dto) {
        User user = new User();
        user.setId(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        // user.setPasswordHash(dto.getPasswordHash());
        user.setProvider("local");
        userMapper.updateUser(user);
    }

    // ✅ 5. Delete user
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    public void deleteUserByName(String name) {
        userMapper.deleteUserByName(name);
    }

    // ✅ 6. Find by exact name
    public List<UserDTO> findByName(String name) {
        return userMapper.findByName(name).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ 7. Search by partial name
    public List<UserDTO> searchByName(String keyword) {
        return userMapper.searchByName(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ 8. Get all users ordered by name
    public List<UserDTO> findAllOrderedByName() {
        return userMapper.findAllOrderedByName().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAllOrderedByID() {
        return userMapper.findAllOrderedById().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ 9. Find by email
    public UserDTO findByEmail(String email) {
        return toDTO(userMapper.findByEmail(email));
    }

    // ✅ 10. Find latest user
    public UserDTO findLatestUser() {
        return toDTO(userMapper.findLatestUser());
    }

    // ✅ 11. Count all users
    public int countUsers() {
        return userMapper.countUsers();
    }

    // ✅ 12. Update only email
    public void updateEmail(Long id, String email) {
        userMapper.updateEmail(id, email);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    // 🔐 Login logic
    public User login(String email, String rawPassword) {
        User user = userMapper.findByEmail(email); // find user from DB

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new RuntimeException("❌ Invalid email or password");
        }

        return user;
    }

}
