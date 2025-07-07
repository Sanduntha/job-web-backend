package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {
    private Long id;        // add this
    private String email;
    private String password;  // add this
    private String role;
    private String token;     // JWT token returned on login success
}
