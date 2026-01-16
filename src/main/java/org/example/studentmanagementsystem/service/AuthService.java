package org.example.studentmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.example.studentmanagementsystem.dto.LoginRequestDTO;
import org.example.studentmanagementsystem.dto.LoginResponseDTO;
import org.example.studentmanagementsystem.dto.SaveLayoutRequestDTO;
import org.example.studentmanagementsystem.mapper.AdminMapper;
import org.example.studentmanagementsystem.models.Admin;
import org.example.studentmanagementsystem.repository.AdminRepository;
import org.example.studentmanagementsystem.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final AdminRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AdminMapper mapper;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Admin admin = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        return mapper.toDTO(admin, token);
    }

    public Map<String, String> signup(LoginRequestDTO request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = encoder.encode(request.getPassword());

        Admin admin = mapper.toEntity(request, encodedPassword);
        repository.save(admin);

        return Map.of("message", "User registered successfully");
    }

    public Map<String, String> saveLayout(SaveLayoutRequestDTO dto) {
        Admin admin = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        admin.setLayout(dto.getLayout()); // save JSON
        repository.save(admin);

        return Map.of("message", "Layout saved successfully");
    }

}
