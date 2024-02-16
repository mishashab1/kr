package com.example.back.security;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class CodeService {

    public String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(100000 + secureRandom.nextInt(900000));
    }

    public boolean validateCode(String inputCode, String sessionCode, Long codeTime) {
        return sessionCode.equals(inputCode) && ((new Date().getTime() - codeTime) < 60000);
    }
}

