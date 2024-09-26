package me.leeminsoo.usedpark.dto.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatAlarmDTO {
    private Long roomId;
    private String senderId;
    private LocalDateTime senderTime;
}
