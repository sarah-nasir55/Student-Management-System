package org.example.studentmanagementsystem.mapper;

import org.example.studentmanagementsystem.dto.LoginRequestDTO;
import org.example.studentmanagementsystem.dto.LoginResponseDTO;
import org.example.studentmanagementsystem.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public LoginResponseDTO toDTO(Admin admin, String token) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setToken(token);
        dto.setEmail(admin.getEmail());
        dto.setLayout(admin.getLayout());
        return dto;
    }
    public Admin toEntity(LoginRequestDTO dto, String encodedPassword) {
        Admin admin = new Admin(dto.getEmail(), encodedPassword);
        return admin;
    }
}

