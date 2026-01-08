package org.example.studentmanagementsystem.controller;

import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semesters")
public class SemesterController {

    private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) {
        return ResponseEntity.ok(service.createSemester(semester));
    }

    @GetMapping
    public ResponseEntity<List<Semester>> getAllSemesters() {
        return ResponseEntity.ok(service.getAllSemesters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> getSemesterById(@PathVariable String id) {
        return ResponseEntity.ok(service.getSemesterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semester> updateSemester(
            @PathVariable String id,
            @RequestBody Semester semester
    ) {
        return ResponseEntity.ok(service.updateSemester(id, semester));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable String id) {
        service.deleteSemester(id);
        return ResponseEntity.noContent().build();
    }
}
