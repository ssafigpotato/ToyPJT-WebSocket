package com.example.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // spring의 설정(configuration)클래스임을 의미 -> 여기서는 websocket관련 설정 담당
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커 사용 설정
/**
 * stomp 메시지 브로커 활성화
 * WebSocket을 사용하지만, STOMP를 추가로 지원하도록 설정하는 어노테이션
 * 기본적인 WebSocket은 1:1 통신만 가능하지만,
 * STOMP를 사용하면 Pub-Sub 구조(발행/구독 모델)를 지원할 수 있음
 * STOMP를 활성화하면 *Message Broker(메시지 브로커)를 사용해서 메시지를 여러 클라이언트에게 동시에 전달할 수 있음
 * */
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 📌 WebSocket 통신 흐름
     * 1️⃣ 클라이언트가 /ws 엔드포인트로 WebSocket 연결
     * 2️⃣ 클라이언트가 예) /app/chat.sendMessage 경로로 메시지 전송
     * 3️⃣ 서버는 메시지를 처리한 후 /topic/public으로 구독자에게 메시지 전달
     * */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic"); // 클라이언트가 구독할 주소(브로커가 메시지 전달할 경로 설정)
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 주소
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws") // 클라이언트가 WebSocket에 연결할 엔드포인트 설정
                .setAllowedOrigins("*") // CORS 허용
                .withSockJS(); // WebSocket 미지원 환경에서는 SockJS사용
                // SockJS를 사용하면 WebSocket이 지원되지 않는 환경에서도 Ajax를 이용한 폴링 방식으로 연결할 수 있음
    }
}
