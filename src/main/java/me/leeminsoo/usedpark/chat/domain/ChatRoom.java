package me.leeminsoo.usedpark.chat.domain;


import jakarta.persistence.*;
import lombok.*;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;

import java.util.List;

@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "room",cascade = CascadeType.REMOVE)
    private List<ChatMessage> messages;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ChatRoom(String name,User buyer,User seller,Item item){
        this.name = name;
        this.buyer = buyer;
        this.seller = seller;
        this.item = item;
    }

}
