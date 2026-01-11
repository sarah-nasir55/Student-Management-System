package org.example.studentmanagementsystem.repository;

import org.example.studentmanagementsystem.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
