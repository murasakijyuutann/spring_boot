package com.example.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthResponseDTO {
    private String message;
    private String name;
    private String email;
}
