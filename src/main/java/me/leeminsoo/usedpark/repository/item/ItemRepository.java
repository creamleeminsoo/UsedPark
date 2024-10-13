package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByTitleContaining(String keyword,Pageable pageable);
    long count();
    @Query("SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "LEFT JOIN i.images img " +
            "ORDER BY i.createdAt DESC")
    List<ItemListResponseDTO> findTop6RecentItems(Pageable pageable);


    @Query(value = "SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "LEFT JOIN  i.images img " +
            "WHERE i.address.id = :addressId " +
            "AND (img.isRepresentative = true OR img IS NULL)",
        countQuery = "SELECT COUNT(i) FROM Item i WHERE i.address.id = :addressId") // image가 null일수도있으니 LEFT JOIN 사용
    Page<ItemListResponseDTO> findByAddressWithRepresentativeImage(@Param("addressId") Long addressId, Pageable pageable);


    @Query(value = "SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "LEFT JOIN  i.images img " +
            "WHERE i.category.id = :categoryId " +
            "AND (img.isRepresentative = true OR img IS NULL)",
            countQuery = "SELECT COUNT(i) FROM Item i WHERE i.category.id = :categoryId")
    Page<ItemListResponseDTO> findByCategoryWithRepresentativeImage(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "LEFT JOIN  i.images img " +
            "WHERE img.isRepresentative = true OR img IS NULL",
            countQuery = "SELECT COUNT(i) FROM Item i"
    )
    Page<ItemListResponseDTO> findAllWithRepresentativeImage(Pageable pageable);

    @Query(value = "SELECT new me.leeminsoo.usedpark.dto.item.ItemListResponseDTO(i, size(i.carts), img) " +
            "FROM Item i " +
            "JOIN FETCH i.category c " +
            "JOIN FETCH i.address a " +
            "LEFT JOIN i.images img " +
            "WHERE i.user.id = :userId " +
            "AND (img.isRepresentative = true OR img IS NULL)",
            countQuery = "SELECT COUNT(i) FROM Item i WHERE i.user.id = :userId"
    )
    Page<ItemListResponseDTO> findAllWithRepresentativeImageByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.user.nickname LIKE %:nickname%")
    Page<Item> findByUserNicknameContaining(String nickname, Pageable pageable);


}
