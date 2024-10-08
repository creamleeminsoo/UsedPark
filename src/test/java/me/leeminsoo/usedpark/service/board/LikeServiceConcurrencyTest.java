package me.leeminsoo.usedpark.service.board;

import me.leeminsoo.usedpark.config.error.exception.notpound.UserNotFoundException;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.repository.board.BoardRepository;
import me.leeminsoo.usedpark.repository.board.LikeRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class LikeServiceConcurrencyTest {

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;

    private Long postId;
    private static final int THREAD_COUNT = 100;

    @BeforeEach
    public void setUp() {
        boardRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
        likeRepository.deleteAll();

        Board board = new Board("test");
        boardRepository.save(board);

        for (int i = 1; i <= THREAD_COUNT; i++) {
            User testUser = new User("user" + i + "@test.com", "user" + i);
            userRepository.save(testUser);
        }

        User user = userRepository.findByEmail("user1@test.com").orElseThrow(() -> new UserNotFoundException());
        Post post = Post.builder().board(board).user(user).title("첫번째글").content("내용").build();
        Post savedPost = postRepository.save(post);
        postId = savedPost.getId();
    }

    @DisplayName("100명의 유저가 동시에 좋아요를 누를 시 좋아요가 100이 된다")
    @Test
    public void testConcurrentLikesWithMultipleUsers() throws InterruptedException {
        System.out.println("게시글 ID: " + postId);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 4; i <= THREAD_COUNT+4; i++) {
            int userId = i;
            executorService.submit(() -> {
                try {
                    User testUser = userRepository.findById((long) userId)
                            .orElseThrow(() -> new IllegalArgumentException("유저 ID " + userId + "를 찾을 수 없습니다."));
                    likeService.addLike(postId, testUser);
                } catch (Exception e) {
                    System.err.println("에러 발생: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        int finalLikeCount = likeRepository.countLikesByPostId(postId);
        System.out.println("좋아요 수: " + finalLikeCount);
        Assertions.assertEquals(THREAD_COUNT, finalLikeCount);
    }
}
