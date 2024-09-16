package me.leeminsoo.usedpark.domain.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url",nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @Column(name = "is_representative", nullable = false)
    private boolean isRepresentative;

    public String getFilename() {
        return url.substring(url.lastIndexOf("/") + 1);
    }


}
