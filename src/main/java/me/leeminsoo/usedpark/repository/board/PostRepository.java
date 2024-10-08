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

    @Query("select p from Post p left join p.likes l where p.board.id = :boardId group by p order by count(l) desc")
    Page<Post> findByBoardIdOrderByLikes(Pageable pageable, @Param("boardId") Long boardId);

    @Query("select p from Post p left join p.likes l group by p order by count(l) desc")
    List<Post> findTopPostsOrderByLikes(Pageable pageable);








}
