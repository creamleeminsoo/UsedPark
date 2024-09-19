package me.leeminsoo.usedpark.domain.chat;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(name = "send_time",updatable = false)
    private LocalDateTime sendTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @Builder
    public ChatMessage(String sender, String message, ChatRoom room,LocalDateTime sendTime){
        this.sender = sender;
        this.message = message;
        this.room = room;
        this.sendTime = sendTime;
    }
}
