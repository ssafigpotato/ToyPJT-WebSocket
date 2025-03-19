package com.example.chatapp.repository;

import com.example.chatapp.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// ChatRepository 추가 (JPA를 사용해 메시지 저장)
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    // ChatMessage의 기본키가 Long타입이니까 Long
    // JpaRepository<ChatMessage, Long> → ChatMessage 엔티티를 DB에 저장/조회하는 기능 제공
    // findByRoomIdOrderByTimestampAsc(String roomId) → 특정 채팅방 메시지를 시간순으로 정렬해 조회
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);

}
