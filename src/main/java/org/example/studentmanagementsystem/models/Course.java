package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
@Access(AccessType.FIELD)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "credit_hours", nullable = false)
    private int creditHours;

    @Column(nullable = false)
    private String instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    protected Course() {}

    public Course(String id,
                  String name,
                  int creditHours,
                  String instructor,
                  Semester semester) {
        this.id = id;
        this.name = name;
        this.creditHours = creditHours;
        this.instructor = instructor;
        this.semester = semester;
    }

    public void update(String name,
                       int creditHours,
                       String instructor,
                       Semester semester) {
        this.name = name;
        this.creditHours = creditHours;
        this.instructor = instructor;
        this.semester = semester;
    }

    public String id() { return id; }
    public String name() { return name; }
    public int creditHours() { return creditHours; }
    public String instructor() { return instructor; }
    public Semester semester() { return semester; }
}
