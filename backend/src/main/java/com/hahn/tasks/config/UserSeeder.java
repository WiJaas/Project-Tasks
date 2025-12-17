package com.hahn.tasks.config;

import com.hahn.tasks.model.User;
import com.hahn.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            User admin = User.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("password"))
                    .name("Admin User")
                    .build();
            userRepository.save(admin);
        }
    }
}
