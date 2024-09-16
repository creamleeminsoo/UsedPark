package me.leeminsoo.usedpark.dto.board.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentResponseDTO {
    private Long id;
    private String content;

    public AddCommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.content= comment.getContent();
    }
}
