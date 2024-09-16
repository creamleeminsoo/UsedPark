package me.leeminsoo.usedpark.dto.board.post.view;


import lombok.Getter;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Post;

@Getter
@Setter

public class PopularPostsResponseDTO {
    private Long id;
    private String title;
    private String author;
    private int commentCount;
    private int likeCount;
    private Board board;

    public PopularPostsResponseDTO(Post post,int commentCount,int likeCount){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getUser().getNickname();
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.board = post.getBoard();
    }

}
