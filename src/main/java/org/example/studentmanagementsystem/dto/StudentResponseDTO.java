package org.example.studentmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentResponseDTO {

    private String id;
    private String name;
    private String semesterId;
    private String semesterName;
    private List<PhoneNumberDTO> phoneNumbers;
    private List<AddressDTO> addresses;

    private List<EnrollmentResponseDTO> enrollments;

}
