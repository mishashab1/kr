package com.example.back.controller;

import com.example.back.dto.Code2faDTO;
import com.example.back.dto.request.AuthRequest;
import com.example.back.dto.request.SignUpRequest;
import com.example.back.dto.response.AuthResponse;
import com.example.back.model.User;
import com.example.back.security.AuthenticationService;
import com.example.back.security.CodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("api/server/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CodeService codeService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        User user = authenticationService.signUp(request);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> signIn(@RequestBody AuthRequest request) {
        authenticationService.signIn(request);

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/login2FA")
    public ResponseEntity<AuthResponse> signInWith2FA(@RequestBody Code2faDTO request) {
        return ResponseEntity.ok(authenticationService.signInWith2FA(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
