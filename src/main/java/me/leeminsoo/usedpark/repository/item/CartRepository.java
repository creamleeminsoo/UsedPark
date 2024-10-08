package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Cart;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    boolean existsByItemAndUser(Item item, User user);
    Optional<Cart> findByItemId(Long itemId);

    @Modifying
    @Query(value = "INSERT INTO cart (item_id, user_id) VALUES (:itemId, :userId)", nativeQuery = true)
    void addCart(@Param("itemId") Long itemId, @Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) FROM Cart WHERE item.id = :itemId")
    int countCartsByItemId(@Param("itemId") Long itemId);

    @Query("SELECT i FROM Item i JOIN Cart c ON i.id = c.item.id WHERE c.user.id = :userId") // List<cart>로 맵핑 문제생겨서 JPQL 사용 쿼리 결과를 JPA가 자동으로 엔티티에 매핑
    List<Item> findItemsByUserId(@Param("userId") Long userId);



}
