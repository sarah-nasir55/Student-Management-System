package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SemesterDTO {

    private String id;

    @NotBlank(message = "Semester name is required")
    private String semester;

}
