package com.example.back.dto.request;

public record AuthRequest(
        String email,
        String password
) { }
