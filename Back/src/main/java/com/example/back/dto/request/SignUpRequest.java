package com.example.back.dto.request;

public record SignUpRequest(
        String firstName,
        String lastName,
        String surname,
        String email,
        String password
) {
}
