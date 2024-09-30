package me.leeminsoo.usedpark.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ChatMessageDTO {
    private Long roomId;
    private String message;
    private String sender;
    private LocalDateTime sendTime;
    private Long receiver;
}
