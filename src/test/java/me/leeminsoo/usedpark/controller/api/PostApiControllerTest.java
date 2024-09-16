package me.leeminsoo.usedpark.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.AddPostRequestDTO;
import me.leeminsoo.usedpark.repository.board.BoardRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PostApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();
    }

    @DisplayName("addPost 글 추가에 성공한다")
    @Test
    public void addPost() throws Exception{
        final String url = "/api/posts";
        final String title = "title";
        final String content = "content";

        Board board = new Board("hello");
        User user = User.builder().email("email@naver.com")
                .nickname("nick").build();
        final AddPostRequestDTO userRequest = new AddPostRequestDTO(title,content,board,user);

        final String requestBody = objectMapper.writeValueAsString(userRequest);


        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isCreated());
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }
    @DisplayName("getPost 글 조회에 성공한다")
    @Test
    public void getPost() throws Exception {
        final String url = "/api/posts/{postId}";
        final String title = "title";
        final String content = "content";

        Board board = new Board("hello");
        final Post post = Post.builder().title(title).content(content).build();
        postRepository.save(post);

        ResultActions resultActions = mockMvc.perform(get(url, post.getId()));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));

    }
    @DisplayName("getPosts 글 목록조회에 성공한다")
    @Test
    public void getPosts() throws Exception {
        final String url = "/api/posts";
        final String title = "title";
        final String content = "content";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        ResultActions resultActions = mockMvc.perform(get(url));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("deletePost 글 삭제에 성공한다")
    @Test
    public void deletePost() throws Exception {
        final String url = "/api/posts/{postId}";
        final String title = "title";
        final String content= "content";

        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();
        postRepository.save(post);

        ResultActions resultActions = mockMvc.perform(delete(url, post.getId()));

        resultActions.andExpect(status().isOk());
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isZero();
    }

    @DisplayName("updatePost 글 수정에 성공한다")
    @Test
    public void updatePost() throws Exception{
        final String url = "/api/posts/{postId}";
        final String title = "oldTitle";
        final String content = "oldContent";
        final String newTitle = "newTitle";
        final String newContent = "newContent";

        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();
        postRepository.save(post);

        Post post1 = Post.builder().
                title(newTitle)
                .content(newContent)
                .build();
        String requestBody = objectMapper.writeValueAsString(post1);

        ResultActions resultActions = mockMvc.perform(put(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(status().isOk());

        List<Post> posts = postRepository.findAll();

        assertThat(posts.get(0).getTitle()).isEqualTo(newTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(newContent);

    }

}