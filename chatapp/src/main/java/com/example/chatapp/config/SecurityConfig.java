package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // Spring Security가 활성화된 상태에서 WebSocket을 요청해서 인증이 필요한 상황
    // WebSocket 요청도 Security 설정에서 허용되지 않아서 인증 없이 접근 불가
    //  기본적으로 WebSocket 요청은 보안 검사를 받음
    // → 이를 해결하려면 WebSocket 엔드포인트(/ws)를 Security에서 예외 처리해야 함!
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()  // ✅ WebSocket 요청 허용
                        .requestMatchers("/topic/**").permitAll() // ✅ WebSocket 메시지 허용
                        .requestMatchers("/app/**").permitAll() // ✅ STOMP 메시지 허용
                        .anyRequest().permitAll() // ✅ 나머지 요청도 허용 (테스트용)
                );
        return http.build();
    }
}
