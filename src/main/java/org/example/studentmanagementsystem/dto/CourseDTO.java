package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CourseDTO {

    private String id;

    @NotBlank(message = "Course name is required")
    private String name;

    @Positive(message = "Credit hours must be positive")
    private int creditHours;

    @NotBlank(message = "Instructor name is required")
    private String instructor;

    @NotBlank(message = "Semester ID is required")
    private String semesterId;
}
