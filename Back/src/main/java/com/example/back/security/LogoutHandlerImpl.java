package com.example.back.security;

import com.example.back.model.Token;
import com.example.back.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final TokenService tokenService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);

        Token storedToken = tokenService.getToken(jwt)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);

            tokenService.saveToken(storedToken);

            SecurityContextHolder.clearContext();
        }
    }
}
