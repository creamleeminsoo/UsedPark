package me.leeminsoo.usedpark.chat.controller;


import lombok.RequiredArgsConstructor;

import me.leeminsoo.usedpark.chat.domain.ChatRoom;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.chat.dto.ChatRoomRequest;
import me.leeminsoo.usedpark.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;


    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> createChatRoom(@RequestBody @Validated ChatRoomRequest request, @AuthenticationPrincipal User user) {

            request.setBuyerId(user.getId());
            ChatRoom room = chatService.createRoom(request);
            Map<String, Object> response = new HashMap<>();
            response.put("roomId", room.getId());
            return ResponseEntity.ok(response);
    }

    @GetMapping("/chat/{roomId}")
    public String getChatRoom(@PathVariable Long roomId, @AuthenticationPrincipal User user, Model model) {

        ChatRoom room = chatService.getRoomById(roomId);

        model.addAttribute("user", user);
        model.addAttribute("room", room);
        return "/chat/chat";
    }
    @GetMapping("/chat")
    public String getChatRooms(@AuthenticationPrincipal User user,Model model){

        List<ChatRoom> chatRooms = chatService.getChatRooms(user.getId());
        model.addAttribute("rooms",chatRooms);
        model.addAttribute("user",user);

        return "/chat/chatList";
    }
    @ResponseBody
    @DeleteMapping("/chat/{roomId}")
    public ResponseEntity<Map<String,Object>> deleteChatRoom(@PathVariable Long roomId,@AuthenticationPrincipal User user){
        Long id = chatService.deleteChatRoom(roomId,user.getId());
        Map<String,Object> response = new HashMap<>();
        response.put("id",id);
        response.put("message","채팅방 을 나가셨습니다");
        return ResponseEntity.ok(response);
    }
}
