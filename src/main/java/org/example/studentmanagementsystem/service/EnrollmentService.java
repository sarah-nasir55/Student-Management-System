package org.example.studentmanagementsystem.service;

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

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final EnrollmentMapper mapper;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             SemesterRepository semesterRepository,
                             EnrollmentMapper mapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.mapper = mapper;
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
