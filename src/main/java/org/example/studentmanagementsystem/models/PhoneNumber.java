package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "phone_number")
@Access(AccessType.FIELD)
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    protected PhoneNumber() {}

    public PhoneNumber(String id, String phone, Student student) {
        this.id = id;
        this.phone = phone;
        this.student = student;
    }

    public String id() { return id; }
    public String phone() { return phone; }
    public Student student() { return student; }
}
