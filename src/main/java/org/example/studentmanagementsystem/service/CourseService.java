package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.exception.DatabaseOperationException;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.models.Course;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final JdbcTemplate jdbcTemplate;

    public CourseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create Course
    public Course createCourse(Course course) {
        try {
            String id = UUID.randomUUID().toString();
            course.setId(id);

            String sql = """
                INSERT INTO course (id, name, credit_hours, instructor)
                VALUES (?, ?, ?, ?)
            """;

            jdbcTemplate.update(
                    sql,
                    id,
                    course.getName(),
                    course.getCreditHours(),
                    course.getInstructor()
            );

            return course;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to create course", ex);
        }
    }

    //Get all courses list
    public List<Course> getAllCourses() {
        try {
            String sql = """
                SELECT id, name, credit_hours, instructor
                FROM course
                ORDER BY name
            """;

            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Course c = new Course();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setCreditHours(rs.getInt("credit_hours"));
                c.setInstructor(rs.getString("instructor"));
                return c;
            });

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to fetch courses", ex);
        }
    }

    //Get course by id
    public Course getCourseById(String id) {
        try {
            String sql = """
                SELECT id, name, credit_hours, instructor
                FROM course
                WHERE id = ?
            """;

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Course c = new Course();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setCreditHours(rs.getInt("credit_hours"));
                c.setInstructor(rs.getString("instructor"));
                return c;
            }, id);

        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
    }

    //Update Course
    public Course updateCourse(String id, Course course) {
        try {
            String sql = """
                UPDATE course
                SET name = ?, credit_hours = ?, instructor = ?
                WHERE id = ?
            """;

            int rows = jdbcTemplate.update(
                    sql,
                    course.getName(),
                    course.getCreditHours(),
                    course.getInstructor(),
                    id
            );

            if (rows == 0) {
                throw new ResourceNotFoundException("Course not found with id: " + id);
            }

            course.setId(id);
            return course;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to update course", ex);
        }
    }

    // Delete Course
    public void deleteCourse(String id) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM enrollment WHERE course_id = ?",
                    id
            );

            int rows = jdbcTemplate.update(
                    "DELETE FROM course WHERE id = ?",
                    id
            );

            if (rows == 0) {
                throw new ResourceNotFoundException("Course not found with id: " + id);
            }

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to delete course", ex);
        }
    }
}
