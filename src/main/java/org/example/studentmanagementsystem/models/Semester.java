package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "semester")
@Access(AccessType.FIELD)
public class Semester {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String semester;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    protected Semester() {}

    public Semester(String id, String semester) {
        this.id = id;
        this.semester = semester;
    }

    public void update(String semester) {
        this.semester = semester;
    }

    public String id() {
        return id;
    }

    public String semester() {
        return semester;
    }
}
