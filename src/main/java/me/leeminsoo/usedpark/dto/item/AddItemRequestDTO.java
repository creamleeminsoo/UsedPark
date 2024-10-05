package me.leeminsoo.usedpark.dto.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequestDTO {
    @Size(min = 1,max = 20,message = "1부터 20글자 이내 값만 입력 할 수있습니다.")
    private String title;
    @NotNull(message = "브랜드는 필수 입력값 입니다.")
    private String brand;

    @NotNull(message = "가격은 필수 입력값 입니다.")
    private String price;

    private User user;

    @Size(max = 1000)
    private String content;

    @NotNull(message = "카테고리는 필수 선택입니다")
    private Long categoryId;

    @NotNull(message = "주소는 필수 선택입니다")
    private Long addressId;

    private byte representativeImageIndex;


}
