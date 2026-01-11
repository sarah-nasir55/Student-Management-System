package org.example.studentmanagementsystem.controller;

import jakarta.validation.Valid;
import org.example.studentmanagementsystem.dto.SemesterDTO;
import org.example.studentmanagementsystem.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/semesters")
public class SemesterController {

    private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SemesterDTO> createSemester(
            @Valid @RequestBody SemesterDTO semester
    ) {
        return ResponseEntity.ok(service.createSemester(semester));
    }

    @GetMapping
    public ResponseEntity<List<SemesterDTO>> getAllSemesters() {
        return ResponseEntity.ok(service.getAllSemesters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterDTO> getSemesterById(@PathVariable String id) {
        return ResponseEntity.ok(service.getSemesterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterDTO> updateSemester(
            @PathVariable String id,
            @Valid @RequestBody SemesterDTO semester
    ) {
        return ResponseEntity.ok(service.updateSemester(id, semester));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSemester(@PathVariable String id) {
        service.deleteSemester(id);
        return ResponseEntity.ok(Map.of("message", "Semester deleted successfully"));
    }
}
