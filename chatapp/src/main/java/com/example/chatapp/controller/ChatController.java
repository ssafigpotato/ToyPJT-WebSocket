package com.example.chatapp.controller;

import com.example.chatapp.dto.ChatMessageDTO;
import com.example.chatapp.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller // 이 클래스가 Spring MVC 컨트롤러가 아니라, WebSocket 메시지를 처리하는 컨트롤러라는 의미
public class ChatController {

    /**
     * 📌 실행 흐름 정리
     * 1. 클라이언트 → 서버 (메시지 전송)
     * 1️⃣ 클라이언트가 /app/chat.sendMessage 로 메시지를 보냄
     * 2️⃣ ChatController.sendMessage()가 실행됨
     * 3️⃣ chatService.saveMessage(chatMessageDTO)를 호출해서 DB에 저장
     * 4️⃣ @SendTo("/topic/public") 를 통해 모든 구독자에게 메시지를 전달
     *
     * 2. 서버 → 클라이언트 (메시지 받기)
     * 1️⃣ 클라이언트가 /topic/public을 구독하고 있음
     * 2️⃣ 누군가 메시지를 보내면, 서버가 /topic/public으로 메시지를 전달
     * 3️⃣ 모든 구독자(클라이언트)가 메시지를 자동으로 받음
     * */
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 클라이언트가 "/app/chat.sendMessage"로 메시지 보내면 실행됨
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        chatService.saveMessage(chatMessageDTO); // DB에 저장하고
        return chatMessageDTO; // 모든 구독자에게 메시지 전송
    }

}
