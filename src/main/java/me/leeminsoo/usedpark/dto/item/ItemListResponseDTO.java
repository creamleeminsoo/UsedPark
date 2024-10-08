package me.leeminsoo.usedpark.dto.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.ItemImage;
import me.leeminsoo.usedpark.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemListResponseDTO {
    private Long id;
    private String title;
    private String brand;
    private String price;
    private User user;
    private Category category;
    private Address address;
    private int cartCount;
    private ItemImage image;
    private boolean itemStatus;

    public ItemListResponseDTO(Item item,int cartCount,ItemImage image){
        this.id = item.getId();
        this.title = item.getTitle();
        this.brand = item.getBrand();
        this.price = item.getPrice();
        this.user = item.getUser();
        this.category = item.getCategory();
        this.address = item.getAddress();
        this.cartCount = cartCount;
        this.image = image;
        this.itemStatus = item.isItemStatus();
    }
}
