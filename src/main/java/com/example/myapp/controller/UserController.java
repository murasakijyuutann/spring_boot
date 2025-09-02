package com.example.myapp.controller;

import com.example.myapp.dto.*;
import com.example.myapp.service.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Basic CRUD

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO dto) {
        userService.createUser(dto);
        return ResponseEntity.ok("✅ User created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.ok("✏️ User updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("❌ User deleted successfully.");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name) {
        userService.deleteUserByName(name);
        return ResponseEntity.ok("❌ User deleted successfully.");
    }

    // ✅ Custom Queries from UserMapper

    @GetMapping("/search/by-name")
    public List<UserDTO> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @GetMapping("/search/by-keyword")
    public List<UserDTO> searchByName(@RequestParam String keyword) {
        return userService.searchByName(keyword);
    }

    @GetMapping("/ordered-by-name")
    public List<UserDTO> getAllOrderedByName() {
        return userService.findAllOrderedByName();
    }

    @GetMapping("/ordered-by-id")
    public List<UserDTO> getAllOrderedById() {
        return userService.findAllOrderedByID();
    }

    @GetMapping("/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/latest")
    public UserDTO findLatestUser() {
        return userService.findLatestUser();
    }

    @GetMapping("/count")
    public int countUsers() {
        return userService.countUsers();
    }

    @PutMapping("/email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable Long id, @RequestParam String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.ok("📧 Email updated successfully.");
    }
}
