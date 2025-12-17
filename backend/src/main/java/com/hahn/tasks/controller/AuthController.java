package com.hahn.tasks.controller;

import com.hahn.tasks.dto.LoginRequest;
import com.hahn.tasks.dto.LoginResponse;
import com.hahn.tasks.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new LoginResponse(token);
    }
}
