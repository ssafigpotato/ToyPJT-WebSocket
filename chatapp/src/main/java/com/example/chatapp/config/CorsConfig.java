package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    // REST API 요청(/ws/info)에서 문제 발생
    // 따라서 Spring Security 또는 WebMvcConfigurer를 이용해 CORS 정책을 허용해줘야
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                        .allowedOrigins("http://127.0.0.1:5500") // 프론트엔드 주소 넣으면 됨 ("http://127.0.0.1:5500")이런거
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //  허용할 HTTP 메서드
                        .allowedHeaders("*")
                        .allowCredentials(true); // 인증정보 포함 여부
            }
        };
    }
}
