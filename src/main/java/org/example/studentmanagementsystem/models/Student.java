package org.example.studentmanagementsystem.models;

import java.util.List;
import java.util.Map;

public class Student {

    private String id;
    private String name;

    // Foreign keys
    private String semesterId;
    private List<Map<String, Object>> enrollments;
    private List<PhoneNumber> phoneNumbers;
    private List<Address> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public List<Map<String, Object>> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Map<String, Object>> enrollments) {
        this.enrollments = enrollments;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
