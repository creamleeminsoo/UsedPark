package me.leeminsoo.usedpark.dto.board.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDTO {
    @NotNull
    private String title;
    @Size(min = 1,max = 1000)
    private String content;
}
