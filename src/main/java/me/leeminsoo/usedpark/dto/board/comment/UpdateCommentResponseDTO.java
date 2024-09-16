package me.leeminsoo.usedpark.dto.board.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Comment;

@NoArgsConstructor
@Getter
@Setter
public class UpdateCommentResponseDTO {
    private String content;

    public UpdateCommentResponseDTO(Comment comment) {
        this.content = comment.getContent();
    }
}
