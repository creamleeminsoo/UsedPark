package me.leeminsoo.usedpark.controller.api.post;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.comment.*;
import me.leeminsoo.usedpark.dto.board.comment.AddCommentRequestDTO;
import me.leeminsoo.usedpark.service.board.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/{postId}/comments")
        public ResponseEntity<AddCommentResponseDTO> saveComment(@RequestBody @Validated AddCommentRequestDTO dto,@PathVariable Long postId,@AuthenticationPrincipal User user){
        dto.setUser(user);
        Comment comment = commentService.saveComment(dto,postId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddCommentResponseDTO(comment));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "commentId") Long commentId, @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId,user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponseDTO> updateComment(@PathVariable(name = "commentId") Long commentId,
                                                                  @RequestBody @Validated UpdateCommentDTO dto,
                                                                  @AuthenticationPrincipal User user){
        Comment comment = commentService.updateComment(commentId,dto,user);
        return ResponseEntity.ok().body(new UpdateCommentResponseDTO(comment));
    }

    @GetMapping("/api/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments() {
        List<CommentResponseDTO> comments = commentService.getComments().stream().map(CommentResponseDTO::new).toList();

        return ResponseEntity.ok().body(comments);
    }

}
