package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;


@Value
public class UserRequest {
    @NotBlank(message = "First name is required")
    private final String firstName;

    private final String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private final String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private final String password;
    private final String role;


}
