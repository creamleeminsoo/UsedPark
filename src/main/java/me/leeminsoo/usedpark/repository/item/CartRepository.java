package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Cart;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
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

//    @Query("SELECT DISTINCT i FROM Item i " +
//            "JOIN FETCH i.category " +
//            "JOIN FETCH i.address " +
//            "JOIN FETCH i.user " +
//            "JOIN FETCH i.carts c " +
//            "WHERE c.user.id = :userId") // 쿼리 나누기
//    List<Item> findItemsInCartByUser(@Param("userId") Long userId);

    @Query("SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "JOIN FETCH i.user u " +
            "LEFT JOIN i.images img " +
            "WHERE (img.isRepresentative = true OR img IS NULL) AND i.id IN (" +
            "SELECT cart.item.id FROM Cart cart WHERE cart.user.id = :userId)") // 한방쿼리
    List<ItemListResponseDTO> findItemsInCartByUser(@Param("userId") Long userId);











}
