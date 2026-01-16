package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveLayoutRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String layout;
}
