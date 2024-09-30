package me.leeminsoo.usedpark.chat;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.chat.service.ChatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ChatMessageCleanupScheduler {
    private final ChatService chatService;

    @Scheduled(cron = "0 0 1 * * ?", zone =  "Asia/Seoul") // 오전 1시에 실행
    public void deleteOldMessages() {
        chatService.deleteOldMessages();
    }



}
