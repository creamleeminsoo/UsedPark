package me.leeminsoo.usedpark.repository.board;

import jakarta.transaction.Transactional;
import me.leeminsoo.usedpark.domain.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findByBoardId(Pageable pageable,Long boardId);
    Page<Post> findByUserId(Pageable pageable, Long userId);
    Page<Post> findByUserIdAndBoardId(Pageable pageable, Long userId, Long boardId);
    Page<Post> findByTitleContaining(String keyword,Pageable pageable);
    Page<Post> findByContentContaining(String keyword,Pageable pageable);
    Page<Post> findByTitleContainingAndBoardId(String keyword, Long boardId, Pageable pageable);
    Page<Post> findByContentContainingAndBoardId(String keyword, Long boardId, Pageable pageable);
    long count();

    @Modifying
    @Transactional
    @Query("update Post p set p.view = p.view + 1 where p.id = :postId")
    void updateView(@Param("postId") Long postId);

    @Query("SELECT p FROM Post p LEFT JOIN p.likes l WHERE p.board.id = :boardId GROUP BY p ORDER BY count(l) desc")
    Page<Post> findByBoardIdOrderByLikes(Pageable pageable, @Param("boardId") Long boardId);

    @Query("SELECT p FROM Post p LEFT JOIN p.likes l GROUP BY p ORDER BY count(l) desc")
    List<Post> findTopPostsOrderByLikes(Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.comments " +
            "JOIN FETCH p.user " +
            "WHERE p.id = :postId" // 댓글이 없는 게시글도 있기에 left join
    )
    Optional<Post> findByIdWithFetchJoin(@Param("postId") Long postId);









}
