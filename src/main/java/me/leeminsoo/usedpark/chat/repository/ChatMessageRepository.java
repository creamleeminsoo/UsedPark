package me.leeminsoo.usedpark.chat.repository;

import me.leeminsoo.usedpark.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomId(Long roomId);
    void deleteBySendTimeBefore(LocalDateTime dateTime);
}
