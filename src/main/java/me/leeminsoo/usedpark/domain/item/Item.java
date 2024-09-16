package me.leeminsoo.usedpark.domain.item;

import jakarta.persistence.*;
import lombok.*;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private String price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "content")
    private String content;

    @Column(name = "item_status",columnDefinition = "boolean default true")
    private boolean itemStatus = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<ItemImage> images;

    @OneToMany(mappedBy = "item",cascade = CascadeType.REMOVE)
    private List<Cart> carts;

    @ManyToMany
    @JoinTable(
            name = "item_address",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

    @Builder
    public Item(String title,String brand,String price,User user,String content,Category category, List<Address> addresses){
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.user = user;
        this.content = content;
        this.category = category;
        this.addresses = addresses;
    }

    public void update(String title,String brand,String price,String content,Category category, List<Address> addresses){
        this.title = title;
        this.brand = brand;
        this.content = content;
        this.category = category;
        this.addresses = addresses;
        this.price = price;
    }
    public void itemStatusUpdate(boolean itemStatus) {
        this.itemStatus = !itemStatus;
    }


}
