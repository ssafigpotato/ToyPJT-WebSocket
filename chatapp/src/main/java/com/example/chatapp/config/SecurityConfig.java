package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // PasswordEncoder 빈 등록 (이걸 추가해야 오류 해결됨!)
    // Could not autowire. No beans of 'PasswordEncoder' type found. 오류 해결 방법
    // 원인: PasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 인터페이스. 프로젝트에 암호화 관련 Bean이 등록되지 않아서 발생
    // 즉, PasswordEncoder를 직접 Bean으로 등록해야 함!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
