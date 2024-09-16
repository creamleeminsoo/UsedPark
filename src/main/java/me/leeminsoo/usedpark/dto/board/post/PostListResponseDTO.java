package me.leeminsoo.usedpark.dto.board.post;

import lombok.Getter;
import me.leeminsoo.usedpark.domain.board.Post;



@Getter
public class PostListResponseDTO {
    private final String title;
    private final String content;

    public PostListResponseDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
