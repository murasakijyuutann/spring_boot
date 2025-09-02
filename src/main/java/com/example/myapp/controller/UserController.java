package com.example.myapp.controller;

import com.example.myapp.dto.*;
import com.example.myapp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@Tag(name = "User API", description = "Operations related to user management")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Basic CRUD

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Returns user details by user ID")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO dto) {
        userService.createUser(dto);
        return ResponseEntity.ok("✅ User created successfully.");
    }

    @Operation(summary = "Update user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.ok("✏️ User updated successfully.");
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("❌ User deleted successfully.");
    }

    @Operation(summary = "Delete user by name")
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name) {
        userService.deleteUserByName(name);
        return ResponseEntity.ok("❌ User deleted successfully.");
    }

    // ✅ Custom Queries from UserMapper

    @Operation(summary = "Search users by exact name")
    @GetMapping("/search/by-name")
    public List<UserDTO> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @Operation(summary = "Search users by keyword")
    @GetMapping("/search/by-keyword")
    public List<UserDTO> searchByName(@RequestParam String keyword) {
        return userService.searchByName(keyword);
    }

    @Operation(summary = "Get all users ordered by name (ASC)")
    @GetMapping("/ordered-by-name")
    public List<UserDTO> getAllOrderedByName() {
        return userService.findAllOrderedByName();
    }

    @Operation(summary = "Get all users ordered by ID (ASC)")
    @GetMapping("/ordered-by-id")
    public List<UserDTO> getAllOrderedById() {
        return userService.findAllOrderedByID();
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @Operation(summary = "Get latest registered user")
    @GetMapping("/latest")
    public UserDTO findLatestUser() {
        return userService.findLatestUser();
    }

    @Operation(summary = "Count total users")
    @GetMapping("/count")
    public int countUsers() {
        return userService.countUsers();
    }

    @Operation(summary = "Update email by user ID")
    @PutMapping("/email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable Long id, @RequestParam String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.ok("📧 Email updated successfully.");
    }
}
