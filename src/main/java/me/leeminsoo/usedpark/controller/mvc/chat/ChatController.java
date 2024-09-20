package me.leeminsoo.usedpark.controller.mvc.chat;


import lombok.RequiredArgsConstructor;

import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.UserNotFoundException;
import me.leeminsoo.usedpark.domain.chat.ChatRoom;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.chat.ChatMessageDTO;
import me.leeminsoo.usedpark.dto.chat.ChatRoomRequest;
import me.leeminsoo.usedpark.service.chat.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> createChatRoom(@RequestBody @Validated ChatRoomRequest request, @AuthenticationPrincipal User user) {
        if (user == null) {
            throw new UserNotAuthenticationException();
        } else {
            request.setBuyerId(user.getId());
            ChatRoom room = chatService.createRoom(request);
            Map<String, Object> response = new HashMap<>();
            response.put("roomId", room.getId());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/chat")
    public String getChatRoom(@RequestParam Long roomId, @AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            throw new UserNotAuthenticationException();
        }
        ChatRoom room = chatService.getRoomById(roomId);
        model.addAttribute("user", user);
        model.addAttribute("room", room);
        return "/chat/chat";
    }


}
