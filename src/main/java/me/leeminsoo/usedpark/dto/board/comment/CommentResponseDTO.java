package me.leeminsoo.usedpark.dto.board.comment;

import lombok.Getter;
import me.leeminsoo.usedpark.domain.board.Comment;

@Getter
public class CommentResponseDTO {
    private final String content;

    public CommentResponseDTO(Comment comment){
        this.content = comment.getContent();
    }
}
