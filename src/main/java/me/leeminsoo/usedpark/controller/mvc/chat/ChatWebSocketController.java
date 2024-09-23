package me.leeminsoo.usedpark.controller.mvc.chat;


import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.dto.chat.AlarmMessage;
import me.leeminsoo.usedpark.dto.chat.ChatMessageDTO;
import me.leeminsoo.usedpark.service.chat.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/{roomId}")
    @SendTo("/queue/{roomId}")
    public ChatMessageDTO chat(@DestinationVariable Long roomId, ChatMessageDTO dto){
        messagingTemplate.convertAndSend("/queue/user/" + dto.getReceiver(),new AlarmMessage(dto.getRoomId()
        ));
        return chatService.createChat(roomId,dto);
    }

    @MessageMapping("/user/{userId}")
    public void alarm(@DestinationVariable Long userId) {
    }


}

