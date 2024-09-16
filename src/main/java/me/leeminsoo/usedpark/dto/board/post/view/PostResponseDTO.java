package me.leeminsoo.usedpark.dto.board.post.view;



import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.board.PostImage;


import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter

public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;
    private int likeCount;
    private List<PostImage> images;
    private String nickName;
    private int view;
    private LocalDateTime createdAt;


    @Builder
    public PostResponseDTO(Long id,String title, String content, List<Comment> comments, List<PostImage> images, String nickName,int view,LocalDateTime createdAt,int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.images = images;
        this.nickName = nickName;
        this.view = view;
        this.createdAt = createdAt;
        this.likeCount = likeCount;

    }
}
