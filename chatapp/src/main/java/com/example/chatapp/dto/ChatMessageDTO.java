package com.example.chatapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    private Long userId; // userID 저장
    private String content;
    private String roomId;
    private String timestamp;
}
