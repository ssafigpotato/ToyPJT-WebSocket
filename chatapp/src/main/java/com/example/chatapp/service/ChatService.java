package com.example.chatapp.service;

import com.example.chatapp.dto.ChatMessageDTO;
import com.example.chatapp.entity.ChatMessage;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.ChatRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository; // 채팅 메시지 저장소
    private final UserRepository userRepository; // 사용자 저장소

    // 생성자 주입
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    // 1) 채팅 메시지 저장하는 메소드
    public ChatMessage saveMessage(ChatMessageDTO chatMessageDTO) {
        // findById는 Spring Data JPA가 자동으로 제공하는 기본 메서드이므로 바로 사용 가능
        /**
         * DTOㅇ[서 userId를 가져와서
         * DB에서 해당 ID의 유저를 조회
         * 유저가 없으면 예외처리
         * */
        User sender = userRepository.findById(chatMessageDTO.getUserId()) // User 조회
                .orElseThrow(() -> new RuntimeException("User not found")); // 얘외 처리

        ChatMessage chatMessage = new ChatMessage(); // 객체 생성
        chatMessage.setSender(sender); // 필드 값 설정
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setRoomId(chatMessageDTO.getRoomId());
        chatMessage.setTimestamp(chatMessageDTO.getTimestamp());

        return chatRepository.save(chatMessage); // DB에 저장(INSERT문 실행)
        // 반환타입이 ChatMessage임
    }

    // 2) 특정 채팅방의 모든 메시지 조회
    public List<ChatMessageDTO> getChatMessages(String roomId) {
        // (1) DB에서 해당 roomID의 채팅 메시지를 시간순으로 가져옴
        List<ChatMessage> chatMessages = chatRepository.findByRoomIdOrderByTimestampAsc(roomId);

        // (2) ChatMessage 리스트를 ChatMessageDTO 리스트로 변환할 새로운 리스트 생성
        List<ChatMessageDTO> chatMessageDTOS = new ArrayList<>();

        // (3) 가져온 채팅 메시지들을 돌면서 DTO로 변환
        for(ChatMessage msg : chatMessages) {
            // (4) 새로운 dto 객체 생성
            ChatMessageDTO dto = new ChatMessageDTO();
            // (5) DTO 필드 값 설정
            dto.setUserId(msg.getSender().getId()); // msg.getSender로 User객체가 반환되고, 거기서 User객체의 id(Long타입)을 가져오기
            dto.setContent(msg.getContent());
            dto.setTimestamp(msg.getTimestamp());
            dto.setRoomId(msg.getRoomId());
            // (6) 변환된 dto 객체를 리스트에 추가
            chatMessageDTOS.add(dto);
        }
        //(7) 변환된 DTO 리스트 반환
        return chatMessageDTOS;
    }

    /**
     * 아래처럼 stream을 사용해서 함수형 프로그래밍 스타일 적용 가능
     * public List<ChatMessageDTO> getChatMessages(String roomId) {
     *     return chatRepository.findByRoomIdOrderByTimestampAsc(roomId) // 1️⃣ DB에서 메시지를 불러옴
     *             .stream() // 2️⃣ 불러온 리스트를 스트림으로 변환
     *             .map(msg -> { // 3️⃣ 각 ChatMessage 객체를 ChatMessageDTO로 변환
     *                 ChatMessageDTO dto = new ChatMessageDTO();
     *                 dto.setUserId(msg.getSender().getId()); // 4️⃣ 유저 ID 저장
     *                 dto.setContent(msg.getContent()); // 5️⃣ 메시지 내용 저장
     *                 dto.setRoomId(msg.getRoomId()); // 6️⃣ 채팅방 ID 저장
     *                 dto.setTimestamp(msg.getTimestamp()); // 7️⃣ 메시지 전송 시간 저장
     *                 return dto; // 8️⃣ 변환된 DTO 반환
     *             })
     *             .collect(Collectors.toList()); // 9️⃣ 변환된 DTO 리스트를 최종 반환
     * }
     * */


}
