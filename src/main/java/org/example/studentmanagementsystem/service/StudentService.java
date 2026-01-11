package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.dto.StudentRequestDTO;
import org.example.studentmanagementsystem.dto.StudentResponseDTO;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.mapper.StudentMapper;
import org.example.studentmanagementsystem.models.Address;
import org.example.studentmanagementsystem.models.PhoneNumber;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.models.Student;
import org.example.studentmanagementsystem.repository.SemesterRepository;
import org.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository studentRepository,
                          SemesterRepository semesterRepository,
                          StudentMapper mapper) {
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.mapper = mapper;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {

        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));

        Student student = mapper.toEntity(dto, semester);
        studentRepository.save(student);

        return mapper.toDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapper.toDTO(student);
    }

    public StudentResponseDTO updateStudent(String id, StudentRequestDTO dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));

        student.update(
                dto.getName(),
                semester
        );

        return mapper.toDTO(student);
    }

    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Student not found with id: " + id
            );
        }
        studentRepository.deleteById(id);
    }
}
