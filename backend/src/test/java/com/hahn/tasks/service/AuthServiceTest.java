package com.hahn.tasks.auth;

import com.hahn.tasks.model.User;
import com.hahn.tasks.repository.UserRepository;
import com.hahn.tasks.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {
        User user = User.builder()
                .email("admin@test.com")
                .password("encoded")
                .build();

        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded"))
                .thenReturn(true);
        when(jwtUtil.generateToken("admin@test.com"))
                .thenReturn("jwt-token");

        String token = authService.login("admin@test.com", "password");

        assertThat(token).isEqualTo("jwt-token");
    }
}
