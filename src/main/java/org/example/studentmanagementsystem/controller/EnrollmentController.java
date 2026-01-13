package org.example.studentmanagementsystem.controller;

import jakarta.validation.Valid;
import org.example.studentmanagementsystem.dto.EnrollmentRequestDTO;
import org.example.studentmanagementsystem.dto.EnrollmentResponseDTO;
import org.example.studentmanagementsystem.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(
            @Valid @RequestBody EnrollmentRequestDTO request
    ) {
        return ResponseEntity.ok(service.createEnrollment(request));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollments()

    {
        return ResponseEntity.ok(service.getEnrollments());
    }
}
