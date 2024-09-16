package me.leeminsoo.usedpark.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.dto.board.comment.AddCommentRequestDTO;
import me.leeminsoo.usedpark.dto.board.comment.UpdateCommentDTO;
import me.leeminsoo.usedpark.repository.board.CommentRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }



    @DisplayName("deleteComment: 댓글삭제에 성공한다")
    @Test
    public void deleteComment() throws Exception {
        final String url = "/api/comments/{commentId}";
        final String title = "title";
        final String content = "content";
        final String commentContent = "commentContent";

        Post post = Post.builder().title(title).content(content).build();
        postRepository.save(post);
        Comment comment = Comment.builder().post(post).content(commentContent).build();
        commentRepository.save(comment);

        ResultActions resultActions = mockMvc.perform(delete(url,comment.getId()));

        resultActions.andExpect(status().isOk());
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isZero();
    }

    @DisplayName("updateComment: 댓글 수정에 성공한다")
    @Test
    public void updateComment() throws Exception{
        final String url = "/api/comments/{commentId}";
        final String title = "title";
        final String content = "content";
        final String commentContent = "commentContent";
        final String newComment = "newComment";

        Post post = Post.builder().title(title).content(content).build();
        postRepository.save(post);
        Comment comment = Comment.builder().post(post).content(commentContent).build();
        commentRepository.save(comment);

        UpdateCommentDTO updateCommentDTO = new UpdateCommentDTO(newComment);
        String userRequest = objectMapper.writeValueAsString(updateCommentDTO);

        ResultActions resultActions = mockMvc.perform(put(url, comment.getId()).contentType(MediaType.APPLICATION_JSON_VALUE).content(userRequest));

        resultActions.andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments.get(0).getContent()).isEqualTo(newComment);
    }

    @DisplayName("getComments 댓글 조회에 성공한다")
    @Test
    public void getComments() throws Exception {
        final String url = "/api/comments";
        final String title = "title";
        final String content = "content";
        final String commentContent = "commentContent";
        Post post = Post.builder().title(title).content(content).build();
        postRepository.save(post);
        Comment comment = Comment.builder().post(post).content(commentContent).build();
        commentRepository.save(comment);

        ResultActions resultActions = mockMvc.perform(get(url));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(commentContent));

    }


}