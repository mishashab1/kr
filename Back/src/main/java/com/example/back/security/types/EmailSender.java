package com.example.back.security.types;

public interface EmailSender {
    void sendCode(String to, String email);
}
