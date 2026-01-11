package org.example.studentmanagementsystem.repository;

import org.example.studentmanagementsystem.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, String> {
}
