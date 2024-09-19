package me.leeminsoo.usedpark.controller.mvc.chat;


import lombok.RequiredArgsConstructor;

import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.chat.ChatRoom;
import me.leeminsoo.usedpark.dto.chat.ChatMessageDTO;
import me.leeminsoo.usedpark.dto.chat.ChatRoomRequest;
import me.leeminsoo.usedpark.service.chat.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/queue/{roomId}")
    public ChatMessageDTO chat(@DestinationVariable Long roomId, ChatMessageDTO dto){
        return chatService.createChat(roomId,dto);
    }

    @PostMapping("/chat")
    public String chatView(@RequestBody @Validated ChatRoomRequest request, Model model){
        if (request.getSellerId() == null){
            throw new UserNotAuthenticationException();
        }else {
            ChatRoom room = chatService.createRoom(request);
            model.addAttribute("room",room);
            return "/chat/chat";
        }
    }
}
