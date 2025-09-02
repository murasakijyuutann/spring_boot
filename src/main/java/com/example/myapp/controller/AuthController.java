package com.example.myapp.controller;

import com.example.myapp.dto.UserCreateDTO;
import com.example.myapp.dto.UserLoginDTO;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
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

    // ✅ Register endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO dto) {
        userService.createUser(dto); // existing method
        return ResponseEntity.ok("✅ Registration successful");
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO dto, HttpSession session) {
        User user = userService.login(dto.getEmail(), dto.getPassword());
        session.setAttribute("user", user); // store user in session
        return ResponseEntity.ok("✅ Login successful");
    }

    // ✅ Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // destroy session
        return ResponseEntity.ok("✅ Logged out successfully");
    }

    // ✅ Check current session user
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return ResponseEntity.status(401).body("❌ Not logged in");
    }
    return ResponseEntity.ok(user);
}

}
