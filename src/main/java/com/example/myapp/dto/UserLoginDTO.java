package com.example.myapp.dto;

import jakarta.validation.constraints.*;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
