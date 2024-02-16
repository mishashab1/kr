package com.example.back.security;

import com.example.back.dto.Code2faDTO;
import com.example.back.dto.request.AuthRequest;
import com.example.back.dto.request.SignUpRequest;
import com.example.back.dto.response.AuthResponse;
import com.example.back.model.Token;
import com.example.back.model.User;
import com.example.back.model.enums.RoleType;
import com.example.back.model.enums.TokenType;
import com.example.back.service.RoleService;
import com.example.back.service.TokenService;
import com.example.back.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticatedUserResolver resolver;

    private final HttpSession session;
    private final CodeService codeService;
    private final EmailService emailService;

    private final TokenService tokenService;
    private final RoleService roleService;
    private final UserService userService;

    public User signUp(SignUpRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(roleService.getRole(RoleType.USER))
                .build();

        return userService.saveUser(user);
    }

    public void signIn(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userService.getUserByEmail(request.email());
        revokeAllUserTokens(user);

        generate2FACodeAndSend(user);
    }

    public AuthResponse signInWith2FA(Code2faDTO inputCode) {
        String sessionCode = (String) session.getAttribute("code");
        Long codeTime = (Long) session.getAttribute("codeTime");
        User user = (User) session.getAttribute("user");

        if (!codeService.validateCode(inputCode.code(), sessionCode, codeTime)) {
            throw new BadCredentialsException("Неверный 2FA код.");
        }

        List<SimpleGrantedAuthority> authorities = roleService.getAuthorities(user.getId());

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getEmail(), user.getPassword(), authorities);

        String accessToken = jwtAuthenticationService.generateAccessToken(userDetails);
        String refreshToken = jwtAuthenticationService.generateRefreshToken(userDetails);

        saveUserToken(user, accessToken);

        session.removeAttribute("user");
        session.removeAttribute("code");
        session.removeAttribute("codeTime");

        return new AuthResponse(accessToken, refreshToken);
    }

    public void generate2FACodeAndSend(User user) {
        String code = codeService.generateCode();

        session.setAttribute("user", user);
        session.setAttribute("code", code);
        session.setAttribute("codeTime", new Date().getTime());
        // TODO: отправить код на почту
        emailService.sendCode(user.getEmail(), buildEmail(code));
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

        refreshToken = authHeader.substring(7);
        email = jwtAuthenticationService.extractUsername(refreshToken);

        if (email != null) {
            User user = userService.getUserByEmail(email);

            List<SimpleGrantedAuthority> authorities = roleService.getAuthorities(user.getId());

            UserDetailsImpl userDetails = new UserDetailsImpl(user.getEmail(), user.getPassword(), authorities);

            if (jwtAuthenticationService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = jwtAuthenticationService.generateAccessToken(userDetails);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String accessToken) {
        Token token = Token.builder()
                .user(user)
                .token(accessToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenService.saveToken(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> tokens = tokenService.getAllValidTokens(user.getId());

        if (tokens.isEmpty()) return;

        tokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenService.saveAllTokens(tokens);
    }

    private String buildEmail(String code) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + code + "</p>\n" +
                "</div>";
    }
}
