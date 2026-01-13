package org.example.studentmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.example.studentmanagementsystem.dto.EnrollmentRequestDTO;
import org.example.studentmanagementsystem.dto.EnrollmentResponseDTO;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.mapper.EnrollmentMapper;
import org.example.studentmanagementsystem.models.Course;
import org.example.studentmanagementsystem.models.Enrollment;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.models.Student;
import org.example.studentmanagementsystem.repository.CourseRepository;
import org.example.studentmanagementsystem.repository.EnrollmentRepository;
import org.example.studentmanagementsystem.repository.SemesterRepository;
import org.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final EnrollmentMapper mapper;

    public List<EnrollmentResponseDTO> getEnrollments(){
        return enrollmentRepository.findAll().stream().map(e -> {
            EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
            dto.setEnrollmentId(e.id());
            dto.setCourseId(e.course().id());
            dto.setStudentId(e.student().id());
            dto.setCourseName(e.course().name());
            dto.setSemesterId(e.semester().id());
            dto.setSemesterName(e.semester().semester());
            dto.setStudentName(e.student().name());
             return dto;
        }).toList();
    }
    public EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id: " + request.getCourseId()
                        )
                );

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found with id: " + request.getSemesterId()
                        )
                );

        Enrollment enrollment = mapper.toEntity(student, course, semester);
        enrollmentRepository.save(enrollment);

        return mapper.toDTO(enrollment);
    }
}
