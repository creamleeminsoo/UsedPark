package me.leeminsoo.usedpark.repository.chat;

import me.leeminsoo.usedpark.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findByBuyerIdAndSellerId(Long buyerId, Long sellerId);

    ChatRoom findBySellerIdAndBuyerId(Long sellerId, Long buyerId);
}
