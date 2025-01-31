package me.leeminsoo.usedpark.dto.board.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequestDTO {

    @Size(min = 1,max = 100)
    private String content;
    private User user;



    public AddCommentRequestDTO(String content) {
        this.content = content;
    }


}
