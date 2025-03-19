package com.example.chatapp.controller;

import com.example.chatapp.dto.ChatMessageDTO;
import com.example.chatapp.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller // 이 클래스가 Spring MVC 컨트롤러가 아니라, WebSocket 메시지를 처리하는 컨트롤러라는 의미
public class ChatController {

    /**
     * 📌 실행 흐름 정리
     * 1. 클라이언트가 WebSocket 연결	stompClient.connect()로 WebSocket(/ws)에 연결
     * 2. 특정 방 구독	클라이언트가 /topic/{roomId} 구독
     * 3. 클라이언트가 메시지 전송	/app/chat.sendMessage로 메시지 전송
     * 4. 서버(ChatController)에서 메시지 수신	@MessageMapping("/chat.sendMessage") 실행
     * 5. 서버에서 메시지를 DB에 저장	chatService.saveMessage(chatMessageDTO) 호출
     * 6. 서버가 해당 방(roomId)의 구독자에게 메시지 전송	messagingTemplate.convertAndSend("/topic/{roomId}", chatMessageDTO) 실행
     * 7. 방(roomId)을 구독한 클라이언트들에게 메시지 전달	모든 해당 방 구독자들이 메시지
     *
     * ✅ 클라이언트 → 서버
     * 1️⃣ 클라이언트가 stompClient.send("/app/chat.sendMessage")로 JSON 메시지를 보냄.
     * 2️⃣ @MessageMapping("/chat.sendMessage") 메서드에서 @Payload를 통해 ChatMessageDTO로 변환.
     * 3️⃣ chatService.saveMessage(chatMessageDTO);를 호출해 DB에 저장.
     *
     * ✅ 서버 → 클라이언트(구독자)
     * 4️⃣ convertAndSend("/topic/{roomId}", chatMessageDTO); 실행.
     * 5️⃣ /topic/{roomId}을 구독하고 있는 클라이언트들이 자동으로 메시지를 받음.
     */

    private final ChatService chatService;
    // 특정 구독자들에게 메시지를 보낼 때 사용하는 Spring의 WebSocket 메시지 전송 도구
    // 특정 roomId를 가진 채팅방에만 메시지를 보내기 위해 사용함
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트가 "/app/chat.sendMessage"로 메시지 보내면 실행됨
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        /**
         *  @Payload -> WebSocket 메시지 핸들러 메서드에서, 클라이언트가 보낸 데이터를 자동으로 객체로 변환해주는 어노테이션
         *  클라이언트가 /app/chat.sendMessage로 보낸 JSON 데이터를 자동으로 ChatMessageDTO 객체로 변환
         *  @Payload가 없으면, 메시지가 객체로 자동 변환되지 않고 직접 JSON을 파싱해야 함.
         */

//        System.out.println("받은 메시지: " + chatMessageDTO.getContent()); // 받은 메시지 확인
        chatService.saveMessage(chatMessageDTO); // DB에 저장하고

        // "/topic/{roomId}에 메시지 전송 (해당 방에 있는 유저들만 받기
        String destination = "/topic/" + chatMessageDTO.getRoomId();
        messagingTemplate.convertAndSend(destination, chatMessageDTO); // WebSocket 메시지를 특정 구독자에게 보내는 역할을 하는 메서드
    }

}
