package org.example.studentmanagementsystem.controller;

import org.example.studentmanagementsystem.dto.EnrollmentRequest;
import org.example.studentmanagementsystem.models.Course;
import org.example.studentmanagementsystem.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Course> createEnrollment(
            @RequestBody EnrollmentRequest request
    ) {
        Course course = service.createEnrollment(
                request.getStudentId(),
                request.getCourseId(),
                request.getSemesterId()
        );
        return ResponseEntity.ok(course);
    }

//    @GetMapping
//    public ResponseEntity<List<Course>> getAllEnrollments() {
//        return ResponseEntity.ok(service.getAllEnrollments());
//    }
}
