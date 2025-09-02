package com.example.myapp.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    private String password; // ✅ only if you plan to support changing password
    // private String passwordHash;
}
