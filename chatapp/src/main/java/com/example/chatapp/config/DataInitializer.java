package com.example.chatapp.config;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
// Spring Boot가 DB 연결 전에 CommandLineRunner를 실행할 수도 있음
//existsByUsername("testuser")가 항상 false를 반환하는 원인이 될 수 있음
public class DataInitializer  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner initUsers() {
        return args -> {
            boolean exists = userRepository.existsByUsername("testuser");
            System.out.println("🔍 존재하는가? " + exists);  // ✅ 이 로그를 확인하자

            if (!exists) {
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
