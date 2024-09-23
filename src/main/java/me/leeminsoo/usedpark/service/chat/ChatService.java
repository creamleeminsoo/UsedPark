package me.leeminsoo.usedpark.service.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.ChatRoomNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.ItemNotFoundException;
import me.leeminsoo.usedpark.domain.chat.ChatMessage;
import me.leeminsoo.usedpark.domain.chat.ChatRoom;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.chat.ChatMessageDTO;
import me.leeminsoo.usedpark.dto.chat.ChatRoomRequest;
import me.leeminsoo.usedpark.dto.chat.AlarmMessage;
import me.leeminsoo.usedpark.repository.chat.ChatMessageRepository;
import me.leeminsoo.usedpark.repository.chat.ChatRoomRepository;
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ChatRoom createRoom(ChatRoomRequest request) {
        Long buyerId = request.getBuyerId();
        Long sellerId = request.getSellerId();
        Long itemId = request.getItemId();
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        ChatRoom room = chatRoomRepository.findByBuyerIdAndSellerIdAndItemId(buyerId, sellerId, itemId);

        if (room == null) {
            List<User> users = userRepository.findAllById(List.of(buyerId, sellerId));
            if (users.size() != 2) {
                throw new IllegalArgumentException("자신과 채팅할수없습니다");
            }

            User buyer = users.stream().filter(u -> u.getId().equals(buyerId)).findFirst().get();
            User seller = users.stream().filter(u -> u.getId().equals(sellerId)).findFirst().get();

            return chatRoomRepository.save(ChatRoom.builder().name(request.getItemTitle()).buyer(buyer).seller(seller).item(item).build());
        } else {
            return room;
        }
    }


    public ChatMessageDTO createChat(Long roomId, ChatMessageDTO dto){
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMessage message = ChatMessage.builder()
                .sender(dto.getSender())
                .message(dto.getMessage())
                .room(room)
                .sendTime(dto.getSendTime())
                .build();
        chatMessageRepository.save(message);
        return dto;
    }

   public ChatRoom getRoomById(Long roomId){
        return chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
   }
   public List<ChatRoom> getChatRooms(Long userId){
        return chatRoomRepository.findByUserId(userId);
   }


}
