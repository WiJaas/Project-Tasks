package com.hahn.tasks.service;

import com.hahn.tasks.dto.LoginRequest;
import com.hahn.tasks.dto.LoginResponse;
import com.hahn.tasks.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldLoginSuccessfully() {

        // GIVEN
        LoginRequest request =
                new LoginRequest("admin@test.com", "password");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "admin@test.com", null);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "admin@test.com", "password")))
                .thenReturn(authentication);

        when(tokenProvider.generateToken(authentication))
                .thenReturn("jwt-token");

        // WHEN
        LoginResponse response = authenticationService.login(request);

        // THEN
        assertThat(response.getToken()).isEqualTo("jwt-token");
    }
}
