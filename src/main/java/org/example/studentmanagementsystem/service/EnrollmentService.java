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
public class EnrollmentService {

    private final JdbcTemplate jdbcTemplate;

    public EnrollmentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Course createEnrollment(String studentId, String courseId, String semesterId) {
        try {
            String enrollmentId = UUID.randomUUID().toString();

            String sql = """
                INSERT INTO enrollment (id, student_id, course_id, semester_id)
                VALUES (?, ?, ?, ?)
            """;

            jdbcTemplate.update(
                    sql,
                    enrollmentId,
                    studentId,
                    courseId,
                    semesterId
            );

            return jdbcTemplate.queryForObject(
                    """
                    SELECT id, name, credit_hours, instructor
                    FROM course
                    WHERE id = ?
                    """,
                    (rs, rowNum) -> {
                        Course c = new Course();
                        c.setId(rs.getString("id"));
                        c.setName(rs.getString("name"));
                        c.setCreditHours(rs.getInt("credit_hours"));
                        c.setInstructor(rs.getString("instructor"));
                        return c;
                    },
                    courseId
            );

        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException(
                    "Invalid student, course, or semester ID"
            );
        }
    }
//
//    public List<Course> getAllEnrollments() {
//        try {
//            String sql = """
//                SELECT DISTINCT
//                    c.id,
//                    c.name,
//                    c.credit_hours,
//                    c.instructor
//                FROM enrollment e
//                JOIN course c ON e.course_id = c.id
//                ORDER BY c.name
//            """;
//
//            return jdbcTemplate.query(sql, (rs, rowNum) -> {
//                Course c = new Course();
//                c.setId(rs.getString("id"));
//                c.setName(rs.getString("name"));
//                c.setCreditHours(rs.getInt("credit_hours"));
//                c.setInstructor(rs.getString("instructor"));
//                return c;
//            });
//
//        } catch (DataAccessException ex) {
//            throw new DatabaseOperationException("Failed to fetch enrollments");
//        }
//    }
}
