package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class SemesterDTO {

    private String id;

    @NotBlank(message = "Semester name is required")
    private String semester;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
