package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;
@Entity
@Table(name = "enrollment")
@Access(AccessType.FIELD)
public class Enrollment {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    protected Enrollment() {}

    public Enrollment(String id, Student student, Course course, Semester semester) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public String id() {
        return id;
    }

    public Student student() {
        return student;
    }

    public Course course() {
        return course;
    }

    public Semester semester() {
        return semester;
    }
}
