package com.example.chatapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 여러 개의 메시지가 하나의 사용자에게 연결됨
    @JoinColumn(name = "user_id", nullable = false) // users 테이블의 ID 참조
    private User sender; // User엔티티와 관계맺기

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String roomId; // 채팅방 ID

    @Column(nullable = false)
    private String timestamp; // 메시지 전송 시간


}
