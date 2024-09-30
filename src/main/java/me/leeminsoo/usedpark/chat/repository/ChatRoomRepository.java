package me.leeminsoo.usedpark.chat.repository;

import me.leeminsoo.usedpark.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findByBuyerIdAndSellerIdAndItemId(Long buyerId, Long sellerId, Long itemId);

    @Query("SELECT c FROM ChatRoom c WHERE c.buyer.id = :userId OR c.seller.id = :userId")
    List<ChatRoom> findByUserId(@Param("userId") Long userId);
}
