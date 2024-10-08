package me.leeminsoo.usedpark.dto.board.post;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.user.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPostRequestDTO {
    @Size(min =1, max = 20)
    private String title;

    @Size(min = 1, max = 1000)
    private String content;

    private Board board;

    private User user;


}
