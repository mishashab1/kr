package com.example.back.security;

import com.example.back.model.User;
import com.example.back.security.types.AuthenticationFacade;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserResolver {
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    public User getAuthenticatedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();

        return userService.getUserByEmail(authenticationFacade.getAuthentication().getName());
    }
}