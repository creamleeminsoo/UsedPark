package me.leeminsoo.usedpark.dto.board.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCommentDTO {
    @Size(min = 1,max = 100)
    private String content;
}
