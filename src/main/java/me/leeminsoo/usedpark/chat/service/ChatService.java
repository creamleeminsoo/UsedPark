package me.leeminsoo.usedpark.chat.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.leeminsoo.usedpark.chat.domain.ChatMessage;
import me.leeminsoo.usedpark.chat.domain.ChatRoom;
import me.leeminsoo.usedpark.chat.dto.ChatMessageDTO;
import me.leeminsoo.usedpark.chat.dto.ChatRoomRequest;
import me.leeminsoo.usedpark.chat.repository.ChatMessageRepository;
import me.leeminsoo.usedpark.chat.repository.ChatRoomRepository;
import me.leeminsoo.usedpark.config.error.exception.notpound.ChatRoomNotFoundException;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.service.item.ItemService;
import me.leeminsoo.usedpark.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public ChatRoom createRoom(ChatRoomRequest request) {
        Long buyerId = request.getBuyerId();
        Long sellerId = request.getSellerId();
        Long itemId = request.getItemId();
        Item item = itemService.findItem(itemId);

        ChatRoom room = chatRoomRepository.findByBuyerIdAndSellerIdAndItemId(buyerId, sellerId, itemId);

        if (room == null) {
            List<User> users = userService.findAllById(List.of(buyerId, sellerId));
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
    @Transactional
    public void deleteOldMessages(){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1); // 일주일전 메시지
        chatMessageRepository.deleteBySendTimeBefore(oneWeekAgo);
        log.info("오래된 메세지 삭제 (기준 날짜: {})", oneWeekAgo);

    }

    @Transactional
    public ChatMessageDTO createChat(Long roomId, ChatMessageDTO dto){

        ChatRoom room = entityManager.getReference(ChatRoom.class, roomId);
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

   @Transactional
   public Long deleteChatRoom(Long roomId,Long userId){
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        if (room.getBuyer() != null && room.getBuyer().getId().equals(userId)){
            room.setBuyer(null);
        }else {
            room.setSeller(null);
        }
        Map<String, Object> message = new HashMap<>();
        message.put("message", "상대방이 채팅방에서 나갔습니다.");
        message.put("userId", userId);
        messagingTemplate.convertAndSend("/queue/" + roomId, message);

        if (room.getBuyer() == null && room.getSeller() == null){
            chatRoomRepository.deleteById(roomId);
        }
        return roomId;
   }


}
