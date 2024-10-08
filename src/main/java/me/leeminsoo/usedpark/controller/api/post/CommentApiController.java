package me.leeminsoo.usedpark.controller.api.post;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.comment.AddCommentRequestDTO;
import me.leeminsoo.usedpark.dto.board.comment.UpdateCommentDTO;
import me.leeminsoo.usedpark.service.board.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;
    Map<String,Object> response;
    @PostMapping("/api/{postId}/comments")
        public ResponseEntity<Map<String,Object>> saveComment(@RequestBody @Validated AddCommentRequestDTO dto,@PathVariable Long postId,@AuthenticationPrincipal User user){
        dto.setUser(user);
        Comment comment = commentService.saveComment(dto,postId);
        response = new HashMap<>();
        response.put("id",comment.getId());
        response.put("message","댓글이 성공적으로 생성되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "commentId") Long commentId, @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId,user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<Map<String,Object>> updateComment(@PathVariable(name = "commentId") Long commentId,
                                                                  @RequestBody @Validated UpdateCommentDTO dto,
                                                                  @AuthenticationPrincipal User user){
        Comment comment = commentService.updateComment(commentId,dto,user);
        response = new HashMap<>();
        response.put("id",comment.getId());
        response.put("message","댓글이 성공적으로 수정되었습니다.");
        return ResponseEntity.ok().body(response);
    }

}
