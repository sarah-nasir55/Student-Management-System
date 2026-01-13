package org.example.studentmanagementsystem.dto;

import lombok.Data;

@Data
public class EnrollmentResponseDTO {

    private String enrollmentId;
    private String studentId;
    private String courseId;
    private String courseName;
    private String semesterId;
    private String semesterName;
    private String studentName;
}
