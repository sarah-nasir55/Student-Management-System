package org.example.studentmanagementsystem.mapper;

import org.example.studentmanagementsystem.dto.CourseDTO;
import org.example.studentmanagementsystem.models.Course;
import org.example.studentmanagementsystem.models.Semester;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CourseMapper {

    public Course toEntity(CourseDTO dto, Semester semester) {
        return new Course(
                null,
                dto.getName(),
                dto.getCreditHours(),
                dto.getInstructor(),
                semester
        );
    }

    public CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.id());
        dto.setName(course.name());
        dto.setCreditHours(course.creditHours());
        dto.setInstructor(course.instructor());
        dto.setSemesterId(course.semester().id());
        return dto;
    }
}
