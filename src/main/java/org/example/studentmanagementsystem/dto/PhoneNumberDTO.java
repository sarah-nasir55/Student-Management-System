package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PhoneNumberDTO {

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9+\\-]{7,15}$",
            message = "Invalid phone number format"
    )
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
