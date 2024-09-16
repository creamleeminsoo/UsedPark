package me.leeminsoo.usedpark.dto.board.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequestDTO {

    @NotNull
    private String content;
    private User user;



    public AddCommentRequestDTO(String content) {
        this.content = content;
    }


}
