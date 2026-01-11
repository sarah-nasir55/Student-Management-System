package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.dto.CourseDTO;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.mapper.CourseMapper;
import org.example.studentmanagementsystem.models.Course;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.repository.CourseRepository;
import org.example.studentmanagementsystem.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository courseRepository,
                         SemesterRepository semesterRepository,
                         CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.mapper = mapper;
    }

    public CourseDTO createCourse(CourseDTO dto) {

        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found with id: " + dto.getSemesterId()
                        )
                );

        Course course = mapper.toEntity(dto, semester);
        courseRepository.save(course);

        return mapper.toDTO(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDTO getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id: " + id
                        )
                );
        return mapper.toDTO(course);
    }

    public CourseDTO updateCourse(String id, CourseDTO dto) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id: " + id
                        )
                );

        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found with id: " + dto.getSemesterId()
                        )
                );

        course.update(
                dto.getName(),
                dto.getCreditHours(),
                dto.getInstructor(),
                semester
        );

        courseRepository.save(course);

        return mapper.toDTO(course);
    }

    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Course not found with id: " + id
            );
        }
        courseRepository.deleteById(id);
    }
}
