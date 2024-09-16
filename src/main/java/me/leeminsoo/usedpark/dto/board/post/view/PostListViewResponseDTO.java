package me.leeminsoo.usedpark.dto.board.post.view;

import lombok.Getter;


import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;

@Getter
@Setter

public class PostListViewResponseDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final Integer view;
    private final User user;
    private int likesCount;
    private String boardName;

    public PostListViewResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.user = post.getUser();

    }

    public PostListViewResponseDTO(Post post,int likesCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.user = post.getUser();
        this.likesCount = likesCount;
        this.boardName = post.getBoard().getName();
    }


}
