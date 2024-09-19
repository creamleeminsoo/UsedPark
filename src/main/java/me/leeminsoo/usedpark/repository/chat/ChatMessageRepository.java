package me.leeminsoo.usedpark.repository.chat;

import me.leeminsoo.usedpark.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomId(Long roomId);
}
