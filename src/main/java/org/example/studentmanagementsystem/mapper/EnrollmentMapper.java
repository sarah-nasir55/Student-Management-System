package org.example.studentmanagementsystem.mapper;

import org.example.studentmanagementsystem.dto.EnrollmentRequestDTO;
import org.example.studentmanagementsystem.dto.EnrollmentResponseDTO;
import org.example.studentmanagementsystem.models.Course;
import org.example.studentmanagementsystem.models.Enrollment;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.models.Student;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EnrollmentMapper {

    public Enrollment toEntity(Student student,
                               Course course,
                               Semester semester) {
        return new Enrollment(
                UUID.randomUUID().toString(),
                student,
                course,
                semester
        );
    }

    public EnrollmentResponseDTO toDTO(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setEnrollmentId(enrollment.id());
        dto.setCourseId(enrollment.course().id());
        dto.setCourseName(enrollment.course().name());
        dto.setSemesterId(enrollment.semester().id());
        dto.setSemesterName(enrollment.semester().semester());
        dto.setStudentId(enrollment.student().id()); //always initialize the id first otherwise will not pick the next fields for that object
        dto.setStudentName(enrollment.student().name());
        return dto;
    }
}
