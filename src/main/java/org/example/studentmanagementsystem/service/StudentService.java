package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.exception.DatabaseOperationException;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.models.Address;
import org.example.studentmanagementsystem.models.PhoneNumber;
import org.example.studentmanagementsystem.models.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final JdbcTemplate jdbcTemplate;

    public StudentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Student createStudent(Student student) {
        try {
            String studentId = UUID.randomUUID().toString();
            student.setId(studentId);

            jdbcTemplate.update(
                    "INSERT INTO student (id, name, semester_id) VALUES (?, ?, ?)",
                    studentId,
                    student.getName(),
                    student.getSemesterId()
            );

            if (student.getPhoneNumbers() != null) {
                for (PhoneNumber phone : student.getPhoneNumbers()) {
                    jdbcTemplate.update(
                            "INSERT INTO phone_number (id, student_id, phone) VALUES (?, ?, ?)",
                            UUID.randomUUID().toString(),
                            studentId,
                            phone.getPhone()
                    );
                }
            }

            if (student.getAddresses() != null) {
                for (Address addr : student.getAddresses()) {
                    jdbcTemplate.update(
                            "INSERT INTO address (id, student_id, address) VALUES (?, ?, ?)",
                            UUID.randomUUID().toString(),
                            studentId,
                            addr.getAddress()
                    );
                }
            }

            return student;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to create student");
        }
    }

    public List<Student> getAllStudents() {

        try {
            List<Student> students = jdbcTemplate.query(
                    """
                    SELECT id, name, semester_id
                    FROM student
                    ORDER BY name
                    """,
                    (rs, rowNum) -> {
                        Student s = new Student();
                        s.setId(rs.getString("id"));
                        s.setName(rs.getString("name"));
                        s.setSemesterId(rs.getString("semester_id"));
                        s.setPhoneNumbers(new ArrayList<>());
                        s.setAddresses(new ArrayList<>());
                        s.setEnrollments(new ArrayList<>());
                        return s;
                    }
            );

            //for converting student list to student map (Id, s1)
            Map<String, Student> studentMap = students.stream()
                    .collect(Collectors.toMap(Student::getId, s -> s));

            jdbcTemplate.query(
                    "SELECT student_id, phone FROM phone_number",
                    rs -> {
                        Student s = studentMap.get(rs.getString("student_id"));
                        if (s != null) {
                            s.getPhoneNumbers().add(
                                    new PhoneNumber(rs.getString("phone"))
                            );
                        }
                    }
            );

            jdbcTemplate.query(
                    "SELECT student_id, address FROM address",
                    rs -> {
                        Student s = studentMap.get(rs.getString("student_id"));
                        if (s != null) {
                            s.getAddresses().add(
                                    new Address(rs.getString("address"))
                            );
                        }
                    }
            );

            jdbcTemplate.query(
                    """
                    SELECT
                        e.student_id,
                        e.id            AS enrollment_id,
                        c.id            AS course_id,
                        c.name          AS course_name,
                        c.credit_hours,
                        c.instructor,
                        sem.id          AS semester_id,
                        sem.semester    AS semester_name
                    FROM enrollment e
                    JOIN course c     ON e.course_id = c.id
                    JOIN semester sem ON e.semester_id = sem.id
                    """,
                    rs -> {
                        Student s = studentMap.get(rs.getString("student_id"));
                        if (s != null) {
                            Map<String, Object> enrollment = new LinkedHashMap<>();
                            enrollment.put("enrollment_id", rs.getString("enrollment_id"));
                            enrollment.put("course_id", rs.getString("course_id"));
                            enrollment.put("course_name", rs.getString("course_name"));
                            enrollment.put("credit_hours", rs.getInt("credit_hours"));
                            enrollment.put("instructor", rs.getString("instructor"));
                            enrollment.put("semester_id", rs.getString("semester_id"));
                            enrollment.put("semester_name", rs.getString("semester_name"));

                            s.getEnrollments().add(enrollment);
                        }
                    }
            );

            return students;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to fetch students");
        }
    }

    public Map<String, Object> getStudentById(String studentId) {

        try { //queryForMap returns one row, queryForList returns multiple rows
            Map<String, Object> student = jdbcTemplate.queryForMap(
                    """
                    SELECT 
                        s.id           AS student_id,
                        s.name         AS student_name,
                        s.semester_id  AS semester_id,
                        sem.semester   AS semester_name
                    FROM student s
                    LEFT JOIN semester sem ON s.semester_id = sem.id
                    WHERE s.id = ?
                    """,
                    studentId
            );

            List<String> phones = jdbcTemplate.queryForList(
                    "SELECT phone FROM phone_number WHERE student_id = ?",
                    String.class,
                    studentId
            );

            List<String> addresses = jdbcTemplate.queryForList(
                    "SELECT address FROM address WHERE student_id = ?",
                    String.class,
                    studentId
            );

            List<Map<String, Object>> enrollments = jdbcTemplate.queryForList(
                    """
                    SELECT
                        e.id            AS enrollment_id,
                        c.id            AS course_id,
                        c.name          AS course_name,
                        c.credit_hours,
                        c.instructor,
                        sem.id          AS semester_id,
                        sem.semester    AS semester_name
                    FROM enrollment e
                    JOIN course c     ON e.course_id = c.id
                    JOIN semester sem ON e.semester_id = sem.id
                    WHERE e.student_id = ?
                    """,
                    studentId
            );

            student.put("phones", phones);
            student.put("addresses", addresses);
            student.put("enrollments", enrollments);

            return student;

        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
    }

    @Transactional
    public Student updateStudent(String studentId, Student student) {
        try {
            int rows = jdbcTemplate.update(
                    "UPDATE student SET name = ? WHERE id = ?",
                    student.getName(),
                    studentId
            );

            if (rows == 0) {
                throw new ResourceNotFoundException("Student not found with id: " + studentId);
            }

            jdbcTemplate.update(
                    "DELETE FROM phone_number WHERE student_id = ?",
                    studentId
            );

            jdbcTemplate.update(
                    "DELETE FROM address WHERE student_id = ?",
                    studentId
            );

            if (student.getPhoneNumbers() != null) {
                for (PhoneNumber phone : student.getPhoneNumbers()) {
                    jdbcTemplate.update(
                            "INSERT INTO phone_number (id, student_id, phone) VALUES (?, ?, ?)",
                            UUID.randomUUID().toString(),
                            studentId,
                            phone.getPhone()
                    );
                }
            }

            if (student.getAddresses() != null) {
                for (Address addr : student.getAddresses()) {
                    jdbcTemplate.update(
                            "INSERT INTO address (id, student_id, address) VALUES (?, ?, ?)",
                            UUID.randomUUID().toString(),
                            studentId,
                            addr.getAddress()
                    );
                }
            }

            student.setId(studentId);
            return student;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to update student");
        }
    }

    @Transactional
    public void deleteStudent(String studentId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM phone_number WHERE student_id = ?",
                    studentId
            );

            jdbcTemplate.update(
                    "DELETE FROM address WHERE student_id = ?",
                    studentId
            );

            int rows = jdbcTemplate.update(
                    "DELETE FROM student WHERE id = ?",
                    studentId
            );

            if (rows == 0) {
                throw new ResourceNotFoundException("Student not found with id: " + studentId);
            }

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to delete student");
        }
    }
}
