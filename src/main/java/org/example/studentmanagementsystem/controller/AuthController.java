package org.example.studentmanagementsystem.controller;

import jakarta.validation.Valid;
import org.example.studentmanagementsystem.dto.LoginRequestDTO;
import org.example.studentmanagementsystem.dto.LoginResponseDTO;
import org.example.studentmanagementsystem.dto.SaveLayoutRequestDTO;
import org.example.studentmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return service.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
           @Valid @RequestBody LoginRequestDTO dto
    ) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/layout")
    public ResponseEntity<Map<String, String>> saveLayout(
            @Valid @RequestBody SaveLayoutRequestDTO request
    ) {
        return ResponseEntity.ok(service.saveLayout(request));
    }
}
