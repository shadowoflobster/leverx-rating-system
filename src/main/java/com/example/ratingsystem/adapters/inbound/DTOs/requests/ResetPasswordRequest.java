package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String code;
    private String password;
}