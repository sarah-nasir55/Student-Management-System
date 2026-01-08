package org.example.studentmanagementsystem.service;

import org.example.studentmanagementsystem.exception.DatabaseOperationException;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.models.Semester;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SemesterService {

    private final JdbcTemplate jdbcTemplate;

    public SemesterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Semester createSemester(Semester semester) {
        try {
            String id = UUID.randomUUID().toString();
            semester.setId(id);

            String sql = "INSERT INTO semester (id, semester) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, semester.getSemester());

            return semester;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to create semester");
        }
    }

    public List<Semester> getAllSemesters() {
        try {
            String sql = "SELECT id, semester FROM semester ORDER BY semester";

            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Semester s = new Semester();
                s.setId(rs.getString("id"));
                s.setSemester(rs.getString("semester"));
                return s;
            });

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to fetch semesters");
        }
    }

    public Semester getSemesterById(String id) {
        try {
            String sql = "SELECT id, semester FROM semester WHERE id = ?";

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Semester s = new Semester();
                s.setId(rs.getString("id"));
                s.setSemester(rs.getString("semester"));
                return s;
            }, id);

        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException("Semester not found with id: " + id);
        }
    }

    public Semester updateSemester(String id, Semester semester) {
        try {
            String sql = "UPDATE semester SET semester = ? WHERE id = ?";

            int rows = jdbcTemplate.update(sql, semester.getSemester(), id);

            if (rows == 0) {
                throw new ResourceNotFoundException("Semester not found with id: " + id);
            }

            semester.setId(id);
            return semester;

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to update semester");
        }
    }

    @Transactional
    public void deleteSemester(String id) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM enrollment WHERE semester_id = ?",
                    id
            );

            jdbcTemplate.update(
                    "DELETE FROM student WHERE semester_id = ?",
                    id
            );

            int rows = jdbcTemplate.update(
                    "DELETE FROM semester WHERE id = ?",
                    id
            );

            if (rows == 0) {
                throw new ResourceNotFoundException("Semester not found with id: " + id);
            }

        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to delete semester");
        }
    }
}
