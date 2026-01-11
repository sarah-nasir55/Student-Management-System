package org.example.studentmanagementsystem.dto;

import java.util.List;

public class StudentResponseDTO {

    private String id;
    private String name;
    private String semesterId;
    private String semesterName;
    private List<PhoneNumberDTO> phoneNumbers;
    private List<AddressDTO> addresses;

    private List<EnrollmentResponseDTO> enrollments;

    public List<EnrollmentResponseDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentResponseDTO> enrollments) {
        this.enrollments = enrollments;
    }

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

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public List<PhoneNumberDTO> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDTO> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
