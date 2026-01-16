package org.example.studentmanagementsystem.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Access(AccessType.FIELD)
public class Student {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL, // ALL operations on Student propagate to Address
            orphanRemoval = true // If an Address is removed from the list, it is DELETED from the database
    )
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "student",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    protected Student() {}

    public Student(String id, String name, Semester semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }
    public List<Enrollment> enrollments() {
        return enrollments;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public void replaceAddresses(List<Address> newAddresses) {
        this.addresses.clear();
        this.addresses.addAll(newAddresses);
    }

    public void replacePhoneNumbers(List<PhoneNumber> newPhoneNumbers) {
        this.phoneNumbers.clear();
        this.phoneNumbers.addAll(newPhoneNumbers);
    }
    public void update(String name, Semester semester) {
        this.name = name;
        this.semester = semester;
    }

    public String id() { return id; }
    public String name() { return name; }
    public Semester semester() { return semester; }
    public List<Address> addresses() { return addresses; }
    public List<PhoneNumber> phoneNumbers() { return phoneNumbers; }
}
