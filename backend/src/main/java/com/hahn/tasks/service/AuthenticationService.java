package com.hahn.tasks.service;

import com.hahn.tasks.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.hahn.tasks.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest request) {
        // Authenticate the user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get the authenticated UserDto (safe DTO)
        UserDto userDto = (UserDto) auth.getPrincipal();

        // Build the login response with token and optional user info
        return new LoginResponse(
                tokenProvider.generateToken(auth),
                userDto.getId(),     // optional
                userDto.getEmail()   // optional
        );
    }
}
