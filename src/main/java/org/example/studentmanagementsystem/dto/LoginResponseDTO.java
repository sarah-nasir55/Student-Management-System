package org.example.studentmanagementsystem.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String email;
    private String layout;
}

