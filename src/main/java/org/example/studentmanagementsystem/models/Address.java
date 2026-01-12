package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
@Access(AccessType.FIELD)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    protected Address() {}

    public Address(String id, String address, Student student) {
        this.id = id;
        this.address = address;
        this.student = student;
    }

    public String id() { return id; }
    public String address() { return address; }
    public Student student() { return student; }
}
