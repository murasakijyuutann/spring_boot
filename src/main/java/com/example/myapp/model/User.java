// ✅ Updated User.java (model)
package com.example.myapp.model;


import lombok.Data;
import java.time.LocalDateTime;


@Data
public class User {
private Long id;
private String name;
private String email;
private String passwordHash;
private LocalDateTime createdAt;
private String provider;
}

// TODO: 🔒 Remove passwordHash before deploying or exposing to frontend
