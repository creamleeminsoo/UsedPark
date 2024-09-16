package me.leeminsoo.usedpark.repository.board;

import jakarta.transaction.Transactional;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.board.PostLike;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<PostLike ,Long> {

    boolean existsByPostAndUser(Post post, User user);

    @Modifying
    @Query(value = "INSERT INTO likes (post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void addLike(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) FROM likes WHERE post_id = :postId", nativeQuery = true)
    int countLikesByPostId(@Param("postId") Long postId);


}
