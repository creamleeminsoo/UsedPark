package me.leeminsoo.usedpark.chat.controller;


import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.dto.alarm.ChatAlarmDTO;
import me.leeminsoo.usedpark.chat.dto.ChatMessageDTO;
import me.leeminsoo.usedpark.service.alarm.AlarmService;
import me.leeminsoo.usedpark.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

    private final ChatService chatService;
    private final AlarmService alarmService;

    @MessageMapping("/{roomId}")
    @SendTo("/queue/{roomId}")
    public ChatMessageDTO chat(@DestinationVariable Long roomId, ChatMessageDTO dto){
        ChatAlarmDTO chatAlarmDTO = ChatAlarmDTO.builder().roomId(roomId).senderId(dto.getSender()).senderTime(dto.getSendTime()).build();
        alarmService.customAlarm(dto.getReceiver(),chatAlarmDTO,"새로운 채팅이 도착했습니다","chat");
        return chatService.createChat(roomId,dto);
    }
}

