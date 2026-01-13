package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "Student name is required")
    private String name;

    @NotBlank(message = "Semester ID is required")
    private String semesterId;

    @NotEmpty(message = "At least one phone number is required")
    private List<PhoneNumberDTO> phoneNumbers;

    @NotEmpty(message = "At least one address is required")
    private List<AddressDTO> addresses;


}
