package org.example.studentmanagementsystem.repository;

import org.example.studentmanagementsystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("""
    SELECT DISTINCT s
    FROM Student s
    LEFT JOIN FETCH s.addresses
    LEFT JOIN FETCH s.phoneNumbers
    LEFT JOIN FETCH s.enrollments e
    LEFT JOIN FETCH e.course
    LEFT JOIN FETCH e.semester
    LEFT JOIN FETCH s.semester
""")
    List<Student> findAllWithDetails();

    @Query("""
    SELECT s
    FROM Student s
    LEFT JOIN FETCH s.addresses
    LEFT JOIN FETCH s.phoneNumbers
    LEFT JOIN FETCH s.enrollments e
    LEFT JOIN FETCH e.course
    LEFT JOIN FETCH e.semester
    LEFT JOIN FETCH s.semester
    WHERE s.id = :id
""")
    Optional<Student> findByIdWithDetails(String id);

}
