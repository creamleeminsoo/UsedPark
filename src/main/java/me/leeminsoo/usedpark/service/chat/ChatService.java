package me.leeminsoo.usedpark.service.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.ChatRoomNotFoundException;
import me.leeminsoo.usedpark.domain.chat.ChatMessage;
import me.leeminsoo.usedpark.domain.chat.ChatRoom;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.chat.ChatMessageDTO;
import me.leeminsoo.usedpark.dto.chat.ChatRoomRequest;
import me.leeminsoo.usedpark.repository.chat.ChatMessageRepository;
import me.leeminsoo.usedpark.repository.chat.ChatRoomRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createRoom(ChatRoomRequest request){
        Long buyerId = request.getBuyerId();
        Long sellerId = request.getSellerId();
        ChatRoom room = chatRoomRepository.findByBuyerIdAndSellerId(buyerId,sellerId);
        if (room == null) {
            User buyer = userRepository.findById(buyerId).orElseThrow(UserNotAuthenticationException::new);
            User seller = userRepository.findById(sellerId).orElseThrow(UserNotAuthenticationException::new);
            return chatRoomRepository.save(new ChatRoom(seller,buyer));
        }else {
            return room;
        }
    }

    public ChatMessageDTO createChat(Long roomId, ChatMessageDTO dto){
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMessage message = ChatMessage.builder()
                .sender(dto.getSender())
                .message(dto.getMessage())
                .room(room)
                .build();
        chatMessageRepository.save(message);
        return dto;
    }

    public List<ChatMessage> getChatMessages(Long roomId){
        return chatMessageRepository.findAllByRoomId(roomId);
    }
}
