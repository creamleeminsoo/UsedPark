package me.leeminsoo.usedpark.controller.api.post;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.LikeResponse;
import me.leeminsoo.usedpark.service.board.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<LikeResponse> addLike(@PathVariable(name = "postId") Long postId , @AuthenticationPrincipal User user){

        int likeCount = likeService.addLike(postId,user);
        LikeResponse likeResponse = new LikeResponse(likeCount);
        return ResponseEntity.status(HttpStatus.OK).body(likeResponse);
    }



}
