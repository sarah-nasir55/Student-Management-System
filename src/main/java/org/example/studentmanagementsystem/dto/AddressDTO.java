package org.example.studentmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressDTO {

    @NotBlank(message = "Address cannot be empty")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
