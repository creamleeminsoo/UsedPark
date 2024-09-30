package me.leeminsoo.usedpark.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRoomRequest {
    @NotNull
    private Long sellerId;

    private Long buyerId;

    @NotNull
    private String itemTitle;

    @NotNull
    private Long itemId;

}
