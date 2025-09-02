package com.example.myapp.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserRegisterDTO {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    private String password;
}

// private String passwordHash; // ❌ do NOT include hashed password in DTO