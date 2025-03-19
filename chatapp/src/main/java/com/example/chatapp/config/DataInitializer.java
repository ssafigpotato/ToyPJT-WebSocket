package com.example.chatapp.config;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initUsers() {
        return args -> {
            // ✅ 이미 존재하는 사용자 체크 후 추가
            if (!userRepository.existsByUsername("testuser")) {
                User user = new User();
                user.setUsername("testuser");
                user.setPassword(passwordEncoder.encode("1234"));
                userRepository.save(user);
                System.out.println("✅ testuser 계정이 추가되었습니다.");
            } else {
                System.out.println("⚠ testuser 계정이 이미 존재합니다.");
            }
        };
    }
}
