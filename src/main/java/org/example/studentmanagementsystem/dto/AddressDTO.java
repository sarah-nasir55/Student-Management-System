package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {

    @NotBlank(message = "Address cannot be empty")
    private String address;

}
