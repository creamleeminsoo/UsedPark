package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByTitleContaining(String keyword,Pageable pageable);
    Page<Item> findByAddresses_Id(Long addressId, Pageable pageable);
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Item> findByUserId(Long userId,Pageable pageable);
    long count();
    List<Item> findTop6ByOrderByCreatedAtDesc();

    @Query("SELECT i FROM Item i WHERE i.user.nickname LIKE %:nickname%")
    Page<Item> findByUserNicknameContaining(String nickname, Pageable pageable);

}
