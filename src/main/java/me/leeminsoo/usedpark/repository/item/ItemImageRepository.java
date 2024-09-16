package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    void deleteByItemId(Long itemId);
    List<ItemImage> findByItemId(Long itemId);
}
