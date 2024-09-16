package me.leeminsoo.usedpark.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE) //카테고리 생성 삭제는 관리자만 가능하게 할것이므로
    @JsonIgnore //순환참조 예외생길수있으므로 category객체만 가져올땐 item객체에 의존관계는 필요없음
    private List<Item> items;

}
