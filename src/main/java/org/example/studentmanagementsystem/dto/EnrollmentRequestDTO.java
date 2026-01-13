package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Course ID is required")
    private String courseId;

    @NotBlank(message = "Semester ID is required")
    private String semesterId;
}
