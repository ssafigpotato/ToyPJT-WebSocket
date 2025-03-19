package com.example.chatapp.config;

import com.example.chatapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {
    // Spring Boot가 실행될 때, 자동으로 testuser를 DB에 저장
    @Bean
    CommandLineRunner initDatabase(UserService userService){
        return args -> {
            userService.createUser("testuser", "password123");
            System.out.println("테스트 계정 생성!");
        };
    }
}
