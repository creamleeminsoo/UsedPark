package me.leeminsoo.usedpark.controller.api.post;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.AddPostRequestDTO;
import me.leeminsoo.usedpark.dto.board.post.PostListResponseDTO;
import me.leeminsoo.usedpark.dto.board.post.UpdatePostDTO;
import me.leeminsoo.usedpark.service.board.BoardService;
import me.leeminsoo.usedpark.service.board.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final BoardService boardService;
    Map<String, Object> response;

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostListResponseDTO>> getPosts() {
        List<PostListResponseDTO> posts = postService.findAll().stream()
                .map(PostListResponseDTO::new)
                .toList();
        return ResponseEntity.ok()
                .body(posts);
    }
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<PostListResponseDTO> getPost(@PathVariable(name = "postId") Long postId){
        Post post = postService.findById(postId);

        return ResponseEntity.ok()
                .body(new PostListResponseDTO(post));
    }
    @PostMapping("/api/posts")
    public ResponseEntity<Map<String, Object>> addPost(@RequestPart("post") @Validated AddPostRequestDTO dto,
                                                        @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles,
                                                        @RequestParam(name = "boardId") Long boardId, @AuthenticationPrincipal User user) {

        dto.setBoard(boardService.getBoard(boardId));
        dto.setUser(user);
        Post post = postService.save(dto,imageFiles);
        response = new HashMap<>();
        response.put("id", post.getId());
        response.put("message","글이 성공적으로 생성되었습니다");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "postId") Long postId,@AuthenticationPrincipal User user){

        postService.delete(postId, user);

        return ResponseEntity.ok().build();

    }
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<Map<String,Object>> updatePost(@PathVariable(name = "postId") Long postId,
                                           @RequestPart("post") @Validated UpdatePostDTO dto,
                                           @AuthenticationPrincipal User user,
                                           @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles){
        Post post = postService.update(postId,dto,user,imageFiles);
        response = new HashMap<>();
        response.put("id",post.getId());
        response.put("message","성공적으로 수정완료 되었습니다");


        return ResponseEntity.ok().body(response);
    }


}
