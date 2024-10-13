package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    void deleteByItemId(Long itemId);
    List<ItemImage> findByItemId(Long itemId);
    ItemImage findByItemIdAndIsRepresentativeTrue(Long itemId); // ItemImage는 null일수도 있으니 Optional 객체 사용 x

    @Query("SELECT img " +
            "FROM ItemImage img " +
            "WHERE img.item.id IN :itemIds AND img.isRepresentative = true")
    List<ItemImage> findAllRepresentativeImagesByItems(@Param("itemIds") List<Long> itemIds);


}
