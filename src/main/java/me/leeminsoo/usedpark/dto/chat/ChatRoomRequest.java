package me.leeminsoo.usedpark.dto.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRoomRequest {
    @NotNull
    private Long sellerId;

    private Long buyerId;
}
