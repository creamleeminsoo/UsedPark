package me.leeminsoo.usedpark.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.ItemImage;
import me.leeminsoo.usedpark.domain.user.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ItemResponseDTO {
    private Long id;
    private String title;
    private String brand;
    private String price;
    private User user;
    private Category category;
    private List<Address> addresses;
    private String content;
    private int cartCount;
    private List<ItemImage> images;

    public ItemResponseDTO(Item item, int cartCount){
        this.id = item.getId();
        this.title = item.getTitle();
        this.brand = item.getBrand();
        this.price = item.getPrice();
        this.user = item.getUser();
        this.category = item.getCategory();
        this.addresses = item.getAddresses();
        this.cartCount = cartCount;
        this.images = item.getImages();
        this.content = item.getContent();
    }
}
