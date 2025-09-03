package com.example.myapp.controller;

import com.example.myapp.dto.UserCreateDTO;
import com.example.myapp.dto.UserDTO;
import com.example.myapp.dto.UserLoginDTO;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registration successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO dto) {
        userService.createUser(dto);
        return ResponseEntity.ok("✅ Registration successful");
    }

    @Operation(summary = "Login with email and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO dto, HttpSession session) {
        try {
            User user = userService.login(dto.getEmail(), dto.getPassword());
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok("✅ Login successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("❌ Invalid credentials");
        }
    }

    @Operation(summary = "Logout the current session")
    @ApiResponse(responseCode = "200", description = "Logged out successfully")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("✅ Logged out successfully");
    }

    @Operation(summary = "Get current logged-in user info")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "401", description = "Not logged in"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body("❌ Not logged in");
        }

        UserDTO user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("❌ User not found");
        }

        // DTO with only safe fields
        record UserSessionDTO(Long id, String email) {}
        return ResponseEntity.ok(new UserSessionDTO(user.getId(), user.getEmail()));
    }
}
