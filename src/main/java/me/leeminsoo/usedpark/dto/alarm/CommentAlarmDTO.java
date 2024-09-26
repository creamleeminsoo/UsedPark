package me.leeminsoo.usedpark.dto.alarm;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentAlarmDTO {
    private Long postId;
    private LocalDateTime createdAt;
    private Long boardId;
}
