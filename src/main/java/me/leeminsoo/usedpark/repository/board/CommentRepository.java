package me.leeminsoo.usedpark.repository.board;

import me.leeminsoo.usedpark.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment , Long> {
    long count();
}
